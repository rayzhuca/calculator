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
     * @throws Exception if input is invalid
     */
    public double calculate(String input) throws Exception {
        try {
            String[] array = assembleArray(input);
            return calculate(array);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Calculates a string into a list to be organized
     * 
     * @param input String to be calculated
     * @return mathematically organized list
     * @throws Exception if input is invalid
     */
    public String[] assembleArray(String input) throws Exception {
        try {
            input = Separator.combineSigns(Separator.removeWhiteSpace(input));
            ArrayList<String> list = Separator.separateParts(input);
            return list.toArray(new String[0]);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Calculates an array to a double
     * 
     * @param array[] Strings to be calculated
     * @return calculated number
     * @throws Exception if list is invalid
     */
    public double calculate(String[] array) throws Exception {
        try {
            ArrayList<String> queue = Separator.shuntingYard(array);
            Separator.checkArray(array);
            return Separator.calculatePostfix(queue);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Checks if an array is valid for calculating
     * 
     * @param array[] the array to be calculated
     * @return if array is invalid
     */
    public boolean checkValidity(String[] array) {
        try {
            Separator.checkArray(array);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a list is valid for calculating
     * 
     * @param list[] the list to be calculated
     * @return if list is invalid
     */
    public boolean checkValidity(ArrayList<String> list) {
        try {
            Separator.checkList(list);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(new Calculator().calculate("2+2/2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}