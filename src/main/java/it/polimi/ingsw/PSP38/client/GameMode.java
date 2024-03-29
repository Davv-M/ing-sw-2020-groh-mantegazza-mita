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

    /**
     * This method is used to save the string coming from the client
     * @param dataRead is the string read by the client
     */
    void setStringRead(String dataRead);

    /**
     * This method is used to comunicate the number of players
     * @param numOfPlayers the number of players
     */

    void setNumOfPlayers(int numOfPlayers);

    void setPlayersDivinities(Map<String, String> playersDivinities);

    /**
     * This method is used to set <code>nickname</code>
     *
     * @param nickname nickname
     */
    void setNickname(String nickname);
}
