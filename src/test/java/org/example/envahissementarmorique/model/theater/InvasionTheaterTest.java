package org.example.envahissementarmorique.model.theater;

import org.example.envahissementarmorique.model.character.base.*;
import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.item.Foods;
import org.example.envahissementarmorique.model.item.Freshness;
import org.example.envahissementarmorique.model.place.Battlefield;
import org.example.envahissementarmorique.model.place.GaulishVillage;
import org.example.envahissementarmorique.model.place.Place;
import org.example.envahissementarmorique.model.place.RomanCamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for InvasionTheater class.
 * Tests theater management, simulation mechanics, and battle system.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
@DisplayName("InvasionTheater Tests")
class InvasionTheaterTest {

    private InvasionTheater theater;
    private GaulishVillage gaulishVillage;
    private RomanCamp romanCamp;
    private Battlefield battlefield;
    private ClanLeader gaulishLeader;
    private ClanLeader romanLeader;

    /**
     * Concrete implementation of Place for testing.
     */
    private static class TestPlace extends Place {
        public TestPlace(String name, float area, ClanLeader chief) {
            super(name, area, chief);
        }

        @Override
        protected boolean canAddCharacter(GameCharacter c) {
            return c != null && !c.isDead();
        }
    }

    @BeforeEach
    void setUp() {
        theater = new InvasionTheater("Armorique", 10);

        gaulishLeader = new ClanLeader("Abraracourcix", "M", 50, null);
        gaulishVillage = new GaulishVillage("Village Gaulois", 1000f, gaulishLeader);
        gaulishLeader.setPlace(gaulishVillage);

        romanLeader = new ClanLeader("Caesar", "M", 55, null);
        romanCamp = new RomanCamp("Roman Camp", 800f, romanLeader);
        romanLeader.setPlace(romanCamp);

        battlefield = new Battlefield("Battlefield of Armorique", 500f);
    }

    /**
     * Initialization Tests
     */

    @Test
    @DisplayName("Should initialize theater with correct attributes")
    void testTheaterInitialization() {
        assertEquals("Armorique", theater.getName());
        assertEquals(10, theater.getMaxPlaces());
        assertEquals(0, theater.getPlaces().size());
        assertEquals(0, theater.getClanLeaders().size());
    }

    @Test
    @DisplayName("Should create theater with empty collections")
    void testEmptyCollections() {
        assertNotNull(theater.getPlaces());
        assertNotNull(theater.getClanLeaders());
        assertTrue(theater.getPlaces().isEmpty());
        assertTrue(theater.getClanLeaders().isEmpty());
    }

    /**
     * Place Management Tests
     */

    @Test
    @DisplayName("Should add place to theater")
    void testAddPlace() {
        boolean added = theater.addPlace(gaulishVillage);

        assertTrue(added);
        assertEquals(1, theater.getPlaces().size());
        assertTrue(theater.getPlaces().contains(gaulishVillage));
    }

    @Test
    @DisplayName("Should add multiple places")
    void testAddMultiplePlaces() {
        theater.addPlace(gaulishVillage);
        theater.addPlace(romanCamp);
        theater.addPlace(battlefield);

        assertEquals(3, theater.getPlaces().size());
    }

    @Test
    @DisplayName("Should not add null place")
    void testAddNullPlace() {
        boolean added = theater.addPlace(null);

        assertFalse(added);
        assertEquals(0, theater.getPlaces().size());
    }

    @Test
    @DisplayName("Should not add place when theater is full")
    void testAddPlaceWhenFull() {
        InvasionTheater smallTheater = new InvasionTheater("Small", 2);

        smallTheater.addPlace(gaulishVillage);
        smallTheater.addPlace(romanCamp);
        boolean added = smallTheater.addPlace(battlefield);

        assertFalse(added);
        assertEquals(2, smallTheater.getPlaces().size());
    }

    @Test
    @DisplayName("Should remove place from theater")
    void testRemovePlace() {
        theater.addPlace(gaulishVillage);
        theater.addPlace(romanCamp);

        boolean removed = theater.removePlace(gaulishVillage);

        assertTrue(removed);
        assertEquals(1, theater.getPlaces().size());
        assertFalse(theater.getPlaces().contains(gaulishVillage));
    }

    @Test
    @DisplayName("Should return false when removing non-existent place")
    void testRemoveNonExistentPlace() {
        theater.addPlace(gaulishVillage);

        boolean removed = theater.removePlace(romanCamp);

        assertFalse(removed);
        assertEquals(1, theater.getPlaces().size());
    }

    /**
     * Clan Leader Management Tests
     */

