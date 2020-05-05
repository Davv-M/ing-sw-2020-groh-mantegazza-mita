/*
package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.model.*;
import it.polimi.ingsw.PSP38.virtualView.GameRequest;
import it.polimi.ingsw.PSP38.virtualView.PreGameRequest;

import java.util.*;

public class ControllerMain{


    public static void main(String[] args) {
        List<Player> players = new LinkedList<>();
        List<String> illegalNickNames = new LinkedList<>();
        List<Worker.Color> availableColors = new LinkedList<>(Arrays.asList(Worker.Color.values()));
        PreGameRequest pgr = new PreGameRequest();
        GameRequest gr = new GameRequest();


        int numberOfPlayers = pgr.askNumOfPlayer();
        for (int i = 0; i < numberOfPlayers; ++i) {
            addPlayer(players, illegalNickNames, availableColors, pgr);
        }
        players.sort((p1, p2) -> p1.getAge() < p2.getAge() ? 1 : p1.getAge() == p2.getAge() ? 0 : -1);



        Game currentGame = new Game(players);
        Map<Player, StrategyDivinityCard> playersDivinities = pgr.assignDivinityCards(players);



        for (Player player : players) {
            for (int i = 0; i < Game.WORKERS_PER_PLAYER; ++i) {
                currentGame.setCurrentBoard(currentGame.getCurrentBoard().withWorker(pgr.askWorkerPosition(player)));
            }
        }



        List<Round> rounds = new LinkedList<>();
        for (Player p : players) {
            rounds.add(new Round(p,playersDivinities.get(p)));
        }


        System.out.println("START GAME! ");
        int round = 1;
        while (true) {
            System.out.println("Round number: " + round);
            round++;
            for (Round r : rounds) {
                currentGame.setCurrentBoard(r.play(currentGame.getCurrentBoard()));
                gr.printBoard(currentGame.getCurrentBoard());
            }
        }
    }

    private static void addPlayer(List<Player> players, List<String> illegalNickNames, List<Worker.Color> availableColors, PreGameRequest pgr) {
        Player newPlayer = pgr.askNewPlayer(illegalNickNames, availableColors);
        players.add(newPlayer);
        illegalNickNames.add(newPlayer.getNickname());
        availableColors.remove(newPlayer.getColor());
    }





}

*/
