package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Tower;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.*;

/**
 * Interface representing a divinity card of the game Santorini.
 *
 * @author Maximilien Groh (10683107), Davide Mantegazza (10568661), Matteo Mita (10487862)
 */

public abstract class DivinityCard {
    private final List<WorkerAction> moveSequence;

    /**
     * Different colors that a worker can have.
     */
    enum Name {
        APOLLO,
        ARTEMIS,
        ATHENA,
        ATLAS,
        DEMETER,
        HEPHAESTUS,
        MINOTAUR,
        PAN,
        PROMETHEUS,
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
    public Set<Cell> preMove(Worker worker, Board currentBoard) {
        Set<Cell> neighborCells = currentBoard.neighborsOf(worker.getPosition());
        neighborCells.removeIf(c -> currentBoard.heightOf(c) > currentBoard.heightOf(worker.getPosition()) + 1);

        return neighborCells;
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
        if (!preMove(worker, currentBoard).contains(destinationCell)) {
            throw new IllegalArgumentException("You can't move on that cell.");
        }

        return currentBoard.withoutWorker(worker).withWorker(worker.withPosition(destinationCell));
    }

    /**
     * Returns a list of possible cells where the given worker can build
     *
     * @param worker       the worker that has to build
     * @param currentBoard the current board of the game
     * @return a list of possible cells where the given worker can build
     */
    public Set<Cell> preBuild(Worker worker, Board currentBoard) {

        return currentBoard.neighborsOf(worker.getPosition());
    }

    /**
     * Adds a tower level or a dome to the given cell, depending on the current tower's height and returns the updated board
     *
     * @param destinationCell         the cell on which to build
     * @param currentBoard the current board of the game
     * @return the updated board with the updated cell's tower's height
     */
    public Board build(Worker worker, Cell destinationCell, Board currentBoard) throws IllegalArgumentException {
        if (!preBuild(worker, currentBoard).contains(destinationCell)) {
            throw new IllegalArgumentException("you can't build on that cell.");
        }

        int currentHeight = currentBoard.heightOf(destinationCell);
        return currentHeight == Tower.MAX_HEIGHT ?
                currentBoard.withDome(destinationCell) :
                currentBoard.withoutTower(currentBoard.getTowersPositions().get(destinationCell))
                        .withTower(new Tower(destinationCell, currentHeight + 1));
    }

    public Board specialMove(Worker worker, Cell destinationCell, Board currentBoard){
        return currentBoard;
    }

    public Board specialBuild(Worker worker, Cell destinationCell, Board currentBoard){
        return currentBoard;
    }

    public List<WorkerAction> getMoveSequence() {
        return moveSequence;
    }

    public boolean isWinner(Board board, Cell previousPosition, Cell currentPosition) {
        return !previousPosition.equals(currentPosition) &&
                board.heightOf(currentPosition) == 3 &&
                board.heightOf(previousPosition) == 2;
    }

}