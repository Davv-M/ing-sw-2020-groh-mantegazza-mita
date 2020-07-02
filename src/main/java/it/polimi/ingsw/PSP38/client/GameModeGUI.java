package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.client.GUIComponents.SantoriniWindow;
import it.polimi.ingsw.PSP38.common.Message;
import it.polimi.ingsw.PSP38.server.controller.divinityCards.*;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


public class GameModeGUI implements GameMode {
    private int numOfPlayers = 0;
    private volatile boolean isDataReady = false;
    private String dataReadFromClient;
    private static String nickname = "anonymous";
    private static String age;
    private Map<String, String> playersDivinities = new HashMap<>();
    private JFrame frame;
    private String customStringRead;
    private SantoriniWindow santoriniWindow;
    private String availableDivinities;
    private JPanel cardButtons;
    private boolean isMyTurn = false;
    private static String columnSelected;
    private static String rowSelected;
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
        int n = JOptionPane.showOptionDialog(frame,
                "You are the first player to join this game. Please insert the number of players",
                "Choose the number of players",
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
        String newString;
        newString = JOptionPane.showInputDialog(null, customStringRead, "Error:", JOptionPane.QUESTION_MESSAGE);
        setStringRead(newString);
    }

    public void illegalInt() {
        String newStringInt;
        newStringInt = JOptionPane.showInputDialog(null, customStringRead, "Error:", JOptionPane.QUESTION_MESSAGE);
        setStringRead(newStringInt);
    }


