package edu.ncsu.csc216.pack_scheduler.catalog;



import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Activity;
import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the CourseCatalog class. Tests are based off of the tests used in GP3 for the WolfScheduler class.
 * 
 * @author Kergan Sanderson
 * @author Sarah Heckman
 */
public class CourseCatalogTest {

    /** Valid course records */
    private final String validTestFile = "test-files/course_records.txt";
    /** Invalid course records */
    private final String invalidTestFile = "test-files/invalid_course_records.txt";

    /** Course name */
    private static final String NAME = "CSC216";
    /** Course title */
    private static final String TITLE = "Software Development Fundamentals";
    /** Course section */
    private static final String SECTION = "001";
    /** Course credits */
    private static final int CREDITS = 3;
    /** Course instructor id */
    private static final String INSTRUCTOR_ID = "sesmith5";
    /** Course enrollment cap */
    private static final int CAP = 10;
    /** Course meeting days */
    private static final String MEETING_DAYS = "TH";
    /** Course start time */
    private static final int START_TIME = 1330;
    /** Course end time */
    private static final int END_TIME = 1445;

    /**
     * Resets course_records.txt for use in other tests.
     */
    @Before
    public void setUp() {
        //Reset course_records.txt so that it's fine for other needed tests
        Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
        Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
        try {
            Files.deleteIfExists(destinationPath);
            Files.copy(sourcePath, destinationPath);
        } catch (IOException e) {
            fail("Unable to reset files");
        }
    }

    /**
     * Tests CourseCatalog's constructor.
     */
    @Test
    public void testCourseCatalog() {
        CourseCatalog cC1 = new CourseCatalog();
        assertEquals(0, cC1.getCourseCatalog().length);
        cC1.saveCourseCatalog("test-files/actual_empty_export.txt");
        checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");

        //Test with valid file containing 8 courses.  Will test other methods in other tests.
        CourseCatalog cC2 = new CourseCatalog();
        cC2.loadCoursesFromFile(validTestFile);
        assertEquals(13, cC2.getCourseCatalog().length);		
    }

    /**
     * Tests CourseCatalog's newCourseCatalog() method.
     */
    @Test
    public void testNewCourseCatalog() {
        CourseCatalog cC = new CourseCatalog();

        // Add some courses
        assertTrue(cC.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP,
                MEETING_DAYS, START_TIME, END_TIME));
        assertEquals(1, cC.getCourseCatalog().length);

        // Reset the catalog
        cC.newCourseCatalog();
        assertEquals(0, cC.getCourseCatalog().length);

