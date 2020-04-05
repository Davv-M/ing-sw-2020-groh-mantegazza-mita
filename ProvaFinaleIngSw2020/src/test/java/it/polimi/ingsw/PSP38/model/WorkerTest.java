package it.polimi.ingsw.PSP38.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    Cell cellTest = new Cell(0,0);
    Cell cellWitDomeTest = cellTest.withDome();
    Worker workerTest = new Worker(Worker.Color.YELLOW, cellTest);

    @Test(expected = NullPointerException.class)
    public void ConstructorWithoutColour(){
        new Worker(null, cellTest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConstructorWithoutCell(){
        new Worker(Worker.Color.RED, cellWitDomeTest);
    }

    @Test
    public void getCell() {
        assertEquals(cellTest, workerTest.getCell());
    }

    @Test
    public void getColor() {
        assertEquals(Worker.Color.YELLOW, workerTest.getColor());
    }
}