module JavaFXApplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;

    opens poov.vacinascrud to javafx.fxml, javafx.graphics;
    opens poov.vacinascrud.modelo to javafx.base, javafx.graphics;
    opens poov.modelo.dao to javafx.fxml, javafx.graphics;
    opens poov.vacinascrud.controller to javafx.fxml, javafx.graphics;
    opens poov.modelo to javafx.base;
    exports poov.vacinascrud;
}
