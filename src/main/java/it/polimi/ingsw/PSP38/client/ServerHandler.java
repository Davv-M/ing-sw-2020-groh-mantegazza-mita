package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.Message;
import it.polimi.ingsw.PSP38.common.Protocol;
import it.polimi.ingsw.PSP38.common.utilities.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

/**
 * this class is responsible for communication client-server
 * notify client when read something by an observer pattern
 */

public class ServerHandler extends Observable implements Observer, Runnable {

    private static ObjectInputStream input;
    private static ObjectOutputStream output;
    private final static Client nextRequestObserver = new Client();
    private static Protocol protocolRead;
    private static Message message;
    private static String customMessageString;
    private static List<Byte> board;
    private static int numOfPlayers = 0;
    private static Map<String, String> playersDivinities = null;
    private static final Object lock = new Object();

    /**
     * Is the only class constructor
     *
     * @param serverSocket is the socket used to communicate with server
     */
    public ServerHandler(Socket serverSocket) {

        try {
            output = new ObjectOutputStream(serverSocket.getOutputStream());
            input = new ObjectInputStream(serverSocket.getInputStream());
            serverSocket.setSoTimeout(6000);
            ServerConnectionHandler serverConnectionHandler = new ServerConnectionHandler(this);
            Thread serverConnectionHandlerThread = new Thread(serverConnectionHandler);
            serverConnectionHandlerThread.start();
        } catch (IOException e) {
            serverLost();
        }
    }

    /**
     * This method executes the operations needed to listen server constantly
     */

    public void run() {
        try {
            while (true) {
                switch ((Protocol) input.readObject()) {
                    case NOTIFY_MESSAGE: {
                        protocolRead = Protocol.NOTIFY_MESSAGE;
                        setMessage();
                        notifyClient();
                        break;
                    }
                    case TOO_LATE: {
                        protocolRead = Protocol.TOO_LATE;
                        notifyClient();
                        break;
                    }
                    case DISPLAY_BOARD: {
                        protocolRead = Protocol.DISPLAY_BOARD;
                        setBoard();
                        notifyClient();
                        break;
                    }
                    case ASK_INT: {
                        protocolRead = Protocol.ASK_INT;
                        notifyClient();
                        break;
                    }
                    case NOTIFY_NUM_PLAYERS: {
                        protocolRead = Protocol.NOTIFY_NUM_PLAYERS;
                        numOfPlayers = input.readInt();
                        notifyClient();
                        break;
                    }
                    case NOTIFY_NICKNAME: {
                        protocolRead = Protocol.NOTIFY_NICKNAME;
                        setCustomMessageString();
                        notifyClient();
                        break;
                    }
                    case NOTIFY_PLAYERS_DIVINITIES: {
                        protocolRead = Protocol.NOTIFY_PLAYERS_DIVINITIES;
                        playersDivinities = (Map<String, String>) input.readObject();
                        notifyClient();
                        break;
                    }
                    case ASK_STRING: {
                        protocolRead = Protocol.ASK_STRING;
                        notifyClient();
                        break;
                    }
                    case CANT_MOVE: {
                        protocolRead = Protocol.CANT_MOVE;
                        notifyClient();
                        break;
                    }
                    case NOTIFY_CUSTOM_STRING: {
                        protocolRead = Protocol.NOTIFY_CUSTOM_STRING;
                        setCustomMessageString();
                        notifyClient();
                        break;
                    }
                    case PING: {
                        break;
                    }
                    case CLIENT_LOST: {
                        protocolRead = Protocol.CLIENT_LOST;
                        notifyClient();
                        break;
                    }
                    default:
                        System.out.println("protocol error");
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            serverLost();
        }


    }


    /**
     * notify observers new data has been received from the server
     */
    private void notifyClient() {
        nextRequestObserver.update();
    }

    /**
     * set the last message received
     */
    private void setMessage() throws IOException, ClassNotFoundException {
        message = (Message) input.readObject();
    }

    /**
     *
     */
    private void setCustomMessageString() throws IOException, ClassNotFoundException {
        customMessageString = (String) input.readObject();
    }


    public static String getCustomMessageString() {
        return customMessageString;
    }

    /**
     * set the last board received
     */
    private static void setBoard() throws IOException {
        byte rows = input.readByte();
        byte columns = input.readByte();
        List<Byte> encodedBoard = new LinkedList<>();
        encodedBoard.add(rows);
        encodedBoard.add(columns);
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < columns; ++col) {
                byte b = input.readByte();
                encodedBoard.add(b);
            }
        }
        board = encodedBoard;
    }

    /**
     * Write a string to server by using Stream
     *
     * @param writeString is the string written
     */
    public static void writeString(String writeString) throws IOException {
        synchronized (lock) {
            output.writeObject(Protocol.RETURN_STRING);
            output.writeObject(writeString);
        }

    }

    /**
     * Write an int to server by using Stream
     *
     * @param writeInt is the int written
     */
    public static void writeInt(int writeInt) throws IOException {
        synchronized (lock) {
            output.writeObject(Protocol.RETURN_INT);
            output.writeInt(writeInt);
            output.flush();
        }


    }


    /**
     * @return the last protocol read
     */
    public static Protocol getProtocol() {
        return protocolRead;
    }

    /**
     * @return the last message read
     */
    public static Message getMessage() {
        return message;
    }

    /**
     * @return the last board read
     */
    public static List<Byte> readBoard() {
        return board;
    }

    /**
     * If required write the new client input to the server by using <code>writeString</code> or <code>writeInt</code>
     */
    @Override
    public void update() {
        if (protocolRead == Protocol.ASK_INT) {
            int number;
            try {
                number = Integer.parseInt(Client.getStringInput());
                ServerHandler.writeInt(number);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println(Client.getStringInput() + " is not a number, please enter an integer number");
            }
        }
        if (protocolRead == Protocol.ASK_STRING) {
            try {
                writeString(Client.getStringInput());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void ping() throws IOException {
        synchronized (lock) {
            output.writeObject(Protocol.PING);
        }
    }


    public void serverLost() {
        protocolRead = Protocol.NOTIFY_MESSAGE;
        message = Message.SERVER_LOST;
        notifyClient();
    }


    public static int getNumOfPlayers() {
        return numOfPlayers;
    }

    public static Map<String, String> getPlayersDivinities() {
        return playersDivinities;
    }
}
