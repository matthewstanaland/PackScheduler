/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Provides functionality for checking for time conflict between Activity objects.
 * 
 * @author Kergan Sanderson
 */
public interface Conflict {
    
    /**
     * Checks if this activity has a time conflict with the given Activity.
     * @param possibleConflictingActivity the Activity that may have a time conflict with this Activity
     * @throws ConflictException when this Activity has a time conflict with the provided Activity
     */
    void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
