package petrizzi.scheduler.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import petrizzi.scheduler.helper.HelperFunctions;
import petrizzi.scheduler.helper.Queries;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller for create-appt-view.fxml that allows the user to generate new appointments.
 */
public class CreateApptController implements Initializable {

    /**
     * Combo box for selecting appointment Contact.
     */
    @FXML
    private ComboBox<String> contactComboBox;

    /**
     * Button for saving appointment.
     */
    @FXML
    private Button saveButton;

    /**
     * Button for cancelling appointment creation.
     */
    @FXML
    private Button cancelButton;

    /**
     * Combo box for selecting appointment Customer ID.
     */
    @FXML
    private ComboBox<Integer> customerIdComboBox;

    /**
     * Text field for the user to input appointment description.
     */
    @FXML
    private TextField descriptionField;

    /**
     * DatePicker for the user to select appointment start date.
     */
    @FXML
    private DatePicker startDateField;

    /**
     * DatePicker for the user to select appointment end date.
     */
    @FXML
    private DatePicker endDateField;

    /**
     * Spinner for the user to select or input appointment end hour.
     */
    @FXML
    private Spinner<Integer> endHoursSpinner;

    /**
     * Spinner for the user to select or input appointment end minute.
     */
    @FXML
    private Spinner<Integer> endMinutesSpinner;

    /**
     * Text field for the user to enter appointment location.
     */
    @FXML
    private TextField locationField;

    /**
     * Spinner for the user to select appointment start hour.
     */
    @FXML
    private Spinner<Integer> startHoursSpinner;

    /**
     * Factory for time spinner.
     */
    SpinnerValueFactory<Integer> StartHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
    SpinnerValueFactory<Integer> EndHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);

    SpinnerValueFactory<Integer> StartMinFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5);
    SpinnerValueFactory<Integer> EndMinFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5);

    /**
     * Spinner for the user to select appointment start minute.
     */
    @FXML
    private Spinner<Integer> startMinutesSpinner;

    /**
     * Text field for the user to input appointment title.
     */
    @FXML
    private TextField titleField;

    /**
     * Text field for the user to input appointment type.
     */
    @FXML
    private TextField typeField;

    /**
     * ComboBox for the user to select an appointment UserID.
     */
    @FXML
    private ComboBox<Integer> userIdComboBox;

    /**
     * On Click mouse event that retrieves values from the form, validates them and then calls the createAppointment
     * query function with them.
     * @param event Triggers on click of the saveButton.
     * @throws IOException
     * @throws SQLException
     * @throws InvocationTargetException
     */
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

    /**
     * MouseEvent for the cancel button that returns the user to the directory.
     * @param event Triggers on click of the cancelButton.
     * @throws IOException
     */
    @FXML
    void cancelButtonClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("directory-view.fxml", cancelButton);
    }

    /**
     * Initialize method that sets spinner value factories and comboBox items.
     * @param url
     * @param resourceBundle
     */
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
