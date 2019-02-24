package main.calculator;

import java.util.ArrayList;
import java.util.Stack;

import main.calculator.Operation;
import main.calculator.Operation.Operators;
import java.lang.String;

/**
 * <h1>Organizes data from strings.</h1>
 * <p>
 * The Separator class is responsible for separating parts of a string into an
 * array, reorganizing arrays, handling arrays and calculating them into a
 * double. <b>Note:</b> This class is only intended to be used by Calculator.
 *
 * @author Ray Z.
 * @since 2019-02-10
 */
final class Separator {

    /**
     * Removes spaces in a string.
     * 
     * @param string The string to be evaluated.
     * @return The string without spaces.
     */
    protected static String removeWhiteSpace(String string) {
        return string.replaceAll("\\s+", "");
    }

    /**
     * Checks if character is a sign.
     * 
     * @param character The character to be checked.
     * @return If character is a sign, true if yes.
     */
    private static boolean isSign(char character) {
        return character == '+' || character == '-' ? true : false;
    }

    /**
     * Combines two signs together mathematically.
     * 
     * @param first  The first sign to be checked.
     * @param second The second sign to be checked.
     * @return The signs combined.
     */
    private static char combineSign(char first, char second) {
        return (first == '+') ? (second == '+' ? '+' : '-') : (second == '+' ? '-' : '+');
    }

    /**
     * Checks if a character is numeric.
     * 
     * @param character The character to be checked.
     * @return If the character is numeric, true if yes.
     */
    protected static boolean isNumeric(char character) {
        try {
            Double.parseDouble(String.valueOf(character));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a string is numeric.
     * 
     * @param string The string to be checked.
     * @return If the string is numeric, true if yes.
     */
    protected static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Takes a string and combines all signs inside mathematically.
     * 
     * @param original The string to be manipulated.
     * @return The manipulated string.
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
        String manipulated = "";
        for (String str : manipulatedArray) {
            manipulated = manipulated + str;
        }

        return manipulated;
    }

    /**
     * Separates string into numbers and operators into an arraylist.
     * 
     * @param string The string to be separated into an arraylist.
     * @return An arraylist of number and others in order.
     */
    protected static ArrayList<String> separateParts(String string) throws IllegalArgumentException {
        ArrayList<String> list = new ArrayList<String>();

        boolean foundNum = false;
        boolean foundString = false;
        char[] charArray = string.toCharArray();
        String queuedNumber = "";
        String queuedString = "";
        boolean wasPeriod = false;
        for (int i = 0; charArray.length > i; i++) {
            char character = charArray[i];
            if (i == 0 && isSign(character)) {
                queuedNumber = queuedNumber + character;
            } else if (character == '.') {
                if (wasPeriod) {
                    throw new IllegalArgumentException("Doubled period");
                }
                wasPeriod = true;
                foundString = false;
                foundNum = true;
                queuedNumber = queuedNumber + character;
            } else if (isNumeric(character)) {
                if (foundString && !queuedString.isEmpty()) {
                    list.add(queuedString);
                    queuedString = "";
                }

                foundString = false;
                foundNum = true;
                wasPeriod = false;
                queuedNumber = queuedNumber + character;
            } else {
                wasPeriod = false;
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
     * Evaluates a list and checks to see if the list is valid for calculations.
     * 
     * @param list The list to be evaluated.
     * @return If list is valid, true if yes.
     */
    protected static boolean checkList(ArrayList<String> list) throws IllegalArgumentException {
        try {
            return checkArray(list.toArray(new String[0]));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    /**
     * Evaluates an array and checks to see if the list is valid for calculations.
     * 
     * @param list The array to be evaluated.
     * @return If array is valid, true if yes.
     */
    protected static boolean checkArray(String[] array) throws IllegalArgumentException {
        int openParenthesis = 0;
        int closeParenthesis = 0;
        boolean wasOperatorPrefix = false;
        boolean wasOperatorSuffix = false;
        for (String string : array) {
            if (string.equals(".")) {
                throw new IllegalArgumentException("Unexpected period");
            }
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

        return true;
    }

    /**
     * Parses a mathematical expression from infix form into postfix form using the
     * shunting-yard algorithm from an array of strings.
     * 
     * @param input[] The array of strings to be rearraged.
     * @return The post form of the input.
     */
    protected static ArrayList<String> shuntingYard(String[] input) {
        ArrayList<String> list = new ArrayList<String>();
        Stack<Operators> stack = new Stack<Operators>();

        for (int i = 0; i < input.length; i++) {
            String token = input[i];
            if (isNumeric(token)) {
                list.add(token);
            } else if (Operators.isOperator(token)) {
                Operators tokenEnum = Operators.getEnumFromOperator(token);
                if (!stack.isEmpty()) {
                    while (stack.lastElement().getPrecedence() > tokenEnum.getPrecedence()
                            || (stack.lastElement().getPrecedence() == tokenEnum.getPrecedence()
                                    && stack.lastElement().getPrefix())
                                    && !stack.lastElement().getOperator().equals("(_")) {
                        list.add(stack.pop().getOperator());
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
                    list.add(stack.pop().getOperator());
                }
                while (stack.lastElement().equals(Operators.OPEN_PARENTHESIS)) {
                    stack.pop();
                }
            }
        }

        while (!stack.isEmpty()) {
            list.add(stack.pop().getOperator());
        }

        return list;
    }

    /**
     * Calculates a mathematical arraylist in postfix form into a double
     * mathematically.
     * 
     * @param array[] The array to be evaluated.
     * @return The number returned.
     */
    protected static double calculatePostfix(String[] array) {
        ArrayList<Double> stack = new ArrayList<Double>();

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