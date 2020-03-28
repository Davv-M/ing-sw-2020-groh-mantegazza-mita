package it.polimi.ingsw.PSP038.model;

/**
 * Immutable class representing the blocks of towers
 * that can be placed on top of the board cells.
 *
 * @author Maximilien Groh (10683107)
 */

public final class TowerBlock extends CellDecorator {
    public static final int MAX_BLOCKS = 3;

    /**
     * Constructs a block of tower with the
     * given cell underneath.
     *
     * @param cellBelow the cell below the tower block
     * @throws IllegalArgumentException if the given cell is already
     *                                  of height >= {@code MAX_BLOCKS}
     */
    public TowerBlock(ICell cellBelow) throws IllegalArgumentException {
        super(cellBelow);
        if (cellBelow.height() >= MAX_BLOCKS) {
            throw new IllegalArgumentException("Towers can't be made of more than "
                    + MAX_BLOCKS + " blocks");
        }
    }

    /**
     * Returns the height of the cell below + 1
     *
     * @return height of the cell below + 1
     */
    @Override
    public int height() {
        return super.height() + 1;
    }
}
