package it.polimi.ingsw.PSP038.debug;

import it.polimi.ingsw.PSP038.model.Board;

public class Main {
    /*public static void main(String[] args){
        Board board = Board.withFreeCells();
        BoardPrinter.printBoard(board);
        board = board.withCell(new TowerBlock(new Cell(2, 2)));
        BoardPrinter.printBoard(board);
        board = board.withCell(cell(0, 1, 3, true));
        BoardPrinter.printBoard(board);
        ICell worker = new Worker(cell(0, 1, 3, true), Worker.Color.RED);

        System.out.println(((Worker) worker).color());
    }

    private static ICell cell(int x, int y, int height, boolean hasDome) {
        if(hasDome){
            return new Dome(cell(x, y, height, false));
        } else if (height == 0){
            return new Cell(x, y);
        } else {
            return new TowerBlock(cell(x, y, height - 1, false));
        }
    }*/
}
