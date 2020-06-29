package it.polimi.ingsw.PSP38.server.virtualView;

import java.io.IOException;

/**
 * @author Matteo Mita (10487862)
 */
public class ClientConnectionHandler implements Runnable{
    private final ClientHandler client;

    public ClientConnectionHandler(ClientHandler ch){
        client = ch;
    }

    public void run(){
        try {
            while (true) {
                client.ping();
                Thread.sleep(3000);
            }
        } catch (InterruptedException e){

        } catch (IOException e){
            updateClientConnection();
            System.out.println("connection lost with: "+client.getNickname());
        }
    }

    public void updateClientConnection(){
        if(client.getTotNumPlayers()==0){
            Server.reduceClientsNum(client.getClientNum());
            Server.reduceContPlayer();
        }
        if(client.getClientNum()<=client.getTotNumPlayers()){
            Server.notifyClientLost();
        }
    }
}
