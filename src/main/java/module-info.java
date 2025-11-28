module org.example.envahissementarmorique {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.envahissementarmorique.app to javafx.fxml;
    exports org.example.envahissementarmorique.app;
}