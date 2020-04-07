package it.polimi.ingsw.PSP38.model;

/**
 * This class implements the players currently in the game
 * @author Davide Mantegazza (10568661)
 */
public class Player {
    private final String nickname;
    private final int age;
    private final Worker.Color color;


    public Player(String nickname, int age, Worker.Color color) {
        this.nickname = nickname;
        this.age=age;
        this.color=color;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public Worker.Color getColor() {
        return color;
    }
}

