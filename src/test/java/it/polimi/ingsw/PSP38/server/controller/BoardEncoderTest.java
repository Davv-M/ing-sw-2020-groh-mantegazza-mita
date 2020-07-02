package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.common.BytesForBoard;
import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Tower;
import it.polimi.ingsw.PSP38.server.model.Worker;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class BoardEncoderTest {

    @Test
    public void bytesForBoard() {
        HashSet<Worker> workers = new HashSet<>();
        HashSet<Tower> towers = new HashSet<>();
        HashSet<Cell> domes = new HashSet<>();

        Cell cellEmpty1 = new Cell(1, 0);
        Cell cellEmpty2 = new Cell(2, 0);
        Cell cellEmpty3 = new Cell(3, 0);

        Tower towerEmpty1 = new Tower(cellEmpty1, 1);
        Tower towerEmpty2 = new Tower(cellEmpty2, 2);
        Tower towerEmpty3 = new Tower(cellEmpty3, 3);

        Cell cellDome0 = new Cell(4, 0);
        Cell cellDome1 = new Cell(0, 1);
        Cell cellDome2 = new Cell(1, 1);
        Cell cellDome3 = new Cell(2, 1);

        Tower towerDome1 = new Tower(cellDome1, 1);
        Tower towerDome2 = new Tower(cellDome2, 2);
        Tower towerDome3 = new Tower(cellDome3, 3);

        Cell cellBlueWorker0 = new Cell(3, 1);
        Cell cellBlueWorker1 = new Cell(4, 1);
        Cell cellBlueWorker2 = new Cell(0, 2);
        Cell cellBlueWorker3 = new Cell(1, 2);

        Worker blue0 = new Worker(WorkerColor.BLUE, cellBlueWorker0);
        Worker blue1 = new Worker(WorkerColor.BLUE, cellBlueWorker1);
        Worker blue2 = new Worker(WorkerColor.BLUE, cellBlueWorker2);
        Worker blue3 = new Worker(WorkerColor.BLUE, cellBlueWorker3);

        Tower towerBlueWorker1 = new Tower(cellBlueWorker1, 1);
        Tower towerBlueWorker2 = new Tower(cellBlueWorker2, 2);
        Tower towerBlueWorker3 = new Tower(cellBlueWorker3, 3);

        Cell cellBlackWorker0 = new Cell(2, 2);
        Cell cellBlackWorker1 = new Cell(3, 2);
        Cell cellBlackWorker2 = new Cell(4, 2);
        Cell cellBlackWorker3 = new Cell(0, 3);

        Worker black0 = new Worker(WorkerColor.BLACK, cellBlackWorker0);
        Worker black1 = new Worker(WorkerColor.BLACK, cellBlackWorker1);
        Worker black2 = new Worker(WorkerColor.BLACK, cellBlackWorker2);
        Worker black3 = new Worker(WorkerColor.BLACK, cellBlackWorker3);

        Tower towerBlackWorker1 = new Tower(cellBlackWorker1, 1);
        Tower towerBlackWorker2 = new Tower(cellBlackWorker2, 2);
        Tower towerBlackWorker3 = new Tower(cellBlackWorker3, 3);

        Cell cellWhiteWorker0 = new Cell(1, 3);
        Cell cellWhiteWorker1 = new Cell(2, 3);
        Cell cellWhiteWorker2 = new Cell(3, 3);
        Cell cellWhiteWorker3 = new Cell(4, 3);

        Worker white0 = new Worker(WorkerColor.WHITE, cellWhiteWorker0);
        Worker white1 = new Worker(WorkerColor.WHITE, cellWhiteWorker1);
        Worker white2 = new Worker(WorkerColor.WHITE, cellWhiteWorker2);
        Worker white3 = new Worker(WorkerColor.WHITE, cellWhiteWorker3);

        Tower towerWhiteWorker1 = new Tower(cellWhiteWorker1, 1);
        Tower towerWhiteWorker2 = new Tower(cellWhiteWorker2, 2);
        Tower towerWhiteWorker3 = new Tower(cellWhiteWorker3, 3);

        workers.add(blue0);
        workers.add(blue1);
        workers.add(blue2);
        workers.add(blue3);

        workers.add(black0);
        workers.add(black1);
        workers.add(black2);
        workers.add(black3);

        workers.add(white0);
        workers.add(white1);
        workers.add(white2);
        workers.add(white3);

        towers.add(towerEmpty1);
        towers.add(towerEmpty2);
        towers.add(towerEmpty3);

        towers.add(towerDome1);
        towers.add(towerDome2);
        towers.add(towerDome3);

        towers.add(towerBlueWorker1);
        towers.add(towerBlueWorker2);
        towers.add(towerBlueWorker3);

        towers.add(towerBlackWorker1);
        towers.add(towerBlackWorker2);
        towers.add(towerBlackWorker3);

        towers.add(towerWhiteWorker1);
        towers.add(towerWhiteWorker2);
        towers.add(towerWhiteWorker3);

        domes.add(cellDome0);
        domes.add(cellDome1);
        domes.add(cellDome2);
        domes.add(cellDome3);

        Board board = new Board(workers, towers, domes);
        List<Byte> bytesForBoard = BoardEncoder.bytesForBoard(board);
        byte rowsByte = Board.ROWS;
        byte columnsByte = Board.COLUMNS;
        byte empty0 = 0;
        byte empty1 = BytesForBoard.TOWER_1;
        byte empty2 = BytesForBoard.TOWER_2;
        byte empty3 = BytesForBoard.TOWER_3;

        byte dome0 = BytesForBoard.DOME;
        byte dome1 = BytesForBoard.DOME + BytesForBoard.TOWER_1;
        byte dome2 = BytesForBoard.DOME + BytesForBoard.TOWER_2;
        byte dome3 = BytesForBoard.DOME + BytesForBoard.TOWER_3;

        byte blueWorker0 = BytesForBoard.WORKER_BLUE;
        byte blueWorker1 = BytesForBoard.WORKER_BLUE + BytesForBoard.TOWER_1;
        byte blueWorker2 = BytesForBoard.WORKER_BLUE + BytesForBoard.TOWER_2;
        byte blueWorker3 = BytesForBoard.WORKER_BLUE + BytesForBoard.TOWER_3;

        byte blackWorker0 = BytesForBoard.WORKER_BLACK;
        byte blackWorker1 = BytesForBoard.WORKER_BLACK + BytesForBoard.TOWER_1;
        byte blackWorker2 = BytesForBoard.WORKER_BLACK + BytesForBoard.TOWER_2;
        byte blackWorker3 = BytesForBoard.WORKER_BLACK + BytesForBoard.TOWER_3;

        byte whiteWorker0 = BytesForBoard.WORKER_WHITE;
        byte whiteWorker1 = BytesForBoard.WORKER_WHITE + BytesForBoard.TOWER_1;
        byte whiteWorker2 = BytesForBoard.WORKER_WHITE + BytesForBoard.TOWER_2;
        byte whiteWorker3 = BytesForBoard.WORKER_WHITE + BytesForBoard.TOWER_3;
        List<Byte> correctBytesForBoard = Arrays.asList(rowsByte, columnsByte,
                empty0, empty1, empty2, empty3, dome0,
                dome1, dome2, dome3, blueWorker0, blueWorker1,
                blueWorker2, blueWorker3, blackWorker0, blackWorker1, blackWorker2,
                blackWorker3, whiteWorker0, whiteWorker1, whiteWorker2, whiteWorker3,
                empty0, empty0, empty0, empty0, empty0);

        assertEquals(correctBytesForBoard, bytesForBoard);
    }
}