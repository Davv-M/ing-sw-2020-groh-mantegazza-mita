package it.polimi.ingsw.PSP38.model;

/**
 * This class implements the players currently in the game
 *
 * @author Davide Mantegazza (10568661)
 */
public class Player {
    private final String nickname;
    private final int age;
    private final Worker.Color color;

    /**
     * Construct a player with the given parameters
     *
     * @param nickname the player's nickname
     * @param age      the player's age
     * @param color    the player's color
     */
    public Player(String nickname, int age, Worker.Color color) {
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
    public Worker.Color getColor() {
        return color;
    }
}

