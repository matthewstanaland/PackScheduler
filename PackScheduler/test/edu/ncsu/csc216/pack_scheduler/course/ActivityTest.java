/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the Activity class Conflict implementation in the WolfScheduler project.
 * 
 * @author Kergan Sanderson
 */
class ActivityTest {

    /**
     * Tests the checkConflict method from the Conflict interface for two Activities that do not conflict.
     */
    @Test
    public void testCheckConflict() {
        // Tests expected commutative method call for a no-conflict scenario
        Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 150, "MW", 1330, 1445);
        Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 150, "TH", 1330, 1445);
        
        assertDoesNotThrow(() -> a1.checkConflict(a2));
        assertDoesNotThrow(() -> a2.checkConflict(a1));
    }
    
    /**
     * Tests the checkConfict method from the Conflict interface for two Activities that do conflict on a single day.
     */
    @Test
    public void testCheckConflictWithConflict() {
        // Tests for conflict between activities that share one meeting day
        Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 150, "MW", 1330, 1445);
        Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 150, "M", 1330, 1445);
        
        Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
        assertEquals("Schedule conflict.", e1.getMessage());
        
        Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
        assertEquals("Schedule conflict.", e2.getMessage());
    }
    
    /**
     * Tests the checkConflict method for two Activities that have a single minute of overlap.
     */
    @Test
    public void testCheckConflictSingleMinute() {
     // Tests for conflict between activities where endTime of one is the startTime of another
        Activity a3 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 150, "TH", 1330, 1445);
        Activity a4 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 150, "TH", 1445, 1600);
        
        Exception e3 = assertThrows(ConflictException.class, () -> a3.checkConflict(a4));
        assertEquals("Schedule conflict.", e3.getMessage());
        
        Exception e4 = assertThrows(ConflictException.class, () -> a4.checkConflict(a3));
        assertEquals("Schedule conflict.", e4.getMessage());
    }

}
