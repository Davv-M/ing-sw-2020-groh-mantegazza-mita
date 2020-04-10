package it.polimi.ingsw.PSP38.model;

import java.util.List;

/**
 * This class contains the methods needed to manage the execution of a game
 *
 * @author Davide Mantegazza (10568661)
 */

public class Game {
    private List<Player> players;
    private GameStatus status;
    private Board currentBoard;
    private int round;
    private Player currentlyPlaying;
    private int currentNumOfPlayers;
    private int currentIndex = 0;
    private boolean isGameFull = false;

    /**
     * constructor of the Game class.
     *
     * @param listOfPlayers represents the list of players that gaming
     * @throws NullPointerException is thrown if no player is submitted
     */
    public Game(List<Player> listOfPlayers) throws NullPointerException {
        currentNumOfPlayers = listOfPlayers.size();
        status = GameStatus.WAITING;
        currentBoard = new Board();
        players = listOfPlayers;

    }

    /**
     * @return the currentBoard of the game
     */
    public Board getCurrentBoard() {
        return currentBoard;
    }

    /**
     * @param currentBoard update the currentBoard of the game
     */

    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }


    /**
     * Getter method for parameter {@code round}
     *
     * @return the current round
     */
    public int getRound() {
        return round;
    }

    /**
     * @return the current game status
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * This method is used to add new players to the game, if there are still places available
     * @param p represents the player that's going to be added to the game
     * @throws NullPointerException is thrown if {@code p} is null
     */

    /*public void addPlayer(Player p) throws NullPointerException{
        if(currentNumOfPlayers<numOfPlayers){
            if (p.getDateOfBirth().compareTo(players.get(0).getDateOfBirth())<0)
                players.add(0, p);
            else
                players.add(p);
            currentNumOfPlayers++;
        }
        else{
            gameFull=true;
        }
    }*/
    /*public void addPlayer(Player p) throws NullPointerException{
        if(currentNumOfPlayers<numOfPlayers){
            players.add(p);
            currentNumOfPlayers++;
        }
        else{
            gameFull=true;
        }
    }*/

    /**
     * @return the following player located in the player list
     */
    public Player pickNextPlayer() {
        if (currentIndex == (players.size() - 1)) {
            currentlyPlaying = players.get(0);
        } else {
            currentIndex = players.indexOf(getCurrentlyPlaying());
            currentlyPlaying = players.get(currentIndex);
        }
        return currentlyPlaying;
    }

    /**
     * @return the player that is playing in this moment
     */
    public Player getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    /**
     * @return true if in the game it has been reached the maximum number of players
     */
    public boolean isGameFull() {
        return isGameFull;
    }

    /**
     * @return the current num of players
     */
    public int getCurrentNumOfPlayers() {
        return currentNumOfPlayers;
    }

    /**
     * @return the players's list
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * the possible status of the game
     */
    private enum GameStatus {WAITING, READY, INPROGRESS, TERMINATED}
}