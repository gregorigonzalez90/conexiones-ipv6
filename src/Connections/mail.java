
package Connections;

import java.util.logging.Logger;

public class mail {

    private static final Logger LOGGER = Logger.getLogger(Advertisement.class.getName());
    private static final int PORT = 43000;
    private static final String MCAST_ADDR = "FF02::1";

    public static void main(String[] args) {

        Advertisement ad = new Advertisement();
        String[] a = ad.listPublicFiles("public");
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}
