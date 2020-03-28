package it.polimi.ingsw.PSP038.model;

public final class TowerBlock extends CellDecorator {

    public TowerBlock(ICell cellBelow){
        super(cellBelow);
    }

    @Override
    public int height(){
        return super.height() + 1;
    }
}