        // Check that resetting doesn't break future adds
        assertTrue(cC.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP,
                MEETING_DAYS, START_TIME, END_TIME));
        assertEquals(1, cC.getCourseCatalog().length);
    }

    /**
     * Tests CourseCatalog's loadCoursesFromFile method.
     */
    @Test
    public void testLoadCoursesFromFile() {
        // Test with invalid file. Should have an empty catalog and schedule. 
        CourseCatalog cC1 = new CourseCatalog();
        cC1.loadCoursesFromFile(invalidTestFile);
        assertEquals(0, cC1.getCourseCatalog().length);
        
        // Test with valid file
        CourseCatalog cC = new CourseCatalog();
        cC.loadCoursesFromFile(validTestFile);

        //Get the catalog and make sure contents are correct
        //Name, section, title
        String[][] catalog = cC.getCourseCatalog();
        //Row 0
        assertEquals("CSC116", catalog[0][0]);
        assertEquals("001", catalog[0][1]);
        assertEquals("Intro to Programming - Java", catalog[0][2]);
        //Row 1
        assertEquals("CSC116", catalog[1][0]);
        assertEquals("002", catalog[1][1]);
        assertEquals("Intro to Programming - Java", catalog[1][2]);
        //Row 2
        assertEquals("CSC116", catalog[2][0]);
        assertEquals("003", catalog[2][1]);
        assertEquals("Intro to Programming - Java", catalog[2][2]);
        //Row 3
        assertEquals("CSC216", catalog[3][0]);
        assertEquals("001", catalog[3][1]);
        assertEquals("Software Development Fundamentals", catalog[3][2]);
        //Row 4
        assertEquals("CSC216", catalog[4][0]);
        assertEquals("002", catalog[4][1]);
        assertEquals("Software Development Fundamentals", catalog[4][2]);
        //Row 5
        assertEquals("CSC216", catalog[5][0]);
        assertEquals("601", catalog[5][1]);
        assertEquals("Software Development Fundamentals", catalog[5][2]);
        //Row 6
        assertEquals("CSC217", catalog[6][0]);
        assertEquals("202", catalog[6][1]);
        assertEquals("Software Development Fundamentals Lab", catalog[6][2]);
        //Row 7
        assertEquals("CSC217", catalog[7][0]);
        assertEquals("211", catalog[7][1]);
        assertEquals("Software Development Fundamentals Lab", catalog[7][2]);
        //Row 8
        assertEquals("CSC217", catalog[8][0]);
        assertEquals("223", catalog[8][1]);
        assertEquals("Software Development Fundamentals Lab", catalog[8][2]);
        //Row 9
        assertEquals("CSC217", catalog[9][0]);
        assertEquals("601", catalog[9][1]);
        assertEquals("Software Development Fundamentals Lab", catalog[9][2]);
        //Row 10
        assertEquals("CSC226", catalog[10][0]);
        assertEquals("001", catalog[10][1]);
        assertEquals("Discrete Mathematics for Computer Scientists", catalog[10][2]);
        //Row 11
        assertEquals("CSC230", catalog[11][0]);
        assertEquals("001", catalog[11][1]);
        assertEquals("C and Software Tools", catalog[11][2]);
        //Row 12
        assertEquals("CSC316", catalog[12][0]);
        assertEquals("001", catalog[12][1]);
        assertEquals("Data Structures and Algorithms", catalog[12][2]);
    }

    /**
     * Test WolfScheduler.addCourse().
     */
    @Test
    public void testAddCourseToSchedule() {
        CourseCatalog cC = new CourseCatalog();

        //Attempt to add a course
        assertTrue(cC.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP,
                MEETING_DAYS, START_TIME, END_TIME));
        Activity c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP,
                MEETING_DAYS, START_TIME, END_TIME);
        
        assertEquals(1, cC.getCourseCatalog().length);
        String[] course = cC.getCourseCatalog()[0];
        assertEquals(NAME, course[0]);
        assertEquals(SECTION, course[1]);
        assertEquals(TITLE, course[2]);
        assertEquals(c.getMeetingString(), course[3]);

        //Attempt to add an identical course
        assertFalse(cC.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP,
                MEETING_DAYS, START_TIME, END_TIME));
        
        // Attempt to add a Course with an invalid name
        try {
            cC.addCourseToCatalog(null, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, CAP,
                    MEETING_DAYS, START_TIME, END_TIME);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid course name.", e.getMessage());
        }
    }

    /**
     * Tests CourseCatalog's removeCourseFromCatalog() method.
     */
    @Test
    public void testRemoveCourseFromCatalog() {
        CourseCatalog cC = new CourseCatalog();
        
        // Attempt to remove from an empty schedule
        assertFalse(cC.removeCourseFromCatalog(NAME, SECTION));
        
        // Add Courses
        assertTrue(cC.addCourseToCatalog("CSC217", "Software Development Fundamentals Lab", "202", 1, "sesmith5", 10, "M", 1040, 1230));
        assertTrue(cC.addCourseToCatalog("CSC230", "C and Software Tools", "001", 3, "dbsturgi", 10, "MW", 1145, 1300));
        assertTrue(cC.addCourseToCatalog("CSC116", "Intro to Programming - Java", "002", 3, "jtking", 10, "TH", 910, 1100));
        assertEquals(3, cC.getCourseCatalog().length);
        
        // Remove Courses
        assertTrue(cC.removeCourseFromCatalog("CSC230", "001"));
        assertEquals(2, cC.getCourseCatalog().length);
        
        assertTrue(cC.removeCourseFromCatalog("CSC116", "002"));
        assertEquals(1, cC.getCourseCatalog().length);
        
        assertTrue(cC.removeCourseFromCatalog("CSC217", "202"));
        assertEquals(0, cC.getCourseCatalog().length);
        
        // Check that removing all doesn't break future additions
        assertTrue(cC.addCourseToCatalog("CSC217", "Software Development Fundamentals Lab", "202", 1, "sesmith5", 10, "M", 1040, 1230));
        assertEquals(1, cC.getCourseCatalog().length);
    }

    /**
     * Test CourseCatalog's getCourseFromCatalog() method.
     */
    @Test
    public void testGetCourseFromCatalog() {
        CourseCatalog cC = new CourseCatalog();
        cC.loadCoursesFromFile(validTestFile);

        //Attempt to get a course that doesn't exist
        assertNull(cC.getCourseFromCatalog("CSC 492", "001"));

        //Attempt to get a course that does exist
        Activity c = new Course(NAME, TITLE, SECTION, CREDITS, null, CAP, MEETING_DAYS, START_TIME, END_TIME);
        assertEquals(c, cC.getCourseFromCatalog("CSC216", "001"));
    }

    /**
     * Test CourseCatalog's getCourseCatalog() method.
     */
    @Test
    public void testGetCourseCatalog() {
        CourseCatalog cC = new CourseCatalog();
        
        // Add Courses
        assertTrue(cC.addCourseToCatalog("CSC217", "Software Development Fundamentals Lab", "202", 1, "sesmith5", 10, "M", 1040, 1230));
        assertTrue(cC.addCourseToCatalog("CSC230", "C and Software Tools", "001", 3, "dbsturgi", 10, "MW", 1145, 1300));
        assertTrue(cC.addCourseToCatalog("CSC116", "Intro to Programming - Java", "002", 3, "jtking", 10, "TH", 910, 1100));
        assertEquals(3, cC.getCourseCatalog().length);
        
        String[][] catalog = cC.getCourseCatalog();
        
        // first row
        assertEquals("CSC116", catalog[0][0]);
        assertEquals("002", catalog[0][1]);
        assertEquals("Intro to Programming - Java", catalog[0][2]);
        assertEquals("TH 9:10AM-11:00AM", catalog[0][3]);
        
        // second row
        assertEquals("CSC217", catalog[1][0]);
        assertEquals("202", catalog[1][1]);
        assertEquals("Software Development Fundamentals Lab", catalog[1][2]);
        assertEquals("M 10:40AM-12:30PM", catalog[1][3]);
        
        // third row
        assertEquals("CSC230", catalog[2][0]);
        assertEquals("001", catalog[2][1]);
        assertEquals("C and Software Tools", catalog[2][2]);
        assertEquals("MW 11:45AM-1:00PM", catalog[2][3]);
    }

    /**
     * Tests CourseCatalog's saveCourseCatalog() method.
     */
    @Test
    public void testSaveCourseCatalog() {
        //Test that empty schedule exports correctly
        CourseCatalog cC = new CourseCatalog();
        cC.saveCourseCatalog("test-files/actual_empty_export.txt");
        checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");

        //Add courses and test that exports correctly
        cC.addCourseToCatalog("CSC226", "Discrete Mathematics for Computer Scientists", "001", 3, "tmbarnes", 10, "MWF", 935, 1025);
        cC.addCourseToCatalog("CSC216", "Software Development Fundamentals", "002", 3, "ixdoming", 10, "MW", 1330, 1445);
        assertEquals(2, cC.getCourseCatalog().length);
        cC.saveCourseCatalog("test-files/actual_catalog_export.txt");
        checkFiles("test-files/expected_catalog_export.txt", "test-files/actual_catalog_export.txt");
    }

    /**
     * Helper method to compare two files for the same contents
     * @param expFile expected output
     * @param actFile actual output
     */
    private void checkFiles(String expFile, String actFile) {
        try (Scanner expScanner = new Scanner(new File(expFile));
                Scanner actScanner = new Scanner(new File(actFile));) {

            while (actScanner.hasNextLine()) {
                assertEquals(expScanner.nextLine(), actScanner.nextLine());
            }
            if (expScanner.hasNextLine()) {
                fail();
            }

            expScanner.close();
            actScanner.close();
        } catch (IOException e) {
            fail("Error reading files.");
        }
    }

}
