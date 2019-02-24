package main;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;

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
        this(new Manager());
    }

    /**
     * Calculates a string to double
     * 
     * @param input String to be calculated
     * @return Calculated number
     * @throws IllegalArgumentException if input is invalid
     */
    public double calculate(String input) throws IllegalArgumentException {
        String[] array = assembleArray(input);
        
        return calculate(array);
    }

    /**
     * Calculates a string into an mathematically organized list to be calculated
     * 
     * @param input String to be calculated
     * @return mathematically organized list
     * @throws IllegalArgumentException if input is invalid
     */
    public String[] assembleArray(String input) throws IllegalArgumentException {
        input = Separator.combineSigns(Separator.removeWhiteSpace(input));
        ArrayList<String> list;
        try {
            list = Separator.separateParts(input);
            list = Separator.checkList(list);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        
        return list.toArray(new String[0]);
    }

    /**
     * Calculates an array to a double
     * 
     * @param list String to be calculated
     * @return calculated number
     */
    public double calculate(String[] list) throws IllegalArgumentException {
        double solution = 0;

        ArrayList<String> queue = Separator.shuntingYard(list);
        
        return Separator.calculatePostfix(queue);
    }

    public static void main(String[] args) {
        System.out.println(new Calculator().calculate("2+0.5"));
    }
}