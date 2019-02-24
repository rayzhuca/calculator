package main.example;

import java.util.Scanner;
import main.calculator.Calculator;

/**
 * Example on how to use the calculator library.
 */
public class Main {
    private static final String ANSI_RED = "\u001b[31;1m";
    private static final String ANSI_RESET = "\u001B[0m";

    /**
     * The main method.
     * 
     * @param args[] The arguments of the java file is ran.
     */
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        try (scanner) {
            while (true) {
                String input = scanner.nextLine();
                try {
                    double result = calculator.calculate(input);
                    System.out.println(ANSI_RED + result + ANSI_RESET);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}