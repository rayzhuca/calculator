package main.calculator;

/**
 * An enum of possible operations
 */
public enum Operator {
    ADD("+", true, 1), SUBTRACT("-", true, 1), MULTIPLY("*", true, 2), DIVIDE("/", true, 2), EXPONENT("^", true, 3),
    SQAURE("sqrt", true, 3), // FACTORIAL("!", false, 4),
    OPEN_PARENTHESIS("_(", false, 0), CLOSE_PARENTHESIS("_(", false, 0); // The parentheses are special cases

    private String operator;
    private boolean isPrefix;
    private int precedence;

    /**
     * Constructor for the Operations enum
     * 
     * @param operator the operator symbol used in a string
     * @param isPrefix if the operator is calculated based on a prefix
     */
    Operator(String operator, boolean isPrefix, int precedence) {
        this.operator = operator;
        this.isPrefix = isPrefix;
        this.precedence = precedence;
    }

    /**
     * Checks if the operator is a parenthesis
     */
    protected boolean isParenthesis(Operator operator) {
        return operator.operator == "_(" || operator.operator == "_)";
    }

    /**
     * Gets the operator of the enum
     * 
     * @return the operator of the enum
     */
    protected String getOperator() {
        return operator;
    }

    /**
     * Checks if string is an operator
     * 
     * @param string to be checked
     * @return if string is an operator
     */
    protected static boolean isOperator(String string) {
        for (Operator operation : Operator.values()) {
            if (operation.getOperator().toLowerCase().equals(string.toLowerCase())) {
                if (operation.operator == "_(" || operation.operator == "_)") {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if character is an operator
     * 
     * @param character to be checked
     * @return if character is an operator
     */
    protected boolean isOperator(char character) {
        for (Operator operation : Operator.values()) {
            if (operation.toString().toLowerCase().equals(Character.toString(character).toLowerCase())) {
                if (operation.operator == "_(" || operation.operator == "_)") {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the enum based on the operator
     */
    protected static Operator getEnumFromOperator(String operator) throws IllegalArgumentException {
        for (Operator operation : Operator.values()) {
            if (operation.operator.toLowerCase().equals(operator.toLowerCase())) {
                return operation;
            }
        }
        return null;
    }

    /**
     * Gets if the operator is a prefix
     * 
     * @return if the operator is a prefix
     */
    protected boolean getPrefix() {
        return isPrefix;
    }

    /**
     * Checks if operator in form of a string is a prefix
     * 
     * @param string of the operator
     * @return if operator is a prefix
     */
    protected static boolean isPrefix(String string) {
        Operator operator;
        try {
            operator = Operator.valueOf(string);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return operator.getPrefix();
    }

    /**
     * Gets the predence of the operator
     * 
     * @return the precedence of the operator
     */
    protected int getPrecedence() {
        return precedence;
    }
}