package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

import java.util.EnumSet;
import java.util.Set;

/**
 * Représente une potion magique dans la simulation.
 * <p>
 * Recette de base : gui (mistletoe), carottes, sel, trèfle à quatre feuilles frais,
 * poisson passablement frais, huile de roche, miel, hydromel, ingrédient secret.
 * </p>
 * <p>
 * Variantes possibles :
 * <ul>
 *   <li>Ajouter du homard ou des fraises rend la potion nourrissante</li>
 *   <li>Remplacer l'huile de roche par du jus de betterave est également valide</li>
 *   <li>Ajouter du lait de licorne à deux têtes confère un pouvoir de duplication</li>
 *   <li>Ajouter des poils d'Idéfix confère un pouvoir de métamorphose</li>
 * </ul>
 * </p>
 * <p>
 * Effets principaux :
 * <ul>
 *   <li>1 dose : force surhumaine temporaire et invincibilité</li>
 *   <li>1 marmite complète (≥10 doses) : effets permanents</li>
 *   <li>2 marmites complètes (≥20 doses) : transformation en statue de granit</li>
 * </ul>
 * </p>
 *
 * @author Envahissement
 * @version 1.0
 */
public final class Potion extends Consumable {

    /** Ingrédients de la recette de base de la potion magique. */
    public static final Set<Foods> BASE_RECIPE = EnumSet.of(
            Foods.MISTLETOE,
            Foods.CARROT,
            Foods.SALT,
            Foods.FRESH_FOUR_LEAF_CLOVER,
            Foods.FRESH_FISH,
            Foods.ROCK_OIL,
            Foods.HONEY,
            Foods.MEAD,
            Foods.SECRET_INGREDIENT
    );

    /** Nombre de doses par marmite. */
    public static final int DOSES_PER_CAULDRON = 10;

    /** Bonus de force par dose. */
    public static final int DOSE_STRENGTH_BOOST = 100;

    /** Nombre de doses de cette potion. */
    private int doses;

    /** Liste des ingrédients présents dans cette potion. */
    private java.util.List<Foods> ingredients;

    /**
     * Crée une nouvelle potion avec 1 dose.
     *
     * @param foods L'ingrédient principal de la potion.
     */
    public Potion(Foods foods) {
        super(foods);
        this.doses = 1;
        this.ingredients = new java.util.ArrayList<>();
        this.ingredients.add(foods);
    }

    /**
     * Crée une nouvelle potion avec un nombre spécifique de doses.
     *
     * @param foods L'ingrédient principal de la potion.
     * @param doses Le nombre de doses.
     */
    public Potion(Foods foods, int doses) {
        super(foods);
        this.doses = doses;
        this.ingredients = new java.util.ArrayList<>();
        this.ingredients.add(foods);
    }

    /**
     * Récupère le nombre de doses de cette potion.
     *
     * @return Le nombre de doses.
     */
    public int getDoses() {
        return doses;
    }

    /**
     * Ajoute un ingrédient à la potion.
     *
     * @param ingredient L'ingrédient à ajouter.
     */
    public void addIngredient(Foods ingredient) {
        if (ingredient != null && !ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
        }
    }

    /**
     * Récupère la liste des ingrédients de cette potion.
     *
     * @return Une liste des ingrédients.
     */
    public java.util.List<Foods> getIngredients() {
        return new java.util.ArrayList<>(ingredients);
    }

    /**
     * Permet à un personnage de boire cette potion.
     * C'est un alias pour {@link #consume(GameCharacter)} afin de respecter la spécification UML.
     *
     * @param character Le personnage qui boit la potion.
     */
    public void drink(GameCharacter character) {
        consume(character);
    }

    /**
     * Applique les effets de cette potion sur le personnage consommateur.
     * <ul>
     *   <li>1 dose : +100 force, invincibilité temporaire</li>
     *   <li>1 marmite (≥10 doses) : effets permanents</li>
     *   <li>2 marmites (≥20 doses) : transformation en statue de granit</li>
     * </ul>
     *
     * @param consumer Le personnage consommant la potion.
     */
    @Override
    public void consume(GameCharacter consumer) {
        int currentPotionLevel = consumer.getMagicpotion();
        int newPotionLevel = currentPotionLevel + doses;

        // Transformation en statue de granit si 2 marmites ou plus
        if (newPotionLevel >= 2 * DOSES_PER_CAULDRON) {
            consumer.setHealth(0);
            consumer.setStrength(0);
            consumer.setEndurance(0);
            consumer.setMagicpotion(newPotionLevel);
            System.out.println(consumer.getName() + " a été transformé en statue de granit !");
            return;
        }

        // Augmentation de la force
        int strengthBoost = doses * DOSE_STRENGTH_BOOST;
        consumer.setStrength(consumer.getStrength() + strengthBoost);

        // Si 1 marmite ou plus (≥10 doses), effets permanents
        if (newPotionLevel >= DOSES_PER_CAULDRON) {
            consumer.setEndurance(consumer.getEndurance() + 1000);
            System.out.println(consumer.getName() + " gagne une force surhumaine permanente et l'invincibilité !");
        } else {
            System.out.println(consumer.getName() + " gagne une force surhumaine temporaire !");
        }

        // Mise à jour du niveau de potion magique
        consumer.setMagicpotion(newPotionLevel);
    }

    @Override
    public String toString() {
        return "Potion : " + foods.getLabel() + " (" + doses + " doses)";
    }
}
