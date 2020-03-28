package it.polimi.ingsw.PSP038.model;

import java.util.Optional;

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
     * Determines whether it is possible to move and build on the cell
     *
     * @return <b>true</b> if it is possible, <b>false</b> otherwise
     */

    default boolean canAddOnTop(){
        return true;
    }

    /**
     * Returns the neighbor of the cell in question in the given direction if there is one,
     * the empty optional value otherwise.
     *
     * @param dir The direction of the neighbor cell.
     * @return the neighbor of the cell in question in the given direction if there is one,
     * the empty optional value otherwise.
     */

    Optional<ICell> neighbor(Direction dir);
}
