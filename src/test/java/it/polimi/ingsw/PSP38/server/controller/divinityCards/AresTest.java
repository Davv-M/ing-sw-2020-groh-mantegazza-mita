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

public class AresTest {
    Ares ares = new Ares();

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
        ares.move(worker, destinationCell, board, false);
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
        ares.move(worker, destinationCell, board, false);
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
        ares.move(worker, destinationCell, board, false);
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
        ares.build(worker, destinationCell, board, false);
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
        ares.build(worker, destinationCell, board, false);
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
        ares.build(worker, destinationCell, board, false);
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
        ares.move(worker, destinationCell, board, false);
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
        ares.move(worker, destinationCell, board, false);
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
        board = ares.move(worker, destinationCell, board, false);
        assertTrue(ares.isWinner(board, workerPosition, destinationCell));
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
        board = ares.move(worker, destinationCell, board, false);
        assertFalse(ares.isWinner(board, workerPosition, destinationCell));
    }

    @Test
    public void optionalActionRemovesFreeTowerBlockCorrectly(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Worker workerMoved = new Worker(WorkerColor.BLUE, new Cell(3, 3));
        Worker worker2 = new Worker(WorkerColor.BLUE, new Cell(1, 1));
        Cell towerPosition = new Cell(0,0);
        Tower tower = new Tower(towerPosition, 3);
        workers.add(workerMoved);
        workers.add(worker2);
        towers.add(tower);
        Board board = new Board(workers, towers, domes);
        board = ares.optionalAction(workerMoved, towerPosition, board, false);
        assertEquals(2, board.heightOf(towerPosition));
    }

    @Test
    public void optionalActionThrowsExceptionWhenCellHasDome() throws IllegalArgumentException{
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell contains a dome.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Worker workerMoved = new Worker(WorkerColor.BLUE, new Cell(3, 3));
        Worker worker2 = new Worker(WorkerColor.BLUE, new Cell(1, 1));
        Cell towerPosition = new Cell(0,0);
        Tower tower = new Tower(towerPosition, 3);
        workers.add(workerMoved);
        workers.add(worker2);
        towers.add(tower);
        domes.add(towerPosition);
        Board board = new Board(workers, towers, domes);
        ares.optionalAction(workerMoved, towerPosition, board, false);
    }

    @Test
    public void optionalActionThrowsExceptionWhenCellHasWorker() throws IllegalArgumentException{
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell contains a worker.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Worker workerMoved = new Worker(WorkerColor.BLUE, new Cell(3, 3));
        Worker worker2 = new Worker(WorkerColor.BLUE, new Cell(1, 1));
        Cell towerPosition = new Cell(0,0);
        Tower tower = new Tower(towerPosition, 3);
        Worker worker3 = new Worker(WorkerColor.WHITE, towerPosition);
        workers.add(workerMoved);
        workers.add(worker2);
        workers.add(worker3);
        towers.add(tower);
        Board board = new Board(workers, towers, domes);
        ares.optionalAction(workerMoved, towerPosition, board, false);
    }

    @Test
    public void optionalActionThrowsExceptionWhenCellNotNeighbor() throws IllegalArgumentException{
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell is too far away.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Worker workerMoved = new Worker(WorkerColor.BLUE, new Cell(3, 3));
        Worker worker2 = new Worker(WorkerColor.BLUE, new Cell(1, 1));
        Cell towerPosition = new Cell(1,3);
        Tower tower = new Tower(towerPosition, 3);
        workers.add(workerMoved);
        workers.add(worker2);
        towers.add(tower);
        Board board = new Board(workers, towers, domes);
        ares.optionalAction(workerMoved, towerPosition, board, false);
    }

    @Test
    public void getMoveSequence() {
        assertEquals(List.of(WorkerAction.MOVE, WorkerAction.BUILD, WorkerAction.OPTIONAL_ACTION), ares.getMoveSequence());
    }

    @Test
    public void toStringCorrect(){
        assertEquals("Ares", ares.toString());
    }
}