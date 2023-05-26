module com.example._6quiprends {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example._6quiprend to javafx.fxml;
    exports com.example._6quiprend;
}