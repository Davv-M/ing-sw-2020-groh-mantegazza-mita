package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;

/**
 * Concrete implementation of Pan's power, extends <code>StrategyDivinityCard</code> interface.
 *
 * @author Maximilien Groh (10683107)
 */

public class Pan extends DivinityCard {
    @Override
    public boolean isWinner(Board board, Cell previousPosition, Cell currentPosition) {
        return super.isWinner(board, previousPosition, currentPosition) ||
                board.heightOf(currentPosition) <= board.heightOf(previousPosition) - 2;
    }
}
