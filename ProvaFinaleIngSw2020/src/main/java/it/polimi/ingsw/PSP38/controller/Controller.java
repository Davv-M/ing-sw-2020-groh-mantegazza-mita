package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.controller.divinityStrategy.*;
import it.polimi.ingsw.PSP38.model.Player;
import it.polimi.ingsw.PSP38.model.Worker;
import it.polimi.ingsw.PSP38.virtualView.Server;

import java.util.*;

public class Controller {
    public static List<Player> players = new LinkedList<>();
    List<String> illegalNicknames = new LinkedList<>();
    List<Worker.Color> availableColors = new LinkedList<>(Arrays.asList(Worker.Color.values()));
    List<StrategyDivinityCard.Name> availableDivinityCards = new LinkedList<>(Arrays.asList(StrategyDivinityCard.Name.values()));
    List<StrategyDivinityCard.Name> selectedCard = new LinkedList<>();

    public Controller(){ }

    public boolean isNicknameAvailable(String nickname){return (!illegalNicknames.contains(nickname));}

    public void addIllegalNickname( String nickname){ illegalNicknames.add(nickname);}

    public void addPlayer(String nickname, int age){
        Player newPlayer = new Player(nickname, age, availableColors.remove(0));
        players.add(newPlayer);
        players.sort((p1, p2) -> Integer.compare(p2.getAge(), p1.getAge()));
    }

    public int numCurrentPlayers(){return players.size();}

    public String youngestPlayer (){return players.get(0).getNickname();}




}
