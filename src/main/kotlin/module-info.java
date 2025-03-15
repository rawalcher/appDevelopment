module org.example.appdevelopment {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens org.example.appdevelopment to javafx.fxml;
    exports org.example.appdevelopment;
}