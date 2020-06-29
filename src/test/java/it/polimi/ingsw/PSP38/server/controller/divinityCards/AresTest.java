package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Tower;
import it.polimi.ingsw.PSP38.server.model.Worker;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class AresTest {
    Ares ares = new Ares();

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
        ares.move(worker, destinationCell, board);
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
        ares.move(worker, destinationCell, board);
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
        ares.move(worker, destinationCell, board);
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
        ares.build(worker, destinationCell, board);
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
        ares.build(worker, destinationCell, board);
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
        ares.move(worker, destinationCell, board);
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
        ares.move(worker, destinationCell, board);
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
        board = ares.move(worker, destinationCell, board);
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
        Worker worker2 = new Worker(WorkerColor.WHITE, destinationCell);
        Tower tower = new Tower(workerPosition, 3);
        Tower tower2 = new Tower(destinationCell, 3);
        workers.add(worker);
        workers.add(worker2);
        towers.add(tower);
        towers.add(tower2);
        Board board = new Board(workers, towers, domes);
        board = ares.move(worker, destinationCell, board);
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
        board = ares.optionalAction(workerMoved, towerPosition, board);
        assertEquals(2, board.heightOf(towerPosition));
    }

    @Test(expected = IllegalArgumentException.class)
    public void optionalActionThrowsExceptionWhenCellHasDome(){
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
        ares.optionalAction(workerMoved, towerPosition, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void optionalActionThrowsExceptionWhenCellHasWorker(){
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
        ares.optionalAction(workerMoved, towerPosition, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void optionalActionThrowsExceptionWhenCellNotNeighbor(){
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
        ares.optionalAction(workerMoved, towerPosition, board);
    }
}