package petrizzi.scheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import petrizzi.scheduler.helper.JDBC;
import petrizzi.scheduler.helper.Queries;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("directory-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        //Queries.createCustomer("AAA", "BBB", "CCC", "DDD", 69);
        launch();
        JDBC.closeConnection();
    }
}