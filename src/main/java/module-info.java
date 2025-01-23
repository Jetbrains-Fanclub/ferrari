module dk.eamv.ferrari {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.simplejavamail;
    requires org.simplejavamail.core;

    opens dk.eamv.ferrari to javafx.fxml;
    exports dk.eamv.ferrari;
}
