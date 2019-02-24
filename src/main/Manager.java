package main;

/**
 * <h1>Manager manages settings for Calculator</h1>
 * <p>
 * <b>Note:</b> This class is only intended to be used by Calculator
 *
 * @author Ray
 * @since 2019-02-9
 */
public class Manager {
    private Operation.Operators[] allowedOperations = Operation.Operators.values();
    
    /**
     * Makes a Calculator instance with default settings
     */
    public Manager() {

    }
    
    /**
     * Makes a Calculator instance with an array of allowed Operations
     * 
     * @param operations[] The allowed operations to be used by the calculator
     */
    public Manager(Operation.Operators[] operations) {
        allowedOperations = operations;
    }

    /**
     * Returns the allowed operations
     * 
     * @return The allowed operation
     */
    public Operation.Operators[] getAllowedOperations() {
        return allowedOperations;
    }
    
    /**
     * Sets the operations that is allowed 
     * 
     * @param operations The operations allowed
     */
    public void setOperations(Operation.Operators[] operations) {
        allowedOperations = operations;
    }

    /**
     * Checks if operation is allowed
     * 
     * @param operation the operation to be checked
     * @return if operation is allowed
     */
    public boolean checkOperationValidity(Operation.Operators operation) {
        for (Operation.Operators v : allowedOperations) {
            if (v == operation) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if character is an operator
     * 
     * @param character the operator to be checked
     * @return if character is a operator
     */
    public boolean isOperatior(char character) {
        for (Operation.Operators v : allowedOperations) {
            if (v.getOperator().charAt(0) == character) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if string is an operator
     * 
     * @param string the operator to be checked
     * @return if string is a operator
     */
    public boolean isOperatior(String string) {
        for (Operation.Operators v : allowedOperations) {
            if (v.getOperator() == string) {
                return true;
            }
        }

        return false;
    }
}