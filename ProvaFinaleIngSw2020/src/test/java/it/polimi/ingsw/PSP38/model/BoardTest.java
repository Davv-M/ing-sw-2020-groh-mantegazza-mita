package it.polimi.ingsw.PSP38.model;


import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.model.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {

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
}