package edu.ncsu.csc216.pack_scheduler.directory;



import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Tests FacultyDirectory.
 * @author Sarah Heckman
 * @author Cameron Edwards
 * @author Matt Stanaland
 */
public class FacultyDirectoryTest {
	
	/** Valid course records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Fac";
	/** Test last name */
	private static final String LAST_NAME = "Ulty";
	/** Test id */
	private static final String ID = "fculty";
	/** Test email */
	private static final String EMAIL = "fculty@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_COURSES = 3;
	
	
	/**
	 * Resets course_records.txt for use in other tests.
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {		
		//Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests FacultyDirectory().
	 */
	@Test
	public void testFacultyDirectory() {
		//Test that the FacultyDirectory is initialized to an empty list
		FacultyDirectory sd = new FacultyDirectory();
		assertFalse(sd.removeFaculty("sesmith5"));
		assertEquals(0, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.testNewFacultyDirectory().
	 */
	@Test
	public void testNewFacultyDirectory() {
		//Test that if there are faculty members in the directory, they 
		//are removed after calling newFacultyDirectory().
		FacultyDirectory sd = new FacultyDirectory();
		
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
		
		sd.newFacultyDirectory();
		assertEquals(0, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.loadFacultyFromFile().
	 */
	@Test
	public void testLoadFacultyFromFile() {
		FacultyDirectory sd = new FacultyDirectory();
				
		//Test valid file
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.addFaculty().
	 */
	@Test
	public void testAddFaculty() {
		FacultyDirectory sd = new FacultyDirectory();
		
		//Test valid Faculty
		sd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		String [][] facultyDirectory = sd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertEquals(FIRST_NAME, facultyDirectory[0][0]);
		assertEquals(LAST_NAME, facultyDirectory[0][1]);
		assertEquals(ID, facultyDirectory[0][2]);
	}

	/**
	 * Tests FacultyDirectory.removeFaculty().
	 */
	@Test
	public void testRemoveFaculty() {
		FacultyDirectory sd = new FacultyDirectory();
				
		//Add faculty members and remove
		sd.loadFacultyFromFile(validTestFile);
		assertEquals(8, sd.getFacultyDirectory().length);
		assertTrue(sd.removeFaculty("ebriggs"));
		String [][] facultyDirectory = sd.getFacultyDirectory();
		assertEquals(7, facultyDirectory.length);
		assertEquals("Norman", facultyDirectory[5][0]);
		assertEquals("Brady", facultyDirectory[5][1]);
		assertEquals("nbrady", facultyDirectory[5][2]);
	}

	/**
	 * Tests FacultyDirectory.saveFacultyDirectory().
	 */
	@Test
	public void testSaveFacultyDirectory() {
		FacultyDirectory sd = new FacultyDirectory();
		
		//Add a faculty
		sd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		assertEquals(1, sd.getFacultyDirectory().length);
		sd.addFaculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "pw", "pw", 3);
		assertEquals(2, sd.getFacultyDirectory().length);
		sd.addFaculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "pw", "pw", 1);
		assertEquals(3, sd.getFacultyDirectory().length);
		sd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
	}
	
	/**
	 * Helper method to compare two files for the same contents.
	 * @param expFile the path to the expected file
	 * @param actFile the path to the actual file
	 */
	private void checkFiles(String expFile, String actFile) {
	    try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
	         Scanner actScanner = new Scanner(new FileInputStream(actFile))) {

	        while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
	            String expectedLine = expScanner.nextLine();
	            String actualLine = actScanner.nextLine();
	            assertEquals(expectedLine, actualLine, "Mismatch in file contents.");
	        }

	        // Ensure both files are fully read
	        if (expScanner.hasNextLine()) {
	            fail("Expected file has more lines than the actual file.");
	        }
	        if (actScanner.hasNextLine()) {
	            fail("Actual file has more lines than the expected file.");
	        }
	    } catch (IOException e) {
	        fail("Error reading files: " + e.getMessage());
	    }
	}



	
	/**
	 * Tests getting a Faculty by their id
	 */
	@Test
	public void testGetFacultyById() {
		FacultyDirectory sd = new FacultyDirectory();
		
		//Add a faculty
		sd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
		Faculty newFac = sd.getFacultyById("awitt");
		assertEquals("Ashely", newFac.getFirstName());
	}
	
	@Test
	public void testLoadFacultyFromFileEdgeCases() {
	    FacultyDirectory sd = new FacultyDirectory();

	    // Test loading a valid file
	    sd.loadFacultyFromFile(validTestFile);
	    assertEquals(8, sd.getFacultyDirectory().length);

	    // Test loading a non-existent file
	    Exception e = assertThrows(IllegalArgumentException.class, () -> sd.loadFacultyFromFile("nonexistent_file.txt"));
	    assertEquals("Unable to read file nonexistent_file.txt", e.getMessage());

	    // Ensure directory is empty after attempting to load invalid file
	    sd.newFacultyDirectory();
	    assertEquals(0, sd.getFacultyDirectory().length);
	}

	@Test
	public void testSaveFacultyDirectoryEdgeCases() {
	    FacultyDirectory sd = new FacultyDirectory();

	    // Add faculty members with the same hashed password used in the file
	    sd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
	    sd.addFaculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "pw", "pw", 3);
	    sd.addFaculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "pw", "pw", 1);

	    // Save the faculty directory to an actual output file
	    String actualFile = "test-files/actual_faculty_records.txt";
	    sd.saveFacultyDirectory(actualFile);

	    // Compare the actual output file with the expected file
	    checkFiles("test-files/expected_faculty_records.txt", actualFile);

	    // Test saving to an invalid file path
	    Exception e = assertThrows(IllegalArgumentException.class, () -> sd.saveFacultyDirectory("/invalid_path/test_faculty.txt"));
	    assertEquals("Unable to write to file /invalid_path/test_faculty.txt", e.getMessage());
	}

	@Test
	public void testSaveFacultyDirectoryWithFullRecords() {
	    FacultyDirectory sd = new FacultyDirectory();

	    // Add faculty members matching the full expected file
	    sd.addFaculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", "pw", "pw", 2);
	    sd.addFaculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", "pw", "pw", 3);
	    sd.addFaculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", "pw", "pw", 1);
	    sd.addFaculty("Halla", "Aguirre", "haguirr", "Fusce.dolor.quam@amalesuadaid.net", "pw", "pw", 3);
	    sd.addFaculty("Kevyn", "Patel", "kpatel", "risus@pellentesque.ca", "pw", "pw", 1);
	    sd.addFaculty("Elton", "Briggs", "ebriggs", "arcu.ac@ipsumsodalespurus.edu", "pw", "pw", 3);
	    sd.addFaculty("Norman", "Brady", "nbrady", "pede.nonummy@elitfermentum.co.uk", "pw", "pw", 1);
	    sd.addFaculty("Lacey", "Walls", "lwalls", "nascetur.ridiculus.mus@fermentum.net", "pw", "pw", 2);

	    // Save the faculty directory to an output file
	    String actualFile = "test-files/actual_full_faculty_records.txt";
	    sd.saveFacultyDirectory(actualFile);

	    // Compare the actual output file with the expected file
	    checkFiles("test-files/expected_full_faculty_records.txt", actualFile);
	}


}
	
	
