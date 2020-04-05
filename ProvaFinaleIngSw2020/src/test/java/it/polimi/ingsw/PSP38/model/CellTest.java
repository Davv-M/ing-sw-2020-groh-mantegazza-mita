package it.polimi.ingsw.PSP38.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {

    Cell cellTest = new Cell(0,0);
    Cell cellTest1= new Cell(1,1,2,true);

    @Test
    public void withTowerHeight() {
        Cell cellTestHeight = cellTest.withTowerHeight(3);
        assertEquals(3, cellTestHeight.getTowerHeight());
        assertFalse(cellTestHeight.hasDome());
    }

    @Test
    public void withDome() {
        Cell cellTestDome = cellTest.withDome();
        assertTrue(cellTestDome.hasDome());
    }

    @Test
    public void withDomeAndHeight() {
        Cell cellTestDomeAndHeight = cellTest.withTowerHeight(3).withDome();
        assertEquals(3, cellTestDomeAndHeight.getTowerHeight());
        assertTrue(cellTestDomeAndHeight.hasDome());
    }

    @Test
    public void withTowerHeight1() {
        Cell cellTestHeight = cellTest1.withTowerHeight(1);
        assertEquals(1, cellTestHeight.getTowerHeight());
        assertTrue(cellTestHeight.hasDome());
    }

    @Test
    public void withDome1() {
        Cell cellTestDome = cellTest1.withDome();
        assertTrue(cellTestDome.hasDome());
    }

    @Test
    public void withDomeAndHeight1() {
        Cell cellTestDomeAndHeight = cellTest1.withTowerHeight(3).withDome();
        assertEquals(3, cellTestDomeAndHeight.getTowerHeight());
        assertTrue(cellTestDomeAndHeight.hasDome());
    }

}