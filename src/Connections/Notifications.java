package Connections;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Notifications extends Thread {

    private static final Logger LOGGER = Logger.getLogger(Advertisement.class.getName());
    private static InetAddress GROUP;
    private static int PORT = 43000;
    private String MCAST_ADDR = "FF02::1";
    private String interfaz_name = "en3";

    MulticastSocket multicastSocket = null;
    int packetsize = 256;
    
    ArrayList<ArrayList<String>> listFilesForUser;
    ArrayList<String> usersName;
    
    public Notifications(String mcast_addr, int port) {
        this.PORT = port;

        try {
            GROUP = Inet6Address.getByAddress(MCAST_ADDR, InetAddress.getByName(MCAST_ADDR).getAddress(), NetworkInterface.getByName(interfaz_name));
        } catch (SocketException ex) {
            Logger.getLogger(Notifications.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
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
                System.out.println("Cliente " + mensaje);
            }
        } catch (IOException ex) {
            Logger.getLogger(Notifications.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
