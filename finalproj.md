# User Management System (JavaFX)

## **Introduction**
This project is a **JavaFX application** for managing user data, integrated with a **database**. It provides two main interfaces:  
* **User Menu** (`userMenuController`) – Allows users to view and edit their profile.  
* **Admin Menu** (`adminMenuController` & `adminCreatUserController`) – Enables administrators to manage users (view, edit, create, delete).  

This documentation explains the **algorithms**, **data structures**, **functions**, and **challenges** of implementation.

---

## **System Overview**
The application consists of **three main JavaFX controller classes**:  
1. `userMenuController` – Manages the user profile interface.  
2. `adminMenuController` – Handles the admin interface for managing users.  
3. `adminCreatUserController` – Supports creating new users.  

User data is stored in a **relational database** (table `users`) with fields:  
`idusers`, `username`, `firstname`, `lastname`, `email`, `password`, `position`, `gender`, `age`, `resume`, `job_details`.

---

## **Key Algorithms**

### **1. Loading User Data** (`userMenuController.loadUserData`)  
**Goal:** Display the logged-in user’s information.  
**Steps:**  
* Retrieve the username from `currentUsernameProperty`.  
* Establish a database connection via `DatabaseConnection.getConnection`.  
* Execute an SQL `SELECT` query to fetch user data.  
* Populate UI labels (`usernameLabel`, `positionLabel`, etc.) from the result set.  
* Handle missing/null values by displaying `"N/A"`.

---

### **2. Updating User Data** (`userMenuController.updateUserData`)  
**Goal:** Save edited user data.  
**Steps:**  
* Collect data from input fields into an `ArrayList<String>`.  
* Validate required fields (name, email, resume).  
* Prepare and execute an `UPDATE` SQL query.  
* Use the current password if the new password is empty.  
* Switch the interface back to view mode after saving.

---

### **3. Loading Users** (`adminMenuController.loadUsers`)  
**Goal:** Populate the user list in a table.  
**Steps:**  
* Clear the current table contents.  
* Retrieve all users from the database with an SQL `SELECT` query.  
* Convert each result into a `User` object and store it in an `ArrayList<User>`.  
* Display users in the `TableView`, refreshing the UI.

---

### **4. Editing a User** (`adminMenuController.saveUserChanges`)  
**Goal:** Update user details.  
**Steps:**  
* Retrieve the selected user.  
* Collect data into an `ArrayList<String>`.  
* Validate input and convert age to a number (handling empty values).  
* Execute an `UPDATE` SQL query.  
* Refresh the table after a successful update.

---

### **5. Deleting a User** (`adminMenuController.deleteUser`)  
**Goal:** Remove a user from the database.  
**Steps:**  
* Retrieve the selected user.  
* Execute a `DELETE` SQL query.  
* Refresh the table to reflect changes.

---

### **6. Creating a User** (`adminCreatUserController.initialize`)  
**Goal:** Add a new user.  
**Steps:**  
* Collect data from fields and checkboxes into an `ArrayList<String>`.  
* Validate input and handle age conversion.  
* Execute an `INSERT` SQL query.  
* Display a success message.

---

## **Data Structures Used**
* `ArrayList` – Stores form data and user lists.  
* `User` Class – Represents individual users in the system.  
* `ObservableList` – Automatically updates the UI when modified.  
* `StringProperty` – Tracks the current username dynamically.

---

## **Challenges & Solutions**

### **1. Handling null values:**  
* Converts null values to `"N/A"` or `0` in constructors and queries.

### **2. Input Validation:**  
* Ensures required fields are filled and correctly formats numeric values.

### **3. Database Connection Management:**  
* Uses `try-with-resources` to avoid resource leaks.

### **4. UI Synchronization:**  
* Calls `usersTable.refresh()` to reflect database changes immediately.

### **5. Switching UI Panels:**  
* Manages visibility with `setVisible(true/false)` for smooth transitions.

---

## **Conclusion**
This JavaFX application provides a **structured system** for user management with an **integrated database**.  
It uses `ArrayList` for efficient data handling and `ObservableList` for seamless UI updates.  
**Error handling** and **validation** ensure reliability, while database connections are managed properly.  

**Future improvements could include authentication.**






# Project Requirement List

The application is a **JavaFX-based user management system** integrated with a **database**, providing interfaces for users and administrators.  
Below are **10 key requirements** that must be implemented:

---

### **1. User Authentication**  
Users must log in with a **username** to access the user menu (`userMenuController`).

---

### **2. View User Profile**  
Users must view their data (**username**, **position**, **email**, **age**, **resume**) in the view mode (`viewPane`).

---

### **3. Edit User Profile**  
Users must edit their **username**, **email**, **password**, and **resume** in edit mode (`editPane`), with changes saved to the database.

---

### **4. Administrator Access**  
Administrators must access the **admin menu** (`adminMenuController`) to manage all users.

---

### **5. Display User List**  
Administrators must see a table listing all users, including fields:  
`idusers`, `username`, `firstname`, `lastname`, `email`, `password`, `position`, `gender`, `age`, `resume`, and `job_details`.

---

