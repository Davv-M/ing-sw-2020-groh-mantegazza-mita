package it.polimi.ingsw.PSP038.debug;

import it.polimi.ingsw.PSP038.model.Board;
import it.polimi.ingsw.PSP038.model.Cell;
import it.polimi.ingsw.PSP038.model.ICell;

public final class BoardPrinter {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void printBoard(Board board){
        printRowsSeparators();
        for (int row = 0; row < Cell.ROWS; ++row) {
            for (int col = 0; col < Cell.COLUMNS; ++col) {
                System.out.print("|");
                printTopCell(board.cellAt(col, row));
                System.out.print("|");
            }
            System.out.println();
            for (int col = 0; col < Cell.COLUMNS; ++col) {
                System.out.print("|");
                printUpperMiddleCell(board.cellAt(col, row));
                System.out.print("|");
            }
            System.out.println();
            for (int col = 0; col < Cell.COLUMNS; ++col) {
                System.out.print("|");
                printLowerMiddleCell(board.cellAt(col, row));
                System.out.print("|");
            }
            System.out.println();
            for (int col = 0; col < Cell.COLUMNS; ++col) {
                System.out.print("|");
                printBottomCell(board.cellAt(col, row));
                System.out.print("|");
            }
            printRowsSeparators();
        }
    }

    private static void printRowsSeparators(){
        System.out.println();
        for(int i = 0; i < Cell.COLUMNS; ++i){
            System.out.print(" -- -- -- -- ");
        }
        System.out.println();
    }

    private static void printTopCell(ICell cell){
        if(cell.hasDome() && cell.height() == 3){
            printDome();
        } else {
            printFree();
        }
    }

    private static void printUpperMiddleCell(ICell cell){
            switch(cell.height()){
                case 3:
                    printTowerBlock();
                    break;
                case 2:
                    if(cell.hasDome()){
                        printDome();
                    } else {
                        printFree();
                    }
                    break;
                default:
                    printFree();
            }
    }

    private static void printLowerMiddleCell(ICell cell){
        switch(cell.height()){
            case 3:
            case 2:
                printTowerBlock();
                break;
            case 1:
                if(cell.hasDome()){
                    printDome();
                } else {
                    printFree();
                }
                break;
            default:
                printFree();
        }
    }

    private static void printBottomCell(ICell cell){
        switch(cell.height()){
            case 3:
            case 2:
            case 1:
                printTowerBlock();
                break;
            case 0:
                if(cell.hasDome()){
                    printDome();
                } else {
                    printFree();
                }
        }
    }

    private static void printDome(){
        System.out.print(ANSI_GREEN_BACKGROUND + "   " + ANSI_RESET);
        System.out.print(ANSI_BLUE_BACKGROUND + "OOOOO" + ANSI_RESET);
        System.out.print(ANSI_GREEN_BACKGROUND + "   " + ANSI_RESET);
    }

    private static void printTowerBlock(){
        System.out.print(ANSI_GREEN_BACKGROUND + "  " + ANSI_RESET);
        System.out.print(ANSI_WHITE_BACKGROUND + "XXXXXXX" + ANSI_RESET);
        System.out.print(ANSI_GREEN_BACKGROUND + "  " + ANSI_RESET);
    }

    private static void printFree(){
        System.out.print(ANSI_GREEN_BACKGROUND + "           " + ANSI_RESET);
    }
}
