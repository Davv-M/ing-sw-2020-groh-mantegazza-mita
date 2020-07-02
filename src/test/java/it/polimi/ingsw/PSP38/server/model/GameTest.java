package it.polimi.ingsw.PSP38.server.model;

import it.polimi.ingsw.PSP38.common.WorkerColor;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GameTest {

    @Test
    public void constantsAreCorrect() {
        assertEquals(3, Game.MAX_NUMBER_OF_PLAYERS);
        assertEquals(2, Game.MIN_NUMBER_OF_PLAYERS);
        assertEquals(2, Game.WORKERS_PER_PLAYER);
    }

    @Test
    public void addPlayer() {
        Game game = new Game();
        game.addPlayer("max", 27);
        assertEquals(1, game.getCurrNumPlayers());
        game.addPlayer("marco", 25);
        assertEquals("marco", game.getCurrentPlayerTurn().getNickname());
    }

    @Test
    public void removePlayer() {
        Game game = new Game();
        game.addPlayer("max", 27);
        game.removePlayer(new Player("max", 27, WorkerColor.values()[0]));

        assertEquals(0, game.getCurrNumPlayers());
    }

    @Test
    public void getCurrentBoard() {
        Cell position1 = new Cell(0, Board.ROWS - 1);
        Cell position2 = new Cell(0, 1);
        Cell position3 = new Cell(0, 2);
        Tower tower1 = new Tower(position1, 3);
        Tower tower2 = new Tower(position2, 1);
        Tower tower3 = new Tower(position3, 2);
        Worker worker1 = new Worker(WorkerColor.BLUE, new Cell(0, 0));
        Worker worker2 = new Worker(WorkerColor.BLUE, position1);
        Worker worker3 = new Worker(WorkerColor.BLACK, position2);
        Set<Tower> setWithTowers = new HashSet<>();
        setWithTowers.add(tower1);
        setWithTowers.add(tower2);
        setWithTowers.add(tower3);
        Set<Worker> setWithWorkers = new HashSet<>();
        setWithWorkers.add(worker1);
        setWithWorkers.add(worker2);
        setWithWorkers.add(worker3);
        Set<Cell> setWithDome = new HashSet<>();
        setWithDome.add(position3);

        Board board = new Board(setWithWorkers, setWithTowers, setWithDome);

        Game game = new Game();
        game.setCurrentBoard(board);

        assertEquals(board, game.getCurrentBoard());
    }

    @Test
    public void getCurrentPlayerTurn() {
        Game game = new Game();
        game.addPlayer("matteo", 29);
        game.addPlayer("max", 27);
        game.addPlayer("marco", 30);
        assertEquals("max", game.getCurrentPlayerTurn().getNickname());
    }

    @Test
    public void getOpponents() {
        Game game = new Game();
        game.addPlayer("matteo", 29);
        game.addPlayer("max", 27);
        game.addPlayer("marco", 30);
        assertEquals(Arrays.asList("matteo", "marco"), Arrays.asList(game.getOpponents().stream().map(Player::getNickname).toArray()));
    }

    @Test
    public void setTotNumPlayers() {
        Game game = new Game();
        game.setTotNumPlayers(3);
        assertEquals(3, game.getTotNumPlayers());
    }

    @Test
    public void getCurrNumPlayers() {
        Game game = new Game();
        assertEquals(0, game.getCurrNumPlayers());
        game.addPlayer("matteo", 29);
        assertEquals(1, game.getCurrNumPlayers());
        game.addPlayer("max", 27);
        assertEquals(2, game.getCurrNumPlayers());
        game.addPlayer("marco", 30);
        assertEquals(3, game.getCurrNumPlayers());
    }

    @Test
    public void nicknameToPlayer() {
        Game game = new Game();
        game.addPlayer("matteo", 29);
        game.addPlayer("max", 27);
        game.addPlayer("marco", 30);
        Player p = game.nicknameToPlayer("max");
        assertEquals("max", p.getNickname());
        assertEquals(27, p.getAge());
    }

    @Test
    public void setWinner() {
        Game game = new Game();
        Player max = new Player("max", 27, WorkerColor.WHITE);
        game.setWinner(max);

        assertEquals(max, game.getWinner());
    }
}