package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.BytesForBoard;
import it.polimi.ingsw.PSP38.common.WorkerColor;

public class BoardDecoder {

    public static boolean isFree(byte b) {
        return !(hasWorker(b) || hasDome(b));
    }

    public static boolean hasDome(byte b) {
        return isAndEqual(b, BytesForBoard.DOME);
    }

    public static boolean hasWorker(byte b) {
        return isAndEqual(b, BytesForBoard.WORKER_WHITE)
                || isAndEqual(b, BytesForBoard.WORKER_BLACK)
                || isAndEqual(b, BytesForBoard.WORKER_BLUE);
    }

    public static WorkerColor workerColor(byte b) {
        if (isAndEqual(b, BytesForBoard.WORKER_WHITE)) {
            return WorkerColor.WHITE;
        } else if (isAndEqual(b, BytesForBoard.WORKER_BLACK)) {
            return WorkerColor.BLACK;
        } else if (isAndEqual(b, BytesForBoard.WORKER_BLUE)) {
            return WorkerColor.BLUE;
        } else {
            throw new IllegalArgumentException("color unknown");
        }
    }

    public static int towerHeight(byte b) {
        if(isAndEqual(b, BytesForBoard.TOWER_1)){
            return 1;
        } else if(isAndEqual(b, BytesForBoard.TOWER_2)){
            return 2;
        } else if(isAndEqual(b, BytesForBoard.TOWER_3)){
            return 3;
        } else{
            return 0;
        }
    }

    private static boolean isAndEqual(byte byteToCheck, byte flag){
        return (byteToCheck & flag) == flag;
    }
}
