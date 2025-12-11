package org.example.envahissementarmorique.model.theater;

import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.character.base.Gaulish.Gaulois;
import org.example.envahissementarmorique.model.character.base.Roman.Roman;
import org.example.envahissementarmorique.model.place.Battlefield;
import org.example.envahissementarmorique.model.place.GaulishVillage;
import org.example.envahissementarmorique.model.place.RomanCamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la simulation de guerre entre clans.
 *
 * @author Envahissement Armorique Team
 * @version 1.0
 */
class ClanWarSimulationTest {

    private ClanWarSimulation simulation;
    private GaulishVillage villageGaulois;
    private RomanCamp campRomain;
    private Battlefield battlefield;
    private ClanLeader chefGaulois;
    private ClanLeader chefRomain;

    @BeforeEach
    void setUp() {
        simulation = new ClanWarSimulation("Test War", 10);

        // Configuration Clan 1 (Gaulois)
        villageGaulois = new GaulishVillage("Village Test", 5000, null);
        chefGaulois = new ClanLeader("Chef Gaulois", "M", 50, villageGaulois);
        villageGaulois.setChief(chefGaulois);
        simulation.setClan1(chefGaulois, villageGaulois);

        // Configuration Clan 2 (Romain)
        campRomain = new RomanCamp("Camp Test", 8000, null);
        chefRomain = new ClanLeader("Chef Romain", "M", 45, campRomain);
        campRomain.setChief(chefRomain);
        simulation.setClan2(chefRomain, campRomain);

        // Configuration Battlefield
        battlefield = new Battlefield("Test Battlefield", 10000);
        simulation.setBattlefield(battlefield);
    }

    @Test
    void testSimulationCreation() {
        assertNotNull(simulation);
        assertEquals("Test War", simulation.getName());
    }

    @Test
    void testAddCharacterToClan1() {
        Gaulois asterix = new Gaulois("Astérix", "M", 1.60, 35, 80, 85, 100, 100, 75, 0);
        simulation.addCharacterToClan1(asterix);

        assertEquals(1, villageGaulois.getNumberOfCharacters());
        assertEquals("Astérix", villageGaulois.getCharacters().get(0).getName());
    }

    @Test
    void testAddCharacterToClan2() {
        Roman legionnaire = new Roman("Légionnaire", "M", 1.75, 28, 70, 75, 90, 100, 80, 0);
        simulation.addCharacterToClan2(legionnaire);

        assertEquals(1, campRomain.getNumberOfCharacters());
        assertEquals("Légionnaire", campRomain.getCharacters().get(0).getName());
    }

    @Test
    void testAddMultipleCharactersToClan1() {
        Gaulois asterix = new Gaulois("Astérix", "M", 1.60, 35, 80, 85, 100, 100, 75, 0);
        Gaulois obelix = new Gaulois("Obélix", "M", 1.85, 35, 90, 95, 120, 100, 70, 0);

        simulation.addCharacterToClan1(asterix);
        simulation.addCharacterToClan1(obelix);

        assertEquals(2, villageGaulois.getNumberOfCharacters());
    }

    @Test
    void testAddMultipleCharactersToClan2() {
        Roman leg1 = new Roman("Légionnaire 1", "M", 1.75, 28, 70, 75, 90, 100, 80, 0);
        Roman leg2 = new Roman("Légionnaire 2", "M", 1.76, 29, 71, 76, 91, 100, 81, 0);
        Roman leg3 = new Roman("Légionnaire 3", "M", 1.77, 30, 72, 77, 92, 100, 82, 0);

        simulation.addCharacterToClan2(leg1);
        simulation.addCharacterToClan2(leg2);
        simulation.addCharacterToClan2(leg3);

        assertEquals(3, campRomain.getNumberOfCharacters());
    }

    @Test
    void testDeployWarriorsToBattlefield() {
        // Ajouter des guerriers
        Gaulois asterix = new Gaulois("Astérix", "M", 1.60, 35, 80, 85, 100, 100, 75, 0);
        Roman legionnaire = new Roman("Légionnaire", "M", 1.75, 28, 70, 75, 90, 100, 80, 0);

        simulation.addCharacterToClan1(asterix);
        simulation.addCharacterToClan2(legionnaire);

        // Déployer
        simulation.deployWarriorsToBattlefield();

        // Vérifier que les guerriers sont sur le champ de bataille
        assertEquals(2, battlefield.getNumberOfCharacters());
        assertEquals(0, villageGaulois.getNumberOfCharacters());
        assertEquals(0, campRomain.getNumberOfCharacters());
    }

    @Test
    void testReturnSurvivors() {
        // Ajouter des guerriers
        Gaulois asterix = new Gaulois("Astérix", "M", 1.60, 35, 80, 85, 100, 100, 75, 0);
        Roman legionnaire = new Roman("Légionnaire", "M", 1.75, 28, 70, 75, 90, 100, 80, 0);

        simulation.addCharacterToClan1(asterix);
        simulation.addCharacterToClan2(legionnaire);

        // Déployer
        simulation.deployWarriorsToBattlefield();

        // Ramener les survivants
        simulation.returnSurvivors();

        // Vérifier que les guerriers sont revenus (tous vivants dans ce test)
        assertTrue(villageGaulois.getNumberOfCharacters() > 0 || campRomain.getNumberOfCharacters() > 0);
    }

    @Test
    void testBattlefield() {
        assertNotNull(battlefield);
        assertEquals("Test Battlefield", battlefield.getName());
    }

