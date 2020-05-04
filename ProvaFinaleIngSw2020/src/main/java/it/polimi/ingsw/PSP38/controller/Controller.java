package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.model.Game;
import it.polimi.ingsw.PSP38.model.Player;
import it.polimi.ingsw.PSP38.model.Worker;
import it.polimi.ingsw.PSP38.utilities.ArgumentChecker;

import java.util.*;

public class Controller {
    private int numOfPlayers = 0;
    private static List<Player> players = new LinkedList<>();
    private List<String> illegalNicknames = new LinkedList<>();
    private List<Worker.Color> availableColors = new LinkedList<>(Arrays.asList(Worker.Color.values()));
    private List<StrategyDivinityCard.Name> availableDivinityCards = new LinkedList<>(Arrays.asList(StrategyDivinityCard.Name.values()));
    private List<StrategyDivinityCard.Name> selectedCards = new LinkedList<>();

    public Controller() {
    }

    public void setNumOfPlayers(int numOfPlayers) throws IllegalArgumentException {
        this.numOfPlayers = numOfPlayers;
        notifyAll();
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public boolean isNicknameAvailable(String nickname) {
        return (!illegalNicknames.contains(nickname));
    }

    public int checkNumOfPlayers(int numOfPlayers) throws IllegalArgumentException {
        return ArgumentChecker.requireBetween(Game.MIN_NUMBER_OF_PLAYERS, Game.MAX_NUMBER_OF_PLAYERS, numOfPlayers);
    }

    public int checkAge(int age) throws IllegalArgumentException {
        return ArgumentChecker.requireBetween(Player.MIN_AGE, Player.MAX_AGE, age);
    }

    public void addIllegalNickname(String nickname) {
        illegalNicknames.add(nickname);
    }

    public void addPlayer(String nickname, int age) {
        Player newPlayer = new Player(nickname, age, availableColors.remove(0));
        players.add(newPlayer);
        players.sort(Comparator.comparingInt(Player::getAge));
    }

    public String youngestPlayer() {
        return players.get(0).getNickname();
    }

    public void checkGameFull() {
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

    public List<StrategyDivinityCard.Name> getAvailableDivinityCards() {
        return availableDivinityCards;
    }

    public boolean isSelectedCardCorrect(String selectedCard) {
        try {
            StrategyDivinityCard.Name selectedCardEnum = StrategyDivinityCard.Name.valueOf(selectedCard.toUpperCase());
            return availableDivinityCards.contains(selectedCardEnum);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void setSelectedCard(String selectedCard) {
        StrategyDivinityCard.Name selectedCardEnum = StrategyDivinityCard.Name.valueOf(selectedCard);
        selectedCards.add(selectedCardEnum);
        availableDivinityCards.remove(selectedCardEnum);
    }
}
