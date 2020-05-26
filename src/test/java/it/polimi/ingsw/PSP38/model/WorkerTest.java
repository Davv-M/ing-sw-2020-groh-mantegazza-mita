package it.polimi.ingsw.PSP38.model;

import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    Cell cellTest = new Cell(0,0);
    Worker workerTest = new Worker(WorkerColor.BLUE, cellTest);

    @Test
    public void getCell() {
        assertEquals(cellTest, workerTest.getPosition());
    }

    @Test
    public void getColor() {
        assertEquals(WorkerColor.BLUE, workerTest.getColor());
    }
}