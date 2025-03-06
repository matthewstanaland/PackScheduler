package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Test Student Class
 * 
 * @author Dreese Abdelilah
 * @author Kergan Sanderson
 */
class StudentTest {

	/** Test Students's first name */
	private String firstName = "Dreese";

	/** Test Student's last name */
	private String lastName = "Abdelilah";

	/** Test Student's Id */
	private String id = "dabdeli";

	/** Test Student's email */
	private String email = "dabdeli@ncsu.edu";

	/** Test Student's max credits */
	private int maxCredits = 18;

	/** Test Student's password */
	private String password = "kwenlfnwelkfnwelk";

	/**
	 * Test HashCode
	 */
	@Test
	void testHashCode() {
		Student s1 = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Student s2 = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Student s3 = new Student("Adam", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");

		assertEquals(s1.hashCode(), s2.hashCode());

		assertNotEquals(s1.hashCode(), s3.hashCode());
	}

	/**
	 * Test constructing a valid Student
	 */
	@Test
	void testValidStudent() {

		Student s1 = assertDoesNotThrow(() -> new Student(firstName, lastName, id, email, password, maxCredits),
				"Should not throw exception");

		assertAll("Student", () -> assertEquals("Dreese", s1.getFirstName()),
				() -> assertEquals("Abdelilah", s1.getLastName()),
				() -> assertEquals("dabdeli@ncsu.edu", s1.getEmail()), () -> assertEquals("dabdeli", s1.getId()),
				() -> assertEquals("kwenlfnwelkfnwelk", s1.getPassword()), () -> assertEquals(18, s1.getMaxCredits()));

	}

	/**
	 * Testing Student constructor
	 */
	@Test
	void testStudentConstuctor() {
		Student s1 = assertDoesNotThrow(() -> new Student(firstName, lastName, id, email, password),
				"Should not throw exception");

		assertAll("Student", () -> assertEquals("Dreese", s1.getFirstName()),
				() -> assertEquals("Abdelilah", s1.getLastName()),
				() -> assertEquals("dabdeli@ncsu.edu", s1.getEmail()), () -> assertEquals("dabdeli", s1.getId()),
				() -> assertEquals("kwenlfnwelkfnwelk", s1.getPassword()));
	}

	/**
	 * Test SetEmail invalid if null
	 */
	@Test
	void testSetEmailInvalidNull() {
		Student s = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setEmail(null));
		assertEquals("Invalid email", e.getMessage());
		assertEquals("dabdeli@ncsu.edu", s.getEmail());
	}

