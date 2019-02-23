package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import main.Operation;
import main.Operation.Operators;
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

                if (Operators.getEnumFromOperator(queuedString) != null) {
                    list.add(queuedString);
                    queuedString = "";
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
        boolean wasOperatorPrefix = false;
        boolean wasOperatorSuffix = false;
        for (int i = 0; list.size() > i; i++) {
            String string = list.get(i);
            if (!isNumeric(string) && !isSign(string.charAt(0))) {
                if (!Operators.isOperator(string) && !(string.equals("(") || string.equals(")"))) {
                    throw new IllegalArgumentException("Unknown operator");
                }
            }

            if (Operators.isOperator(string)) {
                Operators operator = Operators.getEnumFromOperator(string);
                if (wasOperatorPrefix && operator.getPrefix()) {
                    throw new IllegalArgumentException("Doubled prefix operators");
                }
                if (wasOperatorSuffix && !operator.getPrefix()) {
                    throw new IllegalArgumentException("Doubled suffix operators");
                }

                if (operator.getPrefix()) {
                    wasOperatorPrefix = true;
                    wasOperatorSuffix = false;
                } else {
                    wasOperatorPrefix = false;
                    wasOperatorSuffix = true;
                }
            } else {
                wasOperatorSuffix = false;
                wasOperatorPrefix = false;
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
     * Parses a mathematical expression from infix form into postfix form
     * using the shunting-yard algorithm
     * 
     * @param input the input to be rearraged
     * @return the postfix form of the input
     */
    public static ArrayList<String> shuntingYard(String[] input) {
        ArrayList<String> queue = new ArrayList<String>();
        Stack<Operators> stack = new Stack<Operators>();

        for (int i = 0; i < input.length; i++) {
            String token = input[i];
            if (isNumeric(token)) {
                queue.add(token);
            } else if (Operators.isOperator(token)) {
                Operators tokenEnum = Operators.getEnumFromOperator(token);
                if (!stack.isEmpty()) {
                    while (stack.lastElement().getPrecedence() > tokenEnum.getPrecedence()
                            || (stack.lastElement().getPrecedence() == tokenEnum.getPrecedence()
                                    && stack.lastElement().getPrefix())
                                    && !stack.lastElement().getOperator().equals("(_")) {
                        queue.add(stack.pop().getOperator());
                        if (stack.isEmpty()) {
                            break;
                        }
                    }
                }
                stack.push(tokenEnum);
            } else if (token.equals("(")) {
                Operators tokenEnum = Operators.getEnumFromOperator(token);
                stack.push(tokenEnum);
            } else if (token.equals(")")) {
                while (!stack.lastElement().equals(Operators.OPEN_PARENTHESIS)) {
                    queue.add(stack.pop().getOperator());
                }
                while (stack.lastElement().equals(Operators.OPEN_PARENTHESIS)) {
                    stack.pop();
                }
            }
        }

        while (!stack.isEmpty()) {
            queue.add(stack.pop().getOperator());
        }

        return queue;
    }

    /**
     * Calculates a queue that is a mathematical expression in postfix form into a double
     * 
     * @param queue the queue to be calculated
     * @return the number returned
     */
    public static double calculatePostfix(ArrayList<String> queue) {
        ArrayList<Double> stack = new ArrayList<Double>();
        String[] array = queue.toArray(new String[0]);
        int i = 0;
        
        for (String ele : array) {
            if (isNumeric(ele)) {
                stack.add(Double.parseDouble(ele));
            } else if (Operators.isOperator(ele)) {
                double num2 = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                double num1 = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);

                Operators operator = Operators.getEnumFromOperator(ele);

                stack.add(Operation.operate(operator, num1, num2));
            }
        }

		return stack.toArray(new Double[0])[0];
    }
}