    @Test
    void testPlacesCount() {
        // 3 lieux : village, camp, battlefield
        assertEquals(3, simulation.getPlaces().size());
    }

    @Test
    void testRandomlyModifyCharactersDoesNotCrash() {
        Gaulois asterix = new Gaulois("Astérix", "M", 1.60, 35, 80, 85, 100, 100, 75, 0);
        simulation.addCharacterToClan1(asterix);

        // Cette méthode ne devrait pas planter
        assertDoesNotThrow(() -> simulation.randomlyModifyCharacters());
    }

    @Test
    void testSpawnFoodDoesNotCrash() {
        // Cette méthode ne devrait pas planter
        assertDoesNotThrow(() -> simulation.spawnFood());
    }

    @Test
    void testDegradeFoodDoesNotCrash() {
        // Cette méthode ne devrait pas planter
        assertDoesNotThrow(() -> simulation.degradeFood());
    }

    @Test
    void testConductBattleWithCharacters() {
        // Ajouter des guerriers
        Gaulois asterix = new Gaulois("Astérix", "M", 1.60, 35, 80, 85, 100, 100, 75, 0);
        Roman legionnaire = new Roman("Légionnaire", "M", 1.75, 28, 70, 75, 90, 100, 80, 0);

        simulation.addCharacterToClan1(asterix);
        simulation.addCharacterToClan2(legionnaire);

        // Déployer
        simulation.deployWarriorsToBattlefield();

        // Combat - ne devrait pas planter
        assertDoesNotThrow(() -> simulation.conductBattle());
    }

    @Test
    void testDisplayStatus() {
        // Ajouter quelques personnages
        Gaulois asterix = new Gaulois("Astérix", "M", 1.60, 35, 80, 85, 100, 100, 75, 0);
        Roman legionnaire = new Roman("Légionnaire", "M", 1.75, 28, 70, 75, 90, 100, 80, 0);

        simulation.addCharacterToClan1(asterix);
        simulation.addCharacterToClan2(legionnaire);

        // Ne devrait pas planter
        assertDoesNotThrow(() -> simulation.displayStatus());
    }

    @Test
    void testDisplayFinalResults() {
        // Ajouter quelques personnages
        Gaulois asterix = new Gaulois("Astérix", "M", 1.60, 35, 80, 85, 100, 100, 75, 0);
        Roman legionnaire = new Roman("Légionnaire", "M", 1.75, 28, 70, 75, 90, 100, 80, 0);

        simulation.addCharacterToClan1(asterix);
        simulation.addCharacterToClan2(legionnaire);

        // Ne devrait pas planter
        assertDoesNotThrow(() -> simulation.displayFinalResults());
    }

    @Test
    void testAddPlaceReturnValue() {
        GaulishVillage newVillage = new GaulishVillage("Nouveau Village", 3000, null);
        boolean result = simulation.addPlace(newVillage);
        assertTrue(result);
    }

    @Test
    void testAddPlaceWhenFull() {
        // Créer une simulation avec seulement 3 places max
        ClanWarSimulation smallSim = new ClanWarSimulation("Small Sim", 3);

        // Ajouter les lieux par défaut via la configuration
        GaulishVillage v = new GaulishVillage("V", 1000, null);
        RomanCamp c = new RomanCamp("C", 1000, null);
        Battlefield b = new Battlefield("B", 1000);

        ClanLeader l1 = new ClanLeader("L1", "M", 40, v);
        ClanLeader l2 = new ClanLeader("L2", "M", 40, c);

        v.setChief(l1);
        c.setChief(l2);

        smallSim.setClan1(l1, v);
        smallSim.setClan2(l2, c);
        smallSim.setBattlefield(b);

        // Maintenant c'est plein (3/3), essayer d'ajouter un 4ème lieu
        GaulishVillage extra = new GaulishVillage("Extra", 1000, null);
        boolean result = smallSim.addPlace(extra);
        assertFalse(result);
    }

    @Test
    void testGauloisCannotEnterRomanCamp() {
        Gaulois asterix = new Gaulois("Astérix", "M", 1.60, 35, 80, 85, 100, 100, 75, 0);

        // Essayer d'ajouter un Gaulois au camp romain - devrait échouer
        boolean result = campRomain.addCharacter(asterix);
        assertFalse(result);
        assertEquals(0, campRomain.getNumberOfCharacters());
    }

    @Test
    void testRomanCannotEnterGaulishVillage() {
        Roman legionnaire = new Roman("Légionnaire", "M", 1.75, 28, 70, 75, 90, 100, 80, 0);

        // Essayer d'ajouter un Romain au village gaulois - devrait échouer
        boolean result = villageGaulois.addCharacter(legionnaire);
        assertFalse(result);
        assertEquals(0, villageGaulois.getNumberOfCharacters());
    }

    @Test
    void testBothTypesCanEnterBattlefield() {
        Gaulois asterix = new Gaulois("Astérix", "M", 1.60, 35, 80, 85, 100, 100, 75, 0);
        Roman legionnaire = new Roman("Légionnaire", "M", 1.75, 28, 70, 75, 90, 100, 80, 0);

        // Les deux types peuvent entrer sur un champ de bataille
        assertTrue(battlefield.addCharacter(asterix));
        assertTrue(battlefield.addCharacter(legionnaire));
        assertEquals(2, battlefield.getNumberOfCharacters());
    }
}

