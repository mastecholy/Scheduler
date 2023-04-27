package petrizzi.scheduler.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;
import petrizzi.scheduler.Main;
import petrizzi.scheduler.helper.HelperFunctions;
import petrizzi.scheduler.helper.JDBC;
import petrizzi.scheduler.helper.Queries;
import petrizzi.scheduler.helper.ReportDialog;
import petrizzi.scheduler.model.Appointment;
import petrizzi.scheduler.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class DirectoryController implements Initializable {

    public static Customer selectedCustomer;
    public static Appointment selectedAppointment;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

        apptIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        apptContactColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentContactName"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        apptStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startString"));
        apptEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endString"));
        apptCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentCustomerID"));
        apptUserIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentUserID"));

        try {
            populateCustomersTableView(customerTableView);
            populateAppointmentsTableView(appointmentTableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Appointment> appointments= FXCollections.observableArrayList();
        appointments.addAll(appointmentTableView.getItems());


        FilteredList<Appointment> monthFilteredList = new FilteredList<>(appointments, appointment ->
                appointment.getAppointmentStart().getMonth().equals(LocalDateTime.now().getMonth()) &&
                        appointment.getAppointmentStart().getYear() == LocalDateTime.now().getYear());
        FilteredList<Appointment> weekFilteredList = new FilteredList<>(appointments, appointment ->
                appointment.getAppointmentStart().get(WeekFields.ISO.weekOfYear()) ==
                        LocalDateTime.now().get(WeekFields.ISO.weekOfYear()));

        apptFilter.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == allRadio) {
                appointmentTableView.setItems(appointments);
            } else if (newValue == monthRadio) {
                appointmentTableView.setItems(monthFilteredList);
            } else if (newValue == weekRadio) {
                appointmentTableView.setItems(weekFilteredList);
            }
        });


    }

    @FXML
    private TableColumn<Customer, Integer> customerIDColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerAddressColumn;

    @FXML
    private TableColumn<Customer, String> customerPostalCodeColumn;

    @FXML
    private TableColumn<Customer, String> customerPhoneNumberColumn;

    @FXML
    private TableColumn<Customer, Integer> customerDivisionIDColumn;

    @FXML
    private TableColumn<Appointment, String> apptContactColumn;

    @FXML
    private TableColumn<Appointment, Integer> apptCustomerIDColumn;

    @FXML
    private TableColumn<Appointment, String> apptDescriptionColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apptEndTimeColumn;

    @FXML
    private ToggleGroup apptFilter = new ToggleGroup();

    @FXML
    private RadioButton allRadio;

    @FXML
    private RadioButton monthRadio;

    @FXML
    private RadioButton weekRadio;

    @FXML
    private TableColumn<Appointment, Integer> apptIDColumn;

    @FXML
    private TableColumn<Appointment, String> apptLocationColumn;

    @FXML
    private TableColumn<Appointment, LocalDateTime> apptStartTimeColumn;

    @FXML
    private TableColumn<Appointment, String> apptTitleColumn;

    @FXML
    private TableColumn<Appointment, String> apptTypeColumn;

    @FXML
    private TableColumn<Appointment, Integer> apptUserIDColumn;


    public void populateCustomersTableView(TableView<Customer> tableView) throws SQLException {

        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM CUSTOMERS";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        tableView.getItems().clear();

        while (rs.next()) {
            Customer customer = new Customer(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getInt("Division_ID")
            );
            tableView.getItems().add(customer);
        }
    }

    public void populateAppointmentsTableView(TableView<Appointment> tableView) throws SQLException {

        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        tableView.getItems().clear();

        while (rs.next()) {
            Appointment appointment = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );

            tableView.getItems().add(appointment);
        }
    }



    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableView<Appointment> appointmentTableView;



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
    private Button logoutButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button removeApptButton;

    @FXML
    void addCustomerClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("create-customer-view.fxml", addCustomerButton);
    }

    @FXML
    void editApptClick(MouseEvent event) throws IOException {
        if (appointmentTableView.getSelectionModel().getSelectedItem()==null) {
            HelperFunctions.sendAlert(Alert.AlertType.ERROR, "No customer selected.", "You must select an appointment to edit.");
            return;
        }
        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        HelperFunctions.changeStage("edit-appt-view.fxml", editApptButton);
    }

    @FXML
    void addApptClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("create-appt-view.fxml", addApptButton);
    }

    @FXML
    void editCustomerClick(MouseEvent event) throws IOException {
        if (customerTableView.getSelectionModel().getSelectedItem()==null) {
            HelperFunctions.sendAlert(Alert.AlertType.ERROR, "No customer selected.", "You must select a customer to edit.");
            return;
        }
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        HelperFunctions.changeStage("edit-customer-view.fxml", editCustomerButton);
    }

    @FXML
    void removeCustomerClick(MouseEvent event) throws SQLException, IOException {
        if (customerTableView.getSelectionModel().getSelectedItem()==null) {
            HelperFunctions.sendAlert(Alert.AlertType.ERROR, "No customer selected.", "You must select a customer to remove.");
            return;
        }
        if (HelperFunctions.sendAlert(Alert.AlertType.CONFIRMATION, "Delete customer?",
                "Are you sure you want to permanently delete this customer and all of their appointments?")) {
            selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
            int cID = selectedCustomer.getCustomerID();
            Queries.deleteCustomer(cID);
            populateCustomersTableView(customerTableView);
            populateAppointmentsTableView(appointmentTableView);
            HelperFunctions.sendAlert(Alert.AlertType.INFORMATION, "Customer deleted.",
                    "Customer and related appointments have been permanently deleted.");}
    }

    @FXML
    void removeAppointmentClick(MouseEvent event) throws SQLException {
        if (appointmentTableView.getSelectionModel().getSelectedItem()==null) {
            HelperFunctions.sendAlert(Alert.AlertType.ERROR, "No customer selected.", "You must select an appointment to remove.");
            return;
        }
        if (HelperFunctions.sendAlert(Alert.AlertType.CONFIRMATION, "Delete appointment?",
                "Are you sure you want to permanently delete this appointment?")){
            selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
            int aID = selectedAppointment.getAppointmentID();
            Queries.deleteAppointment(aID);
            populateAppointmentsTableView(appointmentTableView);
            HelperFunctions.sendAlert(Alert.AlertType.INFORMATION, "Appointment deleted.",
                    "Appointment has been permanently deleted.");}
        }

    @FXML
    void reportsClick(MouseEvent event){
        ReportDialog dialog = new ReportDialog();
        dialog.showAndWait();

    }

    @FXML
    void exitClick(MouseEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void logoutClick(MouseEvent event) throws IOException{
        HelperFunctions.changeStage("login-view.fxml", logoutButton);
        JDBC.closeConnection();
    }

}
