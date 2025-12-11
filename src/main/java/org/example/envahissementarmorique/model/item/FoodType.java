package org.example.envahissementarmorique.model.item;

/**
 * Énumération représentant les différentes catégories d'aliments disponibles dans la simulation.
 * <p>
 * Cette classification permet de gérer les règles de consommation
 * (ex. : certains régimes spécifiques ou règles pour les légumes).
 * </p>
 *
 * Catégories :
 * <ul>
 *   <li>{@link #LIQUIDS} : liquides (boissons, jus, hydromel, etc.)</li>
 *   <li>{@link #MEATS} : viandes et poissons</li>
 *   <li>{@link #VEGETABLES} : légumes et plantes comestibles</li>
 *   <li>{@link #SPICES} : épices et petites substances aromatiques</li>
 *   <li>{@link #SECRET_INGREDIENTS} : ingrédients secrets ou spéciaux</li>
 * </ul>
 *
 * Chaque catégorie possède un libellé affichable pour faciliter la lecture
 * dans les inventaires ou les interfaces.
 *
 * @author Envahissement Armorique
 * @version 1.0
 */
public enum FoodType {

    LIQUIDS("Liquides"),
    MEATS("Viandes"),
    VEGETABLES("Légumes"),
    SPICES("Épices"),
    SECRET_INGREDIENTS("Ingrédients secrets");

    private final String label;

    /**
     * Constructeur pour définir le libellé de la catégorie d'aliment.
     *
     * @param label Le nom affiché de la catégorie.
     */
    FoodType(String label) {
        this.label = label;
    }

    /**
     * Retourne le libellé de la catégorie d'aliment.
     *
     * @return Le nom de la catégorie sous forme de chaîne de caractères.
     */
    public String getLabel() {
        return label;
    }
}
