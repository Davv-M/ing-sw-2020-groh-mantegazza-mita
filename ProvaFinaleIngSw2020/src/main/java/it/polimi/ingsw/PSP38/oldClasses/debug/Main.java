package it.polimi.ingsw.PSP38.oldClasses.debug;

import it.polimi.ingsw.PSP38.client.BoardPainter;
import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;
import it.polimi.ingsw.PSP38.server.controller.BoardEncoder;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        Cell cell1 = new Cell(0, 1, 3, false);
        Cell cell2 = new Cell(1, 1, 2, true);
        Cell cell3 = new Cell(3, 4, 1, false);
        Cell cell4 = new Cell(3, 4, 1, false);

        board = board.withCell(cell1);
        board = board.withCell(cell2);
        board = board.withCell(cell3);
        board = board.withWorker(new Worker(WorkerColor.PURPLE, cell1));
        board = board.withWorker(new Worker(WorkerColor.RED, cell4));
        board = board.withWorker(new Worker(WorkerColor.YELLOW, cell3));

        BoardPainter.printBoard(BoardEncoder.bytesForBoard(board));
    }

}
