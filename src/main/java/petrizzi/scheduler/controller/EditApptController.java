package petrizzi.scheduler.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;
import petrizzi.scheduler.helper.HelperFunctions;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditApptController implements Initializable {

    @FXML
    private ComboBox<?> contactComboBox;

    @FXML
    private ComboBox<?> customerIdComboBox;

    @FXML
    private TextField descriptionField;

    @FXML
    private Spinner<Integer> endHoursSpinner;

    @FXML
    private Spinner<Integer> endMinutesSpinner;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField idField;

    @FXML
    private TextField locationField;

    @FXML
    private Spinner<Integer> startHoursSpinner;

    SpinnerValueFactory<Integer> StartHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
    SpinnerValueFactory<Integer> EndHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);

    SpinnerValueFactory<Integer> StartMinFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5);
    SpinnerValueFactory<Integer> EndMinFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5);

    @FXML
    private Spinner<Integer> startMinutesSpinner;

    @FXML
    private TextField titleField;

    @FXML
    private TextField typeField;

    @FXML
    private ComboBox<?> userIdComboBox;

    @FXML
    void saveButtonClick(MouseEvent event) throws IOException {
        ////// add persistence
        HelperFunctions.changeStage("directory-view.fxml", saveButton);
    }

    @FXML
    void cancelButtonClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("directory-view.fxml", cancelButton);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startHoursSpinner.setValueFactory(StartHourFactory);
        startMinutesSpinner.setValueFactory(StartMinFactory);
        endHoursSpinner.setValueFactory(EndHourFactory);
        endMinutesSpinner.setValueFactory(EndMinFactory);

    }

}