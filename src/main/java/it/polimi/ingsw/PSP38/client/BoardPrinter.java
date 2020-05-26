package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.WorkerColor;

import java.util.Iterator;
import java.util.List;

public class BoardPrinter {
    private static int boardRows;
    private static int boardColumns;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String UNICODE_PERSON = "\ud80c\udc4c";

    public static void printBoard(List<Byte> encodedBoard) {
        boardRows = encodedBoard.remove(0);
        boardColumns = encodedBoard.remove(0);
        printRowsSeparators();
        for (int row = 0; row < boardRows; ++row) {
            System.out.print("|");
            for (int col = 0; col < boardColumns; ++col) {
                printFree();
                System.out.print("|");
            }
            System.out.println();
            printRowTopLayer(encodedBoard, row);
            printRowUpperMidLayer(encodedBoard, row);
            printRowLowerMidLayer(encodedBoard, row);
            printRowBottomLayer(encodedBoard, row);
            printRowsSeparators();
        }
    }

    private static void printRowsSeparators() {
        for (int i = 0; i < boardColumns; ++i) {
            System.out.print(" -- -- -- --");
        }
        System.out.println();
    }


    private static void printRowTopLayer(List<Byte> encodedBoard, int row) {
        System.out.print("|");
        for (int col = 0; col < boardColumns; ++col) {
            byte encodedCell = encodedBoard.get(rowMajorIndex(col, row));
            if (BoardDecoder.towerHeight(encodedCell) == 3) {
                if (BoardDecoder.hasDome(encodedCell)) {
                    printDome();
                } else if (BoardDecoder.hasWorker(encodedCell)) {
                    printWorker(BoardDecoder.workerColor(encodedCell));
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

    private static void printRowUpperMidLayer(List<Byte> encodedBoard, int row) {
        System.out.print("|");
        for (int col = 0; col < boardColumns; ++col) {
            byte encodedCell = encodedBoard.get(rowMajorIndex(col, row));

            switch (BoardDecoder.towerHeight(encodedCell)) {
                case 3:
                    printTowerTop();
                    break;
                case 2:
                    if (BoardDecoder.hasDome(encodedCell)) {
                        printDome();
                    } else if (BoardDecoder.hasWorker(encodedCell)) {
                        printWorker(BoardDecoder.workerColor(encodedCell));
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

    private static void printRowLowerMidLayer(List<Byte> encodedBoard, int row) {
        System.out.print("|");
        for (int col = 0; col < boardColumns; ++col) {
            byte encodedCell = encodedBoard.get(rowMajorIndex(col, row));

            switch (BoardDecoder.towerHeight(encodedCell)) {
                case 3:
                case 2:
                    printTowerMiddle();
                    break;
                case 1:
                    if (BoardDecoder.hasDome(encodedCell)) {
                        printDome();
                    } else if (BoardDecoder.hasWorker(encodedCell)) {
                        printWorker(BoardDecoder.workerColor(encodedCell));
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

    private static void printRowBottomLayer(List<Byte> encodedBoard, int row) {
        System.out.print("|");
        for (int col = 0; col < boardColumns; ++col) {
            byte encodedCell = encodedBoard.get(rowMajorIndex(col, row));

            switch (BoardDecoder.towerHeight(encodedCell)) {
                case 3:
                case 2:
                case 1:
                    printTowerBottom();
                    break;
                case 0:
                    if (BoardDecoder.hasDome(encodedCell)) {
                        printDome();
                    } else if (BoardDecoder.hasWorker(encodedCell)) {
                        printWorker(BoardDecoder.workerColor(encodedCell));
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

    private static void printWorker(WorkerColor color) {
        String workerColor;
        switch(color){
            case BLACK:
                workerColor = ANSI_RED_BACKGROUND;
                break;
            case WHITE:
                workerColor = ANSI_PURPLE_BACKGROUND;
                break;
            case BLUE:
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

    private static int rowMajorIndex(int x, int y) {
        return (y * 5) + x;
    }
}
