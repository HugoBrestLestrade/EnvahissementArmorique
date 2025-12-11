package org.example.envahissementarmorique.model.item;

import org.example.envahissementarmorique.model.character.base.GameCharacter;

/**
 * Représente un aliment dans la simulation.
 * <p>
 * Selon les spécifications du TD3 :
 * <ul>
 *   <li>Les aliments améliorent l'indicateur de faim.</li>
 *   <li>Les aliments peuvent être frais ou non. Manger un aliment non frais (comme le poisson) impacte la santé.</li>
 *   <li>La fraîcheur des aliments change avec le temps.</li>
 * </ul>
 * </p>
 *
 * @author Envahissement Armorique
 * @version 1.0
 */
public final class Food extends Consumable {

    private Freshness freshness;

    /**
     * Crée un nouvel aliment.
     * Par défaut, l'aliment est frais lors de sa création.
     *
     * @param foods le type d'aliment (ex. : BOAR, FRESH_FISH, etc.)
     */
    public Food(Foods foods) {
        super(foods);
        this.freshness = Freshness.FRESH;
    }

    /**
     * Crée un nouvel aliment avec un niveau de fraîcheur spécifique.
     *
     * @param foods le type d'aliment (ex. : BOAR, FRESH_FISH, etc.)
     * @param freshness l'état initial de fraîcheur de l'aliment
     */
    public Food(Foods foods, Freshness freshness) {
        super(foods);
        this.freshness = freshness;
    }

    /**
     * Retourne l'état de fraîcheur actuel de l'aliment.
     *
     * @return l'état de fraîcheur {@link Freshness}
     */
    public Freshness getFreshness() {
        return freshness;
    }

    /**
     * Fait se dégrader la fraîcheur de l'aliment avec le temps.
     * <p>
     * Ordre : FRESH -> OKAY -> ROTTEN -> ROTTEN
     * </p>
     */
    public void degrade() {
        this.freshness = this.freshness.degrade();
    }

    /**
     * Retourne la valeur nutritionnelle de l'aliment selon son type.
     *
     * @return la valeur nutritionnelle
     */
    public int getNutritionalValue() {
        return foods.getNutrition();
    }

    /**
     * Applique l'effet de cet aliment sur le personnage spécifié.
     * <p>
     * - Les aliments frais ou corrects augmentent l'indicateur de faim selon leur valeur nutritionnelle.
     * - Les aliments pourris diminuent la santé et la faim.
     * </p>
     *
     * <p>Logique :</p>
     * <ul>
     *   <li>Si pourri : perte de 1/5 des PV et perte de 1/3 de la faim</li>
     *   <li>Sinon : faim = faim + valeur nutritionnelle</li>
     * </ul>
     *
     * @param consumer le personnage consommant l'aliment
     */
    @Override
    public void consume(GameCharacter consumer) {
        if (freshness == Freshness.ROTTEN) {
            int healthLoss = consumer.getHealth() / 5;
            consumer.setHealth(consumer.getHealth() - healthLoss);

            int hungerLoss = consumer.getHunger() / 3;
            consumer.setHunger(consumer.getHunger() - hungerLoss);

            System.out.println(consumer.getName() + " a mangé " + getName() + " pourri et a perdu " + healthLoss + " PV et " + hungerLoss + " de faim !");
        } else {
            int newHunger = consumer.getHunger() + foods.getNutrition();
            consumer.setHunger(newHunger);

            System.out.println(consumer.getName() + " a mangé " + getName() + " et a gagné " + foods.getNutrition() + " de faim !");
        }
    }

    @Override
    public String toString() {
        return foods.getLabel() + " (" + freshness.getLabel() + ")";
    }
}
