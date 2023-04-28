package petrizzi.scheduler.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import petrizzi.scheduler.helper.HelperFunctions;
import petrizzi.scheduler.helper.Queries;
import petrizzi.scheduler.model.Contact;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class CreateApptController implements Initializable {

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<Integer> customerIdComboBox;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker startDateField;

    @FXML
    private DatePicker endDateField;

    @FXML
    private Spinner<Integer> endHoursSpinner;

    @FXML
    private Spinner<Integer> endMinutesSpinner;

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
    private ComboBox<Integer> userIdComboBox;

    @FXML
    void saveButtonClick(MouseEvent event) throws IOException, SQLException, InvocationTargetException {
        try {
            ZoneId easternZone = ZoneId.of("America/New_York");

            LocalDate startDay = startDateField.getValue();
            LocalDate endDay = endDateField.getValue();
            int startHour = startHoursSpinner.getValue();
            int startMin = startMinutesSpinner.getValue();
            int endHour = endHoursSpinner.getValue();
            int endMin = endMinutesSpinner.getValue();
            int customerID = customerIdComboBox.getValue();
            LocalDateTime localStartTime = LocalDateTime.of(startDay, LocalTime.of(startHour, startMin));
            LocalDateTime localEndTime = LocalDateTime.of(endDay, LocalTime.of(endHour, endMin));

            LocalDateTime easternOpenDateTime = LocalDateTime.of(startDay, LocalTime.of(8, 0));
            LocalDateTime easternCloseDateTime = LocalDateTime.of(startDay, LocalTime.of(22, 0));
            LocalDateTime localOpenTime = easternOpenDateTime.atZone(easternZone).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime localCloseTime = easternCloseDateTime.atZone(easternZone).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();


            if ((localStartTime.isBefore(ChronoLocalDateTime.from(localOpenTime)) || localStartTime.isAfter(ChronoLocalDateTime.from(localCloseTime)) ||
                    localEndTime.isBefore(ChronoLocalDateTime.from(localOpenTime)) || localEndTime.isAfter(ChronoLocalDateTime.from(localCloseTime)))) {
                HelperFunctions.sendAlert(Alert.AlertType.ERROR, "Invalid Appointment Time",
                        "Cannot schedule appointment outside of Eastern Time office hours.",
                        "Appointment must take place between 8:00 and 22:00 Eastern Time.");

            } else if (Queries.checkAppointmentOverlap(customerID, localStartTime, localEndTime)) {
                HelperFunctions.sendAlert(Alert.AlertType.ERROR, "Invalid Appointment", "Customer appointment overlap!",
                        "Selected customer has an appointment during this time.");
            } else if (localStartTime.isAfter(localEndTime)){
                HelperFunctions.sendAlert(Alert.AlertType.ERROR, "Invalid Appointment", "Invalid appointment times.", "Appointment start time must be before appointment end time.");
            }else {

                Queries.createAppointment(
                        titleField.getText(),
                        typeField.getText(),
                        descriptionField.getText(),
                        locationField.getText(),
                        localStartTime,
                        localEndTime,
                        customerIdComboBox.getValue(),
                        userIdComboBox.getValue(),
                        contactComboBox.getValue());

                HelperFunctions.changeStage("directory-view.fxml", saveButton);
            }
        } catch (Exception e){
            HelperFunctions.sendAlert(Alert.AlertType.ERROR, "Invalid Field Information", "Please fill ALL fields with valid information and data types.");
        }
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

        try {
            contactComboBox.getItems().setAll(Queries.selectContactNames());
            customerIdComboBox.getItems().setAll(Queries.selectCustomerIDs());
            userIdComboBox.getItems().setAll(Queries.selectUserIDs());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
