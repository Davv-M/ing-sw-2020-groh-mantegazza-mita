package it.polimi.ingsw.PSP38.server.virtualView;

import it.polimi.ingsw.PSP38.server.virtualView.ClientHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Executable class for the server side of Santorini
 */

public class Server {
    public final static int SERVER_SOCKET_PORT = 3457;
    private static int contPlayer = 0;
    private static List<ClientHandler> listForSpuriousWakeUp = new LinkedList<>();

    /**
     * Main method of the server side of Santorini that supervises the creation of a thread for each client connected to
     * the server
     * @param args
     * @throws IOException in case of issues with input and/or output streams associated with the clients
     */
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(SERVER_SOCKET_PORT);
            System.out.println("Server started");
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
        for(ClientHandler ch : listForSpuriousWakeUp){
            ch.setImInWait(false);
        }
    }

    /**
     * Synchronized method used to update the current amount of players
     * @return the parameter <code>contPlayer</code> increased by one
     */
    public static synchronized int updateContPlayer(){
        return ++contPlayer;
    }


}