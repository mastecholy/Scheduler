package petrizzi.scheduler.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import petrizzi.scheduler.Main;
import petrizzi.scheduler.helper.HelperFunctions;
import petrizzi.scheduler.helper.JDBC;
import petrizzi.scheduler.model.Appointment;
import petrizzi.scheduler.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DirectoryController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        try {
            populateCustomersTableView(customerTableView);
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
        HelperFunctions.changeStage("edit-customer-view.fxml", editCustomerButton);
    }

    @FXML
    void removeCustomerClick(MouseEvent event) {

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
    }

}
