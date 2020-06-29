package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Tower;
import it.polimi.ingsw.PSP38.server.model.Worker;

/**
 * Concrete implementation of Zeus' power, extends <code>StrategyDivinityCard</code> interface.
 *
 * @author Maximilien Groh (10683107)
 */

public class Zeus extends DivinityCard {
    @Override
    public void checkBuild(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if (worker.getPosition().equals(destinationCell)) {
            if (currentBoard.heightOf(destinationCell) == Tower.MAX_HEIGHT) {
                throw new IllegalArgumentException("You can't build a dome on this cell");
            }
        } else {
            super.checkBuild(worker, destinationCell, currentBoard);
        }
    }
}
