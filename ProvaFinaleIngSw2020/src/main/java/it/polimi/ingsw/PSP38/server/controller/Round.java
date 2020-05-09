package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Player;
import it.polimi.ingsw.PSP38.server.model.Worker;
import it.polimi.ingsw.PSP38.server.virtualView.ClientHandler;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Round {

    Player player;
    DivinityCard divinityCard;
    ClientHandler client;

    public Round(Player player, DivinityCard divinityCard, ClientHandler client) {
        this.player = player;
        this.divinityCard = divinityCard;
        this.client = client;
    }

    public Board play(Board currentBoard,Controller controller)throws IOException {

        client.notifyMessage("select the worker who want to move");
        Cell cellUnderWorker = controller.askCell(client);
        Worker workerSelected = currentBoard.getWorkersPositions().get(cellUnderWorker);
        Set<Cell> possibleCellsMove = divinityCard.preMove(workerSelected, currentBoard);
        client.notifyMessage("these are the cells where you can move your worker:");
        client.notifyMessage(possibleCellsMove.toString());
        client.notifyMessage("insert the coordinates of the cell where you want to place your worker");
        Cell cellDestination = controller.askCell(client);
        Board boardAfterMove = divinityCard.move(workerSelected, cellDestination, currentBoard);
        Worker workerMoved = boardAfterMove.getWorkersPositions().get(cellDestination);
        Set<Cell> possibleCellsBuild = divinityCard.preBuild(workerMoved, boardAfterMove);
        client.notifyMessage("these are the cells where you can build:");
        client.notifyMessage(possibleCellsBuild.toString());
        client.notifyMessage("insert the coordinates of the cell where you want your worker to build");
        Cell cellDestinationBuild = controller.askCell(client);
        return divinityCard.build(workerMoved, cellDestinationBuild, boardAfterMove);
    }
}