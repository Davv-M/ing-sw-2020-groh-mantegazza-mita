package it.polimi.ingsw.PSP038.model;

import java.util.Optional;

public abstract class CellDecorator implements ICell {
    private final ICell cellBelow;

    public CellDecorator(ICell cellBelow){
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

    @Override
    public final  Optional<ICell> neighbor(Direction dir) {
        return cellBelow.neighbor(dir);
    }
}
