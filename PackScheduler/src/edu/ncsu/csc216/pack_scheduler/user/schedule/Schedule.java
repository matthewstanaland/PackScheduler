package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;
import edu.ncsu.csc216.pack_scheduler.course.ConflictException;

/**
 * A user's schedule of Courses in the Pack Scheduler system.
 * 
 * @author Kergan Sanderson
 * @author Artie Yakovlev
 * @author Cameron Edwards
 */
public class Schedule {

	/** Holds the list of Courses for the user. */
	private ArrayList<Course> schedule;

	/** The title of the user's schedule. */
	private String title;

	/**
	 * Constructs a new Schedule. Sets the default title and creates an empty schedule.
	 */
	public Schedule() {
		schedule = new ArrayList<Course>();
		title = "My Schedule";
	}

	/**
	 * Returns the title of the schedule.
	 * @return the schedule's title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Checks if a course has been successfully added to the schedule. If the course to be added is a duplicate of
	 * a course that is already in the schedule or has a conflict with the schedule, an IllegalArgumentException
	 * is thrown. If course is null, a NullPointerException is thrown.
	 * @param course The called course
	 * @return Returns true if the course is added to the schedule
	 * @throws IllegalArgumentException if course is a duplicate or there is a scheduling conflict
	 * @throws NullPointerException if course is null.
	 */
	public boolean addCourseToSchedule(Course course) {
		for (int i = 0; i < schedule.size(); i++) {
			Course sCourse = schedule.get(i);
			if (sCourse.isDuplicate(course)) {
				throw new IllegalArgumentException("You are already enrolled in " + course.getName());
			}
		}
		for (Course sCourse : schedule) {
			try {
				sCourse.checkConflict(course);
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}
		schedule.add(course);
		return true;
	}

	/**
	 * Removes the specified course from the schedule.
	 * @param course the called course
	 * @return returns true if the course was successfully removed from the schedule and false if the course isn't in the schedule
	 */
	public boolean removeCourseFromSchedule(Course course) {
		boolean inSchedule = false;
		for (Course sCourse : schedule) {
			if (sCourse.isDuplicate(course)) {
				inSchedule = true;
			}
		}
		if (inSchedule) {
			schedule.remove(course);
			return true;
		}
		return false;
	}

	/**
	 * Resets the schedule to a blank version with no courses added. Any courses in the schedule prior to the
	 * reset are lost.
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Course>();
	}

	/**
	 * Returns the name, section, title, meeting information, and enrollment cap for courses in the catalog.
	 * @return a 2D string array of the courses in the the catalogs name, section, title, and meeting information
	 */
	public String[][] getScheduledCourses() {
		String[][] scheduleArray = new String[schedule.size()][5];
		for (int i = 0; i < schedule.size(); i++) {
			Course c = schedule.get(i);
			scheduleArray[i] = c.getShortDisplayArray();
		}
		return scheduleArray;
	}
	
	/**
	 * Sets the title of the schedule to the given title. The title cannot be null, or an IllegalArgumentException
	 * will be thrown.
	 * @param title the new title for the schedule
	 * @throws IllegalArgumentException if title is null
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}
	
	/**
	 * Gets the total number of credits in the schedule. The credits from each course are added together
	 * and returned as a sum.
	 * @return the sum of credits of all the courses in the schedule
	 */
	public int getScheduleCredits() {
		int credits = 0;
		for (Course course : schedule) {
			credits += course.getCredits();
		}
		return credits;
	}
	
	/**
	 * Determines whether or not a course can be added to the schedule. Considers concepts of time conflicts
	 * and duplicates. If the course is null, has a time conflict with a Course already in the schedule, or
	 * is a duplicate of a Course already in the schedule, it cannot be added to the schedule.
	 * @param other the Course that is being analyzed to determine if it can be added to the schedule
	 * @return true if the course can be added without error and false otherwise
	 */
	public boolean canAdd(Course other) {
		if (other == null) {
			return false;
		}
		for (Course course : schedule) {
			if (course.isDuplicate(other)) {
				return false;
			}
			try {
				course.checkConflict(other);
			} catch (ConflictException e) {
				return false;
			}
		}
		return true;
	}

}
