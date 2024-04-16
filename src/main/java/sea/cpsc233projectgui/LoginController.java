package sea.cpsc233projectgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    private Login login;

    public LoginController() {
        login = new Login();
    }

    /**
     * Allows the user to login to the main application
     * @param event The ActionEvent that triggers the login
     */
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidLogin(username, password)) {
            // Load the FXML file for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("library.fxml"));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.show();
        } else {
            // Display an error message if the login is invalid
            statusLabel.setText("Invalid username or password!");
        }
    }

    /**
     * Allows the login button to check if the login id and password is valid
     * @param username the set username to login to the application
     * @param password the set password to login to the application
     * @return True if login id and password are valid, False otherwise
     */
    private boolean isValidLogin(String username, String password) {
        // Return true if the login is successful, false otherwise
        return username.equals("admin") && password.equals("sea");
    }
}
