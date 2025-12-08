module org.example.envahissementarmorique {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens org.example.envahissementarmorique to javafx.fxml;

    exports org.example.envahissementarmorique.model.character.base.Lycan;
    exports org.example.envahissementarmorique.model.PackAndAlpha;
    exports org.example.envahissementarmorique.model.Yell;
}
