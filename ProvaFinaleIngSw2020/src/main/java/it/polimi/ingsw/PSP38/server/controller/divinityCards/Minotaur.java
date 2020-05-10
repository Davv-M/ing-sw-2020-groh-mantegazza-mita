package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Direction;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Minotaur extends DivinityCard {

    @Override
    public Set<Cell> preMove(Worker worker, Board currentBoard) {
        Set<Cell> neighborCells = currentBoard.neighborsOf(worker.getPosition());
        Map<Cell, Worker> workersPositions = currentBoard.getWorkersPositions();
        neighborCells.removeIf(c -> currentBoard.heightOf(c) > currentBoard.heightOf(worker.getPosition()) + 1);
        neighborCells.removeIf(c -> currentBoard.hasWorkerAt(c) && workersPositions.get(c).getColor() == worker.getColor());
        return neighborCells;
    }

    @Override
    public Board move(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if (currentBoard.hasWorkerAt(destinationCell) &&
                worker.getColor() != currentBoard.workerAt(destinationCell).getColor() &&
                preMove(worker, currentBoard).contains(destinationCell)){

            int xWorker = worker.getPosition().getX();
            int yWorker = worker.getPosition().getY();

            int xDestination = destinationCell.getX();
            int yDestination = destinationCell.getY();

            Direction dir = Direction.coordinatesToDirection(xWorker, yWorker, xDestination, yDestination);
            Optional<Cell> possibleNeighbor = currentBoard.neighborOf(destinationCell, dir);
            if (possibleNeighbor.isPresent()
                    && !currentBoard.hasDomeAt(possibleNeighbor.get())
                    && !currentBoard.hasWorkerAt(possibleNeighbor.get())) {

                Worker opponent = currentBoard.getWorkersPositions().get(destinationCell);
                Board updatedBoard = currentBoard.withoutWorker(opponent).withWorker(opponent.withPosition(possibleNeighbor.get()));
                updatedBoard = updatedBoard.withoutWorker(worker).withWorker(worker.withPosition(destinationCell));

                return updatedBoard;
            }
        }

        return super.move(worker, destinationCell, currentBoard);




    }




}
