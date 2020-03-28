package it.polimi.ingsw.PSP038.model;

/**
 * Interface that represents the abstract concept of cell.
 * It is at the top of the Cell hierarchy.
 *
 * @author Maximilien Groh (10683107)
 */

public interface ICell {

    /**
     * Returns cell's x coordinate.
     *
     * @return cell's x coordinate.
     */

    int x();

    /**
     * Returns cell's y coordinate.
     *
     * @return cell's y coordinate.
     */

    int y();

    /**
     * Returns cell's tower level or 0 if no tower is present on che cell.
     *
     * @return cell's tower level or 0 if no tower is present on che cell.
     */

    int height();

    /**
     * Determines whether the cell has a Dome on top of it
     *
     * @return <b>true</b> if it is has a Dome, <b>false</b> otherwise
     */

    default boolean hasDome() {
        return false;
    }

}
