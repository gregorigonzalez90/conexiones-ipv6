package Connections;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Notifications {

    private static InetAddress GROUP;
    private static int PORT = 43000;
    private String MCAST_ADDR = "FF02::1";
    private String interfaz_name = "en3";

    public Notifications(String mcast_addr, int port) {
        this.PORT = port;

        try {
            GROUP = Inet6Address.getByAddress(MCAST_ADDR, InetAddress.getByName(MCAST_ADDR).getAddress(), NetworkInterface.getByName(interfaz_name));
        } catch (SocketException ex) {
            Logger.getLogger(Notifications.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Notifications.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static Thread client() {
        return new Thread(new Runnable() {
            public void run() {
                try {
                    MulticastSocket multicastSocket = null;

                    multicastSocket = new MulticastSocket(PORT);
                    multicastSocket.joinGroup(GROUP);
                    System.out.println("IP multicast: " + multicastSocket.getInterface().getHostAddress());
                    while (true) {

                        byte[] receiveData = new byte[256];

                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length, GROUP, PORT);
                        multicastSocket.receive(receivePacket);
                        System.out.println("Cliente " + new String(receivePacket.getData(), 0, 5));
//                                LOGGER.info("Client received from : " + receivePacket.getAddress() + ", " + new String(receivePacket.getData()));

                    }
                } catch (IOException ex) {
                    Logger.getLogger(Notifications.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }
}
