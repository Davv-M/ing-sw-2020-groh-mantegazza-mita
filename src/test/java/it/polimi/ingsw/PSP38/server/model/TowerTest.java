package it.polimi.ingsw.PSP38.server.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TowerTest {

    @Test
    public void constantsAreCorrect() {
        assertEquals(3, Tower.MAX_HEIGHT);
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsExceptionNullPosition(){
        new Tower(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionNegativeHeight(){
        new Tower(new Cell(0,0), -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsExceptionHeightOutOfBounds(){
        new Tower(new Cell(0,0), 4);
    }

    @Test
    public void getPosition(){
        Tower tower = new Tower(new Cell(0, 0), 1);
        assertEquals(new Cell(0, 0), tower.getPosition());
    }

    @Test
    public void getHeight(){
        Tower tower = new Tower(new Cell(0, 0), 1);
        assertEquals(1, tower.getHeight());
    }

    @Test
    public void equalsCorrect() {
        Tower t1 = new Tower(new Cell(0, 0), 1);
        Tower t2 = new Tower(new Cell(0, 0), 2);
        Tower t3 = new Tower(new Cell(0, 0), 3);
        Tower t4 = new Tower(new Cell(0, 0), 1);
        Tower t5 = new Tower(new Cell(0, 1), 1);

        assertEquals(t1, t4);
        assertNotEquals(t1, t2);
        assertNotEquals(t1, t3);
        assertNotEquals(t3, t2);
        assertNotEquals(t4, t5);
    }

    @Test
    public void hashCodeTowersConsistentWithEquals() {
        for (int row = 0; row < Board.ROWS; row++) {
            for(int col = 0; col < Board.COLUMNS; col++){
                for(int h = 1; h < Tower.MAX_HEIGHT; h++){
                    Tower t = new Tower(new Cell(col, row), h);
                    Tower t1 = new Tower(new Cell(col, row), h);
                    if(t.equals(t1)){
                        assertEquals(t.hashCode(), t1.hashCode());
                    }
                }
            }
        }
    }
}