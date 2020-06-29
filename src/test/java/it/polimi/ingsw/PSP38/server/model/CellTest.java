package it.polimi.ingsw.PSP38.server.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class CellTest {
    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionNegativeX(){
        new Cell(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionNegativeY(){
        new Cell(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionOutOfBoundsColumns(){
        new Cell(Board.COLUMNS, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionOutOfBoundsRows(){
        new Cell(0, Board.ROWS);
    }

    @Test
    public void getX(){
        Cell cell = new Cell(1, 2);
        assertEquals(1, cell.getX());
    }

    @Test
    public void getY(){
        Cell cell = new Cell(1, 2);
        assertEquals(2, cell.getY());
    }

    @Test
    public void rowMajorIndex() {
        Cell c0 = new Cell(0,0);
        Cell c5 = new Cell(0, 1);
        Cell c4 = new Cell(4, 0);
        Cell c24 = new Cell(4, 4);

        assertEquals(0, c0.rowMajorIndex());
        assertEquals(5, c5.rowMajorIndex());
        assertEquals(4, c4.rowMajorIndex());
        assertEquals(24, c24.rowMajorIndex());
    }

    @Test
    public void isOnPerimeter() {
        Cell cell;
        for(int row = 0; row < Board.ROWS; row++){
            for(int col = 0; col < Board.COLUMNS; col++){
                cell = new Cell(col, row);
                if(col == 0 || col == Board.COLUMNS - 1 || row == 0 || row  == Board.ROWS -1){
                    assertTrue(cell.isOnPerimeter());
                } else {
                    assertFalse(cell.isOnPerimeter());
                }
            }
        }
    }

    @Test
    public void equalsCorrect() {
        Cell c1 = new Cell(0,0);
        Cell c2 = new Cell(0,1);
        Cell c4 = new Cell(1,0);
        Cell c3 = new Cell(0,0);
        assertNotEquals(c1, c2);
        assertNotEquals(c1, c4);
        assertNotEquals(c4, c2);
        assertEquals(c1, c3);
    }

    @Test
    public void hashCodeCellsConsistentWithEquals() {
        for (int row = 0; row < Board.ROWS; row++) {
            for(int col = 0; col < Board.COLUMNS; col++){
                Cell c = new Cell(col, row);
                Cell c1 = new Cell(col, row);
                if (c.equals(c1)) {
                    assertEquals(c.hashCode(), c1.hashCode());
                }
            }
        }
    }
}