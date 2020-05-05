/*
package it.polimi.ingsw.PSP38.virtualView;

import it.polimi.ingsw.PSP38.server.controller.StrategyDivinityCard;
import it.polimi.ingsw.PSP38.controller.divinityStrategy.*;
import it.polimi.ingsw.PSP38.model.*;

import java.util.*;

public class PreGameRequest {


    public int askNumOfPlayer() {
        int numberOfPlayers;
        Scanner s = new Scanner(System.in);
        do {
            System.out.println("Please enter the number of players (between " + Game.MIN_NUMBER_OF_PLAYERS
                    + " and " + Game.MAX_NUMBER_OF_PLAYERS + ")");
            try {
                numberOfPlayers = s.nextInt();
            } catch (InputMismatchException e) {
                numberOfPlayers = 0;
                System.out.println("The number of players must be an integer");
            }
            s.nextLine();
        } while (numberOfPlayers < Game.MIN_NUMBER_OF_PLAYERS || numberOfPlayers > Game.MAX_NUMBER_OF_PLAYERS);

        return numberOfPlayers;

    }

    public Player askNewPlayer(List<String> illegalNicknames, List<Worker.Color> availableWorkerColors)  {
        Scanner s = new Scanner(System.in);

        System.out.println("Please choose a nickname");
        String nickname = s.nextLine();
        while (illegalNicknames.contains(nickname)) {
            System.out.println("That name is not available. Please enter a new one");
            nickname = s.nextLine();
        }

        System.out.println("Please enter your age");
        int age = s.nextInt();
        while (age < 0) {
            System.out.println("Age can not be negative. Please enter your age");
            age = s.nextInt();
            s.nextLine();
        }

        System.out.println("These are the available worker colors :");
        for (Worker.Color color : availableWorkerColors) {
            System.out.println(color);
        }
        System.out.println("Please select one of the available colors");
        s = new Scanner(System.in);
        String inputColor;
        Worker.Color color;
        do {
            try {
                inputColor = s.nextLine();
                color = Worker.Color.valueOf(inputColor);
            } catch (IllegalArgumentException e) {
                inputColor = "";
                color = null;
                System.out.println("The color selected isn't available. Please enter a new one");
            }
        } while (!availableWorkerColors.contains(color));

        return new Player(nickname, age, Worker.Color.valueOf(inputColor));
    }

    public Worker askWorkerPosition(Player player) {
        int x=-1;
        int y=-1;
        Cell startingPosition = null;

        do {
            System.out.println(player.getNickname() + " Where do you want to place your worker? - example:(column,row)");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.next();
            try {
                String[] inputs = s.split(",");
                x = Integer.parseInt(inputs[0]);
                y = Integer.parseInt(inputs[1]);
            } catch (InputMismatchException e) {
                System.out.println("Please insert an integers coordinates");
            }
            if (x < 0 || x>= Board.COLUMNS || y < 0 || y>= Board.ROWS)
                System.out.println("Remember column between: 0 and "+(Board.COLUMNS-1) +" & row between: 0 and "+ (Board.ROWS-1));
        } while ( x < 0 || x>= Board.COLUMNS || y < 0 || y>= Board.ROWS );
        startingPosition = new Cell(x, y);
        return new Worker(player.getColor(), startingPosition);
    }

    public Map<Player, StrategyDivinityCard> assignDivinityCards(List<Player> players) {
        Player youngestPlayer = players.get(0);
        System.out.println(youngestPlayer.getNickname() + " select " + players.size()
                + " divinity cards from this list :");
        for (String divinityCardName : StrategyDivinityCard.DIVINITY_CARDS_NAMES) {
            System.out.println(divinityCardName);
        }
        Scanner s = new Scanner(System.in);
        List<String> availableDivinities = new LinkedList<>(StrategyDivinityCard.DIVINITY_CARDS_NAMES);
        List<String> selectedCards = new LinkedList<>();
        for (int i = 0; i < players.size(); ++i) {
            String divinityCard = s.nextLine();
            while (!availableDivinities.contains(divinityCard)) {
                System.out.println("This divinity isn't available or has already been chosen. Please select a new one");
                divinityCard = s.nextLine();
            }
            selectedCards.add(divinityCard);
            availableDivinities.remove(divinityCard);
        }

        return askToChooseDivinities(players, selectedCards);
    }

    private static Map<Player, StrategyDivinityCard> askToChooseDivinities(List<Player> players, List<String> selectedCards) {
        List<Player> playerCardDistributionOrder = new LinkedList<>(players);
        playerCardDistributionOrder.remove(players.get(0));
        playerCardDistributionOrder.add(players.get(0));
        Scanner s = new Scanner(System.in);

        Map<Player, StrategyDivinityCard> playersDivinities = new HashMap<>();
        for (Player player : playerCardDistributionOrder) {
            System.out.println(player.getNickname() + " select your divinity card from this list :");
            for (String divinityCardName : selectedCards) {
                System.out.println(divinityCardName);
            }
            String divinityCard = s.nextLine();
            while (!selectedCards.contains(divinityCard)) {
                System.out.println("This divinity isn't available. Please select a new one");
                divinityCard = s.nextLine();
            }
            playersDivinities.put(player, stringToStrategy(divinityCard));
            selectedCards.remove(divinityCard);
        }

        return playersDivinities;
    }

    private static StrategyDivinityCard stringToStrategy(String divinityCard) {
        switch (divinityCard) {
            case "Apollo":
                return new StrategyApollo();
            case "Artemis":
                return new StrategyArtemis();
            case "Athena":
                return new StrategyAthena();
            case "Atlas":
                return new StrategyAtlas();
            case "Demeter":
                return new StrategyDemeter();
            default:
                throw new IllegalArgumentException("Illegal divinity card");
        }
    }


}
*/
