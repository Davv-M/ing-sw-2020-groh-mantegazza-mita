package it.polimi.ingsw.PSP38.client;

//import it.polimi.ingsw.PSP38.client.GUIComponents.ConnectionComponent;
import it.polimi.ingsw.PSP38.client.GUIComponents.SantoriniWindow;
import it.polimi.ingsw.PSP38.common.Message;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;


public class GameModeGUI implements GameMode {
    volatile boolean isDataReady = false;
    private String dataReadFromClient;
    private static String nickname = "anonymous";
    private static String age;
    private JFrame frame;
    private String customStringRead;
    private SantoriniWindow santoriniWindow;


    public GameModeGUI() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            santoriniWindow =new SantoriniWindow();
            frame = santoriniWindow.createSantoriniWindow(this);
        });
    }

    public JFrame getFrame(){return frame;}


    public void insertNumPlayer() {
        Object[] options = {"2", "3"};
                int n = JOptionPane.showOptionDialog(santoriniWindow.getStartPanel().getStartPanel(),
                        "You are the first player to join this game. Please insert the number of players",
                        null,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        null);
                if (n==JOptionPane.YES_OPTION){
                    setStringRead("2");

                }else if(n==JOptionPane.NO_OPTION){
                    setStringRead("3");
                }
    }

    public void illegalString() {
        System.out.println("String not recognized exception: " + customStringRead);
        String newString;
        do{
            newString = JOptionPane.showInputDialog(null, customStringRead,"String not recognized exception: ",JOptionPane.QUESTION_MESSAGE);
        }while (newString.equals(""));
        setStringRead(newString);
    }

    public void illegalInt() {
        System.out.println("Integer not recognized exception: " + customStringRead);
        String newStringInt;
        do{
            newStringInt = JOptionPane.showInputDialog(null,customStringRead,"Integer not recognized exception: ",JOptionPane.QUESTION_MESSAGE);
        }while (newStringInt.equals(""));
        setStringRead(newStringInt);
    }



    public void waitForNumPlayer() {
        System.out.println("Please wait for the first player to select the number of players.");

    }

    public void gameFull() {
        JOptionPane.showMessageDialog(null,"The game is currently full, please try later.","Game full ",JOptionPane.WARNING_MESSAGE);
    }

    public void chooseNickname() {
        setStringRead(nickname);
    }

    public void illegalNickname() {
        String newNickname;
        newNickname = JOptionPane.showInputDialog("This nickname is unavailable, please choose another one:");
        nickname = newNickname;
        setStringRead(newNickname);
    }

    public void setAge() {
        setStringRead(age);
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




    public void illegalArgument() {
        System.out.println("Illegal argument exception: " + customStringRead);
    }



    public void waitForFullGame() {
        System.out.println("Hold on, all the players will be ready in a few seconds");

        CardLayout cl = (CardLayout)(getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "waitForPlayers");
    }

    public void divinityCardNotExists() {
        System.out.println("This divinity card doesn't exist. Please select a new one");
    }

    public void divinityCardChosen() {
        System.out.println("This divinity card has already been chosen. Please select a new one");
    }

    public void illegalYesOrNo() {//non dovrebbe servire
    }

    public void illegalDivinity() {
        System.out.println("Illegal divinity card");
    }

    public void selectWorker() {
        System.out.println("Player, please select the worker you want to move");//inserire nickname giocatore
    }


    public void waitYourTurn() {
        System.out.println("please wait");
        JOptionPane.showMessageDialog(null, "please wait");

    }

    private void workerNotYours() {
        System.out.println("Player, this worker doesn't belong to you");
    }

    private void displayAvailableDivinities() {
        System.out.println(customStringRead);
    }

    private void displayDivinityMessage() {
        CardLayout cl = (CardLayout)(getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "cardChoice");
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
        Object[] options = {"yes", "no"};
        int n = JOptionPane.showOptionDialog(santoriniWindow.getMainSetupFrame(),
                "Do you want to use your special ability",
                null,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                null);
        if (n==JOptionPane.YES_OPTION){
            setStringRead("yes");

        }else if(n==JOptionPane.NO_OPTION){
            setStringRead("no");
        }
    }


    @Override
    public void decodeMessage(Message m) {
        switch (m) {
            case WELCOME:
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
                displayDivinityMessage();
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
            case CONNECTED_TO_SERVER:
                break;
            default:
                System.out.println("Message not recognized\n");
                System.out.println(m);
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

    @Override
    public void setStringRead(String dataRead) {
        dataReadFromClient = dataRead;
        isDataReady = true;
    }


    @Override
    public void displayBoard() {

    }

    public static void setIP(String ipAddress){
        Client.connectionHandling(ipAddress,Client.getServerSocketPort());
    }

    public static void setNickname(String nicknameRead){
        nickname=nicknameRead;
    }

    public static void setAge(String ageRead){
        age=ageRead;
    }

    public SantoriniWindow getSantoriniWindow() {
        return santoriniWindow;
    }
}
