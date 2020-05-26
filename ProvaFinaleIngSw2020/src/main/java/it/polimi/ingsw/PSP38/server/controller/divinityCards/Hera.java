package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.model.Cell;

public class Hera extends DivinityCard {
    @Override
    public boolean blockOpponentWinningCondition(Cell currentPosition) {
        return currentPosition.isOnPerimeter();
    }
}
