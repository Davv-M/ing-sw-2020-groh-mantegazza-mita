package it.polimi.ingsw.PSP38.virtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public final static int SOCKET_PORT = 4567;

    public static void main(String[] args) {
        ServerSocket socket;
        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }
        //first client
        try {
            /* accepts connections; for every connection we accept,
             * create a new Thread executing a ClientHandler */
            Socket client = socket.accept();
            ClientHandler clientHandler = new ClientHandler(client);
            Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
            thread.start();
        } catch (IOException e) {
            System.out.println("connection dropped");
        }

        while (true) {
            try {
                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
}
