package it.polimi.ingsw.PSP38.server.model;

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

    /**
     * Converts a normalized vector in a direction
     *
     * @param nX vector's x coordinate
     * @param nY vector's y coordinate
     * @return the corresponding direction
     */

    public static Direction coordinatesToDirection(int nX, int nY) {
        if (nX == 0 && nY == -1) {
            return N;
        } else if (nX == 1 && nY == -1) {
            return NE;
        } else if (nX == 1 && nY == 0) {
            return E;
        } else if (nX == 1 && nY == 1) {
            return SE;
        } else if (nX == 0 && nY == 1) {
            return S;
        } else if (nX == -1 && nY == 1) {
            return SW;
        } else if (nX == -1 && nY == 0) {
            return W;
        } else if (nX == -1 && nY == -1) {
            return NW;
        } else {
            throw new IllegalArgumentException("unknown direction");
        }
    }

    /**
     * Reverses the direction.
     *
     * @return The opposite direction.
     * @throws IllegalArgumentException
     *             if the direction is unknown.
     */

    public Direction opposite() {
        switch (this) {
            case N:
                return S;
            case NE:
                return SW;
            case E:
                return W;
            case SE:
                return NW;
            case S:
                return N;
            case SW:
                return NE;
            case W:
                return E;
            case NW:
                return SE;
            default:
                throw new IllegalArgumentException("unknown direction");
        }
    }
}
