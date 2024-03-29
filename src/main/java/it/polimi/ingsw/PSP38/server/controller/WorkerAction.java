package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.common.Message;

/**
 * Enum representing the actions a worker can take on his turn,
 * depending on the divinity card
 *
 * @author Maximilien Groh (10683107)
 */

public enum WorkerAction {
    MOVE,
    BUILD,
    OPTIONAL_ACTION,
    OPTIONAL_ABILITY;

    public Message question() {
        switch (this) {
            case MOVE:
                return Message.WORKER_MOVE;
            case BUILD:
                return Message.WORKER_BUILD;
            case OPTIONAL_ACTION:
            case OPTIONAL_ABILITY:
                return Message.WORKER_OPTIONAL_ABILITY;
            default:
                return Message.ILLEGAL_ACTION;
        }
    }

    public boolean isOptional() {
        return this == OPTIONAL_ACTION || this == OPTIONAL_ABILITY;
    }
}
