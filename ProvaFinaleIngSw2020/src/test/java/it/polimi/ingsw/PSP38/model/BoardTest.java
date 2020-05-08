package it.polimi.ingsw.PSP38.model;


import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Direction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class BoardTest {

    Board freeBoardTest = new Board();
    Cell cellTest = new Cell(0, 0);

    @Test
    public void neighborsCells() {
        List<Cell> listCellTest = new ArrayList<>();
        listCellTest.add(new Cell(1, 2));
        listCellTest.add(new Cell(1, 3));
        listCellTest.add(new Cell(2, 3));
        listCellTest.add(new Cell(3, 3));
        listCellTest.add(new Cell(3, 2));
        listCellTest.add(new Cell(3, 1));
        listCellTest.add(new Cell(2, 1));
        listCellTest.add(new Cell(1, 1));
        assertEquals(listCellTest.toString(), freeBoardTest.neighborsCells(new Cell(2, 2)).toString());
        List<Cell> listCellTest1 = new ArrayList<>();
        listCellTest1.add(new Cell(0, 1));
        listCellTest1.add(new Cell(1, 1));
        listCellTest1.add(new Cell(1, 0));
        assertEquals(listCellTest1.toString(), freeBoardTest.neighborsCells(new Cell(0, 0)).toString());
        assertEquals(3, freeBoardTest.neighborsCells(new Cell(0, 0)).size());
        assertEquals(3, freeBoardTest.neighborsCells(new Cell(0, 4)).size());
        assertEquals(3, freeBoardTest.neighborsCells(new Cell(4, 0)).size());
        assertEquals(3, freeBoardTest.neighborsCells(new Cell(4, 4)).size());

    }

    @Test
    public void neighborOfUpperRightCornerCorrect() {
        Cell upperRight = new Cell(Board.COLUMNS - 1, 0);

        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(upperRight, Direction.N));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(upperRight, Direction.E));
        assertEquals(Optional.of(new Cell(Board.COLUMNS - 1, 1)), freeBoardTest.directionNeighbor(upperRight, Direction.S));
        assertEquals(Optional.of(new Cell(Board.COLUMNS - 2, 0)), freeBoardTest.directionNeighbor(upperRight, Direction.W));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(upperRight, Direction.NE));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(upperRight, Direction.SE));
        assertEquals(Optional.of(new Cell(Board.COLUMNS - 2, 1)), freeBoardTest.directionNeighbor(upperRight, Direction.SW));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(upperRight, Direction.NW));
    }

    @Test
    public void neighborOfUpperLeftCornerCorrect() {
        Cell upperLeft = new Cell(0, 0);

        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(upperLeft, Direction.N));
        assertEquals(Optional.of(new Cell(1, 0)), freeBoardTest.directionNeighbor(upperLeft, Direction.E));
        assertEquals(Optional.of(new Cell(0, 1)), freeBoardTest.directionNeighbor(upperLeft, Direction.S));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(upperLeft, Direction.W));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(upperLeft, Direction.NE));
        assertEquals(Optional.of(new Cell(1, 1)), freeBoardTest.directionNeighbor(upperLeft, Direction.SE));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(upperLeft, Direction.SW));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(upperLeft, Direction.NW));
    }

    @Test
    public void neighborOfLowerRightCornerCorrect() {
        Cell lowerRight = new Cell(Board.COLUMNS - 1, Board.ROWS - 1);

        assertEquals(Optional.of(new Cell(Board.COLUMNS - 1, Board.ROWS - 2)), freeBoardTest.directionNeighbor(lowerRight, Direction.N));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(lowerRight, Direction.E));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(lowerRight, Direction.S));
        assertEquals(Optional.of(new Cell(Board.COLUMNS - 2, Board.ROWS - 1)), freeBoardTest.directionNeighbor(lowerRight, Direction.W));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(lowerRight, Direction.NE));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(lowerRight, Direction.SE));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(lowerRight, Direction.SW));
        assertEquals(Optional.of(new Cell(Board.COLUMNS - 2, Board.ROWS - 2)), freeBoardTest.directionNeighbor(lowerRight, Direction.NW));
    }

    @Test
    public void neighborOfLowerLeft() {
        Cell lowerLeft = new Cell(0, Board.ROWS - 1);

        assertEquals(Optional.of(new Cell(0, Board.ROWS - 2)), freeBoardTest.directionNeighbor(lowerLeft, Direction.N));
        assertEquals(Optional.of(new Cell(1, Board.ROWS - 1)), freeBoardTest.directionNeighbor(lowerLeft, Direction.E));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(lowerLeft, Direction.S));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(lowerLeft, Direction.W));
        assertEquals(Optional.of(new Cell(1, Board.ROWS - 2)), freeBoardTest.directionNeighbor(lowerLeft, Direction.NE));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(lowerLeft, Direction.SE));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(lowerLeft, Direction.SW));
        assertEquals(Optional.empty(), freeBoardTest.directionNeighbor(lowerLeft, Direction.NW));
    }
}