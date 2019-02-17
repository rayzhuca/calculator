package main;

import java.util.ArrayList;

/**
 * <h1>Main controller of calculation methods</h1>
 * <p>
 * Calculator class converts a string to a double, and can be instantiated to
 * customize specific operations avaiable, and others.
 *
 * @author Ray
 * @version 1
 * @since 2019-02-9
 */
public class Calculator extends Manager {
    private Manager manager;

    /**
     * Makes a Calculator instance with a pre-made Manager instance
     * 
     * @param manager The manager to be used
     */
    public Calculator(Manager manager) {
        this.manager = manager;
    }

    /**
     * Makes a Calculator instance with default settings
     * 
     * @return an instance of Calculator
     */
    public Calculator() {

    }

    /**
     * Calculates a string to double
     * 
     * @param input String to be calculated
     * @return Calculated number
     * @throws IllegalArgumentException if input is invalid
     */
    public double calculate(String input) throws IllegalArgumentException {
        input = Separator.removeWhiteSpace(input);
        input = Separator.combineSigns(input);
        ArrayList<String> list = Separator.separateParts(input);
        try {
            list = Separator.checkList(list);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return 0;
    }
}