package it.polimi.ingsw.PSP38.server.controller.divinityStrategies;

import it.polimi.ingsw.PSP38.server.controller.StrategyDivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of Artemis' power, extends <code>StrategyDivinityCard</code> interface.
 * @author Davide Mantegazza (10568661)
 * @version 1.1
 */
public class StrategyArtemis implements StrategyDivinityCard {
    public static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE, WorkerAction.SPECIAL_ABILITY, WorkerAction.BUILD);


}
