package it.polimi.ingsw.PSP38.client;
import java.io.IOException;

public class ServerConnectionHandler implements Runnable{
    private final ServerHandler serverHandler;

    public ServerConnectionHandler(ServerHandler sh){
        serverHandler = sh;
    }

    public void run(){
        try {
            while (true) {
                serverHandler.ping();
                Thread.sleep(3000);
            }
        } catch (InterruptedException ignored){
        } catch (IOException e){
            serverHandler.serverLost();
        }
    }
}