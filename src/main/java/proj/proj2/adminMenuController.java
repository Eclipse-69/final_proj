package proj.proj2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class adminMenuController {

    @FXML
    private Button createNewUserButton;

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, Integer> idusersColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> firstnameColumn;
    @FXML
    private TableColumn<User, String> lastnameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> positionColumn;
    @FXML
    private TableColumn<User, String> genderColumn;
    @FXML
    private TableColumn<User, Integer> ageColumn;
    @FXML
    private TableColumn<User, String> resumeColumn;
    @FXML
    private TableColumn<User, String> jobDetailsColumn;

    @FXML
    private TextField editIdusersField;
    @FXML
    private TextField editUsernameField;
    @FXML
    private TextField editFirstnameField;
    @FXML
    private TextField editLastnameField;
    @FXML
    private TextField editEmailField;
    @FXML
    private TextField editPasswordField;
    @FXML
    private TextField editPositionField;
    @FXML
    private TextField editGenderField;
    @FXML
    private TextField editAgeField;
    @FXML
    private TextField editResumeField;
    @FXML
    private TextField editJobDetailsField;

    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;

    @FXML
    void initialize() {

        idusersColumn.setCellValueFactory(new PropertyValueFactory<>("idusers"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        resumeColumn.setCellValueFactory(new PropertyValueFactory<>("resume"));
        jobDetailsColumn.setCellValueFactory(new PropertyValueFactory<>("jobDetails"));


        loadUsers();


        usersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                editIdusersField.setText(String.valueOf(newSelection.getIdusers()));
                editUsernameField.setText(newSelection.getUsername() != null ? newSelection.getUsername() : "");
                editFirstnameField.setText(newSelection.getFirstname() != null ? newSelection.getFirstname() : "");
                editLastnameField.setText(newSelection.getLastname() != null ? newSelection.getLastname() : "");
                editEmailField.setText(newSelection.getEmail() != null ? newSelection.getEmail() : "");
                editPasswordField.setText(newSelection.getPassword() != null ? newSelection.getPassword() : "");
                editPositionField.setText(newSelection.getPosition() != null ? newSelection.getPosition() : "");
                editGenderField.setText(newSelection.getGender() != null ? newSelection.getGender() : "");
                editAgeField.setText(String.valueOf(newSelection.getAge()));
                editResumeField.setText(newSelection.getResume() != null ? newSelection.getResume() : "");
                editJobDetailsField.setText(newSelection.getJobDetails() != null ? newSelection.getJobDetails() : "");
            } else {
                clearEditFields();
            }
        });


        createNewUserButton.setOnAction(event -> openScene("/proj/proj2/adminCreatUser.fxml"));
        saveButton.setOnAction(event -> saveUserChanges());
        deleteButton.setOnAction(event -> deleteUser());
    }

    private void loadUsers() {
        usersTable.getItems().clear();
        Connection connection = null;
        try {

            connection = DatabaseConnection.getConnection();
            if (connection == null || connection.isClosed()) {
                throw new SQLException("Соединение с базой данных отсутствует или закрыто.");
            }
            System.out.println("Соединение с базой данных установлено успешно.\n");

            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT idusers, username, firstname, lastname, email, password, position, gender, age, resume, job_details FROM users");
                 ResultSet rs = stmt.executeQuery()) {

                int count = 0;
                while (rs.next()) {
                    usersTable.getItems().add(new User(
                            rs.getInt("idusers"),
                            rs.getString("username"),
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("position"),
                            rs.getString("gender"),
                            rs.getObject("age") != null ? rs.getInt("age") : 0,
                            rs.getString("resume"),
                            rs.getString("job_details")
                    ));
                    count++;
                    System.out.println("Загружен пользователь: ID=" + rs.getInt("idusers") + ", Username=" + rs.getString("username"));
                }
                System.out.println("Успешно загружено " + count + " пользователей.");
                if (count == 0) {
                    System.out.println("Предупреждение: В базе данных нет пользователей.");
                    usersTable.getItems().add(new User(0, "Нет данных", "", "", "", "", "", "", 0, "", ""));
                }
            }
            usersTable.refresh();
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке пользователей: " + e.getMessage());
            e.printStackTrace();
            usersTable.getItems().add(new User(0, "Ошибка", "Загрузка", "неудачна (" + e.getMessage() + ")", "", "", "", "", 0, "", ""));
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveUserChanges() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            System.out.println("Выберите пользователя для редактирования.");
            return;
        }

        String newUsername = editUsernameField.getText();
        String newFirstname = editFirstnameField.getText();
        String newLastname = editLastnameField.getText();
        String newEmail = editEmailField.getText();
        String newPassword = editPasswordField.getText();
        String newPosition = editPositionField.getText();
        String newGender = editGenderField.getText();
        int newAge;
        try {
            newAge = editAgeField.getText().isEmpty() ? 0 : Integer.parseInt(editAgeField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Возраст должен быть числом.");
            return;
        }
        String newResume = editResumeField.getText();
        String newJobDetails = editJobDetailsField.getText();

        if (newUsername.isEmpty() || newFirstname.isEmpty() || newLastname.isEmpty() || newEmail.isEmpty() ||
                newPassword.isEmpty() || newPosition.isEmpty() || newGender.isEmpty()) {
            System.out.println("Все обязательные поля должны быть заполнены!");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE users SET username = ?, firstname = ?, lastname = ?, email = ?, password = ?, position = ?, gender = ?, age = ?, resume = ?, job_details = ? WHERE idusers = ?")) {

            stmt.setString(1, newUsername);
            stmt.setString(2, newFirstname);
            stmt.setString(3, newLastname);
            stmt.setString(4, newEmail);
            stmt.setString(5, newPassword);
            stmt.setString(6, newPosition);
            stmt.setString(7, newGender);
            stmt.setObject(8, newAge != 0 ? newAge : null);
            stmt.setString(9, newResume.isEmpty() ? null : newResume);
            stmt.setString(10, newJobDetails.isEmpty() ? null : newJobDetails);
            stmt.setInt(11, selectedUser.getIdusers());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Пользователь обновлен!");
                loadUsers();
                clearEditFields();
            } else {
                System.out.println("Ошибка: Не удалось обновить пользователя.");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении пользователя: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            System.out.println("Ошибка: Выберите пользователя для удаления.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM users WHERE idusers = ?")) {

            stmt.setInt(1, selectedUser.getIdusers());
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Пользователь удален!");
                loadUsers();
                clearEditFields();
            } else {
                System.out.println("Ошибка: Не удалось удалить пользователя.");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearEditFields() {
        editIdusersField.clear();
        editUsernameField.clear();
        editFirstnameField.clear();
        editLastnameField.clear();
        editEmailField.clear();
        editPasswordField.clear();
        editPositionField.clear();
        editGenderField.clear();
        editAgeField.clear();
        editResumeField.clear();
        editJobDetailsField.clear();
        usersTable.getSelectionModel().clearSelection();
    }

    private void openScene(String fxmlFilePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println("Ошибка при открытии сцены: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static class User {
        private final int idusers;
        private final String username;
        private final String firstname;
        private final String lastname;
        private final String email;
        private final String password;
        private final String position;
        private final String gender;
        private final int age;
        private final String resume;
        private final String jobDetails;

        public User(int idusers, String username, String firstname, String lastname, String email, String password,
                    String position, String gender, int age, String resume, String jobDetails) {
            this.idusers = idusers;
            this.username = (username != null) ? username : "";
            this.firstname = (firstname != null) ? firstname : "";
            this.lastname = (lastname != null) ? lastname : "";
            this.email = (email != null) ? email : "";
            this.password = (password != null) ? password : "";
            this.position = (position != null) ? position : "";
            this.gender = (gender != null) ? gender : "";
            this.age = age;
            this.resume = (resume != null) ? resume : "";
            this.jobDetails = (jobDetails != null) ? jobDetails : "";
        }

        public int getIdusers() { return idusers; }
        public String getUsername() { return username; }
        public String getFirstname() { return firstname; }
        public String getLastname() { return lastname; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public String getPosition() { return position; }
        public String getGender() { return gender; }
        public int getAge() { return age; }
        public String getResume() { return resume; }
        public String getJobDetails() { return jobDetails; }
    }
}