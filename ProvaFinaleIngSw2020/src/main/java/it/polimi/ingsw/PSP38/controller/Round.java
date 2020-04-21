package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.model.Board;
import it.polimi.ingsw.PSP38.model.Cell;
import it.polimi.ingsw.PSP38.model.Player;
import it.polimi.ingsw.PSP38.model.Worker;

import it.polimi.ingsw.PSP38.virtualView.GameRequest;

import java.util.List;


public class Round{

    Player player;
    StrategyDivinityCard strategy;
    public Round ( Player player , StrategyDivinityCard divinityCard){
        this.player = player;
        strategy = divinityCard;
    }


    public Board play(Board currentBoard){
        GameRequest gr = new GameRequest();
        int[] xy;

        xy = gr.requestCoordinates(" insert the coordinates of the worker you want to move - example:(column,row) ", player);
        Cell cellUnderWorker = currentBoard.cellAt(xy[0],xy[1]);
        Worker workerSelected = currentBoard.workerAt(cellUnderWorker);
        List<Cell> possibleCellsMove = strategy.preMove(workerSelected, currentBoard);
        gr.showPossibleCells(" these are the cells where you can move your worker :", player, possibleCellsMove);

        xy = gr.requestCoordinates(" insert the coordinates of the cell where you want to place your worker - example:(column,row) ", player);
        Cell cellDestination = currentBoard.cellAt(xy[0],xy[1]);
        Board boardAfterMove = strategy.move(workerSelected, cellDestination, currentBoard);


        Cell cellUnderWorkerMoved = boardAfterMove.cellAt(xy[0], xy[1]);
        Worker workerMoved = boardAfterMove.workerAt(cellUnderWorkerMoved);
        List<Cell> possibleCellsBuild = strategy.preBuild(workerMoved, boardAfterMove);
        gr.showPossibleCells(" these are the cells where your worker can build :", player, possibleCellsBuild);

        xy = gr.requestCoordinates(" insert the coordinates of the cell where you want your worker to build - example:(column,row) ", player);
        Cell cellDestinationBuild = boardAfterMove.cellAt(xy[0],xy[1]);
        Board boardAfterBuild = strategy.build(cellDestinationBuild, boardAfterMove);


        return boardAfterBuild;
    }



}
