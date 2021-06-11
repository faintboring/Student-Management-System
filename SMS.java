// compile command: javac --module-path /Users/ziyanzeng/Downloads/javafx-sdk-16/lib --add-modules javafx.controls,javafx.graphics,javafx.media,javafx.fxml SMS.java
// run command: java --module-path /Users/ziyanzeng/Downloads/javafx-sdk-16/lib --add-modules javafx.controls,javafx.graphics,javafx.media SMS

//CS101 Final Project - Student Management System
//Author: Ziyan Zeng (zz1681@nyu.edu)

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

//import all necessary packages
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

public class SMS extends Application {

	//textfield and buttons for the first scene (student management system scene)
	private TextField tfSMS= new TextField();
	private Button btSearch = new Button("Search");
	private Button btAdd = new Button("Add");

	//text fields, text areas, scrollpane, and buttons for the second scene (student status scene)
	private TextField tfName = new TextField();
	private TextField tfID = new TextField();
	private TextArea taCourse = new TextArea();
	private ScrollPane spCourse = new ScrollPane(taCourse);
	private TextField tfBalance = new TextField();
	private TextField tfEnroll = new TextField();
	private Button btEnrollInStatus = new Button("Enroll");
	private Button btPayInStatus = new Button("Pay Tuition");
	private Button btBackInStatus = new Button("Back");

	//text fields and buttons in the third scene(Pay Tuition scene)
	private TextField tfBalanceInPay = new TextField();
	private TextField tfPay = new TextField();
	private Button btPayInPay = new Button("Make A Payment");
	private Button btBackInPay = new Button("Back");

	//text field, text area, scrollpane, and buttons in the fourth scene (Course Enrollment Scene)
	private TextArea taCourseInEnroll = new TextArea();
	private ScrollPane spCourseInEnroll = new ScrollPane(taCourseInEnroll);
	private TextField tfEnrollInEnroll = new TextField();
	private Button btEnrollInEnroll = new Button("Enroll");
	private Button btBackInEnroll = new Button("Back");

	//text area, scrollpane, text field, and buttons in the fifth scene (Students with Same Name scene)
	private TextArea taStatusTwo = new TextArea();
	private ScrollPane spStatusTwo = new ScrollPane(taStatusTwo);
	private TextField tfIDStatusTwo = new TextField();
	private Button btRefineSearch = new Button("Pinpoint a Student");
	private Button btBackInStatusTwo = new Button("Back");

	//array lists to store students and students with same names (which is filtered out from the student list)
	private ArrayList<Student> studentList = new ArrayList<Student>();
	private ArrayList<Student> studentsWithSameName = new ArrayList<Student>();

	//store the ID, course, and Name for the current student object we are dealing with
	private String currentID;
	private String currentCourse;
	private String currentName;

