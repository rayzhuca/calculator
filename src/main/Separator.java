package main;

import java.util.ArrayList;
import main.BinaryTree.Node;

import java.lang.String;

/**
 * <h1>Separates string to be calculated</h1>
 * <p>
 * <b>Note:</b> This class is only intended to be used by Calculator
 *
 * @author Ray
 * @since 2019-02-10
 */
final class Separator {

    /**
     * Removes whitespace in a string
     * 
     * @param string the string to be manipulated
     * @return the string without whitespace
     */
    public static String removeWhiteSpace(String string) {
        return string.replaceAll("\\s+", "");
    }

    /**
     * Checks if character is '+' or '-'
     * 
     * @param character the character to be checked
     * @return if character is a sign
     */
    private static boolean isSign(char character) {
        return character == '+' || character == '-' ? true : false;
    }

    /**
     * Combines two signs
     * 
     * @param first  the first sign to be checked
     * @param second the second sign to be checked
     * @return the sign combined
     */
    private static char combineSign(char first, char second) {
        return (first == '+') ? (second == '+' ? '+' : '-') : (second == '+' ? '-' : '+');
    }

    /**
     * Checks if a character is numeric
     * 
     * @param character to be checked
     * @return if character is numeric
     */
    public static boolean isNumeric(char character) {
        try {
            Double.parseDouble(String.valueOf(character));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a string is numeric
     * 
     * @param string to be checked
     * @return if string is numeric
     */
    public static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Combines signs (- and +) together
     * 
     * @param original The string to be manipulated
     * @return Manipulated string
     */
    public static String combineSigns(String original) {
        ArrayList<String> list = new ArrayList<String>();
        char[] originalCharArray = original.toCharArray();
        boolean foundSign = false;
        char queuedSign = '+';

        for (int i = 0; originalCharArray.length > i; i++) {
            char character = originalCharArray[i];
            if (isSign(character)) {
                foundSign = true;
                queuedSign = combineSign(queuedSign, character);
            } else {
                if (foundSign) {
                    list.add(String.valueOf(queuedSign));
                    queuedSign = '+';
                    foundSign = false;
                }
                list.add(String.valueOf(character));
            }
        }

        String[] manipulatedArray = list.toArray(new String[list.size()]);
        ;
        String manipulated = "";
        for (String str : manipulatedArray) {
            manipulated = manipulated + str;
        }

        return manipulated;
    }

    /**
     * Separates string into numbers and operations
     * 
     * @param string The string to be separated
     * @return A string list of number and others in order
     */
    public static ArrayList<String> separateParts(String string) {
        ArrayList<String> list = new ArrayList<String>();

        boolean foundNum = false;
        boolean foundString = false;
        char[] charArray = string.toCharArray();
        String queuedNumber = "";
        String queuedString = "";
        for (int i = 0; charArray.length > i; i++) {
            char character = charArray[i];
            if (isNumeric(character)) {
                if (foundString && !queuedString.isEmpty()) {
                    list.add(queuedString);
                    queuedString = "";
                }

                foundString = false;
                foundNum = true;
                queuedNumber = queuedNumber + character;
            } else {
                if (foundNum && !queuedNumber.isEmpty()) {
                    list.add(queuedNumber);
                    queuedNumber = "";
                }

                foundNum = false;
                foundString = true;

                if (isSign(character)) {
                    list.add(Character.toString(character));
                } else if (character == '(' || character == ')') {
                    list.add(Character.toString(character));
                } else {
                    queuedString = queuedString + character;
                }
            }
        }
        if (!queuedNumber.isEmpty()) {
            list.add(queuedNumber);
        } else if (!queuedString.isEmpty()) {
            list.add(queuedString);
        }
        return list;
    }

    /**
     * Checks if a list is valid to be calculated
     * 
     * @param list of the original list
     * @return the new list with grouped operations
     */
    public static ArrayList<String> checkList(ArrayList<String> list) throws IllegalArgumentException {
        int openParenthesis = 0;
        int closeParenthesis = 0;
        boolean wasOperator = false;
        for (int i = 0; list.size() > i; i++) {
            String string = list.get(i);
            if (!isNumeric(string) && !isSign(string.charAt(0))) {
                if (!Operation.Operators.isOperator(string) && !(string.equals("(") || string.equals(")"))) {
                    throw new IllegalArgumentException("Unknown operator");
                }
            }

            if (Operation.Operators.isOperator(string)) {
                Operation.Operators operator = Operation.Operators.valueOf(string.toUpperCase());
                if (operator.getPrefix()) {
                    wasOperator = true;
                } else {
                    wasOperator = false;
                }
            } else {
                wasOperator = true;
            }
        }

        if (openParenthesis > closeParenthesis) {
            if (openParenthesis - closeParenthesis == 1) {
                throw new IllegalArgumentException("Unexpected open parenthesis");
            } else {
                throw new IllegalArgumentException("Unexpected open parentheses");
            }
        } else if (closeParenthesis > openParenthesis) {
            if (closeParenthesis - openParenthesis == 1) {
                throw new IllegalArgumentException("Unexpected close parenthesis");
            } else {
                throw new IllegalArgumentException("Unexpected close parentheses");
            }
        }

        return list;
    }

    /**
     * Makes a expression tree from a list ordered mathematically (GEMDAS)
     * 
     * @param list of the list to be sorted
     * @return a binary tree
     */
    public static synchronized BinaryTree organizeMathematically(ArrayList<String> list) {
        BinaryTree tree = new BinaryTree();
        String lowestPrecedenceString = "";
        int lowestPrecedenceIndex = 0;
        String[] array = list.toArray(new String[0]);

        for (int i = 0; i < array.length; i++) {
            String string = array[i];
            if (checkPrecedence(lowestPrecedenceString, string)) {
                lowestPrecedenceString = string;
                lowestPrecedenceIndex = i;
            }
        }

        String[][] arrays;

        String[] part1 = new String[lowestPrecedenceIndex];
        String[] part2 = new String[array.length - lowestPrecedenceIndex];

        System.arraycopy(array, 0, part1, 0, part1.length);
        System.arraycopy(array, part1.length, part2, 0, part2.length);

        organizeMathematically(0, tree, new String[][] {part1, part2});

        return tree;
    }

    /**
     * A recursive function that is used by organizeMathematically, it splits a list
     * into two, until it reaches the end, which then will be added to the binary
     * tree
     * 
     * @param key       the key the node is on
     * @param tree      the tree it is adding on
     * @param array[][] the arrays of arrays that is splited
     */
    private static String[][] organizeMathematically(int key, BinaryTree tree, String[][] array) {
        return null;
    }

    /**
     * Returns true if string have a higher precedence than check
     * 
     * @param string the string to be checked against
     * @param check  the string to be checked
     * @return if string have a higher precedence than check
     */
    private static boolean checkPrecedence(String string, String check) {
        return getPrecedence(string) > getPrecedence(check);
    }

    /**
     * Gets an int for precedence of the operator
     * 
     * @param string the string to be checked
     * @return the precednce
     */
    private static int getPrecedence(String string) {
        switch (string) {
        case "+":
        case "-":
            return 1;
        case "*":
        case "/":
            return 2;
        case "^":
        case "sqrt":
            return 3;
        case "!":
            return 4;
        case "(":
        case ")":
            return 5;
        default:
            return -1;
        }
    }
}