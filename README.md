PackScheduler

A Course Scheduling System for Efficient Academic Planning

Overview

PackScheduler is a course scheduling system designed to help students efficiently manage their academic schedules. Developed as part of coursework at NC State University, this system ensures conflict-free scheduling, credit validation, and persistent storage for course records.

It enforces strict course scheduling rules, allowing students to only register for courses that fit within their credit limits and available time slots.

Features

✅ Course Registration & Management – Add, remove, and modify course selections
✅ Schedule Conflict Detection – Prevents time slot overlaps
✅ Student & Faculty Management – Organize and track user schedules
✅ Data Persistence – Save and load schedules via CSV file storage
✅ Validation Rules – Ensure valid meeting times, course credit limits, and unique course sections
✅ JUnit Testing – Fully unit-tested for reliability

Technologies Used
	•	Java – Core programming language
	•	JUnit – Unit testing framework
	•	Git – Version control
	•	Eclipse – IDE used for development
	•	CSV File Parsing – Reads and writes schedules to files
	•	Maven (if applicable) – For dependency management

Installation & Setup

Prerequisites

Ensure you have:
	•	Java (JDK 11 or later)
	•	Eclipse or IntelliJ (or any preferred Java IDE)
	•	Git (if cloning the repository)

Cloning the Repository

To download and set up the project locally:

git clone https://github.com/matthewstanaland/PackScheduler.git
cd PackScheduler

Running the Application
	1.	Open the project in Eclipse or another Java IDE.
	2.	Ensure all dependencies are set up.
	3.	Run Main.java to start the scheduler.

Usage
	•	Students can:
	•	View available courses
	•	Register for courses within credit limits
	•	Export schedules as CSV files
	•	Administrators can:
	•	Add, update, or remove courses
	•	Manage student and faculty records

Testing

This project includes unit tests to ensure functionality:

mvn test

or
Run tests in JUnit through your IDE.

Future Enhancements

🔹 Integrate a GUI for better user experience
🔹 Add database support for persistent storage beyond CSV files
🔹 Automated schedule optimization to suggest the best possible schedules

License

This project is licensed under the MIT License.
