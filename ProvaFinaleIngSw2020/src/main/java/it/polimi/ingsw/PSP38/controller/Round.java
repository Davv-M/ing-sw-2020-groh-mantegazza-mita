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
    DivinityCard strategy;
    public Round ( Player p , DivinityCard dc){
        player = p;
        strategy = dc;
    }
    public Board play(Board currentBoard){
        Scanner s = new Scanner(System.in);
        System.out.println("inserisci le cordinate del worker che vuoi muovere");
        int x;
        int y;
        x = s.nextInt();
        y = s.nextInt();
        Cell cellUnderWorker = currentBoard.cellAt(x, y);
        Worker worker = currentBoard.workerAt(cellUnderWorker);

        List<Cell> possibleCells = strategy.preMove(worker, currentBoard);
        System.out.println(" queste sono le celle: ");
        for ( Cell c: possibleCells){
            System.out.println(c);
        }
        System.out.println("inserisci le cordinate della cella in cui muoversi");
        x = s.nextInt();
        y = s.nextInt();
        Cell cellDestination = currentBoard.cellAt(x,y);




        Board b = strategy.move(worker, cellDestination, currentBoard);
        Cell cellUnderWorkerMoved = b.cellAt(x, y);
        Worker workerMoved = b.workerAt(cellUnderWorkerMoved);
        List<Cell> possibleCellsBuild = strategy.preBuild(workerMoved, b);
        System.out.println(" queste sono le celle dove puoi costruire: ");
        for ( Cell c2: possibleCellsBuild){
            System.out.println(c2);
        }
        System.out.println("inserisci le cordinate della cella dove vuoi costruire");
        x = s.nextInt();
        y = s.nextInt();
        Cell cellDestinationBuild = b.cellAt(x,y);
        b = strategy.build(cellDestinationBuild, b);
        BoardPrinter.printBoard(b);
        return b;
    };

}
