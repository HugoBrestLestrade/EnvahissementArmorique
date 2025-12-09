package org.example.envahissementarmorique.model.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Freshness enum.
 * Tests freshness degradation logic.
 */
@DisplayName("Freshness Enum Tests")
class FreshnessTest {

    @Test
    @DisplayName("Should degrade from FRESH to OKAY")
    void testDegradeFreshToOkay() {
        Freshness fresh = Freshness.FRESH;
        Freshness degraded = fresh.degrade();

        assertEquals(Freshness.OKAY, degraded);
    }

    @Test
    @DisplayName("Should degrade from OKAY to ROTTEN")
    void testDegradeOkayToRotten() {
        Freshness okay = Freshness.OKAY;
        Freshness degraded = okay.degrade();

        assertEquals(Freshness.ROTTEN, degraded);
    }

    @Test
    @DisplayName("Should stay ROTTEN when already ROTTEN")
    void testDegradeRottenStaysRotten() {
        Freshness rotten = Freshness.ROTTEN;
        Freshness degraded = rotten.degrade();

        assertEquals(Freshness.ROTTEN, degraded);
        assertSame(rotten, degraded.degrade());
    }

    @Test
    @DisplayName("Should have correct labels")
    void testLabels() {
        assertEquals("Fresh", Freshness.FRESH.getLabel());
        assertEquals("Quite Fresh", Freshness.OKAY.getLabel());
        assertEquals("Rotten", Freshness.ROTTEN.getLabel());
    }

    @Test
    @DisplayName("Should degrade multiple times correctly")
    void testMultipleDegradations() {
        Freshness current = Freshness.FRESH;

        // First degradation: FRESH -> OKAY
        current = current.degrade();
        assertEquals(Freshness.OKAY, current);

        // Second degradation: OKAY -> ROTTEN
        current = current.degrade();
        assertEquals(Freshness.ROTTEN, current);

        // Third degradation: ROTTEN -> ROTTEN
        current = current.degrade();
        assertEquals(Freshness.ROTTEN, current);

        // Fourth degradation: still ROTTEN
        current = current.degrade();
        assertEquals(Freshness.ROTTEN, current);
    }

    @Test
    @DisplayName("All enum values should be accessible")
    void testEnumValues() {
        Freshness[] values = Freshness.values();

        assertEquals(3, values.length);
        assertEquals(Freshness.FRESH, values[0]);
        assertEquals(Freshness.OKAY, values[1]);
        assertEquals(Freshness.ROTTEN, values[2]);
    }
}

