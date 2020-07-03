package it.polimi.ingsw.PSP38.common.utilities;

import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Player;
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

    /**
     * Verifies that the given x coordinate is not out of bounds.
     * If it is not the case, it throws an IllegalArgumentException.
     *
     * @param x the x coordinate
     * @return the given value
     * @throws IllegalArgumentException if the value is outside of the board's boundaries
     */

    public static int checkXCoordinate(int x) throws IllegalArgumentException {
        return requireBetween(0, Board.COLUMNS - 1, x);
    }

    /**
     * Verifies that the given y coordinate is not out of bounds.
     * If it is not the case, it throws an IllegalArgumentException.
     *
     * @param y the y coordinate
     * @return the given value
     * @throws IllegalArgumentException if the value is outside of the board's boundaries
     */

    public static int checkYCoordinate(int y) throws IllegalArgumentException {
        return requireBetween(0, Board.ROWS - 1, y);
    }

    /**
     * Verifies that the value is between {@code Player.MIN_AGE} and {@code Player.MAX_AGE}.
     * If it is not the case, it throws an IllegalArgumentException.
     *
     * @param age the value to check
     * @return the given value
     * @throws IllegalArgumentException if the value is outside of the given boundaries
     */

    public static int checkAge(int age) throws IllegalArgumentException {
        return ArgumentChecker.requireBetween(Player.MIN_AGE, Player.MAX_AGE, age);
    }

    /**
     * Verifies that the given string is equal to "yes" or "no".
     * If it is not the case, it throws an IllegalArgumentException.
     *
     * @param answer the string to check
     * @return the given string
     * @throws IllegalArgumentException if the value is not equal to "yes" or "no"
     */

    public static String checkYesOrNo(String answer) throws IllegalArgumentException {
        if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no")) {
            return answer;
        } else {
            throw new IllegalArgumentException("Please answer with either \"yes\" or \"no\".");
        }
    }

    /**
     * Verifies that the worker's position is adjacent to the given cell.
     * If it is not the case, it throws an IllegalArgumentException.
     *
     * @param worker          the worker
     * @param destinationCell the cell to check
     * @param currentBoard    the board containing the cells
     * @throws IllegalArgumentException if the given cell isn't adjacent to the cell on which
     *                                  the worker is standing
     */

    public static void checkNeighbor(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        Set<Cell> neighborCells = currentBoard.neighborsOf(worker.getPosition());
        if (!neighborCells.contains(destinationCell)) {
            throw new IllegalArgumentException("This cell is not a neighbor of the worker's cell.");
        }
    }

    /**
     * Verifies that the board doesn't contain a dome on the given cell.
     * If it does, it throws an IllegalArgumentException.
     *
     * @param destinationCell the cell to check
     * @param currentBoard    the board containing the cells
     * @throws IllegalArgumentException if the board contains a dome on the given cell
     */

    public static void checkDome(Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if (currentBoard.hasDomeAt(destinationCell)) {
            throw new IllegalArgumentException("This cell contains a dome.");
        }
    }

    /**
     * Verifies that the board doesn't contain a worker on the given cell.
     * If it does, it throws an IllegalArgumentException.
     *
     * @param destinationCell the cell to check
     * @param currentBoard    the board containing the cells
     * @throws IllegalArgumentException if the board contains a worker on the given cell
     */

    public static void checkWorker(Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if (currentBoard.hasWorkerAt(destinationCell)) {
            throw new IllegalArgumentException("This cell contains a worker.");
        }
    }

    /**
     * Verifies that the board contains a worker on the given cell.
     * If it doesn't, it throws an IllegalArgumentException.
     *
     * @param destinationCell the cell to check
     * @param currentBoard    the board containing the cells
     * @throws IllegalArgumentException if the board doesn't contain a worker on the given cell
     */

    public static void checkNoWorker(Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if (!currentBoard.hasWorkerAt(destinationCell)) {
            throw new IllegalArgumentException("This cell doesn't contain a worker.");
        }
    }

    /**
     * Verifies that the height difference between the given cell and
     * the cell on which the worker is standing is not greater than 1.
     * If it is, it throws an IllegalArgumentException.
     *
     * @param worker          the worker
     * @param destinationCell the cell to check
     * @param currentBoard    the board containing the cells
     * @throws IllegalArgumentException if the height difference between the given cell and
     *                                  the cell on which the worker is standing is greater than 1.
     */

    public static void checkHeight(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if (currentBoard.heightOf(destinationCell) > currentBoard.heightOf(worker.getPosition()) + 1) {
            throw new IllegalArgumentException("This tower is too high.");
        }
    }

    /**
     * Verifies that the board doesn't contain a worker of the same color
     * of the given worker on the cell given as parameter.
     * If it does, it throws an IllegalArgumentException.
     *
     * @param worker          the worker
     * @param destinationCell the cell to check
     * @param currentBoard    the board containing the cells
     * @throws IllegalArgumentException if the given cell contains a worker of the same color
     *                                  as the one given as parameter.
     */

    public static void checkWorkerSameColor(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if (currentBoard.hasWorkerAt(destinationCell) && currentBoard.workerAt(destinationCell).getColor() == worker.getColor()) {
            throw new IllegalArgumentException("This cell contains a worker with the same color.");
        }
    }

    /**
     * Verifies that the board contains a tower on the given cell.
     * If it doesn't, it throws an IllegalArgumentException.
     *
     * @param destinationCell the cell to check
     * @param currentBoard    the board containing the cells
     * @throws IllegalArgumentException if the board doesn't contain a tower on
     *                                  the given cell.
     */

    public static void checkTower(Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if (currentBoard.heightOf(destinationCell) == 0) {
            throw new IllegalArgumentException("This cell doesn't contain a tower.");
        }
    }
}
