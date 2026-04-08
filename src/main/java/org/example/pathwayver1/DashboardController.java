package org.example.pathwayver1;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.*;

public class DashboardController {

    @FXML private ComboBox<String> accountDropdown;
    @FXML private Label balanceLabel;
    @FXML private void handleExit(){
        Stage stage = (Stage) accountDropdown.getScene().getWindow();
        stage.close();
    }

    public void setUser(UserAccount user){
        accountDropdown.getItems().clear();

        for (Account acc: user.getAccounts()){
            accountDropdown.getItems().add(acc.getAccountType());
        }
        accountDropdown.setOnAction(e -> {
            String selected = accountDropdown.getValue();
            for (Account acc: user.getAccounts()){
                balanceLabel.setText("Balance: $" + acc.getBalance());
            }
        });
    }
}
