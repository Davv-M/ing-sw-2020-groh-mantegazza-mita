package it.polimi.ingsw.PSP38.server.model;


import it.polimi.ingsw.PSP38.common.WorkerColor;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class BoardTest {

    @Test(expected = NullPointerException.class)
    public void constructorThrowsExceptionNullWorkers(){
        new Board(null, new HashSet<>(), new HashSet<>());
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsExceptionNullTowers(){
        new Board(new HashSet<>(), null, new HashSet<>());
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsExceptionNullDomes(){
        new Board(new HashSet<>(), new HashSet<>(), null);
    }

    @Test
    public void constantsAreCorrect() {
        assertEquals(5, Board.COLUMNS);
        assertEquals(5, Board.ROWS);
    }

    @Test
    public void neighborOfUpperRightCornerCorrect() {
        Board emptyBoard = new Board();
        Cell upperRight = new Cell(Board.COLUMNS - 1, 0);

        assertEquals(Optional.empty(), emptyBoard.neighborOf(upperRight, Direction.N));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(upperRight, Direction.E));
        assertEquals(Optional.of(new Cell(Board.COLUMNS - 1, 1)), emptyBoard.neighborOf(upperRight, Direction.S));
        assertEquals(Optional.of(new Cell(Board.COLUMNS - 2, 0)), emptyBoard.neighborOf(upperRight, Direction.W));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(upperRight, Direction.NE));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(upperRight, Direction.SE));
        assertEquals(Optional.of(new Cell(Board.COLUMNS - 2, 1)), emptyBoard.neighborOf(upperRight, Direction.SW));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(upperRight, Direction.NW));
    }

    @Test
    public void neighborsOfUpperRightCorrect() {
        Board emptyBoard = new Board();
        Cell upperRight = new Cell(Board.COLUMNS - 1, 0);
        Set<Cell> neighborsTest = emptyBoard.neighborsOf(upperRight);
        Set<Cell> neighborsCorrect = Set.of(new Cell(Board.COLUMNS - 2, 0),
                new Cell(Board.COLUMNS - 2, 1),
                new Cell(4, 1));
        assertEquals(neighborsCorrect, neighborsTest);
    }

    @Test
    public void neighborOfUpperLeftCornerCorrect() {
        Board emptyBoard = new Board();
        Cell upperLeft = new Cell(0, 0);

        assertEquals(Optional.empty(), emptyBoard.neighborOf(upperLeft, Direction.N));
        assertEquals(Optional.of(new Cell(1, 0)), emptyBoard.neighborOf(upperLeft, Direction.E));
        assertEquals(Optional.of(new Cell(0, 1)), emptyBoard.neighborOf(upperLeft, Direction.S));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(upperLeft, Direction.W));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(upperLeft, Direction.NE));
        assertEquals(Optional.of(new Cell(1, 1)), emptyBoard.neighborOf(upperLeft, Direction.SE));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(upperLeft, Direction.SW));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(upperLeft, Direction.NW));
    }

    @Test
    public void neighborsOfUpperLeftCorrect() {
        Board emptyBoard = new Board();
        Cell upperLeft = new Cell(0, 0);
        Set<Cell> neighborsTest = emptyBoard.neighborsOf(upperLeft);
        Set<Cell> neighborsCorrect = Set.of(new Cell(0, 1),
                new Cell(1, 1),
                new Cell(1, 0));
        assertEquals(neighborsCorrect, neighborsTest);
    }

    @Test
    public void neighborOfLowerRightCornerCorrect() {
        Board emptyBoard = new Board();
        Cell lowerRight = new Cell(Board.COLUMNS - 1, Board.ROWS - 1);

        assertEquals(Optional.of(new Cell(Board.COLUMNS - 1, Board.ROWS - 2)), emptyBoard.neighborOf(lowerRight, Direction.N));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(lowerRight, Direction.E));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(lowerRight, Direction.S));
        assertEquals(Optional.of(new Cell(Board.COLUMNS - 2, Board.ROWS - 1)), emptyBoard.neighborOf(lowerRight, Direction.W));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(lowerRight, Direction.NE));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(lowerRight, Direction.SE));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(lowerRight, Direction.SW));
        assertEquals(Optional.of(new Cell(Board.COLUMNS - 2, Board.ROWS - 2)), emptyBoard.neighborOf(lowerRight, Direction.NW));
    }

    @Test
    public void neighborsOfLowerRightCorrect() {
        Board emptyBoard = new Board();
        Cell lowerRight = new Cell(Board.COLUMNS - 1, Board.ROWS - 1);

        Set<Cell> neighborsTest = emptyBoard.neighborsOf(lowerRight);
        Set<Cell> neighborsCorrect = Set.of(new Cell(Board.COLUMNS - 1, Board.ROWS - 2),
                new Cell(Board.COLUMNS - 2, Board.ROWS - 2),
                new Cell(Board.COLUMNS - 2, Board.ROWS - 1));
        assertEquals(neighborsCorrect, neighborsTest);
    }

    @Test
    public void neighborOfLowerLeft() {
        Board emptyBoard = new Board();
        Cell lowerLeft = new Cell(0, Board.ROWS - 1);

        assertEquals(Optional.of(new Cell(0, Board.ROWS - 2)), emptyBoard.neighborOf(lowerLeft, Direction.N));
        assertEquals(Optional.of(new Cell(1, Board.ROWS - 1)), emptyBoard.neighborOf(lowerLeft, Direction.E));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(lowerLeft, Direction.S));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(lowerLeft, Direction.W));
        assertEquals(Optional.of(new Cell(1, Board.ROWS - 2)), emptyBoard.neighborOf(lowerLeft, Direction.NE));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(lowerLeft, Direction.SE));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(lowerLeft, Direction.SW));
        assertEquals(Optional.empty(), emptyBoard.neighborOf(lowerLeft, Direction.NW));
    }

    @Test
    public void neighborsOfLowerLeftCorrect() {
        Board emptyBoard = new Board();
        Cell lowerLeft = new Cell(0, Board.ROWS - 1);

        Set<Cell> neighborsTest = emptyBoard.neighborsOf(lowerLeft);
        Set<Cell> neighborsCorrect = Set.of(new Cell(0, Board.ROWS - 2),
                new Cell(1, Board.ROWS - 2),
                new Cell(1, Board.ROWS - 1));
        assertEquals(neighborsCorrect, neighborsTest);
    }

    @Test
    public void withTowerNullArgumentCorrect() {
        Board emptyBoard = new Board();

        Board boardWithTowerNull = emptyBoard.withTower(null);
        assertEquals(emptyBoard, boardWithTowerNull);
    }

    @Test
    public void withTowerCorrectlyAddsTower() {
        Board emptyBoard = new Board();
        Cell position = new Cell(0, Board.ROWS - 1);
        Tower tower = new Tower(position, 1);
        Board boardWithTower = emptyBoard.withTower(tower);
        Set<Tower> setWithTower = new HashSet<>();
        setWithTower.add(tower);
        Board correctBoardWithTower = new Board(new HashSet<>(), setWithTower, new HashSet<>());

        assertEquals(correctBoardWithTower, boardWithTower);
    }

    @Test
    public void withWorkerNullArgumentCorrect() {
        Board emptyBoard = new Board();

        Board boardWithTowerNull = emptyBoard.withTower(null);
        assertEquals(emptyBoard, boardWithTowerNull);
    }

    @Test
    public void withWorkerCorrectlyAddsNewWorker() {
        Board emptyBoard = new Board();
        Cell position = new Cell(0, Board.ROWS - 1);
        Worker worker = new Worker(WorkerColor.BLUE, position);
        Board boardWithWorker = emptyBoard.withWorker(worker);
        Set<Worker> setWithWorker = new HashSet<>();
        setWithWorker.add(worker);
        Board correctBoardWithWorker = new Board(setWithWorker, new HashSet<>(), new HashSet<>());

        assertEquals(correctBoardWithWorker, boardWithWorker);
    }

    @Test
    public void withoutWorkerNullArgumentCorrect() {
        Cell position = new Cell(0, Board.ROWS - 1);
        Worker worker = new Worker(WorkerColor.BLUE, position);
        Set<Worker> setWithWorker = new HashSet<>();
        setWithWorker.add(worker);
        Board boardWithWorker = new Board(setWithWorker, new HashSet<>(), new HashSet<>());

        Board boardWithoutNullWorker = boardWithWorker.withoutWorker(null);
        assertEquals(boardWithWorker, boardWithoutNullWorker);
    }

    @Test
    public void withoutWorkerCorrectlyRemovesNewWorker() {
        Board emptyBoard = new Board();
        Cell position = new Cell(0, Board.ROWS - 1);
        Worker worker = new Worker(WorkerColor.BLUE, position);
        Set<Worker> setWithWorker = new HashSet<>();
        setWithWorker.add(worker);
        Board boardWithWorker = new Board(setWithWorker, new HashSet<>(), new HashSet<>());

        Board boardWithoutWorker = boardWithWorker.withoutWorker(worker);

        assertEquals(emptyBoard, boardWithoutWorker);
    }

    @Test
    public void withoutWorkersNullArgumentCorrect() {
        Cell position1 = new Cell(0, Board.ROWS - 1);
        Cell position2 = new Cell(0, 1);
        Cell position3 = new Cell(0, 2);
        Worker worker1 = new Worker(WorkerColor.BLUE, position1);
        Worker worker2 = new Worker(WorkerColor.BLUE, position2);
        Worker worker3 = new Worker(WorkerColor.BLACK, position3);
        Set<Worker> setWithWorkers = new HashSet<>();
        setWithWorkers.add(worker1);
        setWithWorkers.add(worker2);
        setWithWorkers.add(worker3);
        Board boardWithWorkers = new Board(setWithWorkers, new HashSet<>(), new HashSet<>());

        Board boardWithoutNullWorker = boardWithWorkers.withoutWorkers(null);
        assertEquals(boardWithWorkers, boardWithoutNullWorker);
    }

    @Test
    public void withoutWorkersCorrectlyRemovesAllWorkers() {
        Cell position1 = new Cell(0, Board.ROWS - 1);
        Cell position2 = new Cell(0, 1);
        Cell position3 = new Cell(0, 2);
        Worker worker1 = new Worker(WorkerColor.BLUE, position1);
        Worker worker2 = new Worker(WorkerColor.BLUE, position2);
        Worker worker3 = new Worker(WorkerColor.BLACK, position3);
        Set<Worker> setWithWorkers = new HashSet<>();
        setWithWorkers.add(worker3);
        Board correctBoardWithoutWorkers = new Board(setWithWorkers, new HashSet<>(), new HashSet<>());
        setWithWorkers.add(worker1);
        setWithWorkers.add(worker2);
        Board boardWithWorkers = new Board(setWithWorkers, new HashSet<>(), new HashSet<>());

        Board boardWithoutWorkers = boardWithWorkers.withoutWorkers(WorkerColor.BLUE);

        assertEquals(correctBoardWithoutWorkers, boardWithoutWorkers);
    }

    @Test
    public void withoutTowerNullArgumentCorrect(){
        Cell position = new Cell(0, Board.ROWS - 1);
        Tower tower = new Tower(position, 1);
        Set<Tower> setWithTower = new HashSet<>();
        setWithTower.add(tower);
        Board boardWithTower = new Board(new HashSet<>(), setWithTower, new HashSet<>());

        assertEquals(boardWithTower, boardWithTower.withoutTower(null));
    }

    @Test
    public void withoutTowerCorrectlyRemovesTower(){
        Board emptyBoard = new Board();
        Cell position = new Cell(0, Board.ROWS - 1);
        Tower tower = new Tower(position, 1);
        Set<Tower> setWithTower = new HashSet<>();
        setWithTower.add(tower);
        Board boardWithTower = new Board(new HashSet<>(), setWithTower, new HashSet<>());

        assertEquals(emptyBoard, boardWithTower.withoutTower(tower));
    }

    @Test
    public void withDomeNullArgumentCorrect() {
        Board emptyBoard = new Board();

        Board boardWithDomeNull = emptyBoard.withDome(null);
        assertEquals(emptyBoard, boardWithDomeNull);
    }

    @Test
    public void withDomeCorrectlyAddsDome() {
        Board emptyBoard = new Board();
        Cell position = new Cell(0, Board.ROWS - 1);
        Board boardWithDome = emptyBoard.withDome(position);
        Set<Cell> setWithDome = new HashSet<>();
        setWithDome.add(position);
        Board correctBoardWithDome = new Board(new HashSet<>(), new HashSet<>(), setWithDome);

        assertEquals(correctBoardWithDome, boardWithDome);
    }

    @Test
    public void getWorkersPositions() {
        Cell position1 = new Cell(0, Board.ROWS - 1);
        Cell position2 = new Cell(0, 1);
        Cell position3 = new Cell(0, 2);
        Worker worker1 = new Worker(WorkerColor.BLUE, position1);
        Worker worker2 = new Worker(WorkerColor.BLUE, position2);
        Worker worker3 = new Worker(WorkerColor.BLACK, position3);
        Map<Cell, Worker> workerCells = new HashMap<>();
        workerCells.put(position1, worker1);
        workerCells.put(position2, worker2);
        workerCells.put(position3, worker3);
        Set<Worker> setWithWorkers = new HashSet<>();
        setWithWorkers.add(worker1);
        setWithWorkers.add(worker2);
        setWithWorkers.add(worker3);
        Board boardWithWorkers = new Board(setWithWorkers, new HashSet<>(), new HashSet<>());
        assertEquals(workerCells, boardWithWorkers.getWorkersPositions());
    }

    @Test
    public void getTowersPositions() {
        Cell position1 = new Cell(0, Board.ROWS - 1);
        Cell position2 = new Cell(0, 1);
        Cell position3 = new Cell(0, 2);
        Tower tower1 = new Tower(position1, 3);
        Tower tower2 = new Tower(position2, 1);
        Tower tower3 = new Tower(position3, 2);
        Map<Cell, Tower> towerCells = new HashMap<>();
        towerCells.put(position1, tower1);
        towerCells.put(position2, tower2);
        towerCells.put(position3, tower3);
        Set<Tower> setWithTowers = new HashSet<>();
        setWithTowers.add(tower1);
        setWithTowers.add(tower2);
        setWithTowers.add(tower3);
        Board boardWithTowers = new Board(new HashSet<>(), setWithTowers, new HashSet<>());
        assertEquals(towerCells, boardWithTowers.getTowersPositions());
    }

    @Test
    public void hasDomeAt() {
        Cell position1 = new Cell(0, Board.ROWS - 1);
        Cell position2 = new Cell(0, 1);
        Cell position3 = new Cell(0, 2);
        Set<Cell> setWithDome = new HashSet<>();
        setWithDome.add(position1);
        setWithDome.add(position2);
        setWithDome.add(position3);
        Board boardWithDome = new Board(new HashSet<>(), new HashSet<>(), setWithDome);

        assertTrue(boardWithDome.hasDomeAt(position1));
        assertTrue(boardWithDome.hasDomeAt(position2));
        assertTrue(boardWithDome.hasDomeAt(position3));
        assertFalse(boardWithDome.hasDomeAt(new Cell(0, 0)));
        assertFalse(boardWithDome.hasDomeAt(new Cell(3, 0)));
        assertFalse(boardWithDome.hasDomeAt(new Cell(3, 3)));
    }

    @Test
    public void hasWorkerAt() {
        Cell position1 = new Cell(0, Board.ROWS - 1);
        Cell position2 = new Cell(0, 1);
        Cell position3 = new Cell(0, 2);
        Worker worker1 = new Worker(WorkerColor.BLUE, position1);
        Worker worker2 = new Worker(WorkerColor.BLUE, position2);
        Worker worker3 = new Worker(WorkerColor.BLACK, position3);
        Set<Worker> setWithWorkers = new HashSet<>();
        setWithWorkers.add(worker1);
        setWithWorkers.add(worker2);
        setWithWorkers.add(worker3);
        Board boardWithWorkers = new Board(setWithWorkers, new HashSet<>(), new HashSet<>());

        assertTrue(boardWithWorkers.hasWorkerAt(position1));
        assertTrue(boardWithWorkers.hasWorkerAt(position2));
        assertTrue(boardWithWorkers.hasWorkerAt(position3));
        assertFalse(boardWithWorkers.hasWorkerAt(new Cell(0, 0)));
        assertFalse(boardWithWorkers.hasWorkerAt(new Cell(3, 0)));
        assertFalse(boardWithWorkers.hasWorkerAt(new Cell(3, 3)));
    }

    @Test(expected = NullPointerException.class)
    public void workerAtThrowsExceptionIfCellNull(){
        Board emptyBoard = new Board();
        emptyBoard.workerAt(null);
    }

    @Test(expected = NullPointerException.class)
    public void workerAtThrowsExceptionIfWorkerNotPresent(){
        Board emptyBoard = new Board();
        emptyBoard.workerAt(new Cell(1, 1));
    }

    @Test
    public void workerAtCorrectlyReturnsWorker(){
        Cell position1 = new Cell(0, Board.ROWS - 1);
        Cell position2 = new Cell(0, 1);
        Cell position3 = new Cell(0, 2);
        Worker worker1 = new Worker(WorkerColor.BLUE, position1);
        Worker worker2 = new Worker(WorkerColor.BLUE, position2);
        Worker worker3 = new Worker(WorkerColor.BLACK, position3);
        Set<Worker> setWithWorkers = new HashSet<>();
        setWithWorkers.add(worker1);
        setWithWorkers.add(worker2);
        setWithWorkers.add(worker3);
        Board boardWithWorkers = new Board(setWithWorkers, new HashSet<>(), new HashSet<>());

        assertEquals(worker1, boardWithWorkers.workerAt(position1));
        assertEquals(worker2, boardWithWorkers.workerAt(position2));
        assertEquals(worker3, boardWithWorkers.workerAt(position3));
    }

    @Test
    public void heightOf(){
        Cell position1 = new Cell(0, Board.ROWS - 1);
        Cell position2 = new Cell(0, 1);
        Cell position3 = new Cell(0, 2);
        Tower tower1 = new Tower(position1, 3);
        Tower tower2 = new Tower(position2, 1);
        Tower tower3 = new Tower(position3, 2);
        Set<Tower> setWithTowers = new HashSet<>();
        setWithTowers.add(tower1);
        setWithTowers.add(tower2);
        setWithTowers.add(tower3);
        Board boardWithTowers = new Board(new HashSet<>(), setWithTowers, new HashSet<>());

        assertEquals(0, boardWithTowers.heightOf(new Cell(0, 0)));
        assertEquals(3, boardWithTowers.heightOf(position1));
        assertEquals(1, boardWithTowers.heightOf(position2));
        assertEquals(2, boardWithTowers.heightOf(position3));
        assertEquals(0, boardWithTowers.heightOf(null));
    }

    @Test
    public void equalsCorrect(){
        Cell position1 = new Cell(0, Board.ROWS - 1);
        Cell position2 = new Cell(0, 1);
        Cell position3 = new Cell(0, 2);
        Tower tower1 = new Tower(position1, 3);
        Tower tower2 = new Tower(position2, 1);
        Tower tower3 = new Tower(position3, 2);
        Worker worker1 = new Worker(WorkerColor.BLUE, new Cell(0,0));
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

        Board board1 = new Board(setWithWorkers, setWithTowers, setWithDome);

        position1 = new Cell(0, Board.ROWS - 1);
        position2 = new Cell(0, 1);
        position3 = new Cell(0, 2);
        tower1 = new Tower(position1, 3);
        tower2 = new Tower(position2, 1);
        tower3 = new Tower(position3, 2);
        worker1 = new Worker(WorkerColor.BLUE, new Cell(0,0));
        worker2 = new Worker(WorkerColor.BLUE, position1);
        worker3 = new Worker(WorkerColor.BLACK, position2);
        setWithTowers = new HashSet<>();
        setWithTowers.add(tower1);
        setWithTowers.add(tower2);
        setWithTowers.add(tower3);
        setWithWorkers = new HashSet<>();
        setWithWorkers.add(worker1);
        setWithWorkers.add(worker2);
        setWithWorkers.add(worker3);
        setWithDome = new HashSet<>();
        setWithDome.add(position3);

        Board board2 = new Board(setWithWorkers, setWithTowers, setWithDome);

        assertEquals(board1, board2);
        assertNotEquals(null, board1);
    }
}