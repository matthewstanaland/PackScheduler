/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * FacultyRecordIo Tests
 */
public class FacultyRecordIOTest {

	/** The expected file from storing the validStudent records. */
    private static final String EXPECTED_FILE = "test-files/expected_full_faculty_records.txt";
    /** The actual file that the validStudent records are stored in. */
    private static final String ACTUAL_FILE = "test-files/faculty_records.txt";
    /** The file containing invalid student records. */
    private static final String INVALID_FILE = "test-files/invalid_faculty_records.txt";
    /** The output file that test results will be written to. */
    private static final String OUTPUT_FILE = "test-files/output-faculty_records.txt";
    /** A file pathname that does not exist. */
    private static final String DNE_FILE = "test-files/not-existant-file.txt";

    /** The zeroth valid faculty record. */
    private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";
    /** The first valid faculty record. */
    private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
    /** The 2 valid faculty record. */
    private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
    /** The 3 valid faculty record. */
    private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
    /** The 4 valid faculty record. */
    private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
    /** The 5 valid faculty record. */
    private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,3";
    /** The 6 valid faculty record. */
    private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,1";
    /** The 7 valid faculty record. */
    private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2";

    /** A collection of valid faculty records. Sorted alphabetically by last name. */
    private String [] validFaculty = {validFaculty0, validFaculty1, validFaculty2, validFaculty3, validFaculty4,
            validFaculty5, validFaculty6, validFaculty7};

    /** "pw", hashed using HASH_ALGORITHM */
    private String hashPW;
    
    /** The SHA-256 hash algorithm name. */
    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * Ensures the actual file is prepared and hashes all of the passwords in the validFacultys array before each test.
     * This avoids storing faculty passwords in plain text and therefore increases safety.
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
            
            for (int i = 0; i < validFaculty.length; i++) {
                validFaculty[i] = validFaculty[i].replace(",pw,", "," + hashPW + ",");
            }
        } catch (NoSuchAlgorithmException e) {
            fail("Unable to create hash during setup");
        }
    }

    /**
     * Tests the readFacultyRecords method in FacultyRecordIO. Evaluates that valid Faculty are read in correctly, 
     * invalid faculty are not read in, and that a FileNotFoundException is thrown when the method is passed a 
     * non-existant file.
     */
    @Test
    void testReadStudentRecords() {
        LinkedList<Faculty> testValidFaculty = new LinkedList<Faculty>();
        LinkedList<Faculty> testInvalidFaculty = new LinkedList<Faculty>();
        
        try {
            testValidFaculty = FacultyRecordIO.readFacultyRecords(ACTUAL_FILE);
            testInvalidFaculty = FacultyRecordIO.readFacultyRecords(INVALID_FILE);
        } catch (FileNotFoundException e) {
            fail("Unable to read input file.");
        }
        
        // check state of all valid faculty that are read in
        for (int i = 0; i < testValidFaculty.size(); i++) {
            assertEquals(validFaculty[i], testValidFaculty.get(i).toString());
        }
        
        // ensure that no invalid faculty are read in
        assertEquals(0, testInvalidFaculty.size());
        assertThrows(FileNotFoundException.class, () -> FacultyRecordIO.readFacultyRecords(DNE_FILE));
    }

    /**
     * Tests the writeFacultyRecords method in FacultyRecordIO. Evaluates that writing a list of Faculty to a file 
     * results in the expected output and that an IOException is thrown if a file can't be written to.
     */
    @Test
    void testWriteStudentRecords() {
        try {
            LinkedList<Faculty> testValidFaculty = FacultyRecordIO.readFacultyRecords(ACTUAL_FILE);
            FacultyRecordIO.writeFacultyRecords(OUTPUT_FILE, testValidFaculty);
            
            // check that an IOException is thrown when the output file is not found.
            Exception exception = assertThrows(IOException.class, 
                    () -> FacultyRecordIO.writeFacultyRecords("/home/sesmith5/actual_faculty_records.txt", testValidFaculty));
            assertEquals("/home/sesmith5/actual_faculty_records.txt (No such file or directory)", exception.getMessage());
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
