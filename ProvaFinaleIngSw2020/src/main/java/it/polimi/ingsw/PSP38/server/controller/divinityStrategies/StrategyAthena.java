package it.polimi.ingsw.PSP38.server.controller.divinityStrategies;

import it.polimi.ingsw.PSP38.server.controller.StrategyDivinityCard;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;

import java.util.List;

public class StrategyAthena implements StrategyDivinityCard {
    private static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE, WorkerAction.BUILD);
}
