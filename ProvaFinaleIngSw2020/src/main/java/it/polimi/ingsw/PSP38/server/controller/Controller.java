package it.polimi.ingsw.PSP38.server.controller;

import it.polimi.ingsw.PSP38.common.WorkerColor;
import it.polimi.ingsw.PSP38.server.model.*;
import it.polimi.ingsw.PSP38.server.controller.divinityStrategies.*;
import it.polimi.ingsw.PSP38.common.utilities.ArgumentChecker;
import it.polimi.ingsw.PSP38.server.virtualView.ClientHandler;
import it.polimi.ingsw.PSP38.server.virtualView.Server;

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
        wakeUpAll();
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

    public synchronized void checkGameFull(ClientHandler ch) {
        if (getNumOfPlayers() > players.size()) {
            pauseClient(ch);
        } else {
            wakeUpAll();
        }
    }

    public synchronized void pauseClient(ClientHandler ch) {
        try {
            ch.setImInWait(true);
            while(ch.getImInWait()){
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void wakeUpAll(){
        Server.wakeUpAll();
        notifyAll();
    }

    public synchronized void updateTurn() {
        players.add(players.remove(0));
        wakeUpAll();
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
        playersDivinities.put(nicknameToPlayer(getCurrentPlayerTurn()), stringToStrategy(selectedCard));
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
            case HEPHAESTUS:
                return new StrategyHephaestus();
            case MINOTAUR:
                return new StrategyMinotaur();
            case PAN:
                return new StrategyPan();
            case PROMETHEUS:
                return new StrategyPrometheus();
            default:
                throw new IllegalArgumentException("Illegal divinity card");
        }
    }

    public synchronized List<Byte> getEncodedBoard(){
        return BoardEncoder.bytesForBoard(game.getCurrentBoard());
    }

    public synchronized int checkXCoordinate(int x) throws IllegalArgumentException{
        return ArgumentChecker.requireBetween(0, Board.COLUMNS - 1, x);
    }

    public synchronized int checkYCoordinate(int y) throws IllegalArgumentException{
        return ArgumentChecker.requireBetween(0, Board.ROWS - 1, y);
    }

    public synchronized void placeWorker(int x, int y, String nickname) throws IllegalArgumentException{
        Cell cell = game.getCurrentBoard().cellAt(x, y);
        Player player = nicknameToPlayer(nickname);
        if(cell.hasDome()){
            throw new IllegalArgumentException("you can't place a worker on a cell cell containing a dome!");
        } else if(game.getCurrentBoard().getWorkersPositions().containsKey(cell)){
            throw new IllegalArgumentException("This cell already contains a worker");
        }
        game.setCurrentBoard(game.getCurrentBoard().withWorker(new Worker(player.getColor(), cell)));
    }

    private synchronized Player nicknameToPlayer(String nickname) {
        Optional<Player> player = players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst();
        return player.orElse(null);
    }
}
