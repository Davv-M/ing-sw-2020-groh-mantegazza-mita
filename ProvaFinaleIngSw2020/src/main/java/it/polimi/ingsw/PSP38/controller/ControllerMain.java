package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.model.*;

import java.util.*;

public class ControllerMain {


    public static void main(String[] args) {
        Player pippo = new Player("Pippo", 12, Worker.Color.RED);
        Player pluto = new Player("Pluto", 11, Worker.Color.YELLOW);
        List<Player> players = Arrays.asList(pippo, pluto);
        Game currentGame = new Game(players);


        System.out.println("Pippo posiziona il worker (colonna [ invio ] , riga [ invio ])");
        Scanner s = new Scanner(System.in);
        int x = s.nextInt();
        int y = s.nextInt();
        Cell startCell = new Cell(x,y);
        Worker worker = new Worker(pippo.getColor(), startCell);
        currentGame.setCurrentBoard(currentGame.getCurrentBoard().withWorker(worker, startCell));

        System.out.println("Pluto posiziona il worker (colonna [ invio ] , riga [ invio ])");
        x = s.nextInt();
        y = s.nextInt();
        startCell = new Cell(x,y);
        worker = new Worker(pluto.getColor(), startCell);
        currentGame.setCurrentBoard(currentGame.getCurrentBoard().withWorker(worker, startCell));

        Map<Player, StrategyDivinityCard> playersDivinity = new HashMap<>();
        playersDivinity.put(pippo, new StrategyApollo());
        playersDivinity.put(pluto, new StrategyAtlas());

        List<Round> rounds = new LinkedList<>();
        for(Player p: players) {
            rounds.add(new Round(p, playersDivinity.get(p)));
        }


        System.out.println("Inizi partita: ");
        int round = 1;
        while( true ){
            System.out.println("Round numero: " + round);
            round++;
            for(Round r: rounds){
                currentGame.setCurrentBoard(r.play(currentGame.getCurrentBoard()));
            }
        }
    }
}

