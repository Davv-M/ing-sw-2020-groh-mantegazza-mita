package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.OptionalAction;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Tower;
import it.polimi.ingsw.PSP38.server.model.Worker;
import static it.polimi.ingsw.PSP38.common.utilities.ArgumentChecker.*;

import java.util.List;

/**
 * Concrete implementation of Ares' power, extends <code>DivinityCard</code> abstract class.
 *
 * @author Maximilien Groh (10683107)
 */

public class Ares extends DivinityCard implements OptionalAction {
    private static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE, WorkerAction.OPTIONAL_ACTION, WorkerAction.BUILD);

    @Override
    public Board optionalAction(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException{
        Worker otherWorker = currentBoard.getWorkersPositions().values().stream().filter(w -> w.getColor() == worker.getColor() &&
                !w.getPosition().equals(worker.getPosition())).findFirst().get();
        checkNeighbor(otherWorker, destinationCell, currentBoard);
        checkDome(destinationCell, currentBoard);
        checkWorker(destinationCell, currentBoard);
        checkTower(destinationCell, currentBoard);

        Tower tower = currentBoard.getTowersPositions().get(destinationCell);
        Board newBoard = currentBoard.withoutTower(tower);

        return tower.getHeight() == 1 ? newBoard : newBoard.withTower(new Tower(tower.getPosition(), tower.getHeight() - 1));
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
