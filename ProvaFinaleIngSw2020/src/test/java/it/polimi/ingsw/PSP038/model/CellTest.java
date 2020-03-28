package it.polimi.ingsw.PSP038.model;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class CellTest {

    @Test
    public void neighborOfUpperRightCornerCorrect() {
        Cell upperRight = new Cell(Cell.COLUMNS - 1, 0);

        assertEquals(Optional.empty(), upperRight.neighbor(Direction.N));
        assertEquals(Optional.empty(), upperRight.neighbor(Direction.E));
        assertEquals(Optional.of(new Cell(Cell.COLUMNS - 1, 1)), upperRight.neighbor(Direction.S));
        assertEquals(Optional.of(new Cell(Cell.COLUMNS - 2, 0)), upperRight.neighbor(Direction.W));
        assertEquals(Optional.empty(), upperRight.neighbor(Direction.NE));
        assertEquals(Optional.empty(), upperRight.neighbor(Direction.SE));
        assertEquals(Optional.of(new Cell(Cell.COLUMNS - 2, 1)), upperRight.neighbor(Direction.SW));
        assertEquals(Optional.empty(), upperRight.neighbor(Direction.NW));

    }
    @Test
    public void neighborOfUpperLeftCornerCorrect() {
        Cell upperLeft = new Cell(0, 0);

        assertEquals(Optional.empty(), upperLeft.neighbor(Direction.N));
        assertEquals(Optional.of(new Cell(1, 0)), upperLeft.neighbor(Direction.E));
        assertEquals(Optional.of(new Cell(0, 1)), upperLeft.neighbor(Direction.S));
        assertEquals(Optional.empty(), upperLeft.neighbor(Direction.W));
        assertEquals(Optional.empty(), upperLeft.neighbor(Direction.NE));
        assertEquals(Optional.of(new Cell(1, 1)), upperLeft.neighbor(Direction.SE));
        assertEquals(Optional.empty(), upperLeft.neighbor(Direction.SW));
        assertEquals(Optional.empty(), upperLeft.neighbor(Direction.NW));
    }

    @Test
    public void neighborOfLowerRightCornerCorrect() {
        Cell lowerRight = new Cell(Cell.COLUMNS - 1, Cell.ROWS - 1);

        assertEquals(Optional.of(new Cell(Cell.COLUMNS - 1, Cell.ROWS - 2)), lowerRight.neighbor(Direction.N));
        assertEquals(Optional.empty(), lowerRight.neighbor(Direction.E));
        assertEquals(Optional.empty(), lowerRight.neighbor(Direction.S));
        assertEquals(Optional.of(new Cell(Cell.COLUMNS - 2, Cell.ROWS - 1)), lowerRight.neighbor(Direction.W));
        assertEquals(Optional.empty(), lowerRight.neighbor(Direction.NE));
        assertEquals(Optional.empty(), lowerRight.neighbor(Direction.SE));
        assertEquals(Optional.empty(), lowerRight.neighbor(Direction.SW));
        assertEquals(Optional.of(new Cell(Cell.COLUMNS - 2, Cell.ROWS - 2)), lowerRight.neighbor(Direction.NW));
    }

    @Test
    public void neighborOfLowerLeft() {
        Cell lowerLeft = new Cell(0, Cell.ROWS - 1);

        assertEquals(Optional.of(new Cell(0, Cell.ROWS - 2)), lowerLeft.neighbor(Direction.N));
        assertEquals(Optional.of(new Cell(1, Cell.ROWS - 1)), lowerLeft.neighbor(Direction.E));
        assertEquals(Optional.empty(), lowerLeft.neighbor(Direction.S));
        assertEquals(Optional.empty(), lowerLeft.neighbor(Direction.W));
        assertEquals(Optional.of(new Cell(1, Cell.ROWS - 2)), lowerLeft.neighbor(Direction.NE));
        assertEquals(Optional.empty(), lowerLeft.neighbor(Direction.SE));
        assertEquals(Optional.empty(), lowerLeft.neighbor(Direction.SW));
        assertEquals(Optional.empty(), lowerLeft.neighbor(Direction.NW));
    }

    @Test
    public void equalsCorrect() {
        Cell c1 = new Cell(0, 0);
        Cell c2 = new Cell(0, 1);
        Cell c4 = new Cell(1, 0);
        Cell c3 = new Cell(0, 0);
        assertFalse(c1.equals(c2));
        assertFalse(c1.equals(c4));
        assertFalse(c4.equals(c2));
        assertTrue(c1.equals(c3));
    }
}