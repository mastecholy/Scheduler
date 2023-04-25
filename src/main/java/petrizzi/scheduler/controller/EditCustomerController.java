package petrizzi.scheduler.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import petrizzi.scheduler.helper.HelperFunctions;
import petrizzi.scheduler.helper.Queries;
import petrizzi.scheduler.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditCustomerController implements Initializable {

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


    @FXML
    void cancelButtonClick(MouseEvent event) throws IOException {
        HelperFunctions.changeStage("directory-view.fxml", cancelButton);
    }

    @FXML
    void saveButtonClick(MouseEvent event) throws IOException, SQLException {
        Queries.updateCustomer(
                Integer.parseInt(ID.getText()),
                name.getText(),
                address.getText(),
                postalCode.getText(),
                phone.getText(),
                Queries.selectRegionID(regionBox.getValue()));
        HelperFunctions.changeStage("directory-view.fxml", saveButton);
    }



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



