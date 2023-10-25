module com.example.cs213project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cs213project3 to javafx.fxml;
    exports com.cs213project3;
}