/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;


/**
 * Test Faculty Class 
 */
public class FacultyTest {

		/** Test Students's first name */
		private String firstName = "Dreese";

		/** Test Student's last name */
		private String lastName = "Abdelilah";

		/** Test Student's Id */
		private String id = "dabdeli";

		/** Test Student's email */
		private String email = "dabdeli@ncsu.edu";

		/** Test Student's max credits */
		private int maxCourses = 3;
		

		/** Test Student's password */
		private String password = "kwenlfnwelkfnwelk";

		/**
		 * Test HashCode
		 */
		@Test
		void testHashCode() {
			Faculty s1 = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Faculty s2 = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Faculty s3 = new Faculty("Adam", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");

			assertEquals(s1.hashCode(), s2.hashCode());

			assertNotEquals(s1.hashCode(), s3.hashCode());
		}

		/**
		 * Test constructing a valid Faculty
		 */
		@Test
		void testValidFaculty() {

			Faculty s1 = assertDoesNotThrow(() -> new Faculty(firstName, lastName, id, email, password, maxCourses),
					"Should not throw exception");

			assertAll("Faculty", () -> assertEquals("Dreese", s1.getFirstName()),
					() -> assertEquals("Abdelilah", s1.getLastName()),
					() -> assertEquals("dabdeli@ncsu.edu", s1.getEmail()), () -> assertEquals("dabdeli", s1.getId()),
					() -> assertEquals("kwenlfnwelkfnwelk", s1.getPassword()), () -> assertEquals(3, s1.getMaxCourses()));

		}

		/**
		 * Testing Faculty constructor
		 */
		@Test
		void testFacultyConstuctor() {
			Faculty s1 = assertDoesNotThrow(() -> new Faculty(firstName, lastName, id, email, password),
					"Should not throw exception");

			assertAll("Faculty", () -> assertEquals("Dreese", s1.getFirstName()),
					() -> assertEquals("Abdelilah", s1.getLastName()),
					() -> assertEquals("dabdeli@ncsu.edu", s1.getEmail()), () -> assertEquals("dabdeli", s1.getId()),
					() -> assertEquals("kwenlfnwelkfnwelk", s1.getPassword()));
		}

		/**
		 * Test SetEmail invalid if null
		 */
		@Test
		void testSetEmailInvalidNull() {
			Faculty s = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Exception e = assertThrows(IllegalArgumentException.class, () -> s.setEmail(null));
			assertEquals("Invalid email", e.getMessage());
			assertEquals("dabdeli@ncsu.edu", s.getEmail());
		}

		/**
		 * Test SetEmail invalid if no @
		 */
		@Test
		void testSetEmailInvalidNoAt() {
			Faculty s = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Exception e = assertThrows(IllegalArgumentException.class, () -> s.setEmail("dabdelincsu.edu"));
			assertEquals("Invalid email", e.getMessage());

		}

		/**
		 * Test SetEmail invalid if no .
		 */
		@Test
		void testSetEmailInvalidNoDot() {
			Faculty s = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Exception e = assertThrows(IllegalArgumentException.class, () -> s.setEmail("dabdeli@ncsuedu"));
			assertEquals("Invalid email", e.getMessage());

		}

		/**
		 * Test for null password
		 */
		@Test
		void testSetPassword() {
			Faculty s = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Exception e = assertThrows(IllegalArgumentException.class, () -> s.setPassword(null));
			assertEquals("Invalid password", e.getMessage());
			assertEquals("kwenlfnwelkfnwelk", s.getPassword());
		}

		/**
		 * Test for empty string password
		 */
		@Test
		void testSetPasswordEmpty() {
			Faculty s = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Exception e = assertThrows(IllegalArgumentException.class, () -> s.setPassword(""));
			assertEquals("Invalid password", e.getMessage());
		}

		/**
		 * Test SetMaxCourses Invalid
		 */
		@Test
		void testSetMaxCoursesInvalid() {
			Faculty s = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Exception e = assertThrows(IllegalArgumentException.class, () -> s.setMaxCourses(19));
			assertEquals("Invalid max courses", e.getMessage());
			assertEquals(3, s.getMaxCourses());
		}

		/**
		 * Test Faculty First name nullException
		 */
		@Test
		void testSetFirstNameExcetpion() {
			Faculty s = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Exception e = assertThrows(IllegalArgumentException.class, () -> s.setFirstName(null));
			assertEquals("Invalid first name", e.getMessage());
			assertEquals("Dreese", s.getFirstName());
		}

		/**
		 * Test Faculty First name emptyString
		 */
		@Test
		void testSetFirstNameEmpty() {
			Faculty s = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Exception e = assertThrows(IllegalArgumentException.class, () -> s.setFirstName(""));
			assertEquals("Invalid first name", e.getMessage());

		}

		/**
		 * Test Faculty Last name nullException
		 */
		@Test
		void testSetLastName() {
			Faculty s = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Exception e = assertThrows(IllegalArgumentException.class, () -> s.setLastName(null));
			assertEquals("Invalid last name", e.getMessage());
			assertEquals("Abdelilah", s.getLastName());
		}

		/**
		 * Test Faculty Last name empty
		 */
		@Test
		void testSetLastNameEmptyString() {
			Faculty s = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Exception e = assertThrows(IllegalArgumentException.class, () -> s.setLastName(""));
			assertEquals("Invalid last name", e.getMessage());
		}
		

		/**
		 * Test Faculty Equals method works
		 */
		@Test
		void testEqualsObject() {
			Faculty s1 = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Faculty s2 = new Faculty("Dreese", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");
			Faculty s3 = new Faculty("Adam", "Abdelilah", "dabdeli", "dabdeli@ncsu.edu", "kwenlfnwelkfnwelk");

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
			Faculty s = new Faculty(firstName, lastName, id, email, password);
			assertEquals("Dreese,Abdelilah,dabdeli,dabdeli@ncsu.edu," + password + ",3", s.toString());
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
			
			Faculty s1 = new Faculty(firstName, lastName, id, email, hashPW);
			assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",3", s1.toString());
		}

	}

