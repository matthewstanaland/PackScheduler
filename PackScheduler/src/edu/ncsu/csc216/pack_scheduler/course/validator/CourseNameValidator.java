package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Contains the functionality to validate or invalidate a Course name.
 * 
 * @author Kergan Sanderson
 */
public class CourseNameValidator {
    
    /** Records whether the FSM is in a valid end state. */
    private boolean validEndState;
    
    /** Records the number of letters during the FSM. */
    private int letterCount;
    
    /** Records the number of digits during the FSM. */
    private int digitCount;
    
    /** Contains the current state object that the FSM is in. */
    private State currentState;
    
    /** A final instance of the FSM's Initial State. */
    private final State stateInitial;
    
    /** A final instance of the FSM's Letter State. */
    private final State stateLetter;
    
    /** A final instance of the FSM's Digit State. */
    private final State stateNumber;
    
    /** A final instance of the FSM's Suffix State. */
    private final State stateSuffix;
    
    /**
     * Constructs a new CourseNameValidator by initializing all of the states, 
     * setting the current state to the initial state, and initializing the other 
     * instance variables to their initial values.
     */
    public CourseNameValidator() {
        // initialize progress fields
        validEndState = false;
        letterCount = 0;
        digitCount = 0;
        
        // initialize final State objects
        stateInitial = new InitialState();
        stateLetter = new LetterState();
        stateNumber = new NumberState();
        stateSuffix = new SuffixState();
        
        // set the current state to the initial state
        currentState = stateInitial;
    }
    
    /**
     * Determines whether a given Course name is valid. A Course name is valid 
     * if it starts with no more than 4 letters, and is followed by three digits. 
     * Optionally, there may be a single letter suffix after the digits.
     * 
     * @param courseName the given name to be validated
     * @return true if name is valid and false if the FSM ends in a non-final state
     * @throws InvalidTransitionException if the name contains invalid characters or is formatted incorrectly
     */
    public boolean isValid(String courseName) throws InvalidTransitionException {
        // reset all fields
        validEndState = false;
        letterCount = 0;
        digitCount = 0;
        currentState = stateInitial;
        
        // declare a temporary storage character
        char c;
        
        // iterate through each character in the name in order
        // and let the state dictate the appropriate actions
        for (int i = 0; i < courseName.length(); i++) {
            c = courseName.charAt(i);
            if (Character.isLetter(c)) {
                currentState.onLetter();
            } else if (Character.isDigit(c)) {
                currentState.onDigit();
            } else {
                currentState.onOther();
            }
        }
        
        // validEndState is true if the FSM ends with three digits in stateDigit or stateSuffix
        return validEndState;

    }
    
    /**
     * A state that the isValid FSM can be in when validating a Course name.
     * 
     * @author Kergan Sanderson
     */
    private abstract class State {
        
        /**
         * Constructs a new State. There are no instance fields associated with a State, so 
         * this is a verbose default constructor.
         */
        public State() {
            super();
        }
        
        /** 
         * Behavior of the isValid FSM for handling a letter input. 
         * @throws InvalidTransitionException if the letter transition is not valid
         */
        public abstract void onLetter() throws InvalidTransitionException;
        
        /** 
         * Behavior of the isValid FSM for handling a digit input. 
         * @throws InvalidTransitionException if the digit transition is not valid
         */
        public abstract void onDigit() throws InvalidTransitionException;
        
        /**
         * Behavior of the isValid FSM for handling non-letter non-digit input.
         * Will always throw an InvalidTransitionException.
         * @throws InvalidTransitionException input character is not valid in a course name
         */
        public void onOther() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name can only contain letters and digits.");
        }
        
    }
    
    /**
     * The initial state of the isValid FSM. Inner class of CourseNameValidator.
     * 
     * @author Kergan Sanderson
     */
    private class InitialState extends State {
        
        /**
         * Constructs a new InitialState instance for context class storage.
         */
        private InitialState() {
            super();
        }

        /**
         * Transitions the FSM to the LetterState when a letter is encountered.
         * Increments the letterCount.
         */
        @Override
        public void onLetter() {
            letterCount++;
            currentState = stateLetter;
        }
        
        /**
         * Throws an InvalidTransitionException if a digit is encountered 
         * at the start of the course name.
         * @throws InvalidTransitionException when a course name starts with a digit
         */
        @Override
        public void onDigit() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name must start with a letter.");
        }
        
    }
    
    /**
     * The letter state of the isValid FSM. Inner class of CourseNameValidator.
     * 
     * @author Kergan Sanderson
     */
    private class LetterState extends State {
        
        /** The maximum number of prefix letters a name can have. */
        private final int maxPrefixLetters = 4;
        
        /**
         * Constructs a new LetterState instance for context class storage.
         */
        private LetterState() {
            super();
        }

        /**
         * Handles the transition when a letter is encountered. 
         * Increments the letter count and checks if it exceeds the 
         * maximum allowed number of prefix letters.
         * @throws InvalidTransitionException if more than 4 letters are entered
         */
        @Override
        public void onLetter() throws InvalidTransitionException {
            letterCount++;
            if (letterCount > maxPrefixLetters) {
                throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
            }
        }
        
        /**
         * Handles the transition when a digit is encountered. 
         * Moves the FSM to the NumberState and increments digit count.
         */
        @Override
        public void onDigit() {
            digitCount++;
            currentState = stateNumber;
        }
        
    }
    
    /**
     * The digit state of the isValid FSM. Inner class of CourseNameValidator.
     * This is a valid final state.
     * 
     * @author Kergan Sanderson
     */
    private class NumberState extends State {
        
        /** The required number of digits in a Course name. */
        private final int courseNumberLength = 3;
        
        /**
         * Constructs a new NumberState instance for context class storage.
         */
        private NumberState() {
            super();
        }

        /**
         * Transitions the FSM to the SuffixState if exactly 3 digits have been entered.
         * @throws InvalidTransitionException if fewer than 3 digits are present
         */
        @Override
        public void onLetter() throws InvalidTransitionException {
            if (courseNumberLength == digitCount) {
                currentState = stateSuffix;
            } else {
                throw new InvalidTransitionException("Course name must have 3 digits.");
            }
        }
        
        /**
         * Handles the transition when a digit is encountered. 
         * Increments digit count and validates the number of digits.
         * @throws InvalidTransitionException if more than 3 digits are entered
         */
        @Override
        public void onDigit() throws InvalidTransitionException {
            digitCount++;
            if (digitCount == courseNumberLength) {
                validEndState = true;
            }
            if (digitCount > courseNumberLength) {
                throw new InvalidTransitionException("Course name can only have 3 digits.");
            }
        } 
    }
    
    /**
     * The suffix state of the isValid FSM. Inner class of CourseNameValidator.
     * This is a valid final state.
     * 
     * @author Kergan Sanderson
     */
    private class SuffixState extends State {
        
        /**
         * Constructs a new SuffixState instance for context class storage.
         */
        private SuffixState() {
            super();
        }

        /**
         * Throws an InvalidTransitionException when a letter is encountered, 
         * as only one suffix letter is allowed.
         * @throws InvalidTransitionException if more than one suffix letter is entered
         */
        @Override
        public void onLetter() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
        }
        
        /**
         * Throws an InvalidTransitionException if a digit is encountered after the suffix.
         * @throws InvalidTransitionException if digits appear after the suffix
         */
        @Override
        public void onDigit() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
        }
        
    }
    
}
