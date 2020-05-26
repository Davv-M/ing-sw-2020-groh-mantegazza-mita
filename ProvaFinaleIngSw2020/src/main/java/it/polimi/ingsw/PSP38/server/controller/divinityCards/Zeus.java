package it.polimi.ingsw.PSP38.server.controller.divinityCards;

import it.polimi.ingsw.PSP38.server.controller.DivinityCard;
import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Tower;
import it.polimi.ingsw.PSP38.server.model.Worker;

import java.util.Set;

public class Zeus extends DivinityCard {
    @Override
    public Set<Cell> preBuild(Worker worker, Board currentBoard) {
        Cell workerPosition = worker.getPosition();
        Set<Cell> neighborCells = currentBoard.neighborsOf(workerPosition);
        if(currentBoard.heightOf(workerPosition) < Tower.MAX_HEIGHT){
            neighborCells.add(workerPosition);
        }
        return neighborCells;
    }
}
