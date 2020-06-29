package it.polimi.ingsw.PSP38.common;

/**
 * Class made of a series of unmodifiable byte parameters that represent the different status of a cell of the board
 *
 * @author Maximilien Groh (10683107)
 */
public class BytesForBoard {
    public static final byte TOWER_1 = 1;
    public static final byte TOWER_2 = 2;
    public static final byte TOWER_3 = 4;
    public static final byte DOME = 8;
    public static final byte WORKER_WHITE = 16;
    public static final byte WORKER_BLACK = 32;
    public static final byte WORKER_BLUE = 64;
}
