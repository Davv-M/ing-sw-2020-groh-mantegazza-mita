package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.Message;

public class MessageDecoderCLI implements MessageDecoder{

    @Override
    public void decodeMessage(Message m){
        switch (m){
            case WELCOME:
                System.out.println("Welcome to Santorini");
                break;
            case INSERT_NUM_PLAYERS:
                System.out.println("You are the first player to join this game. Please insert the number of players (between 2 and 3)");
                break;
            case WAIT_FOR_NUM_PLAYERS:
                System.out.println("Please wait for the first player to select the number of players.");
                break;
            case GAME_FULL:
                System.out.println("The game is currently full, please try later. If you want to see the match stay connected");
                break;
            case CHOOSE_NICKNAME:
                System.out.println("Choose your nickname");
                break;
            case SET_AGE:
                System.out.println("How old are you? (integer between 8 and 99)");
                break;
            case WAIT_FOR_DIVINITIES: //attendi scelta divinità
            case NOT_YOUR_TURN: //segnala che è il turno di qualcun altro
            case PLACE_YOUR_WORKERS:
                System.out.println("Please place all of your workers on the board");
                break;
            case PLACE_A_WORKER://posiziona un worker specifico
            case SET_CELL_COORDS:
                System.out.println("Please insert the cell's coordinates (x, y)");
                break;
            case YOU_WIN:
                System.out.println("You are the winner!");
                break;
            case YOU_LOSE://segnala chi ha vinto
            case SELECT_WORKER:
                System.out.println("Player, please select the worker you want to move");//inserire nickname giocatore
                break;
            case WORKER_NOT_YOURS:
                System.out.println("Player, this worker doesn't belong to you");
                break;
            case ASK_SPECIAL_ACTION:
                System.out.println("Do you want to use your special ability");
            default:
                System.out.println("Message not recognized");
        }
    }
}
