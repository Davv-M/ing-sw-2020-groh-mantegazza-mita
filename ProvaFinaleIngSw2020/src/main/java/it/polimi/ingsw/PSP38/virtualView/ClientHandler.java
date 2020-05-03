package it.polimi.ingsw.PSP38.virtualView;

import it.polimi.ingsw.PSP38.controller.Controller;
import it.polimi.ingsw.PSP38.controller.StrategyDivinityCard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

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
                System.out.println("Continue..");
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void notifyMessage(String message)throws IOException{
        output.writeObject(Protocol.NOTIFY_MESSAGE);
        output.writeObject(message);

    }

    private void notifyExtraClient() throws IOException{
        if(this.clientNum>controller.getNumOfPlayer()){
            notifyMessage("game full, please try later");
            controller.waitMe();
        }

    }

    private void firstPlayerSetNumOfPlayers() throws IOException {
        if(controller.checkImFirst()){
            controller.setNumOfPlayer(askNumPlayer());
        }else{
            if(controller.getNumOfPlayer() == 0){
                notifyMessage("Please waiting your challenger chooses players' number");
                controller.waitMe();
            }
        }
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
                notifyMessage("The nickname already exists");
                output.writeObject(Protocol.ASK_NICKNAME);
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
        int divinityChosen = -1;
        if (controller.youngestPlayer().equals(nickname)){
            while(divinityChosen < controller.getNumOfPlayer()-1){
                divinityChosen++;
                while(true){
                    try {
                        output.writeObject(Protocol.ASK_DIVINITY_CARD);
                        output.writeObject(controller.getNumOfPlayer()-divinityChosen);
                        List<String> availableDivinityCards = new LinkedList<>();
                        for(StrategyDivinityCard.Name dcn: controller.getAvailableDivinityCards() ) {
                            availableDivinityCards.add(dcn.toString());
                        }
                        output.writeObject(availableDivinityCards);
                        String selectedCard = (String)input.readObject();
                        if(controller.isSelectedCardCorrect(selectedCard)){
                            controller.setSelectedCard(selectedCard);
                            break;
                        }else{
                            notifyMessage("please select one of the available's cards(<DIVINITY CARD>)" );
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }




}
