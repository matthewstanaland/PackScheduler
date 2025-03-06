package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;
import edu.ncsu.csc217.collections.list.SortedList;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * CourseCatalog class uses a SortedList of courses that make up 
 * the catalog it can make a new catalog, load courses from a file, 
 * add courses to catalog, remove and get courses from catalog,and get and save the course catalog.
 * @author Dreese Abdelilah
 */
public class CourseCatalog {
    /** The catalog that Courses are stored in. */
    private SortedList<Course> catalog;

    /**
     * Course Catalog constructor that constructs an empty catalog
     */
    public CourseCatalog() {
        catalog = new SortedList<>();
        
    }

    /**
     * newCourseCatalog constructs an empty catalog
     */
    public void newCourseCatalog() {
        catalog = new SortedList<>();
    }

    /**
     * LoadCoursesFromFile loads course records into the catalog and any 
     * file not found exceptions are caught and an IllegalArgumentException is thrown to the client
     * @param fileName fileName with a type String
     * @throws IllegalArgumentException if fileName is invalid
     */
    public void loadCoursesFromFile(String fileName) {
        try {
            this.catalog = CourseRecordIO.readCourseRecords(fileName);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid file name.");
        }
    }

    /**
     * add CourseToCatalog adds course to catalog and returns true.
     * but if it returns false if the course already exists in the catalog.
     * and throws an illegal argument exception if there is an error constructing the course.
     * @param name Course name
     * @param title Course Title
     * @param section Course section
     * @param credits Course credits
     * @param instructorId Course instructor id
     * @param enrollmentCap Enrollment cap for the Course
     * @param meetingDays Course meeting days
     * @param startTime Course start time
     * @param endTime Course end time
     * @return true if adds Course and false if Course already exists
     * @throws IllegalArgumentException if error constructing Course
     */
    public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int enrollmentCap, 
            String meetingDays, int startTime, int endTime) {
        Course course;
        // let any exceptions propagate up to the user
        course = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);

        for (int i = 0; i < catalog.size(); i++) {
            Course c1 = catalog.get(i);

            if (c1.compareTo(course) == 0) {
                return false;
            }
        }
        catalog.add(course);
        return true;
    }

    /**
     * remove course from catalog and will return true and false if course is not in the catalog
     * @param name course name
     * @param section course section
     * @return true if the course is removed and false if the course is not in the catalog
     */
    public boolean removeCourseFromCatalog(String name, String section) {
        for(int i = 0; i < catalog.size(); i++) {
            Course course = catalog.get(i);

            if(course.getName().equals(name) && course.getSection().equals(section)) {
                catalog.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Get course from catalog returns the 
     * course from the catalog with the given name and section.
     * and returns null if the course is not in the catalog
     * 
     * @param name    string name input
     * @param section string section input
     * @return Course or null
     */
    public Course getCourseFromCatalog(String name, String section) {
        for (int i = 0; i < catalog.size(); i++) {
            if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
                return catalog.get(i);
            }
        }
        return null;
    }

    /**
     * getCourseCatalog returns the name, section, title and meeting information for courses in the catalog
     * 
     * @return a 2D string array of the courses in the the catalogs name, section, title, and meeting information
     */
    public String[][] getCourseCatalog() {
        String[][] catalogArray = new String[catalog.size()][5];
        for (int i = 0; i < catalog.size(); i++) {
            Course c = catalog.get(i);
            catalogArray[i] = c.getShortDisplayArray();
        }
        return catalogArray;
    }

    /**
     * saveCourseCatalog saves the catalog course records to the given file and any IOExceptions are caught and an 
     * IllegalArgumentException is thrown to the client
     * 
     * @param fileName String that gets passed through
     * @throws IllegalArgumentException The file cannot be saved
     */
    public void saveCourseCatalog(String fileName) {
        try {
            CourseRecordIO.writeCourseRecords(fileName, catalog);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid file name.");
        }
    }

}
