package it.polimi.ingsw.PSP38.model;

import static java.util.Objects.requireNonNull;

/**
 * Immutable class that represents a worker.
 *
 * @author Matteo Mita (10487862)
 */

public final class Worker {
    private final Color color;
    private final Cell position;

    /**
     * Construct a worker with the given parameters
     *
     * @param color    the color of the worker
     * @param position the cell below the worker
     * @throws NullPointerException     if an argument is null
     * @throws IllegalArgumentException if the given cell has a dome on top
     */

    public Worker(Color color, Cell position) throws NullPointerException, IllegalArgumentException {
        this.color = requireNonNull(color);
        if (requireNonNull(position).hasDome()) {
            throw new IllegalArgumentException("Workers can't stand on domes");
        }
        this.position = position;
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

    public Color getColor() {
        return color;
    }

    /**
     * Different colors that a worker can have
     * ({@code YELLOW, BLUE, RED}).
     */

    public enum Color {
        RED, PURPLE, YELLOW
    }

}
