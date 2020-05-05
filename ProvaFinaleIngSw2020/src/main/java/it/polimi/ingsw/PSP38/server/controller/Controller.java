package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.model.Game;
import it.polimi.ingsw.PSP38.server.controller.divinityStrategies.*;
import it.polimi.ingsw.PSP38.server.model.Player;
import it.polimi.ingsw.PSP38.common.utilities.ArgumentChecker;

import java.util.*;

public class Controller {
    private int numOfPlayers = 0;
    private static final List<Player> players = new LinkedList<>();
    private static String youngestPlayer;
    private final List<String> illegalNicknames = new LinkedList<>();
    private final List<WorkerColor> availableColors = new LinkedList<>(Arrays.asList(WorkerColor.values()));
    private List<StrategyDivinityCard.Name> availableDivinityCards = new LinkedList<>(Arrays.asList(StrategyDivinityCard.Name.values()));
    private final List<StrategyDivinityCard.Name> selectedCards = new LinkedList<>();
    private Game game;
    private final Map<Player, StrategyDivinityCard> playersDivinities = new HashMap<>();

    public Controller() {
    }

    public synchronized void setNumOfPlayers(int numOfPlayers) throws IllegalArgumentException {
        this.numOfPlayers = numOfPlayers;
        notifyAll();
    }

    public synchronized int getNumOfPlayers() {
        return numOfPlayers;
    }

    public synchronized boolean isNicknameAvailable(String nickname) {
        return (!illegalNicknames.contains(nickname));
    }

    public synchronized int checkNumOfPlayers(int numOfPlayers) throws IllegalArgumentException {
        return ArgumentChecker.requireBetween(Game.MIN_NUMBER_OF_PLAYERS, Game.MAX_NUMBER_OF_PLAYERS, numOfPlayers);
    }

    public synchronized int checkAge(int age) throws IllegalArgumentException {
        return ArgumentChecker.requireBetween(Player.MIN_AGE, Player.MAX_AGE, age);
    }

    public synchronized void addIllegalNickname(String nickname) {
        illegalNicknames.add(nickname);
    }

    public synchronized void addPlayer(String nickname, int age) {
        Player newPlayer = new Player(nickname, age, availableColors.remove(0));
        players.add(newPlayer);
        players.sort(Comparator.comparingInt(Player::getAge));
        youngestPlayer = players.get(0).getNickname();
    }

    public synchronized String youngestPlayer() {
        return youngestPlayer;
    }

    public synchronized void checkGameFull() {
        if (getNumOfPlayers() > players.size()) {
            pauseClient();
        } else {
            notifyAll();
        }
    }

    public synchronized void pauseClient() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateTurn() {
        players.add(players.remove(0));
        notifyAll();
    }

    public synchronized void createGame() {
        game = new Game(players);
    }

    public synchronized String getCurrentPlayerTurn() {
        return players.get(0).getNickname();
    }

    public synchronized List<StrategyDivinityCard.Name> getAvailableDivinityCards() {
        return availableDivinityCards;
    }

    public synchronized boolean isSelectedCardCorrect(String selectedCard) {
        try {
            StrategyDivinityCard.Name selectedCardEnum = StrategyDivinityCard.Name.valueOf(selectedCard.toUpperCase());
            return availableDivinityCards.contains(selectedCardEnum);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public synchronized void setSelectedCards(String selectedCard) {
        StrategyDivinityCard.Name selectedCardEnum = StrategyDivinityCard.Name.valueOf(selectedCard.toUpperCase());
        selectedCards.add(selectedCardEnum);
        availableDivinityCards.remove(selectedCardEnum);
        if (selectedCards.size() == getNumOfPlayers()) {
            availableDivinityCards = new LinkedList<>(selectedCards);
        }
    }

    public synchronized void setPlayerDivinity(String selectedCard) {
        StrategyDivinityCard.Name selectedCardEnum = StrategyDivinityCard.Name.valueOf(selectedCard.toUpperCase());
        Player currentPlayer = players.stream().filter(p -> p.getNickname().equals(getCurrentPlayerTurn())).findFirst().get();
        playersDivinities.put(currentPlayer, stringToStrategy(selectedCard));
        availableDivinityCards.remove(selectedCardEnum);
    }

    private synchronized StrategyDivinityCard stringToStrategy(String selectedCard) {
        StrategyDivinityCard.Name selectedCardEnum = StrategyDivinityCard.Name.valueOf(selectedCard.toUpperCase());
        switch (selectedCardEnum) {
            case APOLLO:
                return new StrategyApollo();
            case ARTEMIS:
                return new StrategyArtemis();
            case ATHENA:
                return new StrategyAthena();
            case ATLAS:
                return new StrategyAtlas();
            case DEMETER:
                return new StrategyDemeter();
            default:
                throw new IllegalArgumentException("Illegal divinity card");
        }
    }
}
