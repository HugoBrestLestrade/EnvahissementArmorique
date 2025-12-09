package org.example.envahissementarmorique.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.place.Place;
import org.example.envahissementarmorique.model.theater.InvasionTheater;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the statistics view.
 * Displays comprehensive theater statistics with charts.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
public class StatisticsController {

    @FXML private Label totalPlacesLabel;
    @FXML private Label totalCharactersLabel;
    @FXML private Label casualtiesLabel;
    @FXML private Label totalFoodLabel;
    @FXML private PieChart factionPieChart;
    @FXML private BarChart<String, Number> healthBarChart;
    @FXML private TableView<PlaceStatistic> placesTableView;
    @FXML private TableColumn<PlaceStatistic, String> placeNameColumn;
    @FXML private TableColumn<PlaceStatistic, String> placeTypeColumn;
    @FXML private TableColumn<PlaceStatistic, Integer> placeCharactersColumn;
    @FXML private TableColumn<PlaceStatistic, Integer> placeFoodColumn;
    @FXML private TableColumn<PlaceStatistic, Float> placeAreaColumn;

    private InvasionTheater theater;
    private Stage dialogStage;
    private ObservableList<PlaceStatistic> placeStatistics;

    /**
     * Sets the theater instance.
     *
     * @param theater the invasion theater
     */
    public void setTheater(InvasionTheater theater) {
        this.theater = theater;
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
        if (theater == null) return;

        placeStatistics = FXCollections.observableArrayList();

        placeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        placeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        placeCharactersColumn.setCellValueFactory(new PropertyValueFactory<>("characterCount"));
        placeFoodColumn.setCellValueFactory(new PropertyValueFactory<>("foodCount"));
        placeAreaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));

        placesTableView.setItems(placeStatistics);

        updateStatistics();
    }

    /**
     * Updates all statistics and charts.
     */
    private void updateStatistics() {
        if (theater == null) return;

        totalPlacesLabel.setText(String.valueOf(theater.getPlaces().size()));
        totalCharactersLabel.setText(String.valueOf(theater.getTotalCharacters()));

        int casualties = 0;
        int totalFood = 0;

        for (Place place : theater.getPlaces()) {
            totalFood += place.getFoods().size();
            for (GameCharacter character : place.getCharacters()) {
                if (character.isDead()) {
                    casualties++;
                }
            }
        }

        casualtiesLabel.setText(String.valueOf(casualties));
        totalFoodLabel.setText(String.valueOf(totalFood));

        updateFactionChart();
        updateHealthChart();
        updatePlacesTable();
    }

    /**
     * Updates the faction distribution pie chart.
     */
    private void updateFactionChart() {
        Map<String, Integer> factionCounts = new HashMap<>();

        for (Place place : theater.getPlaces()) {
            for (GameCharacter character : place.getCharacters()) {
                if (!character.isDead()) {
                    String faction = character.getFaction();
                    factionCounts.put(faction, factionCounts.getOrDefault(faction, 0) + 1);
                }
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : factionCounts.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        factionPieChart.setData(pieChartData);
    }

    /**
     * Updates the average health bar chart.
     */
    private void updateHealthChart() {
        Map<String, Integer> factionHealthSum = new HashMap<>();
        Map<String, Integer> factionCount = new HashMap<>();

        for (Place place : theater.getPlaces()) {
            for (GameCharacter character : place.getCharacters()) {
                if (!character.isDead()) {
                    String faction = character.getFaction();
                    factionHealthSum.put(faction,
                        factionHealthSum.getOrDefault(faction, 0) + character.getHealth());
                    factionCount.put(faction,
                        factionCount.getOrDefault(faction, 0) + 1);
                }
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Average Health");

        for (Map.Entry<String, Integer> entry : factionHealthSum.entrySet()) {
            String faction = entry.getKey();
            double avgHealth = (double) entry.getValue() / factionCount.get(faction);
            series.getData().add(new XYChart.Data<>(faction, avgHealth));
        }

        healthBarChart.getData().clear();
        healthBarChart.getData().add(series);
    }

    /**
     * Updates the places table.
     */
    private void updatePlacesTable() {
        placeStatistics.clear();

        for (Place place : theater.getPlaces()) {
            placeStatistics.add(new PlaceStatistic(
                place.getName(),
                place.getClass().getSimpleName(),
                place.getNumberOfCharacters(),
                place.getFoods().size(),
                place.getArea()
            ));
        }
    }

    /**
     * Handles refreshing the statistics.
     */
    @FXML
    private void handleRefresh() {
        updateStatistics();
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
     * Inner class to represent place statistics for the table.
     */
    public static class PlaceStatistic {
        private final String name;
        private final String type;
        private final int characterCount;
        private final int foodCount;
        private final float area;

        /**
         * Creates a new place statistic.
         *
         * @param name the place name
         * @param type the place type
         * @param characterCount the number of characters
         * @param foodCount the number of food items
         * @param area the area in square meters
         */
        public PlaceStatistic(String name, String type, int characterCount, int foodCount, float area) {
            this.name = name;
            this.type = type;
            this.characterCount = characterCount;
            this.foodCount = foodCount;
            this.area = area;
        }

        public String getName() { return name; }
        public String getType() { return type; }
        public int getCharacterCount() { return characterCount; }
        public int getFoodCount() { return foodCount; }
        public float getArea() { return area; }
    }
}

