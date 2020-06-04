package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.client.GUIComponents.ConnectionComponent;
import it.polimi.ingsw.PSP38.client.GUIComponents.SetNumOfPlayersComponent;
import it.polimi.ingsw.PSP38.common.Message;

import javax.swing.*;


public class GameModeGUI implements GameMode {
    volatile boolean isDataReady = false;
    String dataReadFromClient;
    public JFrame frame;
    private String customStringRead;

    public void welcome() {
        System.out.println("Welcome to Santorini");
    }

    /*public GameModeGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        connectionHandling();
    }*/


    public void insertNumPlayer() {
        SetNumOfPlayersComponent snopc = new SetNumOfPlayersComponent(this);
        frame.add(snopc);
    }

    public void waitForNumPlayer() {
        System.out.println("Please wait for the first player to select the number of players.");
    }

    public void gameFull() {
        System.out.println("The game is currently full, please try later. If you want to see the match stay connected");
    }

    public void chooseNickname() {
        System.out.println("Choose your nickname");
    }

    public void setAge() {
        System.out.println("\"How old are you? (integer between 8 and 99)\"");
    }

    public void waitForDivinities() {
        System.out.println("Please wait for " + customStringRead + " to choose the divinity cards that will be used in this game");
    }

    public void notYourTurn() {
        System.out.println("It's " + customStringRead + "'s turn, please wait");
    }

    public void placeYourWorkers() {
        System.out.println("Please place all of your workers on the board");
    }


    public void placeAWorker() {
        System.out.println("Place your worker number " + customStringRead);
    }

    public void setCellXCoordinate() {
        System.out.println("Please insert the cell's x coordinate");
    }

    public void setCellYCoordinate() {
        System.out.println("Please insert the cell's y coordinate");
    }

    public void youWin() {
        System.out.println("You are the winner!");
    }

    public void youLose() {
        System.out.println("You lose! The winner is " + customStringRead);
    }

    public void illegalString() {
        System.out.println("String not recognized exception: " + customStringRead);
    }

    public void illegalInt() {
        System.out.println("Integer not recognized exception: " + customStringRead);
    }

    public void illegalArgument() {
        System.out.println("Illegal argument exception: " + customStringRead);
    }

    public void illegalNickname() {
        System.out.println("This nickname is unavailable, please choose another one");
    }

    public void waitForFullGame() {
        System.out.println("Hold on, all the players will be ready in a few seconds");
    }

    public void divinityCardNotExists() {
        System.out.println("This divinity card doesn't exist. Please select a new one");
    }

    public void divinityCardChosen() {
        System.out.println("This divinity card has already been chosen. Please select a new one");
    }

    public void illegalYesOrNo() {
        System.out.println("Please answer with either \"yes\" or \"no\"");
    }

    public void illegalDivinity() {
        System.out.println("Illegal divinity card");
    }

    public void selectWorker() {
        System.out.println("Player, please select the worker you want to move");//inserire nickname giocatore
    }


    public void waitYourTurn() {
        System.out.println("please wait");

    }

    private void workerNotYours() {
        System.out.println("Player, this worker doesn't belong to you");
    }

    private void displayAvailableDivinities() {
        System.out.println(customStringRead);
    }

    private void display_divinity_message() {
        System.out.println(customStringRead + ", please select a divinity card from this list :\n");
    }

    private void unableToFinishTurn() {
        System.out.println("You can't finish your turn. You lose.");
    }

    private void workerMove() {
        System.out.println("Select the cell where you want to move");
    }

    private void workerBuild() {
        System.out.println("Select the cell where you want to build");
    }

    private void workerOptionalAbility() {
        System.out.println("Select the cell where you want to DAFARE");
    }

    private void illegalAction() {
        System.out.println("Unknown worker action");
    }

    private void askSpecialAction() {
        System.out.println("Do you want to use your special ability");
    }


    @Override
    public void decodeMessage(Message m) {
        switch (m) {
            case WELCOME:
                welcome();
                break;
            case INSERT_NUM_PLAYERS:
                insertNumPlayer();
                break;
            case WAIT_FOR_NUM_PLAYERS:
                waitForNumPlayer();
                break;
            case GAME_FULL:
                gameFull();
                break;
            case CHOOSE_NICKNAME:
                chooseNickname();
                break;
            case SET_AGE:
                setAge();
                break;
            case WAIT_FOR_DIVINITIES:
                waitForDivinities();
                break;
            case NOT_YOUR_TURN:
                notYourTurn();
                break;
            case PLACE_YOUR_WORKERS:
                placeYourWorkers();
                break;
            case PLACE_A_WORKER:
                placeAWorker();
                break;
            case SET_CELL_X_COORD:
                setCellXCoordinate();
                break;
            case SET_CELL_Y_COORD:
                setCellYCoordinate();
                break;
            case YOU_WIN:
                youWin();
                break;
            case YOU_LOSE:
                youLose();
            case SELECT_WORKER:
                selectWorker();
                break;
            case WORKER_NOT_YOURS:
                workerNotYours();
                break;
            case ASK_SPECIAL_ACTION:
                askSpecialAction();
                break;
            case ILLEGAL_NICKNAME:
                illegalNickname();
                break;
            case WAIT_FOR_FULL_GAME:
                waitForFullGame();
                break;
            case DIVINITY_CARD_NOT_EXISTS:
                divinityCardNotExists();
                break;
            case DIVINITY_CARD_CHOSEN:
                divinityCardChosen();
                break;
            case ILLEGAL_YES_OR_NO:
                illegalYesOrNo();
                break;
            case ILLEGAL_DIVINITY:
                illegalDivinity();
                break;
            case DISPLAY_DIVINITY_MESSAGE:
                display_divinity_message();
                break;
            case DISPLAY_AVAILABLE_DIVINITIES:
                displayAvailableDivinities();
                break;
            case ILLEGAL_INT:
                illegalInt();
                break;
            case ILLEGAL_STRING:
                illegalString();
                break;
            case UNABLE_TO_FINISH_TURN:
                unableToFinishTurn();
                break;
            case ILLEGAL_ARGUMENT:
                illegalArgument();
                break;
            case WORKER_MOVE:
                workerMove();
                break;
            case WORKER_BUILD:
                workerBuild();
                break;
            case WORKER_OPTIONAL_ABILITY:
                workerOptionalAbility();
                break;
            case ILLEGAL_ACTION:
                illegalAction();
                break;
            case WAIT:
                waitYourTurn();
                break;
            default:
                System.out.println("Message not recognized");
                break;
        }
    }


    @Override
    public void updateCustomString() {
        customStringRead = Client.getCustomString();

    }

    @Override
    public String nextInput() {
        while (!isDataReady) {
            Thread.onSpinWait();
        }
        isDataReady = false;
        return dataReadFromClient;
    }

    public void setStringRead(String dataRead) {
        dataReadFromClient = dataRead;
        isDataReady = true;
    }


    @Override
    public void displayBoard() {

    }

    @Override
    public void connectionHandling() {
        ConnectionComponent cc = new ConnectionComponent();
        cc.createConnectionWindow();
        frame.add(cc);
        frame.getContentPane().setPreferredSize(cc.getPreferredSize());
    }


}
