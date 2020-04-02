package it.polimi.ingsw.PSP38.debug;

import it.polimi.ingsw.PSP38.model.Board;
import it.polimi.ingsw.PSP38.model.Cell;

public final class BoardPrinter {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void printBoard(Board board){
        printRowsSeparators();
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLUMNS; ++col) {
                System.out.print("|");
                printTopCell(board.cellAt(row, col));
                System.out.print("|");
            }
            System.out.println();
            for (int col = 0; col < Board.COLUMNS; ++col) {
                System.out.print("|");
                printUpperMiddleCell(board.cellAt(row, col));
                System.out.print("|");
            }
            System.out.println();
            for (int col = 0; col < Board.COLUMNS; ++col) {
                System.out.print("|");
                printLowerMiddleCell(board.cellAt(row, col));
                System.out.print("|");
            }
            System.out.println();
            for (int col = 0; col < Board.COLUMNS; ++col) {
                System.out.print("|");
                printBottomCell(board.cellAt(row, col));
                System.out.print("|");
            }
            printRowsSeparators();
        }
    }

    private static void printRowsSeparators(){
        System.out.println();
        for(int i = 0; i < Board.COLUMNS; ++i){
            System.out.print(" -- -- -- -- ");
        }
        System.out.println();
    }

    private static void printTopCell(Cell cell){
        if(cell.hasDome() && cell.getTowerHeight() == 3){
            printDome();
        } else {
            printFree();
        }
    }

    private static void printUpperMiddleCell(Cell cell){
            switch(cell.getTowerHeight()){
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

    private static void printLowerMiddleCell(Cell cell){
        switch(cell.getTowerHeight()){
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

    private static void printBottomCell(Cell cell){
        switch(cell.getTowerHeight()){
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
