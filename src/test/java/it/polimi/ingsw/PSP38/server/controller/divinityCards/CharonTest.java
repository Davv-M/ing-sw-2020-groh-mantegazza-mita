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

public class CharonTest {
    Charon charon = new Charon();

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
        charon.move(worker, destinationCell, board, false);
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
        charon.move(worker, destinationCell, board, false);
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
        charon.move(worker, destinationCell, board, false);
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
        charon.build(worker, destinationCell, board, false);
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
        charon.build(worker, destinationCell, board, false);
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
        charon.build(worker, destinationCell, board, false);
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
        charon.move(worker, destinationCell, board, false);
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
        charon.move(worker, destinationCell, board, false);
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
        board = charon.move(worker, destinationCell, board, false);
        assertTrue(charon.isWinner(board, workerPosition, destinationCell));
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
        board = charon.move(worker, destinationCell, board, false);
        assertFalse(charon.isWinner(board, workerPosition, destinationCell));
    }

    @Test
    public void getMoveSequence() {
        assertEquals(List.of(WorkerAction.OPTIONAL_ACTION, WorkerAction.MOVE, WorkerAction.BUILD), charon.getMoveSequence());
    }

    @Test
    public void optionalActionThrowsExceptionSelectedCellNotNeighbor() throws IllegalArgumentException{
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell is not a neighbor of the worker's cell.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell selectedCell = new Cell(1,1);
        Worker worker = new Worker(WorkerColor.BLUE, new Cell(3, 3));
        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        charon.optionalAction(worker, selectedCell, board, false);
    }

    @Test
    public void optionalActionThrowsExceptionSelectedCellHasDome() throws IllegalArgumentException{
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell doesn't contain a worker.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell selectedCell = new Cell(2,2);
        Worker worker = new Worker(WorkerColor.BLUE, new Cell(3, 3));
        workers.add(worker);
        domes.add(selectedCell);
        Board board = new Board(workers, towers, domes);
        charon.optionalAction(worker, selectedCell, board, false);
    }

    @Test
    public void optionalActionThrowsExceptionSelectedCellHasWorkerSameColor() throws IllegalArgumentException{
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell contains a worker with the same color.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell selectedCell = new Cell(2,2);
        Worker worker = new Worker(WorkerColor.BLUE, new Cell(3, 3));
        Worker worker2 = new Worker(WorkerColor.BLUE, selectedCell);
        workers.add(worker);
        workers.add(worker2);
        Board board = new Board(workers, towers, domes);
        charon.optionalAction(worker, selectedCell, board, false);
    }

    @Test
    public void optionalActionThrowsExceptionSelectedCellHasNoWorker() throws IllegalArgumentException{
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("This cell doesn't contain a worker.");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell selectedCell = new Cell(2,2);
        Worker worker = new Worker(WorkerColor.BLUE, new Cell(3, 3));
        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        charon.optionalAction(worker, selectedCell, board, false);
    }

    @Test
    public void optionalActionThrowsExceptionOppositeCellOutOfBounds() throws IllegalArgumentException{
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("the selected worker can't be moved");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell selectedCell = new Cell(1,1);
        Worker worker = new Worker(WorkerColor.BLUE, new Cell(0, 0));
        Worker opponent = new Worker(WorkerColor.WHITE, selectedCell);
        workers.add(worker);
        workers.add(opponent);
        Board board = new Board(workers, towers, domes);
        charon.optionalAction(worker, selectedCell, board, false);
    }

    @Test
    public void optionalActionThrowsExceptionOppositeCellHasDome() throws IllegalArgumentException{
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("the selected worker can't be moved");

        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell selectedCell = new Cell(1,1);
        Worker worker = new Worker(WorkerColor.BLUE, new Cell(2, 2));
        Worker opponent = new Worker(WorkerColor.WHITE, selectedCell);
        workers.add(worker);
        workers.add(opponent);
        domes.add(new Cell(3, 3));
        Board board = new Board(workers, towers, domes);
        charon.optionalAction(worker, selectedCell, board, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void optionalActionThrowsExceptionOppositeCellHasWorker(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell selectedCell = new Cell(1,1);
        Worker worker = new Worker(WorkerColor.BLUE, new Cell(2, 2));
        Worker opponent = new Worker(WorkerColor.WHITE, selectedCell);
        Worker otherOpponent = new Worker(WorkerColor.BLACK, new Cell(3, 3));
        workers.add(worker);
        workers.add(opponent);
        workers.add(otherOpponent);
        Board board = new Board(workers, towers, domes);
        charon.optionalAction(worker, selectedCell, board, false);
    }

    @Test
    public void toStringCorrect(){
        assertEquals("Charon", charon.toString());
    }
}