package it.polimi.ingsw.PSP38.server.controller.divinityStrategies;

import it.polimi.ingsw.PSP38.server.controller.StrategyDivinityCard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of Artemis' power, extends <code>StrategyDivinityCard</code> interface.
 * @author Davide Mantegazza (10568661)
 * @version 1.1
 */
public class StrategyArtemis implements StrategyDivinityCard {
    private boolean isSecondMove=false;
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

    @Override
    public Board move(Worker worker, Cell destinationCell, Board oldBoard) {
        setSecondMove();
        return oldBoard.moveWorker(worker, destinationCell);
    }

    /**
     * Getter method for <code>isSecondMove</code> parameter
     * @return the current value of <code>isSecondMove</code>
     */
    public boolean isSecondMove() {
        return isSecondMove;
    }

    /**
     * This method inverts the state of the variable <code>isSecondMove</code>
     */
    public void setSecondMove() {
        isSecondMove = !isSecondMove;
    }
}
