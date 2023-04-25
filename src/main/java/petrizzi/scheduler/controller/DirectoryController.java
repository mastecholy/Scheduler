package petrizzi.scheduler.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import petrizzi.scheduler.Main;
import petrizzi.scheduler.helper.HelperFunctions;
import petrizzi.scheduler.helper.JDBC;
import petrizzi.scheduler.helper.Queries;
import petrizzi.scheduler.model.Appointment;
import petrizzi.scheduler.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DirectoryController implements Initializable {

    public static Customer selectedCustomer;

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
    private ToggleGroup apptFilter;

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

        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (rs.next()) {
            Appointment appointment = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    Timestamp.valueOf(rs.getString("Start")).toLocalDateTime(),
                    Timestamp.valueOf(rs.getString("End")).toLocalDateTime(),
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
        HelperFunctions.changeStage("edit-appt-view.fxml", editApptButton);
    }

    @FXML
    void addApptClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("create-appt-view.fxml", addApptButton);
    }

    @FXML
    void editCustomerClick(MouseEvent event) throws IOException {
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        HelperFunctions.changeStage("edit-customer-view.fxml", editCustomerButton);
    }

    @FXML
    void removeCustomerClick(MouseEvent event) throws SQLException, IOException {
        if (HelperFunctions.sendAlert(Alert.AlertType.CONFIRMATION, "Delete customer?",
                "Are you sure you want to permanently delete this customer and all of their appointments?")) {
            selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
            int cID = selectedCustomer.getCustomerID();
            Queries.deleteCustomer(cID);
            populateCustomersTableView(customerTableView);
            HelperFunctions.sendAlert(Alert.AlertType.INFORMATION, "Customer deleted.",
                    "Customer and related appointments have been permanently deleted.");}
    }

    @FXML
    void removeApptClick(MouseEvent event) {

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
