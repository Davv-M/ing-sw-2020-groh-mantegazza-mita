package it.polimi.ingsw.PSP038.debug;

import it.polimi.ingsw.PSP038.model.Board;
import it.polimi.ingsw.PSP038.model.Player;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.cellAt(3,4).addLevel();

        board.cellAt(2,3).addLevel();
        board.cellAt(2,3).addLevel();

        board.cellAt(0,0).addLevel();
        board.cellAt(0,0).addLevel();
        board.cellAt(0,0).addLevel();
        board.cellAt(0,0).addDome();

        board.cellAt(2,2).addDome();

        Player player = new Player("Pippo01");
        player.createWorkers();
        player.getWorker1().setPosition(1,1);

        BoardPrinter.printBoard(board);

    }

}
