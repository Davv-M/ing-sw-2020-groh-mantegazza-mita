package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.debug.BoardPrinter;
import it.polimi.ingsw.PSP38.model.Board;
import it.polimi.ingsw.PSP38.model.Cell;
import it.polimi.ingsw.PSP38.model.Player;
import it.polimi.ingsw.PSP38.model.Worker;

import java.util.List;
import java.util.Scanner;

public class Round {
    Player player;
    StrategyDivinityCard strategy;
    public Round ( Player player , StrategyDivinityCard divinityCard){
        this.player = player;
        strategy = divinityCard;
    }


    public Board play(Board currentBoard){
        Scanner s = new Scanner(System.in);
        System.out.println(player.getNickname()+ " insert the coordinates of the worker you want to move");
        int x = s.nextInt();
        int y = s.nextInt();
        Cell cellUnderWorker = currentBoard.cellAt(x, y);
        Worker workerSelected = currentBoard.workerAt(cellUnderWorker);
        List<Cell> possibleCellsMove = strategy.preMove(workerSelected, currentBoard);
        System.out.println(player.getNickname()+ " these are the cells where you can move your worker :");
        for ( Cell c: possibleCellsMove){
            System.out.println(c);
        }


        System.out.println(player.getNickname()+ " insert the coordinates of the cell where you want to place your worker");
        x = s.nextInt();
        y = s.nextInt();
        Cell cellDestination = currentBoard.cellAt(x,y);
        Board boardAfterMove = strategy.move(workerSelected, cellDestination, currentBoard);


        Cell cellUnderWorkerMoved = boardAfterMove.cellAt(x, y);
        Worker workerMoved = boardAfterMove.workerAt(cellUnderWorkerMoved);
        List<Cell> possibleCellsBuild = strategy.preBuild(workerMoved, boardAfterMove);
        System.out.println(player.getNickname()+" these are the cells where your worker can build :");
        for ( Cell c2: possibleCellsBuild){
            System.out.println(c2);
        }


        System.out.println(player.getNickname()+" insert the coordinates of the cell where you want your worker to build");
        x = s.nextInt();
        y = s.nextInt();
        Cell cellDestinationBuild = boardAfterMove.cellAt(x,y);
        Board boardAfterBuild = strategy.build(cellDestinationBuild, boardAfterMove);
        BoardPrinter.printBoard(boardAfterBuild);
        return boardAfterBuild;
    }

}
