package it.polimi.ingsw.PSP38.model;

import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    Cell cellTest = new Cell(0,0);
    Cell cellWitDomeTest = cellTest.withDome();
    Worker workerTest = new Worker(WorkerColor.YELLOW, cellTest);

    @Test(expected = NullPointerException.class)
    public void ConstructorWithoutColour(){
        new Worker(null, cellTest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ConstructorWithoutCell(){
        new Worker(WorkerColor.RED, cellWitDomeTest);
    }

    @Test
    public void getCell() {
        assertEquals(cellTest, workerTest.getPosition());
    }

    @Test
    public void getColor() {
        assertEquals(WorkerColor.YELLOW, workerTest.getColor());
    }
}