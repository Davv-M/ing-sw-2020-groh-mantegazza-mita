package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Direction;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.Optional;

public class Minotaur extends DivinityCard {

    @Override
    public Board move(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if (currentBoard.hasWorkerAt(destinationCell) &&
                worker.getColor() != currentBoard.workerAt(destinationCell).getColor()) {

            int x = destinationCell.getX() - worker.getPosition().getX();
            int y = destinationCell.getY() - worker.getPosition().getY();
            Direction dir = Direction.coordinatesTodirection(x, y);
            Optional<Cell> possibleNeighbor = currentBoard.neighborOf(destinationCell, dir);
            if (possibleNeighbor.isPresent()
                    && !currentBoard.hasDomeAt(possibleNeighbor.get())
                    && !currentBoard.hasWorkerAt(possibleNeighbor.get())) {

                Worker opponent = currentBoard.getWorkersPositions().get(destinationCell);
                Board updatedBoard = currentBoard.withoutWorker(opponent).withWorker(opponent.withPosition(possibleNeighbor.get()));
                updatedBoard = updatedBoard.withoutWorker(worker).withWorker(worker.withPosition(opponent.getPosition()));

                return updatedBoard;
            }
        }

        return super.move(worker, destinationCell, currentBoard);
    }
}
