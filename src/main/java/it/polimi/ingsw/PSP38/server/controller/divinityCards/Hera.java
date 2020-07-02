package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.model.Cell;

/**
 * Concrete implementation of Hera's power, extends <code>DivinityCard</code> abstract class.
 *
 * @author Maximilien Groh (10683107)
 */

public class Hera extends DivinityCard {
    @Override
    public boolean blockOpponentWinningCondition(Cell currentPosition) {
        return currentPosition.isOnPerimeter();
    }

    @Override
    public String toString() {
        return "Hera";
    }
}
