package org.example.pathwayver1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import java.util.Optional;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("LoginView.fxml")
        );
        Scene scene = new Scene(loader.load());
        stage.setTitle("Pathway Banking");
        stage.setResizable(false);
        stage.setScene(scene);

        // Intercept the X button on the title bar
        stage.setOnCloseRequest(event -> {
            event.consume();

            // === Uses the helper method now instead of a separate Alert ===
            if (showExitConfirmation()) {
                stage.close();
            }
        });

        stage.show();
    }

    // Custom styled exit confirmation — used by both the X button and EXIT button
    public static boolean showExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "",
                new ButtonType("Yes"),
                new ButtonType("No"));
        alert.setTitle("Exit Application");
        alert.setHeaderText("Leaving so soon?");
        alert.setContentText("Are you sure you want to close out of the application?");

        alert.setGraphic(new javafx.scene.image.ImageView(
                new javafx.scene.image.Image(
                        MainApp.class.getResourceAsStream("images/grnpig.png"),
                        52, 52, true, true
                )
        ));

        // Style the dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: #2b2b3d;" +
                        "-fx-font-family: 'Cascadia Mono';"
        );

        // Style the header and content text
        dialogPane.lookup(".header-panel").setStyle(
                "-fx-background-color: #C7FFC8;"
        );
        dialogPane.lookup(".content").setStyle(
                "-fx-text-fill: white; -fx-font-size: 14px;"
        );

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().getText().equals("Yes");
    }

    public static boolean showCancelRegistrationConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "",
                new ButtonType("Yes"),
                new ButtonType("No"));
        alert.setTitle("Cancel Registration");
        alert.setHeaderText("Leaving registration?");
        alert.setContentText("Are you sure you want to close out of registration?");

        alert.setGraphic(new javafx.scene.image.ImageView(
                new javafx.scene.image.Image(
                        MainApp.class.getResourceAsStream("images/grnpig.png"),
                        52, 52, true, true
                )
        ));

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: #2b2b3d;" +
                        "-fx-font-family: 'Cascadia Mono';"
        );

        dialogPane.lookup(".header-panel").setStyle(
                "-fx-background-color: #C7FFC8;"
        );
        dialogPane.lookup(".content").setStyle(
                "-fx-text-fill: white; -fx-font-size: 14px;"
        );

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().getText().equals("Yes");
    }

    public static boolean showCancelResetPasswordConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "",
                new ButtonType("Yes"),
                new ButtonType("No"));
        alert.setTitle("Cancel Password Reset");
        alert.setHeaderText("Leaving password reset?");
        alert.setContentText("Are you sure you want to leave password reset?");

        alert.setGraphic(new javafx.scene.image.ImageView(
                new javafx.scene.image.Image(
                        MainApp.class.getResourceAsStream("images/grnpig.png"),
                        52, 52, true, true
                )
        ));

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: #2b2b3d;" +
                        "-fx-font-family: 'Cascadia Mono';"
        );

        dialogPane.lookup(".header-panel").setStyle(
                "-fx-background-color: #C7FFC8;"
        );
        dialogPane.lookup(".content").setStyle(
                "-fx-text-fill: white; -fx-font-size: 14px;"
        );

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().getText().equals("Yes");
    }

    public static void main(String[] args) {
        launch(args);
    }
}