module com.imc.sample.imc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.imc.sample.imc to javafx.fxml;
    exports com.imc.sample.imc;
}