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

public class AtlasTest {
    Atlas atlas = new Atlas();

    @Test(expected = IllegalArgumentException.class)
    public void moveOnCellNotNeighborThrowsException() {
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(3, 3);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        atlas.move(worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveOnDomeThrowsException() {
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        domes.add(destinationCell);
        Board board = new Board(workers, towers, domes);
        atlas.move(worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveOnTowerTooHighThrowsException() {
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
        atlas.move(worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildOnDomeThrowsException() {
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        domes.add(destinationCell);
        Board board = new Board(workers, towers, domes);
        atlas.optionalAbility(false, worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildOnCellNotNeighborThrowsException() {
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(3, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        atlas.optionalAbility(false, worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildOnWorkerThrowsException() {
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
        atlas.optionalAbility(false, worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveOnSameColorWorkerThrowsException() {
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
        atlas.move(worker, destinationCell, board);
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveOnDifferentColorWorkerThrowsException() {
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
        atlas.move(worker, destinationCell, board);
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
        board = atlas.move(worker, destinationCell, board);
        assertTrue(atlas.isWinner(board, workerPosition, destinationCell));
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
        board = atlas.move(worker, destinationCell, board);
        assertFalse(atlas.isWinner(board, workerPosition, destinationCell));
    }

    @Test
    public void typeOfAction() {
        assertEquals(WorkerAction.MOVE, atlas.typeOfAction(WorkerAction.MOVE));
        assertEquals(WorkerAction.BUILD, atlas.typeOfAction(WorkerAction.BUILD));
        assertEquals(WorkerAction.BUILD, atlas.typeOfAction(WorkerAction.OPTIONAL_ABILITY));
    }

    @Test
    public void getMoveSequence() {
        assertEquals(List.of(WorkerAction.MOVE, WorkerAction.OPTIONAL_ABILITY), atlas.getMoveSequence());
    }

    @Test
    public void optionalAbilityBuildsDomeCorrectlyFreeCell(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        Board board = new Board(workers, towers, domes);
        board = atlas.optionalAbility(true, worker, destinationCell, board);
        assertTrue(board.hasDomeAt(destinationCell));
        assertEquals(0, board.heightOf(destinationCell));
    }

    @Test
    public void optionalAbilityBuildsDomeCorrectlyLevel1Tower(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Tower tower = new Tower(destinationCell, 1);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        towers.add(tower);
        Board board = new Board(workers, towers, domes);
        board = atlas.optionalAbility(true, worker, destinationCell, board);
        assertTrue(board.hasDomeAt(destinationCell));
        assertEquals(1, board.heightOf(destinationCell));
    }

    @Test
    public void optionalAbilityBuildsDomeCorrectlyLevel2Tower(){
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();
        Cell workerPosition = new Cell(1, 1);
        Cell destinationCell = new Cell(2, 2);
        Tower tower = new Tower(destinationCell, 2);
        Worker worker = new Worker(WorkerColor.BLUE, workerPosition);

        workers.add(worker);
        towers.add(tower);
        Board board = new Board(workers, towers, domes);
        board = atlas.optionalAbility(true, worker, destinationCell, board);
        assertTrue(board.hasDomeAt(destinationCell));
        assertEquals(2, board.heightOf(destinationCell));
    }
}