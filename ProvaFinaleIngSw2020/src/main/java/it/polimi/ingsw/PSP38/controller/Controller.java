package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.controller.divinityStrategy.*;
import it.polimi.ingsw.PSP38.model.Game;
import it.polimi.ingsw.PSP38.model.Player;
import it.polimi.ingsw.PSP38.model.Worker;
import it.polimi.ingsw.PSP38.utilities.ArgumentChecker;
import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class Controller {
    private int numOfPlayers = 0;
    private static final List<Player> players = new LinkedList<>();
    private static String youngestPlayer;
    private final List<String> illegalNicknames = new LinkedList<>();
    private final List<Worker.Color> availableColors = new LinkedList<>(Arrays.asList(Worker.Color.values()));
    private final List<StrategyDivinityCard.Name> availableDivinityCards = new LinkedList<>(Arrays.asList(StrategyDivinityCard.Name.values()));
    private final List<StrategyDivinityCard.Name> selectedCards = new LinkedList<>();
    private Game game;
    private final Map<Player, StrategyDivinityCard> playersDivinities = new HashMap<>();

    public Controller() {
    }

    public synchronized void setNumOfPlayers(int numOfPlayers) throws IllegalArgumentException {
        this.numOfPlayers = numOfPlayers;
        wakeUpClients();
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

    public synchronized void checkGameFull(int clientNum) {
        if (getNumOfPlayers() > players.size()) {
            System.out.println(clientNum + " is paused");
            pauseClient();
        } else {
            wakeUpClients();
            System.out.println(clientNum + " has woken up every other client");
        }
    }

    public synchronized void pauseClient() {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public synchronized void wakeUpClients(){
        notifyAll();
    }

    public synchronized List<StrategyDivinityCard.Name> getAvailableDivinityCards() {
        return availableDivinityCards;
    }

    public synchronized boolean isSelectedCardCorrectFromAvailableCards(String selectedCard) {
        try {
            StrategyDivinityCard.Name selectedCardEnum = StrategyDivinityCard.Name.valueOf(selectedCard.toUpperCase());
            return availableDivinityCards.contains(selectedCardEnum);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public synchronized boolean isSelectedCardCorrectFromSelectedCards(String selectedCard) {
        try {
            StrategyDivinityCard.Name selectedCardEnum = StrategyDivinityCard.Name.valueOf(selectedCard.toUpperCase());
            return selectedCards.contains(selectedCardEnum);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public synchronized void setSelectedCard(String selectedCard) {
        StrategyDivinityCard.Name selectedCardEnum = StrategyDivinityCard.Name.valueOf(selectedCard.toUpperCase());
        selectedCards.add(selectedCardEnum);
        availableDivinityCards.remove(selectedCardEnum);
    }

    public synchronized void createGame(){
        game = new Game(players);
    }

    public synchronized Player updatePlayers(){
        players.add(players.remove(0));
        return players.get(0);
    }

    public synchronized Player getCurrentPlayerTurn(){
        return players.get(0);
    }

    public synchronized  List<StrategyDivinityCard.Name> getSelectedCards() {
        return selectedCards;
    }

    public synchronized void setPlayerDivinity(Player player, String selectedCard){
        StrategyDivinityCard.Name selectedCardEnum = StrategyDivinityCard.Name.valueOf(selectedCard.toUpperCase());
        playersDivinities.put(player, stringToStrategy(selectedCard));
        selectedCards.remove(selectedCardEnum);
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

    public synchronized void print(){
        for(Player p: players){
            System.out.println("players :");
            System.out.println(p);
            System.out.println();
        }
        for(String n: illegalNicknames){
            System.out.println("illegal nicknames :");
            System.out.println(n);
            System.out.println();
        }
        System.out.println("map : ");
        System.out.println(playersDivinities);
        System.out.println();

    }
}
