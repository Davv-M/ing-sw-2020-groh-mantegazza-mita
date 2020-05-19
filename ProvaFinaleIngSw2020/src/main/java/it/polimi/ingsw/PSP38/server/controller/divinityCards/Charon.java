package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.controller.OptionalAction;
import it.polimi.ingsw.PSP38.server.controller.WorkerAction;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Direction;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Charon extends DivinityCard implements OptionalAction {
    private static final List<WorkerAction> moveSequence = Arrays.asList(WorkerAction.OPTIONAL_ACTION, WorkerAction.MOVE, WorkerAction.BUILD);

    @Override
    public Board optionalAction(Worker worker, Cell destinationCell, Board currentBoard) {
        if(!currentBoard.neighborsOf(worker.getPosition()).contains(destinationCell)){
            throw new IllegalArgumentException("You must select a neighboring cell");
        }
        if(currentBoard.hasWorkerAt(destinationCell)){
            if(currentBoard.workerAt(destinationCell).getColor() != worker.getColor()){
                int vectorX = destinationCell.getX() - worker.getPosition().getX();
                int vectorY = destinationCell.getY() - worker.getPosition().getY();

                Direction dir = Direction.coordinatesToDirection(vectorX, vectorY);
                Optional<Cell> possibleNeighbor = currentBoard.neighborOf(destinationCell, dir.opposite());
                if (possibleNeighbor.isPresent()
                        && !currentBoard.hasDomeAt(possibleNeighbor.get())
                        && !currentBoard.hasWorkerAt(possibleNeighbor.get())) {

                    Worker opponent = currentBoard.getWorkersPositions().get(destinationCell);

                    return currentBoard.withoutWorker(opponent).withWorker(opponent.withPosition(possibleNeighbor.get()));
                } else {
                    throw new IllegalArgumentException("the selected worker can't be moved");
                }
            } else {
                throw new IllegalArgumentException("The worker you select can't be yours");
            }
        } else {
            throw new IllegalArgumentException("You must select a worker");
        }
    }

    @Override
    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }
}
