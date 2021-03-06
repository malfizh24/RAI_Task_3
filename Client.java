package rai_socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {

    static Socket clientSocket = null;
    static PrintStream outStream = null;
    static DataInputStream inStream = null;
    static BufferedReader inLine = null;
    static boolean close = false;

    public static void main(String[] args) {
        try {
            int port = 8000;
            String host = "localhost";
            if (args.length < 2) {
                System.out.println("Terhubung ke port " + port);
            } else {
                host = args[0];
                port = Integer.valueOf(args[1]).intValue();
            }
            clientSocket = new Socket(host, port);
            inLine = new BufferedReader(new InputStreamReader(System.in));
            outStream = new PrintStream(clientSocket.getOutputStream());
            inStream = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Exception");
        }
        if (clientSocket != null && outStream != null && inStream != null) {
            try {
                new Thread((Runnable) new Client()).start();
                while (!close) {
                    try {
                        outStream.println(inLine.readLine().trim());
                    } catch (IOException ex) {
                        System.out.println("Client chat");
                    }
                }
                outStream.close();
                inStream.close();
                clientSocket.close();
            } catch (IOException ex) {
                System.out.println("Exception");
            }
        }
    }

}
