package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * Tests the CourseNameValidator Class. Starts in the initial state and tests all 
 * possible valid transitions through the following states. Also tests an invalid 
 * transition for each state.
 * 
 * @author Kergan Sanderson
 * @author Cameron Edwards
 * @author Artie Yakovlev
 */
class CourseNameValidatorFSMTest {
    
    /** A CourseNameValidatorFSM for access to the isValid method. */
    private CourseNameValidatorFSM validator;

    /**
     * Tests path through the initial state. Considers invalid input at and beyond this state.
     */
	@Test
	public void testStateInitial() {
	    validator = new CourseNameValidatorFSM();
	    try {
	        assertTrue(validator.isValid("LLLL555"));
	    } catch (InvalidTransitionException e) {
	        fail();
	    }
	    
	    Exception e1 = assertThrows(InvalidTransitionException.class, () -> validator.isValid("5FD555"));
        assertEquals(e1.getMessage(), "Course name must start with a letter.");
	    
	    Exception e2 = assertThrows(InvalidTransitionException.class, () -> validator.isValid("*FD555"));
        assertEquals(e2.getMessage(), "Course name can only contain letters and digits.");
	}
	
	/**
     * Tests path through the first letter state. Considers invalid input at and beyond this state.
     */
    @Test
    public void testStateL() {
        validator = new CourseNameValidatorFSM();
        try {
            assertTrue(validator.isValid("LL555"));
            assertTrue(validator.isValid("L555"));
        } catch (InvalidTransitionException e) {
            fail();
        }
        Exception e = assertThrows(InvalidTransitionException.class, () -> validator.isValid("L&FD555"));
        assertEquals(e.getMessage(), "Course name can only contain letters and digits.");
    }
    
    /**
     * Tests path through the two letter state. Considers invalid input at and beyond this state.
     */
    @Test
    public void testStateLL() {
        validator = new CourseNameValidatorFSM();
        try {
            assertTrue(validator.isValid("LLL555"));
            assertTrue(validator.isValid("LL555"));
        } catch (InvalidTransitionException e) {
            fail();
        }
        Exception e = assertThrows(InvalidTransitionException.class, () -> validator.isValid("LL&D555"));
        assertEquals(e.getMessage(), "Course name can only contain letters and digits.");
    }

    /**
     * Tests path through the three letter state. Considers invalid input at and beyond this state.
     */
    @Test
    public void testStateLLL() {
        validator = new CourseNameValidatorFSM();
        try {
            assertTrue(validator.isValid("LLLL555"));
            assertTrue(validator.isValid("LLL555"));
        } catch (InvalidTransitionException e) {
            fail();
        }
        Exception e = assertThrows(InvalidTransitionException.class, () -> validator.isValid("LLL^555"));
        assertEquals(e.getMessage(), "Course name can only contain letters and digits.");
    }
    
    /**
     * Tests path through the four letter state. Considers invalid input at and beyond this state.
     */
    @Test
    public void testStateLLLL() {
        validator = new CourseNameValidatorFSM();
        try {
            assertTrue(validator.isValid("LLLL555"));
        } catch (InvalidTransitionException e) {
            fail();
        }
        
        Exception e1 = assertThrows(InvalidTransitionException.class, () -> validator.isValid("LLLLL555"));
        assertEquals(e1.getMessage(), "Course name cannot start with more than 4 letters.");
        
        Exception e = assertThrows(InvalidTransitionException.class, () -> validator.isValid("LLLL^55"));
        assertEquals(e.getMessage(), "Course name can only contain letters and digits.");
    }
    
    /**
     * Tests path through the first digit state. Considers invalid input at and beyond this state.
     */
    @Test
    public void testStateD() {
        validator = new CourseNameValidatorFSM();
        try {
            assertTrue(validator.isValid("L155"));
        } catch (InvalidTransitionException e) {
            fail();
        }
        
        Exception e1 = assertThrows(InvalidTransitionException.class, () -> validator.isValid("L5L5"));
        assertEquals(e1.getMessage(), "Course name must have 3 digits.");
        
        Exception e2 = assertThrows(InvalidTransitionException.class, () -> validator.isValid("L^55"));
        assertEquals(e2.getMessage(), "Course name can only contain letters and digits.");
    }
    
    /**
     * Tests path through the second digit state. Considers invalid input at and beyond this state.
     */
    @Test
    public void testStateDD() {
        validator = new CourseNameValidatorFSM();
        try {
            assertTrue(validator.isValid("L515"));
        } catch (InvalidTransitionException e) {
            fail();
        }
        
        Exception e1 = assertThrows(InvalidTransitionException.class, () -> validator.isValid("L55L"));
        assertEquals(e1.getMessage(), "Course name must have 3 digits.");
        
        
        Exception e2 = assertThrows(InvalidTransitionException.class, () -> validator.isValid("L5^5"));
        assertEquals(e2.getMessage(), "Course name can only contain letters and digits.");
    }
    
    /**
     * Tests path to the three digit final state. Considers invalid input at and beyond this state.
     */
    @Test
    public void testStateDDD() {
        validator = new CourseNameValidatorFSM();
        try {
            assertTrue(validator.isValid("L555"));
            assertTrue(validator.isValid("L555A"));
        } catch (InvalidTransitionException e) {
            fail();
        }
        
        Exception e1 = assertThrows(InvalidTransitionException.class, () -> validator.isValid("L5555"));
        assertEquals(e1.getMessage(), "Course name can only have 3 digits.");
        
        
        Exception e2 = assertThrows(InvalidTransitionException.class, () -> validator.isValid("L55^"));
        assertEquals(e2.getMessage(), "Course name can only contain letters and digits.");
    }
    
    /**
     * Tests path to the suffix final state. Considers all invalid input at and beyond this state.
     */
    @Test
    public void testStateSuffix() {
        validator = new CourseNameValidatorFSM();
        try {
            assertTrue(validator.isValid("L555A"));
        } catch (InvalidTransitionException e) {
            fail();
        }
        
        Exception e1 = assertThrows(InvalidTransitionException.class, () -> validator.isValid("L555AL"));
        assertEquals(e1.getMessage(), "Course name can only have a 1 letter suffix.");
        
        Exception e2 = assertThrows(InvalidTransitionException.class, () -> validator.isValid("L555A5"));
        assertEquals(e2.getMessage(), "Course name cannot contain digits after the suffix.");
        
        Exception e3 = assertThrows(InvalidTransitionException.class, () -> validator.isValid("L555%"));
        assertEquals(e3.getMessage(), "Course name can only contain letters and digits.");
    }
}
