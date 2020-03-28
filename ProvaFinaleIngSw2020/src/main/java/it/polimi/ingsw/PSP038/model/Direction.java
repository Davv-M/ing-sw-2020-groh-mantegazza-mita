package it.polimi.ingsw.PSP038.model;

/**
 * Directions that a player can take to move. They represent the four cardinal
 * directions ({@code N, E, S, W}) and the four intercardinal directions ({@code NE, SE, SW, NW})
 * from the frame of reference of the game board.
 *
 * @author Maximilien Groh (10683107)
 */

public enum Direction {
    N(0, -1),
    NE(1, -1),
    E(1, 0),
    SE(1, 1),
    S(0, 1),
    SW(-1, 1),
    W(-1, 0),
    NW(-1, -1);

    private final int x;
    private final int y;

    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the direction's x coordinate.
     *
     * @return the direction's x coordinate.
     */

    public int x() {
        return x;
    }

    /**
     * Returns the direction's y coordinate.
     *
     * @return the direction's y coordinate.
     */

    public int y() {
        return y;
    }
}
