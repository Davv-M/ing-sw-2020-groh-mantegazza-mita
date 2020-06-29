package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.common.BytesForBoard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.LinkedList;
import java.util.List;

/**
 * Class used to encode the board information to be sent from server to client.
 *
 * @author Maximilien Groh (10683107)
 */

public class BoardEncoder {

    /**
     * Encodes the board into a list of bytes.
     *
     * @param board the board to encode
     * @return the encoded board
     */

    public static List<Byte> bytesForBoard(Board board){
        List<Byte> bytesForBoard = new LinkedList<>();
        bytesForBoard.add((byte)Board.ROWS);
        bytesForBoard.add((byte)Board.COLUMNS);
        for(int row = 0; row < Board.ROWS; ++row){
            for(int col = 0; col < Board.COLUMNS; ++col){
                Cell cell = new Cell(col, row);
                byte b = byteForTower(board.heightOf(cell));
                if(board.hasDomeAt(cell)){
                    b+= BytesForBoard.DOME;
                }
                else if(board.hasWorkerAt(cell)){
                    b += byteForWorker(board.getWorkersPositions().get(cell));
                }

            bytesForBoard.add(b);
            }
        }

        return bytesForBoard;
    }

    private static byte byteForTower(int cellHeight){
        switch(cellHeight){
            case 0:
                return 0;
            case 1:
                return BytesForBoard.TOWER_1;
            case 2:
                return BytesForBoard.TOWER_2;
            case 3:
                return BytesForBoard.TOWER_3;
            default:
                throw new IllegalArgumentException("tower too high");
        }
    }

    private static byte byteForWorker(Worker worker) {
        switch(worker.getColor()){
            case BLACK:
                return BytesForBoard.WORKER_BLACK;
            case WHITE:
                return BytesForBoard.WORKER_WHITE;
            case BLUE:
                return BytesForBoard.WORKER_BLUE;
            default:
                throw new IllegalArgumentException("impossible color");
        }
    }
}
