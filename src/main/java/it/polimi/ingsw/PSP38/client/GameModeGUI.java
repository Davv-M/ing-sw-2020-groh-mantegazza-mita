package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.client.GUIComponents.ConnectionComponent;
import it.polimi.ingsw.PSP38.common.Message;

import javax.swing.*;
import java.net.Socket;

public class GameModeGUI implements GameMode {
    String stringRead;
    JFrame frame;
    private static Socket serverSocket;
    private static ServerHandler nextInputObserver;

    public GameModeGUI(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        connectionHandling();
        Client.setObserver(nextInputObserver);
    }


    @Override
    public void decodeMessage(Message m) {
    }

    @Override
    public void updateCustomString() {

    }

    @Override
    public String nextInput() {
        return stringRead;
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

    public static void setServerSocket(Socket ss){
        serverSocket = ss;
    }

    public static void setServerHandler(ServerHandler sh){
        nextInputObserver = sh;
    }




}
