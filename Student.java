//CS101 Final Project
//Student class for the Student Management System

/* Project Proposal:
 * <<Student Management System>>
 * This project is also a simple Student Management System. 
 * In this project you will be learning how to add new students to the database, 
 * how to generate a 5 digit unique studentID for each student, 
 * how to enroll students in the given courses. 
 * Also, you will be implementing the following operations enroll, view balance, pay tuition fees, show status, etc. 
 * The status will show all the details of the student including name, id, courses enrolled and balance.
 * This is one of the best projects to implement the OOPS concepts. 
 *
 * Extra requirement from the course instructor:
 * Add a GUI; Input and output for GUI
 */


//import the arraylist package
import java.util.ArrayList;

public class Student {

	//necessary data fields, including name, id, course, balance, and current student amount
	private String name;
	private String studentID;
	private ArrayList<String> course = new ArrayList<>();
	private int balance = 30000;
	private static int currentStudent = 0;

	//the constructor only needs a name, which will be provided by the user;
	Student(String name) {
		this.name = name;

		//the currentStudent variable will be added with 1 when a new student object is created
		currentStudent = currentStudent + 1;

		//then we generate the 5-digit unique ID for the student object
		this.studentID = idGenerator();
	}

	//generate a five digit unique ID
	public String idGenerator() {
		//a stringbuilder used to store the generated id
		StringBuilder id = new StringBuilder(0);

		//the following if/else if statement is attaching the 0's in front of the static currentStudent variable
		//such that we can have a five-digit unique ID for each student object
		//it also includes error handeling, preventing the id exceeds 99999 or get below 0
		if (this.currentStudent >= 0 && this.currentStudent <= 9) {
			id.append("0000" + Integer.toString(this.currentStudent));
		} else if (this.currentStudent >= 10 && this.currentStudent <= 99) {
			id.append("000" + Integer.toString(this.currentStudent));
		} else if (this.currentStudent >= 100 && this.currentStudent <= 999) {
			id.append("00" + Integer.toString(this.currentStudent));
		} else if (this.currentStudent >= 1000 && this.currentStudent <= 9999) {
			id.append("0" + Integer.toString(this.currentStudent));
		} else if (this.currentStudent >= 10000 && this.currentStudent <= 99999) {
			id.append(Integer.toString(this.currentStudent));
		} else if (this.currentStudent >= 99999) {
			id.append("Error: ID maximum reached");
		} else if (this.currentStudent < 0) {
			id.append("Error: ID cannot be negative");
		}
		return id.toString();
	}

	//a setter method for courses
	public void enrollCourse(String course) {
		(this.course).add(course);
	}

	//a getter method for courses, version one
	public String getCourseEnrolled() {
		//store the number of courses a student is taking
		int numberOfCourse = (this.course).size();

		//a string builder to store courses
		StringBuilder list = new StringBuilder(0);

		//if the student takes any amount of courses other than 0
		if (numberOfCourse != 0) {

			//we go through the course list to attach the courses, including the index of the courses (first one, second one...etc)
		    for (int i = 0; i < (this.course).size(); i++) {
		    	String index = Integer.toString(i + 1) + ". ";
		    	list.append(index);
			    list.append((this.course).get(i));
			    list.append("\n");
	    	}
		}
		//if the student does not take any course, we append the special message
		else {
			list.append("No Course Enrolled Yet");
		}
		return list.toString();
	}

	//a second version used for getting courses
	public String getCourseEnrolledV2() {

		//a string builder for courses
		StringBuilder list = new StringBuilder(0);

		//if the student takes more than one courses
		if (amountOfCourses() > 1) {
			//append the first course as usuall
			list.append("1. " + (this.course).get(0) + "\n");

			//however, start from the second course, we add some spaces before the index
			//to make the correct display format in the text area in the student with same names scene
			//other than the spaces, we append the courses as usual
			for (int i = 1; i < (this.course).size(); i++) {
				String index = Integer.toString(i + 1) + ". ";
				list.append("                                  ");
				list.append(index);
			    list.append((this.course).get(i));
			    list.append("\n");
			}
		}
		return list.toString();
	}

	//return how many courses a particular student is taking
	public int amountOfCourses() {
		return (this.course).size();
	}

	//a getter method for balance
	public int viewBalance() {
		return this.balance;
	}

	//a setter method for tuition;
	public void payTuition(int payment) {
		this.balance = this.balance - payment;
	}

	//return a student object's name
	public String getName() {
		return this.name;
	}

	//return a student object's ID
	public String getID() {
		return this.studentID;
	}

}