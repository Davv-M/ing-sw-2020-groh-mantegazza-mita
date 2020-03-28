package it.polimi.ingsw.PSP038.model;

public final class Dome extends CellDecorator implements ICell{

    public Dome(ICell cellBelow){
        super(cellBelow);
    }

    @Override
    public boolean canAddOnTop() {
        return false;
    }
}
