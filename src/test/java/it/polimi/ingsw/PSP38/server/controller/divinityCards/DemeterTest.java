package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Tower;
import it.polimi.ingsw.PSP38.server.model.Worker;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class DemeterTest {
    Demeter demeter = new Demeter();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void moveOnCellNotNeighborThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell is not a neighbor of the worker's cell.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(3, 3);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        demeter.move(worker, destinationCell, board, false);
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
        demeter.move(worker, destinationCell, board, false);
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
        demeter.move(worker, destinationCell, board, false);
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
        demeter.build(worker, destinationCell, board, false);
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
        demeter.build(worker, destinationCell, board, false);
    }

    @Test
    public void buildOnCellNotNeighborThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell is not a neighbor of the worker's cell.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(3, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        demeter.build(worker, destinationCell, board, false);
    }

    @Test
    public void moveOnSameColorWorkerThrowsException() throws IllegalArgumentException {
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
        demeter.move(worker, destinationCell, board, false);
    }

    @Test
    public void moveOnDifferentColorWorkerThrowsException() throws IllegalArgumentException {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell contains a worker.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);
        Worker worker2 = new Worker(WorkerColor.WHITE, destinationCell);

        workers.add(worker);
        workers.add(worker2);
        Board board = new Board(workers, towers, domes);
        demeter.move(worker, destinationCell, board, false);
    }

    @Test
    public void isWinnerWhenMovingFrom2to3True(){
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
        board = demeter.move(worker, destinationCell, board, false);
        assertTrue(demeter.isWinner(board, workerPosition, destinationCell));
    }

    @Test
    public void isWinnerWhenMovingFrom3to3False(){
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
        board = demeter.move(worker, destinationCell, board, false);
        assertFalse(demeter.isWinner(board, workerPosition, destinationCell));
    }

    @Test
    public void getMoveSequence() {
        assertEquals(List.of(WorkerAction.MOVE, WorkerAction.BUILD, WorkerAction.OPTIONAL_ACTION), demeter.getMoveSequence());
    }

    @Test
    public void typeOfAction() {
        assertEquals(WorkerAction.MOVE, demeter.typeOfAction(WorkerAction.MOVE));
        assertEquals(WorkerAction.BUILD, demeter.typeOfAction(WorkerAction.BUILD));
        assertEquals(WorkerAction.BUILD, demeter.typeOfAction(WorkerAction.OPTIONAL_ACTION));
    }

    @Test
    public void optionalActionThrowsExceptionPreviousBuildCell() throws IllegalArgumentException{
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("You can't build twice on the same cell");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(1, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        board = demeter.build(worker, destinationCell, board, false);

        demeter.optionalAction(worker, destinationCell, board, false);
    }

    @Test
    public void optionalActionCorrect(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(1, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        board = demeter.build(worker, destinationCell, board, false);
        assertEquals(1, board.heightOf(destinationCell));
        destinationCell = new Cell(0, 0);
        board = demeter.optionalAction(worker, destinationCell, board, false);
        assertEquals(1, board.heightOf(destinationCell));
        destinationCell = new Cell(0, 1);
        board = demeter.optionalAction(worker, destinationCell, board, false);
        assertEquals(1, board.heightOf(destinationCell));
        destinationCell = new Cell(1, 0);
        board = demeter.optionalAction(worker, destinationCell, board, false);
        assertEquals(1, board.heightOf(destinationCell));
        destinationCell = new Cell(2, 0);
        board = demeter.optionalAction(worker, destinationCell, board, false);
        assertEquals(1, board.heightOf(destinationCell));
        destinationCell = new Cell(0, 2);
        board = demeter.optionalAction(worker, destinationCell, board, false);
        assertEquals(1, board.heightOf(destinationCell));
        destinationCell = new Cell(2, 1);
        board = demeter.optionalAction(worker, destinationCell, board, false);
        assertEquals(1, board.heightOf(destinationCell));
        destinationCell = new Cell(2, 2);
        board = demeter.optionalAction(worker, destinationCell, board, false);
        assertEquals(1, board.heightOf(destinationCell));
    }

    @Test
    public void toStringCorrect(){
        assertEquals("Demeter", demeter.toString());
    }
}