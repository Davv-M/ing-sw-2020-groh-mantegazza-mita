package it.polimi.ingsw.PSP38.server.virtualView;

import it.polimi.ingsw.PSP38.common.Message;
import it.polimi.ingsw.PSP38.common.Protocol;
import it.polimi.ingsw.PSP38.server.controller.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

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
        Thread threadDataReceiver= new Thread(dataReceiver);
        threadDataReceiver.start();
    }

    public void ping()throws IOException{
            synchronized (lock){
                output.writeObject(Protocol.PING);
            }
    }


    public void setPaused(boolean bool) {
        isPaused = bool;
    }

    public boolean isPaused(){ return isPaused;}

    @Override
    public void run() {
        try {
            controller.start(this);
        } catch (IOException ignored) { }
    }

    /*public void notifyMessage(String message) throws IOException {
        synchronized (lock){
            output.writeObject(Protocol.NOTIFY_MESSAGE);
            output.writeObject(message);
        }

    }*/

    public void notifyMessage(Message message) throws IOException {
        synchronized (lock){
            output.writeObject(Protocol.NOTIFY_MESSAGE);
            output.writeObject(message);
        }

    }

    public void notifyMessageString(String customString) throws IOException {
        synchronized (lock){
            output.writeObject(Protocol.NOTIFY_CUSTOM_STRING);
            output.writeObject(customString);
        }
    }


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
                    notifyMessage(Message.ILLEGAL_INT);
                    isDataReady = false;
                }
            } while (true);

    }

    public String askString(Function<String, String> checkString) throws IOException {
        String string;

            do {
                try {
                    synchronized (lock){
                        output.writeObject(Protocol.ASK_STRING);
                    }
                    while (!isDataReady) {
                        Thread.onSpinWait();

                    }
                    string = checkString.apply(dataReceiver.getLastStringRead());
                    isDataReady = false;
                    return string;
                } catch (IllegalArgumentException e) {
                    notifyMessage(Message.ILLEGAL_STRING);
                    isDataReady = false;
                }
            } while (true);

    }

    public void displayBoard() throws IOException {
        synchronized (lock){
            output.writeObject(Protocol.DISPLAY_BOARD);
            for(byte b : controller.getEncodedBoard()){
                output.writeByte(b);
            }
            output.flush();
        }

    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public ObjectInputStream getInputStream(){
        return input;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(!(o instanceof Controller)){
            throw new IllegalArgumentException();
        }
        try {
            displayBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDataReady(){
        isDataReady = true;
    }

    public int getClientNum(){ return clientNum;}

    public void reduceClientNum(){ clientNum--; }

    public int getTotNumPlayers(){
        return controller.getTotNumPlayers();
    }

    public void notifyEndGame(Protocol protocolForEnd)throws IOException{
        synchronized (lock){
            switch (protocolForEnd){
                case TOO_LATE:{
                    output.writeObject(Protocol.TOO_LATE);
                    break;
                }
                case CLIENT_LOST:{
                    output.writeObject(Protocol.CLIENT_LOST);
                    break;
                }
                case CANT_MOVE:{
                    output.writeObject(Protocol.CANT_MOVE);
                    break;
                }
            }

        }
    }
}

