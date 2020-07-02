package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Tower;
import it.polimi.ingsw.PSP38.server.model.Worker;

import static it.polimi.ingsw.PSP38.common.utilities.ArgumentChecker.*;

import java.util.*;

/**
 * Abstract class representing a divinity card of the game Santorini.
 *
 * @author Maximilien Groh (10683107)
 */

public abstract class DivinityCard {
    private final List<WorkerAction> moveSequence;

    /**
     * Different names that a divinity can have.
     */
    enum Name {
        APOLLO,
        ARES,
        ARTEMIS,
        ATHENA,
        ATLAS,
        CHARON,
        DEMETER,
        HEPHAESTUS,
        HERA,
        HESTIA,
        MINOTAUR,
        PAN,
        PROMETHEUS,
        ZEUS
    }

    /**
     * constructs the divinity with the specified move sequence
     *
     * @param moveSequence the list of actions the worker associated with the divinity
     *                     can take during his turn
     */

    public DivinityCard(List<WorkerAction> moveSequence) {
        this.moveSequence = List.copyOf(moveSequence);
    }

    /**
     * constructs the divinity with the standard move sequence
     */

    public DivinityCard() {
        this(Arrays.asList(WorkerAction.MOVE, WorkerAction.BUILD));
    }


    /**
     * Returns a list of cells where the given worker can move
     *
     * @param worker       the worker that has to be moved
     * @param currentBoard the current board of the game
     */
    public void checkMove(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        checkNeighbor(worker, destinationCell, currentBoard);
        checkDome(destinationCell, currentBoard);
        checkHeight(worker, destinationCell, currentBoard);
        checkWorker(destinationCell, currentBoard);
    }

    /**
     * Moves the given worker on the given cell and returns the updated board
     *
     * @param worker          the worker that has to be moved
     * @param destinationCell the cell where the worker has to be moved
     * @param currentBoard    the current board of the game
     * @return the updated board
     */

    public Board move(Worker worker, Cell destinationCell, Board currentBoard, boolean isSimulation) throws IllegalArgumentException {
        checkMove(worker, destinationCell, currentBoard);
        return currentBoard.withoutWorker(worker).withWorker(worker.withPosition(destinationCell));
    }

    /**
     * Returns a list of possible cells where the given worker can build
     *
     * @param worker       the worker that has to build
     * @param currentBoard the current board of the game
     */
    public void checkBuild(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        checkNeighbor(worker, destinationCell, currentBoard);
        checkDome(destinationCell, currentBoard);
        checkWorker(destinationCell, currentBoard);
    }

    /**
     * Adds a tower level or a dome to the given cell, depending on the current tower's height and returns the updated board
     *
     * @param destinationCell the cell on which to build
     * @param currentBoard    the current board of the game
     * @return the updated board with the updated cell's tower's height
     */
    public Board build(Worker worker, Cell destinationCell, Board currentBoard, boolean isSimulation) throws IllegalArgumentException {
        checkBuild(worker, destinationCell, currentBoard);

        int currentHeight = currentBoard.heightOf(destinationCell);
        return currentHeight == Tower.MAX_HEIGHT ?
                currentBoard.withDome(destinationCell) :
                currentBoard.withoutTower(currentBoard.getTowersPositions().get(destinationCell))
                        .withTower(new Tower(destinationCell, currentHeight + 1));
    }

    /**
     * Checks if the opponents action doesn't break the divinity card
     * rules, and throws an exception if it does
     *
     * @param action          the action the opponent wants to take
     * @param worker          the worker that wants to take action
     * @param destinationCell the cell where the action is taken
     * @param currentBoard    the current board of the game
     * @throws IllegalArgumentException if the action taken is illegal by the divinity card rules
     */

    public void checkOpponentMove(WorkerAction action, Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {

    }

    /**
     * Determines the type of action (build or move)
     * based on the divinity card and returns it
     *
     * @param action the action taken
     * @return the type of action based on the divinity card
     */

    public WorkerAction typeOfAction(WorkerAction action) {
        return action;
    }

    /**
     * @return the sequence of moves the divinity can take
     */

    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }

    /**
     * Determines whether the worker associated with the divinity card is
     * winning or not, based on his previous position and his current position
     *
     * @param board            the current board of the game
     * @param previousPosition previous position of the worker
     * @param currentPosition  current position of the worker
     * @return <b>true</b> if he is the winner of the game , <b>false</b> otherwise
     */

    public boolean isWinner(Board board, Cell previousPosition, Cell currentPosition) {
        return board.heightOf(previousPosition) == Tower.MAX_HEIGHT - 1 && board.heightOf(currentPosition) == Tower.MAX_HEIGHT;
    }

    /**
     * Determines if the opponent can win based on his current position
     *
     * @param currentPosition current position of the worker that just moved
     * @return <b>true</b> if this divinity card doesn't allow the opponent to win,
     * <b>false</b> otherwise
     */

    public boolean blockOpponentWinningCondition(Cell currentPosition) {
        return false;
    }


}