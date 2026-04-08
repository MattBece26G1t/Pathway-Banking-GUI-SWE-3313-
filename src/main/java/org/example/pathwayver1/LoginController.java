package org.example.pathwayver1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class LoginController {

    @FXML private TextField userIDField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private int numOfAttempts = 0;
    private int maxAttempts = 5;
    @FXML private Button loginButton;

    // Shared UserManager instance — holds all registered accounts
    private static UserManager userManager = new UserManager();

    // Getter so other controllers (Registration, etc.) can access the same UserManager
    public static UserManager getUserManager() {
        return userManager;
    }

    @FXML
    private void initialize() {
        errorLabel.setVisible(false);
    }

    @FXML
    private void handleLogin() {
        String id = userIDField.getText().trim();
        String pass = passwordField.getText();

        if (id.isEmpty() || pass.isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Please enter both UserID and password.");
            return;
        }

        try {
            UserAccount user = userManager.login(id, pass);
            numOfAttempts = 0;

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/pathwayver1/DashboardView.fxml"));
                Scene scene = new Scene(loader.load(), 800, 600);
                DashboardController controller = loader.getController();
                controller.setUser(user);
                Stage stage = (Stage) userIDField.getScene().getWindow();
                stage.setScene(scene);
            }catch (Exception x){
                x.printStackTrace();
            }

            // TODO: Switch to Dashboard when it's ready

        } catch (IllegalArgumentException e) {
            numOfAttempts++;
            errorLabel.setVisible(true);

            if (numOfAttempts >= maxAttempts) {
                errorLabel.setText("Too many failed attempts. Please try again in 30 seconds.");
                loginButton.setDisable(true);

                // Re-enable after 30 seconds
                Timeline lockout = new Timeline(
                        new KeyFrame(Duration.seconds(30), event -> {
                            loginButton.setDisable(false);
                            numOfAttempts = 0;
                            errorLabel.setText("You may try again.");
                        })
                );
                lockout.play();
            } else {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    @FXML
    private void handleRegister() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("RegistrationView.fxml")
        );
        Stage stage = (Stage) userIDField.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
    }

    @FXML
    private void handleForgotUserID() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("RecoverIDView.fxml")
        );
        Stage stage = (Stage) userIDField.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
    }

    @FXML
    private void handleResetPassword() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("ResetPassView.fxml")
        );
        Stage stage = (Stage) userIDField.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
    }

    public void prefillCredentials(String id, String pass) {
        userIDField.setText(id);
        passwordField.setText(pass);
    }

    @FXML
    private void handleExit() {
        if (MainApp.showExitConfirmation()) {
            Stage stage = (Stage) userIDField.getScene().getWindow();
            stage.close();
        }
    }

}