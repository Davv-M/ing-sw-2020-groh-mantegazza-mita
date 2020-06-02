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

public class ServerHandler extends Observable implements Observer, Runnable{

    private static ObjectInputStream input;
    private static ObjectOutputStream output;
    private final static Client nextRequestObserver = new Client();
    private static Protocol protocolRead;
    private static Message message;
    private static String customMessageString;
    private static List<Byte> board;
    private static final Object lock = new Object();

    /**
     * Is the only class constructor
     * @param serverSocket is the socket used to communicate with server
     */
    public ServerHandler(Socket serverSocket){

        try {
            output = new ObjectOutputStream(serverSocket.getOutputStream());
            input = new ObjectInputStream(serverSocket.getInputStream());
            serverSocket.setSoTimeout(6000);
            ServerConnectionHandler serverConnectionHandler = new ServerConnectionHandler(this);
            Thread serverConnectionHandlerThread = new Thread(serverConnectionHandler);
            serverConnectionHandlerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method executes the operations needed to listen server constantly
     */

    public void run() {
        try {
            while (true) {
                switch ((Protocol) input.readObject()){
                    case NOTIFY_MESSAGE:{
                        protocolRead = Protocol.NOTIFY_MESSAGE;
                        setMessage();
                        notifyClient();
                        break;
                    }
                    case TOO_LATE:{
                        endGame(Protocol.TOO_LATE);
                        break;
                    }
                    case DISPLAY_BOARD:{
                        protocolRead = Protocol.DISPLAY_BOARD;
                        setBoard();
                        notifyClient();
                        break;
                    }
                    case ASK_INT:{
                        protocolRead = Protocol.ASK_INT;
                        notifyClient();
                        break;
                    }
                    case ASK_STRING:{
                        protocolRead = Protocol.ASK_STRING;
                        notifyClient();
                        break;
                    }
                    case CANT_MOVE:{
                        endGame(Protocol.CANT_MOVE);
                        break;
                    }
                    case NOTIFY_CUSTOM_STRING:{
                        protocolRead = Protocol.NOTIFY_CUSTOM_STRING;
                        setCustomMessageString();
                        notifyClient();
                        break;
                    }
                    case PING:{
                        break;
                    }
                    case CLIENT_LOST:{
                        endGame(Protocol.CLIENT_LOST);
                        break;
                    }
                    default:
                        System.out.println("protocol error");
                }

            }
        }catch (IOException | ClassNotFoundException e){
            serverLost();
        }


    }



    /**
     * notify observers that is available a new data or request from the server
     */
    private static void notifyClient(){
        nextRequestObserver.update();
    }

    /**
     * set the last message received
     */
    private static void setMessage() throws IOException,ClassNotFoundException {
        message = (Message) input.readObject();

    }

    /**
     *
     */
    private static void setCustomMessageString() throws IOException, ClassNotFoundException {
        customMessageString = (String) input.readObject();
    }

    /**
     *
     */
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
     * @param writeString is the string written
     */
    public static void writeString(String writeString)throws IOException{
        synchronized (lock){
            output.writeObject(Protocol.RETURN_STRING);
            output.writeObject(writeString);
        }

    }

    /**
     * Write an int to server by using Stream
     * @param writeInt is the int written
     */
    public static void writeInt(int writeInt)throws IOException{
        synchronized (lock){
            output.writeObject(Protocol.RETURN_INT);
            output.writeInt(writeInt);
            output.flush();
        }



    }


    /**
     * @return the last protocol read
     */
    public static Protocol getProtocol(){ return protocolRead;}

    /**
     * @return the last message read
     */
    public static Message getMessage(){ return message;}

    /**
     * @return the last board read
     */
    public static List<Byte> readBoard(){ return board;}

    /**
     * If required write the new client input to the server by using </writeString()> or </writeInt()>
     */
    @Override
    public void update(){
        if(protocolRead == Protocol.ASK_INT){
            int number;
            try {
                number = Integer.parseInt(Client.getStringInput());
                ServerHandler.writeInt(number);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e){
                System.out.println(Client.getStringInput() + " is not a number, please enter an integer number");
            }
        }
        if(protocolRead == Protocol.ASK_STRING){
            try {
                writeString(Client.getStringInput());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void ping()throws IOException{
        synchronized (lock){
            output.writeObject(Protocol.PING);
        }
    }

    public void endGame(Protocol protocolExit){
        switch (protocolExit){
            case TOO_LATE:{
                System.out.println("game full, please try later.");
                break;
            }
            case CLIENT_LOST:{
                System.out.println("your challenger lost connection, please restart app");
                break;
            }
            case CANT_MOVE:{
                System.out.println("you can't move, You Lose!");
                break;
            }

        }
        System.exit(0);
    }

    public void serverLost(){
        System.out.println("connection lost with server, please restart app ");
        System.exit(0);
    }



}
