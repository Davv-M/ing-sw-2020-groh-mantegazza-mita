package it.polimi.ingsw.PSP38.server.virtualView;

import it.polimi.ingsw.PSP38.common.Message;
import it.polimi.ingsw.PSP38.common.Protocol;
import it.polimi.ingsw.PSP38.server.controller.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;


/**
 * Class used to handling data from/to the specific client
 *
 * @author Maximilien Groh (10683107)
 * @author Matteo Mita (10487862)
 */

public class ClientHandler implements Observer, Runnable {
    private String nickname = "anonymous";
    private int clientNum;
    private static final Controller controller = new Controller();
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean isPaused = false;
    private final Object lock = new Object();
    private final DataReceiver dataReceiver;
    private volatile boolean isDataReady = false;

    /**
     * Constructor method of the class
     * Initialises the input and output Stream for the class
     * and starts the thread used to receive data and manage the connection whit the specific client
     *
     * @param clientSocket socket of the client to manage
     */
    public ClientHandler(Socket clientSocket) {
        clientNum = Server.incrementContPlayer();
        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.addObserver(this);
        ClientConnectionHandler clientConnectionHandler = new ClientConnectionHandler(this);
        Thread clientConnectionHandlerThread = new Thread(clientConnectionHandler);
        clientConnectionHandlerThread.start();
        dataReceiver = new DataReceiver(this);
        Thread threadDataReceiver = new Thread(dataReceiver);
        threadDataReceiver.start();
    }

    /**
     * Void method that sent an ack to the client
     *
     * @throws IOException if the client is unreachable
     */
    public void ping() throws IOException {
        synchronized (lock) {
            output.writeObject(Protocol.PING);
        }
    }

    /**
     * Setter of <code>isPaused</code>
     *
     * @param bool boolean that set the pause status of the thread
     */
    public void setPaused(boolean bool) {
        isPaused = bool;
    }

    /**
     * Getter of <code>isPaused</code>
     *
     * @return the boolean values of <code>isPaused</code>
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Start the game for the specific client
     */
    @Override
    public void run() {
        try {
            controller.start(this);
        } catch (IOException ignored) {
        }
    }

    /**
     * Void method used to notify a default message to the client
     *
     * @param message Protocol that represent the specific message
     * @throws IOException if the client is unreachable
     */
    public void notifyMessage(Message message) throws IOException {
        synchronized (lock) {
            output.writeObject(Protocol.NOTIFY_MESSAGE);
            output.writeObject(message);
        }

    }

    /**
     * Void method used to notify a custom message to the client
     *
     * @param customString specific custom message
     * @throws IOException if the client is unreachable
     */
    public void notifyMessageString(String customString) throws IOException {
        synchronized (lock) {
            output.writeObject(Protocol.NOTIFY_CUSTOM_STRING);
            output.writeObject(customString);
        }
    }

    /**
     * Method used to ask an integer values to the client
     *
     * @param checkInt integer values read from the client that needs to be verified
     * @return the integer read
     * @throws IOException if the client is unreachable
     */
    public int askInt(Function<Integer, Integer> checkInt) throws IOException {
        int num;
        do {
            try {
                synchronized (lock) {
                    output.writeObject(Protocol.ASK_INT);
                }
                while (!isDataReady) {
                    Thread.onSpinWait();

                }
                num = checkInt.apply(dataReceiver.getLastIntRead());
                isDataReady = false;
                return num;
            } catch (IllegalArgumentException e) {
                notifyMessageString(e.getMessage());
                notifyMessage(Message.ILLEGAL_INT);
                isDataReady = false;
            }
        } while (true);
    }

    /**
     * Method used to ask a String values to the client
     *
     * @param checkString String values read from the client that needs to be verified
     * @return the String read
     * @throws IOException if the client is unreachable
     */
    public String askString(Function<String, String> checkString) throws IOException {
        String string;
        do {
            try {
                synchronized (lock) {
                    output.writeObject(Protocol.ASK_STRING);
                }
                while (!isDataReady) {
                    Thread.onSpinWait();

                }
                string = checkString.apply(dataReceiver.getLastStringRead());
                isDataReady = false;
                return string;
            } catch (IllegalArgumentException e) {
                notifyMessageString(e.getMessage());
                notifyMessage(Message.ILLEGAL_STRING);
                isDataReady = false;
            }
        } while (true);

    }

    /**
     * Void method used to send the actual Board to the client
     *
     * @throws IOException if the client is unreachable
     */
    public void displayBoard() throws IOException {
        synchronized (lock) {
            output.writeObject(Protocol.DISPLAY_BOARD);
            for (byte b : controller.getEncodedBoard()) {
                output.writeByte(b);
            }
            output.flush();
        }
    }

    /**
     * Getter method of nickname values
     *
     * @return client's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter method of nickname values
     *
     * @param nickname String with values of nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter method of the InputStream
     *
     * @return an ObjectInputStream with client's input stream
     */
    public ObjectInputStream getInputStream() {
        return input;
    }

    /**
     * Called when there is an Board's update. Invoke the displayBoard() method
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof Controller)) {
            throw new IllegalArgumentException();
        }
        try {
            displayBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter method of <code>isDataReady</code> boolean values to true
     */
    public void setDataReady() {
        isDataReady = true;
    }

    /**
     * Getter method of <code>ClientNum</code> values
     *
     * @return clientNum
     */
    public int getClientNum() {
        return clientNum;
    }

    /**
     * Reduce the Client num values
     */
    public void reduceClientNum() {
        clientNum--;
    }

    /**
     * Void method used to notify the client the number of players
     */
    public void notifyNumPlayers(int numPlayers) throws IOException {
        synchronized (lock){
            output.writeObject(Protocol.NOTIFY_NUM_PLAYERS);
            output.writeInt(numPlayers);
        }
    }

    /**
     * Void method used to notify the client the number of players
     */
    public void notifyPlayersDivinities(Map<String, String> map) throws IOException {
        synchronized (lock){
            output.writeObject(Protocol.NOTIFY_PLAYERS_DIVINITIES);
            output.writeObject(map);
        }
    }

    /**
     * Getter method of num Players
     *
     * @return the total num of players
     */
    public int getTotNumPlayers() {
        return controller.getTotNumPlayers();
    }

    /**
     * Void method that notify the client who lost for the <code>protocolForEnd</code> reason
     *
     * @param protocolForEnd reason of end Game
     */
    public void notifyEndGame(Protocol protocolForEnd) throws IOException {
        synchronized (lock) {
            switch (protocolForEnd) {
                case TOO_LATE: {
                    output.writeObject(Protocol.TOO_LATE);
                    break;
                }
                case CLIENT_LOST: {
                    output.writeObject(Protocol.CLIENT_LOST);
                    break;
                }
                case CANT_MOVE: {
                    output.writeObject(Protocol.CANT_MOVE);
                    break;
                }
            }

        }
    }
}

