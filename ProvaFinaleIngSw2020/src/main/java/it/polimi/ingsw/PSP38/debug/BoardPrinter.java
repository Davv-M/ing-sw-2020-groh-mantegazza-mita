package it.polimi.ingsw.PSP38.debug;

import it.polimi.ingsw.PSP38.model.Board;
import it.polimi.ingsw.PSP38.model.Cell;
import it.polimi.ingsw.PSP38.model.Worker;

public final class BoardPrinter {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String UNICODE_PERSON = "\ud80c\udc4c";

    public static void printBoard(Board board) {
        printRowsSeparators();
        for (int row = 0; row < Board.ROWS; ++row) {
            System.out.print("|");
            for (int col = 0; col < Board.COLUMNS; ++col) {
                printFree();
                System.out.print("|");
            }
            System.out.println();
            printRowTopLayer(board, row);
            printRowUpperMidLayer(board, row);
            printRowLowerMidLayer(board, row);
            printRowBottomLayer(board, row);
            printRowsSeparators();
        }
    }

    private static void printRowsSeparators() {
        for (int i = 0; i < Board.COLUMNS; ++i) {
            System.out.print(" -- -- -- --");
        }
        System.out.println();
    }


    private static void printRowTopLayer(Board board, int row) {
        System.out.print("|");
        for (int col = 0; col < Board.COLUMNS; ++col) {
            Cell cell = board.cellAt(col, row);

            if (cell.getTowerHeight() == 3) {
                if (cell.hasDome()) {
                    printDome();
                } else if (board.getWorkersPositions().containsKey(cell)) {
                    printWorker(board.getWorkersPositions().get(cell));
                } else {
                    printFree();
                }
            } else {
                printFree();
            }
            System.out.print("|");
        }
        System.out.println();
    }

    private static void printRowUpperMidLayer(Board board, int row) {
        System.out.print("|");
        for (int col = 0; col < Board.COLUMNS; ++col) {
            Cell cell = board.cellAt(col, row);

            switch (cell.getTowerHeight()) {
                case 3:
                    printTowerTop();
                    break;
                case 2:
                    if (cell.hasDome()) {
                        printDome();
                    } else if (board.getWorkersPositions().containsKey(cell)) {
                        printWorker(board.getWorkersPositions().get(cell));
                    } else {
                        printFree();
                    }
                    break;
                default:
                    printFree();
            }

            System.out.print("|");
        }
        System.out.println();
    }

    private static void printRowLowerMidLayer(Board board, int row) {
        System.out.print("|");
        for (int col = 0; col < Board.COLUMNS; ++col) {
            Cell cell = board.cellAt(col, row);

            switch (cell.getTowerHeight()) {
                case 3:
                case 2:
                    printTowerMiddle();
                    break;
                case 1:
                    if (cell.hasDome()) {
                        printDome();
                    } else if (board.getWorkersPositions().containsKey(cell)) {
                        printWorker(board.getWorkersPositions().get(cell));
                    } else {
                        printFree();
                    }
                    break;
                default:
                    printFree();
            }
            System.out.print("|");
        }
        System.out.println();
    }

    private static void printRowBottomLayer(Board board, int row) {
        System.out.print("|");
        for (int col = 0; col < Board.COLUMNS; ++col) {
            Cell cell = board.cellAt(col, row);

            switch (cell.getTowerHeight()) {
                case 3:
                case 2:
                case 1:
                    printTowerBottom();
                    break;
                case 0:
                    if (cell.hasDome()) {
                        printDome();
                    } else if (board.getWorkersPositions().containsKey(cell)) {
                        printWorker(board.getWorkersPositions().get(cell));
                    } else {
                        printFree();
                    }
            }
            System.out.print("|");
        }
        System.out.println();
    }

    private static void printDome() {
        System.out.print(ANSI_GREEN_BACKGROUND + "   " + ANSI_RESET);
        System.out.print(ANSI_BLUE_BACKGROUND + "OOOOO" + ANSI_RESET);
        System.out.print(ANSI_GREEN_BACKGROUND + "   " + ANSI_RESET);
    }

    private static void printWorker(Worker worker) {
        String workerColor;
        switch(worker.getColor()){
            case RED:
                workerColor = ANSI_RED_BACKGROUND;
                break;
            case PURPLE:
                workerColor = ANSI_PURPLE_BACKGROUND;
                break;
            case YELLOW:
                workerColor = ANSI_YELLOW_BACKGROUND;
                break;
            default:
                workerColor = ANSI_GREEN_BACKGROUND;

        }
        System.out.print(ANSI_GREEN_BACKGROUND + "     " + ANSI_RESET);
        System.out.print(workerColor + UNICODE_PERSON + ANSI_RESET);
        System.out.print(ANSI_GREEN_BACKGROUND + "     " + ANSI_RESET);
    }

    private static void printTowerTop() {
        System.out.print(ANSI_GREEN_BACKGROUND + "  " + ANSI_RESET);
        System.out.print(ANSI_WHITE_BACKGROUND + "XXXXXXX" + ANSI_RESET);
        System.out.print(ANSI_GREEN_BACKGROUND + "  " + ANSI_RESET);
    }

    private static void printTowerMiddle() {
        System.out.print(ANSI_GREEN_BACKGROUND + "  " + ANSI_RESET);
        System.out.print(ANSI_WHITE_BACKGROUND + "XXXXXXX" + ANSI_RESET);
        System.out.print(ANSI_GREEN_BACKGROUND + "  " + ANSI_RESET);
    }

    private static void printTowerBottom() {
        System.out.print(ANSI_GREEN_BACKGROUND + "  " + ANSI_RESET);
        System.out.print(ANSI_WHITE_BACKGROUND + "XXXXXXX" + ANSI_RESET);
        System.out.print(ANSI_GREEN_BACKGROUND + "  " + ANSI_RESET);
    }

    private static void printFree() {
        System.out.print(ANSI_GREEN_BACKGROUND + "           " + ANSI_RESET);
    }
}
