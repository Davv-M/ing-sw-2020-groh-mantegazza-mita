package it.polimi.ingsw.PSP038.model;

import java.util.ArrayList;

/**
 * This class contains the methods needed to manage the execuition of a game
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

    public Game(int np, Player p) {
        numOfPlayers=np;
        addPlayer(p);
        status=GameStatus.WAITING;
        currentBoard= new Board();
    }

    public int getRound() {
        return round;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void addPlayer(Player p) {
        if(currentNumOfPlayers<numOfPlayers){
            if (p.getDateOfBirth().compareTo(players.get(0).getDateOfBirth())<0)
                players.add(0, p);
            else
                players.add(p);
            currentNumOfPlayers++;
        }
    }

    public void pickNextPlayer(){
        if(currentIndex==(players.size()-1)){
            currentlyPlaying=players.get(0);
        }
        else{
            currentIndex=players.indexOf(getCurrentlyPlaying());
            currentlyPlaying=players.get(currentIndex);
        }
    }
    public Player getCurrentlyPlaying() {
        return currentlyPlaying;
    }
}
