package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.client.GUIComponents.SantoriniWindow;
import it.polimi.ingsw.PSP38.common.Message;
import it.polimi.ingsw.PSP38.common.Protocol;
import it.polimi.ingsw.PSP38.common.utilities.Observer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;

/**
 * Executable class for the client side of Santorini
 * this class is responsible for showing to users all new messages from the server, the last board updated
 * and notify the class Client for all user inputs
 */
public class Client extends Observable implements Observer {
    private final static int SERVER_SOCKET_PORT = 3456;
    private static String dataInput;
    private static ServerHandler nextInputObserver;
    private static GameMode gameMode;
    private static String customString;
    private static Socket serverSocket;
    private static final Scanner ipScanner = new Scanner(System.in);
    private static SantoriniWindow santoriniWindow;

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        if (args.length==0) {
            gameMode = new GameModeGUI();
        } else if (args[0].equalsIgnoreCase("cli")) {
            gameMode = new GameModeCLI();
            System.out.println("Insert server IP address:");
            String ipAddress= ipScanner.nextLine();
            connectionHandling(ipAddress,SERVER_SOCKET_PORT);
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
        if(protocolRead == Protocol.CANT_MOVE || protocolRead == Protocol.CLIENT_LOST || protocolRead == Protocol.TOO_LATE){
            endGame(protocolRead);
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
    private static void notifyReadSomething(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {}
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

    public static void connectionHandling(String address, int port){
        try {
            InetAddress addr = InetAddress.getByName(address);
            serverSocket = new Socket(addr, port);
            ServerHandler serverHandler = new ServerHandler(serverSocket);
            nextInputObserver = serverHandler;
            Thread thread = new Thread(serverHandler);
            thread.start();
        } catch (IOException e) {
            gameMode.decodeMessage(Message.SERVER_UNREACHEABLE);
            return;
        }
        gameMode.decodeMessage(Message.CONNECTED_TO_SERVER);
    }

    public static int getServerSocketPort() {
        return SERVER_SOCKET_PORT;
    }

    public static GameMode getGameMode() {
        return gameMode;
    }

    public void endGame(Protocol protocolExit){
        switch (protocolExit){
            case TOO_LATE:{
                gameMode.decodeMessage(Message.GAME_FULL);
                break;
            }
            case CLIENT_LOST:{
                gameMode.decodeMessage(Message.CLIENT_LOST);
                break;
            }
            case CANT_MOVE:{
                gameMode.decodeMessage(Message.CANT_MOVE);
                break;
            }

        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}