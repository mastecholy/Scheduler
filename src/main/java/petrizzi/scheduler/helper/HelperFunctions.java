package petrizzi.scheduler.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import petrizzi.scheduler.Main;
import petrizzi.scheduler.controller.LoginController;
import petrizzi.scheduler.model.Appointment;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public class HelperFunctions {

    public static void changeStage(String fxmlFileName, Node node) throws IOException {
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFileName));
        Object root = fxmlLoader.load();
        Scene scene = new Scene((Parent) root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.show();
    }


    public static boolean sendAlert(Alert.AlertType alertType, String title, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return false;
        }
        return true;
    };

    public static boolean sendAlert(Alert.AlertType alertType, String title, String headerText, String content){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(headerText);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return false;
        }
        return true;
    };

    public static void greeting() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            appointments.setAll(Queries.selectAppointments());
            for (Appointment appointment : appointments) {
                if (appointment.getAppointmentStart().isAfter(LocalDateTime.now()) &&
                        appointment.getAppointmentStart().isBefore(LocalDateTime.now().plusMinutes(15))) {
                    HelperFunctions.sendAlert(Alert.AlertType.INFORMATION, "Upcoming Appointment",
                            "You have an appointment starting in soon!",
                            "Appointment ID: " + appointment.getAppointmentID() +
                                    "\nAppointment Time: " + appointment.getStartString());
                    return;
                }
            }
            HelperFunctions.sendAlert(Alert.AlertType.INFORMATION, "Hello!",
                    "Welcome to the Scheduler.", "You have no imminent appointments.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
