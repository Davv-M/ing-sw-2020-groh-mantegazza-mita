package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.Message;

public class GameModeGUI implements GameMode {
    String stringRead;
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
}
