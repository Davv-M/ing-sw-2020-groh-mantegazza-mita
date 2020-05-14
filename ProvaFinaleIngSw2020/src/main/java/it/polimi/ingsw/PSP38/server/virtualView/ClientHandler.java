package it.polimi.ingsw.PSP38.server.virtualView;

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
    String nickname = "";
    public final int clientNum;
    private static final Controller controller = new Controller();
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean isPaused = false;
    private final Object lock = new Object();

    public ClientHandler(Socket clientSocket) {
        clientNum = Server.updateContPlayer();
        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.addObserver(this);
        VerifierClientConnection verifierClientConnection = new VerifierClientConnection(this, clientNum);
        Thread verifier = new Thread(verifierClientConnection);
        verifier.start();
    }

    public boolean sendAck()throws IOException{
            synchronized (lock){
                output.writeObject(Protocol.ACK);
                return input.readBoolean();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyMessage(String message) throws IOException {
        synchronized (lock){
            output.writeObject(Protocol.NOTIFY_MESSAGE);
            output.writeObject(message);
        }

    }


    public int askInt(Function<Integer, Integer> checkInt) throws IOException {
        int num;
        synchronized (lock) {

            do {
                try {
                    output.writeObject(Protocol.ASK_INT);
                    num = checkInt.apply(input.readInt());
                    return num;
                } catch (IllegalArgumentException e) {
                    notifyMessage(e.getMessage());
                }
            } while (true);
        }
    }

    public String askString(Function<String, String> checkString) throws IOException {
        String string;
        synchronized (lock){
            do {
                try {
                    output.writeObject(Protocol.ASK_STRING);
                    string = checkString.apply((String) input.readObject());
                    return string;
                } catch (IllegalArgumentException e) {
                    notifyMessage(e.getMessage());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
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
}

