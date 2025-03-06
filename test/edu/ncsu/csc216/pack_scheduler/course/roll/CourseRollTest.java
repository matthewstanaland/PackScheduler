package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Unit tests for the CourseRoll class, which represents the list of students 
 * enrolled in a course with a specified enrollment capacity.
 */
public class CourseRollTest {
	/** created Course which will be delegated to work with the courses course roll*/
	Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
	/** A CourseRoll instance for testing, initialized with a specific enrollment capacity */
    private CourseRoll roll = c.getCourseRoll();
    /** Student instance for testing enrollment functionality */
    private Student s1;
    /** Additional student instance for testing enrollment functionality */
    private Student s2;
    /** Additional student instance for testing enrollment functionality */
    private Student s3;
    /** Additional student instance for testing enrollment functionality */
    private Student s4;
    /** Additional student instance for testing enrollment functionality */
    private Student s5;
    /** Additional student instance for testing enrollment functionality */
    private Student s6;
    /** Additional student instance for testing enrollment functionality */
    private Student s7;
    /** Additional student instance for testing enrollment functionality */
    private Student s8;
    /** Additional student instance for testing enrollment functionality */
    private Student s9;
    /** Additional student instance for testing enrollment functionality */
    private Student s10;
    /** Additional student instance for testing enrollment functionality */
    private Student s11;
    /** Additional student instance for testing enrollment functionality */
    private Student s12;
    /** Additional student instance for testing enrollment functionality */
    private Student s13;

    /**
     * Sets up the test environment before each test method. Initializes a 
     * CourseRoll instance with a specific enrollment capacity and creates two 
     * Student objects for testing.
     */
    @BeforeEach
    public void setUp() {
        roll = new CourseRoll(c, 10); // initial capacity
        s1 = new Student("John", "Doe", "jdoe", "jdoe@ncsu.edu", "pw");
        s2 = new Student("Jane", "Smith", "jsmith", "jsmith@ncsu.edu", "pw");
        s3 = new Student("hane", "Smith", "hsmith", "hsmith@ncsu.edu", "pw");
        s4 = new Student("bane", "Smith", "bsmith", "bsmith@ncsu.edu", "pw");
        s5 = new Student("sane", "Smith", "ssmith", "ssmith@ncsu.edu", "pw");
        s6 = new Student("eane", "Smith", "esmith", "esmith@ncsu.edu", "pw");
        s7 = new Student("vane", "Smith", "vsmith", "vsmith@ncsu.edu", "pw");
        s8 = new Student("yane", "Smith", "ysmith", "ysmith@ncsu.edu", "pw");
        s9 = new Student("zane", "Smith", "zsmith", "zsmith@ncsu.edu", "pw");
        s10 = new Student("oane", "Smith", "osmith", "osmith@ncsu.edu", "pw");
        s11 = new Student("pane", "Smith", "psmith", "psmith@ncsu.edu", "pw");
        s12 = new Student("lane", "Smith", "lsmith", "lsmith@ncsu.edu", "pw");
        s13 = new Student("kane", "Smith", "ksmith", "ksmith@ncsu.edu", "pw");
    }

    /**
     * Tests the CourseRoll constructor and verifies that the enrollment capacity 
     * and open seats are set as expected.
     */
    @Test
    public void testConstructor() {
        assertEquals(10, roll.getEnrollmentCap());
        assertEquals(10, roll.getOpenSeats());
    }

    /**
     * Tests setting the enrollment capacity with valid and invalid values.
     * Verifies that the enrollment cap can be set within allowed limits and 
     * throws exceptions when attempting to set it below the minimum or above 
     * the maximum.
     */
    @Test
    public void testSetEnrollmentCap() {
        roll.setEnrollmentCap(15);
        assertEquals(15, roll.getEnrollmentCap());

        assertThrows(IllegalArgumentException.class, () -> roll.setEnrollmentCap(5));
        assertThrows(IllegalArgumentException.class, () -> roll.setEnrollmentCap(251));
    }

    /**
     * Tests the enroll method, ensuring that students are added correctly, 
     * available seats are reduced, and duplicate enrollments are not allowed.
     */
    @Test
    public void testEnroll() {
        roll.enroll(s1);
        assertEquals(9, roll.getOpenSeats());
        roll.enroll(s2);
        assertEquals(8, roll.getOpenSeats());

        assertThrows(IllegalArgumentException.class, () -> roll.enroll(s1)); // duplicate enroll
        
        roll.enroll(s3);
        roll.enroll(s4);
        roll.enroll(s5);
        roll.enroll(s6);
        roll.enroll(s7);
        roll.enroll(s8);
        roll.enroll(s9);
        roll.enroll(s10);
        
        roll.enroll(s11);
        
        assertEquals(roll.getNumberOnWaitlist(), 1);
        
    }

    /**
     * Tests the drop method, verifying that students can be removed from the 
     * course and that the open seats count is updated correctly.
     */
    @Test
    public void testGetWaitlist() {
        roll.enroll(s1);
        roll.enroll(s2);
        roll.enroll(s3);
        roll.enroll(s4);
        roll.enroll(s5);
        roll.enroll(s6);
        roll.enroll(s7);
        roll.enroll(s8);
        roll.enroll(s9);
        roll.enroll(s10);
        
        assertEquals(roll.getNumberOnWaitlist(), 0);
        roll.enroll(s11);
        roll.enroll(s12);
        
        assertEquals(roll.getNumberOnWaitlist(), 2);
        
    }
    
    

    /**
     * Tests the canEnroll method to ensure it returns true when a student can 
     * be enrolled and false when a student is already enrolled.
     */
    @Test
    public void testCanEnroll() {
        assertTrue(roll.canEnroll(s1));
        roll.enroll(s1);
        assertFalse(roll.canEnroll(s1)); // already enrolled
    }
    /**
     * Tests dropping a student from the roll when students are on the waitlist.
     * Ensures that a student from the waitlist is moved to the roll when a 
     * seat opens up.
     */
    @Test
    public void testDropRoll() {
       try {
    	   roll.drop(s1);
       } catch(IllegalArgumentException e) {
    	   assertEquals(roll.getOpenSeats(), roll.getEnrollmentCap());
       }
       roll.enroll(s1);
       roll.enroll(s2);
       roll.enroll(s3);
       roll.enroll(s4);
       roll.enroll(s5);
       roll.enroll(s6);
       roll.enroll(s7);
       roll.enroll(s8);
       roll.enroll(s9);
       roll.enroll(s10);
       roll.enroll(s11);
       roll.enroll(s12);
       
       roll.drop(s1);
       assertEquals(0, roll.getOpenSeats());
       roll.drop(s10);
       assertEquals(0, roll.getOpenSeats());
       roll.drop(s5);
       assertEquals(1, roll.getOpenSeats());
       
       try {
    	   roll.drop(s13);
       } catch (IllegalArgumentException e) {
    	   assertEquals(1, roll.getOpenSeats());
       }
    }
    

    /**
     * Tests the setEnrollmentCap method when the cap is less than the current 
     * number of enrolled students.
     */
    @Test
    public void testSetEnrollmentCapBelowCurrentSize() {
        roll.setEnrollmentCap(15);
        
        // Enroll 15 students
        for (int i = 0; i < 15; i++) {
            Student student = new Student("Student" + i, "LastName" + i, "student" + i, "student" + i + "@ncsu.edu", "pw");
            roll.enroll(student);
            
        }
        
        // Attempt to reduce cap below current number of students
        assertThrows(IllegalArgumentException.class, () -> roll.setEnrollmentCap(14));
    }

    
}
