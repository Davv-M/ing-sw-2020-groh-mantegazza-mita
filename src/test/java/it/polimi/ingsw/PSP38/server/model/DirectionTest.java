package it.polimi.ingsw.PSP38.server.model;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {
    @Test
    public void oppositeOfOppositeIsIdentity() {
        for (Direction d: Direction.values())
            assertEquals(d, d.opposite().opposite());
    }

    @Test
    public void oppositeWorksForAll4Directions() {
        assertEquals(Direction.S, Direction.N.opposite());
        assertEquals(Direction.W, Direction.E.opposite());
        assertEquals(Direction.N, Direction.S.opposite());
        assertEquals(Direction.E, Direction.W.opposite());
        assertEquals(Direction.SE, Direction.NW.opposite());
        assertEquals(Direction.SW, Direction.NE.opposite());
        assertEquals(Direction.NE, Direction.SW.opposite());
        assertEquals(Direction.NW, Direction.SE.opposite());
    }

    @Test
    public void coordinatesToDirectionCorrect() {
        assertEquals(Direction.S, Direction.coordinatesToDirection(0, 1));
        assertEquals(Direction.W, Direction.coordinatesToDirection(-1, 0));
        assertEquals(Direction.N, Direction.coordinatesToDirection(0, -1));
        assertEquals(Direction.E, Direction.coordinatesToDirection(1, 0));
        assertEquals(Direction.SE, Direction.coordinatesToDirection(1, 1));
        assertEquals(Direction.SW, Direction.coordinatesToDirection(-1, 1));
        assertEquals(Direction.NE, Direction.coordinatesToDirection(1, -1));
        assertEquals(Direction.NW, Direction.coordinatesToDirection(-1, -1));
    }
}