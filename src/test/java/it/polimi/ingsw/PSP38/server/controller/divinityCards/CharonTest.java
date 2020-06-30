package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Tower;
import it.polimi.ingsw.PSP38.server.model.Worker;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class CharonTest {
    Charon charon = new Charon();

    @Test(expected = IllegalArgumentException.class)
    public void moveOnCellNotNeighborThrowsException(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(3, 3);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        charon.move(worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveOnDomeThrowsException(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        domes.add(destinationCell);
        Board board = new Board(workers, towers, domes);
        charon.move(worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveOnTowerTooHighThrowsException(){
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
        charon.move(worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildOnDomeThrowsException(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        domes.add(destinationCell);
        Board board = new Board(workers, towers, domes);
        charon.build(worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildOnWorkerThrowsException(){
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
        charon.build(worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildOnCellNotNeighborThrowsException(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(3, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        charon.build(worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveOnSameColorWorkerThrowsException(){
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
        charon.move(worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveOnDifferentColorWorkerThrowsException(){
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
        charon.move(worker, destinationCell, board);
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
        board = charon.move(worker, destinationCell, board);
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
        board = charon.move(worker, destinationCell, board);
        assertFalse(charon.isWinner(board, workerPosition, destinationCell));
    }

    @Test
    public void getMoveSequence() {
        assertEquals(List.of(WorkerAction.OPTIONAL_ACTION, WorkerAction.MOVE, WorkerAction.BUILD), charon.getMoveSequence());
    }

    @Test(expected = IllegalArgumentException.class)
    public void optionalActionThrowsExceptionSelectedCellNotNeighbor(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell selectedCell = new Cell(1,1);
        Worker worker = new Worker(WorkerColor.BLUE, new Cell(3, 3));
        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        charon.optionalAction(worker, selectedCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void optionalActionThrowsExceptionSelectedCellHasDome(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell selectedCell = new Cell(2,2);
        Worker worker = new Worker(WorkerColor.BLUE, new Cell(3, 3));
        workers.add(worker);
        domes.add(selectedCell);
        Board board = new Board(workers, towers, domes);
        charon.optionalAction(worker, selectedCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void optionalActionThrowsExceptionSelectedCellHasWorkerSameColor(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell selectedCell = new Cell(2,2);
        Worker worker = new Worker(WorkerColor.BLUE, new Cell(3, 3));
        Worker worker2 = new Worker(WorkerColor.BLUE, selectedCell);
        workers.add(worker);
        workers.add(worker2);
        Board board = new Board(workers, towers, domes);
        charon.optionalAction(worker, selectedCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void optionalActionThrowsExceptionSelectedCellHasNoWorker(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell selectedCell = new Cell(2,2);
        Worker worker = new Worker(WorkerColor.BLUE, new Cell(3, 3));
        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        charon.optionalAction(worker, selectedCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void optionalActionThrowsExceptionOppositeCellOutOfBounds(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell selectedCell = new Cell(1,1);
        Worker worker = new Worker(WorkerColor.BLUE, new Cell(0, 0));
        Worker opponent = new Worker(WorkerColor.WHITE, selectedCell);
        workers.add(worker);
        workers.add(opponent);
        Board board = new Board(workers, towers, domes);
        charon.optionalAction(worker, selectedCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void optionalActionThrowsExceptionOppositeCellHasDome(){
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
        charon.optionalAction(worker, selectedCell, board);
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
        charon.optionalAction(worker, selectedCell, board);
    }
}