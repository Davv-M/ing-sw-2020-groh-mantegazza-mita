package it.polimi.ingsw.PSP038.model;

import java.util.Date;

/**
 * This class implements the players currently in the game
 * @author Davide Mantegazza (10568661)
 */
public class Player {
    private String nickname;
    private Date dateOfBirth;
    //private Worker[2] workers;
    public Player(String nickname, Date dateOfBirth) {
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
    }

    public String getNickname() {
        return nickname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}
