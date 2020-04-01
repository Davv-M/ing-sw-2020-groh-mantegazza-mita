package it.polimi.ingsw.PSP038.debug;

import it.polimi.ingsw.PSP038.model.*;

import java.util.Scanner;

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

    public static void main(String[] args) {

        Board b = Board.withFreeCells();

        int i=0;
        while(i != 0) {
            Scanner s = new Scanner(System.in);
            System.out.println(" inserisci coordinata x : ");
            int x = s.nextInt();
            System.out.println(" inserisci coordinata y : ");
            int y = s.nextInt();
            ICell cell = b.cellAt(x, y);
            ICell newCell = build(cell);

            b = Board.withCell(newCell);
        }
    }

    public static ICell build(ICell cell){
        switch(cell.height()){
            case 0 :
            case 1 :
            case 2 :
                return new TowerBlock(cell);
            case 3 :
            default :
                return new Dome(cell);
        }
    }



}
