package it.polimi.ingsw.PSP038.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TowerBlockTest {
    ICell freeCell = new Cell(0,0);
    ICell tower1 = new TowerBlock(freeCell);
    ICell tower2 = new TowerBlock(tower1);
    ICell tower3 = new TowerBlock(tower2);

    @Test(expected=IllegalArgumentException.class)
    public void constructorRejectsDome() {
        new TowerBlock(new Dome(freeCell));
    }

    @Test(expected=NullPointerException.class)
    public void constructorRejectsNull() {
        new TowerBlock(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorRejectsTowerBlockWithMaxHeight() {
        new TowerBlock(tower3);
    }

    @Test
    public void heightCorrect() {
        assertEquals(1, tower1.height());
        assertEquals(2, tower2.height());
        assertEquals(3, tower3.height());
    }
}