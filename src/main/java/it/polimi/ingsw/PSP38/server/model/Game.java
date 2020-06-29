package it.polimi.ingsw.PSP38.server.model;

import it.polimi.ingsw.PSP38.common.WorkerColor;

import java.util.*;

/**
 * Class representing the Game
 *
 * @author Maximilien Groh (10683107)
 */

public class Game {
    public static final int MAX_NUMBER_OF_PLAYERS = 3;
    public static final int MIN_NUMBER_OF_PLAYERS = 2;
    public static final int WORKERS_PER_PLAYER = 2;

    private Player winner = null;
    private int totNumPlayers = 0;
    private final List<Player> players = new LinkedList<>();
    private Board currentBoard = new Board();
    private final List<WorkerColor> availableColors = new LinkedList<>(Arrays.asList(WorkerColor.values()));

    /**
     * Adds a player to the game with the given nickname
     * and age, and assigns a color at random, chosen from
     * the ones available
     *
     * @param nickname the nickname of the player
     * @param age      the age of the player
     */

    public void addPlayer(String nickname, int age) {
        Player player = new Player(nickname, age, availableColors.remove(0));
        players.add(player);
        players.sort(Comparator.comparingInt(Player::getAge));
    }

    /**
     * Removes a player from the game
     *
     * @param player the player to remove
     */

    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * @return the board of the game
     */

    public Board getCurrentBoard() {
        return currentBoard;
    }

    /**
     * @return the player who is playing the current turn
     */

    public Player getCurrentPlayerTurn() {
        return players.get(0);
    }

    /**
     * @return the list of players who are not playing the current turn
     */

    public List<Player> getOpponents() {
        List<Player> opponents = new LinkedList<>(players);
        opponents.remove(getCurrentPlayerTurn());
        return opponents;
    }

    /**
     * Sets the total number of players playing the game
     *
     * @param totNumPlayers the total number of players
     */

    public void setTotNumPlayers(int totNumPlayers) {
        this.totNumPlayers = totNumPlayers;
    }

    /**
     * @return the total number of players
     */

    public int getTotNumPlayers() {
        return totNumPlayers;
    }

    /**
     * @return the number of players currently playing the game
     */

    public int getCurrNumPlayers() {
        return players.size();
    }

    /**
     * @param board updates the board of the game
     */

    public void setCurrentBoard(Board board) {
        this.currentBoard = board;
    }

    /**
     * Returns the player with the given nickname
     *
     * @param nickname the nickname of the player
     * @return the player associated with the nickname
     */

    public Player nicknameToPlayer(String nickname) {
        Optional<Player> player = players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst();
        return player.orElse(null);
    }

    /**
     * Sets the winner of the game
     *
     * @param player the winner
     */

    public void setWinner(Player player) {
        winner = player;
    }

    /**
     * Returns the winner of the game and null if
     * the game doesn't have a winner
     *
     * @return the winner of the game
     */

    public Player getWinner() {
        return winner;
    }

    /**
     * updates the player who has to play
     */

    public void updateTurn() {
        players.add(players.remove(0));
    }
}