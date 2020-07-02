package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.BytesForBoard;
import it.polimi.ingsw.PSP38.common.WorkerColor;

/**
 * This class includes methods used to decode the status of the cells of the board. For details regarding the encoding
 * of the board's status, refer to the documentation of <code>BoardEncoder</code> class
 *
 * @author Maximilien Groh (10683107)
 */
public class BoardDecoder {

    /**
     * Method used to check if it's possible to move a worker or build something onto a given cell of the board
     *
     * @param b represents the encoded cell that will be checked
     * @return true if the cell is not occupied by a worker and no dome has been built on it, false otherwise
     */
    public static boolean isFree(byte b) {
        return !(hasWorker(b) || hasDome(b));
    }

    /**
     * Method used to check if the given cell has a dome built on it
     *
     * @param b represents the encoded cell that will be checked
     * @return true if the cell has a dome on it, false otherwise
     */
    public static boolean hasDome(byte b) {
        return isAndEqual(b, BytesForBoard.DOME);
    }

    /**
     * Method used to check if the given cell is occupied by a worker
     *
     * @param b represents the encoded cell that will be checked
     * @return true if a worker of any color is stationing on it, false otherwise
     */
    public static boolean hasWorker(byte b) {
        return isAndEqual(b, BytesForBoard.WORKER_WHITE)
                || isAndEqual(b, BytesForBoard.WORKER_BLACK)
                || isAndEqual(b, BytesForBoard.WORKER_BLUE);
    }

    /**
     * Method used to determine the color of a worker
     *
     * @param b represents a generic encoded worker
     * @return the color of the encoded worker
     * @throws IllegalArgumentException if the code doesn't correspond to one of the defined colors
     */
    public static WorkerColor workerColor(byte b) throws IllegalArgumentException {
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

    /**
     * Method used to determine the height of a given tower
     *
     * @param b represents a generic encoded tower
     * @return the height of the tower represented by <code>b</code>
     */
    public static int towerHeight(byte b) {
        if (isAndEqual(b, BytesForBoard.TOWER_1)) {
            return 1;
        } else if (isAndEqual(b, BytesForBoard.TOWER_2)) {
            return 2;
        } else if (isAndEqual(b, BytesForBoard.TOWER_3)) {
            return 3;
        } else {
            return 0;
        }
    }

    private static boolean isAndEqual(byte byteToCheck, byte flag) {
        return (byteToCheck & flag) == flag;
    }
}
