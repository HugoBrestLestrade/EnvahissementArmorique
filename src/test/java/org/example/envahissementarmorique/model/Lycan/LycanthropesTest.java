package org.example.envahissementarmorique.model.Lycan;

import org.example.envahissementarmorique.model.PackAndAlpha.Pack;
import org.example.envahissementarmorique.model.Yell.Yell;
import org.example.envahissementarmorique.model.character.base.Lycan.CategorieAge;
import org.example.envahissementarmorique.model.character.base.Lycan.Lycanthropes;
import org.example.envahissementarmorique.model.character.base.Lycan.YellType;
import org.junit.jupiter.api.Test;

import static org.example.envahissementarmorique.model.character.base.Lycan.CategorieAge.ADULT;
import static org.example.envahissementarmorique.model.character.base.Lycan.Sexe.FEMALE;
import static org.example.envahissementarmorique.model.character.base.Lycan.Sexe.MALE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe {@link Lycanthropes}.
 *
 * Ces tests vérifient les fonctionnalités principales des lycanthropes, notamment :
 * <ul>
 *     <li>La création et initialisation des lycanthropes</li>
 *     <li>L'ajout à un pack et la séparation</li>
 *     <li>Le hurlement et l'interaction via les Yells</li>
 *     <li>Les tentatives de domination au sein du pack</li>
 *     <li>La transformation en humain</li>
 *     <li>Le vieillissement des lycanthropes</li>
 * </ul>
 *
 * Toutes les méthodes sont testées pour s'assurer que les comportements respectent les règles de simulation.
 *
 * @author Envahissement
 * @version 1.0
 */
class LycanthropesTest {

    /**
     * Teste la création d'un lycanthrope avec ses attributs de base.
     */
    @Test
    void testLycanCreation() {
        Lycanthropes l = new Lycanthropes(MALE, CategorieAge.YOUNG, 10, 0.3, 2);

        assertEquals(MALE, l.getSexe());
        assertEquals(CategorieAge.YOUNG, l.getAgeCategory());
        assertEquals(10, l.getStrength());
        assertEquals("solitaire", l.getRank());
        assertTrue(l.getIdentifier().startsWith("M#"));
    }

    /**
     * Teste l'ajout des lycanthropes dans un pack.
     */
    @Test
    void testLeavePack() {
        Lycanthropes m = new Lycanthropes(MALE, ADULT, 15, 0.2, 0);
        Lycanthropes f = new Lycanthropes(FEMALE, ADULT, 12, 0.2, 1);

        Pack p = new Pack("LuneRouge", m, f);

        assertEquals(p, m.getPack());
        assertEquals(p, f.getPack());
    }

    /**
     * Teste la séparation d'un lycanthrope du pack.
     */
    @Test
    void testSeSeparer() {
        Lycanthropes m = new Lycanthropes(MALE, ADULT, 15, 0.2, 0);
        Lycanthropes f = new Lycanthropes(FEMALE, ADULT, 12, 0.2, 1);

        Pack p = new Pack("LuneRouge", m, f);

        m.leavePack();

        assertNull(m.getPack());
        assertTrue(m.isLone());
    }

    /**
     * Teste le hurlement d'un lycanthrope et la création d'un Yell.
     */
    @Test
    void testHowl() {
        Lycanthropes m = new Lycanthropes(MALE, CategorieAge.YOUNG, 10, 0.2, 1);
        Yell y = m.yell(YellType.BELONGING, "Je suis là");

        assertNotNull(y);
        assertEquals(m, y.emitter);
        assertEquals(YellType.BELONGING, y.type);
    }

    /**
     * Teste la tentative de domination réussie sur un autre lycanthrope.
     */
    @Test
    void testSuccessfulDomination() {
        Lycanthropes dominant = new Lycanthropes(MALE, ADULT, 18, 0.3, 2);
        Lycanthropes femelleAlpha = new Lycanthropes(FEMALE, ADULT, 10, 0.2, 0);

        Pack p = new Pack("Meute1", dominant, femelleAlpha);

        Lycanthropes cible = new Lycanthropes(MALE, CategorieAge.YOUNG, 5, 0.1, 5);
        p.addLycanthrope(cible);

        boolean resultat = dominant.tryDominate(cible);

        assertTrue(resultat);
        assertEquals(5, dominant.getRankIndex());
        assertEquals(2, cible.getRankIndex());
    }

    /**
     * Teste la transformation d'un lycanthrope en humain.
     */
    @Test
    void testHumanTransformation() {
        Lycanthropes alpha = new Lycanthropes(MALE, ADULT, 30, 0.5, 0);
        Lycanthropes femelle = new Lycanthropes(FEMALE, ADULT, 20, 0.3, 1);

        Pack p = new Pack("Lune", alpha, femelle);

        alpha.forceTransformHumanForTest();

        assertTrue(alpha.isHuman());
        assertNull(alpha.getPack());
    }

    /**
     * Teste le vieillissement d'un lycanthrope et la progression de sa catégorie d'âge.
     */
    @Test
    void testAging() {
        Lycanthropes l = new Lycanthropes(FEMALE, CategorieAge.YOUNG, 10, 0.3, 2);

        l.age();

        assertEquals(ADULT, l.getAgeCategory());
    }
}
