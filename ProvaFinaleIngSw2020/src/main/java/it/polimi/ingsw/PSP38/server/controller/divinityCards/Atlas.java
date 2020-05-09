package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;

import java.util.List;

public class Atlas extends DivinityCard {
    private static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE,
            WorkerAction.SPECIAL_ABILITY, WorkerAction.BUILD);

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
