package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

public interface OptionalAction {
    Board optionalAction(Worker worker, Cell destinationCell, Board currentBoard);
}
