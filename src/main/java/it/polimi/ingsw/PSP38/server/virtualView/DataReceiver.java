package it.polimi.ingsw.PSP38.server.virtualView;

import it.polimi.ingsw.PSP38.common.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketTimeoutException;

/**
 * @author Matteo Mita (10487862)
 */

public class DataReceiver implements Runnable{
    private final ClientHandler client;
    private final ObjectInputStream input;
    private int lastIntRead;
    private String lastStringRead;

    DataReceiver (ClientHandler ch){
        client = ch;
        input = ch.getInputStream();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Protocol protocolRead = (Protocol) input.readObject();
                switch (protocolRead) {
                    case PING:
                        break;
                    case RETURN_INT:
                        lastIntRead = input.readInt();
                        client.setDataReady();
                        break;
                    case RETURN_STRING:
                        lastStringRead = (String) input.readObject();
                        client.setDataReady();
                        break;
                }

            }
        }catch (IOException | ClassNotFoundException ignored){}

    }

    public int getLastIntRead(){
        return lastIntRead;
    }

    public String getLastStringRead(){
        return lastStringRead;
    }



}
