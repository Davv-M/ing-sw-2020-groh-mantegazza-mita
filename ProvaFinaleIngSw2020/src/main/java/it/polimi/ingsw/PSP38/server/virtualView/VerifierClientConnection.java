package it.polimi.ingsw.PSP38.server.virtualView;

import java.io.IOException;

public class VerifierClientConnection implements Runnable{
    private final ClientHandler client;
    private final int numClient;

    public VerifierClientConnection(ClientHandler ch, int nc){
        client = ch;
        numClient = nc;
    }

    public void run(){
        try {
            while (true) {
                if (!client.sendAck()){
                    break;
                }
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("client number "+numClient+" disconnected");
        //solleverà un eccezzione
    }
}
