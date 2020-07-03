package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.Message;

import java.util.Map;

/**
 * Interface used to define methods differently used depending on the game mode chosen (CLI or GUI)
 */
public interface GameMode {

    /**
     * This method is used to decode messages coming from the server
     * @param m is the message coming from the server
     */
    void decodeMessage(Message m);

    /**
     * This method is used to save a non - standard string coming from the server
     */
    void updateCustomString();

    /**
     * This method is used to update the next data that will be inputted onto the server
     * @return the inputted string
     */
    String nextInput();

    /**
     * This method is used to display the game board in the client
     */
    void displayBoard();

    void setStringRead(String dataRead);

    void setNumOfPlayers(int numOfPlayers);

    void setPlayersDivinities(Map<String, String> playersDivinities);
}