	@Override
	public void start(Stage primaryStage) {

		//student management system gridpane, used for the first scene
     	GridPane gpSMS = new GridPane();

     	//add all the necessary labels and nodes
     	gpSMS.setHgap(5);
     	gpSMS.setVgap(5);
     	gpSMS.add(new Label("Student Management System"), 0, 0);
     	gpSMS.add(new Label("Author: Ziyan Zeng"), 0, 1);
     	gpSMS.add(new Label(""), 0, 2);
     	gpSMS.add(new Label("Please Enter a Name:"), 0, 3);
     	gpSMS.add(tfSMS, 1, 3);
     	gpSMS.add(btSearch, 1, 4);
     	gpSMS.add(btAdd, 1, 5);

     	//adjust alignments
     	gpSMS.setAlignment(Pos.CENTER);
     	tfSMS.setAlignment(Pos.BOTTOM_RIGHT);
     	GridPane.setHalignment(btSearch, HPos.RIGHT);
     	GridPane.setHalignment(btAdd, HPos.RIGHT);

     	//student status gridpane, used for the second scene 
     	GridPane gpStatus = new GridPane();

     	//add all the necessary labels and nodes
     	gpStatus.setHgap(5);
     	gpStatus.setVgap(5);
     	gpStatus.add(new Label("Name:"), 0, 0);
     	gpStatus.add(tfName, 1, 0);
     	gpStatus.add(new Label("Student ID:"), 0, 1);
     	gpStatus.add(tfID, 1, 1);
     	gpStatus.add(new Label("Course Enrolled:"), 0, 2);
     	gpStatus.add(spCourse, 1, 2);
     	gpStatus.add(new Label("Balance:"), 0, 3);
     	gpStatus.add(tfBalance, 1, 3);
     	gpStatus.add(btEnrollInStatus, 1, 4);
     	gpStatus.add(btPayInStatus, 1, 5);
     	gpStatus.add(btBackInStatus, 1, 6);

     	//adjust alignments; change properties for some nodes
     	gpStatus.setAlignment(Pos.CENTER);
     	tfName.setAlignment(Pos.BOTTOM_RIGHT);
		tfID.setAlignment(Pos.BOTTOM_RIGHT);
		tfBalance.setAlignment(Pos.BOTTOM_RIGHT);
		tfName.setEditable(false);
		tfID.setEditable(false);
		taCourse.setWrapText(true);
		taCourse.setEditable(false);
		taCourse.setPrefColumnCount(20);
		taCourse.setPrefRowCount(10);
		spCourse.setFitToHeight(true);
		spCourse.setFitToWidth(true);
		spCourse.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		tfBalance.setEditable(false);
		GridPane.setHalignment(btEnrollInStatus, HPos.RIGHT);
		GridPane.setHalignment(btPayInStatus, HPos.RIGHT);
		GridPane.setHalignment(btBackInStatus, HPos.RIGHT);

		//Pay tuition gridpane, which is used for the third scene
		GridPane gpPay = new GridPane();

		//add all the necessary nodes
		gpPay.setHgap(5);
     	gpPay.setVgap(5);
		gpPay.add(new Label("Current Balance:"), 0, 0);
		gpPay.add(tfBalanceInPay, 1, 0);
		gpPay.add(new Label("Payment Amount:"), 0, 1);
		gpPay.add(tfPay, 1, 1);
		gpPay.add(btPayInPay, 1, 2);
		gpPay.add(btBackInPay, 1, 3);

		//adjust alignments; change properties for some nodes
		gpPay.setAlignment(Pos.CENTER);
		tfPay.setAlignment(Pos.BOTTOM_RIGHT);
		tfBalanceInPay.setAlignment(Pos.BOTTOM_RIGHT);
		tfBalanceInPay.setEditable(false);
		GridPane.setHalignment(btPayInPay, HPos.RIGHT);
		GridPane.setHalignment(btBackInPay, HPos.RIGHT);

		//course enrollment gridpane, used for the fourth scene
		GridPane gpEnroll = new GridPane();

		//add all the necessary nodes
		gpEnroll.setHgap(5);
     	gpEnroll.setVgap(5);
     	gpEnroll.add(new Label("Current Courses:"), 0, 0);
		gpEnroll.add(spCourseInEnroll, 1, 0);
		gpEnroll.add(new Label("Add a Course:"), 0, 1);
		gpEnroll.add(tfEnrollInEnroll, 1, 1);
		gpEnroll.add(btEnrollInEnroll, 1, 2);
		gpEnroll.add(btBackInEnroll, 1, 3);

		//adjust alignments; change properties for some nodes
		gpEnroll.setAlignment(Pos.CENTER);
		tfEnrollInEnroll.setAlignment(Pos.BOTTOM_RIGHT);
		taCourseInEnroll.setWrapText(true);
		taCourseInEnroll.setEditable(false);
		taCourseInEnroll.setPrefColumnCount(20);
		taCourseInEnroll.setPrefRowCount(10);
		spCourseInEnroll.setFitToHeight(true);
		spCourseInEnroll.setFitToWidth(true);
		spCourseInEnroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		GridPane.setHalignment(btEnrollInEnroll, HPos.RIGHT);
		GridPane.setHalignment(btBackInEnroll, HPos.RIGHT);

		//students with same names gridpane, used for the fifth scene
		GridPane gpStatusTwo = new GridPane();

		//add all the necessary nodes
		gpStatusTwo.setHgap(5);
		gpStatusTwo.setVgap(5);
		gpStatusTwo.add(new Label("The Status of All Students\nwith the Same Name:"), 0, 0);
		gpStatusTwo.add(taStatusTwo, 1 ,0);
		gpStatusTwo.add(new Label("To Pinpoint a Student,\nPlease Enter the ID of\n the Target Student: "), 0, 1);
		gpStatusTwo.add(tfIDStatusTwo, 1, 1);
		gpStatusTwo.add(btRefineSearch, 1, 2);
		gpStatusTwo.add(btBackInStatusTwo, 1, 3);

		//adjust alignments; change properties for some ndoes
		gpStatusTwo.setAlignment(Pos.CENTER);
		tfIDStatusTwo.setAlignment(Pos.BOTTOM_RIGHT);
		taStatusTwo.setEditable(false);
		taStatusTwo.setPrefColumnCount(20);
		taStatusTwo.setPrefRowCount(10);
		spStatusTwo.setPrefViewportWidth(150);
		spStatusTwo.setPrefViewportHeight(50);
		spStatusTwo.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		GridPane.setHalignment(btRefineSearch, HPos.RIGHT);
		GridPane.setHalignment(btBackInStatusTwo, HPos.RIGHT);

		//create the first scene and display it in the primary stage
     	Scene sceneSMS = new Scene(gpSMS, 450, 450);
     	primaryStage.setTitle("Student Management System");
     	primaryStage.setScene(sceneSMS);
     	primaryStage.show();

     	//create all four other scenes 
     	Scene sceneStatus = new Scene(gpStatus, 450, 450);
     	Scene scenePay = new Scene(gpPay, 450, 450);
     	Scene sceneEnroll = new Scene(gpEnroll, 450, 450);
     	Scene sceneStatusTwo = new Scene(gpStatusTwo, 450, 450);

     	//the add button in the student management system scene
     	btAdd.setOnAction(e -> {

     		//if user entered something, then we create a new student object with the name provided by the user
     		//then clear the text field
     		if (!((tfSMS.getText()).equals(""))) {
     			Student stu = new Student(tfSMS.getText());
     		    studentList.add(stu);
     		    tfSMS.clear();
     		}
     	});

     	//the search button in the student management system scene
     	btSearch.setOnAction(e -> {
     		//store the name provided by the user to the current name variable;
     		currentName = tfSMS.getText();

     		//if there is a student with an unique name
     		if (numberOfStudent(tfSMS.getText()) == 1) {
     			//go through the list to find the student
     			for (int i = 0; i < studentList.size(); i++) {
     				//find the student with the same name
     				if (((studentList.get(i)).getName()).equals(tfSMS.getText())) {

     					//get this student object's name, id, course, and balance, and assign these data into the corresponding text field
     					tfName.setText((studentList.get(i)).getName());
     					tfID.setText((studentList.get(i)).getID());
     					tfBalance.setText(Integer.toString((studentList.get(i)).viewBalance()));
     					taCourse.setText((studentList.get(i)).getCourseEnrolled());

     					//store the id and the coruses of the student object we are currently dealing with
     					currentID = (studentList.get(i)).getID();
     					currentCourse = (studentList.get(i)).getCourseEnrolled();
     				}
     			}

     		    //after found the student, clear the text field for the first scene and display the status
     		    tfSMS.clear();
     	        primaryStage.setTitle("Student Status");
		        primaryStage.setScene(sceneStatus);
                primaryStage.show();
     		}
     		//if there are more than one student shara a particular name (there are students with same name)
     		else if (numberOfStudent(tfSMS.getText()) > 1) {

     			//call the formulate status function to display information of all the students
     			formulateStatus();

     		    //after found all students, clear the text field in the first scene and display the status
     		    tfSMS.clear();
     	        primaryStage.setTitle("Status for Students with Same Names");
		        primaryStage.setScene(sceneStatusTwo);
                primaryStage.show();
     		}
     	});

     	//the back button in the student status scene
     	btBackInStatus.setOnAction(e -> {
     		//clear all the materials in the text fields and text areas. Then back to the student management system scene
     		tfName.clear();
     		tfID.clear();
     		tfBalance.clear();
     		taCourse.clear();
     		primaryStage.setTitle("Student Management System");
		    primaryStage.setScene(sceneSMS);
		    primaryStage.show();
     	});

     	//the pay buttion in the student status scene
     	btPayInStatus.setOnAction(e -> {
     		//store the current ID before go to the next scene
     		currentID = tfID.getText();

     		//copy the current balance to the pay tuition scene; and then set the scene to the pay tuition scene
     		tfBalanceInPay.setText(tfBalance.getText());
     		primaryStage.setTitle("Pay Tuition");
		    primaryStage.setScene(scenePay);
		    primaryStage.show();

     	});

     	//the back button in the pay tuition scene
     	btBackInPay.setOnAction(e -> {
     		//clear the textfields in the tuition payment scene and set the scene to the student status scene
     		tfBalanceInPay.clear();
     		tfPay.clear();
     		primaryStage.setTitle("Student Status");
		    primaryStage.setScene(sceneStatus);
		    primaryStage.show();
     	});

     	//the pay button in the tuition payment scene
     	btPayInPay.setOnAction(e -> {
     		//if the user entered something
     		if (!(tfBalanceInPay.equals(""))) {

     			//call the makePayment function and clear the text field of payment amount
     			makePayment();
     			tfPay.clear();
     		}
     	});

     	//the enroll buttion in the student status scene
     	btEnrollInStatus.setOnAction(e -> {

     		//before go to course enrollment page, store the current ID
     		currentID = tfID.getText();

     		//since we stored the id of the student object we are dealing with in the currentID variable
     		//we go through the student list to see whether we have an object with the currentID
     		//if we find it, we display the courses of this student object in the text area of the course enrollment scene
     		for (int i = 0; i < studentList.size(); i++) {
     			if (((studentList.get(i)).getID()).equals(currentID)) {
     				taCourseInEnroll.setText((studentList.get(i)).getCourseEnrolled());
     			}
     		}

     		//then change the scene to the coruse enrollment scene
     		primaryStage.setTitle("Course Enrollment");
		    primaryStage.setScene(sceneEnroll);
		    primaryStage.show();
     	});

     	//the back button in the course enrollment page
     	btBackInEnroll.setOnAction(e -> {

     		//once we back to the student status scene, we clear all the text fields and text areas in the course enrollment scene
     		taCourseInEnroll.clear();
     		tfEnrollInEnroll.clear();

     		//then we change the scene to the student status
     		primaryStage.setTitle("Student Status");
		    primaryStage.setScene(sceneStatus);
		    primaryStage.show();
     	});

     	//the enroll button in the course enrollment page
     	btEnrollInEnroll.setOnAction(e -> {
               //if the user entered a course, we call the addACourse function to add the coruse; then clear the text field
               if (!((tfEnrollInEnroll.getText()).equals(""))) {
                   addACourse();
                   tfEnrollInEnroll.clear();
               }
     	});

     	//the back button in the fifth scene, which is the scene for students with same names
     	btBackInStatusTwo.setOnAction(e -> {

     		//clear all the text fields and text areas; also clear the studentWithSameName array list
     		tfIDStatusTwo.clear();
     		taStatusTwo.clear();
     		studentsWithSameName.clear();

     		//then set the scene to the student management system
     		primaryStage.setTitle("Student Management System");
		    primaryStage.setScene(sceneSMS);
		    primaryStage.show();
     	});

     	//the refine research button in the fifth scene
     	btRefineSearch.setOnAction(e -> {

     		//store the ID of the student object that we are currently dealing with
     		currentID = tfIDStatusTwo.getText();

     		//if the user entered something
     		if (!(currentID.equals(""))) {

     			//go through the student with same name array list
     			for (int i = 0; i < studentsWithSameName.size(); i++) {

     				//if we can find a student object having the same ID as what we previously stored in the currentID variable
     				if (currentID.equals((studentsWithSameName.get(i)).getID())) {

     					//change the name, id, course, balance in the status page to the counterparts of this student object
     					tfName.setText((studentsWithSameName.get(i)).getName());
     					tfID.setText((studentsWithSameName.get(i)).getID());
     					taCourse.setText((studentsWithSameName.get(i)).getCourseEnrolled());
     					tfBalance.setText(Integer.toString((studentsWithSameName.get(i)).viewBalance()));
     				}
     			}

     			//then clear the textfield, textarea, and studentWithSameName array list; and go to the student status scene
     		    tfIDStatusTwo.clear();
     		    taStatusTwo.clear();
     		    studentsWithSameName.clear();
     		    primaryStage.setTitle("Student Status");
     		    primaryStage.setScene(sceneStatus);
     		    primaryStage.show();
     	    }
        });
	}

