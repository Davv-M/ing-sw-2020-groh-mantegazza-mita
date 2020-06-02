package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.Message;
import it.polimi.ingsw.PSP38.common.Protocol;
import it.polimi.ingsw.PSP38.common.utilities.Observer;

import java.util.Observable;

/**
 * Executable class for the client side of Santorini
 * this class is responsible for showing to users all new messages from the server, the last board updated
 * and notify the class Client for all user inputs
 */
public class Client extends Observable implements Observer {
    private static String dataInput;
    private static ServerHandler nextInputObserver;
    private static GameMode gameMode;
    private static String customString;


    public static void main(String[] args){
        if (args.length==0) {
            gameMode = new GameModeGUI();
        } else if (args[0].equalsIgnoreCase("cli")) {
            gameMode = new GameModeCLI();
        } else {
            System.out.println("Parameter not recognized");
            System.exit(0);
        }

        while (true) {
            dataInput = gameMode.nextInput();
            notifyReadSomething();
        }
    }


    /**
     * When Client class receives a new protocol from the server this methods call </@printMessage()> or </displayBoard> to show the new update
     */
    @Override
    public void update() {
        Protocol protocolRead = ServerHandler.getProtocol();
        if (protocolRead == Protocol.NOTIFY_MESSAGE) {
            gameMode.decodeMessage(ServerHandler.getMessage());
        }
        if (protocolRead == Protocol.DISPLAY_BOARD) {
            gameMode.displayBoard();
        }
        if (protocolRead == Protocol.NOTIFY_CUSTOM_STRING) {
            customString = ServerHandler.getCustomMessageString();
            gameMode.updateCustomString();
        }
    }

    /**
     * @return the last data read
     */
    public static String getStringInput() {
        return dataInput;
    }

    /**
     * notify observers that is available a new data input from the client
     */
    private static void notifyReadSomething() {
        Protocol protocolRead = ServerHandler.getProtocol();
        if (protocolRead == Protocol.ASK_INT || protocolRead == Protocol.ASK_STRING) {
            nextInputObserver.update();
        } else {
            gameMode.decodeMessage(Message.WAIT);
        }

    }

    /*public static JFrame createUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cc=new ConnectionComponent();
        frame.add(cc);
        frame.getContentPane().setPreferredSize(cc.getPreferredSize());
        frame.pack();
        frame.setVisible(true);
        cc.requestFocusInWindow();
        return frame;
    }*/

    public static String getCustomString() {
        return customString;
    }

    public static void setObserver(ServerHandler sh){
        nextInputObserver = sh;
    }


}