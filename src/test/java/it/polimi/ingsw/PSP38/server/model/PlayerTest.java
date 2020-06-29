package it.polimi.ingsw.PSP38.server.model;

import it.polimi.ingsw.PSP38.common.WorkerColor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}