package org.example.envahissementarmorique.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.place.*;
import org.example.envahissementarmorique.model.theater.InvasionTheater;

/**
 * Controller for the place creation dialog.
 * Allows creating new places with custom attributes.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class PlaceDialogController {

    @FXML private ComboBox<String> placeTypeComboBox;
    @FXML private Label placeTypeDescriptionLabel;
    @FXML private TextField placeNameField;
    @FXML private Spinner<Integer> areaSpinner;
    @FXML private CheckBox assignChiefCheckBox;
    @FXML private ComboBox<ClanLeader> chiefComboBox;

    private Stage dialogStage;
    private InvasionTheater theater;
    private Place createdPlace;
    private boolean confirmed = false;

    /**
     * Sets the dialog stage.
     *
     * @param dialogStage the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the theater instance.
     *
     * @param theater the invasion theater
     */
    public void setTheater(InvasionTheater theater) {
        this.theater = theater;
    }

    /**
     * Returns whether the user confirmed the dialog.
     *
     * @return true if confirmed
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Gets the created place.
     *
     * @return the created place or null
     */
    public Place getCreatedPlace() {
        return createdPlace;
    }

    /**
     * Initializes the controller.
     * This is called automatically by JavaFX when the FXML is loaded.
     */
    @FXML
    public void initialize() {
        placeTypeComboBox.getItems().addAll(
            "Gaulish Village",
            "Roman Camp",
            "Roman City",
            "Gallo-Roman Town",
            "Battlefield",
            "Enclosure"
        );
        placeTypeComboBox.setValue("Gaulish Village");

        placeTypeComboBox.setOnAction(e -> updatePlaceTypeDescription());

        assignChiefCheckBox.setOnAction(e -> {
            chiefComboBox.setDisable(!assignChiefCheckBox.isSelected());
        });

        updatePlaceTypeDescription();
    }

    /**
     * Initializes the clan leaders ComboBox.
     * Must be called after setTheater() is called.
     */
    public void initializeClanLeaders() {
        if (theater != null) {
            chiefComboBox.getItems().addAll(theater.getClanLeaders());
            chiefComboBox.setCellFactory(lv -> new ListCell<ClanLeader>() {
                @Override
                protected void updateItem(ClanLeader leader, boolean empty) {
                    super.updateItem(leader, empty);
                    setText(empty || leader == null ? "" : leader.getName());
                }
            });
            chiefComboBox.setButtonCell(new ListCell<ClanLeader>() {
                @Override
                protected void updateItem(ClanLeader leader, boolean empty) {
                    super.updateItem(leader, empty);
                    setText(empty || leader == null ? "" : leader.getName());
                }
            });
        }
    }

    /**
     * Updates the place type description based on selection.
     */
    private void updatePlaceTypeDescription() {
        String type = placeTypeComboBox.getValue();
        if (type == null) return;

        String description = switch (type) {
            case "Gaulish Village" ->
                    "A village inhabited by Gaulish people. Only Gaulois and fantastic creatures can enter. Has resistance and morale levels.";
            case "Roman Camp" ->
                    "A Roman military camp. Only Romans and fantastic creatures allowed.";
            case "Roman City" ->
                    "A Roman city with administrative buildings. Only Romans allowed.";
            case "Gallo-Roman Town" ->
                    "A mixed settlement where both Gaulois and Romans can coexist.";
            case "Battlefield" ->
                    "A place for combat. Any character can enter, but combat is frequent.";
            case "Enclosure" ->
                    "A special place for fantastic creatures. Only creatures can enter. Has limited capacity.";
            default -> "Select a place type to see its description.";
        };

        placeTypeDescriptionLabel.setText(description);
    }

    /**
     * Handles the create button.
     */
    @FXML
    private void handleCreate() {
        if (!validateInput()) {
            return;
        }

        String placeName = placeNameField.getText();
        String placeType = placeTypeComboBox.getValue();
        float area = areaSpinner.getValue().floatValue();
        ClanLeader chief = assignChiefCheckBox.isSelected() ? chiefComboBox.getValue() : null;

        createdPlace = createPlaceByType(placeType, placeName, area, chief);

        if (chief != null && createdPlace != null) {
            chief.setPlace(createdPlace);
        }

        confirmed = true;
        dialogStage.close();
    }

    /**
     * Creates a place based on the specified type.
     *
     * @param type the place type
     * @param name the place name
     * @param area the area in square meters
     * @param chief the clan leader (optional)
     * @return the created place
     */
    private Place createPlaceByType(String type, String name, float area, ClanLeader chief) {
        return switch (type) {
            case "Gaulish Village" -> new GaulishVillage(name, area, chief);
            case "Roman Camp" -> new RomanCamp(name, area, chief);
            case "Roman City" -> new RomanCity(name, area, chief);
            case "Gallo-Roman Town" -> new GalloRomanTown(name, area, chief);
            case "Battlefield" -> new Battlefield(name, area);
            case "Enclosure" -> new Enclosure(name, area);
            default -> null;
        };
    }

    /**
     * Validates the input fields.
     *
     * @return true if input is valid
     */
    private boolean validateInput() {
        String errorMessage = "";

        if (placeNameField.getText() == null || placeNameField.getText().trim().isEmpty()) {
            errorMessage += "Please enter a place name.\n";
        }

        if (placeTypeComboBox.getValue() == null) {
            errorMessage += "Please select a place type.\n";
        }

        if (assignChiefCheckBox.isSelected() && chiefComboBox.getValue() == null) {
            errorMessage += "Please select a clan leader or uncheck the option.\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please correct the following errors:");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    /**
     * Handles the cancel button.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}

