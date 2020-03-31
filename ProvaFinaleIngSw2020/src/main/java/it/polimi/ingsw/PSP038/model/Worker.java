package it.polimi.ingsw.PSP038.model;

/**
 * Class that represents a worker.
 *
 * @author Matteo Mita (10487862)
 */

public class Worker {
    private final Color color;
    private final int numberOfTwo;
    private int xCoordinate;
    private int yCoordinate;

    /**
     * Constructs a cell with the given parameters
     *
     * @param num is which one of the two player's worker are
     * @param col color of player's worker
     */

    public Worker(int num, Color col){
        numberOfTwo = num;
        color = col;
    }
    
    public void setPosition(int x, int y){
        xCoordinate = x;
        yCoordinate = y;
    }

    public int getXCoordinate(){ return xCoordinate; }

    public int getYCoordinate(){ return yCoordinate; }

    public Cell getPositionInTheBoard(Board board){
        return board.cellAt(xCoordinate, yCoordinate);
    }

}