    @Test
    @DisplayName("Should add clan leader to theater")
    void testAddClanLeader() {
        theater.addClanLeader(gaulishLeader);

        assertEquals(1, theater.getClanLeaders().size());
        assertTrue(theater.getClanLeaders().contains(gaulishLeader));
    }

    @Test
    @DisplayName("Should add multiple clan leaders")
    void testAddMultipleClanLeaders() {
        theater.addClanLeader(gaulishLeader);
        theater.addClanLeader(romanLeader);

        assertEquals(2, theater.getClanLeaders().size());
    }

    @Test
    @DisplayName("Should not add null clan leader")
    void testAddNullClanLeader() {
        theater.addClanLeader(null);

        assertEquals(0, theater.getClanLeaders().size());
    }

    @Test
    @DisplayName("Should not add duplicate clan leader")
    void testAddDuplicateClanLeader() {
        theater.addClanLeader(gaulishLeader);
        theater.addClanLeader(gaulishLeader);

        assertEquals(1, theater.getClanLeaders().size());
    }

    @Test
    @DisplayName("Should remove clan leader from theater")
    void testRemoveClanLeader() {
        theater.addClanLeader(gaulishLeader);
        theater.addClanLeader(romanLeader);

        boolean removed = theater.removeClanLeader(gaulishLeader);

        assertTrue(removed);
        assertEquals(1, theater.getClanLeaders().size());
        assertFalse(theater.getClanLeaders().contains(gaulishLeader));
    }

    /**
     * Display Tests
     */

    @Test
    @DisplayName("Should display places without errors")
    void testDisplayPlaces() {
        theater.addPlace(gaulishVillage);
        theater.addPlace(romanCamp);

        assertDoesNotThrow(() -> theater.displayPlaces());
    }

    @Test
    @DisplayName("Should display empty theater without errors")
    void testDisplayEmptyTheater() {
        assertDoesNotThrow(() -> theater.displayPlaces());
    }

    @Test
    @DisplayName("Should display all characters without errors")
    void testDisplayAllCharacters() {
        theater.addPlace(gaulishVillage);

        GameCharacter asterix = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 100, 100, 80, 0);
        gaulishVillage.addCharacter(asterix);

