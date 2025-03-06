package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Custom exception for handling Invalid FSM Transitions.
 */
public class InvalidTransitionException extends Exception {

    /** ID used for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a Invalid FSM Transition. with a custom error message.
     * 
     * @param message the detail message for the exception
     */
    public InvalidTransitionException(String message) {
        super(message);
    }

    /**
     * Constructs a ConflictException with a default message.
     * The default message is "Schedule conflict.".
     */
    public InvalidTransitionException() {
        this("Invalid FSM Transition.");
    }
}
