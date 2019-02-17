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
    private Operation.Operations[] allowedOperations = Operation.Operations.values();
    
    /**
     * Makes a Calculator instance with default settings
     */
    public Manager() {

    }
    
    /**
     * Makes a Calculator instance with an array of allowed Operations
     */
    public Manager(Operation.Operations[] operations) {
        allowedOperations = operations;
    }

    /**
     * Returns the allowed operations
     * 
     * @return The allowed operation
     */
    public Operation.Operations[] getAllowedOperations() {
        return allowedOperations;
    }
    
    /**
     * Sets the operations that is allowed 
     * 
     * @param operations The operations allowed
     */
    public void setOperations(Operation.Operations[] operations) {
        allowedOperations = operations;
    }

    /**
     * Checks if operation is allowed
     * 
     * @param operation the operation to be checked
     * @return if operation is allowed
     */
    public boolean checkOperationValidity(Operation.Operations operation) {
        for (Operation.Operations v : allowedOperations) {
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
        for (Operation.Operations v : allowedOperations) {
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
        for (Operation.Operations v : allowedOperations) {
            if (v.getOperator() == string) {
                return true;
            }
        }

        return false;
    }
}