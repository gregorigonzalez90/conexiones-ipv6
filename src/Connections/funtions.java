package Connections;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class funtions {

    DataInputStream in = null;

    public funtions() {
    }

    public File[] ListarDirect() {
        File file = new File("public/");
        File[] ficheros = null;
        if (file.exists()) {
            ficheros = file.listFiles();
            for (int x = 0; x < ficheros.length; x++) {
                System.out.println(ficheros[x].getName());
            }
        } else {
            System.out.println("No Hay Archivos");
        }
        return ficheros;
    }
    
    public void receives_file(Socket so1) {

        try {
            System.out.println("Entre en recibe del servidor");
            in = new DataInputStream(so1.getInputStream());
            // Obtenemos el nombre del archivo
            String nombreArchivo = in.readUTF().toString();
            System.out.println("recibe nombre" + nombreArchivo);
            // Obtenemos el tamaño para el archivo
            int tam = in.readInt();
            System.out.println("Recibe tamaño " + tam);
            //System.out.println(disi.readUTF());
            System.out.println("Recibiendo Archivo " + nombreArchivo + " con tamaño de " + tam + " Kb...");
            // Creamos flujo de salida, este flujo nos sirve para
            // indicar donde guardaremos el archivo
            FileOutputStream foss = new FileOutputStream(nombreArchivo);
            BufferedOutputStream outt = new BufferedOutputStream(foss);
            BufferedInputStream inn = new BufferedInputStream(so1.getInputStream());
            // Creamos el array de bytes para leer los datos del archivo
            byte[] buffer = new byte[tam];
            // Obtenemos el archivo mediante la lectura de bytes enviados
            for (int i = 0; i < buffer.length; i++) {
                buffer[ i] = (byte) inn.read();
            }
            // Escribimos el archivo
            outt.write(buffer);
            // Cerramos flujos
            outt.flush();
            inn.close();
            outt.close();
        } catch (IOException ex) {
            Logger.getLogger(funtions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(funtions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }



    }

    public void send_file(Socket so1, String Name) {
        DataOutputStream salidaa;
        try {
            File archivo = new File(Name);
            int tamaño = (int) archivo.length();

            // Creamos el flujo de salida, este tipo de flujo nos permite hacer la escritura de diferentes tipos de datos tales como Strings, boolean, caracteres y la familia de enteros, etc.
            salidaa = new DataOutputStream(so1.getOutputStream());

            System.out.println("Enviando nombre del Archivo");
            salidaa.writeUTF(Name);
            salidaa.writeInt(tamaño);
            //salida.writeUTF("enviando extra");

            // Creamos flujo de entrada para realizar la lectura del archivo en bytes
            FileInputStream fiss = new FileInputStream(Name);
            BufferedInputStream biss = new BufferedInputStream(fiss);

            // Creamos el flujo de salida para enviar los datos del archivo en bytes
            BufferedOutputStream boss = new BufferedOutputStream(so1.getOutputStream());

            // Creamos un array de tipo byte con el tamaño del archivo 
            byte[] buffer = new byte[tamaño];

            // Leemos el archivo y lo introducimos en el array de bytes 
            biss.read(buffer);

            // Realizamos el envio de los bytes que conforman el archivo
            for (int i = 0; i < buffer.length; i++) {
                boss.write(buffer[ i]);
            }
            System.out.println("Archivo Enviado: " + archivo.getName());
            // Cerramos socket y flujos
            biss.close();
            boss.close();
            //so1.close(); 
        } catch (IOException ex) {
            Logger.getLogger(funtions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        funtions fun = new funtions();
        fun.ListarDirect();
    }
}
