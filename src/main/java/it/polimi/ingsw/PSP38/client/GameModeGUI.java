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
    private String availableDivinities;
    private JPanel cardButtons;
    private static String columnSelected;
    private static String rowSelected;
    private String myDivinity;
    private volatile boolean isCoordinateReady = false;


    public GameModeGUI() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            santoriniWindow = new SantoriniWindow();
            frame = santoriniWindow.createSantoriniWindow(this);
        });
    }

    public JFrame getFrame() {
        return frame;
    }


    public void insertNumPlayer() {
        Object[] options = {"2", "3"};
        int n = JOptionPane.showOptionDialog(santoriniWindow.getStartPanel().getStartPanel(),
                "You are the first player to join this game. Please insert the number of players",
                "Choose number of players",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                null);
        if (n == JOptionPane.YES_OPTION) {
            setStringRead("2");

        } else if (n == JOptionPane.NO_OPTION) {
            setStringRead("3");
        }
    }

    public void illegalString() {
        System.out.println("String not recognized exception: " + customStringRead);
        String newString;
        newString = JOptionPane.showInputDialog(null, customStringRead, "String not recognized exception: ", JOptionPane.QUESTION_MESSAGE);
        setStringRead(newString);
    }

    public void illegalInt() {
        System.out.println("Integer not recognized exception: " + customStringRead);
        String newStringInt;
        newStringInt = JOptionPane.showInputDialog(null, customStringRead, "Integer not recognized exception: ", JOptionPane.QUESTION_MESSAGE);
        setStringRead(newStringInt);
    }


    public void waitForNumPlayer() {
        System.out.println("Please wait for the first player to select the number of players.");
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "waitForNumPlayers");
    }

    public void gameFull() {
        JOptionPane.showMessageDialog(frame, "The game is currently full, please try later.", "Game full ", JOptionPane.WARNING_MESSAGE);
    }

    public void clientLost() {
        JOptionPane.showMessageDialog(frame, "your challenger lost connection, please restart app", "Challenger lost", JOptionPane.WARNING_MESSAGE);
    }

    public void cantMove() {
        JOptionPane.showMessageDialog(frame, "you can't move, You Lose!", "End Game", JOptionPane.WARNING_MESSAGE);
    }

    public void serverLost() {
        JOptionPane.showMessageDialog(frame, "connection lost with server, please restart app ", "Server Lost", JOptionPane.WARNING_MESSAGE);
    }


    public void chooseNickname() { setStringRead(nickname);  }

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
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "waitForDivinities");
    }

    public void notYourTurn() {
        santoriniWindow.getDivinityChoicePanel().setMessage("It's " + customStringRead + "'s turn, please wait");
        santoriniWindow.getGamePanel().setMessage("It's " + customStringRead + "'s turn, please wait");
        santoriniWindow.getGamePanel().setUnmodifiablePanel();
        santoriniWindow.getDivinityChoicePanel().setUnmodifiablePanel();
    }

    public void placeYourWorkers() {
        santoriniWindow.getGamePanel().setModifiablePanel();
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "game");
        santoriniWindow.getGamePanel().setMessage("Please place all of your workers on the board");
    }


    public void placeAWorker() {
        santoriniWindow.getGamePanel().setMessage("Place your worker number " + customStringRead);
        santoriniWindow.getGamePanel().getCellLabel().setText("insert cell's coordinates:");
        santoriniWindow.getGamePanel().getSubmit().setText("Select");
        santoriniWindow.getMainFrame().repaint();
    }

    public void setCellColumnCoordinate() {
        while (!isCoordinateReady) {
            Thread.onSpinWait();
        }
        setStringRead(columnSelected);

    }

    public void setCellRowCoordinate() {
        while (!isCoordinateReady) {
            Thread.onSpinWait();
        }
        setStringRead(rowSelected);
        isCoordinateReady = false;
    }

    public void youWin() {
        JOptionPane.showMessageDialog(frame, "You are the winner!", "You Win", JOptionPane.INFORMATION_MESSAGE);
    }

    public void youLose() {
        JOptionPane.showMessageDialog(frame, "You lose! The winner is " + customStringRead, "You Lose", JOptionPane.INFORMATION_MESSAGE);

    }


    public void waitForFullGame() {
        System.out.println("Hold on, all the players will be ready in a few seconds");
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "waitForPlayers");
    }


    public void selectWorker() {
        santoriniWindow.getGamePanel().setModifiablePanel();
        santoriniWindow.getGamePanel().setMessage("Please select the worker you want to move:");
        santoriniWindow.getGamePanel().getCellLabel().setText("Please select cell that contains worker you want to move");
        santoriniWindow.getGamePanel().getSubmit().setText("Select");
        santoriniWindow.getMainFrame().repaint();
    }


    public void waitYourTurn() {
        System.out.println("please wait");
        JOptionPane.showMessageDialog(frame, "please wait");

    }

    private void workerNotYours() {
        System.out.println("This worker doesn't belong to you");
        santoriniWindow.getGamePanel().setMessage("This worker doesn't belong to you");
    }

    private void displayAvailableDivinities() {
        availableDivinities=customStringRead;
        if(cardButtons!=null){
            BorderLayout borderLayout = (BorderLayout)santoriniWindow.getDivinityChoicePanel().getMainDivinityPanel().getLayout();
            santoriniWindow.getDivinityChoicePanel().getMainDivinityPanel().remove(borderLayout.getLayoutComponent(BorderLayout.CENTER));
        }
        cardButtons = santoriniWindow.getDivinityChoicePanel().createDivinitiesButtonsPanel();
        santoriniWindow.getDivinityChoicePanel().getMainDivinityPanel().add(cardButtons, BorderLayout.CENTER);
        santoriniWindow.getMainFrame().revalidate();
    }

    private void displayDivinityMessage() {
        /*divinitiesScanner = new Scanner(customStringRead);
        santoriniWindow.getCardHolder().add(santoriniWindow.getDivinityChoicePanel().createMainDivinityPanel(this), "cardChoice");*/
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "cardChoice");
        getSantoriniWindow().getMainFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
        santoriniWindow.getDivinityChoicePanel().setModifiablePanel();
    }

    private void unableToFinishTurn() {
        System.out.println("You can't finish your turn. You lose.");
    }

    private void workerMove() {
        santoriniWindow.getGamePanel().setMessage("It's time to Move your worker!");
        santoriniWindow.getGamePanel().getCellLabel().setText("Select the cell where you want to move");
        santoriniWindow.getGamePanel().getSubmit().setText("Move");
        santoriniWindow.getMainFrame().repaint();
    }

    private void workerBuild() {
        santoriniWindow.getGamePanel().setMessage("It's time to Build!");
        santoriniWindow.getGamePanel().getCellLabel().setText("Select the cell where you want to build");
        santoriniWindow.getGamePanel().getSubmit().setText("Build");
        santoriniWindow.getMainFrame().repaint();
    }

    private void workerOptionalAbility() {
        System.out.println("Select the cell where you want to DAFARE");
    }


    private void askSpecialAction() {
        Object[] options = {"yes", "no"};
        int n = JOptionPane.showOptionDialog(santoriniWindow.getMainFrame(),
                "Do you want to use your special ability",
                null,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                null);
        if (n == JOptionPane.YES_OPTION) {
            setStringRead("yes");

        } else if (n == JOptionPane.NO_OPTION) {
            setStringRead("no");
        }
    }

    private void serverUnreacheable(){
        JOptionPane.showMessageDialog(frame, "Server currently unreacheable, please try later", "Connection error", JOptionPane.ERROR_MESSAGE);
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
            case SET_CELL_COLUMN_COORD:
                setCellColumnCoordinate();
                break;
            case SET_CELL_ROW_COORD:
                setCellRowCoordinate();
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
            case CLIENT_LOST:
                clientLost();
                break;
            case CANT_MOVE:
                cantMove();
                break;
            case SERVER_LOST:
                serverLost();
                break;
            case SERVER_UNREACHEABLE:
                serverUnreacheable();
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
        santoriniWindow.getGamePanel().getBoardComponent().setEncodedBoard(ServerHandler.readBoard());
    }

    public static void setIP(String ipAddress) {
        Client.connectionHandling(ipAddress, Client.getServerSocketPort());
    }

    public static void setNickname(String nicknameRead) {
        nickname = nicknameRead;
    }

    public static void setAge(String ageRead) {
        age = ageRead;
    }

    public SantoriniWindow getSantoriniWindow() {
        return santoriniWindow;
    }

    public void setCoordinateReady(boolean coordinateReady) {
        isCoordinateReady = coordinateReady;
    }

    public String getAvailableDivinities() {
        return availableDivinities;
    }

    public static void setColumnSelected(String columnSelectedRead) {
        columnSelected = columnSelectedRead;
    }

    public static void setRowSelected(String rowSelectedRead) {
        rowSelected = rowSelectedRead;
    }



    //in teoria non dovrebbero mai essere chiamati con GUI

    public void illegalYesOrNo() {
        System.out.println("Please answer with either \"yes\" or \"no\"");
    }

    public void illegalDivinity() {
        System.out.println("Illegal divinity card");
    }

    public void divinityCardNotExists() {
        System.out.println("This divinity card doesn't exist. Please select a new one");

    }

    public void divinityCardChosen() {
        System.out.println("This divinity card has already been chosen. Please select a new one");
    }

    private void illegalAction() {
        System.out.println("Unknown worker action");
    }

    public void illegalArgument() {
        System.out.println("Illegal argument exception: " + customStringRead);
    }

    public void setMyDivinity(String divinity) {
        myDivinity = divinity;
        santoriniWindow.getGamePanel().paintDivinityChosen();
    }

    public String getMyDivinity() {
        return myDivinity;
    }
}
