package Connections;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    ServerSocket ServerSocket;
    Socket socket;
    SendFile send;

    public Server(int port) {
        try {
            ServerSocket = new ServerSocket(port);
            send = new SendFile();
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
                send.receives_file(socket);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {

        Server server = new Server(40001);
        server.connet();
    }
}
