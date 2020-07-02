package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.common.Message;
import it.polimi.ingsw.PSP38.common.Protocol;
import it.polimi.ingsw.PSP38.server.model.*;
import it.polimi.ingsw.PSP38.server.controller.divinityCards.*;
import it.polimi.ingsw.PSP38.common.utilities.ArgumentChecker;
import it.polimi.ingsw.PSP38.server.virtualView.ClientHandler;
import it.polimi.ingsw.PSP38.server.virtualView.Server;

import java.io.IOException;
import java.util.*;

/**
 * This class contains all the methods that define the game logic of Santorini
 * @author Maximilien Groh (10683107)
 * @author Matteo Mita (10487862)
 */

public class Controller extends Observable {
    private final List<String> illegalNicknames = new LinkedList<>();
    private final List<DivinityCard.Name> availableDivinityCards = new LinkedList<>(Arrays.asList(DivinityCard.Name.values()));
    private final Game game = new Game();
    private final Map<Player, DivinityCard> playersDivinities = new HashMap<>();

    /**
     * This method is used to check if the nickname chosen by a player is available or not
     * @param nickname is the nickname taken by a player
     * @return the chosen nickname
     * @throws IllegalArgumentException if someone else has picked the same nickname
     */
    private synchronized String checkNickname(String nickname) throws IllegalArgumentException {
        if (illegalNicknames.contains(nickname)) {
            throw new IllegalArgumentException("This nickname is unavailable. Please choose a new one.");
        }
        return nickname;
    }

    /**
     * This method is used to check that the inputted number of players is correct (only while playing with CLI)
     * @param numOfPlayers is the number of players that will take part to a game of Santorini
     * @return the chosen number of players
     * @throws IllegalArgumentException if the number of playes is not between 2 and 3
     */
    private int checkNumOfPlayers(int numOfPlayers) throws IllegalArgumentException {
        return ArgumentChecker.requireBetween(Game.MIN_NUMBER_OF_PLAYERS, Game.MAX_NUMBER_OF_PLAYERS, numOfPlayers);
    }

    /**
     * This method is used to see if the game is full or not
     * @param client is the <code>ClientHandler</code> that is checking for an available place
     * @throws IOException if the game is full
     */
    private synchronized void checkGameFull(ClientHandler client) throws IOException {
        if (game.getTotNumPlayers() > game.getCurrNumPlayers()) {
            client.notifyMessage(Message.WAIT_FOR_FULL_GAME);
            pauseClient(client);
        } else {
            wakeUpAll();
        }
    }

