package org.example.envahissementarmorique.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.envahissementarmorique.model.character.base.*;
import org.example.envahissementarmorique.model.character.base.Gaulish.*;
import org.example.envahissementarmorique.model.character.base.Lycan.Lycanthropes;
import org.example.envahissementarmorique.model.character.base.Roman.General;
import org.example.envahissementarmorique.model.character.base.Roman.Legionnary;
import org.example.envahissementarmorique.model.character.base.Roman.Prefect;
import org.example.envahissementarmorique.model.character.base.Roman.Roman;

/**
 * Controller for the character creation dialog.
 * Allows creating new characters with custom attributes.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class CharacterDialogController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private ToggleGroup genderToggleGroup;
    @FXML private ComboBox<String> factionComboBox;
    @FXML private Spinner<Double> heightSpinner;
    @FXML private Spinner<Integer> ageSpinner;
    @FXML private Slider strengthSlider;
    @FXML private Label strengthValueLabel;
    @FXML private Slider enduranceSlider;
    @FXML private Label enduranceValueLabel;
    @FXML private Slider healthSlider;
    @FXML private Label healthValueLabel;
    @FXML private Slider hungerSlider;
    @FXML private Label hungerValueLabel;
    @FXML private Slider belligerenceSlider;
    @FXML private Label belligerenceValueLabel;

    private Stage dialogStage;
    private GameCharacter createdCharacter;
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
     * Returns whether the user confirmed the dialog.
     *
     * @return true if confirmed
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Gets the created character.
     *
     * @return the created character or null
     */
    public GameCharacter getCreatedCharacter() {
        return createdCharacter;
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        typeComboBox.getItems().addAll(
            "Gaulois",
            "Roman",
            "Druid",
            "Blacksmith",
            "Innkeeper",
            "Merchant",
            "Legionnaire",
            "Prefect",
            "General",
            "Lycanthrope",
            "Fantastic Creature"
        );
        typeComboBox.setValue("Gaulois");

        factionComboBox.getItems().addAll("Gaulois", "Roman", "Créature");
        factionComboBox.setValue("Gaulois");

        typeComboBox.setOnAction(e -> updateFactionBasedOnType());

        heightSpinner.setValueFactory(
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 3.0, 1.70, 0.05)
        );

        ageSpinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 30)
        );

        strengthSlider.valueProperty().addListener((obs, oldVal, newVal) ->
            strengthValueLabel.setText(String.valueOf(newVal.intValue()))
        );

        enduranceSlider.valueProperty().addListener((obs, oldVal, newVal) ->
            enduranceValueLabel.setText(String.valueOf(newVal.intValue()))
        );

        healthSlider.valueProperty().addListener((obs, oldVal, newVal) ->
            healthValueLabel.setText(String.valueOf(newVal.intValue()))
        );

        hungerSlider.valueProperty().addListener((obs, oldVal, newVal) ->
            hungerValueLabel.setText(String.valueOf(newVal.intValue()))
        );

        belligerenceSlider.valueProperty().addListener((obs, oldVal, newVal) ->
            belligerenceValueLabel.setText(String.valueOf(newVal.intValue()))
        );
    }

    /**
     * Updates faction combo box based on selected type.
     */
    private void updateFactionBasedOnType() {
        String type = typeComboBox.getValue();
        if (type == null) return;

        switch (type) {
            case "Gaulois":
            case "Druid":
            case "Blacksmith":
            case "Innkeeper":
            case "Merchant":
                factionComboBox.setValue("Gaulois");
                break;
            case "Roman":
            case "Legionnaire":
            case "Prefect":
            case "General":
                factionComboBox.setValue("Roman");
                break;
            case "Fantastic Creature":
                factionComboBox.setValue("Créature");
                break;
        }
    }

    /**
     * Handles the create button.
     */
    @FXML
    private void handleCreate() {
        if (!validateInput()) {
            return;
        }

        String name = nameField.getText();
        String type = typeComboBox.getValue();
        RadioButton selectedGender = (RadioButton) genderToggleGroup.getSelectedToggle();
        String gender = selectedGender != null ? (selectedGender.getText().equals("Male") ? "M" : "F") : "M";
        String faction = factionComboBox.getValue();
        double height = heightSpinner.getValue();
        int age = ageSpinner.getValue();
        int strength = (int) strengthSlider.getValue();
        int endurance = (int) enduranceSlider.getValue();
        int health = (int) healthSlider.getValue();
        int hunger = (int) hungerSlider.getValue();
        int belligerence = (int) belligerenceSlider.getValue();

        createdCharacter = createCharacterByType(type, name, gender, faction, height, age,
                                                 strength, endurance, health, hunger, belligerence);

        confirmed = true;
        dialogStage.close();
    }

    /**
     * Creates a character based on the specified type.
     *
     * @param type the character type
     * @param name the name
     * @param gender the gender
     * @param faction the faction
     * @param height the height
     * @param age the age
     * @param strength the strength
     * @param endurance the endurance
     * @param health the health
     * @param hunger the hunger
     * @param belligerence the belligerence
     * @return the created character
     */
    private GameCharacter createCharacterByType(String type, String name, String gender,
                                                String faction, double height, int age,
                                                int strength, int endurance, int health,
                                                int hunger, int belligerence) {
        switch (type) {
            case "Gaulois":
                return new Gaulois(name, gender, height, age, strength, endurance, health, hunger, belligerence, 0);
            case "Roman":
                return new Roman(name, gender, height, age, strength, endurance, health, hunger, belligerence, 0);
            case "Druid":
                return new Druid(name, gender, faction, height, age, strength, endurance, health, hunger, belligerence, 0);
            case "Blacksmith":
                return new BlackSmith(name, gender, faction, height, age, strength, endurance, health, hunger, belligerence, 0);
            case "Innkeeper":
                return new InnKeeper(name, gender, faction, height, age, strength, endurance, health, hunger, belligerence, 0);
            case "Merchant":
                return new Merchant(name, gender, faction, height, age, strength, endurance, health, hunger, belligerence, 0);
            case "Legionnaire":
                return new Legionnary(name, gender, faction, height, age, strength, endurance, health, hunger, belligerence, 0);
            case "Prefect":
                return new Prefect(name, gender, faction, height, age, strength, endurance, health, hunger, belligerence, 0);
            case "General":
                return new General(name, gender, faction, height, age, strength, endurance, health, hunger, belligerence, 0);
            case "Fantastic Creature":
                return new FantasticCreature(name, gender, height, age, strength, endurance, health, hunger, belligerence, 0);
            default:
                return new GameCharacter(name, gender, faction, height, age, strength, endurance, health, hunger, belligerence, 0);
        }
    }

    /**
     * Validates the input fields.
     *
     * @return true if input is valid
     */
    private boolean validateInput() {
        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            errorMessage += "Please enter a name.\n";
        }

        if (typeComboBox.getValue() == null) {
            errorMessage += "Please select a character type.\n";
        }

        if (factionComboBox.getValue() == null) {
            errorMessage += "Please select a faction.\n";
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

