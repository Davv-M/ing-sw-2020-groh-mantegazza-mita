package it.polimi.ingsw.PSP38.common;

/**
 *Enumeration class that contains all the possible messages that the server can send to the clients
 * @author Davide Mantegazza (10568661)
 */
public enum Message {
    WELCOME,
    INSERT_NUM_PLAYERS,
    WAIT_FOR_NUM_PLAYERS,
    GAME_FULL,
    CHOOSE_NICKNAME,
    SET_AGE,
    WAIT_FOR_DIVINITIES,
    NOT_YOUR_TURN,
    PLACE_YOUR_WORKERS,
    PLACE_A_WORKER,
    SET_CELL_COORDS,
    YOU_WIN,
    YOU_LOSE,
    SELECT_WORKER,
    WORKER_NOT_YOURS,
    ASK_SPECIAL_ACTION,
}