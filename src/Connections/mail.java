package Connections;

public class mail {

    public static void main(String[] args) throws InterruptedException {

        Advertisement ad = new Advertisement();
        ad.start();
        Notifications noti = new Notifications("ff02::1", 43000);
        noti.start();
        noti.join();

    }
}
