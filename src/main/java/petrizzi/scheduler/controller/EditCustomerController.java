package petrizzi.scheduler.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import petrizzi.scheduler.helper.HelperFunctions;

import java.io.IOException;

public class EditCustomerController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    void cancelButtonClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("directory-view.fxml", cancelButton);

    }

    @FXML
    void saveButtonClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("directory-view.fxml", saveButton);

    }

}
