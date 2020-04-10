package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.model.*;

import java.util.List;
import java.util.Map;

public interface DivinityCard {
    default List<Cell> preMove(Worker w, Board b){
        List<Cell> neighborCells=b.neighborsCells(w.getPosition());
        Map<Cell, Worker> workersPositions=b.getWorkersPositions();
        neighborCells.removeIf(c -> workersPositions.containsKey(c) || c.hasDome() || c.getTowerHeight() > w.getPosition().getTowerHeight() + 1);
        //Removes the current cell from the neighborCells list if the cell is already occupied or there is a complete tower or a tower whose level is
        //more than player+1
        return neighborCells;
    }
    default Board move(Worker w, Cell destinationCell, Board b){
        Cell initialCell = w.getPosition();
        Board newBoard = b.withWorker(w, destinationCell);
        return newBoard;
    }
    default List<Cell> preBuild(Worker w, Board b){
        List<Cell> neighborCellsBuild = b.neighborsCells(w.getPosition());
        Map<Cell, Worker> workersPositions=b.getWorkersPositions();
        neighborCellsBuild.removeIf(c -> workersPositions.containsKey(c) || c.hasDome());
        //Removes the current cell from the neighborCells list if the cell is already occupied or there is a complete tower or a tower whose level is
        //more than player+1
        return neighborCellsBuild;
    }
    default Board build(Cell buildingCell, Board b){
        Cell newCell = buildingCell.getTowerHeight() == 3 ? buildingCell.withDome() : buildingCell.withTowerHeight(buildingCell.getTowerHeight() + 1);
        Board newBoard=b.withCell(newCell);
        return newBoard;
    }
}