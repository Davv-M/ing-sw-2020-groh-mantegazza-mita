package it.polimi.ingsw.PSP38.server.controller;

public enum WorkerAction {
    MOVE,
    BUILD,
    OPTIONAL_ACTION,
    OPTIONAL_ABILITY;

    public String question(){
        switch (this){
            case MOVE:
                return "Select the cell where you want to move";
            case BUILD:
                return "Select the cell where you want to build";
            case OPTIONAL_ACTION:
                return "Select the cell where you want to DAFARE";
            case OPTIONAL_ABILITY:
                return "Select the cell where you want to DAFARE";
            default:
                throw new AssertionError("unknown WorkerAction");
        }
    }
}
