package org.example.envahissementarmorique.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.item.Foods;
import org.example.envahissementarmorique.model.item.Freshness;
import org.example.envahissementarmorique.model.place.Place;

import java.io.IOException;
import java.util.Optional;

/**
 * Controller for the place detail view.
 * Manages character and food operations for a specific place.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class PlaceController {

    @FXML private Label placeNameLabel;
    @FXML private Label placeTypeLabel;
    @FXML private Label placeAreaLabel;
    @FXML private Label chiefNameLabel;
    @FXML private Label charactersCountLabel;
    @FXML private Label foodCountLabel;
    @FXML private TableView<GameCharacter> charactersTableView;
    @FXML private TableColumn<GameCharacter, String> nameColumn;
    @FXML private TableColumn<GameCharacter, String> factionColumn;
    @FXML private TableColumn<GameCharacter, Integer> healthColumn;
    @FXML private TableColumn<GameCharacter, Integer> hungerColumn;
    @FXML private TableColumn<GameCharacter, String> statusColumn;
    @FXML private ListView<Food> foodListView;
    @FXML private Button addCharacterButton;
    @FXML private Button removeCharacterButton;
    @FXML private Button viewCharacterButton;

    private Place place;
    private Stage dialogStage;
    private ObservableList<GameCharacter> charactersObservableList;
    private ObservableList<Food> foodObservableList;

    /**
     * Sets the place to display.
     *
     * @param place the place
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * Sets the dialog stage.
     *
     * @param dialogStage the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        if (place == null) return;

        charactersObservableList = FXCollections.observableArrayList();
        foodObservableList = FXCollections.observableArrayList();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        factionColumn.setCellValueFactory(new PropertyValueFactory<>("faction"));
        healthColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
        hungerColumn.setCellValueFactory(new PropertyValueFactory<>("hunger"));
        statusColumn.setCellValueFactory(cellData -> {
            GameCharacter character = cellData.getValue();
            String status = character.isDead() ? "Dead" : "Alive";
            return new javafx.beans.property.SimpleStringProperty(status);
        });

        charactersTableView.setItems(charactersObservableList);

        foodListView.setItems(foodObservableList);
        foodListView.setCellFactory(lv -> new ListCell<Food>() {
            @Override
            protected void updateItem(Food food, boolean empty) {
                super.updateItem(food, empty);
                if (empty || food == null) {
                    setText(null);
                } else {
                    setText(food.getName() + " - " + food.getFreshness().getLabel());
                }
            }
        });

        updateUI();
    }

    /**
     * Updates the UI with current place data.
     */
    private void updateUI() {
        if (place == null) return;

        placeNameLabel.setText(place.getName());
        placeTypeLabel.setText("Type: " + place.getClass().getSimpleName());
        placeAreaLabel.setText(place.getArea() + " m²");
        chiefNameLabel.setText(place.getChief() != null ? place.getChief().getName() : "No Chief");
        charactersCountLabel.setText(String.valueOf(place.getNumberOfCharacters()));
        foodCountLabel.setText(String.valueOf(place.getFoods().size()));

        charactersObservableList.setAll(place.getCharacters());
        foodObservableList.setAll(place.getFoods());
    }

    /**
     * Handles adding a character.
     */
    @FXML
    private void handleAddCharacter() {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/envahissementarmorique/view/character-dialog.fxml")
            );

            Stage characterDialogStage = new Stage();
            characterDialogStage.setTitle("Create Character");
            characterDialogStage.initModality(Modality.WINDOW_MODAL);
            characterDialogStage.initOwner(dialogStage);
            characterDialogStage.setScene(new Scene(loader.load()));

            CharacterDialogController controller = loader.getController();
            controller.setDialogStage(characterDialogStage);

            characterDialogStage.showAndWait();

            if (controller.isConfirmed()) {
                GameCharacter newCharacter = controller.getCreatedCharacter();
                if (newCharacter != null) {
                    place.addCharacter(newCharacter);
                    updateUI();
                }
            }
        } catch (IOException e) {
            showError("Error", "Could not open character dialog: " + e.getMessage());
        }
    }

    /**
     * Handles removing a character.
     */
    @FXML
    private void handleRemoveCharacter() {
        GameCharacter selected = charactersTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("No Selection", "Please select a character to remove.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Removal");
        alert.setHeaderText("Remove Character");
        alert.setContentText("Remove " + selected.getName() + " from this place?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            place.removeCharacter(selected);
            updateUI();
        }
    }

    /**
     * Handles viewing character details.
     */
    @FXML
    private void handleViewCharacter() {
        GameCharacter selected = charactersTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("No Selection", "Please select a character to view.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Character Details");
        alert.setHeaderText(selected.getName());
        alert.setContentText(
            "Faction: " + selected.getFaction() + "\n" +
            "Gender: " + selected.getGenre() + "\n" +
            "Age: " + selected.getAge() + "\n" +
            "Height: " + selected.getHeight() + "m\n" +
            "Health: " + selected.getHealth() + "\n" +
            "Hunger: " + selected.getHunger() + "\n" +
            "Strength: " + selected.getStrength() + "\n" +
            "Endurance: " + selected.getEndurance() + "\n" +
            "Belligerence: " + selected.getBelligerence() + "\n" +
            "Status: " + (selected.isDead() ? "Dead" : "Alive")
        );
        alert.showAndWait();
    }

    /**
     * Handles adding food.
     */
    @FXML
    private void handleAddFood() {
        ChoiceDialog<Foods> dialog = new ChoiceDialog<>(Foods.BOAR, Foods.values());
        dialog.setTitle("Add Food");
        dialog.setHeaderText("Select Food Type");
        dialog.setContentText("Choose food:");

        Optional<Foods> result = dialog.showAndWait();
        result.ifPresent(foodType -> {
            Food newFood = new Food(foodType, Freshness.FRESH);
            place.addFood(newFood);
            updateUI();
        });
    }

    /**
     * Handles removing food.
     */
    @FXML
    private void handleRemoveFood() {
        Food selected = foodListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("No Selection", "Please select food to remove.");
            return;
        }

        place.removeFood(selected);
        updateUI();
    }

    /**
     * Handles healing all characters.
     */
    @FXML
    private void handleHealAll() {
        place.healAll(30);
        updateUI();
        showInfo("Success", "All characters have been healed by 30 HP.");
    }

    /**
     * Handles feeding all characters.
     */
    @FXML
    private void handleFeedAll() {
        if (place.getFoods().isEmpty()) {
            showWarning("No Food", "There is no food available to feed characters.");
            return;
        }

        place.feedAll();
        updateUI();
        showInfo("Success", "Characters have been fed.");
    }

    /**
     * Handles removing dead characters.
     */
    @FXML
    private void handleRemoveDead() {
        place.removeDeadCharacters();
        updateUI();
        showInfo("Success", "Dead characters have been removed.");
    }

    /**
     * Handles refreshing the view.
     */
    @FXML
    private void handleRefresh() {
        updateUI();
    }

    /**
     * Handles closing the dialog.
     */
    @FXML
    private void handleClose() {
        if (dialogStage != null) {
            dialogStage.close();
        }
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
     * Shows an info alert.
     *
     * @param title the alert title
     * @param message the info message
     */
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
