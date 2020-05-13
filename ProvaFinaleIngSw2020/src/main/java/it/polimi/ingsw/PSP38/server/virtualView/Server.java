package it.polimi.ingsw.PSP38.server.virtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Executable class for the server side of Santorini
 */

public class Server {
    public final static int SERVER_SOCKET_PORT = 3456;
    private static int contPlayer = 0;
    private static final List<ClientHandler> listForSpuriousWakeUp = new LinkedList<>();

    /**
     * Main method of the server side of Santorini that supervises the creation of a thread for each client connected to
     * the server
     *
     * @param args
     */
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(SERVER_SOCKET_PORT);
            System.out.println("Server online");
            //System.out.println("Server IP: "+ InetAddress.getLocalHost());
            do {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                listForSpuriousWakeUp.add(clientHandler);
                Thread threadClient = new Thread(clientHandler);
                threadClient.start();
            } while (true);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method wakes up all the clients put into the <code>listForSpuriousWakeUp</code> linked list through the mehod
     * <code>setImInWait</code> of <code>ClientHandler</code>
     */
    public static void wakeUpAll(){
        for(ClientHandler client : listForSpuriousWakeUp){
            client.setPaused(false);
        }
    }

    /**
     * Synchronized method used to update the current amount of players
     *
     * @return the parameter <code>contPlayer</code> increased by one
     */
    public static synchronized int updateContPlayer(){
        return ++contPlayer;
    }


}