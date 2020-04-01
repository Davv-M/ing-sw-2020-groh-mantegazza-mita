package it.polimi.ingsw.PSP038.model;

import java.util.Date;

/**
 * This class implements the players currently in the game
 * @author Davide Mantegazza (10568661)
 */
public class Player {
    private String nickname;
    private Date dateOfBirth;
    private Worker worker1;
    private Worker worker2;
    private Color color;
    public Player(String nickname) {
        this.nickname = nickname;
        //this.dateOfBirth = dateOfBirth;
    }

    public void createWorkers(){
        worker1 = new Worker(1, color);
        worker2 = new Worker(2, color);
    }

    public Worker getWorker1() {
        return worker1;
    }

    public Worker getWorker2() {
        return worker2;
    }

    public String getNickname() {
        return nickname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

}

