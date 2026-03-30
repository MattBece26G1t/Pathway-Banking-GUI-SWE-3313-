package org.example.pathwayver1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Optional;

public class RegistrationController {

    // === Step Panes ===
    @FXML private Pane step0Pane;
    @FXML private Pane step1Pane;
    @FXML private Pane step2Pane;
    @FXML private Pane step3Pane;
    @FXML private Pane step4Pane;
    @FXML private Pane step5Pane;  // Confirmation
    @FXML private Pane step6Pane;  // Credentials
    @FXML private Pane step7Pane;  // Congratulations

    // === Step 1: Name ===
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;

    // === Step 2: Electronic Communications ===
    @FXML private TextField emailField;
    @FXML private TextField phoneField;

    // === Step 3: Address ===
    @FXML private TextField streetField;
    @FXML private TextField cityField;
    @FXML private TextField stateField;
    @FXML private TextField zipField;
    @FXML private TextField countryField;

    // === Step 4: Date of Birth ===
    @FXML private TextField birthMonthField;
    @FXML private TextField birthDayField;
    @FXML private TextField birthYearField;

    // === Step 5: Confirmation (read-only TextFields) ===
    @FXML private TextField confirmFirstName;
    @FXML private TextField confirmLastName;
    @FXML private TextField confirmEmail;
    @FXML private TextField confirmPhone;
    @FXML private TextField confirmStreet;
    @FXML private TextField confirmCity;
    @FXML private TextField confirmState;
    @FXML private TextField confirmZip;
    @FXML private TextField confirmCountry;
    @FXML private TextField confirmDOB;

    // === Step 6: Credentials ===
    @FXML private TextField userIDField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label credentialHintLabel;

    // === Error Labels ===
    @FXML private Label errorLabel1;
    @FXML private Label errorLabel2;
    @FXML private Label errorLabel3;
    @FXML private Label errorLabel4;
    @FXML private Label errorLabel6;

    private Label currentErrorLabel;

    private UserManager userManager = LoginController.getUserManager();

    // Temporary holder — fields get validated and set step by step
    private UserAccount newUser = new UserAccount();

