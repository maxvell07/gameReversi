module com.example.gamereversi {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires org.junit.jupiter.api;


    opens com.example.gamereversi to javafx.fxml;
    exports com.example.gamereversi;
}