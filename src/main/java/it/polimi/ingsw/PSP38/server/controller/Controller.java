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
 *
 * @author Maximilien Groh (10683107)
 * @author Matteo Mita (10487862)
 */

public class Controller extends Observable {
    private final List<String> illegalNicknames = new LinkedList<>();
    private final List<DivinityCard.Name> availableDivinityCards = new LinkedList<>(Arrays.asList(DivinityCard.Name.values()));
    private final Game game = new Game();
    private final Map<Player, DivinityCard> playersDivinities = new HashMap<>();

    private synchronized String checkNickname(String nickname) throws IllegalArgumentException {
        if (illegalNicknames.contains(nickname)) {
            throw new IllegalArgumentException("This nickname is unavailable. Please choose a new one.");
        }
        return nickname;
    }

    private int checkNumOfPlayers(int numOfPlayers) throws IllegalArgumentException {
        return ArgumentChecker.requireBetween(Game.MIN_NUMBER_OF_PLAYERS, Game.MAX_NUMBER_OF_PLAYERS, numOfPlayers);
    }

    private int checkAge(int age) throws IllegalArgumentException {
        return ArgumentChecker.requireBetween(Player.MIN_AGE, Player.MAX_AGE, age);
    }

    private synchronized void checkGameFull(ClientHandler client) throws IOException {
        if (game.getTotNumPlayers() > game.getCurrNumPlayers()) {
            client.notifyMessage(Message.WAIT_FOR_FULL_GAME);
            pauseClient(client);
        } else {
            wakeUpAll();
        }
    }

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

    private int checkXCoordinate(int x) throws IllegalArgumentException {
        return ArgumentChecker.requireBetween(0, Board.COLUMNS - 1, x);
    }

    private int checkYCoordinate(int y) throws IllegalArgumentException {
        return ArgumentChecker.requireBetween(0, Board.ROWS - 1, y);
    }

