package petrizzi.scheduler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import petrizzi.scheduler.Main;
import petrizzi.scheduler.helper.HelperFunctions;

import java.io.IOException;

public class DirectoryController {

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button editCustomerButton;

    @FXML
    private Button removeCustomerButton;

    @FXML
    private Button addApptButton;

    @FXML
    private Button editApptButton;

    @FXML
    private Button removeApptButton;

    @FXML
    void addCustomerClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("create-customer-view.fxml", addCustomerButton);
    }

    @FXML
    void editApptClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("edit-appt-view.fxml", editApptButton);
    }

    @FXML
    void addApptClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("create-appt-view.fxml", addApptButton);
    }

    @FXML
    void editCustomerClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("edit-customer-view.fxml", editCustomerButton);
    }

    @FXML
    void removeCustomerClick(MouseEvent event) {

    }

    @FXML
    void removeApptClick(MouseEvent event) {

    }

}
