package petrizzi.scheduler.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import petrizzi.scheduler.helper.HelperFunctions;
import petrizzi.scheduler.helper.Queries;
import petrizzi.scheduler.model.Customer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the edit-customer-view.fxml that allows the user to edit the selected customer information.
 */
public class EditCustomerController implements Initializable {

    /**
     * Initialize method that sets the combo boxes, adds a listener for combo box function and populates the form. -
     * CONTAINS LAMBDA
     * <p>
     * Sets the items of the countryBox and regionBox, with a listener that updates the regionBox
     * with the regions of the selected country from the database. Populates all of the fields with the information
     * from the selected customer.
     * <p>
     * LAMBDA JUSTIFICATION - This lambda allows the listener conditions to be set directly in the
     * initialize method. Since the main purpose for this controller's initialize method is to
     * set and control the combo box behavior, it allows for the logic and functionality for the
     * affected nodes to be in one location.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(FXCollections.observableArrayList("United States", "United Kingdom", "Canada"));
        try {
            setCustomer(DirectoryController.selectedCustomer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        countryBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                regionBox.getItems().setAll("Select region.");

            }else {
                String countrySelection = countryBox.getSelectionModel().getSelectedItem();
                int cID;
                if (Objects.equals(countrySelection, "United States")) cID = 1;
                else if (Objects.equals(countrySelection, "United Kingdom")) cID = 2;
                else cID = 3;
                try {
                    List<String> regions = Queries.selectRegions(cID);
                    regionBox.getItems().setAll(regions);
                    regionBox.setDisable(false);
                    regionBox.setValue("Select region.");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> countryBox;

    @FXML
    private ComboBox<String> regionBox;

    @FXML
    private TextField ID;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField postalCode;

    @FXML
    private TextField phone;

    @FXML
    private int regionID;

    /**
     * MouseEvent for the cancel button that returns the user to the directory without
     * submitting any information.
     * @param event cancelButton on mouse click.
     * @throws IOException
     */
    @FXML
    void cancelButtonClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("directory-view.fxml", cancelButton);
    }

    /**
     * MouseEvent for the saveButton that validates the user's input and then updates
     * the selected customer information with a query method.
     * @param event saveButton on click mouse event.
     * @throws IOException
     * @throws SQLException
     * @throws InvocationTargetException
     */
    @FXML
    void saveButtonClick(MouseEvent event) throws IOException, SQLException, InvocationTargetException {
        if(name.getText().isBlank() || address.getText().isBlank() || postalCode.getText().isBlank() || phone.getText().isBlank()) {
            HelperFunctions.sendAlert(Alert.AlertType.ERROR, "Invalid Customer Information", "Please fill ALL fields with valid information and data types.");
            return;
        }
        try {
            Queries.updateCustomer(
                    Integer.parseInt(ID.getText()),
                    name.getText(),
                    address.getText(),
                    postalCode.getText(),
                    phone.getText(),
                    Queries.selectRegionID(regionBox.getValue()));
            HelperFunctions.changeStage("directory-view.fxml", saveButton);
        } catch (Exception e) {
            HelperFunctions.sendAlert(Alert.AlertType.ERROR, "Invalid Customer Information", "Please fill ALL fields with valid information and data types.");
        }
    }


    /**
     * Method called in initialize that sets all the form fields with
     * the selected customer's correlated information.
     * @param customer selected customer passed from directory form.
     * @throws SQLException
     */
    public void setCustomer(Customer customer) throws SQLException {

        ID.setText(String.valueOf(customer.getCustomerID()));
        name.setText(customer.getCustomerName());
        address.setText(customer.getAddress());
        postalCode.setText(customer.getPostalCode());
        phone.setText(customer.getPhone());
        int custDivID = customer.getDivisionID();
        countryBox.setValue(Queries.selectCountry(custDivID));
        regionBox.setValue(Queries.selectRegionName(custDivID));
    }



}



