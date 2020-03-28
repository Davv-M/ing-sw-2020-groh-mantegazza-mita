package it.polimi.ingsw.PSP038.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {

    @Test(expected=IllegalArgumentException.class)
    public void constructorRejectsOutOfBoundX() {
        new Cell(0, Cell.COLUMNS);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorRejectsNegativeX() {
        new Cell(-1, 0);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorRejectsOutOfBoundY() {
        new Cell(0, Cell.ROWS);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorRejectsNegativeY() {
        new Cell(-1, 0);
    }

    @Test
    public void heightCorrect(){
        Cell cell = new Cell(0, 0);
        assertEquals(0, cell.height());
    }

    @Test
    public void hasDomeCorrect(){
        Cell cell = new Cell(0, 0);
        assertFalse(cell.hasDome());
    }
}