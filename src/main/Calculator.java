package main;

/**
 * <h1>Main controller of calculation methods</h1>
 * <p>
 * Calculator class converts a string to a double, and can be instantiated to
 * customize specific operations avaiable, and others.
 *
 * @author Ray Zhu
 * @version In development
 * @since 2019-02-9
 */

public class Calculator {

    /**
     * @return an instance of Calculator
     */
    public Calculator() {

    }

    /**
     * Calculates a string to double
     * 
     * @param input String to be calculated
     * @return Calculated number
     * @exception IllegalArgumentException if input is invalid
     */
    public double calculate(String input) throws IllegalArgumentException {

        return 0;
    }

    public static void main(String[] args) {
        System.out.println(Math.pow(5, 0.5));
        System.out.println(Operate.power(-5, 0.5));
    }
}