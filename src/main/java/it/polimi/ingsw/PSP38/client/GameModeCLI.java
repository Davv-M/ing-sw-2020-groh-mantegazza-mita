package it.polimi.ingsw.PSP38.client;

import it.polimi.ingsw.PSP38.common.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class GameModeCLI implements GameMode {
    private String customStringRead;
    private final Scanner scanner = new Scanner(System.in);
    private Map<String, String> playersDivinities = new HashMap<>();
    private static String nickname = "anonymous";

    /**
     * This method is used to decode messages coming from the server
     *
     * @param m is the message coming from the server
     */
    @Override
    public void decodeMessage(Message m) {
        switch (m) {
            case WELCOME:
                System.out.println("Welcome to Santorini\n");
                break;
            case INSERT_NUM_PLAYERS:
                System.out.println("You are the first player to join this game. Please insert the number of players");
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
                System.out.println("How old are you?");
                break;
            case WAIT_FOR_DIVINITIES:
                System.out.println("Please wait for " + customStringRead +
                        " to choose the divinity cards that will be used in this game");
                break;
            case NOT_YOUR_TURN:
                System.out.println("It's " + customStringRead + "'s turn, please wait");
                break;
            case PLACE_YOUR_WORKERS:
                System.out.println("Please place all of your workers on the board");
                break;
            case PLACE_A_WORKER:
                System.out.println("Place your worker number " + customStringRead);
                break;
            case SET_CELL_COLUMN_COORD:
                System.out.println("Please insert the cell's column coordinate");
                break;
            case SET_CELL_ROW_COORD:
                System.out.println("Please insert the cell's row coordinate");
                break;
            case YOU_WIN:
                System.out.println("You are the winner!");
                break;
            case YOU_LOSE:
                System.out.println("You lose! The winner is " + customStringRead);
            case SELECT_WORKER:
                System.out.println("Please select the worker you want to move");
                break;
            case WORKER_NOT_YOURS:
                System.out.println("This worker doesn't belong to you");
                break;
            case ASK_SPECIAL_ACTION:
                System.out.println("Do you want to use your special ability?");
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
                System.out.println(customStringRead + ", please select a divinity card from this list :\n");
                break;
            case DISPLAY_AVAILABLE_DIVINITIES:
                System.out.println(customStringRead);
                break;
            case ILLEGAL_INT:
                System.out.println("Integer not recognized: " + customStringRead);
                break;
            case ILLEGAL_STRING:
                System.out.println("String not recognized: " + customStringRead);
                break;
            case UNABLE_TO_FINISH_TURN:
                System.out.println("You can't finish your turn. You lose.");
                break;
            case ILLEGAL_ARGUMENT:
                System.out.println("Illegal argument: " + customStringRead);
                break;
            case WORKER_MOVE:
                System.out.println("Select the cell where you want to move");
                break;
            case WORKER_BUILD:
                System.out.println("Select the cell where you want to build");
                break;
            case WORKER_OPTIONAL_ABILITY:
                //workerOptionalAbility();
                break;
            case ILLEGAL_ACTION:
                System.out.println("Unknown worker action");
                break;
            case WAIT:
                System.out.println("Please wait");
                break;
            case SERVER_UNREACHEABLE:
                System.out.println("Server unreachable");
                System.exit(0);
                break;
            case CONNECTED_TO_SERVER:
                System.out.println("Connected");
                break;
            case CLIENT_LOST:
                System.out.println("your challenger lost connection, please restart app");
                System.exit(0);
                break;
            case CANT_MOVE:
                System.out.println("you can't move, You Lose!");
                break;
            case SERVER_LOST:
                System.out.println("connection lost with server, please restart app ");
                System.exit(0);
                break;
            default:
                System.out.println("Message not recognized");
                break;
        }
    }

    private void workerOptionalAbility() {
        switch (playersDivinities.get(nickname).toUpperCase()) {
            case "ARES":
                System.out.println("Select the cell where you want to remove a tower block.");
                break;
            case "ARTEMIS":
                System.out.println("Select the cell where you want to move.");
                break;
            case "ATLAS":
            case "DEMETER":
            case "HEPHAESTUS":
            case "HESTIA":
            case "PROMETHEUS":
                System.out.println("Select the cell where you want to build.");
                break;
            case "CHARON":
                System.out.println("Select the worker you want to push on the other side.");
                break;
            default:
        }
    }


    /**
     * This method is used to save a non - standard string coming from the server
     */
    @Override
    public void updateCustomString() {
        customStringRead = Client.getCustomString();
    }

    /**
     * This method is used to update the next data that will be inputted onto the server
     *
     * @return the inputted string
     */
    @Override
    public String nextInput() {
        return scanner.nextLine();
    }

    /**
     * This method is used to display the game board in the client
     */
    @Override
    public void displayBoard() {
        BoardPrinter.printBoard(ServerHandler.readBoard());
    }

    /**
     * This method is used to save the string coming from the client
     * @param dataRead is the string read by the client
     */
    @Override
    public void setStringRead(String dataRead) {
    }

    /**
     * This method is used to comunicate the number of players
     * @param numOfPlayers
     */
    @Override
    public void setNumOfPlayers(int numOfPlayers) {
        System.out.println("There will be " + numOfPlayers + " players in the game.");
    }

    /**
     * This method is used to print out the chosen divinities
     * @param playersDivinities
     */
    @Override
    public void setPlayersDivinities(Map<String, String> playersDivinities) {
        this.playersDivinities = playersDivinities;
        System.out.println("These are the chosen divinities :");
        for (String player : this.playersDivinities.keySet()) {
            System.out.println(player + " : " + this.playersDivinities.get(player));
        }
    }
}
