package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.Message;

import java.util.Scanner;

public class GameModeCLI implements GameMode {
    private String customStringRead;
    private Scanner scanner = new Scanner(System.in);
    @Override
    public void decodeMessage(Message m){
        switch (m){
            case WELCOME:
                System.out.println("Welcome to Santorini\n");
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
            case WAIT_FOR_DIVINITIES:
                System.out.println("Please wait for "+customStringRead+
                        " to choose the divinity cards that will be used in this game");
                break;
            case NOT_YOUR_TURN:
                System.out.println("It's "+customStringRead+"'s turn, please wait");
                break;
            case PLACE_YOUR_WORKERS:
                System.out.println("Please place all of your workers on the board");
                break;
            case PLACE_A_WORKER:
                System.out.println("Place your worker number "+customStringRead);
                break;
            case SET_CELL_X_COORD:
                System.out.println("Please insert the cell's x coordinate");
                break;
            case SET_CELL_Y_COORD:
                System.out.println("Please insert the cell's y coordinate");
                break;
            case YOU_WIN:
                System.out.println("You are the winner!");
                break;
            case YOU_LOSE:
                System.out.println("You lose! The winner is "+customStringRead);
            case SELECT_WORKER:
                System.out.println("Player, please select the worker you want to move");//inserire nickname giocatore
                break;
            case WORKER_NOT_YOURS:
                System.out.println("Player, this worker doesn't belong to you");
                break;
            case ASK_SPECIAL_ACTION:
                System.out.println("Do you want to use your special ability");
                break;
            case ILLEGAL_NICKNAME:
                System.out.println("This nickname is unavailable, please choose another one");
                break;
            case WAIT_FOR_FULL_GAME:
                System.out.println("Hold on, all the players will be ready in a few seconds");
                break;
            case DIVINITY_CARD_NOT_EXISTS:
                System.out.println("This divinity card doesn't exist. Please select a new one");
                break;
            case DIVINITY_CARD_CHOSEN:
                System.out.println("This divinity card has already been chosen. Please select a new one");
                break;
            case ILLEGAL_YES_OR_NO:
                System.out.println("Please answer with either \"yes\" or \"no\"");
                break;
            case ILLEGAL_DIVINITY:
                System.out.println("Illegal divinity card");
                break;
            case DISPLAY_DIVINITY_MESSAGE:
                System.out.println(customStringRead+", please select a divinity card from this list :\n");
                break;
            case DISPLAY_AVAILABLE_DIVINITIES:
                System.out.println(customStringRead);
                break;
            case ILLEGAL_INT:
                System.out.println("Integer not recognized exception: "+customStringRead);
                break;
            case ILLEGAL_STRING:
                System.out.println("String not recognized exception: "+customStringRead);
                break;
            case UNABLE_TO_FINISH_TURN:
                System.out.println("You can't finish your turn. You lose.");
                break;
            case ILLEGAL_ARGUMENT:
                System.out.println("Illegal argument exception: "+customStringRead);
                break;
            case WORKER_MOVE:
                System.out.println("Select the cell where you want to move");
                break;
            case WORKER_BUILD:
                System.out.println("Select the cell where you want to build");
                break;
            case WORKER_OPTIONAL_ABILITY:
                System.out.println("Select the cell where you want to DAFARE");
                break;
            case ILLEGAL_ACTION:
                System.out.println("Unknown worker action");
                break;
            case WAIT:
                System.out.println("Please wait");
            default:
                System.out.println("Message not recognized");
                break;
        }
    }

    @Override
    public void updateCustomString() {
        customStringRead=Client.getCustomString();
    }

    @Override
    public String nextInput() {
        return scanner.nextLine();
    }

    @Override
    public void displayBoard() {
        BoardPrinter.printBoard(ServerHandler.readBoard());
    }
}
