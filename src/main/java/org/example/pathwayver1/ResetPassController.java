package org.example.pathwayver1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Optional;

public class ResetPassController {

    // === Panes ===
    @FXML private Pane step1Pane;
    @FXML private Pane step2Pane;
    @FXML private Pane dobPane;

    // === Step 1: Email ===
    @FXML private TextField emailField;
    @FXML private Label loadingLabel1;
    @FXML private Label errorLabel1;

    // === Step 1: DOB (inside dobPane) ===
    @FXML private TextField birthMonthField;
    @FXML private TextField birthDayField;
    @FXML private TextField birthYearField;
    @FXML private Label loadingLabel2;
    @FXML private Label errorLabel2;

    // === Step 2: New Password ===
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label credentialHintLabel;
    @FXML private Label errorLabel3;
    @FXML private Label successLabel;

    private UserManager userManager = LoginController.getUserManager();

    // Tracks which stage the Check button is at
    private boolean emailVerified = false;

    // Stores the found user after email verification
    private UserAccount foundUser = null;

    @FXML
    private void initialize() {
        // Start on step 1 with DOB hidden
        step1Pane.setVisible(true);
        step2Pane.setVisible(false);
        dobPane.setVisible(false);

        // Hide all labels
        loadingLabel1.setVisible(false);
        loadingLabel2.setVisible(false);
        errorLabel1.setVisible(false);
        errorLabel2.setVisible(false);
        errorLabel3.setVisible(false);
        successLabel.setVisible(false);

        if (credentialHintLabel != null) {
            credentialHintLabel.setVisible(false);
        }

        // Password hint listeners
        newPasswordField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                credentialHintLabel.setVisible(true);
                credentialHintLabel.setText(
                        "Password must:\n" +
                                "• Be 8-30 characters\n" +
                                "• Contain at least one letter\n" +
                                "• Contain at least one number\n" +
                                "• Contain at least one special character\n" +
                                "• Not contain spaces\n" +
                                "• Not match your UserID"
                );
            }
        });

        confirmPasswordField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                credentialHintLabel.setVisible(true);
                credentialHintLabel.setText(
                        "Re-enter your new password to confirm."
                );
            }
        });
    }

    // === Shared Check Button — behavior changes based on stage ===
    @FXML
    private void handleCheck() {
        if (!emailVerified) {
            checkEmail();
        } else {
            checkDOB();
        }
    }

    // === Email Verification ===
    private void checkEmail() {
        String email = emailField.getText().trim();

        if (email.isEmpty()) {
            errorLabel1.setVisible(true);
            errorLabel1.setText("Please enter your email address.");
            return;
        }

        // Hide previous errors and show loading
        errorLabel1.setVisible(false);
        loadingLabel1.setVisible(true);
        loadingLabel1.setText("Retrieving information and verifying...");

        // Loading animation
        Timeline dotAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0), e ->
                        loadingLabel1.setText("Retrieving information and verifying.")),
                new KeyFrame(Duration.seconds(0.5), e ->
                        loadingLabel1.setText("Retrieving information and verifying..")),
                new KeyFrame(Duration.seconds(1.0), e ->
                        loadingLabel1.setText("Retrieving information and verifying...")),
                new KeyFrame(Duration.seconds(1.5))
        );
        dotAnimation.setCycleCount(2);

        dotAnimation.setOnFinished(e -> {
            loadingLabel1.setVisible(false);

            foundUser = userManager.findByEmail(email);

            if (foundUser != null) {
                // Email found — show DOB fields
                emailVerified = true;
                dobPane.setVisible(true);
            } else {
                errorLabel1.setVisible(true);
                errorLabel1.setText("No account found with that email.");
            }
        });

        dotAnimation.play();
    }

    // === DOB Verification ===
    private void checkDOB() {
        // Clear previous errors
        errorLabel2.setVisible(false);

        try {
            int month = UserAccount.parseBirthMonth(birthMonthField.getText());
            int day = UserAccount.parseBirthDay(birthDayField.getText());
            int year = UserAccount.parseBirthYear(birthYearField.getText());

            // Show loading animation
            loadingLabel2.setVisible(true);
            loadingLabel2.setText("Verifying birthday...");

            Timeline dotAnimation = new Timeline(
                    new KeyFrame(Duration.seconds(0), e ->
                            loadingLabel2.setText("Verifying birthday.")),
                    new KeyFrame(Duration.seconds(0.5), e ->
                            loadingLabel2.setText("Verifying birthday..")),
                    new KeyFrame(Duration.seconds(1.0), e ->
                            loadingLabel2.setText("Verifying birthday...")),
                    new KeyFrame(Duration.seconds(1.5))
            );
            dotAnimation.setCycleCount(2);

            dotAnimation.setOnFinished(e -> {
                loadingLabel2.setVisible(false);

                if (foundUser.verifyDOB(month, day, year)) {
                    // DOB matches — show password reset pane
                    step1Pane.setVisible(false);
                    step2Pane.setVisible(true);
                } else {
                    errorLabel2.setVisible(true);
                    errorLabel2.setText("Date of birth does not match our records.");
                }
            });

            dotAnimation.play();

        } catch (IllegalArgumentException e) {
            errorLabel2.setVisible(true);
            errorLabel2.setText(e.getMessage());
        }
    }

    // === Reset Password ===
    @FXML
    private void handleResetPassword() {
        try {
            String pass = newPasswordField.getText();
            String confirm = confirmPasswordField.getText();

            if (pass == null || pass.trim().isEmpty()) {
                throw new IllegalArgumentException("Password field cannot be empty.");
            }
            if (confirm == null || confirm.trim().isEmpty()) {
                throw new IllegalArgumentException("Confirm password field cannot be empty.");
            }
            if (!pass.equals(confirm)) {
                throw new IllegalArgumentException("Passwords do not match.");
            }

            // Set the new password on the found user
            foundUser.setPassword(pass);

            // Hide error and show success
            errorLabel3.setVisible(false);
            successLabel.setVisible(true);
            successLabel.setText("Password reset success!");

            // Wait 3 seconds then return to login
            Timeline delay = new Timeline(
                    new KeyFrame(Duration.seconds(3), e -> {
                        try {
                            goToLogin();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    })
            );
            delay.play();

        } catch (IllegalArgumentException e) {
            errorLabel3.setVisible(true);
            errorLabel3.setText(e.getMessage());
        }
    }

    // === Cancel (with confirmation) ===
    @FXML
    private void handleCancel() throws Exception {
        if (MainApp.showCancelResetPasswordConfirmation()) {
            goToLogin();
        }
    }

    // === Back (no confirmation needed — just returns to login) ===
    @FXML
    private void handleBack() throws Exception {
        goToLogin();
    }

    // === Helper to return to login screen ===
    private void goToLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("LoginView.fxml")
        );
        Stage stage = (Stage) step1Pane.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
    }
}