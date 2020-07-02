package it.polimi.ingsw.PSP38.server.model;

import it.polimi.ingsw.PSP38.common.WorkerColor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PlayerTest {
    Player player = new Player("Max", 27, WorkerColor.WHITE);

    @Test
    public void constantsAreCorrect() {
        assertEquals(8, Player.MIN_AGE);
        assertEquals(99, Player.MAX_AGE);
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsExceptionNullColor(){
        new Player("", 44, null);
    }

    @Test
    public void getNickName(){
        assertEquals("Max", player.getNickname());
    }

    @Test
    public void getAge(){
        assertEquals(27, player.getAge());
    }

    @Test
    public void getColor(){
        assertEquals(WorkerColor.WHITE, player.getColor());
    }

    @Test
    public void equalsCorrect(){
        Player player2 = new Player("Max", 27, WorkerColor.WHITE);
        Player player3 = new Player("Max", 27, WorkerColor.BLACK);
        Player player4 = new Player("Matteo", 27, WorkerColor.WHITE);
        Player player5 = new Player("Max", 26, WorkerColor.WHITE);

        assertEquals(player, player2);
        assertNotEquals(player, player3);
        assertNotEquals(player, player4);
        assertNotEquals(player, player5);
    }

    @Test
    public void toStringCorrect(){
        assertEquals("Nickname : Max, age : 27, worker color : WHITE", player.toString());
    }
}