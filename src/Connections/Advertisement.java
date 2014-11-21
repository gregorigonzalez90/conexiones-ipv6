package Connections;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Advertisement extends Thread {

    private static final Logger LOGGER = Logger.getLogger(Advertisement.class.getName());
    private int PORT;
    private String MCAST_ADDR;
    private InetAddress GROUP;
    private String interface_name;
    private String dir_public;
    
    DatagramSocket serverSocket = null;
    int packetsize = 256;
    int timeForReUpload = 1000;
    

    
    public Advertisement(String mcast_addr, String interface_name, int port, String dir_public) {
        this.interface_name = interface_name;
        this.MCAST_ADDR = mcast_addr;
        this.PORT = port;
        this.dir_public = dir_public;
        
        try {
            GROUP = Inet6Address.getByAddress(MCAST_ADDR, InetAddress.getByName(MCAST_ADDR).getAddress(), NetworkInterface.getByName(interface_name));
            //GROUP = Inet6Address.getByAddress(MCAST_ADDR, InetAddress.getByName(MCAST_ADDR).getAddress(), 10);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Advertisement.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Advertisement.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            serverSocket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Advertisement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] listPublicFiles(String dir_public) {
        File file = new File(dir_public);
        File[] files = null;
        if (file.exists()) {
            files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.isFile()) {
                        return true;
                    }
                    return false;
                }
            });
            String[] fileNames = new String[files.length];
            for (int x = 0; x < files.length; x++) {
                fileNames[x] = files[x].getName();
            }
            return fileNames;
        } else {
            System.out.println("No Hay Archivos");
        }
        return new String[]{""};
    }

    public void sendPublicFilesAvailable(String[] list) {
        Charset set = Charset.forName("UTF-8");
        for (String file : list) {
            DatagramPacket packet = new DatagramPacket(file.getBytes(set), file.getBytes(set).length, GROUP, PORT);
            try {
                serverSocket.send(packet);
            } catch (IOException ex) {
                Logger.getLogger(Advertisement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void run() {
        while (true) {
            try {
                sendPublicFilesAvailable(listPublicFiles(dir_public));
                Thread.sleep(timeForReUpload);
            } catch (InterruptedException ex) {
                Logger.getLogger(Advertisement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
