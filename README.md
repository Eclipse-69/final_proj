Employee Records Management System
Overview
The Employee Records Management System is a JavaFX-based desktop application designed to manage employee records, including personal information, job details, and performance evaluations. The system supports two user roles: Admin and User. Admins can create, update, and delete employee records, while regular users can view and edit their own profiles. The application uses an SQLite database for persistent storage and provides a user-friendly interface for efficient record management.
Features

User Authentication: Secure login for admins and users with role-based access control.
Admin Features:
View a table of all employee records.
Create new employee records.
Update existing employee details.
Delete employee records.


User Features:
View personal profile details (username, position, email, age, resume).
Edit personal information (username, email, password, resume).


Database Integration: Uses SQLite to store employee data securely.
Intuitive UI: Built with JavaFX for a responsive and modern interface.

Technologies Used

Java: Core programming language.
JavaFX: For building the graphical user interface.
SQLite: Lightweight database for storing employee records.
JDBC: For database connectivity.

Setup Instructions

Prerequisites:

Java Development Kit (JDK) 8 or higher.
JavaFX SDK (included in JDK 8; for later versions, install separately).
SQLite JDBC driver.
An IDE like IntelliJ IDEA or Eclipse with JavaFX support.


Database Setup:

Ensure SQLite is installed.

Create a database file named InfoSystem.db in C:\Users\Warlord\.

Create a users table with the following schema:
CREATE TABLE "users" (
	"idusers"	INTEGER NOT NULL,
	"password"	TEXT NOT NULL,
	"firstname"	TEXT NOT NULL UNIQUE,
	"lastname"	TEXT NOT NULL UNIQUE,
	"username"	TEXT NOT NULL UNIQUE,
	"position"	TEXT NOT NULL,
	"gender"	TEXT NOT NULL,
	"email"	TEXT NOT NULL DEFAULT '@gmail.com',
	"age"	INTEGER,
	"resume"	TEXT,
	"job_details"	TEXT,
	PRIMARY KEY("idusers" AUTOINCREMENT)
);



Project Setup:

Clone the repository or copy the project files.
Add the SQLite JDBC driver to your project’s dependencies.
Configure JavaFX in your IDE (set VM options for JavaFX if using JDK 11+).


Running the Application:

Open the project in your IDE.
Run the Main class (not provided in the code; ensure it initializes JavaFX and loads hello-view.fxml).
Log in using credentials stored in the users table (e.g., username: "admin", password: "admin123" for admin access).



Usage

Login:
Enter your username and password.
Admins are directed to the admin menu; users go to their profile view.


Admin Menu:
View all employee records in a table.
Select a record to edit fields or delete.
Click "Create New User" to add a new employee.


User Menu:
View your profile details.
Click "Edit" to modify username, email, password, or resume.
Save or cancel changes.



Project Structure

HelloController.java: Handles login functionality and redirects to admin or user menus.
userMenuController.java: Manages the user profile view and editing functionality.
adminMenuController.java: Controls admin features for managing employee records.
adminCreatUserController.java: Handles the creation of new employee records.
DatabaseConnection.java: Manages SQLite database connectivity.
FXML Files:
hello-view.fxml: Login screen.
adminMenu.fxml: Admin dashboard.
userMenu.fxml: User profile view.
adminCreatUser.fxml: New user creation form.



Notes

Ensure the database path (C:\Users\Warlord\InfoSystem.db) matches your environment.
Passwords are stored in plain text (consider hashing for production use).
The application assumes the existence of FXML files not provided in the code.

Future Improvements

Implement password hashing for enhanced security.
Add performance evaluation features (e.g., ratings, comments).
Support file uploads for resumes.
Add input validation and error alerts in the UI.