    private synchronized String checkYesOrNo(String answer) throws IllegalArgumentException {
        if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no")) {
            return answer;
        } else {
            throw new IllegalArgumentException("Please answer with either \"yes\" or \"no\".");
        }
    }

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

    private synchronized void wakeUpAll() {
        Server.wakeUpAll();
        notifyAll();
    }

    private synchronized void updateTurn(boolean hasCurrentPlayerLost) {
        if(hasCurrentPlayerLost){
            game.setCurrentBoard(game.getCurrentBoard().withoutWorkers(game.getCurrentPlayerTurn().getColor()));
            game.removePlayer(game.getCurrentPlayerTurn());
            if(game.getCurrNumPlayers() == 1){
                game.setWinner(game.getCurrentPlayerTurn());
            }
        } else {
            game.updateTurn();
        }
        wakeUpAll();
    }

    private DivinityCard stringToStrategy(String selectedCard) {
        DivinityCard.Name selectedCardEnum = DivinityCard.Name.valueOf(selectedCard.toUpperCase());
        switch (selectedCardEnum) {
            case APOLLO:
                return new Apollo();
            case ARES:
                return new Ares();
            case ARTEMIS:
                return new Artemis();
            case ATHENA:
                return new Athena();
            case ATLAS:
                return new Atlas();
            case CHARON:
                return new Charon();
            case DEMETER:
                return new Demeter();
            case HEPHAESTUS:
                return new Hephaestus();
            case HERA:
                return new Hera();
            case HESTIA:
                return new Hestia();
            case MINOTAUR:
                return new Minotaur();
            case PAN:
                return new Pan();
            case PROMETHEUS:
                return new Prometheus();
            case ZEUS:
                return new Zeus();
            default:
                throw new IllegalArgumentException("Illegal divinity card");
        }
    }

    public List<Byte> getEncodedBoard() {
        return BoardEncoder.bytesForBoard(game.getCurrentBoard());
    }

    public void start(ClientHandler client) throws IOException {
        welcomeMessage(client);
        firstPlayerSetNumOfPlayers(client);
        notifyExtraClient(client);
        if (client.getClientNum() <= game.getTotNumPlayers()) {
            game.addPlayer(askNickname(client), askAge(client));
            checkGameFull(client);
            askYoungestPlayerCards(client);
            askDivinity(client);
            placeWorkers(client);
            do {
                playGame(client);
            } while (game.getWinner() == null);

            notifyWinner(client);
        }
    }

    private void playGame(ClientHandler client) throws IOException {
        notifyNotYourTurn(client);
        boolean hasCurrentPlayerLost = false;
        if (game.getWinner() == null) {
            Player clientPlayer = game.nicknameToPlayer(client.getNickname());
            DivinityCard clientDivinity = playersDivinities.get(clientPlayer);
            Worker selectedWorker = askWorker(client);

            for (WorkerAction action : clientDivinity.getMoveSequence()) {
                if(!canTakeAction(selectedWorker, clientDivinity, action)){
                    if(action.isOptional()){
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
                    for(Player p : game.getOpponents()){
                        cantWin = playersDivinities.get(p).blockOpponentWinningCondition(selectedWorker.getPosition());
                    }
                    if(!cantWin){
                        game.setWinner(clientPlayer);
                        break;
                    }
                }
            }
        }
        updateTurn(hasCurrentPlayerLost);
    }

    public int getTotNumPlayers(){return game.getTotNumPlayers();}

    private void welcomeMessage(ClientHandler client) throws IOException {
        client.notifyMessage(Message.WELCOME);
    }

    private void firstPlayerSetNumOfPlayers(ClientHandler client) throws IOException {
        if (client.getClientNum() == 1) {
            client.notifyMessage(Message.INSERT_NUM_PLAYERS);
            game.setTotNumPlayers(client.askInt(this::checkNumOfPlayers));
            wakeUpAll();
        } else if (game.getTotNumPlayers() == 0) {
            client.notifyMessage(Message.WAIT_FOR_NUM_PLAYERS);
            pauseClient(client);
        }
    }

    private void notifyExtraClient(ClientHandler client) throws IOException {
        if (client.getClientNum() > game.getTotNumPlayers()) {
            client.notifyEndGame(Protocol.TOO_LATE);
            //client.notifyMessage(Message.GAME_FULL);
            //client.notifyMessage("If you want to see the match stay connected");
            pauseClient(client);
        }
    }

    private String askNickname(ClientHandler client) throws IOException {
        client.notifyMessage(Message.CHOOSE_NICKNAME);
        String nickname = client.askString(this::checkNickname);
        illegalNicknames.add(nickname);
        client.setNickname(nickname);

        return nickname;
    }

    private int askAge(ClientHandler client) throws IOException {
        client.notifyMessage(Message.SET_AGE);
        return client.askInt(this::checkAge);
    }

    private void askYoungestPlayerCards(ClientHandler client) throws IOException {
        if (game.getCurrentPlayerTurn().getNickname().equals(client.getNickname())) {
            List<DivinityCard.Name> selectedDivinityCards = new LinkedList<>();
            for (int i = 0; i < game.getTotNumPlayers(); ++i) {
                displayAvailableDivinities(client);
                String card = client.askString(this::checkDivinityCard);
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

    private void askDivinity(ClientHandler client) throws IOException {
        notifyNotYourTurn(client);
        displayAvailableDivinities(client);
        String card = client.askString(this::checkDivinityCard);
        DivinityCard.Name cardEnum = DivinityCard.Name.valueOf(card.toUpperCase());
        playersDivinities.put(game.getCurrentPlayerTurn(), stringToStrategy(card));
        availableDivinityCards.remove(cardEnum);
        updateTurn(false);
    }

    private void notifyNotYourTurn(ClientHandler client) throws IOException {
        while (!game.getCurrentPlayerTurn().getNickname().equals(client.getNickname())) {
            if (game.getWinner() == null) {
                client.notifyMessageString(game.getCurrentPlayerTurn().getNickname());
                client.notifyMessage(Message.NOT_YOUR_TURN);
            }
            pauseClient(client);
        }
    }

    private void displayAvailableDivinities(ClientHandler client) throws IOException {
        client.notifyMessageString(client.getNickname());
        client.notifyMessage(Message.DISPLAY_DIVINITY_MESSAGE);
        StringBuilder message = new StringBuilder();
        availableDivinityCards.forEach(card -> message.append(card).append("\n"));
        client.notifyMessageString(message.toString());
        client.notifyMessage(Message.DISPLAY_AVAILABLE_DIVINITIES);
    }

    private void placeWorkers(ClientHandler client) throws IOException {
        notifyNotYourTurn(client);
        displayAllClients();
        client.notifyMessage(Message.PLACE_YOUR_WORKERS);
        Player clientPlayer = game.nicknameToPlayer(client.getNickname());
        for (int i = 0; i < Game.WORKERS_PER_PLAYER; ++i) {
            client.notifyMessageString(String.valueOf(i+1));
            client.notifyMessage(Message.PLACE_A_WORKER);
            Board newBoard;
            do {
                Cell cell = askCell(client);
                try {
                    newBoard = game.getCurrentBoard().withWorker(new Worker(clientPlayer.getColor(), cell));
                    displayAllClients();
                    break;
                } catch (IllegalArgumentException e) {
                    client.notifyMessage(Message.ILLEGAL_ARGUMENT);
                }
            } while (true);
            game.setCurrentBoard(newBoard);
            displayAllClients();
        }
        updateTurn(false);
    }

    private Cell askCell(ClientHandler client) throws IOException {
        int x;
        int y;
        do {
            try {
                client.notifyMessage(Message.SET_CELL_COLUMN_COORD);
                x = client.askInt(this::checkXCoordinate);
                client.notifyMessage(Message.SET_CELL_ROW_COORD);
                y = client.askInt(this::checkYCoordinate);
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

    private void notifyWinner(ClientHandler client) throws IOException {
        if (game.getWinner().getNickname().equals(client.getNickname())) {
            client.notifyMessage(Message.YOU_WIN);
        } else {
            client.notifyMessageString(game.getWinner().getNickname());
            client.notifyMessage(Message.YOU_LOSE);
        }
    }

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

    private Cell askWorkerAction(ClientHandler client, Worker selectedWorker, DivinityCard clientDivinty, WorkerAction action) throws IOException {
        Cell workerPosition = selectedWorker.getPosition();
        Board currentBoard = game.getCurrentBoard();
        boolean useAbility = true;
        if (action.isOptional()) {
            client.notifyMessage(Message.ASK_SPECIAL_ACTION);
            String answer = client.askString(this::checkYesOrNo);
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
                for(Player p : game.getOpponents()){
                    playersDivinities.get(p).checkOpponentMove(clientDivinty.typeOfAction(action), selectedWorker, destinationCell, currentBoard);
                }
                currentBoard = takeAction(selectedWorker, destinationCell, currentBoard, clientDivinty, action, useAbility);
                game.setCurrentBoard(currentBoard);
                displayAllClients();
                return currentBoard.hasWorkerAt(destinationCell) ? destinationCell : workerPosition;
            } catch (IllegalArgumentException e) {
                client.notifyMessageString(e.getMessage());
                client.notifyMessage(Message.ILLEGAL_ARGUMENT);
            }
        } while (true);
    }

    private boolean canTakeAction(Worker selectedWorker, DivinityCard clientDivinty, WorkerAction action){
        for(int row = 0; row < Board.ROWS; ++row){
            for(int col = 0; col < Board.COLUMNS; ++col){
                Cell cell = new Cell(col, row);
                Board board = game.getCurrentBoard();
                try{
                    takeAction(selectedWorker, cell, board, clientDivinty, action, true);
                    return true;
                } catch (IllegalArgumentException ignored){

                }
            }
        }
        return false;
    }

    private Board takeAction(Worker selectedWorker, Cell cell, Board board, DivinityCard clientDivinty, WorkerAction action, boolean useAbility) throws IllegalArgumentException{
        switch (action) {
            case MOVE:
                return clientDivinty.move(selectedWorker, cell, board);
            case BUILD:
                return clientDivinty.build(selectedWorker, cell, board);
            case OPTIONAL_ACTION:
                return ((OptionalAction) clientDivinty).optionalAction(selectedWorker, cell, board);
            case OPTIONAL_ABILITY:
                return ((OptionalAbility) clientDivinty).optionalAbility(useAbility, selectedWorker, cell, board);
            default:
                throw new IllegalArgumentException("WorkerAction " + action + " unknown.");
        }
    }
}