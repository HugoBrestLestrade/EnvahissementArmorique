package org.example.envahissementarmorique.model.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour l’énumération {@link Freshness}.
 * <p>
 * Cette classe teste la logique de dégradation de la fraîcheur des aliments.
 * </p>
 *
 * <p>
 * Les tests incluent :
 * <ul>
 *     <li>La dégradation progressive des valeurs FRESH → OKAY → ROTTEN.</li>
 *     <li>La conservation de l’état ROTTEN lorsqu’on tente de le dégrader davantage.</li>
 *     <li>La vérification des étiquettes (labels) associées à chaque valeur.</li>
 *     <li>La vérification de l’accès à toutes les valeurs de l’énumération.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Chaque méthode de test est annotée avec {@link Test} et possède une description
 * via {@link DisplayName} pour une meilleure lisibilité dans les rapports de test.
 * </p>
 *
 * @author Boud
 * @version 1.0
 */
@DisplayName("Tests de l’énumération Freshness")
class FreshnessTest {

    /**
     * Vérifie que la dégradation passe de FRESH à OKAY.
     */
    @Test
    @DisplayName("Doit dégrader FRESH vers OKAY")
    void testDegradeFreshToOkay() {
        Freshness fresh = Freshness.FRESH;
        Freshness degraded = fresh.degrade();

        assertEquals(Freshness.OKAY, degraded);
    }

    /**
     * Vérifie que la dégradation passe de OKAY à ROTTEN.
     */
    @Test
    @DisplayName("Doit dégrader OKAY vers ROTTEN")
    void testDegradeOkayToRotten() {
        Freshness okay = Freshness.OKAY;
        Freshness degraded = okay.degrade();

        assertEquals(Freshness.ROTTEN, degraded);
    }

    /**
     * Vérifie que ROTTEN reste ROTTEN après une tentative de dégradation.
     */
    @Test
    @DisplayName("Doit rester ROTTEN si déjà ROTTEN")
    void testDegradeRottenStaysRotten() {
        Freshness rotten = Freshness.ROTTEN;
        Freshness degraded = rotten.degrade();

        assertEquals(Freshness.ROTTEN, degraded);
        assertSame(rotten, degraded.degrade());
    }

    /**
     * Vérifie que les étiquettes (labels) des valeurs sont correctes.
     */
    @Test
    @DisplayName("Doit avoir des labels corrects")
    void testLabels() {
        assertEquals("Fresh", Freshness.FRESH.getLabel());
        assertEquals("Quite Fresh", Freshness.OKAY.getLabel());
        assertEquals("Rotten", Freshness.ROTTEN.getLabel());
    }

    /**
     * Vérifie la dégradation multiple successive.
     */
    @Test
    @DisplayName("Doit dégrader plusieurs fois correctement")
    void testMultipleDegradations() {
        Freshness current = Freshness.FRESH;

        // Première dégradation : FRESH -> OKAY
        current = current.degrade();
        assertEquals(Freshness.OKAY, current);

        // Deuxième dégradation : OKAY -> ROTTEN
        current = current.degrade();
        assertEquals(Freshness.ROTTEN, current);

        // Troisième dégradation : ROTTEN -> ROTTEN
        current = current.degrade();
        assertEquals(Freshness.ROTTEN, current);

        // Quatrième dégradation : toujours ROTTEN
        current = current.degrade();
        assertEquals(Freshness.ROTTEN, current);
    }

    /**
     * Vérifie que toutes les valeurs de l’énumération sont accessibles et correctes.
     */
    @Test
    @DisplayName("Toutes les valeurs de l’énumération doivent être accessibles")
    void testEnumValues() {
        Freshness[] values = Freshness.values();

        assertEquals(3, values.length);
        assertEquals(Freshness.FRESH, values[0]);
        assertEquals(Freshness.OKAY, values[1]);
        assertEquals(Freshness.ROTTEN, values[2]);
    }
}
