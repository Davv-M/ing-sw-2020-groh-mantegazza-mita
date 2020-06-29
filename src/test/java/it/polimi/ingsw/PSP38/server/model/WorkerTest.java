package it.polimi.ingsw.PSP38.server.model;

import it.polimi.ingsw.PSP38.common.WorkerColor;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    Cell cellTest = new Cell(0,0);
    Worker workerTest = new Worker(WorkerColor.BLUE, cellTest);

    @Test(expected = NullPointerException.class)
    public void constructorThrowsExceptionNullPosition(){
        new Worker(WorkerColor.BLUE, null);
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsExceptionNullColor(){
        new Worker(null, cellTest);
    }

    @Test
    public void getCell() {
        assertEquals(cellTest, workerTest.getPosition());
    }

    @Test
    public void getColor() {
        assertEquals(WorkerColor.BLUE, workerTest.getColor());
    }

    @Test
    public void withPosition(){
        assertEquals(workerTest, workerTest.withPosition(null));
        assertEquals(new Cell(1, 3), workerTest.withPosition(new Cell(1, 3)).getPosition());
    }

    @Test
    public void equalsCorrect() {
        Worker w1 = new Worker(WorkerColor.BLUE, new Cell(0, 0));
        Worker w2 = new Worker(WorkerColor.BLACK, new Cell(0, 0));
        Worker w3 = new Worker(WorkerColor.WHITE, new Cell(0, 0));
        Worker w4 = new Worker(WorkerColor.WHITE, new Cell(0, 1));

        assertEquals(w1, workerTest);
        assertNotEquals(w2, workerTest);
        assertNotEquals(w3, workerTest);
        assertNotEquals(w4, w3);
    }

    @Test
    public void hashCodeWorkersConsistentWithEquals() {
        for (int row = 0; row < Board.ROWS; row++) {
            for(int col = 0; col < Board.COLUMNS; col++){
                for(WorkerColor color : WorkerColor.values()){
                    Worker w = new Worker(color, new Cell(col, row));
                    Worker w1 = new Worker(color, new Cell(col, row));
                    if(w.equals(w1)){
                        assertEquals(w.hashCode(), w1.hashCode());
                    }
                }
            }
        }
    }
}