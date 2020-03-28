package it.polimi.ingsw.PSP038.model;

import static org.junit.Assert.*;
import static it.polimi.ingsw.PSP038.model.ArgumentChecker.*;

import org.junit.Test;

public class ArgumentCheckerTest {

    @Test
    public void requireNonNegativeAcceptPositives() {
        assertEquals("Zero is accepted", 0, requireNonNegative(0));
        assertEquals("One is accepted", 1, requireNonNegative(1));
        assertEquals("42 is accepted", 42, requireNonNegative(42));
        assertEquals("Max integer is accepted", Integer.MAX_VALUE, requireNonNegative(Integer.MAX_VALUE));
    }

    @Test(expected=IllegalArgumentException.class)
    public void requireNonNegativeRejectMinusOne() {
        requireNonNegative(-1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void requireNonNegativeRejectMinus42() {
        requireNonNegative(-42);
    }

    @Test(expected=IllegalArgumentException.class)
    public void requireNonNegativeRejectMinInteger() {
        requireNonNegative(Integer.MIN_VALUE);
    }

    @Test
    public void requireBetweenAcceptRangeValues(){
        int lowerBound = 1;
        int upperBound = 5;

        assertEquals("1 is accepted", 1, requireBetween(lowerBound, upperBound, 1));
        assertEquals("3 is accepted", 3, requireBetween(lowerBound, upperBound, 3));
        assertEquals("4 is accepted", 4, requireBetween(lowerBound, upperBound, 4));
    }

    @Test(expected=IllegalArgumentException.class)
    public void requireBetweenRejectUpperBound() {
        requireBetween(1, 5, 5);
    }

    @Test(expected=IllegalArgumentException.class)
    public void requireBetweenRejectMaxInteger() {
        requireBetween(1, 5, Integer.MAX_VALUE);
    }

    @Test(expected=IllegalArgumentException.class)
    public void requireBetweenRejectMinInteger() {
        requireBetween(1, 5, Integer.MIN_VALUE);
    }

}