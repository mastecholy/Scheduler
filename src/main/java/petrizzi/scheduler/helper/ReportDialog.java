package petrizzi.scheduler.helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;

public class ReportDialog extends Dialog<Void> {

    public ReportDialog() {

        VBox content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(10));

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(10));

        Label label = new Label("Select Report Type:");
        content.getChildren().add(label);

        RadioButton report1Button = new RadioButton("Number of Customer Appointments by type and month");
        RadioButton report2Button = new RadioButton("Contact schedule");
        RadioButton report3Button = new RadioButton("Report 3");
        ToggleGroup group = new ToggleGroup();
        report1Button.setToggleGroup(group);
        report2Button.setToggleGroup(group);
        report3Button.setToggleGroup(group);
        content.getChildren().addAll(report1Button, report2Button, report3Button);
        content.getChildren().add(buttons);

        Button generateButton = new Button("Generate Report");
        generateButton.setOnAction(event -> {
            try {
                if (report1Button.isSelected()) {
                    generateReport1();
                } else if (report2Button.isSelected()) {
                    generateReport2();
                } else if (report3Button.isSelected()) {
                    generateReport3();
                } else {HelperFunctions.sendAlert(Alert.AlertType.ERROR,"No report selected.", "Please select a report to generate.");
                return;}
            }catch (Exception ignored) {};

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();]]]]]]]]]]]]]]]]]]
        });
        buttons.getChildren().add(generateButton);

        Button cancelButton = new Button("Cancel Report");
        cancelButton.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });
        buttons.getChildren().add(cancelButton);

        getDialogPane().setContent(content);
    }

    public static class ReportData {
        Month month;
        String type;
        int total;
        public Month getMonth() {
            return month;}
        public void setMonth(Month month) {
            this.month = month;}
        public String getType() {
            return type;}
        public void setType(String type) {
            this.type = type;}
        public int getTotal() {
            return total;}
        public void setTotal(int total) {
            this.total = total;}
        public ReportData(int monthNum, String type, int total) {
            this.month = Month.of(monthNum);
            this.type = type;
            this.total = total;

        }
    }

    private void generateReport1() throws SQLException {
        ObservableList<ReportData> data = FXCollections.observableArrayList();

        String sql = "SELECT MONTH(start) AS month, type, COUNT(*) AS total FROM appointments GROUP BY MONTH(start), type";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int month = rs.getInt("month");
            String type = rs.getString("type");
            int total = rs.getInt("total");

            ReportData reportData = new ReportData(month, type, total);
            data.add(reportData);

        }
        TableColumn<ReportData, Month> monthCol = new TableColumn<>("Month");
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));

        TableColumn<ReportData, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<ReportData, Integer> countCol = new TableColumn<>("Count");
        countCol.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableView<ReportData> tableView = new TableView<>();
        tableView.getColumns().add(monthCol);
        tableView.getColumns().add(typeCol);
        tableView.getColumns().add(countCol);
        tableView.setItems(data);

        Button close = new Button("Close Reports");]]]]]]]]]]]]]]]]]]]]]]]]
        close.setOnAction(event -> {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });


        Dialog<Void> dialog = new Dialog<>();
        VBox content = new VBox();
        content.setSpacing(10);
        content.setPadding(new Insets(10));
        content.getChildren().add(tableView);
        content.getChildren().add(close);
        content.setAlignment(Pos.CENTER);
        getDialogPane().setContent(content);

        dialog.show();
    }




    private void generateReport2() {
    }




    private void generateReport3() {

    }



}
