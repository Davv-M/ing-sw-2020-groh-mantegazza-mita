package it.polimi.ingsw.PSP038.model;

/**
 * Directions that a player can take to move. They represent the four cardinal
 * directions ({@code N, E, S, W}) and the four intercardinal directions ({@code NE, SE, SW, NW})
 * from the frame of reference of the game board.
 *
 * @author Maximilien Groh (10683107)
 */

public enum Direction {
    N(-1, 0),
    NE(-1, 1),
    E(0, 1),
    SE(1, 1),
    S(1, 0),
    SW(1, -1),
    W(0, -1),
    NW(-1, -1);

    private final int x;
    private final int y;

    Direction(int x, int y) {
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
