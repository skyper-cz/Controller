package cz.ugv;

import java.io.*;
import java.net.*;

public class Komunikace {
    public static void Communication(String adresa, int port, String smerJizdy) throws UnknownHostException, SocketException {

        InetAddress address = InetAddress.getByName(adresa);
        DatagramSocket socket = new DatagramSocket();
        System.out.println("Odesílám na: " + adresa + ":" + port + " A jedeme: " + smerJizdy);

        try {
            byte[] buffer = smerJizdy.getBytes();
            DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(request);
        }
        catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }
}
}