    public void waitForNumPlayer() {
        notMyTurn();
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "waiting");
        santoriniWindow.getWaitingPanel().setMessage("Please wait for the first player to select the number of players.");

    }

    public void gameFull() {
        Object[] options = {"OK"};
        int n = JOptionPane.showOptionDialog(frame,
                "The game is currently full, please try later",
                "Game full",
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                null);
        if (n == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }

    public void clientLost() {
        Object[] options = {"OK"};
        int n = JOptionPane.showOptionDialog(frame,
                "Your challenger lost connection, please restart app",
                "Challenger lost",
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                null);
        if (n == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }

    public void cantMove() {
        JOptionPane.showMessageDialog(frame, "You can't move, You Lose!", "End Game", JOptionPane.WARNING_MESSAGE);
    }

    public void serverLost() {
        Object[] options = {"OK"};
        int n = JOptionPane.showOptionDialog(frame,
                "Connection lost with server, please restart app ",
                "Server Lost",
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                null);
        if (n == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
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
        notMyTurn();
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "waiting");
        santoriniWindow.getWaitingPanel().setMessage("Please wait for " + customStringRead + " to choose the divinity cards that will be used in this game");

    }

    public void notYourTurn() {
        notMyTurn();
        santoriniWindow.getDivinityChoicePanel().setMessage("It's " + customStringRead + "'s turn, please wait");
        santoriniWindow.getGamePanel().setMessage("It's " + customStringRead + "'s turn, please wait");


    }

    public void placeYourWorkers() {
        myTurn();
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "game");
        santoriniWindow.getGamePanel().setMessage("Please place all of your workers on the board");

    }


    public void placeAWorker() {
        getSantoriniWindow().getMainFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
        santoriniWindow.getGamePanel().setMessage("Place your worker number " + customStringRead);

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
        notMyTurn();
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "waiting");
        santoriniWindow.getWaitingPanel().setMessage("Hold on, all the players will be ready in a few seconds");

    }


    public void selectWorker() {
        myTurn();
        santoriniWindow.getGamePanel().setMessage("Please select the worker you want to move:");
    }


    public void waitYourTurn() {
        JOptionPane.showMessageDialog(frame, "please wait");

    }

    private void displayAvailableDivinities() {
        availableDivinities = customStringRead;
        if (cardButtons != null) {
            BorderLayout borderLayout = (BorderLayout) santoriniWindow.getDivinityChoicePanel().getMainDivinityPanel().getLayout();
            santoriniWindow.getDivinityChoicePanel().getMainDivinityPanel().remove(borderLayout.getLayoutComponent(BorderLayout.CENTER));
        }
        cardButtons = santoriniWindow.getDivinityChoicePanel().createDivinitiesButtonsPanel();
        santoriniWindow.getDivinityChoicePanel().getMainDivinityPanel().add(cardButtons, BorderLayout.CENTER);
        santoriniWindow.getMainFrame().revalidate();
    }

    private void displayDivinityMessage() {
        myTurn();
        santoriniWindow.getDivinityChoicePanel().setMessage(customStringRead + ", please select a divinity card from this list");
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "cardChoice");
        getSantoriniWindow().getMainFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    private void unableToFinishTurn() {
        System.out.println("You can't finish your turn. You lose.");
    }

    private void workerMove() {
        santoriniWindow.getGamePanel().setMessage("It's time to Move your worker!");
    }

    private void workerBuild() {
        santoriniWindow.getGamePanel().setMessage("It's time to Build!");
    }

    private void workerOptionalAbility() {
        switch (playersDivinities.get(nickname).toUpperCase()) {
           case "ARES":
               santoriniWindow.getGamePanel().setMessage("Select the cell where you want to remove a tower block.");
               break;
            case "ARTEMIS":
                santoriniWindow.getGamePanel().setMessage("Select the cell where you want to move.");
                break;
            case "ATLAS":
            case "DEMETER":
            case "HEPHAESTUS":
            case "HESTIA":
            case "PROMETHEUS":
                santoriniWindow.getGamePanel().setMessage("Select the cell where you want to build.");
                break;
            case "CHARON":
                santoriniWindow.getGamePanel().setMessage("Select the worker you want to push on the other side.");
                break;
            default:
        }
    }


    private void askSpecialAction() {
        Object[] options = {"yes", "no"};
        int n = JOptionPane.showOptionDialog(frame,
                "Do you want to use your special ability?",
                "Special Ability",
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

    private void serverUnreacheable() {
        Object[] options = {"OK"};
        int n = JOptionPane.showOptionDialog(frame,
                "Server currently unreacheable, please try later",
                "Connection error",
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                null);
        if (n == JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }

    public void illegalArgument() {
        JOptionPane.showMessageDialog(frame, customStringRead, "Illegal selection:", JOptionPane.WARNING_MESSAGE);
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
            case ASK_SPECIAL_ACTION:
                askSpecialAction();
                break;
            case ILLEGAL_NICKNAME:
                illegalNickname();
                break;
            case WAIT_FOR_FULL_GAME:
                waitForFullGame();
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
            case WAIT:
                waitYourTurn();
                break;
            case CONNECTED_TO_SERVER:
                //do nothing
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
    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    @Override
    public void setPlayersDivinities(Map<String, String> playersDivinities) {
        this.playersDivinities = playersDivinities;
        santoriniWindow.getGamePanel().paintPlayersDivinities(playersDivinities);
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }


    @Override
    public void displayBoard() {
        santoriniWindow.getGamePanel().getBoardComponent().setEncodedBoard(ServerHandler.readBoard());
    }

    public void setIP(String ipAddress) {
        Client.connectionHandling(ipAddress, Client.getServerSocketPort());
    }

    public void setNickname(String nicknameRead) {
        nickname = nicknameRead;
    }

    public void setAge(String ageRead) {
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


    private void myTurn() {
        santoriniWindow.getWaitingPanel().setVisibleHourglass(false);
        santoriniWindow.getDivinityChoicePanel().setVisibleHourglass(false);
        santoriniWindow.getGamePanel().setVisibleHourglass(false);
        isMyTurn = true;
    }

    private void notMyTurn() {
        santoriniWindow.getWaitingPanel().setVisibleHourglass(true);
        santoriniWindow.getDivinityChoicePanel().setVisibleHourglass(true);
        santoriniWindow.getGamePanel().setVisibleHourglass(true);
        isMyTurn = false;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }


}
