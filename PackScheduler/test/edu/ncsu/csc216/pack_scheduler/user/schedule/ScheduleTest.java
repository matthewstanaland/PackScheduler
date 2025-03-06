package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the Schedule class.
 */
public class ScheduleTest {

	/**
	 * Tests the constructor for the Schedule.
	 */
	@Test
	public void testSchedule() {
		Schedule schedule = new Schedule();
		String[][] scheduleArray = schedule.getScheduledCourses();
		
		assertEquals("My Schedule", schedule.getTitle());
		assertEquals(0, scheduleArray.length);
	}
	
	/**
	 * Tests addCourseToSchedule
	 */
	@Test
	public void testAddCourseToSchedule() {
		Schedule schedule = new Schedule();
		String[][] scheduleArray1 = schedule.getScheduledCourses();
		
		assertEquals(0, scheduleArray1.length);
		
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 1100, 1150);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		Course c3 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "001", 3, "tmbarnes", 10, "MWF", 935, 1025);
		assertTrue(schedule.addCourseToSchedule(c1));
		assertTrue(schedule.addCourseToSchedule(c2));
		assertTrue(schedule.addCourseToSchedule(c3));
		String[][] scheduleArray2 = schedule.getScheduledCourses();
		
		assertEquals(3, scheduleArray2.length);
		
		Course c4 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 1100, 1150);
		Course c5 = new Course("CSC316", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 1100, 1150);
		
		assertThrows(IllegalArgumentException.class, () -> schedule.addCourseToSchedule(c4));
		assertThrows(NullPointerException.class, () -> schedule.addCourseToSchedule(null));
		Exception e = assertThrows(IllegalArgumentException.class, () -> schedule.addCourseToSchedule(c5));
		assertEquals("The course cannot be added due to a conflict.", e.getMessage());
	}
	
	/**
	 * Tests removeCourseFromSchedule
	 */
	@Test
	public void testRemoveCourseFromSchedule() {
		Schedule schedule = new Schedule();
		String[][] scheduleArray1 = schedule.getScheduledCourses();
		
		assertEquals(0, scheduleArray1.length);
		
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 1100, 1150);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		Course c3 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "001", 3, "tmbarnes", 10, "MWF", 935, 1025);
		assertTrue(schedule.addCourseToSchedule(c1));
		assertTrue(schedule.addCourseToSchedule(c2));
		assertTrue(schedule.addCourseToSchedule(c3));
		String[][] scheduleArray2 = schedule.getScheduledCourses();
		
		assertEquals(3, scheduleArray2.length);
		assertTrue(schedule.removeCourseFromSchedule(c3));
		
		String[][] scheduleArray3 = schedule.getScheduledCourses();
		
		assertEquals(2, scheduleArray3.length);
		assertFalse(schedule.removeCourseFromSchedule(c3));
	}
	
	/**
	 * Tests resetSchedule.
	 */
	@Test
	public void testResetSchedule() {
		// create a new schedule and add three courses to it
		Schedule schedule = new Schedule();
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 1100, 1150);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		Course c3 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "001", 3, "tmbarnes", 10, "MWF", 935, 1025);
		schedule.addCourseToSchedule(c1);
		schedule.addCourseToSchedule(c2);
		schedule.addCourseToSchedule(c3);
		
		assertEquals(3, schedule.getScheduledCourses().length);
		
		// reset the schedule
		schedule.resetSchedule();
		assertEquals(0, schedule.getScheduledCourses().length);
	}
	
	/**
	 * Tests getScheduledCourses.
	 */
	@Test
	public void testGetScheduledCourses() {
		// create a new schedule and add three courses to it
		Schedule schedule = new Schedule();
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 1100, 1150);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		Course c3 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "001", 3, "tmbarnes", 10, "MWF", 935, 1025);
		schedule.addCourseToSchedule(c1);
		schedule.addCourseToSchedule(c2);
		schedule.addCourseToSchedule(c3);
		
		String[][] courses = schedule.getScheduledCourses();
		
		// check the contents of the courses
		assertAll( // c1
				() -> assertEquals("CSC116", courses[0][0]),
				() -> assertEquals("001", courses[0][1]),
				() -> assertEquals("Intro to Programming - Java", courses[0][2]),
				() -> assertEquals("MW 11:00AM-11:50AM", courses[0][3]),
				() -> assertEquals("10", courses[0][4])
		);
		
		assertAll( // c2
				() -> assertEquals("CSC216", courses[1][0]),
				() -> assertEquals("001", courses[1][1]),
				() -> assertEquals("Software Development Fundamentals", courses[1][2]),
				() -> assertEquals("TH 1:30PM-2:45PM", courses[1][3]),
				() -> assertEquals("10", courses[0][4])
		);
		
		assertAll( // c3
				() -> assertEquals("CSC226", courses[2][0]),
				() -> assertEquals("001", courses[2][1]),
				() -> assertEquals("Discrete Mathematics for Computer Scientists", courses[2][2]),
				() -> assertEquals("MWF 9:35AM-10:25AM", courses[2][3]),
				() -> assertEquals("10", courses[0][4])
		);
	}
	
	/**
	 * Tests setTitle.
	 */
	@Test
	public void testSetTitle() {
		Schedule schedule = new Schedule();
		assertEquals("My Schedule", schedule.getTitle());
		schedule.setTitle("The Super Awesome Best Schedule Ever");
		assertEquals("The Super Awesome Best Schedule Ever", schedule.getTitle());
		
		// check the null case
		Exception e = assertThrows(IllegalArgumentException.class, () -> schedule.setTitle(null));
		assertEquals("Title cannot be null.", e.getMessage());
		assertEquals("The Super Awesome Best Schedule Ever", schedule.getTitle());
		
	}
	
	/**
	 * Tests getScheduleCredits.
	 */
	@Test
	public void testGetScheduleCredits() {
		Schedule schedule = new Schedule();
		// test returning if there are no courses
		assertEquals(0, schedule.getScheduleCredits());
		
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 4, "jdyoung2", 10, "MW", 1100, 1150);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		Course c3 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "001", 2, "tmbarnes", 10, "MWF", 935, 1025);
		
		// test different values of credits with different value courses
		schedule.addCourseToSchedule(c1);
		assertEquals(4, schedule.getScheduleCredits());
		schedule.addCourseToSchedule(c2);
		assertEquals(7, schedule.getScheduleCredits());
		schedule.addCourseToSchedule(c3);
		assertEquals(9, schedule.getScheduleCredits());
	}
	
	/**
	 * Tests canAdd method.
	 */
	@Test
	public void testCanAdd() {
		Schedule schedule = new Schedule();
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 4, "jdyoung2", 10, "MW", 1100, 1150);
		Course c2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		Course c3 = new Course("CSC226", "Discrete Mathematics for Computer Scientists", "001", 2, "tmbarnes", 10, "MWF", 935, 1025);
		
		Course c1Duplicate = new Course("CSC116", "Intro to Programming - Java", "001", 4, "jdyoung2", 10, "MW", 1100, 1150);
		Course c2Conflict = new Course("CSC316", "Super Cool Class", "001", 4, "jdyoung2", 10, "TH", 1430, 1545);
		
		// test valid additions
		assertTrue(schedule.canAdd(c1));
		schedule.addCourseToSchedule(c1);
		assertTrue(schedule.canAdd(c2));
		schedule.addCourseToSchedule(c2);
		assertTrue(schedule.canAdd(c3));
		schedule.addCourseToSchedule(c3);
		
		// test false cases
		assertFalse(schedule.canAdd(null));
		assertFalse(schedule.canAdd(c1Duplicate));
		assertFalse(schedule.canAdd(c2Conflict));
	}

}
