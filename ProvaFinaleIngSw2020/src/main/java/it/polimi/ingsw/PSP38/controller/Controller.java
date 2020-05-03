package it.polimi.ingsw.PSP38.controller;

import it.polimi.ingsw.PSP38.model.Player;
import it.polimi.ingsw.PSP38.model.Worker;
import it.polimi.ingsw.PSP38.virtualView.Server;

import java.util.*;

public class Controller {
    private int numOfPlayer = 0;
    private boolean first = true;
    public static List<Player> players = new LinkedList<>();
    private List<String> illegalNicknames = new LinkedList<>();
    private List<Worker.Color> availableColors = new LinkedList<>(Arrays.asList(Worker.Color.values()));
    private List<StrategyDivinityCard.Name> availableDivinityCards = new LinkedList<>(Arrays.asList(StrategyDivinityCard.Name.values()));
    private List<StrategyDivinityCard.Name> selectedCard = new LinkedList<>();

    public Controller(){ }

    public synchronized void setNumOfPlayer(int numOfPlayer) {
        this.numOfPlayer = numOfPlayer;
        notifyAll();
    }

    public synchronized int getNumOfPlayer() {return numOfPlayer;}

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
        while(getNumOfPlayer() > numCurrentPlayers()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
    }

    public synchronized boolean checkImFirst(){
        if (first){
          first = false;
          return true;
        }else{
            return false;
        }
    }

    public synchronized void waitMe(){
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<StrategyDivinityCard.Name> getAvailableDivinityCards(){ return availableDivinityCards; }

    public synchronized boolean isSelectedCardCorrect(String selectedCard){
        try{
            StrategyDivinityCard.Name selectedCardEnum  = StrategyDivinityCard.Name.valueOf(selectedCard);
            if(this.availableDivinityCards.contains(selectedCardEnum)) {
                return true;
            }else{
                return false;
            }
        }catch (IllegalArgumentException e){
            return false;
        }

    }

    public synchronized void setSelectedCard(String selectedCard){
       StrategyDivinityCard.Name selectedCardEnum  = StrategyDivinityCard.Name.valueOf(selectedCard);
       this.selectedCard.add(selectedCardEnum);
       this.availableDivinityCards.remove(selectedCardEnum);

    }


}
