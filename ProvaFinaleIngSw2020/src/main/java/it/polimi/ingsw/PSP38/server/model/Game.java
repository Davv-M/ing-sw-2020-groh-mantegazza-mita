package it.polimi.ingsw.PSP38.server.model;

import it.polimi.ingsw.PSP38.common.WorkerColor;

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

    private Player winner = null;
    private int totNumPlayers = 0;
    private final List<Player> players = new LinkedList<>();
    private Board currentBoard = new Board();
    private final List<WorkerColor> availableColors = new LinkedList<>(Arrays.asList(WorkerColor.values()));

    public void addPlayer(String nickname, int age){
        Player player = new Player(nickname, age, availableColors.remove(0));
        players.add(player);
        players.sort(Comparator.comparingInt(Player::getAge));
    }

    /**
     * @return the currentBoard of the game
     */
    public Board getCurrentBoard() {
        return currentBoard;
    }

    public Player getCurrentPlayerTurn(){
        return players.get(0);
    }

    public void setTotNumPlayers(int totNumPlayers) {
        this.totNumPlayers = totNumPlayers;
    }

    public int getTotNumPlayers(){
        return totNumPlayers;
    }

    public int getCurrNumPlayers(){
        return players.size();
    }

    /**
     * @param board update the currentBoard of the game
     */

    public void setCurrentBoard(Board board) {
        this.currentBoard = board;
    }

    public Player nicknameToPlayer(String nickname) {
        Optional<Player> player = players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst();
        return player.orElse(null);
    }

    public void setWinner(Player player){
        winner = player;
    }

    public Player getWinner(){
        return winner;
    }

    public void updateTurn(){
        players.add(players.remove(0));
    }
}