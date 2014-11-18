package Connections;

import Controllers.ListFilesUserComponent;

public class mail {

    public static void main(String[] args) throws InterruptedException {
        String mcast_addr = "ff02::1";
        int port = 43000;
        String interface_name = "en0";
        String dir_public = "public";
        
        ListFilesUserComponent fileListManagement = new ListFilesUserComponent();
        
        Advertisement ad = new Advertisement(mcast_addr, interface_name, port, dir_public);
        Notifications noti = new Notifications(mcast_addr, interface_name, port, dir_public);
        noti.setFilesUserComponent(fileListManagement);
        
        ad.start();
        noti.start();
        noti.join();
    }
}
