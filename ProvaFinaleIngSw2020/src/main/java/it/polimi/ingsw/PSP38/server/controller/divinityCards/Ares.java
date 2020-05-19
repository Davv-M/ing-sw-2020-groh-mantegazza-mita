package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.OptionalAction;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Tower;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.List;
import java.util.Set;

public class Ares extends DivinityCard implements OptionalAction {
    private static final List<WorkerAction> moveSequence = List.of(WorkerAction.MOVE, WorkerAction.OPTIONAL_ACTION, WorkerAction.BUILD);

    @Override
    public Board optionalAction(Worker worker, Cell destinationCell, Board currentBoard) {
        Worker otherWorker = currentBoard.getWorkersPositions().values().stream().filter(w -> w.getColor() == worker.getColor() && !w.getPosition().equals(worker.getPosition())).findFirst().get();
        Set<Cell> otherWorkerNeighbors = currentBoard.neighborsOf(otherWorker.getPosition());
        otherWorkerNeighbors.removeIf(c -> currentBoard.hasDomeAt(c) || currentBoard.hasWorkerAt(c) || currentBoard.heightOf(c) == 0);
        if(!otherWorkerNeighbors.contains(destinationCell)){
            throw new IllegalArgumentException("You can't remove blocks from this cell");
        }

        Tower tower = currentBoard.getTowersPositions().get(destinationCell);
        Board newBoard = currentBoard.withoutTower(tower);

        return tower.getHeight() == 1 ? newBoard : newBoard.withTower(new Tower(tower.getPosition(), tower.getHeight() - 1));
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
