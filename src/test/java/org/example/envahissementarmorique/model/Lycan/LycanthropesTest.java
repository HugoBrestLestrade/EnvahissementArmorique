package org.example.envahissementarmorique.model.Lycan;


import org.example.envahissementarmorique.model.PackAndAlpha.Pack;
import org.example.envahissementarmorique.model.Yell.Yell;
import org.example.envahissementarmorique.model.character.base.Lycan.CategorieAge;
import org.example.envahissementarmorique.model.character.base.Lycan.Lycanthropes;
import org.example.envahissementarmorique.model.character.base.Lycan.Sexe;
import org.example.envahissementarmorique.model.character.base.Lycan.YellType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LycanthropesTest {

    @Test
    void testCreationLycan() {
        Lycanthropes l = new Lycanthropes(Sexe.MALE, CategorieAge.JEUNE, 10, 0.3, 2);

        assertEquals(Sexe.MALE, l.getSexe());
        assertEquals(CategorieAge.JEUNE, l.getCategorie());
        assertEquals(10, l.getForce());
        assertEquals("solitaire", l.getRang());
        assertTrue(l.getIdentifiant().startsWith("M#"));
    }

    @Test
    void testAjoutMeute() {
        Lycanthropes m = new Lycanthropes(Sexe.MALE, CategorieAge.ADULTE, 15, 0.2, 0);
        Lycanthropes f = new Lycanthropes(Sexe.FEMELLE, CategorieAge.ADULTE, 12, 0.2, 1);

        Pack p = new Pack("LuneRouge", m, f);

        assertEquals(p, m.getMeute());
        assertEquals(p, f.getMeute());
    }

    @Test
    void testSeSeparer() {
        Lycanthropes m = new Lycanthropes(Sexe.MALE, CategorieAge.ADULTE, 15, 0.2, 0);
        Lycanthropes f = new Lycanthropes(Sexe.FEMELLE, CategorieAge.ADULTE, 12, 0.2, 1);

        Pack p = new Pack("LuneRouge", m, f);

        m.seSeparer();

        assertNull(m.getMeute());
        assertTrue(m.estSolitaire());
    }

    @Test
    void testHurlement() {
        Lycanthropes m = new Lycanthropes(Sexe.MALE, CategorieAge.JEUNE, 10, 0.2, 1);
        Yell y = m.hurler(YellType.APPARTENANCE, "Je suis là");

        assertNotNull(y);
        assertEquals(m, y.emetteur);
        assertEquals(YellType.APPARTENANCE, y.type);
    }

    @Test
    void testDominationReussie() {
        Lycanthropes dominant = new Lycanthropes(Sexe.MALE, CategorieAge.ADULTE, 18, 0.3, 2);
        Lycanthropes femelleAlpha = new Lycanthropes(Sexe.FEMELLE, CategorieAge.ADULTE, 10, 0.2, 0);

        Pack p = new Pack("Meute1", dominant, femelleAlpha);

        Lycanthropes cible = new Lycanthropes(Sexe.MALE, CategorieAge.JEUNE, 5, 0.1, 5);
        p.ajouterLycanthrope(cible);

        boolean resultat = dominant.tenterDominer(cible);

        assertTrue(resultat);
        assertEquals(5, dominant.getRangIndex());
        assertEquals(2, cible.getRangIndex());

    }

    @Test
    void testTransformationEnHumain() {
        Lycanthropes alpha = new Lycanthropes(Sexe.MALE, CategorieAge.ADULTE, 30, 0.5, 0);
        Lycanthropes femelle = new Lycanthropes(Sexe.FEMELLE, CategorieAge.ADULTE, 20, 0.3, 1);

        Pack p = new Pack("Lune", alpha, femelle);

        alpha.transformerEnHumainPourTest();

        assertTrue(alpha.estHumain(), "Le lycan doit être devenu humain");
        assertNull(alpha.getMeute(), "Le lycan ne doit plus avoir de meute");
    }


    @Test
    void testVieillissement() {
        Lycanthropes l = new Lycanthropes(Sexe.FEMELLE, CategorieAge.JEUNE, 10, 0.3, 2);

        l.vieillir();

        assertEquals(CategorieAge.ADULTE, l.getCategorie());
    }
}

