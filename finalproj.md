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

