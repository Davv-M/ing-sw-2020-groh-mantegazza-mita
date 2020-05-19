package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Tower;
import it.polimi.ingsw.PSP38.server.model.Worker;
import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.controller.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ArtemisTest {
    HashSet<Worker> workers = new HashSet<>();
    HashSet<Tower> towers = new HashSet<>();
    HashSet<Cell> domes = new HashSet<>();

    @Test(expected = IllegalArgumentException.class)
    public void moveInACellOccupiedByAPlayer() {
        Cell cellw1 = new Cell(2, 2);
        Worker w1 = new Worker(WorkerColor.WHITE, cellw1);
        Cell cellw2 = new Cell(1, 2);
        Worker w2 = new Worker(WorkerColor.WHITE, cellw2);
        Cell cellt1 = new Cell(2, 3);
        Tower t1 = new Tower(cellt1, 2);
        workers.add(w1);
        workers.add(w2);
        towers.add(t1);
        Board testBoard = new Board(workers, towers, domes);
        Artemis a = new Artemis();
        a.move(w1, cellw2, testBoard);
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveInACellOccupiedByATower() {
        Cell cellw1 = new Cell(2, 2);
        Worker w1 = new Worker(WorkerColor.WHITE, cellw1);
        Cell cellw2 = new Cell(1, 2);
        Worker w2 = new Worker(WorkerColor.WHITE, cellw2);
        Cell cellt1 = new Cell(2, 3);
        Tower t1 = new Tower(cellt1, 2);
        workers.add(w1);
        workers.add(w2);
        towers.add(t1);
        Board testBoard = new Board(workers, towers, domes);
        Artemis a = new Artemis();
        a.move(w1, cellt1, testBoard);
    }

    @Test
    public void checkCorrectValueOfPreviousPosition() {
        Cell cellw1 = new Cell(2, 2);
        Worker w1 = new Worker(WorkerColor.WHITE, cellw1);
        Cell cellw2 = new Cell(1, 2);
        Worker w2 = new Worker(WorkerColor.WHITE, cellw2);
        Cell cellt1 = new Cell(2, 3);
        Tower t1 = new Tower(cellt1, 2);
        Cell cellf1 = new Cell(2, 1);
        workers.add(w1);
        workers.add(w2);
        towers.add(t1);
        Board testBoard = new Board(workers, towers, domes);
        Artemis a = new Artemis();
        a.move(w1, cellf1, testBoard);
        assertEquals(2, a.getPreviousPosition().getX());
        assertEquals(2, a.getPreviousPosition().getY());
    }

    @Test
    public void checkCorrectWorkerPositionAfterMove() {
        Board testBoard;
        Board updatedBoard;
        Cell cellw1 = new Cell(2, 2);
        Worker w1 = new Worker(WorkerColor.WHITE, cellw1);
        Cell cellw2 = new Cell(1, 2);
        Worker w2 = new Worker(WorkerColor.WHITE, cellw2);
        Cell cellt1 = new Cell(2, 3);
        Tower t1 = new Tower(cellt1, 2);
        Cell cellf1 = new Cell(2, 1);
        workers.add(w1);
        workers.add(w2);
        towers.add(t1);
        testBoard = new Board(workers, towers, domes);
        Artemis a = new Artemis();
        updatedBoard = a.move(w1, cellf1, testBoard);
        assertEquals(2, updatedBoard.getWorkersPositions().get(cellf1).getPosition().getX());
        assertEquals(1, updatedBoard.getWorkersPositions().get(cellf1).getPosition().getY());
    }

    @Test (expected = IllegalArgumentException.class)
    public void moveInPreviouslyOccupiedCell(){
        Board testBoard;
        Board updatedBoard;
        Board updatedBoard2;
        Cell cellw1 = new Cell(2, 2);
        Worker w1 = new Worker(WorkerColor.WHITE, cellw1);
        Cell cellw2 = new Cell(1, 2);
        Worker w2 = new Worker(WorkerColor.WHITE, cellw2);
        Cell cellt1 = new Cell(2, 3);
        Tower t1 = new Tower(cellt1, 2);
        Cell cellf1 = new Cell(2, 1);
        workers.add(w1);
        workers.add(w2);
        towers.add(t1);
        testBoard = new Board(workers, towers, domes);
        Artemis a = new Artemis();
        updatedBoard = a.move(w1, cellf1, testBoard);
        updatedBoard2= a.optionalAction(w1, cellw1, updatedBoard);
    }

    @Test
    public void checkOptionalSecondMove(){
        Board testBoard;
        Board updatedBoard;
        Board updatedBoard2;
        Cell cellw1 = new Cell(2, 2);
        Worker w1 = new Worker(WorkerColor.WHITE, cellw1);
        Cell cellw2 = new Cell(1, 2);
        Worker w2 = new Worker(WorkerColor.WHITE, cellw2);
        Cell cellt1 = new Cell(2, 3);
        Tower t1 = new Tower(cellt1, 2);
        Cell cellf1 = new Cell(2, 1);
        Cell cellf2 = new Cell(1,3);
        workers.add(w1);
        workers.add(w2);
        towers.add(t1);
        testBoard = new Board(workers, towers, domes);
        Artemis a = new Artemis();
        updatedBoard = a.move(w1, cellf1, testBoard);
        updatedBoard2= a.optionalAction(w1, cellf2, updatedBoard);
        assertEquals(1, updatedBoard2.getWorkersPositions().get(cellf2).getPosition().getX());
        assertEquals(3, updatedBoard2.getWorkersPositions().get(cellf2).getPosition().getY());
    }
}