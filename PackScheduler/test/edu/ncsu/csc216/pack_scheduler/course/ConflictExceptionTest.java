/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;


import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

/**
 * Tests the constructors of the custom exception ConflictException.
 * 
 * @author Kergan Sanderson
 */
class ConflictExceptionTest {

    /**
     * Test method for the custom ConflictException constructor.
     */
    @Test
    void testConflictExceptionString() {
        ConflictException ce = new ConflictException("Custom exception message");
        assertEquals("Custom exception message", ce.getMessage());
    }

    /**
     * Test method for the default ConflictException constructor.
     */
    @Test
    void testConflictException() {
        ConflictException ce = new ConflictException();
        assertEquals("Schedule conflict.", ce.getMessage());
    }

}
