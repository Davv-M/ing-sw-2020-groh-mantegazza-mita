package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.controller.divinityStrategy.*;
import it.polimi.ingsw.PSP38.model.Player;
import it.polimi.ingsw.PSP38.model.Worker;
import it.polimi.ingsw.PSP38.virtualView.Server;

import java.util.*;

public class Controller {
    public static List<Player> players = new LinkedList<>();
    private List<String> illegalNicknames = new LinkedList<>();
    private List<Worker.Color> availableColors = new LinkedList<>(Arrays.asList(Worker.Color.values()));
    private List<StrategyDivinityCard.Name> availableDivinityCards = new LinkedList<>(Arrays.asList(StrategyDivinityCard.Name.values()));
    private List<StrategyDivinityCard.Name> selectedCard = new LinkedList<>();

    public Controller(){ }


    public synchronized boolean isNicknameAvailable(String nickname){return (!illegalNicknames.contains(nickname));}

    public synchronized void addIllegalNickname( String nickname){ illegalNicknames.add(nickname);}

    public synchronized void addPlayer(String nickname, int age){
        Player newPlayer = new Player(nickname, age, availableColors.remove(0));
        players.add(newPlayer);
        players.sort((p1, p2) -> Integer.compare(p2.getAge(), p1.getAge()));
    }

    public synchronized int numCurrentPlayers(){return players.size();}

    public synchronized String youngestPlayer (){return players.get(players.size()-1).getNickname();}

    public synchronized void checkGameFull(){
        while(Server.getNumOfPlayer() > numCurrentPlayers()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
    }


}
