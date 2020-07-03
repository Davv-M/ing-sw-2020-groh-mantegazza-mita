package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.server.model.Board;
import it.polimi.ingsw.PSP38.server.model.Cell;
import it.polimi.ingsw.PSP38.server.model.Worker;

/**
 * Interface implemented by divinity cards that can decide wether to take action
 * using their special ability or to take action in the normal way.
 *
 * @author Maximilien Groh (10683107)
 */

public interface OptionalAbility {
    Board optionalAbility(boolean useAbility, Worker worker, Cell destinationCell, Board currentBoard, boolean isSimulation);
}
