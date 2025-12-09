package org.example.envahissementarmorique.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.place.Place;
import org.example.envahissementarmorique.model.theater.InvasionTheater;

import java.io.IOException;
import java.util.Optional;

/**
 * Controller for the main theater view.
 * Manages the theater simulation, places, and clan leaders.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class TheaterController {

    @FXML private Label theaterNameLabel;
    @FXML private Label placesCountLabel;
    @FXML private Label totalCharactersLabel;
    @FXML private ListView<Place> placesListView;
    @FXML private ListView<ClanLeader> clanLeadersListView;
    @FXML private Spinner<Integer> intervalsSpinner;
    @FXML private Button startSimulationButton;
    @FXML private Button pauseSimulationButton;
    @FXML private Button stopSimulationButton;
    @FXML private Button addPlaceButton;
    @FXML private Button removePlaceButton;
    @FXML private Button viewPlaceButton;
    @FXML private Button addLeaderButton;
    @FXML private Button removeLeaderButton;
    @FXML private ProgressBar simulationProgressBar;
    @FXML private Label simulationStatusLabel;
    @FXML private TextArea consoleTextArea;

    private InvasionTheater theater;
    private Stage primaryStage;
    private ObservableList<Place> placesObservableList;
    private ObservableList<ClanLeader> leadersObservableList;
    private boolean simulationRunning = false;

    /**
     * Sets the theater instance.
     *
     * @param theater the invasion theater
     */
    public void setTheater(InvasionTheater theater) {
        this.theater = theater;
    }

    /**
     * Sets the primary stage.
     *
     * @param primaryStage the primary stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        if (theater == null) {
            return;
        }

        placesObservableList = FXCollections.observableArrayList();
        leadersObservableList = FXCollections.observableArrayList();

        placesListView.setItems(placesObservableList);
        placesListView.setCellFactory(lv -> new ListCell<Place>() {
            @Override
            protected void updateItem(Place place, boolean empty) {
                super.updateItem(place, empty);
                if (empty || place == null) {
                    setText(null);
                } else {
                    setText(place.getName() + " (" + place.getNumberOfCharacters() + " characters)");
                }
            }
        });

        clanLeadersListView.setItems(leadersObservableList);
        clanLeadersListView.setCellFactory(lv -> new ListCell<ClanLeader>() {
            @Override
            protected void updateItem(ClanLeader leader, boolean empty) {
                super.updateItem(leader, empty);
                if (empty || leader == null) {
                    setText(null);
                } else {
                    setText(leader.getName() + " - " +
                           (leader.getPlace() != null ? leader.getPlace().getName() : "No place"));
                }
            }
        });

        intervalsSpinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 5)
        );

        updateUI();
        logToConsole("Application started. Welcome to Invasion Theater!");
    }

    /**
     * Updates the UI with current theater state.
     */
    private void updateUI() {
        if (theater == null) return;

        theaterNameLabel.setText(theater.getName());
        placesCountLabel.setText(theater.getPlaces().size() + "/" + theater.getMaxPlaces());
        totalCharactersLabel.setText(String.valueOf(theater.getTotalCharacters()));

        placesObservableList.setAll(theater.getPlaces());
        leadersObservableList.setAll(theater.getClanLeaders());
    }

    /**
     * Logs a message to the console.
     *
     * @param message the message to log
     */
    private void logToConsole(String message) {
        Platform.runLater(() -> {
            consoleTextArea.appendText("[" + java.time.LocalTime.now() + "] " + message + "\n");
        });
    }

    /**
     * Handles adding a new place.
     */
    @FXML
    private void handleAddPlace() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/envahissementarmorique/view/place-dialog.fxml")
            );

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create New Place");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(new Scene(loader.load()));

            PlaceDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTheater(theater);
            controller.initializeClanLeaders();

            dialogStage.showAndWait();

            if (controller.isConfirmed()) {
                Place newPlace = controller.getCreatedPlace();
                if (newPlace != null) {
                    theater.addPlace(newPlace);
                    updateUI();
                    logToConsole("Place created: " + newPlace.getName());
                }
            }
        } catch (IOException e) {
            showError("Error", "Could not open place creation dialog: " + e.getMessage());
        }
    }

    /**
     * Handles removing a place.
     */
    @FXML
    private void handleRemovePlace() {
        Place selectedPlace = placesListView.getSelectionModel().getSelectedItem();
        if (selectedPlace == null) {
            showWarning("No Selection", "Please select a place to remove.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Remove Place");
        alert.setContentText("Are you sure you want to remove " + selectedPlace.getName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            theater.removePlace(selectedPlace);
            updateUI();
            logToConsole("Place removed: " + selectedPlace.getName());
        }
    }

    /**
     * Handles viewing place details.
     */
    @FXML
    private void handleViewPlace() {
        Place selectedPlace = placesListView.getSelectionModel().getSelectedItem();
        if (selectedPlace == null) {
            showWarning("No Selection", "Please select a place to view.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/envahissementarmorique/view/place-view.fxml")
            );

            Stage placeStage = new Stage();
            placeStage.setTitle("Place Details - " + selectedPlace.getName());
            placeStage.initModality(Modality.WINDOW_MODAL);
            placeStage.initOwner(primaryStage);
            placeStage.setScene(new Scene(loader.load()));

            PlaceController controller = loader.getController();
            controller.setPlace(selectedPlace);
            controller.setDialogStage(placeStage);
            controller.initialize();

            placeStage.showAndWait();
            updateUI();
        } catch (IOException e) {
            showError("Error", "Could not open place view: " + e.getMessage());
        }
    }

    /**
     * Handles adding a clan leader.
     */
    @FXML
    private void handleAddLeader() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create Clan Leader");
        dialog.setHeaderText("Create a New Clan Leader");
        dialog.setContentText("Enter leader name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                ClanLeader leader = new ClanLeader(name, "M", 45, null);
                theater.addClanLeader(leader);
                updateUI();
                logToConsole("Clan leader created: " + name);
            }
        });
    }

    /**
     * Handles removing a clan leader.
     */
    @FXML
    private void handleRemoveLeader() {
        ClanLeader selectedLeader = clanLeadersListView.getSelectionModel().getSelectedItem();
        if (selectedLeader == null) {
            showWarning("No Selection", "Please select a clan leader to remove.");
            return;
        }

        theater.removeClanLeader(selectedLeader);
        updateUI();
        logToConsole("Clan leader removed: " + selectedLeader.getName());
    }

    /**
     * Handles starting the simulation.
     */
    @FXML
    private void handleStartSimulation() {
        if (theater.getPlaces().isEmpty()) {
            showWarning("No Places", "Please add at least one place before starting the simulation.");
            return;
        }

        simulationRunning = true;
        startSimulationButton.setDisable(true);
        pauseSimulationButton.setDisable(false);
        stopSimulationButton.setDisable(false);

        int intervals = intervalsSpinner.getValue();
        simulationStatusLabel.setText("Simulation running...");
        logToConsole("Starting simulation with " + intervals + " intervals");

        new Thread(() -> {
            try {
                for (int i = 1; i <= intervals; i++) {
                    if (!simulationRunning) break;

                    final int currentInterval = i;
                    Platform.runLater(() -> {
                        simulationProgressBar.setProgress((double) currentInterval / intervals);
                        simulationStatusLabel.setText("Interval " + currentInterval + " / " + intervals);
                        logToConsole("=== INTERVAL " + currentInterval + " ===");
                    });

                    logToConsole("Conducting battles...");
                    theater.conductBattles();

                    Thread.sleep(500);

                    logToConsole("Modifying character states...");
                    theater.randomlyModifyCharacters();

                    Thread.sleep(500);

                    logToConsole("Spawning food...");
                    theater.spawnFood();

                    Thread.sleep(500);

                    logToConsole("Degrading food...");
                    theater.degradeFood();

                    Thread.sleep(1000);

                    Platform.runLater(this::updateUI);
                }

                Platform.runLater(() -> {
                    simulationStatusLabel.setText("Simulation completed!");
                    simulationProgressBar.setProgress(1.0);
                    startSimulationButton.setDisable(false);
                    pauseSimulationButton.setDisable(true);
                    stopSimulationButton.setDisable(true);
                    logToConsole("Simulation completed successfully!");
                });

            } catch (InterruptedException e) {
                Platform.runLater(() -> {
                    simulationStatusLabel.setText("Simulation interrupted");
                    logToConsole("Simulation interrupted: " + e.getMessage());
                });
            }
        }).start();
    }

    /**
     * Handles pausing the simulation.
     */
    @FXML
    private void handlePauseSimulation() {
        simulationRunning = false;
        pauseSimulationButton.setDisable(true);
        simulationStatusLabel.setText("Simulation paused");
        logToConsole("Simulation paused by user");
    }

    /**
     * Handles stopping the simulation.
     */
    @FXML
    private void handleStopSimulation() {
        simulationRunning = false;
        startSimulationButton.setDisable(false);
        pauseSimulationButton.setDisable(true);
        stopSimulationButton.setDisable(true);
        simulationProgressBar.setProgress(0);
        simulationStatusLabel.setText("Simulation stopped");
        logToConsole("Simulation stopped by user");
    }

    /**
     * Handles viewing statistics.
     */
    @FXML
    private void handleViewStatistics() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/envahissementarmorique/view/statistics-view.fxml")
            );

            Stage statsStage = new Stage();
            statsStage.setTitle("Theater Statistics");
            statsStage.initModality(Modality.WINDOW_MODAL);
            statsStage.initOwner(primaryStage);
            statsStage.setScene(new Scene(loader.load()));

            StatisticsController controller = loader.getController();
            controller.setTheater(theater);
            controller.setDialogStage(statsStage);
            controller.initialize();

            statsStage.showAndWait();
        } catch (IOException e) {
            showError("Error", "Could not open statistics view: " + e.getMessage());
        }
    }

    /**
     * Handles viewing all characters.
     */
    @FXML
    private void handleViewAllCharacters() {
        theater.displayAllCharacters();
        logToConsole("Displaying all characters in console");
    }

    /**
     * Handles clearing the console.
     */
    @FXML
    private void handleClearConsole() {
        consoleTextArea.clear();
    }

    /**
     * Shows an error alert.
     *
     * @param title the alert title
     * @param message the error message
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows a warning alert.
     *
     * @param title the alert title
     * @param message the warning message
     */
    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

