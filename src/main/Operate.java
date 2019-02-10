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
     * @param numbers... Numbers to be added
     * @return The sum of numbers[]
     */
    protected static double add(double... numbers) {
        if (numbers.length == 0 || numbers == null) {
            return 0;
        }

        double sum = 0;

        for (double num : numbers) {
            sum += num;
        }
        return sum;
    }

    /**
     * Subtracts doubles together
     * 
     * @param numbers... Numbers to be subtracted
     * @return The difference of numbers[]
     */
    protected static double subtract(double... numbers) {
        if (numbers.length == 0 || numbers == null) {
            return 0;
        }

        double difference = numbers[0] * 2;

        for (double num : numbers) {
            difference -= num;
        }
        return difference;
    }

    /**
     * Multiplies doubles together
     * 
     * @param numbers... Numbers to be multiplied
     * @return The product of numbers[]
     */
    protected static double multiply(double... numbers) {
        if (numbers.length == 0 || numbers == null) {
            return 0;
        }

        double product = 0;

        for (double num : numbers) {
            product *= num;
        }
        return product;
    }

    /**
     * Divides doubles together
     * 
     * @param numbers... Numbers to be divided
     * @return The quotient of numbers[]
     */
    protected static double divide(double... numbers) {
        if (numbers.length == 0 || numbers == null) {
            return 0;
        }

        double quotient = numbers[0] * numbers[0];

        for (double num : numbers) {
            quotient /= num;
        }
        return quotient;
    }
}