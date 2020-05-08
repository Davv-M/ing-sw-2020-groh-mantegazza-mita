package it.polimi.ingsw.PSP38.server.model;

import java.util.Objects;

public class Tower {
    public static final int MAX_HEIGHT = 3;

    private final Cell position;
    private final int height;


    public Tower(Cell position, int height) throws NullPointerException{
        this.position = Objects.requireNonNull(position);
        this.height = height;
    }

    public Cell getPosition(){
        return position;
    }

    public int getHeight(){
        return height;
    }

    public Tower withTowerHeight(int height){
        return new Tower(position, height);
    }

    @Override
    public int hashCode() {
        return position.rowMajorIndex();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Tower other = (Tower) obj;
        return position.equals(other.position) && height == other.getHeight();
    }
}
