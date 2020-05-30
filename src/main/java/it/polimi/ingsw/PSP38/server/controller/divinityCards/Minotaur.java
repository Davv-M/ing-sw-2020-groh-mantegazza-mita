package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Direction;
import it.polimi.ingsw.PSP38.server.model.Worker;

import static it.polimi.ingsw.PSP38.common.utilities.ArgumentChecker.*;

import java.util.Optional;

public class Minotaur extends DivinityCard {

    @Override
    public Board move(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        checkNeighbor(worker, destinationCell, currentBoard);
        checkDome(destinationCell, currentBoard);
        checkHeight(worker, destinationCell, currentBoard);
        checkWorkerSameColor(worker, destinationCell, currentBoard);

        int vectorX = destinationCell.getX() - worker.getPosition().getX();
        int vectorY = destinationCell.getY() - worker.getPosition().getY();

        Direction dir = Direction.coordinatesToDirection(vectorX, vectorY);
        Optional<Cell> possibleNeighbor = currentBoard.neighborOf(destinationCell, dir);
        if (possibleNeighbor.isPresent()
                && !currentBoard.hasDomeAt(possibleNeighbor.get())
                && !currentBoard.hasWorkerAt(possibleNeighbor.get())) {

            Worker opponent = currentBoard.getWorkersPositions().get(destinationCell);
            Board updatedBoard = currentBoard.withoutWorker(opponent).withWorker(opponent.withPosition(possibleNeighbor.get()));
            updatedBoard = updatedBoard.withoutWorker(worker).withWorker(worker.withPosition(destinationCell));

            return updatedBoard;
        }


        return super.move(worker, destinationCell, currentBoard);
    }
}
