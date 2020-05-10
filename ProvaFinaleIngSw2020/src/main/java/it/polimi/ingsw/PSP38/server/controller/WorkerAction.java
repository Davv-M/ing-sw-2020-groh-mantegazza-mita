package it.polimi.ingsw.PSP38.server.controller;

public enum WorkerAction {
    MOVE,
    BUILD,
    SPECIAL_MOVE,
    SPECIAL_BUILD;


    @Override
    public String toString() {
        return this == MOVE || this == SPECIAL_MOVE ? "move" : "build";
    }
}
