/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Faculty Record IO class 
 */
public class FacultyRecordIO {

	 /**
     * Reads in student records from a file to create Faculty. Each Faculty has information recorded on one 
     * line of the input file. Teachers are read in from a file and sorted alphabetically by last name. 
     * If the file is unable to be opened, a FileNotFoundException is thrown.
     * @param fileName the name of the file containing the Teacher record
     * @return a list of Teachers created from the information in the record
     * @throws FileNotFoundException if the file is unable to be opened
     */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		LinkedList<Faculty> facultys = new LinkedList<Faculty>();
		
		while (fileReader.hasNextLine()){
		    try {
		        Faculty faculty = processStudent(fileReader.nextLine());
		        facultys.add(faculty);
		    } catch (IllegalArgumentException e) {
		        // Invalid line because a student could not be created
		        continue; // this line is to prevent a PMD for an empty catch block
		    }
		}
		    
		fileReader.close();
		return facultys;
	}
	
	/**
	 * Reads in the information from a Faculty record on a single Faculty member/teacher and creates a 
	 * Teacher with that information. If the record is not in the right format, an IllegalArgumentException
	 * is thrown.
	 * @param facultyLine the line containing the student's information
	 * @return a Teacher created with the line information
	 * @throws IllegalArgumentException if input is not of the expected type or there is not enough input
	 */
	private static Faculty processStudent(String facultyLine) {
	    Scanner input = new Scanner(facultyLine);
	    input.useDelimiter(",");
	    
	    try {
	        // read in tokens
	        String firstName = input.next();
	        String lastName = input.next();
	        String id = input.next();
	        String email = input.next();
	        String hashPW = input.next();
	        int maxCourses = input.nextInt();
	        
	        input.close();
	        return new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
	        
	    } catch (InputMismatchException e) {
	        input.close();
	        throw new IllegalArgumentException();
	    } catch (NoSuchElementException e) {
	        throw new IllegalArgumentException();
	    } catch (IllegalArgumentException e) {
	        throw new IllegalArgumentException();
	    }
	}

	/**
	 * Writes the given record of Teachers to a file. Teachers are written to the file in alphabetical order. 
	 * If the write is unsuccessful, an IOException is thrown.
	 * @param fileName file to write record of Teachers to
	 * @param facultyDirectory list of Teachers to write
	 * @throws IOException if the file cannot be written to
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		
		for (int i = 0; i < facultyDirectory.size(); i++) {
		    fileWriter.println(facultyDirectory.get(i).toString());
		}
		
		fileWriter.close();
	}

}
