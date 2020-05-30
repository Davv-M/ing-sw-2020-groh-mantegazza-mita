package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.Message;

public interface GameMode {
    void decodeMessage(Message m);
    void updateCustomString();
    String nextInput();
    void displayBoard();
}
