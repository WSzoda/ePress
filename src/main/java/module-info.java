module com.example.projekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projekt to javafx.fxml;
    exports com.example.projekt;
    exports com.example.projekt.controllers;
    opens com.example.projekt.controllers to javafx.fxml;
}