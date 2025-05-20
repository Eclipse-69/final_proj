package proj.proj2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class adminCreatUserController {

    @FXML
    private TextField password_field;

    @FXML
    private CheckBox userCheckBoxFemale;

    @FXML
    private CheckBox userCheckBoxMale;

    @FXML
    private Button userCreateButton;

    @FXML
    private TextField userEmail_field;

    @FXML
    private TextField userLastname_field;

    @FXML
    private TextField userFirstname_field;

    @FXML
    private TextField userPosition_field;

    @FXML
    private TextField username_field;

    @FXML
    void initialize() {
        userCreateButton.setOnAction(event -> {
            String username = username_field.getText();
            String password = password_field.getText();
            String email = userEmail_field.getText();
            String firstname = userFirstname_field.getText();
            String lastname = userLastname_field.getText();
            String position = userPosition_field.getText();
            String gender = userCheckBoxMale.isSelected() ? "Male" : userCheckBoxFemale.isSelected() ? "Female" : "Not Specified";


            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || position.isEmpty()) {
                System.out.println("Ошибка: Все поля должны быть заполнены!");
                return;
            }

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO users (username, password, email, firstname, lastname, position, gender) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, firstname);
                preparedStatement.setString(5, lastname);
                preparedStatement.setString(6, position);
                preparedStatement.setString(7, gender);

                preparedStatement.executeUpdate();
                System.out.println("Пользователь создан!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Ошибка при создании пользователя!");
            }
        });
    }
}