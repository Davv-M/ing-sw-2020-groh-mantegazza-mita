package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;

import java.util.List;

public class Demeter extends DivinityCard {
    private static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE, WorkerAction.BUILD, WorkerAction.SPECIAL_ABILITY);
}
