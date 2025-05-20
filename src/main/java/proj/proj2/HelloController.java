

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField password_field;

    @FXML
    private TextField username_field;

    @FXML
    void initialize() {
        loginButton.setOnAction(event -> {
            String username = username_field.getText();
            String password = password_field.getText();

            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("Ошибка: Имя пользователя и пароль не могут быть пустыми.");
                return;
            }

            try (Connection connection = DatabaseConnection.getConnection()) {
                UserData userData = getUserData(connection, username);

                if (userData == null) {
                    System.out.println("Ошибка: Неверное имя пользователя или пароль.");
                    return;
                }

                if (!userData.getPassword().equals(password)) {
                    System.out.println("Ошибка: Неверное имя пользователя или пароль.");
                    return;
                }

                FXMLLoader loader;
                if ("admin".equals(userData.getPosition())) {
                    loader = new FXMLLoader(getClass().getResource("/proj/proj2/adminMenu.fxml"));
                } else {
                    loader = new FXMLLoader(getClass().getResource("/proj/proj2/userMenu.fxml"));
                }

                try {
                    Parent root = loader.load();
                    //сортировка
                    if (!"admin".equals(userData.getPosition())) {
                        userMenuController controller = loader.getController();
                        controller.setCurrentUsername(username); // Set username
                    }
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private UserData getUserData(Connection connection, String username) {
        String query = "SELECT password, position FROM users WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return new UserData(resultSet.getString("password"), resultSet.getString("position"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class UserData {
    private String password;
    private String position;

    public UserData(String password, String position) {
        this.password = password;
        this.position = position;
    }

    public String getPassword() {
        return password;
    }

    public String getPosition() {
        return position;
    }
}