	//formulate status for different students with same name
	private void formulateStatus() {
		
		//add all student objects with the samename to the students with same name array list
		for (int i = 0; i < studentList.size(); i++) {
			if (currentName.equals((studentList.get(i)).getName())) {
				studentsWithSameName.add(studentList.get(i));
			}
		}

		//a stringbuilder to store all the status infomation
		StringBuilder displayAllStatus = new StringBuilder(0);

		//go through the studentWithSameName list
		for (int j = 0; j < studentsWithSameName.size(); j++) {
			//append the student number (indicate the number of student with same names), student name, student id (which are unique)
			displayAllStatus.append("Student #" + Integer.toString(j + 1) + "\n");
			displayAllStatus.append("Name: " + (studentsWithSameName.get(j)).getName() + "\n");
			displayAllStatus.append("Student ID: " + (studentsWithSameName.get(j)).getID() + "\n");

			//if the student has no course enrolled or just enrolled one course
			if (((studentsWithSameName.get(j)).getCourseEnrolled()).equals("No Course Enrolled Yet") || (studentsWithSameName.get(j)).amountOfCourses() == 1) {
				//we directly append the courses
				displayAllStatus.append("Course Enrolled: " + (studentsWithSameName.get(j)).getCourseEnrolled() + "\n");
			}
			//if the student has more than two courses enrolled, we use version two of the getCourseEnroll function, which will add some spaces between courses such that we have a better format
			else {
				displayAllStatus.append("Course Enrolled: " + (studentsWithSameName.get(j)).getCourseEnrolledV2());
			}

			//then we append the balance
			displayAllStatus.append("Balance: " + Integer.toString((studentsWithSameName.get(j)).viewBalance()) + "\n" + "\n");
		}

		//after appended all the necessary information, we set the text of the textarea to the string representation of the finished stringbuilder
		taStatusTwo.setText(displayAllStatus.toString());
	}

