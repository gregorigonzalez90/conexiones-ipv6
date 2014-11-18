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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Advertisement {

    private static final Logger LOGGER = Logger.getLogger(Advertisement.class.getName());
    private static final int PORT = 43000;
    private static final String MCAST_ADDR = "FF02::1";

    private static InetAddress GROUP;
    private String interfaz_name = "en3";

    public Advertisement() {
        try {
            GROUP = Inet6Address.getByAddress(MCAST_ADDR, InetAddress.getByName(MCAST_ADDR).getAddress(), NetworkInterface.getByName(interfaz_name));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Advertisement.class.getName()).log(Level.SEVERE, null, ex);
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
        return new String[] {""};
    }

    private static Thread server() {
        return new Thread(new Runnable() {
            public void run() {
                try {
                    DatagramSocket serverSocket = null;

                    serverSocket = new DatagramSocket();

                    while (true) {
                        byte[] sendData = new byte[256];
                        String data = new String("Prueba");
                        DatagramPacket sendPacket = new DatagramPacket(data.getBytes(), data.getBytes().length, GROUP, PORT);
                        try {
                            serverSocket.send(sendPacket);
                            System.out.println("Server: " + sendPacket.getAddress());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Advertisement.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(Advertisement.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                } catch (SocketException ex) {
                    Logger.getLogger(Advertisement.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

}
