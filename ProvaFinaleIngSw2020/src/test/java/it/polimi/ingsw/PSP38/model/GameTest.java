/*package it.polimi.ingsw.PSP38.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import it.polimi.ingsw.PSP38.model.Game.*;

import static org.junit.Assert.*;

public class GameTest {
    String p;
    Player f;
    @Before
    public void initTest(){
        String p="pippo";
        String p1="carlo";
        Player f = new Player(p);
        Player f1 = new Player(p1);
    }

    @Test(expected = NullPointerException.class)
    public void ConstructorRejectsOutOfBound(){
        new Game(2, null);
    }

    @Test(expected = NullPointerException.class)
    public void AddNullPlayer(){
        Game g=new Game(2, f);
        g.addPlayer(null);
    }

    @Test
    public void ExpectFullGame(){
        Game g=new Game(0, f);
        String p1="carlo";
        Player f1 = new Player(p1);
        g.addPlayer(f1);
        assertTrue(g.isGameFull());
    }

    @Test
    public void AddAPlayer(){
        Game g=new Game(2, f);
        String p1="carlo";
        Player f1 = new Player(p1);
        g.addPlayer(f1);
        assertEquals("carlo", (g.getPlayers().get(g.getPlayers().size()-1)));
    }
}*/