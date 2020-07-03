package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.BytesForBoard;
import it.polimi.ingsw.PSP38.common.WorkerColor;
import org.junit.Test;


import static org.junit.Assert.*;

public class BoardDecoderTest {
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

    @Test
    public void isFree() {
        assertTrue(BoardDecoder.isFree(empty0));
        assertTrue(BoardDecoder.isFree(empty1));
        assertTrue(BoardDecoder.isFree(empty2));
        assertTrue(BoardDecoder.isFree(empty3));

        assertFalse(BoardDecoder.isFree(blueWorker0));
        assertFalse(BoardDecoder.isFree(blueWorker1));
        assertFalse(BoardDecoder.isFree(blueWorker2));
        assertFalse(BoardDecoder.isFree(blueWorker3));

        assertFalse(BoardDecoder.isFree(blackWorker0));
        assertFalse(BoardDecoder.isFree(blackWorker1));
        assertFalse(BoardDecoder.isFree(blackWorker2));
        assertFalse(BoardDecoder.isFree(blackWorker3));

        assertFalse(BoardDecoder.isFree(whiteWorker0));
        assertFalse(BoardDecoder.isFree(whiteWorker1));
        assertFalse(BoardDecoder.isFree(whiteWorker2));
        assertFalse(BoardDecoder.isFree(whiteWorker3));

        assertFalse(BoardDecoder.isFree(dome0));
        assertFalse(BoardDecoder.isFree(dome1));
        assertFalse(BoardDecoder.isFree(dome2));
        assertFalse(BoardDecoder.isFree(dome3));
    }

    @Test
    public void hasDome() {
        assertFalse(BoardDecoder.hasDome(empty0));
        assertFalse(BoardDecoder.hasDome(empty1));
        assertFalse(BoardDecoder.hasDome(empty2));
        assertFalse(BoardDecoder.hasDome(empty3));

        assertFalse(BoardDecoder.hasDome(blueWorker0));
        assertFalse(BoardDecoder.hasDome(blueWorker1));
        assertFalse(BoardDecoder.hasDome(blueWorker2));
        assertFalse(BoardDecoder.hasDome(blueWorker3));

        assertFalse(BoardDecoder.hasDome(blackWorker0));
        assertFalse(BoardDecoder.hasDome(blackWorker1));
        assertFalse(BoardDecoder.hasDome(blackWorker2));
        assertFalse(BoardDecoder.hasDome(blackWorker3));

        assertFalse(BoardDecoder.hasDome(whiteWorker0));
        assertFalse(BoardDecoder.hasDome(whiteWorker1));
        assertFalse(BoardDecoder.hasDome(whiteWorker2));
        assertFalse(BoardDecoder.hasDome(whiteWorker3));

        assertTrue(BoardDecoder.hasDome(dome0));
        assertTrue(BoardDecoder.hasDome(dome1));
        assertTrue(BoardDecoder.hasDome(dome2));
        assertTrue(BoardDecoder.hasDome(dome3));
    }

    @Test
    public void hasWorker() {
        assertFalse(BoardDecoder.hasWorker(empty0));
        assertFalse(BoardDecoder.hasWorker(empty1));
        assertFalse(BoardDecoder.hasWorker(empty2));
        assertFalse(BoardDecoder.hasWorker(empty3));

        assertTrue(BoardDecoder.hasWorker(blueWorker0));
        assertTrue(BoardDecoder.hasWorker(blueWorker1));
        assertTrue(BoardDecoder.hasWorker(blueWorker2));
        assertTrue(BoardDecoder.hasWorker(blueWorker3));

        assertTrue(BoardDecoder.hasWorker(blackWorker0));
        assertTrue(BoardDecoder.hasWorker(blackWorker1));
        assertTrue(BoardDecoder.hasWorker(blackWorker2));
        assertTrue(BoardDecoder.hasWorker(blackWorker3));

        assertTrue(BoardDecoder.hasWorker(whiteWorker0));
        assertTrue(BoardDecoder.hasWorker(whiteWorker1));
        assertTrue(BoardDecoder.hasWorker(whiteWorker2));
        assertTrue(BoardDecoder.hasWorker(whiteWorker3));

        assertFalse(BoardDecoder.hasWorker(dome0));
        assertFalse(BoardDecoder.hasWorker(dome1));
        assertFalse(BoardDecoder.hasWorker(dome2));
        assertFalse(BoardDecoder.hasWorker(dome3));
    }

    @Test
    public void workerColor() {
        assertEquals(WorkerColor.BLUE, BoardDecoder.workerColor(blueWorker0));
        assertEquals(WorkerColor.BLUE, BoardDecoder.workerColor(blueWorker1));
        assertEquals(WorkerColor.BLUE, BoardDecoder.workerColor(blueWorker2));
        assertEquals(WorkerColor.BLUE, BoardDecoder.workerColor(blueWorker3));

        assertEquals(WorkerColor.WHITE, BoardDecoder.workerColor(whiteWorker0));
        assertEquals(WorkerColor.WHITE, BoardDecoder.workerColor(whiteWorker1));
        assertEquals(WorkerColor.WHITE, BoardDecoder.workerColor(whiteWorker2));
        assertEquals(WorkerColor.WHITE, BoardDecoder.workerColor(whiteWorker3));

        assertEquals(WorkerColor.BLACK, BoardDecoder.workerColor(blackWorker0));
        assertEquals(WorkerColor.BLACK, BoardDecoder.workerColor(blackWorker1));
        assertEquals(WorkerColor.BLACK, BoardDecoder.workerColor(blackWorker2));
        assertEquals(WorkerColor.BLACK, BoardDecoder.workerColor(blackWorker3));
    }

    @Test
    public void towerHeight() {
        assertEquals(0, BoardDecoder.towerHeight(empty0));
        assertEquals(1, BoardDecoder.towerHeight(empty1));
        assertEquals(2, BoardDecoder.towerHeight(empty2));
        assertEquals(3, BoardDecoder.towerHeight(empty3));

        assertEquals(0, BoardDecoder.towerHeight(dome0));
        assertEquals(1, BoardDecoder.towerHeight(dome1));
        assertEquals(2, BoardDecoder.towerHeight(dome2));
        assertEquals(3, BoardDecoder.towerHeight(dome3));

        assertEquals(0, BoardDecoder.towerHeight(blueWorker0));
        assertEquals(1, BoardDecoder.towerHeight(blueWorker1));
        assertEquals(2, BoardDecoder.towerHeight(blueWorker2));
        assertEquals(3, BoardDecoder.towerHeight(blueWorker3));

        assertEquals(0, BoardDecoder.towerHeight(whiteWorker0));
        assertEquals(1, BoardDecoder.towerHeight(whiteWorker1));
        assertEquals(2, BoardDecoder.towerHeight(whiteWorker2));
        assertEquals(3, BoardDecoder.towerHeight(whiteWorker3));

        assertEquals(0, BoardDecoder.towerHeight(blackWorker0));
        assertEquals(1, BoardDecoder.towerHeight(blackWorker1));
        assertEquals(2, BoardDecoder.towerHeight(blackWorker2));
        assertEquals(3, BoardDecoder.towerHeight(blackWorker3));
    }
}