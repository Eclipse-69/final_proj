module proj.proj2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens proj.proj2 to javafx.fxml;
    exports proj.proj2;
}