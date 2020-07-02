package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Direction;
import it.polimi.ingsw.PSP38.server.model.Worker;

import static it.polimi.ingsw.PSP38.common.utilities.ArgumentChecker.*;

import java.util.Optional;

/**
 * Concrete implementation of Minotaur's power, extends <code>DivinityCard</code> abstract class.
 *
 * @author Maximilien Groh (10683107)
 */

public class Minotaur extends DivinityCard {

    @Override
    public Board move(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        checkNeighbor(worker, destinationCell, currentBoard);
        checkDome(destinationCell, currentBoard);
        checkHeight(worker, destinationCell, currentBoard);
        checkWorkerSameColor(worker, destinationCell, currentBoard);
        if (currentBoard.hasWorkerAt(destinationCell)) {
            int vectorX = destinationCell.getX() - worker.getPosition().getX();
            int vectorY = destinationCell.getY() - worker.getPosition().getY();

            Direction dir = Direction.coordinatesToDirection(vectorX, vectorY);
            Optional<Cell> possibleNeighbor = currentBoard.neighborOf(destinationCell, dir);

            if (possibleNeighbor.isPresent()) {
                if (currentBoard.hasDomeAt(possibleNeighbor.get())) {
                    throw new IllegalArgumentException("You can't push your opponent's worker because there is a dome behind it.");
                } else if (currentBoard.hasWorkerAt(possibleNeighbor.get())) {
                    throw new IllegalArgumentException("You can't push your opponent's worker because there is another worker behind it.");
                }

                Worker opponent = currentBoard.workerAt(destinationCell);
                Board updatedBoard = currentBoard.withoutWorker(opponent).withWorker(opponent.withPosition(possibleNeighbor.get()));
                updatedBoard = updatedBoard.withoutWorker(worker).withWorker(worker.withPosition(destinationCell));

                return updatedBoard;
            } else {
                throw new IllegalArgumentException("You can't push your opponent outside of the board.");
            }

        }

        return super.move(worker, destinationCell, currentBoard);
    }

    @Override
    public String toString() {
        return "Minotaur";
    }
}
