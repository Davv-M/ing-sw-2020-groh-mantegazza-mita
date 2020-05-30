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
 * @author Maximilien Groh (10683107), Davide Mantegazza (10568661), Matteo Mita (10487862)
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

    public DivinityCard(List<WorkerAction> moveSequence){
        this.moveSequence = List.copyOf(moveSequence);
    }

    public DivinityCard() {
        this(Arrays.asList(WorkerAction.MOVE, WorkerAction.BUILD));
    }


    /**
     * Returns a list of cells where the given worker can move
     *
     * @param worker       the worker that has to be moved
     * @param currentBoard the current board of the game
     */
    public void checkMove(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException{
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

    public Board move(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        checkMove(worker, destinationCell, currentBoard);
        return currentBoard.withoutWorker(worker).withWorker(worker.withPosition(destinationCell));
    }

    /**
     * Returns a list of possible cells where the given worker can build
     *
     * @param worker       the worker that has to build
     * @param currentBoard the current board of the game
     */
    public void checkBuild(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException{
        checkNeighbor(worker, destinationCell, currentBoard);
        checkDome(destinationCell, currentBoard);
        checkWorker(destinationCell, currentBoard);
    }

    /**
     * Adds a tower level or a dome to the given cell, depending on the current tower's height and returns the updated board
     *
     * @param destinationCell         the cell on which to build
     * @param currentBoard the current board of the game
     * @return the updated board with the updated cell's tower's height
     */
    public Board build(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        checkBuild(worker, destinationCell, currentBoard);

        int currentHeight = currentBoard.heightOf(destinationCell);
        return currentHeight == Tower.MAX_HEIGHT ?
                currentBoard.withDome(destinationCell) :
                currentBoard.withoutTower(currentBoard.getTowersPositions().get(destinationCell))
                        .withTower(new Tower(destinationCell, currentHeight + 1));
    }

    public void checkOpponentMove(WorkerAction action, Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException{

    }

    public WorkerAction typeOfAction(WorkerAction action){
        return action;
    }

    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }

    public boolean isWinner(Board board, Cell previousPosition, Cell currentPosition) {
        return board.heightOf(previousPosition) == Tower.MAX_HEIGHT - 1 && board.heightOf(currentPosition) == Tower.MAX_HEIGHT;
    }

    public boolean blockOpponentWinningCondition(Cell currentPosition){
        return false;
    }


}