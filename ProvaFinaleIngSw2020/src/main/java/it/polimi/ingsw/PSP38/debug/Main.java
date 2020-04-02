package it.polimi.ingsw.PSP38.debug;

import it.polimi.ingsw.PSP38.model.Board;
import it.polimi.ingsw.PSP38.model.Cell;
import it.polimi.ingsw.PSP38.model.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Board board = new Board();
        Scanner s = new Scanner(System.in);
        while(true){
            System.out.println("insert coordinate x :");
            int x = s.nextInt();
            System.out.println("insert coordinate y :");
            int y = s.nextInt();

            Cell cell = board.cellAt(x, y);
            Cell newCell = buildOn(cell);
            board = board.withCell(newCell);

            BoardPrinter.printBoard(board);
        }

    }

    private static Cell buildOn(Cell cell){
        switch(cell.getTowerHeight()){
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