    /**
     * This method is used to check if the card chosen by the player is valid (only while playing with CLI)
     * @param card is the card chosen by the player
     * @return the chosen card, if this is valid
     * @throws IllegalArgumentException if the card has already been chosen by another player, or it doesn't exists
     */
    private synchronized String checkDivinityCard(String card) throws IllegalArgumentException {
        DivinityCard.Name selectedCardEnum;
        try {
            selectedCardEnum = DivinityCard.Name.valueOf(card.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("This divinity card doesn't exist. Please select a new one.");
        }

        if (!availableDivinityCards.contains(selectedCardEnum)) {
            throw new IllegalArgumentException("This divinity card has already been chosen. Please select a new one.");
        }

        return card;
    }

    /**
     *This method is used to pause a client thread
     * @param client is the client that's going to be stopped
     */
    private synchronized void pauseClient(ClientHandler client) {
        try {
            client.setPaused(true);
            while (client.isPaused()) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method wakes up all the threads in wait
     */
    private synchronized void wakeUpAll() {
        Server.wakeUpAll();
        notifyAll();
    }

    /**
     * This method is called to update the position of players on the board, or to delete them from it if they have lost
     * @param hasCurrentPlayerLost is a flag that states if a player has lost or not
     */
    private synchronized void updateTurn(boolean hasCurrentPlayerLost) {
        if (hasCurrentPlayerLost) {
            game.setCurrentBoard(game.getCurrentBoard().withoutWorkers(game.getCurrentPlayerTurn().getColor()));
            game.removePlayer(game.getCurrentPlayerTurn());
            if (game.getCurrNumPlayers() == 1) {
                game.setWinner(game.getCurrentPlayerTurn());
            }
        } else {
            game.updateTurn();
        }
        wakeUpAll();
    }

    /**
     * This method is used to create a new instance of the class corresponding to the chosen card
     * @param selectedCard is the card chosen by the player
     * @return a new instance of the class corresponding to the chosen card
     */
    private DivinityCard stringToStrategy(String selectedCard) {
        switch (selectedCard.toUpperCase()) {
            case "APOLLO":
                return new Apollo();
            case "ARES":
                return new Ares();
            case "ARTEMIS":
                return new Artemis();
            case "ATHENA":
                return new Athena();
            case "ATLAS":
                return new Atlas();
            case "CHARON":
                return new Charon();
            case "DEMETER":
                return new Demeter();
            case "HEPHAESTUS":
                return new Hephaestus();
            case "HERA":
                return new Hera();
            case "HESTIA":
                return new Hestia();
            case "MINOTAUR":
                return new Minotaur();
            case "PAN":
                return new Pan();
            case "PROMETHEUS":
                return new Prometheus();
            case "ZEUS":
                return new Zeus();
            default:
                throw new IllegalArgumentException("Illegal divinity card");
        }
    }

    /**
     * Getter method for the encoded game board
     * @return the current encoded game board
     */
    public List<Byte> getEncodedBoard() {
        return BoardEncoder.bytesForBoard(game.getCurrentBoard());
    }

    /**
     * This method takes care of all of the preliminary operations in a game of Santorini
     * @param client is the client thread that is starting the game
     * @throws IOException
     */
    public void start(ClientHandler client) throws IOException {
        welcomeMessage(client);
        firstPlayerSetNumOfPlayers(client);
        notifyExtraClient(client);
        if (client.getClientNum() <= game.getTotNumPlayers()) {
            game.addPlayer(askNickname(client), askAge(client));
            checkGameFull(client);
            askYoungestPlayerCards(client);
            client.notifyNumPlayers(game.getTotNumPlayers());
            askDivinity(client);
            placeWorkers(client);
            do {
                playGame(client);
            } while (game.getWinner() == null);

            notifyWinner(client);
        }
    }

    /**
     * This method handles the execution of a game of Santorini
     * @param client is the client thread currently playing the game
     * @throws IOException
     */
    private void playGame(ClientHandler client) throws IOException {
        notifyNotYourTurn(client);
        boolean hasCurrentPlayerLost = false;
        if (game.getWinner() == null) {
            Player clientPlayer = game.nicknameToPlayer(client.getNickname());
            DivinityCard clientDivinity = playersDivinities.get(clientPlayer);
            Worker selectedWorker = askWorker(client);

            for (WorkerAction action : clientDivinity.getMoveSequence()) {
                if (!canTakeAction(selectedWorker, clientDivinity, action)) {
                    if (action.isOptional()) {
                        continue;
                    } else {
                        //client.notifyMessage(Message.UNABLE_TO_FINISH_TURN);
                        client.notifyEndGame(Protocol.CANT_MOVE);
                        hasCurrentPlayerLost = true;
                        break;
                    }
                }
                Cell previousPosition = selectedWorker.getPosition();
                selectedWorker = selectedWorker.withPosition(askWorkerAction(client, selectedWorker, clientDivinity, action));

                if (clientDivinity.isWinner(game.getCurrentBoard(), previousPosition, selectedWorker.getPosition())) {
                    boolean cantWin = false;
                    for (Player p : game.getOpponents()) {
                        cantWin = playersDivinities.get(p).blockOpponentWinningCondition(selectedWorker.getPosition());
                    }
                    if (!cantWin) {
                        game.setWinner(clientPlayer);
                        break;
                    }
                }
            }
        }
        updateTurn(hasCurrentPlayerLost);
    }

    /**
     * Getter method for the current number of players of the current game
     * @return the current number of players in the game
     */
    public int getTotNumPlayers() {
        return game.getTotNumPlayers();
    }

    /**
     * This method sends to the client that has requested it a welcome message (only if playing from CLI)
     * @param client is the current client thread
     * @throws IOException
     */
    private void welcomeMessage(ClientHandler client) throws IOException {
        client.notifyMessage(Message.WELCOME);
    }

    /**
     * This method is used to ask the client to insert the current number of players, if he's the first client to connect to the server
     * @param client
     * @throws IOException
     */
    private void firstPlayerSetNumOfPlayers(ClientHandler client) throws IOException {
        if (client.getClientNum() == 1) {
            client.notifyMessage(Message.INSERT_NUM_PLAYERS);
            game.setTotNumPlayers(client.askInt(this::checkNumOfPlayers, Message.ILLEGAL_INT));
            wakeUpAll();
        } else if (game.getTotNumPlayers() == 0) {
            client.notifyMessage(Message.WAIT_FOR_NUM_PLAYERS);
            pauseClient(client);
        }
    }

    /**
     * This method is used to notify the client that the game is full, and therefore it has to wait
     * @param client
     * @throws IOException
     */
    private void notifyExtraClient(ClientHandler client) throws IOException {
        if (client.getClientNum() > game.getTotNumPlayers()) {
            client.notifyEndGame(Protocol.TOO_LATE);
            //client.notifyMessage(Message.GAME_FULL);
            //client.notifyMessage("If you want to see the match stay connected");
            pauseClient(client);
        }
    }

    /**
     * This method requests the client to provide a nickname
     * @param client
     * @return the nickname chosen
     * @throws IOException
     */
    private String askNickname(ClientHandler client) throws IOException {
        client.notifyMessage(Message.CHOOSE_NICKNAME);
        String nickname = client.askString(this::checkNickname, Message.ILLEGAL_NICKNAME);
        illegalNicknames.add(nickname);
        client.setNickname(nickname);

        return nickname;
    }

    /**
     * This method requests the client to provide the player's age
     * @param client
     * @return the player's age
     * @throws IOException
     */
    private int askAge(ClientHandler client) throws IOException {
        client.notifyMessage(Message.SET_AGE);
        return client.askInt(ArgumentChecker::checkAge, Message.ILLEGAL_INT);
    }

    /**
     * This methods asks to to youngest player to choose the cards that will be used in the game
     * @param client
     * @throws IOException
     */
    private void askYoungestPlayerCards(ClientHandler client) throws IOException {
        if (game.getCurrentPlayerTurn().getNickname().equals(client.getNickname())) {
            List<DivinityCard.Name> selectedDivinityCards = new LinkedList<>();
            for (int i = 0; i < game.getTotNumPlayers(); ++i) {
                displayAvailableDivinities(client);
                String card = client.askString(this::checkDivinityCard, Message.ILLEGAL_DIVINITY);
                DivinityCard.Name cardEnum = DivinityCard.Name.valueOf(card.toUpperCase());
                selectedDivinityCards.add(cardEnum);
                availableDivinityCards.remove(cardEnum);
            }
            availableDivinityCards.clear();
            availableDivinityCards.addAll(selectedDivinityCards);
            updateTurn(false);
        } else {
            client.notifyMessageString(game.getCurrentPlayerTurn().getNickname());
            client.notifyMessage(Message.WAIT_FOR_DIVINITIES);
            pauseClient(client);
        }
    }

    /**
     * This method asks the player to choose between one of the cards chosen by the youngest player
     * @param client
     * @throws IOException
     */
    private void askDivinity(ClientHandler client) throws IOException {
        notifyNotYourTurn(client);
        displayAvailableDivinities(client);
        String card = client.askString(this::checkDivinityCard, Message.ILLEGAL_DIVINITY);
        DivinityCard.Name cardEnum = DivinityCard.Name.valueOf(card.toUpperCase());
        playersDivinities.put(game.getCurrentPlayerTurn(), stringToStrategy(card));
        availableDivinityCards.remove(cardEnum);
        updateTurn(false);
    }

    /**
     * This method is used to notify the client that is not his turn
     * @param client
     * @throws IOException
     */
    private void notifyNotYourTurn(ClientHandler client) throws IOException {
        while (!game.getCurrentPlayerTurn().getNickname().equals(client.getNickname())) {
            if (game.getWinner() == null) {
                client.notifyMessageString(game.getCurrentPlayerTurn().getNickname());
                client.notifyMessage(Message.NOT_YOUR_TURN);
            }
            pauseClient(client);
        }
    }

    /**
     * This method is used to display the available divinities to the client
     * @param client
     * @throws IOException
     */
    private void displayAvailableDivinities(ClientHandler client) throws IOException {
        client.notifyMessageString(client.getNickname());
        client.notifyMessage(Message.DISPLAY_DIVINITY_MESSAGE);
        StringBuilder message = new StringBuilder();
        availableDivinityCards.forEach(card -> message.append(card).append("\n"));
        client.notifyMessageString(message.toString());
        client.notifyMessage(Message.DISPLAY_AVAILABLE_DIVINITIES);
    }

    /**
     * This method is used for the initial placing of the player's workers
     * @param client
     * @throws IOException
     */
    private void placeWorkers(ClientHandler client) throws IOException {
        notifyNotYourTurn(client);
        Map<String, String> map = new HashMap<>();
        for (Player p : playersDivinities.keySet()) {
            map.put(p.getNickname(), playersDivinities.get(p).toString());
        }
        System.out.println(playersDivinities);
        System.out.println(map);
        client.notifyPlayersDivinities(map);
        displayAllClients();
        client.notifyMessage(Message.PLACE_YOUR_WORKERS);
        Player clientPlayer = game.nicknameToPlayer(client.getNickname());
        for (int i = 0; i < Game.WORKERS_PER_PLAYER; ++i) {
            client.notifyMessageString(String.valueOf(i + 1));
            client.notifyMessage(Message.PLACE_A_WORKER);
            Board newBoard;
            do {
                Cell cell = askCell(client);
                try {
                    ArgumentChecker.checkWorker(cell, game.getCurrentBoard());
                    newBoard = game.getCurrentBoard().withWorker(new Worker(clientPlayer.getColor(), cell));
                    displayAllClients();
                    break;
                } catch (IllegalArgumentException e) {
                    client.notifyMessageString(e.getMessage());
                    client.notifyMessage(Message.ILLEGAL_ARGUMENT);
                }
            } while (true);
            game.setCurrentBoard(newBoard);
            displayAllClients();
        }
        updateTurn(false);
    }

    /**
     * This method asks the client to specify a cell's coordinates
     * @param client
     * @return the chosen cell
     * @throws IOException
     */
    private Cell askCell(ClientHandler client) throws IOException {
        int x;
        int y;
        do {
            try {
                client.notifyMessage(Message.SET_CELL_COLUMN_COORD);
                x = client.askInt(ArgumentChecker::checkXCoordinate, Message.ILLEGAL_INT);
                client.notifyMessage(Message.SET_CELL_ROW_COORD);
                y = client.askInt(ArgumentChecker::checkYCoordinate, Message.ILLEGAL_INT);
                return new Cell(x, y);
            } catch (IllegalArgumentException e) {
                client.notifyMessageString(e.getMessage());
                client.notifyMessage(Message.ILLEGAL_ARGUMENT);
            }
        } while (true);
    }

    private void displayAllClients() {
        setChanged();
        notifyObservers();
    }

    /**
     * This method notifies a client that his player has won the game
     * @param client
     * @throws IOException
     */
    private void notifyWinner(ClientHandler client) throws IOException {
        if (game.getWinner().getNickname().equals(client.getNickname())) {
            client.notifyMessage(Message.YOU_WIN);
        } else {
            client.notifyMessageString(game.getWinner().getNickname());
            client.notifyMessage(Message.YOU_LOSE);
        }
    }

    /**
     * This method is called when the player associated to the client has to move a worker
     * @param client
     * @return
     * @throws IOException
     */
    private Worker askWorker(ClientHandler client) throws IOException {
        client.notifyMessage(Message.SELECT_WORKER);
        Player clientPlayer = game.nicknameToPlayer(client.getNickname());
        Cell cellUnderWorker;
        Worker workerSelected;
        do {
            try {
                cellUnderWorker = askCell(client);
                workerSelected = game.getCurrentBoard().workerAt(cellUnderWorker);
            } catch (NullPointerException e) {
                client.notifyMessageString(e.getMessage());
                client.notifyMessage(Message.ILLEGAL_ARGUMENT);
                continue;
            }

            if (clientPlayer.getColor() != workerSelected.getColor()) {
                client.notifyMessage(Message.WORKER_NOT_YOURS);
            } else {
                return workerSelected;
            }
        } while (true);
    }

    /**
     * This method is called if a worker is in the condition of using a special ability, thus asking the player to
     * choose if using or not this ability
     * @param client the client associated to the player
     * @param selectedWorker the intersted worker
     * @param clientDivinty the divinity associated to the player
     * @param action the optional action
     * @return the cell that will be affected by doing this action
     * @throws IOException
     */
    private Cell askWorkerAction(ClientHandler client, Worker selectedWorker, DivinityCard clientDivinty, WorkerAction action) throws IOException {
        Cell workerPosition = selectedWorker.getPosition();
        Board currentBoard = game.getCurrentBoard();
        boolean useAbility = true;
        if (action.isOptional()) {
            client.notifyMessage(Message.ASK_SPECIAL_ACTION);
            String answer = client.askString(ArgumentChecker::checkYesOrNo, Message.ILLEGAL_YES_OR_NO);
            if (answer.equalsIgnoreCase("no")) {
                if (action == WorkerAction.OPTIONAL_ACTION) {
                    return workerPosition;
                } else {
                    useAbility = false;
                }
            }
        }

        do {
            try {
                client.notifyMessage(action.question());
                Cell destinationCell = askCell(client);
                for (Player p : game.getOpponents()) {
                    playersDivinities.get(p).checkOpponentMove(clientDivinty.typeOfAction(action), selectedWorker, destinationCell, currentBoard);
                }
                currentBoard = takeAction(selectedWorker, destinationCell, currentBoard, clientDivinty, action, useAbility, false);
                game.setCurrentBoard(currentBoard);
                displayAllClients();
                return currentBoard.hasWorkerAt(destinationCell) ? destinationCell : workerPosition;
            } catch (IllegalArgumentException e) {
                client.notifyMessageString(e.getMessage());
                client.notifyMessage(Message.ILLEGAL_ARGUMENT);
            }
        } while (true);
    }

    /**
     * This method verifies if a worker is able or not to do a particular action
     * @param selectedWorker
     * @param clientDivinty
     * @param action
     * @return
     */
    private boolean canTakeAction(Worker selectedWorker, DivinityCard clientDivinty, WorkerAction action) {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLUMNS; ++col) {
                Cell cell = new Cell(col, row);
                Board board = game.getCurrentBoard();
                try {
                    takeAction(selectedWorker, cell, board, clientDivinty, action, true, true);
                    return true;
                } catch (IllegalArgumentException ignored) {

                }
            }
        }
        return false;
    }

    /**
     * This method is called when a worker executes an action
     * @param selectedWorker the worker that's taking the action
     * @param cell the cell in which the action will be accomplished
     * @param board the current board
     * @param clientDivinty the player's divinity
     * @param action the action that will be done
     * @param useAbility if the ability will be used
     * @param isSimulation if the action won't be pursued for real
     * @return the new board after doing the action
     * @throws IllegalArgumentException
     */
    private Board takeAction(Worker selectedWorker, Cell cell, Board board, DivinityCard clientDivinty, WorkerAction action, boolean useAbility, boolean isSimulation) throws IllegalArgumentException {
        switch (action) {
            case MOVE:
                return clientDivinty.move(selectedWorker, cell, board, isSimulation);
            case BUILD:
                return clientDivinty.build(selectedWorker, cell, board, isSimulation);
            case OPTIONAL_ACTION:
                return ((OptionalAction) clientDivinty).optionalAction(selectedWorker, cell, board, isSimulation);
            case OPTIONAL_ABILITY:
                return ((OptionalAbility) clientDivinty).optionalAbility(useAbility, selectedWorker, cell, board, isSimulation);
            default:
                throw new IllegalArgumentException("WorkerAction " + action + " unknown.");
        }
    }
}