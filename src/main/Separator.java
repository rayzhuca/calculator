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
    private static boolean isNumeric(char character) {
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
    private static boolean isNumeric(String string) {
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
     * Combines non-numeric parts of the list into operations
     * 
     * @param list of the original list
     * @return the new list with grouped operations
     */
    public static ArrayList<String> checkList(ArrayList<String> list) throws IllegalArgumentException {
        for (int i = 0; list.size() > i; i++) {
            String string = list.get(i);
            if (!isNumeric(string) && !isSign(string.charAt(0))) {
                if (!Operation.Operations.isOperator(string)) {
                    throw new IllegalArgumentException("Unknown operator");
                }
            }
        }

        return list;
    }
    
    /**
     * Organizes the list mathematically, accounting for GEMDAS
     * 
     * @param list of the list to be sorted
     * @return the organized list
     */
    public static ArrayList<String> organizeMathematically(ArrayList<String> list) {
        return list;
    }
}