package it.polimi.ingsw.PSP38.client;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientNEW {
    private static String gameMode;
    public static void main(String[] args) {
        if (args.length==0) {
            gameMode="GUI";
        } else if (args[0].equalsIgnoreCase("cli")) {
            gameMode="CLI";
        } else {
            System.out.println("Parameter not recognized");
        }
        ServerHandler serverHandler = new ServerHandler(serverSocket);
        //nextInputObserver = serverHandler;
        Thread thread = new Thread(serverHandler);
        thread.start();
    }
}
