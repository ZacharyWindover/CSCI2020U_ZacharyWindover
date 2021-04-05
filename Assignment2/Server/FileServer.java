package Server;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class FileServer {

    protected   static        Socket                clientSocket =  null;
    protected   static        ServerSocket          serverSocket =  null;
    protected   static        FileServerThread[]    threads      =  null;

    public      static        int                   SERVER_PORT  =  8000;
    public      static  final int                   MAX_CLIENTS  =  100;
    protected   static        int                   numClients   =  0;

    public FileServer() {

        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            threads = new FileServerThread[MAX_CLIENTS];
            System.out.println("SERVER_PORT: " + SERVER_PORT);

            while(true) {
                clientSocket = serverSocket.accept();
                threads[numClients] = new FileServerThread(clientSocket);
                threads[numClients].start();
                numClients++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException while creating server connection.");
        }

    }

    public static void main(String[] args) {

        FileServer application = new FileServer();

    }

}
