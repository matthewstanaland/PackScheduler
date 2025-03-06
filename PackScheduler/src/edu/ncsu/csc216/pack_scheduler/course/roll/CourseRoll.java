package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.ArrayQueue;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * Represents a course roll, which is a list of students enrolled in a course with 
 * a specific enrollment capacity. The course roll maintains a minimum and maximum 
 * enrollment size.
 */
public class CourseRoll {

	/** The minimum number of students that can be enrolled in the course. */
    private static final int MIN_ENROLLMENT = 10;

    /** The maximum number of students that can be enrolled in the course. */
    private static final int MAX_ENROLLMENT = 250;

    /**Wait list size*/
    private static final int WAITLIST_SIZE = 10;
    
    /** The list of students enrolled in the course, implemented as a custom LinkedAbstractList. */
    private LinkedAbstractList<Student> roll;
    /** wait list using array queue of students*/
    private ArrayQueue<Student> waitlist;

    /** The enrollment cap for the course. */
    private int enrollmentCap;
    


    /**
     * Constructs a CourseRoll with the given enrollment capacity.
     * @param c course c is passed through
     * @param enrollmentCap the maximum number of students that can enroll
     * @throws IllegalArgumentException if c is null
     */
    public CourseRoll(Course c, int enrollmentCap) {
    	if(c == null) {
    		throw new IllegalArgumentException();
    	}
    	waitlist = new ArrayQueue<Student>(WAITLIST_SIZE);
        roll = new LinkedAbstractList<>(enrollmentCap);
        setEnrollmentCap(enrollmentCap);
    }

    /**
     * Sets the enrollment capacity for the course roll.
     * 
     * @param enrollmentCap the new enrollment capacity
     * @throws IllegalArgumentException if enrollmentCap is less than MIN_ENROLLMENT,
     *                                  greater than MAX_ENROLLMENT, or less than the
     *                                  current roll size
     */
    public void setEnrollmentCap(int enrollmentCap) {
        if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
            throw new IllegalArgumentException("Invalid enrollment cap.");
        }
        if (enrollmentCap < roll.size()) {
            throw new IllegalArgumentException("Enrollment cap cannot be less than the number of enrolled students.");
        }
        this.enrollmentCap = enrollmentCap;
        roll.setCapacity(enrollmentCap);
    }

    /**
     * Gets the current number of open seats.
     * 
     * @return the difference between enrollmentCap and roll size
     */
    public int getOpenSeats() {
        return enrollmentCap - roll.size();
    }

    /**
     * Gets the enrollment capacity.
     * 
     * @return the enrollment capacity
     */
    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    /**
     * Enrolls a student in the course.
     * 
     * @param s the student to enroll
     * @throws IllegalArgumentException if student is null, no room in class,
     *                                  student is already enrolled, or if an
     *                                  exception is thrown by LinkedAbstractList
     */
    public void enroll(Student s) {
        if (s == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        if (roll.contains(s)) {
            throw new IllegalArgumentException("Cannot enroll student.");
        }
        if(roll.size() == enrollmentCap) {
        	if(waitlist.size() < WAITLIST_SIZE) {
        		waitlist.enqueue(s);
        	} else {
        		throw new IllegalArgumentException();
        	}
        } else {
        roll.add(roll.size(), s);
        }
    }

    /**
     * Drops a student from the course.
     * 
     * @param s the student to drop
     * @throws IllegalArgumentException if student is null or if an exception is
     *                                  thrown by LinkedAbstractList
     */
    public void drop(Student s) {
        if (s == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        
        for (int i = 0; i < roll.size(); i++) {
            if (roll.get(i).equals(s)) {
                roll.remove(i);
                
               if(!waitlist.isEmpty()) {
            	   roll.add(waitlist.dequeue());
               }
                return;
            }
        }
     
        for(int i = 0; i < waitlist.size(); i++) {
        	Student student = waitlist.dequeue();
        	if(!student.equals(s)) {
        		waitlist.enqueue(student);
        	}
        }
      
    }
    /**
     * method gets the number of students of the wait list
     * @return waitlist.size which is the number of students on the list
     */
    public int getNumberOnWaitlist() {
    	return waitlist.size();
    }
    /**
     * Checks if a student can enroll in the course.
     * 
     * @param s the student to check
     * @return true if student can enroll, false otherwise
     */
    public boolean canEnroll(Student s) {
        if (s == null || roll.size() >= enrollmentCap) {
            return false;
        }
      if(waitlist.contains(s)) {
    	  return false;
      }
        
        for (int i = 0; i < roll.size(); i++) {
            if (roll.get(i).equals(s)) {
                return false;
            }
        }
        return true;
    }
    
   
}

