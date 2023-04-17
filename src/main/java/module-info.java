module petrizzi.scheduler {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens petrizzi.scheduler to javafx.fxml;
    exports petrizzi.scheduler;
    exports petrizzi.scheduler.controller;
    opens petrizzi.scheduler.controller to javafx.fxml;
}