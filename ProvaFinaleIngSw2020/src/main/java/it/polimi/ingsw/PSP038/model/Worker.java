package it.polimi.ingsw.PSP038.model;

import it.polimi.ingsw.PSP038.model.cards.DivinityCard;

public class Worker extends CellDecorator implements DivinityCard {

    private final Color color;

    public Worker(ICell cellBelow, Color color){
        super(cellBelow);
        this.color = color;
    }

    public Color color(){
        return color;
    }

    public enum Color {
        YELLOW, BLUE, RED
    }
}
