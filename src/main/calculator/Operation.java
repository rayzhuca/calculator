package main.calculator;

/**
 * <h1>Operation contains methods for calculations</h1>
 * <p>
 * <b>Note:</b> This class is only intended to be used by Calculator
 *
 * @author Ray
 * @since 2019-02-9
 */
final class Operation {

    /**
     * Adds doubles together
     * 
     * @param addends... Numbers to be added
     * @return The sum of addends[]
     * @throws IllegalArgumentException if sum is infinite
     */
    protected static double add(double... addends) throws IllegalArgumentException {
        if (0 == addends.length|| null == addends) {
            return 0;
        }

        double sum = 0;

        for (double num : addends) {
            sum += num;
        }

        if (Double.isInfinite(sum)) {
            throw new IllegalArgumentException("Sum is infinite");
        }
        return sum;
    }

    /**
     * Subtracts doubles together
     * 
     * @param subtrahend... Numbers to be subtracted
     * @return The difference of subtrahend[]
     * @throws IllegalArgumentException if difference is infinite
     */
    protected static double subtract(double... subtrahend) throws IllegalArgumentException {
        if (0 == subtrahend.length || null == subtrahend) {
            return 0;
        }

        double difference = subtrahend[0] * 2;

        for (double num : subtrahend) {
            difference -= num;
        }

        if (Double.isInfinite(difference)) {
            throw new IllegalArgumentException("Difference is infinite");
        }
        return difference;
    }

    /**
     * Multiplies doubles together
     * 
     * @param multiplier... Numbers to be multiplied
     * @return The product of multiplier[]
     * @throws ArithmeticException if product is infinite
     */
    protected static double multiply(double... multiplier) throws IllegalArgumentException {
        if (0 == multiplier.length || null == multiplier) {
            return 0;
        }

        double product = 1;

        for (double num : multiplier) {
            product *= num;
        }

        if (Double.isInfinite(product)) {
            throw new IllegalArgumentException("Product is infinite");
        }
        return product;
    }

    /**
     * Divides doubles together
     * 
     * @param divisor... Numbers to be divided
     * @return The quotient of divisor[]
     * @throws ArithmeticException if dividend is divided by 0
     * @throws IllegalArgumentException if quotient is infinite
     */
    protected static double divide(double... divisor) throws IllegalArgumentException, ArithmeticException {
        if (divisor.length == 0 || divisor == null) {
            return 0;
        }

        double quotient = divisor[0] * divisor[0];
        boolean dividend = true;

        for (double num : divisor) {
            if (dividend) {
                quotient /= num;
                dividend = false;
                continue;
            } else if (num == 0) {
                throw new ArithmeticException("Division by zero");
            }
            quotient /= num;
        }

        if (Double.isInfinite(quotient)) {
            throw new IllegalArgumentException("Quotient is infinite");
        }
        return quotient;
    }

    /**
     * Exponentiates of doubles together
     * 
     * @param base The base of the power
     * @param exponents... Numbers to be exponentiated
     * @return The power of exponents[]
     * @throws ArithmeticException if the power is infinite
     */
    protected static double power(double base, double... exponents) throws IllegalArgumentException {
        if (exponents.length == 0 || exponents == null) {
            return base;
        }

        double sign = (0 > base) ? -1: 1;
        double power = Math.abs(base);

        for (double exponent : exponents) {
            if (0 == exponent) {
                return 1;
            } else if (0 > exponent) {
                sign = sign * -1;
            }
            power = Math.pow(power, Math.abs(exponent));
        }

        if (Double.isInfinite(power)) {
            throw new IllegalArgumentException("Power is infinite");
        }
        return Math.copySign(power, sign);
    }

    /**
     * Square roots a single number
     * 
     * @param radicand The radicand of a square root, must be positive
     * @param indicies... The indicies of a square root
     * @return The square root of the radicand to the indicies
     * @throws ArithmeticException if the square root is infinite
     * @throws IllegalArgumentException if a radicand is negative
     */
    protected static double square(double radicand, double... indicies) throws IllegalArgumentException, IllegalArgumentException {
        if (indicies.length == 0 || indicies == null) {
            indicies = new double[] {2};
        }
        if (radicand < 0) {
            throw new IllegalArgumentException("Negative radicand"); 
        }

        double square = radicand;

        for (double index : indicies) {
            square = power(square, 1 / index);
        }

        if (Double.isInfinite(square)) {
            throw new IllegalArgumentException("Square root is infinite");
        }
        return square;
    }

    /**
     * Calculates the factorial
     * 
     * @param factorial The factorial to be calculated
     * @return The product of a factorial
     * @throws ArithmeticException if product is infinite
     */
    protected static double factorial(double factorial) throws IllegalArgumentException {
        if (factorial == 0) {
            return 1;
        }

        double product = 1;

        for (int i = 1; i <= Math.abs(factorial); i++) {
            product *= i;
        }

        if (Double.isInfinite(product)) {
            throw new IllegalArgumentException("Product is infinite");
        }
        return Math.copySign(product, factorial);
    }

    /**
     * Calculates a double from two numbers accordingly
     * 
     * @param operator the operation to be used
     * @param num1 the first number
     * @param num2 the second number
     * @return the result
     */
    protected static double operate(Operators operator, double num1, double num2) throws IllegalArgumentException {
		if (operator == Operators.ADD) {
            return add(num1, num2);
        } else if (operator == Operators.SUBTRACT) {
            return subtract(num1, num2);
        } else if (operator == Operators.MULTIPLY) {
            return multiply(num1, num2);
        } else if (operator == Operators.DIVIDE) {
            return divide(num1, num2);
        } else if (operator == Operators.EXPONENT) {
            return power(num1, num2);
        } else if (operator == Operators.SQAURE) {
            return square(num1, num2);
        }
        throw new IllegalArgumentException("Illegal operator");
	}

    /**
     * An enum of possible operations
     */
    protected enum Operators {
        ADD("+", true, 1), SUBTRACT("-", true, 1), MULTIPLY("*", true, 2), DIVIDE("/", true, 2), EXPONENT("^", true, 3), SQAURE("sqrt", true, 3), //FACTORIAL("!", false, 4),
        OPEN_PARENTHESIS("(_", false, 0), CLOSE_PARENTHESIS("(_", false, 0); //The parentheses are special cases

        private String operator;
        private boolean isPrefix;
        private int precedence;

        /**
         * Constructor for the Operations enum
         * 
         * @param operator the operator symbol used in a string
         * @param isPrefix if the operator is calculated based on a prefix
         */
        Operators(String operator, boolean isPrefix, int precedence) {
            this.operator = operator;
            this.isPrefix = isPrefix;
            this.precedence = precedence;
        }

        /**
         * Checks if the operator is a parenthesis
         */
        protected boolean isParenthesis(Operators operator) {
            return operator.operator == "(_" || operator.operator == ")_";
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
            for (Operators operation : Operators.values()) {
                if (operation.getOperator().toLowerCase().equals(string.toLowerCase())) {
                    if (operation.operator == "(_" || operation.operator == ")_") {
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
            for (Operators operation : Operators.values()) {
                if (operation.toString().toLowerCase().equals(Character.toString(character).toLowerCase())) {
                    if (operation.operator == "(_" || operation.operator == ")_") {
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
        protected static Operators getEnumFromOperator(String operator) throws IllegalArgumentException {
            for (Operators operation : Operators.values()) {
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
            Operators operator;
            try {
                operator = Operators.valueOf(string);
            } catch(IllegalArgumentException e) {
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
}