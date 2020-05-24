package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.server.model.*;
import it.polimi.ingsw.PSP38.server.controller.divinityCards.*;
import it.polimi.ingsw.PSP38.common.utilities.ArgumentChecker;
import it.polimi.ingsw.PSP38.server.virtualView.ClientHandler;
import it.polimi.ingsw.PSP38.server.virtualView.Server;

import java.io.IOException;
import java.util.*;

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
            client.notifyMessage("Hold on all players will be ready in few seconds");
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

    private synchronized void pauseClient(ClientHandler client) throws IOException{
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

    private synchronized void updateTurn() {
        game.updateTurn();
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
        if (client.clientNum <= game.getTotNumPlayers()) {
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
        if(game.getWinner() == null){
            Player clientPlayer = game.nicknameToPlayer(client.getNickname());
            DivinityCard clientDivinity = playersDivinities.get(clientPlayer);
            Worker selectedWorker = askWorker(client);

            for (WorkerAction action : clientDivinity.getMoveSequence()) {
                Cell previousPosition = selectedWorker.getPosition();
                selectedWorker = selectedWorker.withPosition(askWorkerAction(client, selectedWorker, clientDivinity, action));
                if (clientDivinity.isWinner(game.getCurrentBoard(), previousPosition, selectedWorker.getPosition())) {
                    game.setWinner(clientPlayer);
                    break;
                }
            }
        }
        updateTurn();
    }

    private void welcomeMessage(ClientHandler client) throws IOException {
        client.notifyMessage("Welcome to SANTORINI\n");
    }

    private void firstPlayerSetNumOfPlayers(ClientHandler client) throws IOException {
        if (client.clientNum == 1) {
            client.notifyMessage("You are the first player to join this game. Please insert the number of players (between 2 and 3)");
            game.setTotNumPlayers(client.askInt(this::checkNumOfPlayers));
            wakeUpAll();
        } else if (game.getTotNumPlayers() == 0) {
            client.notifyMessage("Please wait for the first player to select the number of players.");
            pauseClient(client);
        }
    }

    private void notifyExtraClient(ClientHandler client) throws IOException {
        if (client.clientNum > game.getTotNumPlayers()) {
            client.notifyMessage("game full, please try later.");
            client.notifyMessage("If you want to see the match stay connected");
            pauseClient(client);
        }
    }

    private String askNickname(ClientHandler client) throws IOException {
        client.notifyMessage("Choose your nickname.");
        String nickname = client.askString(this::checkNickname);
        illegalNicknames.add(nickname);
        client.setNickname(nickname);

        return nickname;
    }

    private int askAge(ClientHandler client) throws IOException {
        client.notifyMessage("How old are you? (integer between 8 and 99)");
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
            updateTurn();
        } else {
            client.notifyMessage("Please wait for " + game.getCurrentPlayerTurn().getNickname() +
                    " to choose the divinity cards that will be used in this game.");
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
        updateTurn();
    }

    private void notifyNotYourTurn(ClientHandler client) throws IOException {
        while (!game.getCurrentPlayerTurn().getNickname().equals(client.getNickname())) {
            if(game.getWinner() == null){
                client.notifyMessage("It's " + game.getCurrentPlayerTurn().getNickname() + "'s turn, please wait.");
            }
            pauseClient(client);
        }
    }

    private void displayAvailableDivinities(ClientHandler client) throws IOException {
        StringBuilder message = new StringBuilder(client.getNickname() + ", please select a divinity card from this list :\n");
        availableDivinityCards.forEach(card -> message.append(card).append("\n"));
        client.notifyMessage(message.toString());
    }

    private void placeWorkers(ClientHandler client) throws IOException {
        notifyNotYourTurn(client);
        displayAllClients();
        client.notifyMessage("It's time to place your workers on the board.\n");
        Player clientPlayer = game.nicknameToPlayer(client.getNickname());
        for (int i = 0; i < Game.WORKERS_PER_PLAYER; ++i) {
            client.notifyMessage("Place your worker number " + (i + 1));
            Board newBoard;
            do {
                Cell cell = askCell(client);
                try {
                    newBoard = game.getCurrentBoard().withWorker(new Worker(clientPlayer.getColor(), cell));
                    displayAllClients();
                    break;
                } catch (IllegalArgumentException e) {
                    client.notifyMessage(e.getMessage());
                }
            } while (true);
            game.setCurrentBoard(newBoard);
            displayAllClients();
        }
        updateTurn();
    }

    private Cell askCell(ClientHandler client) throws IOException {
        int x;
        int y;
        do {
            try {
                client.notifyMessage("Please insert the x coordinate :");
                x = client.askInt(this::checkXCoordinate);
                client.notifyMessage("Please insert the y coordinate :");
                y = client.askInt(this::checkYCoordinate);
                return new Cell(x, y);
            } catch (IllegalArgumentException e) {
                client.notifyMessage(e.getMessage());
            }
        } while (true);
    }


    private void displayAllClients() {
        setChanged();
        notifyObservers();
    }

    private void notifyWinner(ClientHandler client) throws IOException {
        if (game.getWinner().getNickname().equals(client.getNickname())) {
            client.notifyMessage("You win!");
        } else {
            client.notifyMessage("You lose! The winner is " + game.getWinner().getNickname());
        }
    }

    private Worker askWorker(ClientHandler client) throws IOException {
        client.notifyMessage("select the worker you want to move.");
        Player clientPlayer = game.nicknameToPlayer(client.getNickname());
        Cell cellUnderWorker;
        Worker workerSelected;
        do {
            try {
                cellUnderWorker = askCell(client);
                workerSelected = game.getCurrentBoard().workerAt(cellUnderWorker);
            } catch (NullPointerException e) {
                client.notifyMessage(e.getMessage());
                continue;
            }

            if (clientPlayer.getColor() != workerSelected.getColor()) {
                client.notifyMessage("The selected worker isn't yours.");
            } else {
                return workerSelected;
            }
        } while (true);
    }

    private Cell askWorkerAction(ClientHandler client, Worker selectedWorker, DivinityCard clientDivinty, WorkerAction action) throws IOException {
        Cell workerPosition = selectedWorker.getPosition();
        Board currentBoard = game.getCurrentBoard();
        boolean useAbility = true;
        if (action == WorkerAction.OPTIONAL_ACTION || action == WorkerAction.OPTIONAL_ABILITY) {
            client.notifyMessage("Do you want to use your special ability ?");
            String answer = client.askString(this::checkYesOrNo);
            if (answer.equalsIgnoreCase("no")) {
                if(action == WorkerAction.OPTIONAL_ACTION){
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
                switch (action) {
                    case MOVE:
                        currentBoard = clientDivinty.move(selectedWorker, destinationCell, currentBoard);
                        break;
                    case BUILD:
                        currentBoard = clientDivinty.build(selectedWorker, destinationCell, currentBoard);
                        break;
                    case OPTIONAL_ACTION:
                        currentBoard = ((OptionalAction) clientDivinty).optionalAction(selectedWorker, destinationCell, currentBoard);
                        break;
                    case OPTIONAL_ABILITY:
                        currentBoard = ((OptionalAbility) clientDivinty).optionalAbility(useAbility, selectedWorker, destinationCell, currentBoard);
                        break;
                    default:
                        throw new IllegalArgumentException("WorkerAction " + action + " unknown.");
                }
                game.setCurrentBoard(currentBoard);
                displayAllClients();
                return currentBoard.hasWorkerAt(destinationCell) ? destinationCell : workerPosition;
            } catch (IllegalArgumentException e) {
                client.notifyMessage(e.getMessage());
            }
        } while (true);
    }
}