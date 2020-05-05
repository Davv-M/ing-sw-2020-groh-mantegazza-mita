package it.polimi.ingsw.PSP38.virtualView;

import it.polimi.ingsw.PSP38.controller.Controller;
import it.polimi.ingsw.PSP38.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Function;

public class ClientHandler implements Runnable {
    private String nickname;
    private final int clientNum;
    private static final Controller controller = new Controller();
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ClientHandler(Socket clientSocket) {
        clientNum = Server.updateContPlayer();
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
            welcomeMessage();
            firstPlayerSetNumOfPlayers();
            notifyExtraClient();
            if (clientNum <= controller.getNumOfPlayers()) {
                nickname = askNickname();
                int age = askAge();
                controller.addPlayer(nickname, age);
                controller.checkGameFull(clientNum);
                askYoungestPlayerCards();
                controller.createGame();
                askDivinity();
                controller.print();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void welcomeMessage() throws IOException {
        notifyMessage("Welcome to SANTORINI");
    }

    private void notifyMessage(String message) throws IOException {
        output.writeObject(Protocol.NOTIFY_MESSAGE);
        output.writeObject(message);
    }

    private void notifyExtraClient() throws IOException {
        if (this.clientNum > controller.getNumOfPlayers()) {
            notifyMessage("game full, please try later");
            controller.pauseClient();
        }
    }

    private void firstPlayerSetNumOfPlayers() throws IOException {
        if (clientNum == 1) {
            controller.setNumOfPlayers(askNumPlayers());
        } else if (controller.getNumOfPlayers() == 0) {
            notifyMessage("Please wait for the first player to select the number of players");
            controller.pauseClient();
        }
    }

    public int askNumPlayers() throws IOException {
        notifyMessage("You are the first player to join this game. Please insert the number of players (between 2 and 3)");
        return askInt(controller::checkNumOfPlayers);
    }

    public String askNickname() throws IOException {
        notifyMessage("Choose your nickname");
        output.writeObject(Protocol.ASK_STRING);
        try {
            nickname = (String) input.readObject();
            while (!controller.isNicknameAvailable(nickname)) {
                notifyMessage("The nickname already exists. Please choose another one");
                output.writeObject(Protocol.ASK_STRING);
                nickname = (String) input.readObject();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        controller.addIllegalNickname(nickname);
        return nickname;
    }

    public int askAge() throws IOException {
        notifyMessage("How old are you? (integer between 8 and 99)");
        return askInt(controller::checkAge);
    }


    public void askYoungestPlayerCards() throws IOException {
        if (controller.youngestPlayer().equals(nickname)) {
            StringBuilder message = new StringBuilder(nickname + ", please select a divinity card from this list :\n");
            controller.getAvailableDivinityCards().forEach(card -> message.append(card).append("\n"));
            notifyMessage(message.toString());

            for (int i = 0; i < controller.getNumOfPlayers(); ++i) {
                notifyMessage("please select a divinity card. (selected " + i + "/" + controller.getNumOfPlayers() + ")");
                do {
                    try {
                        output.writeObject(Protocol.ASK_STRING);
                        String selectedCard = (String) input.readObject();
                        if (controller.isSelectedCardCorrectFromAvailableCards(selectedCard)) {
                            controller.setSelectedCard(selectedCard);
                            break;
                        } else {
                            notifyMessage("This divinity isn't available or has already been chosen. Please select a new one");
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } while (true);
            }
            controller.updatePlayers();
            controller.wakeUpClients();
        } else {
            controller.pauseClient();
        }
    }

    private int askInt(Function<Integer, Integer> checkBounds) throws IOException {
        int num;
        do {
            try {
                output.writeObject(Protocol.ASK_INT);
                num = checkBounds.apply((int) input.readObject());
                break;
            } catch (IllegalArgumentException e) {
                notifyMessage(e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (true);
        return num;
    }

    public void askDivinity() throws IOException {
        while (!controller.getCurrentPlayerTurn().getNickname().equals(nickname)) {
            controller.pauseClient();
        }
        System.out.println("client num = " + nickname + " è uscito dal while");

        StringBuilder message = new StringBuilder(nickname + ", please select a divinity card from this list :\n");
        controller.getSelectedCards().forEach(card -> message.append(card).append("\n"));
        notifyMessage(message.toString());

        do {
            try {
                output.writeObject(Protocol.ASK_STRING);
                String selectedCard = (String) input.readObject();
                if (controller.isSelectedCardCorrectFromSelectedCards(selectedCard)) {
                    controller.setPlayerDivinity(controller.getCurrentPlayerTurn(), selectedCard);
                    break;
                } else {
                    notifyMessage("This divinity isn't available or has already been chosen. Please select a new one");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (true);
        controller.updatePlayers();
        controller.wakeUpClients();
    }

}

