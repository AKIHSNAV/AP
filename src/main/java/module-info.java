module com.example.hello {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires org.testng;


    opens com.example.hello to javafx.fxml;
    exports com.example.hello;
}