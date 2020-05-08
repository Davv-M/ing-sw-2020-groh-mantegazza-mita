package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;

import java.util.List;

/**
 * Concrete implementation of Artemis' power, extends <code>StrategyDivinityCard</code> interface.
 * @author Davide Mantegazza (10568661)
 * @version 1.1
 */
public class Artemis extends DivinityCard {
    public static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE, WorkerAction.SPECIAL_ABILITY, WorkerAction.BUILD);


}
