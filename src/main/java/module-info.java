module com.example.anilist {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.anilist to javafx.fxml;
    exports com.example.anilist;
}