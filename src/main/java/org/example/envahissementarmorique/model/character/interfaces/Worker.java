package org.example.envahissementarmorique.model.character.interfaces;

/**
 * Interface pour les personnages capables de travailler et d'exercer un métier.
 * <p>
 * Un travailleur peut réaliser différentes tâches liées à sa profession.
 * </p>
 *
 * @author Envahissement Armorique
 * @version 1.0
 */
public interface Worker {

    /**
     * Permet au travailleur d'effectuer son métier.
     *
     * @return true si le travail a été effectué avec succès
     */
    boolean work();

    /**
     * Retourne le type de travail que ce travailleur effectue.
     *
     * @return le type de travail sous forme de chaîne de caractères
     */
    String getWorkType();

    /**
     * Permet au travailleur de créer ou produire quelque chose.
     *
     * @param itemName le nom de l'objet à produire
     * @return true si la production a été effectuée avec succès
     */
    boolean produce(String itemName);
}
