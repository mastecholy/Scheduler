package petrizzi.scheduler.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import petrizzi.scheduler.helper.HelperFunctions;
import petrizzi.scheduler.helper.JDBC;
import petrizzi.scheduler.helper.Queries;
import petrizzi.scheduler.model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField PasswordText;

    @FXML
    private TextField UsernameText;

    @FXML
    private Label ZoneIDLabel;

    @FXML
    void onEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                JDBC.openConnection(UsernameText.getText(), PasswordText.getText());
                HelperFunctions.changeStage("directory-view.fxml", LoginButton);
                HelperFunctions.greeting();
            } catch (IOException e) {
                HelperFunctions.sendAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid login information.");
            }
        }
    }

    @FXML
    void LoginButtonClick(ActionEvent event) throws IOException {
        try {
            JDBC.openConnection(UsernameText.getText(), PasswordText.getText());
            HelperFunctions.changeStage("directory-view.fxml", LoginButton);
            HelperFunctions.greeting();
        } catch (IOException e) {
            HelperFunctions.sendAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid login information.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneIDLabel.setText(String.valueOf(ZoneId.systemDefault()));

    }
}
