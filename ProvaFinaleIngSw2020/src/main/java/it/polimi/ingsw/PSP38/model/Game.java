package it.polimi.ingsw.PSP38.model;

import java.util.ArrayList;

/**
 * This class contains the methods needed to manage the execution of a game
 * @author Davide Mantegazza (10568661)
 */

public class Game {
    private ArrayList<Player> players;
    private int numOfPlayers;
    private GameStatus status;
    private Board currentBoard;
    private int round;
    private Player currentlyPlaying;
    private int currentNumOfPlayers=0;
    private int currentIndex=0;
    private boolean gameFull=false;
    private enum GameStatus {WAITING, READY, INPROGRESS, TERMINATED}

    /**
     *Class constructor. This method initialises the class with the parameters np and p, sets the game status to "WAITING", and generates a new board
     * @param np represents the number of players
     * @param p represents the player that creates the current game
     * @throws NullPointerException is thrown if no player is submitted
     */
    public Game(int np, Player p) throws NullPointerException{
        numOfPlayers=np;
        addPlayer(p);
        status=GameStatus.WAITING;
        currentBoard= new Board();
        currentNumOfPlayers++;
    }

    /**
     * Getter method for parameter {@code round}
     * @return the current round
     */
    public int getRound() {
        return round;
    }

    /**
     *
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

    public void addPlayer(Player p) throws NullPointerException{
        if(currentNumOfPlayers<numOfPlayers){
            players.add(p);
            currentNumOfPlayers++;
        }
        else{
            gameFull=true;
        }
    }
    /**
     *
     * @return the following player located in the player list
     */
    public Player pickNextPlayer(){
        if(currentIndex==(players.size()-1)){
            currentlyPlaying=players.get(0);
        }
        else{
            currentIndex=players.indexOf(getCurrentlyPlaying());
            currentlyPlaying=players.get(currentIndex);
        }
        return currentlyPlaying;
    }

    /**
     *
     * @return the player that is playing in this moment
     */
    public Player getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    /**
     *
     * @return true if in the game it has been reached the maximum number of players
     */
    public boolean isGameFull() {
        return gameFull;
    }

    public int getCurrentNumOfPlayers() {
        return currentNumOfPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
