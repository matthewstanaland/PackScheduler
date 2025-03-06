/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Maintains a directory of the all the faculty at NC State
 * @author Cameron Edwards
 * @author Matt Stanaland
 */
public class FacultyDirectory {
	/** List of faculty members in the directory */
	private LinkedList<Faculty> facultyDirectory;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Creates an empty faculty directory.
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}
	
	/**
	 * Creates an empty faculty directory as a LinkedList.
	 * All faculty members in the previous list are lost unless saved by the user.
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}
	
	/**
	 * Constructs the faculty directory by reading in faculty information
	 * from the given file.  Throws an IllegalArgumentException if the 
	 * file cannot be found.
	 * @param fileName file containing list of faculty members
	 * @throws IllegalArgumentException if fileName is invalid
	 */
	public void loadFacultyFromFile(String fileName) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}
	
	/**
	 * Adds a Faculty to the directory.  Returns true if the faculty member is added and false if
	 * the faculty member is unable to be added because their id matches another faculty member's id.
	 * 
	 * This method also hashes the faculty's password for internal storage.
	 * 
	 * @param firstName faculty's first name
	 * @param lastName faculty's last name
	 * @param id faculty's id
	 * @param email faculty's email
	 * @param password faculty's password
	 * @param repeatPassword faculty's repeated password
	 * @param maxCourses faculty's max courses.
	 * @return true if added
	 * @throws IllegalArgumentException if password or repeatPassword are invalid
	 * @throws IllegalArgumentException if construction of the Faculty fails
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password, String repeatPassword, int maxCourses) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || "".equals(password) || "".equals(repeatPassword)) {
			throw new IllegalArgumentException("Invalid password");
		}
		
		hashPW = hashString(password);
		repeatHashPW = hashString(repeatPassword);
		
		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		//If an IllegalArgumentException is thrown, it's passed up from Faculty
		//to the GUI
		Faculty faculty = null;
		if (maxCourses < Faculty.MIN_COURSES || maxCourses > Faculty.MAX_COURSES) {
			faculty = new Faculty(firstName, lastName, id, email, hashPW);
		} else {
			faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
		}
				
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty f = facultyDirectory.get(i);
			if (f.getId().equals(faculty.getId())) {
				return false;
			}
		}
		return facultyDirectory.add(faculty);
	}
	
	/**
	 * Hashes a String according to the SHA-256 algorithm, and outputs the digest in base64 encoding.
	 * This allows the encoded digest to be safely copied, as it only uses [a-zA-Z0-9+/=].
	 * 
	 * @param toHash the String to hash 
	 * @return the encoded digest of the hash algorithm in base64
	 * @throws IllegalArgumentException if unable to hash the password
	 */
	private static String hashString(String toHash) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(toHash.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
	/**
	 * Removes the faculty with the given id from the list of faculty members with the given id.
	 * Returns true if the faculty is removed and false if the faculty member is not in the list.
	 * @param facultyId The id of the faculty member
	 * @return true if removed
	 */
	public boolean removeFaculty(String facultyId) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty f = facultyDirectory.get(i);
			if (f.getId().equals(facultyId)) {
				facultyDirectory.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns all faculty members in the directory with a column for first name, last name, and id.
	 * @return String array containing faculty first name, last name, and id.
	 */
	public String[][] getFacultyDirectory() {
		String [][] directory = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty f = facultyDirectory.get(i);
			directory[i][0] = f.getFirstName();
			directory[i][1] = f.getLastName();
			directory[i][2] = f.getId();
		}
		return directory;
	}
	
	/**
	 * Saves all faculty members in the directory to a file. If fileName is not accessible to be 
	 * written to, an IllegalArgumentException is thrown.
	 * @param fileName name of file to save students to.
	 * @throws IllegalArgumentException if unable to write to fileName
	 */
	public void saveFacultyDirectory(String fileName) {
		try {
			FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}
	
	/**
	 * Finds and returns a faculty that has a given id. If there is no faculty in the directory with that 
	 * id, the method returns null.
	 * @param id the id of the desired faculty
	 * @return the Faculty with the id, or null if there is no match
	 */
	public Faculty getFacultyById(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty faculty = facultyDirectory.get(i);
			if (id.equals(faculty.getId())) {
				return faculty;
			}
		}
		return null;
	}
}
