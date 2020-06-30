package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.OptionalAbility;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.List;

/**
 * Concrete implementation of Atlas' power, extends <code>DivinityCard</code> abstract class.
 *
 * @author Maximilien Groh (10683107)
 */

public class Atlas extends DivinityCard implements OptionalAbility {
    private static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE,
            WorkerAction.OPTIONAL_ABILITY);

    @Override
    public Board optionalAbility(boolean buildDome, Worker worker, Cell destinationCell, Board currentBoard) {
        if(buildDome){
            checkBuild(worker, destinationCell, currentBoard);
            return currentBoard.withDome(destinationCell);
        } else {
            return super.build(worker, destinationCell, currentBoard);
        }
    }

    @Override
    public WorkerAction typeOfAction(WorkerAction action) {
        return action == WorkerAction.OPTIONAL_ABILITY ? WorkerAction.BUILD : super.typeOfAction(action);
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }

    @Override
    public String toString() {
        return "Atlas";
    }
}
