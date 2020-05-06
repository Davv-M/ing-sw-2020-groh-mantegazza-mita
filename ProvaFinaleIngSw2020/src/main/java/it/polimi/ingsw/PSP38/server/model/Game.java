package it.polimi.ingsw.PSP38.server.model;

import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Player;

import java.util.*;

/**
 * This class contains the methods needed to manage the execution of a game
 *
 * @author Davide Mantegazza (10568661)
 */

public class Game {
    public static final int MAX_NUMBER_OF_PLAYERS = 3;
    public static final int MIN_NUMBER_OF_PLAYERS = 2;
    public static final int WORKERS_PER_PLAYER = 2;
    private final List<Player> players = new LinkedList<>();
    private Board currentBoard;

    /**
     * constructor of the Game class.
     *
     * @throws NullPointerException is thrown if no player is submitted
     */
    public Game() throws NullPointerException {
        currentBoard = new Board();
    }

    public void addPlayer(Player player){
        players.add(player);
        players.sort(Comparator.comparingInt(Player::getAge));
    }

    public int getNumberOfPlayers(){
        return players.size();
    }

    public synchronized String youngestPlayer() {
        return players.get(0).getNickname();
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

    public synchronized Player nicknameToPlayer(String nickname) {
        Optional<Player> player = players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst();
        return player.orElse(null);
    }

}