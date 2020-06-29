package it.polimi.ingsw.PSP38.server.model;

import it.polimi.ingsw.PSP38.common.utilities.ArgumentChecker;

import java.util.Objects;

/**
 * Immutable class that represents a tower.
 *
 * @author Maximilien Groh (10683107)
 */

public class Tower {
    public static final int MAX_HEIGHT = 3;

    private final Cell position;
    private final int height;

    /**
     * Constructs a Tower with the given position and height.
     *
     * @param position the cell where the tower stands
     * @param height the height of the tower
     * @throws NullPointerException if the given position is null
     * @throws IllegalArgumentException if the height is not between 0 and {@code MAX_HEIGHT}
     */

    public Tower(Cell position, int height) throws NullPointerException, IllegalArgumentException{
        this.position = Objects.requireNonNull(position);
        this.height = ArgumentChecker.requireBetween(0, MAX_HEIGHT, height);
    }

    /**
     *
     * @return the tower's position
     */

    public Cell getPosition(){
        return position;
    }

    /**
     *
     * @return the tower's height
     */

    public int getHeight(){
        return height;
    }


    @Override
    public int hashCode() {
        return position.rowMajorIndex() + height * Board.COLUMNS * Board.ROWS;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Tower other = (Tower) obj;
        return position.equals(other.position) && height == other.getHeight();
    }
}
