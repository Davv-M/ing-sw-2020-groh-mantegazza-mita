package it.polimi.ingsw.PSP38.virtualView;

import it.polimi.ingsw.PSP38.controller.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private String nickname;
    private int clientNum;
    private static Controller controller = new Controller();
    private static int contPlayer = 0;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientHandler (Socket clientSocket){
        this.clientSocket = clientSocket;
        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            contPlayer++;
            clientNum = contPlayer;
            firstPlayerSetNumOfPlayers();
            notifyExtraClient();
            if(clientNum <= controller.getNumOfPlayer()) {
                nickname = askNickname();
                int age = askAge();
                controller.addPlayer(nickname, age);
                controller.checkGameFull();
                askYoungestPlayerCards();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void notifyExtraClient() throws IOException{
        if(this.clientNum>controller.getNumOfPlayer()){
            notifyGameFull();
            controller.waitMe();
        }

    }

    private void firstPlayerSetNumOfPlayers() throws IOException {
        if(controller.checkImFirst()){
            controller.setNumOfPlayer(askNumPlayer());
        }else{
            if(controller.getNumOfPlayer() == 0){
                notifyWaitingMessage();
                controller.waitMe();
            }
        }
    }

    public void notifyGameFull() throws IOException{
        output.writeObject(Protocol.NOTIFY_GAME_FULL);
    }

    public void notifyWaitingMessage() throws IOException{
        output.writeObject(Protocol.NOTIFY_WAITING_MESSAGE);
    }

    public int askNumPlayer() throws IOException{
        output.writeObject(Protocol.ASK_NUM_PLAYER);
        try {
            return (int)input.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String askNickname() throws IOException{
        output.writeObject(Protocol.ASK_NICKNAME);
        try {
            nickname = (String)input.readObject();
            while(!controller.isNicknameAvailable(nickname)){
                output.writeObject(Protocol.ASK_NICKNAME_AGAIN);
                nickname = (String)input.readObject();;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        controller.addIllegalNickname(nickname);
        return nickname;
    }

    public int askAge() throws IOException{
        output.writeObject(Protocol.ASK_AGE);
        try {
            return (int)input.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public void askYoungestPlayerCards() throws IOException{
        if (controller.youngestPlayer().equals(nickname)){
            String divinityName;
            for(int i=1; i<=controller.getNumOfPlayer(); i++ ){
                    output.writeObject(Protocol.ASK_DIVINITY_CARD);
                try {
                    divinityName = (String)input.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


               /* while (!availableDivinities.contains(divinityCard)) {
                    System.out.println("This divinity isn't available or has already been chosen. Please select a new one");
                    divinityCard = s.nextLine();
                }
                selectedCards.add(divinityCard);
                availableDivinities.remove(divinityCard);
                */















            }



        }
    }




}
