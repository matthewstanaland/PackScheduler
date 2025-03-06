package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InvalidTransitionExceptionTest {

	@Test
	public void testInvalidTransitionExceptionString() {
		InvalidTransitionException ce = new InvalidTransitionException("Custom exception message");
	    assertEquals("Custom exception message", ce.getMessage());
	}
	
	
	@Test
	public void testInvalidTransitionExceptionDefaultMessage() {
		InvalidTransitionException e = new InvalidTransitionException();
	    
	    assertEquals("Invalid FSM Transition.", e.getMessage());
	}

}
