package it.polimi.ingsw.PSP38.server.model;

import it.polimi.ingsw.PSP38.common.WorkerColor;

import static java.util.Objects.requireNonNull;

/**
 * Immutable class that represents a worker.
 *
 * @author Matteo Mita (10487862)
 */

public final class Worker {
    private final WorkerColor color;
    private final Cell position;

    /**
     * Construct a worker with the given parameters
     *
     * @param color    the color of the worker
     * @param position the cell below the worker
     * @throws NullPointerException if an argument is null
     */

    public Worker(WorkerColor color, Cell position) throws NullPointerException {
        this.color = requireNonNull(color);
        this.position = requireNonNull(position);
    }

    /**
     * @return the cell on which the worker is standing
     */

    public Cell getPosition() {
        return position;
    }

    /**
     * @return the worker's color
     */

    public WorkerColor getColor() {
        return color;
    }

    public Worker withPosition(Cell position){
        return position == null ? this : new Worker(color, position);
    }

    @Override
    public int hashCode() {
        return position.rowMajorIndex();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Worker other = (Worker) obj;
        return position.equals(other.position) && color == other.getColor();
    }
}
