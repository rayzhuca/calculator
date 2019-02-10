package main;

/**
 * <h1>Operations used in Calculator</h1>
 * <p>
 * <b>Note:</b> This class is only intended to be used by Calculator
 *
 * @author Ray Zhu
 * @since 2019-02-9
 */

final class Operate {

    /**
     * Adds doubles together
     * 
     * @param addends... Numbers to be added
     * @return The sum of addends[]
     * @exception IllegalArgumentException if sum is infinite
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
     * @exception IllegalArgumentException if difference is infinite
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
     * @exception ArithmeticException if product is infinite
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
     * @exception ArithmeticException if dividend is divided by 0
     * @exception IllegalArgumentException if quotient is infinite
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
     * @exception ArithmeticException if the power is infinite
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
     * Calculates the factorial
     * 
     * @param factorial The factorial to be calculated
     * @return The product of a factorial
     * @exception ArithmeticException if product is infinite
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
}