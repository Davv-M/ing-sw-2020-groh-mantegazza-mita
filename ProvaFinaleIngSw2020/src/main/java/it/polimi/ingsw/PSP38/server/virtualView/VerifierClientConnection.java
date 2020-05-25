package it.polimi.ingsw.PSP38.server.virtualView;

import java.io.IOException;

public class VerifierClientConnection implements Runnable{
    private final ClientHandler client;

    public VerifierClientConnection(ClientHandler ch){
        client = ch;
    }

    public void run(){
        try {
            while (true) {
                client.ping();
                Thread.sleep(10000);
            }
        } catch (InterruptedException e){

        } catch (IOException e){
            System.out.println("connection lost with: "+client.getNickname());
        }
    }
}
