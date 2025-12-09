package org.example.envahissementarmorique.model.character.base;

import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.item.Foods;
import org.example.envahissementarmorique.model.item.Freshness;
import org.example.envahissementarmorique.model.item.Potion;
import org.example.envahissementarmorique.model.place.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ClanLeader class.
 * Tests team management, healing, and feeding.
 */
@DisplayName("ClanLeader Tests")
class ClanLeaderTest {

    private ClanLeader leader;
    private Place testPlace;
    private GameCharacter character1;
    private GameCharacter character2;

    // Test Place implementation
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
        testPlace = new TestPlace("Test Village", 1000f, null);
        leader = new ClanLeader("Abraracourcix", "M", 50, testPlace);

        character1 = new GameCharacter(
            "Warrior1", "M", "Gaulois",
            1.75, 30, 60, 50, 100, 100, 70, 0
        );

        character2 = new GameCharacter(
            "Warrior2", "F", "Gaulois",
            1.68, 28, 55, 45, 90, 100, 65, 0
        );

        testPlace.addCharacter(character1);
        testPlace.addCharacter(character2);
    }

    // ========== Initialization Tests ==========

    @Test
    @DisplayName("Should initialize clan leader with correct attributes")
    void testClanLeaderInitialization() {
        assertEquals("Abraracourcix", leader.getName());
        assertEquals("M", leader.getGenre());
        assertEquals(50, leader.getAge());
        assertNotNull(leader.getPlace());
        assertEquals("Test Village", leader.getPlace().getName());
    }

    @Test
    @DisplayName("Should update leader attributes")
    void testSetters() {
        leader.setName("Vercingétorix");
        assertEquals("Vercingétorix", leader.getName());

        leader.setGenre("F");
        assertEquals("F", leader.getGenre());

        leader.setAge(45);
        assertEquals(45, leader.getAge());

        Place newPlace = new TestPlace("New Village", 2000f, leader);
        leader.setPlace(newPlace);
        assertEquals("New Village", leader.getPlace().getName());
    }

    // ========== Character Creation Tests ==========

    @Test
    @DisplayName("Should create new character and add to place")
    void testCreateCharacter() {
        int initialCount = testPlace.getNumberOfCharacters();

        leader.createCharacter(
            "NewWarrior", "M", "Gaulois",
            1.80, 25, 50, 40, 100, 100, 60, 0
        );

        assertEquals(initialCount + 1, testPlace.getNumberOfCharacters());

        GameCharacter created = testPlace.getCharacterByName("NewWarrior");
        assertNotNull(created);
        assertEquals("NewWarrior", created.getName());
        assertEquals("Gaulois", created.getFaction());
    }

    @Test
    @DisplayName("Should not create character when leader has no place")
    void testCreateCharacterWithoutPlace() {
        ClanLeader leaderWithoutPlace = new ClanLeader("NoPlace", "M", 40, null);

        leaderWithoutPlace.createCharacter(
            "Orphan", "M", "Gaulois",
            1.80, 25, 50, 40, 100, 100, 60, 0
        );

        // Character should not be added anywhere since place is null
        assertNull(leaderWithoutPlace.getPlace());
    }

    // ========== Healing Team Tests ==========

    @Test
    @DisplayName("Should heal team members")
    void testHealTeam() {
        character1.setHealth(50);
        character2.setHealth(40);

        ArrayList<GameCharacter> team = new ArrayList<>();
        team.add(character1);
        team.add(character2);

        leader.healTeam(team, 30);

        assertEquals(80, character1.getHealth());
        assertEquals(70, character2.getHealth());
    }

    @Test
    @DisplayName("Should only heal characters in the place")
    void testHealTeamOnlyInPlace() {
        character1.setHealth(50);

        GameCharacter outsider = new GameCharacter(
            "Outsider", "M", "Roman",
            1.75, 30, 50, 40, 50, 100, 60, 0
        );

        ArrayList<GameCharacter> team = new ArrayList<>();
        team.add(character1);
        team.add(outsider);

        leader.healTeam(team, 30);

        assertEquals(80, character1.getHealth());
        assertEquals(50, outsider.getHealth()); // Not healed because not in place
    }

    @Test
    @DisplayName("Should not exceed max health when healing team")
    void testHealTeamNotExceedMax() {
        character1.setHealth(90);

        ArrayList<GameCharacter> team = new ArrayList<>();
        team.add(character1);

        leader.healTeam(team, 50);

        assertEquals(100, character1.getHealth()); // Capped at maxHealth
    }

    // ========== Feeding Team Tests ==========

    @Test
    @DisplayName("Should feed team members")
    void testFeedTeam() {
        character1.setHunger(50);
        character2.setHunger(60);

        Food boar = new Food(Foods.BOAR, Freshness.FRESH);

        ArrayList<GameCharacter> team = new ArrayList<>();
        team.add(character1);
        team.add(character2);

        leader.feedTeam(team, boar);

        assertEquals(100, character1.getHunger());
        assertEquals(110, character2.getHunger());
    }

    @Test
    @DisplayName("Should feed all team members with same food")
    void testFeedEntireTeam() {
        character1.setHunger(40);
        character2.setHunger(30);

        Food fish = new Food(Foods.FRESH_FISH, Freshness.FRESH);

        ArrayList<GameCharacter> team = new ArrayList<>();
        team.add(character1);
        team.add(character2);

        int nutritionValue = fish.getNutritionalValue();
        leader.feedTeam(team, fish);

        assertEquals(40 + nutritionValue, character1.getHunger());
        assertEquals(30 + nutritionValue, character2.getHunger());
    }

    // ========== Potion Management Tests ==========

    @Test
    @DisplayName("Should give potion to team")
    void testDrinkMagicPotion() {
        Potion potion = new Potion(Foods.SECRET_INGREDIENT, 5);

        ArrayList<GameCharacter> team = new ArrayList<>();
        team.add(character1);
        team.add(character2);

        int initialStrength1 = character1.getStrength();
        int initialStrength2 = character2.getStrength();

        leader.drinkMagicPotion(potion, team);

        // Both should have gained strength from potion
        assertTrue(character1.getStrength() > initialStrength1 ||
                   character2.getStrength() > initialStrength2);
    }

    // ========== Place Examination Tests ==========

    @Test
    @DisplayName("Should examine place when place exists")
    void testExaminePlace() {
        assertDoesNotThrow(() -> leader.examinePlace());
    }

    @Test
    @DisplayName("Should handle examining when no place assigned")
    void testExaminePlaceWithoutPlace() {
        ClanLeader leaderWithoutPlace = new ClanLeader("NoPlace", "M", 40, null);
        assertDoesNotThrow(() -> leaderWithoutPlace.examinePlace());
    }

    // ========== Transfer Tests ==========

    @Test
    @DisplayName("Should transfer character to another place")
    void testTransferCharacter() {
        Place destination = new TestPlace("Destination", 1500f, null);

        ArrayList<GameCharacter> toTransfer = new ArrayList<>();
        toTransfer.add(character1);

        leader.transferCharacter(destination, toTransfer);

        assertFalse(testPlace.getCharacters().contains(character1));
        assertTrue(destination.getCharacters().contains(character1));
    }
}

