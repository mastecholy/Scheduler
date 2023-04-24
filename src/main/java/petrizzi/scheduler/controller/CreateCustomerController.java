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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CreateCustomerController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryBox.setItems(FXCollections.observableArrayList("United States", "United Kingdom", "Canada"));
        regionBox.setDisable(true);
        countryBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                regionBox.getItems().setAll("Select region.");

            }else {
                String countrySelection = countryBox.getSelectionModel().getSelectedItem();
                int cID;
                if (countrySelection == "United States") cID = 1;
                else if (countrySelection == "United Kingdom") cID = 2;
                else cID = 3;
                try {
                    List<String> regions = Queries.selectRegions(cID);
                    regionBox.getItems().setAll(regions);
                    regionBox.setDisable(false);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    @FXML
    private Button cancelButton;

    @FXML
    private Button createButton;

    @FXML
    private ComboBox<String> countryBox;

    @FXML
    private ComboBox<String> regionBox;

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
    void createButtonClick(MouseEvent event) throws IOException, SQLException {
        Queries.createCustomer(
                name.getText(),
                address.getText(),
                postalCode.getText(),
                phone.getText(),
                Queries.selectRegionID(regionBox.getValue())
        );

        HelperFunctions.changeStage("directory-view.fxml", createButton);

    }



}



