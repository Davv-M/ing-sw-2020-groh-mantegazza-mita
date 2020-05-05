package it.polimi.ingsw.PSP38.server.model;

import it.polimi.ingsw.PSP38.common.WorkerColor;

/**
 * This class implements the players currently in the game
 *
 * @author Davide Mantegazza (10568661)
 */
public class Player {
    public static final int MIN_AGE = 8;
    public static final int MAX_AGE = 99;

    private final String nickname;
    private final int age;
    private final WorkerColor color;

    /**
     * Construct a player with the given parameters
     *
     * @param nickname the player's nickname
     * @param age      the player's age
     * @param color    the player's color
     */
    public Player(String nickname, int age, WorkerColor color) {
        this.nickname = nickname;
        this.age = age;
        this.color = color;
    }

    /**
     * @return the player's nickname
     */

    public String getNickname() {
        return nickname;
    }

    /**
     * @return the player's age
     */
    public int getAge() {
        return age;
    }

    /**
     * @return the player's color
     */
    public WorkerColor getColor() {
        return color;
    }

    public String toString() {
        return "Nickname : " + getNickname() + ", age : " + getAge() + ", worker color : " + getColor();
    }
}

