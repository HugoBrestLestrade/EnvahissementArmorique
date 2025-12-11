package org.example.envahissementarmorique.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    @FXML private Label currentRoundLabel;
    @FXML private Label battlesCountLabel;
    @FXML private Label casualtiesCountLabel;
    @FXML private Label foodSpawnedLabel;
    @FXML private VBox combatLogContainer;

    private InvasionTheater theater;
    private Stage primaryStage;
    private ObservableList<Place> placesObservableList;
    private ObservableList<ClanLeader> leadersObservableList;
    private boolean simulationRunning = false;
    private int currentRound = 0;
    private int totalBattles = 0;
    private int totalCasualties = 0;
    private int totalFoodSpawned = 0;

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
                    getClass().getResource("/org/example/envahissementarmorique/view/place-dialog.fxml")            );

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

        // Reset counters
        currentRound = 0;
        totalBattles = 0;
        totalCasualties = 0;
        totalFoodSpawned = 0;

        int intervals = intervalsSpinner.getValue();
        simulationStatusLabel.setText("Simulation running...");
        logToConsole("Starting simulation with " + intervals + " intervals");

        new Thread(() -> {
            try {
                for (int i = 1; i <= intervals; i++) {
                    if (!simulationRunning) break;

                    final int currentInterval = i;
                    currentRound = i;

                    Platform.runLater(() -> {
                        simulationProgressBar.setProgress((double) currentInterval / intervals);
                        simulationStatusLabel.setText("Interval " + currentInterval + " / " + intervals);
                        currentRoundLabel.setText("Round: " + currentInterval);
                        logToConsole("=== INTERVAL " + currentInterval + " ===");

                        // Add separator for new round (keep previous rounds in history)
                        addCombatLogSeparator("ROUND " + currentInterval);
                    });

                    // Conduct battles and capture results
                    logToConsole("Conducting battles...");
                    var combatResults = theater.conductBattles();

                    Platform.runLater(() -> {
                        int roundCasualties = 0;
                        for (var result : combatResults) {
                            addCombatEntry(result);
                            if (result.hasCasualty()) {
                                roundCasualties += (result.isFighter1Died() ? 1 : 0) + (result.isFighter2Died() ? 1 : 0);
                            }
                        }

                        totalBattles += combatResults.size();
                        totalCasualties += roundCasualties;

                        battlesCountLabel.setText(String.valueOf(combatResults.size()));
                        casualtiesCountLabel.setText(String.valueOf(roundCasualties));
                    });

                    Thread.sleep(500);

                    // Modify character states
                    logToConsole("Modifying character states...");
                    var stateChanges = theater.randomlyModifyCharacters();
                    Platform.runLater(() -> {
                        if (!stateChanges.isEmpty()) {
                            addCombatLogSeparator("Character State Changes");
                            for (String message : stateChanges) {
                                addEventEntry(message, "warning");
                            }
                        }
                    });

                    Thread.sleep(500);

                    // Spawn food
                    logToConsole("Spawning food...");
                    var foodMessages = theater.spawnFood();
                    Platform.runLater(() -> {
                        totalFoodSpawned += foodMessages.size();
                        foodSpawnedLabel.setText(String.valueOf(foodMessages.size()));

                        if (!foodMessages.isEmpty()) {
                            addCombatLogSeparator("Food Spawning");
                            for (String message : foodMessages) {
                                addEventEntry(message, "food");
                            }
                        }
                    });

                    Thread.sleep(500);

                    // Degrade food
                    logToConsole("Degrading food...");
                    var degradationMessages = theater.degradeFood();
                    Platform.runLater(() -> {
                        if (!degradationMessages.isEmpty()) {
                            addCombatLogSeparator("Food Degradation");
                            for (String message : degradationMessages) {
                                addEventEntry(message, "event");
                            }
                        }
                    });

                    Thread.sleep(1000);

                    Platform.runLater(() -> {
                        updateUI();
                        addCombatLogSeparator("End of Round " + currentInterval);
                        logToConsole("Round " + currentInterval + " complete - Battles: " + combatResults.size() +
                                    ", Casualties: " + (casualtiesCountLabel.getText()) +
                                    ", Food: " + foodMessages.size());
                    });

                    Thread.sleep(500);
                }

                Platform.runLater(() -> {
                    simulationStatusLabel.setText("Simulation completed!");
                    simulationProgressBar.setProgress(1.0);
                    startSimulationButton.setDisable(false);
                    pauseSimulationButton.setDisable(true);
                    stopSimulationButton.setDisable(true);
                    logToConsole("Simulation completed successfully!");
                    logToConsole("Total Statistics - Battles: " + totalBattles +
                               ", Casualties: " + totalCasualties +
                               ", Food Spawned: " + totalFoodSpawned);
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

    /**
     * Adds a combat entry to the combat log.
     *
     * @param result the combat result
     */
    private void addCombatEntry(org.example.envahissementarmorique.model.theater.CombatResult result) {
        VBox entry = new VBox(5);
        entry.getStyleClass().addAll("combat-entry", "combat-entry-battle");
        entry.setPadding(new Insets(10));

        // Combat header
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Label icon = new Label("⚔️");
        icon.getStyleClass().add("combat-icon");
        icon.setStyle("-fx-font-size: 16px;");

        Label fighters = new Label(result.getFighter1Name() + " (" + result.getFighter1Faction() + ") vs " +
                                   result.getFighter2Name() + " (" + result.getFighter2Faction() + ")");
        fighters.getStyleClass().addAll("combat-text", "combat-text-fighter");
        fighters.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        header.getChildren().addAll(icon, fighters);

        // Health before combat
        HBox beforeHealth = new HBox(20);
        beforeHealth.setPadding(new Insets(5, 0, 0, 25));

        int fighter1HealthBefore = result.getFighter1HealthAfter() + result.getDamageToFighter1();
        int fighter2HealthBefore = result.getFighter2HealthAfter() + result.getDamageToFighter2();

        Label beforeLabel = new Label("Before: " + result.getFighter1Name() + " [HP: " + fighter1HealthBefore + "] | " +
                                     result.getFighter2Name() + " [HP: " + fighter2HealthBefore + "]");
        beforeLabel.getStyleClass().add("combat-text");
        beforeLabel.setStyle("-fx-font-style: italic; -fx-text-fill: #7f8c8d;");

        beforeHealth.getChildren().add(beforeLabel);

        // Combat details - damage dealt
        HBox damageDealt = new HBox(20);
        damageDealt.setPadding(new Insets(2, 0, 0, 25));

        Label damageInfo = new Label("Damage: " + result.getFighter1Name() + " dealt " + result.getDamageToFighter2() + " HP | " +
                                    result.getFighter2Name() + " dealt " + result.getDamageToFighter1() + " HP");
        damageInfo.getStyleClass().add("combat-text");
        damageInfo.setStyle("-fx-font-weight: bold; -fx-text-fill: #e74c3c;");

        damageDealt.getChildren().add(damageInfo);

        // Health after combat
        HBox details = new HBox(20);
        details.setPadding(new Insets(2, 0, 0, 25));

        Label damage1 = new Label("After: " + result.getFighter1Name() + " → " +
                                  result.getFighter1HealthAfter() + " HP");
        damage1.getStyleClass().addAll("combat-text", result.isFighter1Died() ? "combat-text-death" : "combat-text-damage");
        damage1.setStyle(result.isFighter1Died() ? "-fx-text-fill: #c0392b; -fx-font-weight: bold;" : "-fx-text-fill: #27ae60;");

        Label damage2 = new Label(result.getFighter2Name() + " → " +
                                  result.getFighter2HealthAfter() + " HP");
        damage2.getStyleClass().addAll("combat-text", result.isFighter2Died() ? "combat-text-death" : "combat-text-damage");
        damage2.setStyle(result.isFighter2Died() ? "-fx-text-fill: #c0392b; -fx-font-weight: bold;" : "-fx-text-fill: #27ae60;");

        details.getChildren().addAll(damage1, damage2);

        entry.getChildren().addAll(header, beforeHealth, damageDealt, details);

        // Add death notification if applicable
        if (result.hasCasualty()) {
            HBox deathBox = new HBox(5);
            deathBox.setPadding(new Insets(5, 0, 0, 25));
            deathBox.setAlignment(Pos.CENTER_LEFT);

            Label skullIcon = new Label("💀");
            skullIcon.getStyleClass().add("combat-icon");
            skullIcon.setStyle("-fx-font-size: 16px;");

            StringBuilder deaths = new StringBuilder();
            if (result.isFighter1Died()) {
                deaths.append(result.getFighter1Name());
            }
            if (result.isFighter2Died()) {
                if (!deaths.isEmpty()) deaths.append(" and ");
                deaths.append(result.getFighter2Name());
            }
            deaths.append(" has fallen!");

            Label deathLabel = new Label(deaths.toString());
            deathLabel.getStyleClass().addAll("combat-text", "combat-text-death");
            deathLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #c0392b; -fx-font-size: 13px;");

            deathBox.getChildren().addAll(skullIcon, deathLabel);
            entry.getChildren().add(deathBox);
            entry.getStyleClass().add("combat-entry-death");
        }

        combatLogContainer.getChildren().add(entry);
    }

    /**
     * Adds an event entry to the combat log.
     *
     * @param message the event message
     * @param type the type of event (food, event, warning)
     */
    private void addEventEntry(String message, String type) {
        HBox entry = new HBox(10);
        entry.getStyleClass().add("combat-entry");
        entry.setPadding(new Insets(8));
        entry.setAlignment(Pos.CENTER_LEFT);

        String icon;
        String styleClass;
        switch (type) {
            case "food":
                icon = "🍖";
                styleClass = "combat-entry-food";
                break;
            case "warning":
                icon = "⚠️";
                styleClass = "combat-entry-warning";
                break;
            default:
                icon = "ℹ️";
                styleClass = "combat-entry-event";
        }

        entry.getStyleClass().add(styleClass);

        Label iconLabel = new Label(icon);
        iconLabel.getStyleClass().add("combat-icon");

        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("combat-text");
        messageLabel.setWrapText(true);

        entry.getChildren().addAll(iconLabel, messageLabel);
        combatLogContainer.getChildren().add(entry);
    }

    /**
     * Adds a separator with text to the combat log.
     *
     * @param text the separator text
     */
    private void addCombatLogSeparator(String text) {
        VBox separator = new VBox(5);
        separator.setPadding(new Insets(15, 0, 10, 0));
        separator.setAlignment(Pos.CENTER);
        separator.setStyle("-fx-background-color: #ecf0f1; -fx-background-radius: 5;");

        Label label = new Label("═══ " + text + " ═══");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2c3e50; -fx-padding: 5;");

        Separator line = new Separator();
        line.setMaxWidth(Double.MAX_VALUE);
        line.setStyle("-fx-background-color: #3498db;");

        separator.getChildren().addAll(line, label, new Separator());
        combatLogContainer.getChildren().add(separator);
    }
}
