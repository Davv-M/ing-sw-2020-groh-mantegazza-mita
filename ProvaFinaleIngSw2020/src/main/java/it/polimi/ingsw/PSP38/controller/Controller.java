package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.model.Game;
import it.polimi.ingsw.PSP38.model.Player;
import it.polimi.ingsw.PSP38.model.Worker;
import it.polimi.ingsw.PSP38.utilities.ArgumentChecker;

import java.util.*;

public class Controller {
    private int numOfPlayers = 0;
    private static final List<Player> players = new LinkedList<>();
    private final List<String> illegalNicknames = new LinkedList<>();
    private final List<Worker.Color> availableColors = new LinkedList<>(Arrays.asList(Worker.Color.values()));
    private final List<StrategyDivinityCard.Name> availableDivinityCards = new LinkedList<>(Arrays.asList(StrategyDivinityCard.Name.values()));
    private final List<StrategyDivinityCard.Name> selectedCards = new LinkedList<>();

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
    }

    public synchronized String youngestPlayer() {
        return players.get(0).getNickname();
    }

    public synchronized void checkGameFull() {
        while (getNumOfPlayers() > players.size()) {
            pauseClient();
        }
        notifyAll();
    }

    public synchronized void pauseClient() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public synchronized void setSelectedCard(String selectedCard) {
        StrategyDivinityCard.Name selectedCardEnum = StrategyDivinityCard.Name.valueOf(selectedCard);
        selectedCards.add(selectedCardEnum);
        availableDivinityCards.remove(selectedCardEnum);
    }
}
