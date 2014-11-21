package Connections;

import Controllers.ListFilesUserComponent;
import Controllers.ListUsersComponent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Notifications extends Thread {

    private static final Logger LOGGER = Logger.getLogger(Advertisement.class.getName());
    private InetAddress GROUP;
    private int PORT;
    private String MCAST_ADDR;
    private String interface_name;
    private String dir_public;
    
    MulticastSocket multicastSocket = null;
    int packetsize = 256;
    
    ArrayList<ArrayList<String>> listFilesForUser;
    ArrayList<String> usersName;
    private ListFilesUserComponent fileJListManagement;
    private ListUsersComponent listUsersComboBox;
    
    public Notifications(String mcast_addr, String interface_name, int port, String dir_public) {
        this.interface_name = interface_name;
        this.MCAST_ADDR = mcast_addr;
        this.PORT = port;
        this.dir_public = dir_public;
        
        try {
            GROUP = Inet6Address.getByAddress(MCAST_ADDR, InetAddress.getByName(MCAST_ADDR).getAddress(), NetworkInterface.getByName(interface_name));
            //GROUP = Inet6Address.getByAddress(MCAST_ADDR, InetAddress.getByName(MCAST_ADDR).getAddress(), 10);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Notifications.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Notifications.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            multicastSocket = new MulticastSocket(PORT);
            multicastSocket.joinGroup(GROUP);
        } catch (IOException ex) {
            Logger.getLogger(Notifications.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        listFilesForUser = new ArrayList<>();
        usersName = new ArrayList<>();

    }
    
    public void addFileToUser(String filename, String ipAddress) { 
        if(!usersName.contains(ipAddress)) {
            usersName.add(ipAddress);
            listFilesForUser.add(usersName.lastIndexOf(ipAddress), new ArrayList<String>());
        }
        ArrayList<String> temp_array = listFilesForUser.get(usersName.indexOf(ipAddress));
        if(!temp_array.contains(filename)) { 
            temp_array.add(filename);
        }
        renderListUsers();
    }
    
    public void renderListUsers() { 
        listUsersComboBox.refreshList(usersName);
    }
 
    public void run() {
        try {
            LOGGER.info("IP multicast: " + multicastSocket.getInterface().getHostAddress());

            while (true) {
                byte[] receiveData = new byte[packetsize];
                DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length, GROUP, PORT);
                multicastSocket.receive(packet);
                String mensaje = new String(packet.getData(), 0, packet.getLength());
                if(mensaje.matches(".*\\..*")) { 
                    addFileToUser(mensaje, packet.getAddress().getHostAddress());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Notifications.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setFilesUserComponent(ListFilesUserComponent fileJListManagement) {
        this.fileJListManagement = fileJListManagement;
    }

    public void setUserComponent(ListUsersComponent listUsersComboBox) {
        this.listUsersComboBox = listUsersComboBox;
    }
}
