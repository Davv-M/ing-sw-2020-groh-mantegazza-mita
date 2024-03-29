package it.polimi.ingsw.PSP38.server.virtualView;

import it.polimi.ingsw.PSP38.common.Protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Executable class for the server side of Santorini
 *
 * @author Matteo Mita (10487862)
 */


public class Server {
    public final static int SERVER_SOCKET_PORT = 3457;
    private static int contPlayer = 0;
    private static final List<ClientHandler> listOfClients = new LinkedList<>();

    /**
     * Main method of the server side of Santorini that supervises the creation of a thread for each client connected to
     * the server
     * @param args
     */
    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(SERVER_SOCKET_PORT);
            System.out.println("Server online on port " + SERVER_SOCKET_PORT);
            do {
                Socket clientSocket = serverSocket.accept();
                clientSocket.setSoTimeout(6000);
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                listOfClients.add(clientHandler);
                Thread threadClient = new Thread(clientHandler);
                threadClient.start();
            } while (true);
        } catch (IOException e) {
            System.exit(1);
        }
    }

    /**
     * This method wakes up all the clients put into the <code>listOfClients</code> linked list through the mehod
     * <code>setPaused</code> of <code>ClientHandler</code>
     */
    public static void wakeUpAll() {
        for (ClientHandler client : listOfClients) {
            client.setPaused(false);
        }
    }

    /**
     * Synchronized method used to update the current amount of players
     *
     * @return the parameter <code>contPlayer</code> increased by one
     */
    public static synchronized int incrementContPlayer() {
        return ++contPlayer;
    }

    /**
     * Synchronized void method used to decrement the current amount of players connected
     */
    public static synchronized void decrementContPlayer() {
        --contPlayer;
    }

    /**
     * Void method used to notify all clients connected that one client lost connection
     */
    public static void notifyClientLost() {
        for (ClientHandler ch : listOfClients) {
            try {
                ch.notifyEndGame(Protocol.CLIENT_LOST);
            } catch (IOException ignore) {}
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ignore) {}
            System.exit(1);
        }
    }

    /**
     * Void method used to decrement <code>ClientNum</code> for all clients who have
     * <code>ClientNum</code> higher than <code>clientLostNum</code>
     *
     * @param clientLostNum ClientNum of client that lost connection
     */
    public static void reduceClientsNum(int clientLostNum) {
        for (ClientHandler ch : listOfClients) {
            if (ch.getClientNum() >= clientLostNum) {
                ch.decrementClientNum();
            }
        }
    }
}