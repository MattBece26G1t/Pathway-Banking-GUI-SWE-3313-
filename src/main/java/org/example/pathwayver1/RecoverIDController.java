package org.example.pathwayver1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RecoverIDController {

    @FXML private TextField emailOrPhoneField;
    @FXML private Label resultLabel;
    @FXML private Label loadingLabel;

    private UserManager userManager = LoginController.getUserManager();

    @FXML
    private void initialize() {
        resultLabel.setVisible(false);
        loadingLabel.setVisible(false);
    }

    @FXML
    private void handleRecover() {
        String value = emailOrPhoneField.getText().trim();

        if (value.isEmpty()) {
            loadingLabel.setVisible(false);
            resultLabel.setVisible(true);
            resultLabel.setText("Please enter your email or phone number.");
            return;
        }

        // Hide any previous result and show loading
        resultLabel.setVisible(false);
        loadingLabel.setVisible(true);

        // Dot animation — cycles through 1, 2, 3 dots
        Timeline dotAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0), e ->
                        loadingLabel.setText("Retrieving information and verifying.")),
                new KeyFrame(Duration.seconds(0.5), e ->
                        loadingLabel.setText("Retrieving information and verifying..")),
                new KeyFrame(Duration.seconds(1.0), e ->
                        loadingLabel.setText("Retrieving information and verifying...")),
                new KeyFrame(Duration.seconds(1.5)) // pause so the third dot is visible
        );
        dotAnimation.setCycleCount(3); // 3 cycles × 1.5 seconds = 4.5 seconds total

        // When animation finishes, look up the account and show result
        dotAnimation.setOnFinished(e -> {
            loadingLabel.setVisible(false);

            UserAccount found = userManager.findByEmailOrPhone(value);

            resultLabel.setVisible(true);
            if (found != null) {
                resultLabel.setText("Your UserID is: " + found.getUserID());
            } else {
                resultLabel.setText("No account found with that email or phone number.");
            }
        });

        dotAnimation.play();
    }

    @FXML
    private void handleBack() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("LoginView.fxml")
        );
        Stage stage = (Stage) emailOrPhoneField.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
    }
}