module petrizzi.scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens petrizzi.scheduler to javafx.fxml;
    exports petrizzi.scheduler;
    exports petrizzi.scheduler.controller;
    opens petrizzi.scheduler.controller to javafx.fxml;
    opens petrizzi.scheduler.model to javafx.base;
    opens petrizzi.scheduler.helper to javafx.base;


}