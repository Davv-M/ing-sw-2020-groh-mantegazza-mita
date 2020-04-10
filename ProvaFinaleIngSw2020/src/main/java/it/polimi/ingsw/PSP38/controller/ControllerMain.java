package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.debug.BoardPrinter;
import it.polimi.ingsw.PSP38.model.*;

import java.util.*;

public class ControllerMain {
    public static void main(String[] args) {
        Player pippo = new Player("Pippo", 12, Worker.Color.RED);
        Player pluto = new Player("Pluto", 11, Worker.Color.YELLOW);
        List<Player> players = Arrays.asList(pippo, pluto);
        Game currentGame = new Game(players);
        System.out.println("Pippo, posiziona il worker 1 (x, y)");
        Scanner s = new Scanner(System.in);
        int x = s.nextInt();
        int y = s.nextInt();
        Cell cell = new Cell(x,y);
        Worker worker = new Worker(pippo.getColor(), cell);
        currentGame.setCurrentBoard(currentGame.getCurrentBoard().withWorker(worker, cell));
        System.out.println("Pluto, posiziona il worker 1 (x, y)");
        x = s.nextInt();
        y = s.nextInt();
        cell = new Cell(x,y);
        worker = new Worker(pluto.getColor(), cell);
        currentGame.setCurrentBoard(currentGame.getCurrentBoard().withWorker(worker, cell));
        Map<Player,DivinityCard> playersDivinity = new HashMap<>();
        playersDivinity.put(pippo, new Apollo());
        playersDivinity.put(pluto, new Atlas());
        List<Round> rounds = new LinkedList<>();
        for(Player p: players) {
            rounds.add(new Round(p, playersDivinity.get(p)));
        }
        int i = 0;
        while( i<5 ){
            for(Round r: rounds){
               currentGame.setCurrentBoard(r.play(currentGame.getCurrentBoard()));
            }
            i++;
        }
    }
}

