package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Player;
import it.polimi.ingsw.PSP38.server.model.Worker;
import it.polimi.ingsw.PSP38.server.virtualView.ClientHandler;

import java.io.IOException;
import java.util.List;

public class Round {

    Player player;
    StrategyDivinityCard strategy;
    ClientHandler client;

    public Round(Player player, StrategyDivinityCard divinityCard, ClientHandler client) {
        this.player = player;
        strategy = divinityCard;
        this.client = client;
    }

    public Board play(Board currentBoard,Controller controller)throws IOException {

        client.notifyMessage("select the worker who want to move");
        Cell cellUnderWorker = controller.askCell(client);
        Worker workerSelected = currentBoard.workerAt(cellUnderWorker);
        List<Cell> possibleCellsMove = strategy.preFirstMove(workerSelected, currentBoard);
        client.notifyMessage("these are the cells where you can move your worker:");
        client.notifyMessage(possibleCellsMove.toString());
        client.notifyMessage("insert the coordinates of the cell where you want to place your worker");
        Cell cell = controller.askCell(client);
        Cell cellDestination = currentBoard.cellAt(cell.getX(),cell.getY());
        Board boardAfterFirstMove = strategy.firstMove(workerSelected, cellDestination, currentBoard);




        Cell cellUnderWorkerMoved = boardAfterMove.cellAt(cellDestination.getX(),cellDestination.getY());
        Worker workerMoved = boardAfterMove.workerAt(cellUnderWorkerMoved);
        List<Cell> possibleCellsBuild = strategy.preFirstBuild(workerMoved, boardAfterMove);
        client.notifyMessage("these are the cells where you can build:");
        client.notifyMessage(possibleCellsBuild.toString());
        client.notifyMessage("insert the coordinates of the cell where you want your worker to build");
        Cell cell1 = controller.askCell(client);
        Cell cellDestinationBuild = boardAfterMove.cellAt(cell1.getX(),cell1.getY());
        Board boardAfterBuild = strategy.firstBuild(cellDestinationBuild, boardAfterMove);
        return boardAfterBuild;
    }
}