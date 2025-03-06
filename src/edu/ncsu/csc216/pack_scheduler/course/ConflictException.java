/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * A checked exception that indicates when two Activities have a time conflict.
 * 
 * @author Kergan Sanderson
 */
public class ConflictException extends Exception {

    /** ID used for serialization. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs a ConflictException with a provided message.
     * @param message the message to be assigned to the exception
     */
    public ConflictException(String message) {
        super(message);
    }
    
    /**
     * Constructs a ConflictException with the default message "Schedule conflict."
     */
    public ConflictException() {
        this("Schedule conflict.");
    }

}
