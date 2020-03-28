package it.polimi.ingsw.PSP038.model;

import static java.util.Objects.requireNonNull;

/**
 * Abstract class representing the Decorator of a cell.
 *
 * @author Maximilien Groh (10683107)
 */

public abstract class CellDecorator implements ICell {
    private final ICell cellBelow;

    /**
     * Constructor of the cell decorator. This constructor
     * is only used by its subclasses.
     *
     * @param cellBelow the cell decorated by this class.
     * @throws NullPointerException if the given cell is null
     * @throws IllegalArgumentException if the given cell has a Dome on top of it
     */
    public CellDecorator(ICell cellBelow) throws NullPointerException, IllegalArgumentException {
        if (requireNonNull(cellBelow).hasDome()) {
            throw new IllegalArgumentException("can't build on top of a Dome");
        }
        this.cellBelow = cellBelow;
    }

    @Override
    public final int x() {
        return cellBelow.x();
    }

    @Override
    public final int y() {
        return cellBelow.y();
    }

    @Override
    public int height() {
        return cellBelow.height();
    }
}
