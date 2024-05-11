module com.example.hotel_client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.hotel_client to javafx.fxml;
    exports com.example.hotel_client;
}