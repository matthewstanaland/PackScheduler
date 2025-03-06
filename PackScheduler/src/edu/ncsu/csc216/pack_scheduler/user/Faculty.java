/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 *  Faculty
 */
public class Faculty extends User {
	/** The maximum number of courses that any teacher can teach. */
	public static final int MAX_COURSES = 3;

	/** the minimum number of courses that a teacher has to teach. */
	public static final int MIN_COURSES = 1;
	
	/** The maximum number of credits the student can take. */
	private int maxCourses;
	
	private FacultySchedule facultySchedule;
	
	

	/**
	 * Creates a Teacher using their first name, last name, id, email, password, and
	 * max Courses.
	 * 
	 * @param firstName  the faculty first name
	 * @param lastName   the faculty last name
	 * @param id         the faculty permanent id
	 * @param email      the faculty email
	 * @param hashPW     the faculty hashed password
	 * @param maxCourses the max number of Courses that the teacher can teach
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW, int maxCourses) {
		super(firstName, lastName, id, email, hashPW);
		this.setMaxCourses(maxCourses);
		facultySchedule = new FacultySchedule(id);

	}

	/**
	 * Creates a Teacher using their first name, last name, id, email, password, and
	 * the default number of maximum credits.
	 * 
	 * @param firstName the faculty first name
	 * @param lastName  the faculty last name
	 * @param id        the faculty permanent id
	 * @param email     the faculty email
	 * @param hashPW    the faculty hashed password
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, MAX_COURSES);
	}

	/**
	 * Faculty get schedule method gets the schedule
	 * @return facultySchedule the faculty schedule
	 */
	public FacultySchedule getSchedule() {
		return facultySchedule;
	}
	
	/**
	 * if the number of scheduled courses is greater than max courses then return true else false
	 * @return true or false 
	 */
	public boolean isOverloaded() {
		if(facultySchedule.getNumScheduledCourses() > maxCourses) {
			return true;
		}
		
		return false;
	}
	/**
	 * Returns the max number of Courses that the Teacher can take.
	 * 
	 * @return the max number of courses
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Sets the max number of credits that the Teacher can take. If maxCourses is 
	 * below 1 or above the maximum number of credits allowed, then an 
	 * IllegalArgumentException is thrown.
	 * 
	 * @param maxCourses the maxCourses to set
	 * @throws IllegalArgumentException if maxCredits is invalid
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		if (maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}

	/**
	 * Creates a string that returns the Faculty information that is separated by
	 * commas
	 * 
	 * @return FacultyInfo
	 */
	@Override
	public String toString() {
		String theFirst = this.getFirstName();
		String theLast = this.getLastName();
		String theId = this.getId();
		String theEmail = this.getEmail();
		String thePassword = this.getPassword();
		int theMaxCourses = this.getMaxCourses();
		String staffInfo = theFirst + "," + theLast + "," + theId + "," + theEmail + ',' + thePassword + ","
				+ theMaxCourses;

		return staffInfo;
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
		result = prime * result + maxCourses;
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
		Faculty other = (Faculty) obj;
		// Fix PMD notification here by simplifying boolean return
		return !(maxCourses != other.maxCourses);
}
}