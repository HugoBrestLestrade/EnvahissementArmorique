module org.example.envahissementarmorique {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    opens org.example.envahissementarmorique.app to javafx.fxml;
    opens org.example.envahissementarmorique.controller to javafx.fxml;
    opens org.example.envahissementarmorique.model.character.base to javafx.fxml, javafx.base;
    opens org.example.envahissementarmorique.model.place to javafx.fxml, javafx.base;
    opens org.example.envahissementarmorique.model.item to javafx.fxml, javafx.base;
    opens org.example.envahissementarmorique.model.theater to javafx.fxml, javafx.base;

    exports org.example.envahissementarmorique.app;
    exports org.example.envahissementarmorique.controller;
    exports org.example.envahissementarmorique.model.character.base;
    exports org.example.envahissementarmorique.model.character.interfaces;
    exports org.example.envahissementarmorique.model.place;
    exports org.example.envahissementarmorique.model.item;
    exports org.example.envahissementarmorique.model.theater;
}