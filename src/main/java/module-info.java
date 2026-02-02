module com.example.anilist {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens com.example.anilist to javafx.fxml;
    exports com.example.anilist;
}