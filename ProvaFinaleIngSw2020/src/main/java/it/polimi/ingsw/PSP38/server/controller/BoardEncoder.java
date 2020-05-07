package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.common.BytesForBoard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.LinkedList;
import java.util.List;

public class BoardEncoder {
    public static List<Byte> bytesForBoard(Board board){

        List<Byte> bytesForBoard = new LinkedList<>();
        bytesForBoard.add((byte)Board.ROWS);
        bytesForBoard.add((byte)Board.COLUMNS);
        for(int row = 0; row < Board.ROWS; ++row){
            for(int col = 0; col < Board.COLUMNS; ++col){
                Cell cell = board.cellAt(col, row);
                byte b = byteForCell(cell);
                if(board.getWorkersPositions().containsKey(cell)){
                    b += byteForWorker(board.getWorkersPositions().get(cell));
                }

            bytesForBoard.add(b);
            }
        }

        return bytesForBoard;
    }

    private static byte byteForCell(Cell cell){
        byte byteForCell;
        switch(cell.getTowerHeight()){
            case 0:
                byteForCell = 0;
                break;
            case 1:
                byteForCell = BytesForBoard.TOWER_1;
                break;
            case 2:
                byteForCell = BytesForBoard.TOWER_2;
                break;
            case 3:
                byteForCell = BytesForBoard.TOWER_3;
                break;
            default:
                throw new IllegalArgumentException("tower too high");
        }

        if(cell.hasDome()){
            byteForCell += BytesForBoard.DOME;
        }

        return byteForCell;
    }

    private static byte byteForWorker(Worker worker) {
        switch(worker.getColor()){
            case RED:
                return BytesForBoard.WORKER_RED;
            case PURPLE:
                return BytesForBoard.WORKER_PURPLE;
            case YELLOW:
                return BytesForBoard.WORKER_YELLOW;
            default:
                throw new IllegalArgumentException("impossible color");
        }
    }
}
