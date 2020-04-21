package it.polimi.ingsw.PSP38.virtualView;

import java.io.*;
import java.net.*;

import static java.lang.System.err;


public class ServerNetworkHandler {

    public ServerNetworkHandler() {
        int port = 4567;
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            System.out.println("[1] - Server ready on port " + port);
        } catch (IOException e) {
            err.println(" Server error :(");
        }
        try {
            Socket s = ss.accept();
            System.out.println("Client accept");

            ObjectInputStream input = new ObjectInputStream(s.getInputStream());
            int numOfPlayer = (int)input.readObject();



        } catch (IOException | ClassNotFoundException e) {
            err.println("Connession failed");
        }

    }

}

