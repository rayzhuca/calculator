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
        ArrayList<String> list = assembleList(input);
        double solution = calculate(list);
        return solution;
    }

    /**
     * Calculates a string into an mathematically organized list to be calculated
     * 
     * @param input String to be calculated
     * @return mathematically organized list
     * @throws IllegalArgumentException if input is invalid
     */
    public ArrayList<String> assembleList(String input) throws IllegalArgumentException {
        input = Separator.combineSigns(Separator.removeWhiteSpace(input));
        ArrayList<String> list = Separator.separateParts(input);
        System.out.println(list.toString());
        try {
            list = Separator.checkList(list);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        
        return list;
    }

    /**
     * Calculates a list to double
     * 
     * @param list String to be calculated
     * @return calculated number
     */
    public double calculate(ArrayList<String> list) throws IllegalArgumentException {
        double solution = 0;
        // double lastNumber = 0;
        // String operation = "";
        // for (String s : list) {
        //     if (Separator.isNumeric(s)) {
        //         if (operation.isEmpty()) {
        //             lastNumber = Double.parseDouble(s);
        //             continue;
        //         }
        //         if (Operation.Operations.isPrefix(operation)) {
        //             lastNumber = lastNumber + Double.parseDouble(s);
        //         }
        //     } else {
        //         operation = s;
        //         if (!Operation.Operations.isPrefix(s)) {

        //         }
        //     }
        // }

        return solution;
    }
}