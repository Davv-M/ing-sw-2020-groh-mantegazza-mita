package it.polimi.ingsw.PSP38.server.virtualView;

import it.polimi.ingsw.PSP38.common.Protocol;
import it.polimi.ingsw.PSP38.server.controller.Controller;
import it.polimi.ingsw.PSP38.server.model.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.function.Function;

public class ClientHandler implements Runnable {
    private String nickname;
    private final int clientNum;
    private static final Controller controller = new Controller();
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean imInWait = false;

    public ClientHandler(Socket clientSocket) {
        clientNum = Server.updateContPlayer();
        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImInWait(boolean imInWait) {
        this.imInWait = imInWait;
    }

    public boolean getImInWait(){ return imInWait;}

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
                controller.checkGameFull(this);
                askYoungestPlayerCards();
                askDivinity();
                controller.createGame();
                placeWorkers();
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
            controller.pauseClient(this);
        }
    }

    private void notifyNotYourTurn() throws IOException {
        while (!controller.getCurrentPlayerTurn().equals(nickname)) {
            notifyMessage("It's " + controller.getCurrentPlayerTurn() + "'s turn, please wait.");
            controller.pauseClient(this);
        }
    }

    private void firstPlayerSetNumOfPlayers() throws IOException {
        if (clientNum == 1) {
            controller.setNumOfPlayers(askNumPlayers());
        } else if (controller.getNumOfPlayers() == 0) {
            notifyMessage("Please wait for the first player to select the number of players");
            controller.pauseClient(this);
        }
    }

    private int askNumPlayers() throws IOException {
        notifyMessage("You are the first player to join this game. Please insert the number of players (between 2 and 3)");
        return askInt(controller::checkNumOfPlayers);
    }

    private String askNickname() throws IOException {
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

    private int askAge() throws IOException {
        notifyMessage("How old are you? (integer between 8 and 99)");
        return askInt(controller::checkAge);
    }


    private void askYoungestPlayerCards() throws IOException {
        if (controller.youngestPlayer().equals(nickname)) {
            for (int i = 0; i < controller.getNumOfPlayers(); ++i) {
                askDivinity(controller::setSelectedCards);
            }
            controller.updateTurn();
        } else {
            notifyMessage("Please wait for " + controller.youngestPlayer() +
                    " to choose the divinity cards that will be used in this game.");
            controller.pauseClient(this);
        }
    }

    private void askDivinity() throws IOException {
        notifyNotYourTurn();
        askDivinity(controller::setPlayerDivinity);
        controller.updateTurn();
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

    private void askDivinity(Consumer<String> setCollection) throws IOException {
        StringBuilder message = new StringBuilder(nickname + ", please select a divinity card from this list :\n");
        controller.getAvailableDivinityCards().forEach(card -> message.append(card).append("\n"));
        notifyMessage(message.toString());

        String string;
        do {
            try {
                output.writeObject(Protocol.ASK_STRING);
                string = (String) input.readObject();
                if (controller.isSelectedCardCorrect(string)) {
                    setCollection.accept(string);
                    break;
                } else {
                    notifyMessage("This divinity isn't available or has already been chosen. Please select a new one");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    private void displayBoard() throws IOException {
        output.writeObject(Protocol.DISPLAY_BOARD);
        output.writeObject(controller.getEncodedBoard());
    }

    private void placeWorkers() throws  IOException {
        notifyNotYourTurn();
        displayBoard();
        notifyMessage("It's time to place your workers on the board.");
        for(int i = 0; i < Game.WORKERS_PER_PLAYER; ++i){
            int x;
            int y;
            do {
                try {
                    notifyMessage("Please insert the x coordinate for your Worker number " + (i+1) + ":");
                    x = askInt(controller::checkXCoordinate);
                    notifyMessage("Please insert the y coordinate for your Worker number " + (i+1) + ":");
                    y = askInt(controller::checkYCoordinate);
                    controller.placeWorker(x, y, nickname);
                    break;
                } catch (IllegalArgumentException e) {
                    notifyMessage(e.getMessage());
                }
            }while(true);
            displayBoard();
        }
        controller.updateTurn();
    }

}

