package Connections;

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class Client {

    Socket client;
    funtions send;
    
    public Client(String direction , int port){
        try {
            client =  new Socket(direction, port);
            send = new funtions();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        Client client =  new Client("::1", 40001);
    }
}