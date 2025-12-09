package org.example.envahissementarmorique.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.envahissementarmorique.controller.TheaterController;
import org.example.envahissementarmorique.model.theater.InvasionTheater;

/**
 * Main JavaFX Application for the Invasion Theater Simulation.
 * This is the entry point of the application.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class ArmoricaApplication extends Application {

    /**
     * The main theater instance.
     */
    private InvasionTheater theater;

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if the FXML cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        theater = new InvasionTheater("Armorique", 10);

        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/org/example/envahissementarmorique/view/theater-view.fxml")
        );

        Scene scene = new Scene(loader.load(), 1200, 800);

        TheaterController controller = loader.getController();
        controller.setTheater(theater);
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Invasion Theater - Armorique");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);

        primaryStage.show();

        controller.initialize();
    }

    /**
     * Stops the application and performs cleanup.
     *
     * @throws Exception if cleanup fails
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Application closed.");
    }

    /**
     * Main method to launch the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
