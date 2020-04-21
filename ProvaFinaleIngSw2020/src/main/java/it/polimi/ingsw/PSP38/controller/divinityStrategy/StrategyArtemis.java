package it.polimi.ingsw.PSP38.controller.divinityStrategy;

import it.polimi.ingsw.PSP38.controller.StrategyDivinityCard;
import it.polimi.ingsw.PSP38.model.*;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of Artemis' power, extends <code>StrategyDivinityCard</code> interface.
 * @author Davide Mantegazza (10568661)
 * @version 1.0
 */
public class StrategyArtemis implements StrategyDivinityCard {
    private boolean isSecondMove;
    /**
     * Returns a list of cells where the given worker can move
     *
     * @param worker       the worker that has to be moved
     * @param currentBoard the board currently used in the game
     */
    @Override
    public List<Cell> preMove(Worker worker, Board currentBoard) {
        List<Cell> neighborCells = currentBoard.neighborsCells(worker.getPosition());
        Cell startPosition=worker.getPosition();
        Map<Cell, Worker> workersPositions = currentBoard.getWorkersPositions();
        //Removes all cells containing workers or domes or cells with tower height > cell.towerHeight + 1
        if(isSecondMove==true) {
            neighborCells.removeIf(c -> workersPositions.containsKey(c) || c.hasDome() || c.getTowerHeight() >
                    worker.getPosition().getTowerHeight() + 1||c.equals(startPosition));
        }
        else{
            neighborCells.removeIf(c -> workersPositions.containsKey(c) || c.hasDome() || c.getTowerHeight() >
                    worker.getPosition().getTowerHeight() + 1);

        }
        return neighborCells;
    }

    /**
     * Getter method for <code>isSecondMove</code> parameter
     * @return the current value of <code>isSecondMove</code>
     */
    public boolean isSecondMove() {
        return isSecondMove;
    }

    /**
     * Setter method for <code>isSecondMove</code>
     * @param secondMove Wanted value for <code>isSecondMove</code>
     */
    public void setSecondMove(boolean secondMove) {
        isSecondMove = secondMove;
    }
}
