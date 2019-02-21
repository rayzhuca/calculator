package main;

import java.util.ArrayList;
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

        String[] manipulatedArray = list.toArray(new String[list.size()]);;
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
     * Makes an array of indecies to be calculated mathematically, accounting for GEMDAS
     * 
     * @param list of the list to be sorted
     * @return an array of indecies
     */
    public static synchronized int[] organizeMathematically(ArrayList<String> list) {
        int[] indicies = new int[list.size()];
        int nested = 0;

        //loop and get the highest nests
        //add the indicies of that highest nest
        //get the second highest nests
        //etc  

        for (String string : list) {
            if (string == "(") {
                nested += 1;
            } else if (string == ")") {
                nested -= 1;
            }
        }

        return indicies;
    }
}