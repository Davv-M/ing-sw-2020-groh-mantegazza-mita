package it.polimi.ingsw.PSP38.model;


import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class BoardTest {

        Board freeBoardTest = new Board();
        Cell cellTest = new Cell(0,0,3,false);

    @Test
    public void boardWithoutTower() {
        for (int r = 0; r < Board.ROWS; r++){
            for (int c = 0; c < Board.COLUMNS; c++){
                assertFalse(freeBoardTest.cellAt(r,c).hasDome());
                assertEquals(0, freeBoardTest.cellAt(r,c).getTowerHeight());
            }
        }
    }

    @Test
    public void cellAt(){
        Board boardWithCellTest = freeBoardTest.withCell(cellTest);
        assertEquals(cellTest, boardWithCellTest.cellAt(0,0));

    }

    @Test
    public void withCell(){
        Board boardWithCellTest = freeBoardTest.withCell(cellTest);
        assertEquals(3, boardWithCellTest.cellAt(0,0).getTowerHeight());
    }

    @Test
    public void directionNeighbor() {

        assertTrue(freeBoardTest.directionNeighbor(freeBoardTest.cellAt(0,0), Direction.N).isEmpty());
        assertTrue(freeBoardTest.directionNeighbor(freeBoardTest.cellAt(0,0), Direction.W).isEmpty());
        assertTrue(freeBoardTest.directionNeighbor(freeBoardTest.cellAt(0,0), Direction.NW).isEmpty());
        assertTrue(freeBoardTest.directionNeighbor(freeBoardTest.cellAt(0,0), Direction.NE).isEmpty());
        assertEquals(freeBoardTest.cellAt(1,2),freeBoardTest.directionNeighbor(freeBoardTest.cellAt(2,2), Direction.N).get());
        assertEquals(freeBoardTest.cellAt(1,1),freeBoardTest.directionNeighbor(freeBoardTest.cellAt(2,2), Direction.NW).get());
        assertEquals(freeBoardTest.cellAt(2,1),freeBoardTest.directionNeighbor(freeBoardTest.cellAt(2,2), Direction.W).get());
        assertEquals(freeBoardTest.cellAt(3,1),freeBoardTest.directionNeighbor(freeBoardTest.cellAt(2,2), Direction.SW).get());
        assertEquals(freeBoardTest.cellAt(3,2),freeBoardTest.directionNeighbor(freeBoardTest.cellAt(2,2), Direction.S).get());
        assertEquals(freeBoardTest.cellAt(3,3),freeBoardTest.directionNeighbor(freeBoardTest.cellAt(2,2), Direction.SE).get());
        assertEquals(freeBoardTest.cellAt(2,3),freeBoardTest.directionNeighbor(freeBoardTest.cellAt(2,2), Direction.E).get());
        assertEquals(freeBoardTest.cellAt(1,3),freeBoardTest.directionNeighbor(freeBoardTest.cellAt(2,2), Direction.NE).get());

    }

    @Test
    public void neighborsCells() {
        List<Cell> listCellTest = new ArrayList<>();
        listCellTest.add(new Cell(1,2));
        listCellTest.add(new Cell(1,3));
        listCellTest.add(new Cell(2,3));
        listCellTest.add(new Cell(3,3));
        listCellTest.add(new Cell(3,2));
        listCellTest.add(new Cell(3,1));
        listCellTest.add(new Cell(2,1));
        listCellTest.add(new Cell(1,1));
        assertEquals(listCellTest.toString(), freeBoardTest.neighborsCells(freeBoardTest.cellAt(2,2)).toString());
        List<Cell> listCellTest1 = new ArrayList<>();
        listCellTest1.add(new Cell(0,1));
        listCellTest1.add(new Cell(1,1));
        listCellTest1.add(new Cell(1,0));
        assertEquals(listCellTest1.toString(), freeBoardTest.neighborsCells(freeBoardTest.cellAt(0,0)).toString());
        assertEquals(3,freeBoardTest.neighborsCells(freeBoardTest.cellAt(0,0)).size());
        assertEquals(3,freeBoardTest.neighborsCells(freeBoardTest.cellAt(0,4)).size());
        assertEquals(3,freeBoardTest.neighborsCells(freeBoardTest.cellAt(4,0)).size());
        assertEquals(3,freeBoardTest.neighborsCells(freeBoardTest.cellAt(4,4)).size());

    }
}