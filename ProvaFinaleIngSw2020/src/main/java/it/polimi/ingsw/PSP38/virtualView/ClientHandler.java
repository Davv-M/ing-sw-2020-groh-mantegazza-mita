package it.polimi.ingsw.PSP38.virtualView;

import it.polimi.ingsw.PSP38.controller.Controller;
import it.polimi.ingsw.PSP38.controller.StrategyDivinityCard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private String nickname;
    private int age;
    private static Controller controller = new Controller();
    private static Object lock = new Object();
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


    public void run() {
        try {
            if (contPlayer >= Server.getNumOfPlayer() && Server.getNumOfPlayer() != 0 ){
                notifyGameFull();
            } else {
                synchronized (lock) {
                    contPlayer++;
                    if (Server.getNumOfPlayer() == 0) {
                        Server.setNumOfPlayer(askNumPlayer());
                    }
                }
                nickname = askNickname();
                age = askAge();
                controller.addPlayer(nickname, age);
                checkGameFull();
                System.out.println(controller.players);
                askYoungestPlayerCards();


            }

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void notifyGameFull() throws IOException{
        output.writeObject(Protocol.NOTIFY_GAME_FULL);
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

    public synchronized void checkGameFull(){
        while(Server.getNumOfPlayer() > controller.numCurrentPlayers()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(Server.getNumOfPlayer() <= controller.numCurrentPlayers()) { notifyAll(); }
    }

    public void askYoungestPlayerCards(){
        if (controller.youngestPlayer().equals(nickname)){
            String divinityName;
            for(int i=1; i<=Server.getNumOfPlayer(); i++ ){
                try {
                    output.writeObject(Protocol.ASK_DIVINITY_CARD);
                    divinityName = (String)input.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
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
