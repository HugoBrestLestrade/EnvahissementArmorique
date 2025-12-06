package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.Gaulois;
import org.example.envahissementarmorique.model.character.base.Roman;
import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.item.Food;
import java.util.ArrayList;
import java.util.List;

/**
 * Bourgade gallo-romaine - ne peut contenir que des Gaulois et des Romains
 * Zone de cohabitation et d'échanges entre les deux peuples
 */
public class GalloRomanTown extends Place {

    private int peacefulnessLevel; // Niveau de paix (0-100)
    private int commerceLevel;     // Niveau de commerce (0-100)

    public GalloRomanTown(String name, float area, ClanLeader chief) {
        super(name, area, chief);
        this.peacefulnessLevel = 75; // Paix relative par défaut
        this.commerceLevel = 60;     // Commerce modéré par défaut
    }

    public GalloRomanTown(String name, float area, ClanLeader chief, int peacefulnessLevel, int commerceLevel) {
        super(name, area, chief);
        this.peacefulnessLevel = Math.max(0, Math.min(100, peacefulnessLevel));
        this.commerceLevel = Math.max(0, Math.min(100, commerceLevel));
    }

    @Override
    protected boolean canAddCharacter(GameCharacter c) {
        // Accepte uniquement les Gaulois et les Romains
        // PAS de créatures fantastiques (zone civilisée)
        return (c instanceof Gaulois || c instanceof Roman);
    }

    @Override
    public boolean addCharacter(GameCharacter c) {
        if (c == null) {
            System.out.println("Erreur : personnage null");
            return false;
        }

        if (!(c instanceof Gaulois || c instanceof Roman)) {
            System.out.println("❌ " + c.getName() + " ne peut pas entrer dans cette bourgade gallo-romaine");
            System.out.println("   (Seuls les Gaulois et Romains sont acceptés)");
            return false;
        }

        if (canAddCharacter(c)) {
            characters.add(c);
            System.out.println("🏘️ " + c.getName() + " entre dans la bourgade " + name);

            // Ajuster le niveau de paix en fonction de la belligérance
            if (c.getBelligerence() > 70) {
                decreasePeacefulness(5);
            }

            return true;
        }

        return false;
    }

    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("🏘️  BOURGADE GALLO-ROMAINE : " + name);
        System.out.println("Superficie : " + area + " m²");

        if (chief != null) {
            System.out.println("Chef : " + chief.getName());
        } else {
            System.out.println("Chef : Aucun");
        }

        // Statistiques de paix et commerce
        System.out.println("\n📊 Indicateurs :");
        System.out.println("  Niveau de paix : " + getPeacefulnessBar() + " (" + peacefulnessLevel + "%)");
        System.out.println("  Niveau de commerce : " + getCommerceBar() + " (" + commerceLevel + "%)");

        // Composition de la population
        int gauloisCount = countGaulois();
        int romanCount = countRomans();

        System.out.println("\n👥 Population : " + characters.size() + " habitant(s)");
        System.out.println("  - Gaulois : " + gauloisCount);
        System.out.println("  - Romains : " + romanCount);

        if (!characters.isEmpty()) {
            System.out.println("\nHabitants présents :");
            for (GameCharacter c : characters) {
                String status = c.isDead() ? " [MORT]" : " [Santé: " + c.getHealth() + "]";
                String origin = (c instanceof Gaulois) ? " [Gaulois]" : " [Romain]";
                System.out.println("  • " + c.toString() + origin + status);
            }
        } else {
            System.out.println("  (Bourgade déserte)");
        }

        System.out.println("\nNourriture disponible : " + foods.size());
        if (!foods.isEmpty()) {
            System.out.println("Provisions :");
            for (Food f : foods) {
                System.out.println("  • " + f.toString());
            }
        } else {
            System.out.println("  (Aucune provision)");
        }

        System.out.println("\nÉtat général : " + getTownStatus());

        if (isTenseAtmosphere()) {
            System.out.println("⚠️ Atmosphère tendue - Risque de conflit !");
        }

