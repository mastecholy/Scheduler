package petrizzi.scheduler.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import petrizzi.scheduler.helper.HelperFunctions;

import java.io.IOException;

public class CreateCustomerController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button createButton;

    @FXML
    void cancelButtonClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("directory-view.fxml", cancelButton);
    }

    @FXML
    void createButtonClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("directory-view.fxml", createButton);

    }

}