	//add a course function, which is used in the course enrollment scene
	private void addACourse() {

		//store user's input for the intended new course
		String newCourse = tfEnrollInEnroll.getText();

		//then go through the student list
		for (int i = 0; i < studentList.size(); i++) {
			//since in the pay button in the status scene we stored the current ID, we use it directly here
			//if we find a student in the student list with the same id
     		if (((studentList.get(i)).getID()).equals(currentID)) {

     			//we call the enrollCourse function to enroll the intended course
     			(studentList.get(i)).enrollCourse(newCourse);

     			//then store the current coruse into currentCourse variable; and display current coruse in the textareas in the enrollment scene and the status scene
     			currentCourse = (studentList.get(i)).getCourseEnrolled();
     			taCourseInEnroll.setText(currentCourse);
     			taCourse.setText(currentCourse);
     		}
        }
	}

	//make a payment in the Pay Tuition scene
	private void makePayment() {
		//a variable used to store the amount the user wants to pay
		int payAmount = 0;

		//if the user entered something
		if (!((tfPay.getText()).equals(""))) {
			//we store the user's intended amount into the payAmount variable
			payAmount = Integer.parseInt(tfPay.getText());
		}

        //go through the student list to change the current student object's balance
		for (int i = 0; i < studentList.size(); i++) {

			//if we find a student with the same ID
			if (currentID.equals((studentList.get(i)).getID())) {

				//we make a payment by calling the payTuition function
				(studentList.get(i)).payTuition(payAmount);

				//then we change the textfields in both the pay tuition scene and the status scene
				tfBalanceInPay.setText(Integer.toString((studentList.get(i)).viewBalance()));
				tfBalance.setText(Integer.toString((studentList.get(i)).viewBalance()));
			}
		}	
	}

	//check whether there are students with same names
	private int numberOfStudent(String name) {
		//a variable used to store number of students with same names
		int number = 0;

		//we go through the student list
		for (int i = 0; i < studentList.size(); i++) {

			//if the name given is equal to one of the names in the student list
			if (name.equals((studentList.get(i)).getName())) {

				//add the number variable by 1
				number = number + 1;
			}
		}
		return number;
	}

	//the necessary main function used to start the program
	public static void main (String[] args) {
		Application.launch(args);
	}

}