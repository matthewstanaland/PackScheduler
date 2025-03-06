package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Supports reading in student records from a file and writing student records out to a file.
 * A student record contains a line for each student, with their first name, last name, id, email,
 * hashed password, and max number of credits they can take in a semester. The student records are
 * comma separated for values and line separated for different students.
 * @author Dreese Abdelilah
 * @author Ruairi Gallagher
 * @author Kergan Sanderson
 */
public class StudentRecordIO {

    /**
     * Reads in student records from a file to create Students. Each student has information recorded on one 
     * line of the input file. Students are read in from a file and sorted alphabetically by last name. 
     * If the file is unable to be opened, a FileNotFoundException is thrown.
     * @param fileName the name of the file containing the student record
     * @return a list of Students created from the information in the record
     * @throws FileNotFoundException if the file is unable to be opened
     */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Student> students = new SortedList<Student>();
		
		while (fileReader.hasNextLine()){
		    try {
		        Student student = processStudent(fileReader.nextLine());
		        students.add(student);
		    } catch (IllegalArgumentException e) {
		        // Invalid line because a student could not be created
		        continue; // this line is to prevent a PMD for an empty catch block
		    }
		}
		    
		fileReader.close();
		return students;
	}
	
	/**
	 * Reads in the information from a student record on a single student and creates a 
	 * Student with that information. If the record is not in the right format, an IllegalArgumentException
	 * is thrown.
	 * @param studentLine the line containing the student's information
	 * @return a Student created with the line information
	 * @throws IllegalArgumentException if input is not of the expected type or there is not enough input
	 */
	private static Student processStudent(String studentLine) {
	    Scanner input = new Scanner(studentLine);
	    input.useDelimiter(",");
	    
	    try {
	        // read in tokens
	        String firstName = input.next();
	        String lastName = input.next();
	        String id = input.next();
	        String email = input.next();
	        String hashPW = input.next();
	        int maxCredits = input.nextInt();
	        
	        input.close();
	        return new Student(firstName, lastName, id, email, hashPW, maxCredits);
	        
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
	 * Writes the given record of Students to a file. Students are written to the file in alphabetical order. 
	 * If the write is unsuccessful, an IOException is thrown.
	 * @param fileName file to write record of students to
	 * @param studentDirectory list of students to write
	 * @throws IOException if the file cannot be written to
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		
		for (int i = 0; i < studentDirectory.size(); i++) {
		    fileWriter.println(studentDirectory.get(i).toString());
		}
		
		fileWriter.close();
	}

}
