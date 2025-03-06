package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Manages the registration process for students and the registrar.
 * This singleton class is responsible for handling user logins,
 * storing user information, and providing access to the CourseCatalog
 * and StudentDirectory.
 */
public class RegistrationManager {

    /** Singleton instance of RegistrationManager */
    private static RegistrationManager instance;
    /** The course catalog for managing courses */
    private CourseCatalog courseCatalog;
    /** The directory of students */
    private StudentDirectory studentDirectory;
    /** The directory of faculty */
    private FacultyDirectory facultyDirectory;
    /** The registrar user */
    private User registrar;
    /** The currently logged-in user */
    private User currentUser;
    /** Hashing algorithm for password hashing */
    private static final String HASH_ALGORITHM = "SHA-256";
    /** Property file name for registrar information */
    private static final String PROP_FILE = "registrar.properties";

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the registrar and sets up the CourseCatalog and StudentDirectory.
     */
    private RegistrationManager() {
        createRegistrar();
        // Initialize CourseCatalog, StudentDirectory, and FacultyDirectory to remove SpotBugs notifications
        courseCatalog = new CourseCatalog(); 
        studentDirectory = new StudentDirectory();
        facultyDirectory = new FacultyDirectory();
    }

    /**
     * Creates the registrar user by loading properties from the registrar properties file.
     * 
     * @throws IllegalArgumentException if the registrar cannot be created due to an I/O error
     */
    private void createRegistrar() {
        Properties prop = new Properties();

        try (InputStream input = new FileInputStream(PROP_FILE)) {
            prop.load(input);
            String hashPW = hashPW(prop.getProperty("pw"));

            registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), 
                                      prop.getProperty("id"), prop.getProperty("email"), 
                                      hashPW);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot create registrar.");
        }
    }

    /**
     * Hashes a password according to a provided hash algorithm. This enables the password to be stored
     * while protecting the user's information.
     * @param pw the password to be put throught the hashing algorithm
     * @return the hashed form of the input password
     * @throws IllegalArgumentException if there is an error while hashing the password
     */
    private String hashPW(String pw) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(pw.getBytes());
            return Base64.getEncoder().encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Cannot hash password");
        }
    }

    /**
     * Returns the singleton instance of the RegistrationManager.
     *
     * @return the instance of RegistrationManager
     */
    synchronized public static RegistrationManager getInstance() {
        if (instance == null) {
            instance = new RegistrationManager();
        }
        return instance;
    }

    /**
     * Returns the CourseCatalog associated with the RegistrationManager.
     *
     * @return the CourseCatalog
     */
    public CourseCatalog getCourseCatalog() {
        return courseCatalog;
    }

    /**
     * Returns the StudentDirectory associated with the RegistrationManager.
     *
     * @return the StudentDirectory
     */
    public StudentDirectory getStudentDirectory() {
        return studentDirectory;
    }
    
    /**
     * Returns the FacultyDirectory associated with the RegistrationManager.
     *
     * @return the FacultyDirectory
     */
    public FacultyDirectory getFacultyDirectory() {
        return facultyDirectory;
    }

    /**
     * Logs in a user with the specified ID and password. If the id doesn't match a user in the system, an
     * IllegalArgumentException is thrown.
     * 
     * @param id the ID of the user (student or registrar)
     * @param password the password of the user
     * @return true if the login is successful; false otherwise
     * @throws IllegalArgumentException if the id is invalid and doesn't match a user in the system
     */
    public boolean login(String id, String password) {
        if (currentUser != null) { // Check if a user is already logged in
            return false; // Prevent multiple logins
        }
        Student student = studentDirectory.getStudentById(id);
        Faculty faculty = facultyDirectory.getFacultyById(id);
        String localHashPW = hashPW(password); // Hash the provided password
        
        // Check if student exists and passwords match
        if (student != null) {
        	if (student.getPassword().equals(localHashPW)) {
	            currentUser = student;
	            return true;
        	}
        	
        } else if (faculty != null && student == null) {
        	if (faculty.getPassword().equals(localHashPW)) {
        		currentUser = faculty;
        		return true;
        	}
        } else {
        
        	// NOTE: THE FOLLOWING IS AN EDIT BY KERGAN SANDERSON (kasande4) TO GET THE PROPER
            //       ERROR MESSAGE WHEN A USER DOESN'T EXIST DURING LOGIN.
        	if (!registrar.getId().equals(id)) {
            	throw new IllegalArgumentException("User doesn't exist.");
        	}
        }

        // Check registrar credentials
        if (registrar.getId().equals(id) && registrar.getPassword().equals(localHashPW)) {
            currentUser = registrar;
            return true;
        }

        return false; // Login failed
    }

    /**
     * Logs out the currently logged-in user by setting the current user to null.
     */
    public void logout() {
        currentUser = null; // Clear current user on logout
    }

    /**
     * Returns the currently logged-in user.
     *
     * @return the currentUser, or null if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Clears the data in the CourseCatalog and StudentDirectory.
     * This method resets both data structures to their initial empty states.
     */
    public void clearData() {
        courseCatalog.newCourseCatalog();
        studentDirectory.newStudentDirectory();
        facultyDirectory.newFacultyDirectory();  }
    
    // THE FOLLOWING THREE METHODS ARE TEACHING STAFF IMPLEMENTATIONS
    
    /**
     * Returns true if the logged in student can enroll in the given course.
     * @param c Course to enroll in
     * @throws IllegalArgumentException if the current user is a Student
     * @return true if enrolled
     */
    public boolean enrollStudentInCourse(Course c) {
        if (!(currentUser instanceof Student)) {
            throw new IllegalArgumentException("Illegal Action");
        }
        try {
            Student s = (Student)currentUser;
            Schedule schedule = s.getSchedule();
            CourseRoll roll = c.getCourseRoll();
            
            if (s.canAdd(c) && roll.canEnroll(s)) {
                schedule.addCourseToSchedule(c);
                roll.enroll(s);
                return true;
            }
            
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }

    /**
     * Returns true if the logged in student can drop the given course.
     * @param c Course to drop
     * @throws IllegalArgumentException if the currentUser is a Student
     * @return true if dropped
     */
    public boolean dropStudentFromCourse(Course c) {
        if (!(currentUser instanceof Student)) {
            throw new IllegalArgumentException("Illegal Action");
        }
        try {
            Student s = (Student)currentUser;
            c.getCourseRoll().drop(s);
            return s.getSchedule().removeCourseFromSchedule(c);
        } catch (IllegalArgumentException e) {
            return false; 
        }
    }

    /**
     * Resets the logged in student's schedule by dropping them
     * from every course and then resetting the schedule.
     * @throws IllegalArgumentException if the currentUser is a Student
     */
    public void resetSchedule() {
        if (!(currentUser instanceof Student)) {
            throw new IllegalArgumentException("Illegal Action");
        }
        try {
            Student s = (Student)currentUser;
            Schedule schedule = s.getSchedule();
            String [][] scheduleArray = schedule.getScheduledCourses();
            for (int i = 0; i < scheduleArray.length; i++) {
                Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
                c.getCourseRoll().drop(s);
            }
            schedule.resetSchedule();
        } catch (IllegalArgumentException e) {
            //do nothing 
        }
    }

    /**
     * Represents the registrar user in the system.
     */
    private static class Registrar extends User {
        /**
         * Creates a registrar user with the specified information.
         *
         * @param firstName the first name of the registrar
         * @param lastName the last name of the registrar
         * @param id the ID of the registrar
         * @param email the email of the registrar
         * @param hashPW the hashed password of the registrar
         */
        public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
            super(firstName, lastName, id, email, hashPW);
        }
    }
    
    /**
     * Adds the course to the given faculty's schedule
     * @param course Course to be added
     * @param faculty The faculty where the course is being added
     */
    public void addFacultyToCourse(Course course, Faculty faculty) {
    	if (currentUser != null && currentUser == registrar) {
    		FacultySchedule schedule = faculty.getSchedule();
    		schedule.addCourseToSchedule(course);
    	}
    }
    /**
     * Removes the course from the given faculty's schedule
     * @param course The course to be removed
     * @param faculty The faculty where the course will be removed
     */
    public void removeFacultyFromCourse(Course course, Faculty faculty) {
    	if (currentUser != null && currentUser == registrar) {
    		FacultySchedule schedule = faculty.getSchedule();
    		schedule.removeCourseFromSchedule(course);
    	}
    }
    /**
     * Resets the faculty's schedule
     * @param faculty The faculty to be reset
     */
    public void resetFacultySchedule(Faculty faculty) {
    	if (currentUser != null && currentUser == registrar) {
    		FacultySchedule schedule = faculty.getSchedule();
    		schedule.resetSchedule();
    	}
    }
}