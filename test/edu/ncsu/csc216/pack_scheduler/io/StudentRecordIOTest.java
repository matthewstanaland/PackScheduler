package edu.ncsu.csc216.pack_scheduler.io;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Base64;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Tests the static methods in StudentRecordIO that handle file input and output. The writing method stores 
 * student record information in a specific format that can be read in by the reading method.
 */
class StudentRecordIOTest {
    
    /** The expected file from storing the validStudent records. */
    private static final String EXPECTED_FILE = "test-files/expected_full_student_records.txt";
    /** The actual file that the validStudent records are stored in. */
    private static final String ACTUAL_FILE = "test-files/student_records.txt";
    /** The file containing invalid student records. */
    private static final String INVALID_FILE = "test-files/invalid_student_records.txt";
    /** The output file that test results will be written to. */
    private static final String OUTPUT_FILE = "test-files/output-student_records.txt";
    /** A file pathname that does not exist. */
    private static final String DNE_FILE = "test-files/not-existant-file.txt";

    /** The zeroth valid student record. */
    private String validStudent0 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
    /** The first valid student record. */
    private String validStudent1 = "Lane,Berg,lberg,sociis@non.org,pw,14";
    /** The second valid student record. */
    private String validStudent2 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
    /** The third valid student record. */
    private String validStudent3 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
    /** The fourth valid student record. */
    private String validStudent4 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
    /** The fifth valid student record. */
    private String validStudent5 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
    /** The sixth valid student record. */
    private String validStudent6 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
    /** The seventh valid student record. */
    private String validStudent7 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";
    /** The eight valid student record. */
    private String validStudent8 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
    /** The ninth valid student record. */
    private String validStudent9 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";

    /** A collection of valid student records. Sorted alphabetically by last name. */
    private String [] validStudents = {validStudent0, validStudent1, validStudent2, validStudent3, validStudent4,
            validStudent5, validStudent6, validStudent7, validStudent8, validStudent9};

    /** "pw", hashed using HASH_ALGORITHM */
    private String hashPW;
    
    /** The SHA-256 hash algorithm name. */
    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * Ensures the actual file is prepared and hashes all of the passwords in the validStudents array before each test.
     * This avoids storing student passwords in plain text and therefore increases safety.
     */
    @BeforeEach
    public void setUp() {
        // copy expected file values into actual file
        try {
            Scanner expected = new Scanner(new FileInputStream(EXPECTED_FILE));
            PrintStream actual = new PrintStream(new File(ACTUAL_FILE));
            while (expected.hasNextLine()) {
                actual.println(expected.nextLine());
            }
            expected.close();
            actual.close();
        } catch (FileNotFoundException e) {
            fail("Unable to read files during setup");
        }
        
        // hash all passwords
        try {
            String password = "pw";
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(password.getBytes());
            hashPW = Base64.getEncoder().encodeToString(digest.digest());
            
            for (int i = 0; i < validStudents.length; i++) {
                validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
            }
        } catch (NoSuchAlgorithmException e) {
            fail("Unable to create hash during setup");
        }
    }

    /**
     * Tests the readStudentRecords method in StudentRecordIO. Evaluates that valid students are read in correctly, 
     * invalid students are not read in, and that a FileNotFoundException is thrown when the method is passed a 
     * non-existant file.
     */
    @Test
    void testReadStudentRecords() {
        SortedList<Student> testValidStudents = new SortedList<Student>();
        SortedList<Student> testInvalidStudents = new SortedList<Student>();
        
        try {
            testValidStudents = StudentRecordIO.readStudentRecords(ACTUAL_FILE);
            testInvalidStudents = StudentRecordIO.readStudentRecords(INVALID_FILE);
        } catch (FileNotFoundException e) {
            fail("Unable to read input file.");
        }
        
        // check state of all valid students that are read in
        for (int i = 0; i < testValidStudents.size(); i++) {
            assertEquals(validStudents[i], testValidStudents.get(i).toString());
        }
        
        // ensure that no invalid students are read in
        assertEquals(0, testInvalidStudents.size());
        assertThrows(FileNotFoundException.class, () -> StudentRecordIO.readStudentRecords(DNE_FILE));
    }

    /**
     * Tests the writeStudentRecords method in StudentRecordIO. Evaluates that writing a list of students to a file 
     * results in the expected output and that an IOException is thrown if a file can't be written to.
     */
    @Test
    void testWriteStudentRecords() {
        try {
            SortedList<Student> testValidStudents = StudentRecordIO.readStudentRecords(ACTUAL_FILE);
            StudentRecordIO.writeStudentRecords(OUTPUT_FILE, testValidStudents);
            
            // check that an IOException is thrown when the output file is not found.
            Exception exception = assertThrows(IOException.class, 
                    () -> StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", testValidStudents));
            assertEquals("/home/sesmith5/actual_student_records.txt (No such file or directory)", exception.getMessage());
        } catch (IOException e) {
            fail("Unable to write to valid file.");
        }
        
        // the test checkFiles fails if the files are different in any way
        checkFiles(EXPECTED_FILE, OUTPUT_FILE);
    }
    
    /**
     * Helper method to compare two files for the same contents
     * @param expFile expected output
     * @param actFile actual output
     */
    private void checkFiles(String expFile, String actFile) {
        try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
             Scanner actScanner = new Scanner(new FileInputStream(actFile));) {
            
            while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
                String exp = expScanner.nextLine();
                String act = actScanner.nextLine();
                assertEquals(exp, act, "Expected: " + exp + " Actual: " + act); 
                //The third argument helps with debugging!
            }
            if (expScanner.hasNextLine()) {
                fail("The expected results expect another line " + expScanner.nextLine());
            }
            if (actScanner.hasNextLine()) {
                fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
            }
            
            expScanner.close();
            actScanner.close();
        } catch (IOException e) {
            fail("Error reading files.");
        }
    }

}
