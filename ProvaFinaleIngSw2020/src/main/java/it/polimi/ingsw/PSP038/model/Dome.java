package it.polimi.ingsw.PSP038.model;

/**
 * Immutable class representing the Dome piece
 * that can be placed on top of cells.
 *
 * @author Maximilien Groh (10683107)
 */

public final class Dome extends CellDecorator {

    /**
     * Constructs a Dome with the given cell underneath.
     *
     * @param cellBelow the cell below the Dome
     */
    public Dome(ICell cellBelow) {
        super(cellBelow);
    }

    @Override
    public boolean hasDome() {
        return true;
    }

}
