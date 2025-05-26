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