        assertDoesNotThrow(() -> theater.displayAllCharacters());
    }

    /**
     * Character Count Tests
     */

    @Test
    @DisplayName("Should count total characters across all places")
    void testGetTotalCharacters() {
        theater.addPlace(gaulishVillage);
        theater.addPlace(romanCamp);

        GameCharacter asterix = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 100, 100, 80, 0);
        GameCharacter obelix = new Gaulois("Obélix", "M", 1.85, 35, 90, 60, 120, 100, 70, 0);
        GameCharacter centurion = new Roman("Centurion", "M", 1.75, 30, 50, 40, 100, 100, 60, 0);

        gaulishVillage.addCharacter(asterix);
        gaulishVillage.addCharacter(obelix);
        romanCamp.addCharacter(centurion);

        assertEquals(3, theater.getTotalCharacters());
    }

    @Test
    @DisplayName("Should return zero when no characters exist")
    void testGetTotalCharactersEmpty() {
        theater.addPlace(gaulishVillage);
        theater.addPlace(romanCamp);

        assertEquals(0, theater.getTotalCharacters());
    }

    /**
     * Battle Tests
     */

    @Test
    @DisplayName("Should conduct battles on battlefield")
    void testConductBattles() {
        theater.addPlace(battlefield);

        GameCharacter gaulois = new Gaulois("Warrior", "M", 1.70, 30, 60, 50, 100, 100, 80, 0);
        GameCharacter roman = new Roman("Legionnaire", "M", 1.75, 28, 55, 45, 100, 100, 75, 0);

        battlefield.addCharacter(gaulois);
        battlefield.addCharacter(roman);

        assertDoesNotThrow(() -> theater.conductBattles());
    }

    @Test
    @DisplayName("Should not fight if same faction")
    void testNoBattleSameFaction() {
        theater.addPlace(battlefield);

        GameCharacter gaulois1 = new Gaulois("Warrior1", "M", 1.70, 30, 60, 50, 100, 100, 80, 0);
        GameCharacter gaulois2 = new Gaulois("Warrior2", "M", 1.75, 28, 55, 45, 100, 100, 75, 0);

        battlefield.addCharacter(gaulois1);
        battlefield.addCharacter(gaulois2);

        int health1Before = gaulois1.getHealth();
        int health2Before = gaulois2.getHealth();

        theater.conductBattles();

        assertEquals(health1Before, gaulois1.getHealth());
        assertEquals(health2Before, gaulois2.getHealth());
    }

    /**
     * Random Modification Tests
     */

    @Test
    @DisplayName("Should randomly modify characters without errors")
    void testRandomlyModifyCharacters() {
        theater.addPlace(gaulishVillage);

        GameCharacter asterix = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 100, 100, 80, 5);
        gaulishVillage.addCharacter(asterix);

        assertDoesNotThrow(() -> theater.randomlyModifyCharacters());
    }

    @Test
    @DisplayName("Should not modify dead characters")
    void testNoModificationForDeadCharacters() {
        theater.addPlace(gaulishVillage);

        GameCharacter dead = new Gaulois("Dead", "M", 1.70, 30, 50, 40, 0, 100, 50, 0);
        gaulishVillage.addCharacter(dead);

        assertDoesNotThrow(() -> theater.randomlyModifyCharacters());
        assertEquals(0, dead.getHealth());
    }

    /**
     * Food Tests
     */

    @Test
    @DisplayName("Should spawn food in non-battlefield places")
    void testSpawnFood() {
        theater.addPlace(gaulishVillage);
        theater.addPlace(romanCamp);
        theater.addPlace(battlefield);

        int initialFoodCount = gaulishVillage.getFoods().size() +
                              romanCamp.getFoods().size() +
                              battlefield.getFoods().size();

        assertDoesNotThrow(() -> theater.spawnFood());
    }

    @Test
    @DisplayName("Should degrade food freshness")
    void testDegradeFood() {
        theater.addPlace(gaulishVillage);

        Food freshFood = new Food(Foods.BOAR, Freshness.FRESH);
        gaulishVillage.addFood(freshFood);

        theater.degradeFood();

        assertEquals(Freshness.OKAY, freshFood.getFreshness());
    }

    @Test
    @DisplayName("Should degrade food multiple times")
    void testMultipleFoodDegradation() {
        theater.addPlace(gaulishVillage);

        Food freshFood = new Food(Foods.BOAR, Freshness.FRESH);
        gaulishVillage.addFood(freshFood);

        theater.degradeFood();
        assertEquals(Freshness.OKAY, freshFood.getFreshness());

        theater.degradeFood();
        assertEquals(Freshness.ROTTEN, freshFood.getFreshness());

        theater.degradeFood();
        assertEquals(Freshness.ROTTEN, freshFood.getFreshness());
    }

    /**
     * Clan Leader Control Tests
     */

    @Test
    @DisplayName("Should give control to clan leader with valid actions")
    void testGiveClanLeaderControl() {
        theater.addPlace(gaulishVillage);
        theater.addClanLeader(gaulishLeader);

        GameCharacter asterix = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 50, 50, 80, 0);
        gaulishVillage.addCharacter(asterix);

        String input = "4\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> theater.giveClanLeaderControl(gaulishLeader, scanner, 2));
    }

    @Test
    @DisplayName("Should handle clan leader without place")
    void testClanLeaderWithoutPlace() {
        ClanLeader noPlaceLeader = new ClanLeader("NoPlace", "M", 40, null);
        theater.addClanLeader(noPlaceLeader);

        String input = "";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> theater.giveClanLeaderControl(noPlaceLeader, scanner, 2));
    }

    /**
     * Simulation Tests
     */

    @Test
    @DisplayName("Should run simulation without errors")
    void testRunSimulation() {
        theater.addPlace(gaulishVillage);
        theater.addPlace(battlefield);
        theater.addClanLeader(gaulishLeader);

        GameCharacter asterix = new Gaulois("Astérix", "M", 1.65, 35, 70, 50, 100, 100, 80, 0);
        gaulishVillage.addCharacter(asterix);

        String input = "4\n\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> theater.runSimulation(1, scanner));
    }

    /**
     * Getters and Setters Tests
     */

    @Test
    @DisplayName("Should update theater name")
    void testSetName() {
        theater.setName("New Armorique");
        assertEquals("New Armorique", theater.getName());
    }

    @Test
    @DisplayName("Should update max places")
    void testSetMaxPlaces() {
        theater.setMaxPlaces(20);
        assertEquals(20, theater.getMaxPlaces());
    }

    @Test
    @DisplayName("Should return defensive copy of places list")
    void testGetPlacesDefensiveCopy() {
        theater.addPlace(gaulishVillage);

        List<Place> places = theater.getPlaces();
        places.clear();

        assertEquals(1, theater.getPlaces().size());
    }

    @Test
    @DisplayName("Should return defensive copy of clan leaders list")
    void testGetClanLeadersDefensiveCopy() {
        theater.addClanLeader(gaulishLeader);

        List<ClanLeader> leaders = theater.getClanLeaders();
        leaders.clear();

        assertEquals(1, theater.getClanLeaders().size());
    }
}

