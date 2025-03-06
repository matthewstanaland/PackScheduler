/**
 * A package for the WolfScheduler project.
 */
package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * An educational course in the WolfScheduler system. A Course keeps track of its
 * name, title, section, credit hours, instructor's unity id, meeting days, start time,
 * and end time.
 * @author Kergan Sanderson
 */
public class Course extends Activity implements Comparable<Course> {
    
    /** Length of the Course section. */
    private static final int SECTION_LENGTH = 3;
    
    /** Maximum number of credits a course can be worth. */
    private static final int MAX_CREDITS = 5;
    
    /** Minimum number of credits a course can be worth. */
    private static final int MIN_CREDITS = 1;
    
    /** Course's name. */
    private String name;
    
    /** Course's section. */
    private String section;
    
    /** Course's credit hours */
    private int credits;
    
    /** Course's instructor */
    private String instructorId;
    
    /** Course's roll */
    private CourseRoll roll;
    
    /**
     * Constructs a Course object with values for all fields.
     * @param name name of Course
     * @param title title of Course
     * @param section section of Course
     * @param credits credit hours for Course
     * @param instructorId instructor's unity id
     * @param enrollmentCap enrollment cap for the course
     * @param meetingDays meeting days for Course as series of chars
     * @param startTime start time for Course
     * @param endTime end time for Course
     */
    public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
            int startTime, int endTime) {
        super(title, meetingDays, startTime, endTime);
        setName(name);
        setSection(section);
        setCredits(credits);
        setInstructorId(instructorId);
        roll = new CourseRoll(this, enrollmentCap);
    }

    /**
     * Creates a Course with the given name, title, section, credits, instructorId, and meetingDays for
     * courses that are arranged.
     * @param name name of Course
     * @param title title of Course
     * @param section section of Course
     * @param credits credit hours for Course
     * @param instructorId instructor's unity id
     * @param enrollmentCap enrollment cap for the course
     * @param meetingDays meeting days for Course as series of chars
     */
    public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
        this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
    }

    /**
     * Returns the Course's name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Course's name. If the name is null, has a length less than 5 or more than 8,
     * does not contain a space between letter characters and number characters, has less than 1
     * or more than 4 letter characters, and not exactly three trailing digit characters, an
     * IllegalArgumentException is thrown.
     * @param name the name to set
     * @throws IllegalArgumentException if the name parameter is invalid
     */
    private void setName(String name) {
        // Ensure that name is not null
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid course name.");
        }
        
        // Create an instance of the CourseNameValidator
        CourseNameValidator validator = new CourseNameValidator();
        
        // Use the validator to check if the course name is valid
        try {
            if (validator.isValid(name)) {
                // If valid, assign the name
                this.name = name;
            } else {
                // If not in a valid end state, throw an exception
                throw new IllegalArgumentException("Invalid course name.");
            }
        } catch (InvalidTransitionException e) {
            // If the validator throws an InvalidTransitionException, convert it into IllegalArgumentException
        	// CHANGE: use "Invalid course name." as message instead of e.getMessage() to pass Jenkins tests
            throw new IllegalArgumentException("Invalid course name.");
        }
    }


    /**
     * Returns the Course's section.
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * Sets the Course's section. If section is null, not three characters, or any character is
     * not a digit, an IllegalArgumentException is thrown.
     * @param section the section to set
     * @throws IllegalArgumentException if the title parameter is invalid
     */
    public void setSection(String section) {
        if (section == null) {
            throw new IllegalArgumentException("Invalid section.");
        }
        if (section.length() != SECTION_LENGTH) {
            throw new IllegalArgumentException("Invalid section.");
        }
        for (int i = 0; i < SECTION_LENGTH; i++) {
            if (!Character.isDigit(section.charAt(i))) {
                throw new IllegalArgumentException("Invalid section.");
            }
        }
        this.section = section;
    }

    /**
     * Returns the Course's credit hours.
     * @return the credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets the Course's credit hours. If credits is lower than MIN_CREDITS or higher than
     * MAX_CREDITS, an IllegalArgumentException is thrown.
     * @param credits the credits to set
     * @throws IllegalArgumentException if credits parameter is out of bounds
     */
    public void setCredits(int credits) {
        if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
            throw new IllegalArgumentException("Invalid credits.");
        }
        this.credits = credits;
    }

    /**
     * Returns the Course's instructor's unity id.
     * @return the instructorId
     */
    public String getInstructorId() {
        return instructorId;
    }

    /**
     * Sets the Course's instructor's unity id. If instructorId is null or an empty string,
     * an IllegalArgumentException is thrown.
     * @param instructorId the instructorId to set
     * @throws IllegalArgumentException if instructorId parameter is invalid
     */
    public void setInstructorId(String instructorId) {
        if ("".equals(instructorId)) {
            throw new IllegalArgumentException("Invalid instructor id.");
        }
        this.instructorId = instructorId;
    }
    /**
     * Returns the value for the course's roll
     * @return roll
     */
    public CourseRoll getCourseRoll() {
    	return roll;
    }
    
    /**
     * Sets up the course's enrollment cap and course roll
     * @param enrollmentCap the maximum number of students that can be enrolled in the course
     * @throws IllegalArgumentException if the enrollmentCap is below 10 or above 250
     */
    public void setCourseRoll(int enrollmentCap) {
    	if (enrollmentCap < 10 || enrollmentCap > 250) {
    		throw new IllegalArgumentException("Invalid enrollment cap.");
    	}
    	this.roll = new CourseRoll(this, enrollmentCap);
    }
    
    /**
     * Generates a hashCode for Course using all the fields.
     * @return hashCode for Course
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + credits;
        result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((section == null) ? 0 : section.hashCode());
        return result;
    }
    
    /**
     * Compares a given Course for equality to this Course on all fields.
     * @param obj the Object to compare
     * @return true if the Courses are the same on all fields
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Course other = (Course) obj;
        if (credits != other.credits)
            return false;
        if (instructorId == null) {
            if (other.instructorId != null)
                return false;
        } else if (!instructorId.equals(other.instructorId))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (section == null) {
            if (other.section != null)
                return false;
        } else if (!section.equals(other.section))
            return false;
        return true;
    }

    /**
     * Returns a comma separated value String of all Course fields.
     * @return String representation of Course
     */
    @Override
    public String toString() {
        if ("A".equals(getMeetingDays())) {
            return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays();
        }
        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime(); 
    }

    /**
     * Returns the Course name, section, title, and meeting string in an array.
     * @return an array with 4 of the 7 course details.
     */
    @Override
    public String[] getShortDisplayArray() {
        return new String[] {name, section, getTitle(), getMeetingString(), String.valueOf(roll.getOpenSeats())};
    }

    /**
     * Returns the Course name, section, title, credits, instructorId, meeting string, and an empty string in an array.
     * @return an array with all of the course details.
     */
    @Override
    public String[] getLongDisplayArray() {
        return new String[] {name, section, getTitle(), getCredits() + "", instructorId, getMeetingString(), ""};
    }
    
    /**
     * Determines if the given activity is a duplicate of this Course. Courses are duplicates if they have the same name.
     * @param activity the activity to compare to this course
     * @return true if the courses are identical
     */
    @Override
    public boolean isDuplicate(Activity activity) {
        if (activity instanceof Course) {
            Course course = (Course) activity;
            return this.name.equals(course.getName());
        }
        return false;
    }
    

    /**
     * Sets the Course's meeting days, start time, and end time. If the meeting days consist
     * of characters other than 'A', 'M', 'T', 'W', 'H', or 'F', have a duplicate character, 
     * or have other characters when 'A' is in the list, then an IllegalArgumentException is thrown.
     * If the start time is an invalid military time, the end time is an invalid military time, the
     * end time is less than the start time, or a start time or end time is listed when the meeting 
     * days is 'A', then an IllegalArgumentException is thrown.
     * @param meetingDays the days of the week that the course will meet
     * @param startTime the time that the course will start on meeting days
     * @param endTime the time that the course will end on meeting days
     * @throws IllegalArgumentException if meetingDays parameter is invalid
     * @throws IllegalArgumentException if startTime parameter is invalid
     * @throws IllegalArgumentException if endTime parameter is invalid
     */
    @Override
    public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
        if (meetingDays == null || "".equals(meetingDays)) {
            throw new IllegalArgumentException("Invalid meeting days and times.");
        }
        
        // check if meeting days are valid days
        if ("A".equals(meetingDays)) { // arranged meeting days
            if (startTime != 0 || endTime != 0) {
                throw new IllegalArgumentException("Invalid meeting days and times.");
            } else {
                super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
            }
        } else { // not arranged
            int[] dayCounts = new int[5];
            char[] validDays = {'M', 'T', 'W', 'H', 'F'};
            char tempChar;
            for (int i = 0; i < meetingDays.length(); i++) {
                tempChar = meetingDays.charAt(i);
                for (int j = 0; j < validDays.length; j++) {
                    if (tempChar == validDays[j]) {
                        dayCounts[j]++;
                        break; // move on to the next tempChar
                    } else if (j == validDays.length - 1) { // tempChar is not a valid meeting day
                        throw new IllegalArgumentException("Invalid meeting days and times.");
                    }
                }
            }
            for (int count : dayCounts) {
                if (count > 1) { // a meeting day is repeated
                    throw new IllegalArgumentException("Invalid meeting days and times.");
                }
            }
        }
        
        super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }

    /**
     * Compares an object to this Course. If the object is a Course, the name and section of the Courses are 
     * compared. Courses are sorted by name and then section. If o is null, a NullPointerException is thrown.
     * If o is not a Course, a ClassCastException is thrown.
     * @param o the object to compare to this Course
     * @return an integer -1, 0, or 1 representing if the given object is less than, equal to, or greater than this Course.
     * @throws NullPointerException if o is null
     * @throws ClassCastException if o is not an instance of a Course
     */
    @Override
    public int compareTo(Course o) {
        // check for null object
        if (o == null) {
            throw new NullPointerException();
        }
        
        // check for instance of Course
        if (!(o instanceof Course)) {
            throw new ClassCastException();
        }
        
        // Compare the two Course objects
        int nameVal = o.getName().compareTo(this.name);
     	int sectionVal = o.getSection().compareTo(this.section);
    	
        if (nameVal != 0) {
        	if(nameVal < 0) {
        		nameVal = 1;
        	} else if(nameVal > 0) {
        		nameVal = -1;
        	}
            return nameVal;
        } else {
        	if(sectionVal < 0) {
        		sectionVal = 1;
        	} else if(sectionVal > 0) {
        		sectionVal = -1;
        	}
        	return sectionVal;
        }
    }
}
