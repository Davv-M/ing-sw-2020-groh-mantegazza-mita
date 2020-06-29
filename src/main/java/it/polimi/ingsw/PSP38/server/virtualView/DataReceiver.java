package it.polimi.ingsw.PSP38.server.virtualView;

import it.polimi.ingsw.PSP38.common.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Class used to receive data from the specific client
 *
 * @author Matteo Mita (10487862)
 */
public class DataReceiver implements Runnable {
    private final ClientHandler client;
    private final ObjectInputStream input;
    private int lastIntRead;
    private String lastStringRead;

    /**
     * Constructor class
     *
     * @param ch the specific Clienthandler of the client to listen
     */
    DataReceiver(ClientHandler ch) {
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
        } catch (IOException | ClassNotFoundException ignored) {
        }

    }

    /**
     * Getter of <code>lastIntRead</code>.
     *
     * @return the last int read from the specific <code>client</code>
     */
    public int getLastIntRead() {
        return lastIntRead;
    }

    /**
     * Getter of <code>lastStringRead</code>.
     *
     * @return the last String read from the specific <code>client</code>
     */
    public String getLastStringRead() {
        return lastStringRead;
    }


}
