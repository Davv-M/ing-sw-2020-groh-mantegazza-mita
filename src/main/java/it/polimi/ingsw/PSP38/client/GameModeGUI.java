package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.client.GUIComponents.SantoriniWindow;
import it.polimi.ingsw.PSP38.common.Message;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class GameModeGUI implements GameMode {
    private int numOfPlayers = 0;
    private static String nickname = "anonymous";
    private static String age;
    private Map<String, String> playersDivinities = new HashMap<>();
    private JFrame frame;
    private String customStringRead;
    private SantoriniWindow santoriniWindow;
    private volatile boolean isDataReady = false;
    private volatile boolean isCoordinateReady = false;
    private String availableDivinities;
    private String dataReadFromClient;
    private static String columnSelected;
    private static String rowSelected;
    private JPanel cardButtons;
    private boolean isMyTurn = false;

    /**
     * This method is used to invoke the GUI and create the main frame
     *
     */
    public GameModeGUI() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            santoriniWindow = new SantoriniWindow();
            frame = santoriniWindow.createSantoriniWindow(this);
        });
    }

    /**
     * Getter method that return the main frame
     *
     * @return main Frame <code>frame</code>
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * This method is used to decode messages coming from the server
     *
     * @param m is the message coming from the server
     */
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
            case ILLEGAL_STRING:
                illegalString();
                break;
            case UNABLE_TO_FINISH_TURN:
                unableToFinishTurn();
                break;
            case ILLEGAL_ARGUMENT:
                illegalArgument();
                break;
            case SET_CELL_COLUMN_COORD:
                setCellColumnCoordinate();
                break;
            case SET_CELL_ROW_COORD:
                setCellRowCoordinate();
                break;
            case WORKER_MOVE:
                workerMove();
                break;
            case WORKER_BUILD:
                workerBuild();
                break;
            case WORKER_NOT_YOURS:
                workerNotYours();
                break;
            case WORKER_OPTIONAL_ABILITY:
                workerOptionalAbility();
                break;
            case WAIT:
                waitYourTurn();
                break;
            case CLIENT_LOST:
                unreachable("Your challenger lost connection, please restart app");
                break;
            case CANT_MOVE:
                cantMove();
                break;
            case SERVER_LOST:
                unreachable("Connection lost with server, please restart app");
                break;
            case SERVER_UNREACHEABLE:
                unreachable("Server currently unreacheable, please try later");
                break;
            default:
                break;
        }
    }

    private void insertNumPlayer() {
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
        if (n == JOptionPane.CLOSED_OPTION) {
            insertNumPlayer();
        }

    }

    private void illegalString() {
        String[] options = {"OK"};
        JPanel panel = new JPanel();
        JLabel label = new JLabel(customStringRead);
        JTextField text = new JTextField(10);
        panel.add(label);
        panel.add(text);
        int selectedOption = JOptionPane.showOptionDialog(frame, panel, "Error:", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selectedOption == JOptionPane.YES_NO_OPTION) {
            setStringRead(text.getText());
        }
        if (selectedOption == JOptionPane.CLOSED_OPTION) {
            illegalString();
        }
    }

    private void workerNotYours() {
        JOptionPane.showMessageDialog(frame, "This worker doesn't belong to you", "Worker:", JOptionPane.WARNING_MESSAGE);
    }


    private void waitForNumPlayer() {
        notMyTurn();
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "waiting");
        santoriniWindow.getWaitingPanel().setMessage("Please wait for the first player to select the number of players.");
        santoriniWindow.getMainFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void gameFull() {
        Object[] options = {"OK"};
        int n = JOptionPane.showOptionDialog(frame,
                "The game is currently full, please try later",
                "Game full",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                null);
        if (n == JOptionPane.YES_NO_OPTION || n == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

    }

    private void cantMove() {
        JOptionPane.showMessageDialog(frame, "You can't move, You Lose!", "End Game", JOptionPane.WARNING_MESSAGE);
    }


    private void chooseNickname() {
        setStringRead(nickname);
    }

    private void illegalNickname() {
        String[] options = {"OK"};
        JPanel panel = new JPanel();
        JLabel label = new JLabel("This nickname is unavailable, please choose another one");
        JTextField text = new JTextField(10);
        panel.add(label);
        panel.add(text);
        int selectedOption = JOptionPane.showOptionDialog(null, panel, "Error:", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selectedOption == JOptionPane.YES_NO_OPTION) {
            setStringRead(text.getText());
        }
        if (selectedOption == JOptionPane.CLOSED_OPTION) {
            illegalNickname();
        }
    }

    private void setAge() {
        setStringRead(age);
    }

    private void waitForDivinities() {
        notMyTurn();
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "waiting");
        santoriniWindow.getWaitingPanel().setMessage("Please wait for " + customStringRead + " to choose the divinity cards that will be used in this game");
        santoriniWindow.getMainFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void unreachable(String message) {
        Object[] options = {"OK"};
        int n = JOptionPane.showOptionDialog(frame,
                message,
                "error",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                null);
        if (n == JOptionPane.YES_NO_OPTION || n == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
    }

    private void notYourTurn() {
        notMyTurn();
        santoriniWindow.getDivinityChoicePanel().setMessage("It's " + customStringRead + "'s turn, please wait");
        santoriniWindow.getGamePanel().setMessage("It's " + customStringRead + "'s turn, please wait");


    }

    private void placeYourWorkers() {
        myTurn();
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "game");
        santoriniWindow.getGamePanel().setMessage("Please place all of your workers on the board");

    }


    private void placeAWorker() {
        getSantoriniWindow().getMainFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
        santoriniWindow.getGamePanel().setMessage("Place your worker number " + customStringRead);

    }

    private void youWin() {
        JOptionPane.showMessageDialog(frame, "You are the winner!", "You Win", JOptionPane.INFORMATION_MESSAGE);
    }

    private void youLose() {
        JOptionPane.showMessageDialog(frame, "You lose! The winner is " + customStringRead, "You Lose", JOptionPane.INFORMATION_MESSAGE);

    }


    private void waitForFullGame() {
        notMyTurn();
        CardLayout cl = (CardLayout) (getSantoriniWindow().getCardHolder().getLayout());
        cl.show(getSantoriniWindow().getCardHolder(), "waiting");
        santoriniWindow.getWaitingPanel().setMessage("Hold on, all the players will be ready in a few seconds");
        santoriniWindow.getMainFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
    }


    private void selectWorker() {
        myTurn();
        santoriniWindow.getGamePanel().setMessage("Please select the worker you want to move:");
    }


    private void waitYourTurn() {
        JOptionPane.showMessageDialog(frame, "please wait");

    }

    /**
     * This method is used to set the string read, to the selected column coordinates
     */
    public void setCellColumnCoordinate() {
        while (!isCoordinateReady) {
            Thread.onSpinWait();
        }
        setStringRead(columnSelected);

    }

    /**
     * This method is used to set the string read, to the selected row coordinates
     */
    public void setCellRowCoordinate() {
        while (!isCoordinateReady) {
            Thread.onSpinWait();
        }
        setStringRead(rowSelected);
        isCoordinateReady = false;
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
        JOptionPane.showMessageDialog(frame, "You can't finish your turn. You lose.");
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
        if (n == JOptionPane.CLOSED_OPTION) {
            setStringRead("no");
        }
    }

    private void illegalArgument() {
        JOptionPane.showMessageDialog(frame, customStringRead, "Illegal selection:", JOptionPane.WARNING_MESSAGE);
    }


    /**
     * This method is used to save a non - standard string coming from the server
     */
    @Override
    public void updateCustomString() {
        customStringRead = Client.getCustomString();

    }

    /**
     * This method is used to update the next data that will be inputted onto the server
     */
    @Override
    public String nextInput() {
        while (!isDataReady) {
            Thread.onSpinWait();
        }
        isDataReady = false;
        return dataReadFromClient;

    }

    /**
     * This method is used to update the next data that will be inputted onto the server
     *
     */
    @Override
    public void setStringRead(String dataRead) {
        dataReadFromClient = dataRead;
        isDataReady = true;
    }

    /**
     * This method is used to set num of game's players
     *
     * @param numOfPlayers is the num of players
     */
    @Override
    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    /**
     * This method is used to set <code>playersDivinities</code>
     *
     * @param playersDivinities is the association payers-divinities
     */
    @Override
    public void setPlayersDivinities(Map<String, String> playersDivinities) {
        this.playersDivinities = playersDivinities;
        santoriniWindow.getGamePanel().paintPlayersDivinities(playersDivinities);
    }

    /**
     * This method is used to get num of game's players
     *
     * @return numOfPlayer
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * This method is used to display the game board in the client
     */
    @Override
    public void displayBoard() {
        santoriniWindow.getGamePanel().getBoardComponent().setEncodedBoard(ServerHandler.readBoard());
    }

    /**
     * This method is used to set ip
     *
     * @param ipAddress is the string rapresentation of server ip address
     */
    public void setIP(String ipAddress) {
        Client.connectionHandling(ipAddress, Client.getServerSocketPort());
    }

    /**
     * This method is used to set <code>nickname</code>
     *
     * @param nicknameRead nickname
     */
    @Override
    public void setNickname(String nicknameRead) {
        nickname = nicknameRead;
    }

    /**
     * This method is used to set <code>age</code>
     *
     * @param ageRead age
     */
    public void setAge(String ageRead) {
        age = ageRead;
    }

    /**
     * This method is used to set <code>isCoordinateReady</code> to <code>coordinateReady</code>
     *
     * @param coordinateReady coordinate ready
     */
    public void setCoordinateReady(boolean coordinateReady) {
        isCoordinateReady = coordinateReady;
    }

    /**
     * This method is used to get <code>santoriniWindows</code>
     *
     * @return santoriniWindow
     */
    public SantoriniWindow getSantoriniWindow() {
        return santoriniWindow;
    }

    /**
     * Getter method of available divinities
     *
     * @return <code>availableDivinities</code>
     */
    public String getAvailableDivinities() {
        return availableDivinities;
    }

    /**
     * This method is used to set column selected
     *
     * @param columnSelectedRead is the row selected
     */
    public static void setColumnSelected(String columnSelectedRead) {
        columnSelected = columnSelectedRead;
    }

    /**
     * This method is used to set row selected
     *
     * @param rowSelectedRead is the row selected
     */

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

    /**
     * This method is used to know if it's client's turn
     *
     * @return <code>isMyTurn</code>
     */
    public boolean isMyTurn() {
        return isMyTurn;
    }


}
