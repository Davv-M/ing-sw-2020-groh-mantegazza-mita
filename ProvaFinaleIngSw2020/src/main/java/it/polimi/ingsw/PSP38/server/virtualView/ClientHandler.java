package it.polimi.ingsw.PSP38.server.virtualView;

import it.polimi.ingsw.PSP38.common.Protocol;
import it.polimi.ingsw.PSP38.server.controller.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
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

    public ClientHandler(Socket clientSocket) {
        clientNum = Server.updateContPlayer();
        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.addObserver(this);
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
        output.writeObject(Protocol.NOTIFY_MESSAGE);
        output.writeObject(message);
    }

    public int askInt(Function<Integer, Integer> checkInt) throws IOException {
        int num;
        do {
            try {
                output.writeObject(Protocol.ASK_INT);
                num = checkInt.apply((int) input.readObject());
                break;
            } catch (IllegalArgumentException e) {
                notifyMessage(e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (true);
        return num;
    }

    public String askString(Function<String, String> checkString) throws IOException {
        String string;
        do {
            try {
                output.writeObject(Protocol.ASK_STRING);
                string = checkString.apply((String) input.readObject());
                break;
            } catch (IllegalArgumentException e) {
                notifyMessage(e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (true);
        return string;
    }

    public void displayBoard() throws IOException {
        output.writeObject(Protocol.DISPLAY_BOARD);
        for(byte b : controller.getEncodedBoard()){
            output.writeObject(b);
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

