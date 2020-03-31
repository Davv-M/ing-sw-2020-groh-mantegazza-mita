package it.polimi.ingsw.PSP038.utilities;

/**
 * Non instantiable class that provides methods used to control whether
 * arguments are valid or not.
 *
 * @author Maximilien Groh (10683107)
 */

public final class ArgumentChecker {

    /**
     * Empty constructor that causes the class to be non instantiable.
     */

    private ArgumentChecker() {

    }

    /**
     * Verifies that a given value is positive and returns it. If it is not the
     * case, it throws an IllegalArgumentException.
     *
     * @param value Integer that must be checked
     * @return the given value
     * @throws IllegalArgumentException if the given value is strictly negative.
     */

    public static int requireNonNegative(int value)
            throws IllegalArgumentException {
        if (value < 0) {
            throw new IllegalArgumentException(
                    "Non negative value expected, value was " + value
                            + " instead !");
        } else {
            return value;
        }
    }

    /**
     * Verifies that a given value is inside a certain range. If it is not the case,
     * it throws an IllegalArgumentException.
     *
     * @param lowerBound Lower bound of the range
     * @param upperBound Upper bound of the range
     * @param value      Integer that must be checked
     * @return the given value
     * @throws IllegalArgumentException if {@code (value < lowerBound || value >= upperBound)}
     */

    public static int requireBetween(int lowerBound, int upperBound, int value)
            throws IllegalArgumentException {
        if (value < lowerBound || value >= upperBound) {
            throw new IllegalArgumentException("Expected a value between "
                    + lowerBound + "and " + upperBound + ", value was " + value
                    + " instead !");
        } else {
            return value;
        }
    }
}
