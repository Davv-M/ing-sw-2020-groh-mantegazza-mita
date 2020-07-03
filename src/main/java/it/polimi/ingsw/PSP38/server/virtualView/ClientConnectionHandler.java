package it.polimi.ingsw.PSP38.server.virtualView;

import java.io.IOException;


/**
 * Class that handling the connection with the client
 *
 * @author Matteo Mita (10487862)
 */
public class ClientConnectionHandler implements Runnable {
    private final ClientHandler client;

    /**
     * Constructor of the class
     *
     * @param ch clientHandle that wants to check the connection with their client
     */
    public ClientConnectionHandler(ClientHandler ch) {
        client = ch;
    }

    /**
     * Send an ack to the client every 3 seconds
     */
    public void run() {
        try {
            while (true) {
                client.ping();
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {

        } catch (IOException e) {
            updateClientConnection();
            System.out.println("connection lost with: " + client.getNickname());
        }
    }

    /**
     * Notify the Server that the connection with his client is lost
     */
    public void updateClientConnection() {
        if (client.getTotNumPlayers() == 0) {
            Server.reduceClientsNum(client.getClientNum());
            Server.decrementContPlayer();
        }
        if (client.getClientNum() <= client.getTotNumPlayers()) {
            Server.notifyClientLost();
        }
    }
}
