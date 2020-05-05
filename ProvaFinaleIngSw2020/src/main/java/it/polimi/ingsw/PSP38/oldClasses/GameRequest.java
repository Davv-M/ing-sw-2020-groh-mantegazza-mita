/*package it.polimi.ingsw.PSP38.oldClasses;

import it.polimi.ingsw.PSP38.oldClasses.debug.BoardPrinter;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Player;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GameRequest {


    public Object printBoard;

    public int[] requestCoordinates(String message, Player player){
        Scanner scanner = new Scanner(System.in);
        int[] coordinates = new int[2];
        boolean incorrectCoordinates = (coordinates[0] < 0 || coordinates[0] >= Board.COLUMNS) || (coordinates[1] < 0 || coordinates[1] >= Board.ROWS);
        do {
            System.out.println(player.getNickname() + message);
            String s = scanner.next();
            try {
                String[] inputs = s.split(",");
                coordinates[0] = Integer.parseInt(inputs[0]);
                coordinates[1]= Integer.parseInt(inputs[1]);
            } catch (InputMismatchException e) {
                System.out.println("Please insert an integers coordinates");
            }
            if (incorrectCoordinates)
                System.out.println(" please "+ player.getNickname() +" remeber column between: 0 and "+(Board.COLUMNS-1) +" & row between: 0 and "+ (Board.ROWS-1));
        } while (incorrectCoordinates);

        return coordinates;
    }

    public void showPossibleCells(String message, Player player, List<Cell> possibleCells){
        System.out.println(player.getNickname() + message);
        for ( Cell c: possibleCells){
            System.out.println(c);
        }

    }

    public void printBoard(Board currentBoard) {
        BoardPrinter.printBoard(currentBoard);
    }
}*/
