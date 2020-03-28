package it.polimi.ingsw.PSP038.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DomeTest {
    @Test(expected=IllegalArgumentException.class)
    public void constructorRejectsDome() {
        new Dome(new Dome(new Cell(0, 0)));
    }

    @Test(expected=NullPointerException.class)
    public void constructorRejectsNull() {
        new Dome(null);
    }

    @Test
    public void hasDomeTrue(){
        ICell cell = new Cell(0, 0);
        assertTrue(new Dome(cell).hasDome());
        ICell towerBlock1 = new TowerBlock(cell);
        assertTrue(new Dome(towerBlock1).hasDome());
        ICell towerBlock2 = new TowerBlock(towerBlock1);
        assertTrue(new Dome(towerBlock2).hasDome());
        ICell towerBlock3 = new TowerBlock(towerBlock2);
        assertTrue(new Dome(towerBlock3).hasDome());
    }
}