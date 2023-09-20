module com.hms.hospitalmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.hms.hospitalmanagementsystem to javafx.fxml;
    exports com.hms.hospitalmanagementsystem;
}