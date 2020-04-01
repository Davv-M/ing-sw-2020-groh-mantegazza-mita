package it.polimi.ingsw.PSP038.model;

/**
 * Class that represents a board's cell.
 *
 * @author Maximilien Groh (10683107)
 */

public class Cell {

    private int xCoordinate;
    private int yCoordinate;
    private int level;
    private boolean hasDome;
    private final int MAX_HEIGHT = 3;

    /**
     * Constructs a cell with the given parameters
     *
     * @param x number of row
     * @param y number of column
     * @throws IllegalArgumentException
     */

    public Cell(int x, int y) throws IllegalArgumentException {
        xCoordinate = x;
        yCoordinate = y;
        hasDome = false;
        level = 0;
    }

    /**
     * Constructs a cell with the given parameters
     *
     * @param x number of row
     * @param y number of column
     * @param hDome <b>true</b> if it is has a Dome, <b>false</b> otherwise
     * @throws IllegalArgumentException
     */

    public Cell(int x, int y, boolean hDome) throws IllegalArgumentException {
        xCoordinate = x;
        yCoordinate = y;
        hasDome = hDome;
        level = 0;
    }

    /**
     * Returns cell's x coordinate.
     *
     * @return cell's x coordinate.
     */

    public int getXCoordinate() { return xCoordinate; }

    /**
     * Returns cell's x coordinate.
     *
     * @return cell's x coordinate.
     */

    public int getYCoordinate() { return yCoordinate; }

    /**
     * Returns cell's height.
     *
     * @return cell's height.
     */

    public int getHeight() { return level; }

    /**
     * Determines whether the cell has a Dome on top of it
     *
     * @return <b>true</b> if it is has a Dome, <b>false</b> otherwise
     */

    public boolean hasDome() { return hasDome; }

    /**
     * Upgrade the cell's level
     * @throws IncompatibleClassChangeError if the level is {@code MAX_HEIGHT}
     */

    public void addLevel() throws IncompatibleClassChangeError{
        if (level< MAX_HEIGHT)
            level++;
        else throw new IncompatibleClassChangeError();
        }

    /**
     * Add Dome
     * @throws IncompatibleClassChangeError if the dome already exist
     */

    public void addDome() throws IncompatibleClassChangeError{
        if (hasDome == false)
            hasDome = true;
        else throw new IncompatibleClassChangeError();
    }
}