package Connections;

import Views.Primary;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server{

    ServerSocket ServerSocket;
    Socket socket;
    
    public Server (int port) {
        try {
            ServerSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void connet() {
        while (true) {
            try {
                System.out.println("Waiting Connection");
                socket = ServerSocket.accept();
                System.out.println("Is connected" + socket.getInetAddress());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