        System.out.println("========================================\n");
    }

    /**
     * Compte le nombre de Gaulois
     */
    private int countGaulois() {
        int count = 0;
        for (GameCharacter c : characters) {
            if (c instanceof Gaulois && !c.isDead()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Compte le nombre de Romains
     */
    private int countRomans() {
        int count = 0;
        for (GameCharacter c : characters) {
            if (c instanceof Roman && !c.isDead()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Obtient les Gaulois de la bourgade
     */
    public List<GameCharacter> getGaulois() {
        List<GameCharacter> gaulois = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c instanceof Gaulois) {
                gaulois.add(c);
            }
        }
        return gaulois;
    }

    /**
     * Obtient les Romains de la bourgade
     */
    public List<GameCharacter> getRomans() {
        List<GameCharacter> romans = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c instanceof Roman) {
                romans.add(c);
            }
        }
        return romans;
    }

    /**
     * Vérifie si l'atmosphère est tendue
     */
    public boolean isTenseAtmosphere() {
        // Atmosphère tendue si :
        // - Niveau de paix bas
        // - Trop de personnages belligérants
        // - Déséquilibre important entre Gaulois et Romains

        if (peacefulnessLevel < 30) {
            return true;
        }

        int belligerentCount = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead() && c.getBelligerence() > 60) {
                belligerentCount++;
            }
        }

        if (belligerentCount > characters.size() * 0.5) {
            return true;
        }

        int gauloisCount = countGaulois();
        int romanCount = countRomans();
        if (gauloisCount > 0 && romanCount > 0) {
            float ratio = Math.max(gauloisCount, romanCount) / (float)Math.min(gauloisCount, romanCount);
            if (ratio > 3.0) { // Déséquilibre 3:1
                return true;
            }
        }

        return false;
    }

    /**
     * Favorise le commerce entre les habitants
     */
    public void promoteCommerce() {
        System.out.println("\n💰 Promotion du commerce à " + name);

        if (characters.size() < 2) {
            System.out.println("  Pas assez d'habitants pour commercer");
            return;
        }

        // Augmenter le niveau de commerce
        commerceLevel = Math.min(100, commerceLevel + 10);

        // Répartir équitablement la nourriture
        int foodPerPerson = foods.size() / characters.size();
        if (foodPerPerson > 0) {
            System.out.println("  Échange de " + foodPerPerson + " aliment(s) par personne");
            increasePeacefulness(5);
        }

        System.out.println("  Niveau de commerce : " + commerceLevel + "%");
    }

    /**
     * Organise une fête pour améliorer la cohésion
     */
    public void organizeFeast() {
        System.out.println("\n🎉 Organisation d'une fête à " + name);

        if (foods.size() < characters.size()) {
            System.out.println("  ⚠️ Pas assez de nourriture pour tout le monde");
            decreasePeacefulness(10);
            return;
        }

        // Nourrir tout le monde
        feedAll();

        // Améliorer la paix et réduire la belligérance
        for (GameCharacter c : characters) {
            if (!c.isDead()) {
                c.setBelligerence(Math.max(0, c.getBelligerence() - 15));
            }
        }

        increasePeacefulness(20);
        System.out.println("  La fête améliore l'entente entre Gaulois et Romains !");
    }

    /**
     * Gère les conflits potentiels
     */
    public void manageConflicts() {
        System.out.println("\n⚖️ Gestion des conflits à " + name);

        List<GameCharacter> troublemakers = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (!c.isDead() && c.getBelligerence() > 75) {
                troublemakers.add(c);
            }
        }

        if (troublemakers.isEmpty()) {
            System.out.println("  Aucun conflit à signaler");
            increasePeacefulness(5);
        } else {
            System.out.println("  " + troublemakers.size() + " fauteur(s) de troubles identifié(s)");
            for (GameCharacter t : troublemakers) {
                System.out.println("    - " + t.getName() + " est calmé");
                t.setBelligerence(t.getBelligerence() - 25);
            }
            decreasePeacefulness(5);
        }
    }

    /**
     * Augmente le niveau de paix
     */
    public void increasePeacefulness(int amount) {
        peacefulnessLevel = Math.min(100, peacefulnessLevel + amount);
    }

    /**
     * Diminue le niveau de paix
     */
    public void decreasePeacefulness(int amount) {
        peacefulnessLevel = Math.max(0, peacefulnessLevel - amount);
    }

    /**
     * Augmente le niveau de commerce
     */
    public void increaseCommerce(int amount) {
        commerceLevel = Math.min(100, commerceLevel + amount);
    }

    /**
     * Diminue le niveau de commerce
     */
    public void decreaseCommerce(int amount) {
        commerceLevel = Math.max(0, commerceLevel - amount);
    }

    /**
     * Barre visuelle pour le niveau de paix
     */
    private String getPeacefulnessBar() {
        int bars = peacefulnessLevel / 10;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            if (i < bars) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("]");
        return bar.toString();
    }

    /**
     * Barre visuelle pour le niveau de commerce
     */
    private String getCommerceBar() {
        int bars = commerceLevel / 10;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            if (i < bars) {
                bar.append("█");
            } else {
                bar.append("░");
            }
        }
        bar.append("]");
        return bar.toString();
    }

    /**
     * Détermine l'état général de la bourgade
     */
    private String getTownStatus() {
        int avgLevel = (peacefulnessLevel + commerceLevel) / 2;

        if (avgLevel >= 80) {
            return "🟢 Prospère et paisible";
        } else if (avgLevel >= 60) {
            return "🟡 Stable";
        } else if (avgLevel >= 40) {
            return "🟠 Tensions modérées";
        } else {
            return "🔴 Conflits fréquents";
        }
    }

    // Getters et setters
    public int getPeacefulnessLevel() {
        return peacefulnessLevel;
    }

    public void setPeacefulnessLevel(int peacefulnessLevel) {
        this.peacefulnessLevel = Math.max(0, Math.min(100, peacefulnessLevel));
    }

    public int getCommerceLevel() {
        return commerceLevel;
    }

    public void setCommerceLevel(int commerceLevel) {
        this.commerceLevel = Math.max(0, Math.min(100, commerceLevel));
    }
}