module com.example.client_hotel_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.client_hotel_project to javafx.fxml;
    exports com.example.client_hotel_project;
}