    @FXML
    private void initialize() {
        showStep(0);

        // Show UserID rules when user clicks into the userID field
        userIDField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                credentialHintLabel.setVisible(true);
                credentialHintLabel.setText(
                        "UserID must:\n" +
                                "• Be 6-15 characters\n" +
                                "• Start with a letter\n" +
                                "• Contain only letters, numbers, or underscores\n" +
                                "• Not start or end with an underscore"
                );
            }
        });

        // Show Password rules when user clicks into either password field
        passwordField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
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
                        "Re-enter your password to confirm."
                );
            }
        });
    }

    @FXML
    private void handleStartRegister() {
        showStep(1);
    }

    // === Step Navigation ===

    private void showStep(int step) {
        step0Pane.setVisible(step == 0);
        step1Pane.setVisible(step == 1);
        step2Pane.setVisible(step == 2);
        step3Pane.setVisible(step == 3);
        step4Pane.setVisible(step == 4);
        step5Pane.setVisible(step == 5);
        step6Pane.setVisible(step == 6);
        step7Pane.setVisible(step == 7);

        // Point to the right error label for this step
        switch (step) {
            case 1: currentErrorLabel = errorLabel1; break;
            case 2: currentErrorLabel = errorLabel2; break;
            case 3: currentErrorLabel = errorLabel3; break;
            case 4: currentErrorLabel = errorLabel4; break;
            case 6: currentErrorLabel = errorLabel6; break;
        }

        // Hide all error labels when switching
        errorLabel1.setVisible(false);
        errorLabel2.setVisible(false);
        errorLabel3.setVisible(false);
        errorLabel4.setVisible(false);
        errorLabel6.setVisible(false);

        // Hide credential hints when switching steps
        if (credentialHintLabel != null) {
            credentialHintLabel.setVisible(false);
        }
    }

    // === Step 1: Name ===
    @FXML
    private void handleNext1() {
        try {
            newUser.setFirstName(firstNameField.getText());
            newUser.setLastName(lastNameField.getText());

            showStep(2);

        } catch (IllegalArgumentException e) {
            currentErrorLabel.setVisible(true);
            currentErrorLabel.setText(e.getMessage());
        }
    }

    // === Step 2: Electronic Communications ===
    @FXML
    private void handleBack2() {
        showStep(1);
    }

    @FXML
    private void handleNext2() {
        try {
            newUser.setEmail(emailField.getText());
            if (userManager.isEmailTaken(emailField.getText())) {
                throw new IllegalArgumentException("Email already registered.");
            }

            newUser.setPhoneNumber(phoneField.getText());
            if (userManager.isPhoneTaken(phoneField.getText())) {
                throw new IllegalArgumentException("Phone number already registered.");
            }

            showStep(3);

        } catch (IllegalArgumentException e) {
            currentErrorLabel.setVisible(true);
            currentErrorLabel.setText(e.getMessage());
        }
    }

    // === Step 3: Address ===
    @FXML
    private void handleBack3() {
        showStep(2);
    }

    @FXML
    private void handleNext3() {
        try {
            newUser.setStreet(streetField.getText());
            newUser.setCity(cityField.getText());
            newUser.setState(stateField.getText());
            newUser.setZipCode(zipField.getText());
            newUser.setCountry(countryField.getText());

            showStep(4);

        } catch (IllegalArgumentException e) {
            currentErrorLabel.setVisible(true);
            currentErrorLabel.setText(e.getMessage());
        }
    }

    // === Step 4: Date of Birth ===
    @FXML
    private void handleBack4() {
        showStep(3);
    }

    @FXML
    private void handleNext4() {
        try {
            int month = UserAccount.parseBirthMonth(birthMonthField.getText());
            int day = UserAccount.parseBirthDay(birthDayField.getText());
            int year = UserAccount.parseBirthYear(birthYearField.getText());
            newUser.setDOB(month, day, year);

            populateConfirmation();
            showStep(5);

        } catch (IllegalArgumentException e) {
            currentErrorLabel.setVisible(true);
            currentErrorLabel.setText(e.getMessage());
        }
    }

    // === Step 5: Confirmation ===

    private void populateConfirmation() {
        confirmFirstName.setText(newUser.getFirstName());
        confirmLastName.setText(newUser.getLastName());
        confirmEmail.setText(newUser.getEmail());
        confirmPhone.setText(newUser.getPhoneNumberRaw());
        confirmStreet.setText(newUser.getStreet());
        confirmCity.setText(newUser.getCity());
        confirmState.setText(newUser.getState());
        confirmZip.setText(newUser.getZipCode());
        confirmCountry.setText(newUser.getCountry());
        confirmDOB.setText(newUser.getBMonth() + "/" + newUser.getBDay() + "/" + newUser.getBYear());
    }

    @FXML
    private void handleNext5() {
        // User confirmed everything looks good — move to credentials
        showStep(6);
    }

    @FXML
    private void handleBack5() {
        // User wants to go back and fix something — return to step 1
        showStep(1);
    }

    // === Step 6: Credentials ===
    @FXML
    private void handleBack6() {
        showStep(5);
    }

    @FXML
    private void handleRegister() throws Exception {
        try {
            // Validate UserID
            newUser.setUserID(userIDField.getText());
            if (userManager.isUserIDTaken(userIDField.getText())) {
                throw new IllegalArgumentException("UserID already exists.");
            }

            // Validate passwords
            String pass = passwordField.getText();
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
            newUser.setPassword(pass);

            // Assign default Debit/Checking account
            newUser.addAccount(new Account(0));

            // Register the user
            userManager.registerUser(newUser);

            // Success — show congratulations screen
            showStep(7);

        } catch (IllegalArgumentException e) {
            currentErrorLabel.setVisible(true);
            currentErrorLabel.setText(e.getMessage());
        }
    }

    // === Step 7: Congratulations ===
    @FXML
    private void handleOkay() throws Exception {
        // Go to login screen with credentials pre-filled
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("LoginView.fxml")
        );
        Scene scene = new Scene(loader.load());

        // Get the LoginController and pre-fill the credentials
        LoginController loginController = loader.getController();
        loginController.prefillCredentials(newUser.getUserID(), newUser.getPassword());

        Stage stage = (Stage) step7Pane.getScene().getWindow();
        stage.setScene(scene);
    }

    // === Cancel Registration ===
    @FXML
    private void handleCancel() throws Exception {
        if (MainApp.showCancelRegistrationConfirmation()) {
            goToLogin();
        }
    }

    // === Helper to return to login screen ===
    private void goToLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("LoginView.fxml")
        );
        Stage stage = (Stage) step0Pane.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
    }
}