### **6. Edit User Data by Admin**  
Administrators must select a user from the table and **edit all their data**, saving changes to the database.

---

### **7. Delete User**  
Administrators must **delete a user** from the database via the interface.

---

### **8. Create New User**  
Administrators must **create a new user** through a dedicated interface (`adminCreatUserController`), providing all required data.

---

### **9. Input Validation**  
The application must **validate mandatory fields** and ensure **age is numeric** during user creation and editing.

---

### **10. Error Handling**  
The application must **handle database errors** (e.g., connection issues) and display **error messages**.


# Test Cases and Expected Outputs

Below are test cases to validate all application functionalities. Each case includes input data, user actions, and expected output, covering various scenarios, including valid and invalid inputs.

---

## Test Case 1: View User Profile

**Input Data:**
- Username: `john_doe`
- Database contains:  
  `{idusers: 1, username: "john_doe", firstname: "John", lastname: "Doe", email: "john@example.com", password: "pass123", position: "Developer", gender: "Male", age: 30, resume: "Experienced developer", job_details: "Full-time"}`

**Actions:**
- Set `currentUsernameProperty` to `john_doe`
- Open the `userMenuController` interface

**Expected Output:**
- ![image](https://github.com/user-attachments/assets/b1a03040-3524-4b82-b9bc-8c471c7fc495)
- Console: `"Loading data for user: john_doe"`

**Error Scenario:**
- If the user does not exist, console: `"Error: User data not found for username: john_doe"`





---

## Test Case 2: Edit User Profile

**Input Data:**
- Current user: `john_doe`
- New data:  
  `{username: "john_doe2", email: "john2@example.com", password: "", resume: "Senior developer"}`

**Actions:**
- Open the `userMenuController` interface and switch to edit mode
- Enter new data in the text fields
- Click the save button

**Expected Output:**
- ![image](https://github.com/user-attachments/assets/b6e9bd5b-e15f-4403-be3b-c8f60da203d4)
- Interface switches to view mode
- Console: `"Data updated!"`

**Error Scenario:**
- If username is empty, console: `"Error: Fields cannot be empty."`



---

## Test Case 3: Display User List

**Input Data:**
- ![image](https://github.com/user-attachments/assets/8ee843ee-3a39-4183-8c37-6d315f98c102)
- Database contains two users:


**Actions:**
- Open the `adminMenuController` interface

**Expected Output:**
- `usersTable` displays two rows with user data
- Console:
# Test Cases and Expected Outputs

Below are test cases to validate all application functionalities. Each case includes input data, user actions, and expected output, covering various scenarios, including valid and invalid inputs.

---

## Test Case 4: Edit User by Admin

**Input Data:**  
- ![image](https://github.com/user-attachments/assets/955fa78c-502e-4eb8-b878-e70d61981041)


**Actions:**
- Open the `adminMenuController` interface
- Select the user with `idusers=1`
- Enter new data in the text fields
- Click the save button

**Expected Output:**
- Database updated
- Table refreshes, fields clear
- Console: `"User updated!"`

**Error Scenario:**
- If age is non-numeric (e.g., `"abc"`), console: `"Error: Age must be a number."`

---

## Test Case 5: Delete User

**Actions:**
- Open `adminMenuController` interface
- Select the user with `idusers=1`
- Click the delete button

**Expected Output:**
- User removed from database
- Table refreshes, user disappears
- Console: `"User deleted!"`


**Error Scenario:**
- If no user is selected, console: `"Error: Select a user to delete."`

---

## Test Case 6: Create New User

**Input Data:**  
- ![image](https://github.com/user-attachments/assets/4ce3c779-8272-4056-a55b-ca93cfbe581d)


**Actions:**
- Open `adminCreatUserController` interface
- Fill the form and select `"Female"` gender
- Click the create button

**Expected Output:**
- New database record created
- Console: `"User created!"`

**Error Scenario:**
- If username is empty, console: `"Error: All mandatory fields must be filled!"`

---


# Saving Program Output

To verify functionality, save the program's output.

## Console Logs

Redirect `System.out` and `System.err` to a file:

```java
PrintStream out = new PrintStream(new FileOutputStream("output.log"));
System.setOut(out);
System.setErr(out);
```
After running tests, `output.log` will contain messages like:
- "User created!"
- "Error: Fields cannot be empty."
- etc.

## Screenshots
Capture screenshots for each test case:
- **Test 1**: User profile labels filled
- **Test 2**: Edit panel with updated data
- **Test 3**: Table showing users
- **Test 5**: Table after user deletion
- **Test 6**: Completed user creation form

Save screenshots as PNG or JPG files (e.g., test1_user_profile.png).

## Database State

Export the **users** table contents after each test using:
```java
mysqldump -u [username] -p [database_name] users > users_dump.sql
```
## Submission
**Files to submit**:
- `output.log` — Console output log
- `screenshots/` folder(`test1_user_profile.png`,`test2_edit_profile.png`,etc.)
- `users_dump.sql` (Database State)

## Instructions:
- Package files into `project_output.zip`
- Submit via assignment system or provide a cloud storage link.


