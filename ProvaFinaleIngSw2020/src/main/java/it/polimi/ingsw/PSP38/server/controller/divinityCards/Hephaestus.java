package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;

import java.util.Arrays;
import java.util.List;

public class Hephaestus extends DivinityCard {
    private static final List<WorkerAction> moveSequence = Arrays.asList(WorkerAction.MOVE, WorkerAction.BUILD, WorkerAction.SPECIAL_ABILITY);

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
