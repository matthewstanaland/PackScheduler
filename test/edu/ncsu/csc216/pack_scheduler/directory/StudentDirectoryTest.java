package edu.ncsu.csc216.pack_scheduler.directory;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests StudentDirectory.
 * @author Sarah Heckman
 * @author Ruairi Gallagher
 */
public class StudentDirectoryTest {
	
	/** Valid course records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 15;
	
	/**
	 * Resets course_records.txt for use in other tests.
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {		
		//Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests StudentDirectory().
	 */
	@Test
	public void testStudentDirectory() {
		//Test that the StudentDirectory is initialized to an empty list
		StudentDirectory sd = new StudentDirectory();
		assertFalse(sd.removeStudent("sesmith5"));
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.testNewStudentDirectory().
	 */
	@Test
	public void testNewStudentDirectory() {
		//Test that if there are students in the directory, they 
		//are removed after calling newStudentDirectory().
		StudentDirectory sd = new StudentDirectory();
		
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		
		sd.newStudentDirectory();
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.loadStudentsFromFile().
	 */
	@Test
	public void testLoadStudentsFromFile() {
		StudentDirectory sd = new StudentDirectory();
				
		//Test valid file
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.addStudent().
	 */
	@Test
	public void testAddStudent() {
		StudentDirectory sd = new StudentDirectory();
		
		//Test valid Student
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String [][] studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);
	}

	/**
	 * Tests StudentDirectory.removeStudent().
	 */
	@Test
	public void testRemoveStudent() {
		StudentDirectory sd = new StudentDirectory();
				
		//Add students and remove
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		assertTrue(sd.removeStudent("efrost"));
		String [][] studentDirectory = sd.getStudentDirectory();
		assertEquals(9, studentDirectory.length);
		assertEquals("Zahir", studentDirectory[5][0]);
		assertEquals("King", studentDirectory[5][1]);
		assertEquals("zking", studentDirectory[5][2]);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory().
	 */
	@Test
	public void testSaveStudentDirectory() {
		StudentDirectory sd = new StudentDirectory();
		
		//Add a student
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		assertEquals(1, sd.getStudentDirectory().length);
		sd.saveStudentDirectory("test-files/actual_student_records.txt");
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	
	// EVERYTHING BELOW WAS WRITTEN BY RUAIRI GALLAGHER
	
	/** Valid course records */
    private final String validTestFileR = "test-files/student_records_R.txt";
    
    /**
     * Tests studentDirectory.loadStudentsFromFile()
     */
    @Test
    void testValidLoadStudentsFromFileR() {
        StudentDirectory sd = new StudentDirectory();
        sd.loadStudentsFromFile(validTestFileR);
        String[][] studentArray = sd.getStudentDirectory();
        assertEquals(8, sd.getStudentDirectory().length);
        assertEquals("Carlos", studentArray[5][0]);
        assertEquals("Sanchez", studentArray[5][1]);
        assertEquals("casanch3", studentArray[5][2]);
        
    }
    
    /**
     * Tests studentDirectory.loadStudentsFromFile() (invalid)
     */
    @Test
    void testInvalidLoadStudentsFromfileR() {
        StudentDirectory sd = new StudentDirectory();
        //Test invalid filename 
        assertThrows(IllegalArgumentException.class,
                () -> sd.loadStudentsFromFile("invalidFilename"));
    }
    
    /**
     * Tests studentDirectory.addStudent with a null password
     */
    @Test 
    void testAddStudentInvalidPasswordNullPasswordR() {
        StudentDirectory sd = new StudentDirectory();
        //Test addStudentInvalidPassword if the password and or repeatPassword is null
        Exception e1 = assertThrows(IllegalArgumentException.class,
                () -> sd.addStudent("Invalid", "Password", "ivpword", "ivpword@ncsu.edu", null, null, 15));
        assertEquals("Invalid password", e1.getMessage());
                
    }
    
    /**
     * Tests studentDirectory.addStudent with the empty string password
     */
    @Test 
    void testAddStudentInvalidPasswordEmptyStringR() {
        StudentDirectory sd = new StudentDirectory();
        //Test addStudentInvalidPassword if the password and or repeatPassword is the empty string
        Exception e1 = assertThrows(IllegalArgumentException.class,
                () -> sd.addStudent("Invalid", "Password", "ivpword", "ivpword@ncsu.edu", "", "", 15));
        assertEquals("Invalid password", e1.getMessage());
                
    }
    
    /**
     *  Tests studentDirectory.addStudent with the password and repeat password not matching
     */
    @Test 
    void testAddStudentInvalidPasswordRepeatDoesntMatchR() {
        StudentDirectory sd = new StudentDirectory();
        //Test when adding a student correct exception is thrown if the password and repeatPassword don't match.
        Exception e1 = assertThrows(IllegalArgumentException.class,
                () -> sd.addStudent("Invalid", "Password", "ivpword", "ivpword@ncsu.edu", "i", "j", 15));
        assertEquals("Passwords do not match", e1.getMessage());
                
    }
    
    
    /**
     * Tests studentDirectory.addStudent
     */
    @Test
    void testAddStudentR() {
        StudentDirectory sd = new StudentDirectory();
        sd.loadStudentsFromFile(validTestFileR);
        sd.addStudent("Akbari", "Nawedullah", "nakbari", "nakbari@ncsu.edu", "naveed", "naveed", 14);
        String[][] sdArray = sd.getStudentDirectory();
        assertEquals(9, sd.getStudentDirectory().length);
        assertEquals("Selin", sdArray[8][0]);
        assertEquals("Yalcin", sdArray[8][1]);
        assertEquals("syalcin", sdArray[8][2]);
    }

    /**
     * Tests studentDirectory.removeStudent
     */
    @Test
    void testRemoveStudentR() {
        StudentDirectory sd = new StudentDirectory();
        sd.loadStudentsFromFile(validTestFileR);
        sd.removeStudent("dabdeli");
        assertEquals(7, sd.getStudentDirectory().length);
    }
    
    /**
     * Tests studentDirectory.saveStudentDirectory
     */
    @Test
    void testValidSaveStudentDirectoryR() {
        StudentDirectory sd = new StudentDirectory();
        
        //Add a student
        sd.addStudent("Ruairi", "Gallagher", "rjgallag", "rjgallag@ncsu.edu", "wengoknewkfnwe", "wengoknewkfnwe", 14);
        assertEquals(1, sd.getStudentDirectory().length);
        sd.saveStudentDirectory("test-files/actual_student_records_R.txt");
        checkFiles("test-files/expected_student_records_R.txt", "test-files/actual_student_records_R.txt");
    }
    
    @Test
    void testGetStudentById() {
    	StudentDirectory sd = new StudentDirectory();
    	sd.loadStudentsFromFile(validTestFile);
    	Student student = new Student("Emerald", "Frost", "efrost", "adipiscing@acipsumPhasellus.edu", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 3);
    	assertEquals(student, sd.getStudentById("efrost"));
    	
    	assertNull(sd.getStudentById("ajens"));
    }

}
