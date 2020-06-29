package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

/**
 * Concrete implementation of Athena's power, extends <code>DivinityCard</code> abstract class.
 *
 * @author Maximilien Groh (10683107)
 */

public class Athena extends DivinityCard {
    private boolean hasMovedUp = false;

    @Override
    public Board move(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        Board updatedBoard = super.move(worker, destinationCell, currentBoard);
        hasMovedUp = updatedBoard.heightOf(destinationCell) > currentBoard.heightOf(worker.getPosition());
        return updatedBoard;
    }

    @Override
    public void checkOpponentMove(WorkerAction action, Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if(action == WorkerAction.MOVE && hasMovedUp && currentBoard.heightOf(worker.getPosition()) < currentBoard.heightOf(destinationCell)){
            throw new IllegalArgumentException("You can't move on that cell");
        }
    }
}
