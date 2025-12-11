package org.example.envahissementarmorique.model.character.interfaces;

/**
 * Interface représentant un combattant capable de subir des dégâts.
 */
public interface Fighter {

    /**
     * Applique des dégâts au combattant.
     *
     * @param damage le nombre de points de dégâts à infliger
     */
    void takeDamage(int damage);
}
