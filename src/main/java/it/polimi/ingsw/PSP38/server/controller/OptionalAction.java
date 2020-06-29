package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

/**
 * Interface implemented by divinity cards that can decide whether or not to
 * use their special ability
 *
 * @author Maximilien Groh (10683107)
 */

public interface OptionalAction {
    Board optionalAction(Worker worker, Cell destinationCell, Board currentBoard);
}
