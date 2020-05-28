package it.polimi.ingsw.PSP38.server.utilities;

import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.Set;

/**
 * Non instantiable class that provides methods used to control whether
 * arguments are valid or not.
 *
 * @author Maximilien Groh (10683107)
 */

public final class ArgumentChecker {

    /**
     * Empty constructor that causes the class to be non instantiable.
     */

    private ArgumentChecker() {

    }

    /**
     * Verifies that a given value is inside a certain range. If it is not the case,
     * it throws an IllegalArgumentException.
     *
     * @param lowerBound Lower bound of the range
     * @param upperBound Upper bound of the range
     * @param value      Integer that must be checked
     * @return the given value
     * @throws IllegalArgumentException if {@code (value < lowerBound || value >= upperBound)}
     */

    public static int requireBetween(int lowerBound, int upperBound, int value)
            throws IllegalArgumentException {
        if (value < lowerBound || value > upperBound) {
            throw new IllegalArgumentException("Please enter an integer between " + lowerBound + " and " + upperBound);
        } else {
            return value;
        }
    }

    public static void checkNeighbor(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException{
        Set<Cell> neighborCells = currentBoard.neighborsOf(worker.getPosition());
        if(!neighborCells.contains(destinationCell)){
            throw new IllegalArgumentException("This cell is too far away.");
        }
    }

    public static void checkDome(Cell destinationCell, Board currentBoard) throws IllegalArgumentException{
        if(currentBoard.hasDomeAt(destinationCell)){
            throw new IllegalArgumentException("This cell contains a dome.");
        }
    }

    public static void checkWorker(Cell destinationCell, Board currentBoard) throws IllegalArgumentException{
        if(currentBoard.hasWorkerAt(destinationCell)){
            throw new IllegalArgumentException("This cell contains a worker.");
        }
    }

    public static void checkHeight(Worker worker, Cell destinationCell, Board currentBoard){
        if(currentBoard.heightOf(destinationCell) > currentBoard.heightOf(worker.getPosition()) + 1){
            throw new IllegalArgumentException("This tower is too high.");
        }
    }

    public static void checkWorkerSameColor(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if(currentBoard.hasWorkerAt(destinationCell) && currentBoard.workerAt(destinationCell).getColor() == worker.getColor()){
            throw new IllegalArgumentException("This cell contains a worker with the same color.");
        }
    }

    public static void checkTower(Cell destinationCell, Board currentBoard) {
        if(currentBoard.heightOf(destinationCell) == 0){
            throw new IllegalArgumentException("This cell doesn't contain a tower.");
        }
    }
}
