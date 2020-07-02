package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Tower;
import it.polimi.ingsw.PSP38.server.model.Worker;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;

import static org.junit.Assert.*;

public class MinotaurTest {
    Minotaur minotaur = new Minotaur();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void moveOnCellNotNeighborThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell is too far away.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(3, 3);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        minotaur.move(worker, destinationCell, board, false);
    }

    @Test
    public void moveOnDomeThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell contains a dome.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        domes.add(destinationCell);
        Board board = new Board(workers, towers, domes);
        minotaur.move(worker, destinationCell, board, false);
    }

    @Test
    public void moveOnTowerTooHighThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This tower is too high.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);
        Tower tower = new Tower(destinationCell, 2);

        workers.add(worker);
        towers.add(tower);
        Board board = new Board(workers, towers, domes);
        minotaur.move(worker, destinationCell, board, false);
    }

    @Test
    public void buildOnDomeThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell contains a dome.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        domes.add(destinationCell);
        Board board = new Board(workers, towers, domes);
        minotaur.build(worker, destinationCell, board, false);
    }

    @Test
    public void buildOnWorkerThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell contains a worker.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);
        Worker worker2 = new Worker(WorkerColor.BLUE, destinationCell);

        workers.add(worker);
        workers.add(worker2);
        Board board = new Board(workers, towers, domes);
        minotaur.build(worker, destinationCell, board, false);
    }

    @Test
    public void buildOnCellNotNeighborThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell is too far away.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(3, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        minotaur.build(worker, destinationCell, board, false);
    }

    @Test
    public void moveOnSameColorWorkerThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell contains a worker with the same color.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);
        Worker worker2 = new Worker(WorkerColor.BLUE, destinationCell);

        workers.add(worker);
        workers.add(worker2);
        Board board = new Board(workers, towers, domes);
        minotaur.move(worker, destinationCell, board, false);
    }

    @Test
    public void moveOnDifferentColorWorkerCorrect() {
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);
        Worker opponent1 = new Worker(WorkerColor.WHITE, new Cell(1, 1));
        Worker opponent2 = new Worker(WorkerColor.WHITE, new Cell(1, 2));
        Worker opponent3 = new Worker(WorkerColor.WHITE, new Cell(1, 3));
        Worker opponent4 = new Worker(WorkerColor.WHITE, new Cell(2, 1));
        Worker opponent5 = new Worker(WorkerColor.WHITE, new Cell(2, 3));
        Worker opponent6 = new Worker(WorkerColor.WHITE, new Cell(3, 1));
        Worker opponent7 = new Worker(WorkerColor.WHITE, new Cell(3, 2));
        Worker opponent8 = new Worker(WorkerColor.WHITE, new Cell(3, 3));


        workers.add(worker);
        workers.add(opponent1);
        workers.add(opponent2);
        workers.add(opponent3);
        workers.add(opponent4);
        workers.add(opponent5);
        workers.add(opponent6);
        workers.add(opponent7);
        workers.add(opponent8);

        Board board = new Board(workers, towers, domes);

        Cell destinationCell = opponent1.getPosition();
        Board boardTest = minotaur.move(worker, destinationCell, board, false);
        assertEquals(WorkerColor.BLUE, boardTest.workerAt(destinationCell).getColor());
        assertTrue(boardTest.hasWorkerAt(new Cell(0, 0)));
        assertEquals(WorkerColor.WHITE, boardTest.workerAt(new Cell(0, 0)).getColor());

        destinationCell = opponent2.getPosition();
        boardTest = minotaur.move(worker, destinationCell, board, false);
        assertEquals(WorkerColor.BLUE, boardTest.workerAt(destinationCell).getColor());
        assertTrue(boardTest.hasWorkerAt(new Cell(0, 2)));
        assertEquals(WorkerColor.WHITE, boardTest.workerAt(new Cell(0, 2)).getColor());

        destinationCell = opponent3.getPosition();
        boardTest = minotaur.move(worker, destinationCell, board, false);
        assertEquals(WorkerColor.BLUE, boardTest.workerAt(destinationCell).getColor());
        assertTrue(boardTest.hasWorkerAt(new Cell(0, 4)));
        assertEquals(WorkerColor.WHITE, boardTest.workerAt(new Cell(0, 4)).getColor());

        destinationCell = opponent4.getPosition();
        boardTest = minotaur.move(worker, destinationCell, board, false);
        assertEquals(WorkerColor.BLUE, boardTest.workerAt(destinationCell).getColor());
        assertTrue(boardTest.hasWorkerAt(new Cell(2, 0)));
        assertEquals(WorkerColor.WHITE, boardTest.workerAt(new Cell(2, 0)).getColor());

        destinationCell = opponent5.getPosition();
        boardTest = minotaur.move(worker, destinationCell, board, false);
        assertEquals(WorkerColor.BLUE, boardTest.workerAt(destinationCell).getColor());
        assertTrue(boardTest.hasWorkerAt(new Cell(2, 4)));
        assertEquals(WorkerColor.WHITE, boardTest.workerAt(new Cell(2, 4)).getColor());

        destinationCell = opponent6.getPosition();
        boardTest = minotaur.move(worker, destinationCell, board, false);
        assertEquals(WorkerColor.BLUE, boardTest.workerAt(destinationCell).getColor());
        assertTrue(boardTest.hasWorkerAt(new Cell(4, 0)));
        assertEquals(WorkerColor.WHITE, boardTest.workerAt(new Cell(4, 0)).getColor());

        destinationCell = opponent7.getPosition();
        boardTest = minotaur.move(worker, destinationCell, board, false);
        assertEquals(WorkerColor.BLUE, boardTest.workerAt(destinationCell).getColor());
        assertTrue(boardTest.hasWorkerAt(new Cell(4, 2)));
        assertEquals(WorkerColor.WHITE, boardTest.workerAt(new Cell(4, 2)).getColor());

        destinationCell = opponent8.getPosition();
        boardTest = minotaur.move(worker, destinationCell, board, false);
        assertEquals(WorkerColor.BLUE, boardTest.workerAt(destinationCell).getColor());
        assertTrue(boardTest.hasWorkerAt(new Cell(4, 4)));
        assertEquals(WorkerColor.WHITE, boardTest.workerAt(new Cell(4, 4)).getColor());
    }

    @Test
    public void moveOnDifferentColorWorkerInFrontOfDomeThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("You can't push your opponent's worker because there is a dome behind it.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);
        Worker opponent = new Worker(WorkerColor.WHITE, new Cell(1, 1));


        workers.add(worker);
        workers.add(opponent);
        domes.add(new Cell(0, 0));
        Board board = new Board(workers, towers, domes);

        Cell destinationCell = opponent.getPosition();
        minotaur.move(worker, destinationCell, board, false);
    }

    @Test
    public void moveOnDifferentColorWorkerInFrontOfWorkerThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("You can't push your opponent's worker because there is another worker behind it.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);
        Worker opponent = new Worker(WorkerColor.WHITE, new Cell(1, 1));
        Worker behindOpponent = new Worker(WorkerColor.BLACK, new Cell(0, 0));

        workers.add(worker);
        workers.add(opponent);
        workers.add(behindOpponent);
        Board board = new Board(workers, towers, domes);

        Cell destinationCell = opponent.getPosition();
        minotaur.move(worker, destinationCell, board, false);
    }

    @Test
    public void moveOnDifferentColorWorkerOnPerimeterThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("You can't push your opponent outside of the board.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);
        Worker opponent = new Worker(WorkerColor.WHITE, new Cell(0, 0));

        workers.add(worker);
        workers.add(opponent);
        Board board = new Board(workers, towers, domes);

        Cell destinationCell = opponent.getPosition();
        minotaur.move(worker, destinationCell, board, false);
    }

    @Test
    public void isWinnerWhenMovingFrom2to3True() {
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);
        Tower tower = new Tower(workerPosition, 2);
        Tower tower2 = new Tower(destinationCell, 3);
        workers.add(worker);
        towers.add(tower);
        towers.add(tower2);
        Board board = new Board(workers, towers, domes);
        board = minotaur.move(worker, destinationCell, board, false);
        assertTrue(minotaur.isWinner(board, workerPosition, destinationCell));
    }

    @Test
    public void isWinnerWhenMovingFrom3to3False() {
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);
        Tower tower = new Tower(workerPosition, 3);
        Tower tower2 = new Tower(destinationCell, 3);
        workers.add(worker);
        towers.add(tower);
        towers.add(tower2);
        Board board = new Board(workers, towers, domes);
        board = minotaur.move(worker, destinationCell, board, false);
        assertFalse(minotaur.isWinner(board, workerPosition, destinationCell));
    }

    @Test
    public void toStringCorrect(){
        assertEquals("Minotaur", minotaur.toString());
    }

}