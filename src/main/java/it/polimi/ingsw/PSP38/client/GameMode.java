package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.Message;

import java.net.Socket;

public interface GameMode {
    //int SERVER_SOCKET_PORT = 3456;


    void decodeMessage(Message m);
    void updateCustomString();
    String nextInput();
    void displayBoard();
    //void connectionHandling();
}
