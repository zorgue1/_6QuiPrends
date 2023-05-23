module com.example._6quiprends {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example._6quiprends to javafx.fxml;
    exports com.example._6quiprends;
}