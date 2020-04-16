/*
package it.polimi.ingsw.PSP38.debug;

import it.polimi.ingsw.PSP38.model.Board;
import it.polimi.ingsw.PSP38.model.Cell;
import it.polimi.ingsw.PSP38.model.Player;
import it.polimi.ingsw.PSP38.model.Worker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        board = board.withCell(new Cell(0, 1, 3, false));
        board = board.withCell(new Cell(1, 1, 3, true));
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("insert coordinate x :");
            int x = s.nextInt();
            System.out.println("insert coordinate y :");
            int y = s.nextInt();

            Cell cell = board.cellAt(x, y);
            board = board.withWorker(new Worker(Worker.Color.RED, cell), cell);

            BoardPrinter.printBoard(board);
        }

    }

    private static Cell buildOn(Cell cell) {
        switch (cell.getTowerHeight()) {
            case 0:
            case 1:
            case 2:
                return cell.withTowerHeight(cell.getTowerHeight() + 1);
            case 3:
            default:
                return cell.withDome();
        }
    }

}
*/
