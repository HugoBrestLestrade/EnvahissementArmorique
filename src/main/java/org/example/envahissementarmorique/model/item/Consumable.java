package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

/**
 * Représente un objet pouvant être consommé par un personnage dans la simulation.
 * <p>
 * Cette classe est scellée (sealed) pour contrôler strictement la hiérarchie :
 * seuls {@link Food} et {@link Potion} sont des types autorisés de consommables,
 * conformément aux spécifications du TD3.
 * </p>
 *
 * @author Envahissement Armorique
 * @version 1.0
 */
public abstract sealed class Consumable permits Food, Potion {

    /**
     * Le type de l'aliment ou de la potion (ex. : BOAR, FRESH_FISH, WINE, etc.).
     */
    protected final Foods foods;

    /**
     * Constructeur d'un objet consommable.
     *
     * @param foods le type de nourriture ou potion (ne peut pas être null)
     * @throws IllegalArgumentException si foods est null
     */
    public Consumable(Foods foods) {
        if (foods == null) {
            throw new IllegalArgumentException("Foods cannot be null.");
        }
        this.foods = foods;
    }

    /**
     * Retourne le type de consommable.
     *
     * @return le type {@link Foods}
     */
    public Foods getFoods() {
        return foods;
    }

    /**
     * Retourne le nom du consommable.
     *
     * @return le nom sous forme de chaîne de caractères
     */
    public String getName() {
        return foods.getLabel();
    }

    /**
     * Applique l'effet de ce consommable sur le personnage spécifié.
     * <p>
     * Selon le TD3 :
     * <ul>
     *   <li>Les aliments améliorent l'indicateur de faim.</li>
     *   <li>Les potions augmentent le niveau de potion magique.</li>
     * </ul>
     * </p>
     *
     * @param consumer le personnage consommant l'objet
     */
    public abstract void consume(GameCharacter consumer);

    @Override
    public String toString() {
        return foods.getLabel();
    }
}