	/**
	 * Test SetEmail invalid if no @
	 */
	@Test
	void testSetEmailInvalidNoAt() {
		Student s = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setEmail("dabdelincsu.edu"));
		assertEquals("Invalid email", e.getMessage());

	}

	/**
	 * Test SetEmail invalid if no .
	 */
	@Test
	void testSetEmailInvalidNoDot() {
		Student s = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setEmail("dabdeli@ncsuedu"));
		assertEquals("Invalid email", e.getMessage());

	}

	/**
	 * Test for null password
	 */
	@Test
	void testSetPassword() {
		Student s = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setPassword(null));
		assertEquals("Invalid password", e.getMessage());
		assertEquals("kwenlfnwelkfnwelk", s.getPassword());
	}

	/**
	 * Test for empty string password
	 */
	@Test
	void testSetPasswordEmpty() {
		Student s = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setPassword(""));
		assertEquals("Invalid password", e.getMessage());
	}

	/**
	 * Test SetMaxCredits Invalid
	 */
	@Test
	void testSetMaxCreditsInvalid() {
		Student s = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setMaxCredits(19));
		assertEquals("Invalid max credits", e.getMessage());
		assertEquals(18, s.getMaxCredits());
	}

	/**
	 * Test Student First name nullException
	 */
	@Test
	void testSetFirstNameExcetpion() {
		Student s = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setFirstName(null));
		assertEquals("Invalid first name", e.getMessage());
		assertEquals("Dreese", s.getFirstName());
	}

	/**
	 * Test Student First name emptyString
	 */
	@Test
	void testSetFirstNameEmpty() {
		Student s = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setFirstName(""));
		assertEquals("Invalid first name", e.getMessage());

	}

	/**
	 * Test Student Last name nullException
	 */
	@Test
	void testSetLastName() {
		Student s = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setLastName(null));
		assertEquals("Invalid last name", e.getMessage());
		assertEquals("Abdelilah", s.getLastName());
	}

	/**
	 * Test Student Last name empty
	 */
	@Test
	void testSetLastNameEmptyString() {
		Student s = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Exception e = assertThrows(IllegalArgumentException.class, () -> s.setLastName(""));
		assertEquals("Invalid last name", e.getMessage());
	}
	
	/**
	 * Tests the canAdd method.
	 */
	@Test
	void canAdd() {
		Student s = new Student("Kergan", "Sanderson", "kasande4", "kasande4@ncsu.edu", "pw", 13);
		Schedule schedule = s.getSchedule();
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 4, "jdyoung2", 10, "MW", 1100, 1150);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "001", 4, "sesmith5", 10, "TH", 1330, 1445);
		Course c3 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "001", 2, "tmbarnes", 10, "MWF", 935, 1025);
		
		Course c1Duplicate = new Course("CSC116", "Intro to Programming - Java", "001", 2, "jdyoung2", 10, "MW", 1100, 1150);
		Course c2Conflict = new Course("CSC316", "Super Cool Class", "001", 2, "jdyoung2", 10, "TH", 1430, 1545);
		Course cTooManyCredits = new Course("CSC316", "Super Cool Class", "001", 4, "jdyoung2", 10, "M", 1430, 1545);
		
		// test valid additions
		assertTrue(s.canAdd(c1));
		schedule.addCourseToSchedule(c1);
		assertTrue(s.canAdd(c2));
		schedule.addCourseToSchedule(c2);
		assertTrue(s.canAdd(c3));
		schedule.addCourseToSchedule(c3);
		
		// test false cases
		assertFalse(s.canAdd(null));
		assertFalse(s.canAdd(c1Duplicate));
		assertFalse(s.canAdd(c2Conflict));
		assertFalse(s.canAdd(cTooManyCredits));
	}

	/**
	 * Test Student Equals method works
	 */
	@Test
	void testEqualsObject() {
		Student s1 = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Student s2 = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Student s3 = new Student("Adam", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");

		assertEquals(s1, s2);
		assertEquals(s2, s1);

		assertNotEquals(s1, s3);
		assertNotEquals(s2, s3);

	}

	/**
	 * Test toString method
	 */
	@Test
	public void testToString() {
		Student s = new Student(firstName, lastName, id, email, password);
		assertEquals("Dreese,Abdelilah,dabdeli,dabdeli@ncsu.edu," + password + ",18", s.toString());
	}
	
	/**
	 * Test compareTo method
	 */
	@Test
	public void testCompareTo() {
		Student s = new Student("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
		Student s1 = new Student("Adam", "Abdelilah", "abdeli", "aabdeli@ncsu.edu", "klewnfklewnflnewk");
		Student s2 = new Student("Adam", "Abdelilah", "abdeli", "aabdeli@ncsu.edu", "klewnfklewnflnewk");
		
		Exception e = assertThrows(NullPointerException.class, () -> s.compareTo(null));
		assertEquals("Null Pointer Exception.", e.getMessage());
		assertEquals(s.getFirstName().compareTo(s1.getFirstName()), 3);
		assertEquals(s.getLastName().compareTo(s1.getLastName()), 0);
		assertEquals(s.getId().compareTo(s1.getId()), 3);
		assertEquals(s1.getId().compareTo(s2.getId()), 0);
	}
	
	/**
	 * Tests getSchedule method.
	 */
	@Test
	public void testGetSchedule() {
		Student s = new Student("Kergan", "Sanderson", "kasande4", "kasande4@ncsu.edu", "pw");
		Schedule schedule = s.getSchedule();
		assertEquals(0, schedule.getScheduledCourses().length);
		// no further functionality at the time of this comment (1:33PM, October 27th, 2024)
	}

	/**
	 * Test toString() method.
	 */
	@Test
	public void testToStringSarahHeckman() {
		firstName = "first";
		lastName = "last";
		id = "flast";
		email = "first_last@ncsu.edu";
		String hashPW = "";
		final String hashAlgorithm = "SHA-256";
		
		//This is a block of code that is executed when the StudentTest object is
		//created by JUnit.  Since we only need to generate the hashed version
		//of the plaintext password once, we want to create it as the StudentTest object is
		//constructed.  By automating the hash of the plaintext password, we are
		//not tied to a specific hash implementation.  We can change the algorithm
		//easily.
		{
			try {
				String plaintextPW = "password";
				MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
				digest.update(plaintextPW.getBytes());
				hashPW = Base64.getEncoder().encodeToString(digest.digest());
			} catch (NoSuchAlgorithmException e) {
				fail("An unexpected NoSuchAlgorithmException was thrown.");
			}
		}
		
		Student s1 = new Student(firstName, lastName, id, email, hashPW);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",18", s1.toString());
	}

}
