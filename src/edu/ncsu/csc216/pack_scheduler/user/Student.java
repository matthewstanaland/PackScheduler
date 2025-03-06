package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * A student in the PackScheduler system. Each Student has a
 * first and last name, student id, email, password, and maxCredits.
 * 
 * @author Dreese Abdelilah
 * @author Ruairi Gallagher
 * @author Kergan Sanderson
 */
public class Student extends User implements Comparable<Student> {

	/** The maximum number of credits that any student can take. */
	public static final int MAX_CREDITS = 18;

	/** The maximum number of credits the student can take. */
	private int maxCredits;
	
	/** The Schedule of Courses for the Student. */
	private Schedule schedule;

	/**
	 * Creates a Student using their first name, last name, id, email, password, and
	 * max credits.
	 * 
	 * @param firstName  the student's first name
	 * @param lastName   the student's last name
	 * @param id         the student's permanent id
	 * @param email      the student's email
	 * @param hashPW     the student's hashed password
	 * @param maxCredits the max number of credits that the student can take
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		this.setMaxCredits(maxCredits);
		this.schedule = new Schedule(); // Construct an empty schedule upon Student creation

	}

	/**
	 * Creates a Student using their first name, last name, id, email, password, and
	 * the default number of maximum credits.
	 * 
	 * @param firstName the student's first name
	 * @param lastName  the student's last name
	 * @param id        the student's permanent id
	 * @param email     the student's email
	 * @param hashPW    the student's hashed password
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, MAX_CREDITS);
	}

	/**
	 * Returns the max number of credits that the student can take.
	 * 
	 * @return the student's max number of credits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the max number of credits that the student can take. If maxCredits is 
	 * below three or above the maximum number of credits allowed, then an 
	 * IllegalArgumentException is thrown.
	 * 
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException if maxCredits is invalid
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		if (maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Returns the Schedule of Courses that the Student is enrolled in.
	 * @return the Student's Schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}
	
	/**
	 * Determines if a course can be added to a Student's schedule. If the course to add is null,
	 * a duplicate of a course already in a student's schedule, the course has a time conflict with another
	 * class in the students schedule, or the course would exceed the student's maximum credits, then the
	 * course cannot be added.
	 * @param other the course that will be analyzed to determine whether or not it can be added
	 * @return true if the course can be added to the student's schedule and false otherwise
	 */
	public boolean canAdd(Course other) {
		return schedule.canAdd(other) && other.getCredits() + schedule.getScheduleCredits() <= maxCredits;
	}

	/**
	 * Creates a string that returns the student information that is separated by
	 * commas
	 * 
	 * @return studentInfo
	 */
	@Override
	public String toString() {
		String theFirst = this.getFirstName();
		String theLast = this.getLastName();
		String theId = this.getId();
		String theEmail = this.getEmail();
		String thePassword = this.getPassword();
		int theMaxCredits = this.getMaxCredits();
		String studentInfo = theFirst + "," + theLast + "," + theId + "," + theEmail + ',' + thePassword + ","
				+ theMaxCredits;

		return studentInfo;
	}

	/**
	 * Compare To method returns a negative integer,zero, or a positive integer
	 * returning if the Student is less than, equal to, or greater than Student s.
	 * This sorts the Students based on last name, first name, and Unity id.
	 * 
	 * @param s Student s
	 * @return integer -Integer ,zero or +Integer
	 * @throws NullPointerException if object s is null
	 * @throws ClassCastException if s is not an instance of a Student
	 */
	public int compareTo(Student s) {
		if (s == null) {
			throw new NullPointerException("Null Pointer Exception.");
		}
		if (!(s instanceof Student)) {
			throw new ClassCastException("Class Cast Exception.");
		}
		if (this.getLastName().compareTo(s.getLastName()) != 0) {
			return Integer.signum(this.getLastName().compareTo(s.getLastName()));
		}
		if (this.getFirstName().compareTo(s.getFirstName()) != 0) {
			return Integer.signum(this.getFirstName().compareTo(s.getFirstName()));
		}

		return Integer.signum(this.getId().compareTo(s.getId()));

	}
	
	/**
	 * Generates a unique hashcode for the Student using the hashcode method from User in addition
	 * to a value representing maxCredits.
	 * @return a unique hashcode representing the student object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Compares a provided object to this student object and determines if they are equal.
	 * Uses the User equals method for determining equality in addition to comparing the maxCredits variable.
	 * @param obj the object to compare to this student
	 * @return true if the objects are equal and false if they are not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		// Fix PMD notification here by simplifying boolean return
		return !(maxCredits != other.maxCredits);
	}
	
}
