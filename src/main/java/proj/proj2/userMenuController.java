package proj.proj2;

import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userMenuController {

    @FXML
    private Label usernameLabel;
    @FXML
    private Label positionLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label resumeLabel;

    @FXML
    private TextField editUsernameField;
    @FXML
    private TextField editEmailField;
    @FXML
    private TextField editPasswordField;
    @FXML
    private TextField editResumeField;

    @FXML
    private Button editButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane viewPane;
    @FXML
    private AnchorPane editPane;

    private final StringProperty currentUsernameProperty = new SimpleStringProperty();

    public void setCurrentUsername(String username) {
        currentUsernameProperty.set(username);
    }

    @FXML
    void initialize() {

        if (currentUsernameProperty.get() != null && !currentUsernameProperty.get().isEmpty()) {
            loadUserData();
        }


        currentUsernameProperty.addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                loadUserData();
            }
        });

        editButton.setOnAction(event -> {
            editPane.setVisible(true);
            viewPane.setVisible(false);
            loadEditableFields();
        });

        saveButton.setOnAction(event -> updateUserData());

        cancelButton.setOnAction(event -> {
            editPane.setVisible(false);
            viewPane.setVisible(true);
        });
    }

    private void loadUserData() {
        String username = currentUsernameProperty.get();
        if (username == null || username.isEmpty()) {
            System.out.println("Ошибка: Имя пользователя не установлено.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT username, position, email, age, resume FROM users WHERE username = ?")) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Загрузка данных для пользователя: " + username);
                usernameLabel.setText("Name: " + rs.getString("username"));
                positionLabel.setText("Position: " + rs.getString("position"));
                emailLabel.setText("Email: " + rs.getString("email"));
                ageLabel.setText("Age: " + (rs.getObject("age") != null ? rs.getInt("age") : "N/A"));
                resumeLabel.setText("Resume: " + (rs.getObject("resume") != null ? rs.getString("resume") : "N/A"));
            } else {
                System.out.println("Ошибка: Данные пользователя не найдены для username: " + username);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при загрузке данных пользователя: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadEditableFields() {
        editUsernameField.setText(usernameLabel.getText().replace("Name: ", ""));
        editEmailField.setText(emailLabel.getText().replace("Email: ", ""));
        editPasswordField.setText("");
        editResumeField.setText(resumeLabel.getText().replace("Resume: ", ""));
    }

    private void updateUserData() {
        String newUsername = editUsernameField.getText();
        String newEmail = editEmailField.getText();
        String newPassword = editPasswordField.getText();
        String newResume = editResumeField.getText();

        if (newUsername.isEmpty() || newEmail.isEmpty() || newResume.isEmpty()) {
            System.out.println("Ошибка: Поля не могут быть пустыми.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE users SET username = ?, email = ?, password = ?, resume = ? WHERE username = ?")) {

            stmt.setString(1, newUsername);
            stmt.setString(2, newEmail);
            stmt.setString(3, newPassword.isEmpty() ? getCurrentPassword() : newPassword);
            stmt.setString(4, newResume);
            stmt.setString(5, currentUsernameProperty.get());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Данные обновлены!");
                currentUsernameProperty.set(newUsername);
                editPane.setVisible(false);
                viewPane.setVisible(true);
            } else {
                System.out.println("Ошибка: Данные не обновлены.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentPassword() {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT password FROM users WHERE username = ?")) {

            stmt.setString(1, currentUsernameProperty.get());
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("password") : "";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
}