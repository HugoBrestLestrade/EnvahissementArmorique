package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.Gaulish.Gaulois;
import org.example.envahissementarmorique.model.character.base.Roman.Roman;
import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.item.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Bourgade gallo-romaine.
 *
 * <p>
 * Ne peut contenir que des Gaulois et des Romains. Zone de cohabitation et d'échanges entre les deux peuples.
 * Gère le niveau de paix et le niveau de commerce de la bourgade.
 * </p>
 */
public final class GalloRomanTown extends Place {

    /** Niveau de paix (0-100) */
    private int peacefulnessLevel;

    /** Niveau de commerce (0-100) */
    private int commerceLevel;

    /**
     * Constructeur par défaut avec niveaux de paix et de commerce standards.
     *
     * @param name nom de la bourgade
     * @param area superficie en m²
     * @param chief chef de la bourgade
     */
    public GalloRomanTown(String name, float area, ClanLeader chief) {
        super(name, area, chief);
        this.peacefulnessLevel = 75;
        this.commerceLevel = 60;
    }

    /**
     * Constructeur avec niveaux personnalisés de paix et de commerce.
     *
     * @param name nom de la bourgade
     * @param area superficie
     * @param chief chef
     * @param peacefulnessLevel niveau de paix (0-100)
     * @param commerceLevel niveau de commerce (0-100)
     */
    public GalloRomanTown(String name, float area, ClanLeader chief, int peacefulnessLevel, int commerceLevel) {
        super(name, area, chief);
        this.peacefulnessLevel = Math.max(0, Math.min(100, peacefulnessLevel));
        this.commerceLevel = Math.max(0, Math.min(100, commerceLevel));
    }

    /**
     * Vérifie si un personnage peut entrer dans la bourgade.
     *
     * @param character personnage à tester
     * @return true si c'est un Gaulois ou un Romain
     */
    @Override
    protected boolean canAddCharacter(GameCharacter character) {
        return (character instanceof Gaulois || character instanceof Roman);
    }

    /**
     * Ajoute un personnage dans la bourgade si possible.
     *
     * @param character personnage à ajouter
     * @return true si l'ajout a réussi
     */
    @Override
    public boolean addCharacter(GameCharacter character) {
        if (character == null) {
            System.out.println("Error: null character");
            return false;
        }

        if (!(character instanceof Gaulois || character instanceof Roman)) {
            System.out.println("❌ " + character.getName() + " cannot enter this Gallo-Roman town");
            System.out.println("   (Only Gauls and Romans are allowed)");
            return false;
        }

        if (canAddCharacter(character)) {
            characters.add(character);
            System.out.println("🏘️ " + character.getName() + " enters the town " + name);

            if (character.getBelligerence() > 70) {
                decreasePeacefulness(5);
            }

            return true;
        }

        return false;
    }

    /**
     * Affiche les informations de la bourgade et ses habitants.
     */
    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("🏘️ GALLO-ROMAN TOWN: " + name);
        System.out.println("Area: " + area + " m²");

        if (chief != null) {
            System.out.println("Chief: " + chief.getName());
        } else {
            System.out.println("Chief: None");
        }

        System.out.println("\n📊 Indicators:");
        System.out.println("  Peacefulness: " + getPeacefulnessBar() + " (" + peacefulnessLevel + "%)");
        System.out.println("  Commerce: " + getCommerceBar() + " (" + commerceLevel + "%)");

        System.out.println("\n👥 Population: " + characters.size());
        System.out.println("  - Gauls: " + countGaulois());
        System.out.println("  - Romans: " + countRomans());

        if (!characters.isEmpty()) {
            System.out.println("\nResidents:");
            for (GameCharacter c : characters) {
                String status = c.isDead() ? " [DEAD]" : " [Health: " + c.getHealth() + "]";
                String origin = (c instanceof Gaulois) ? " [Gaul]" : " [Roman]";
                System.out.println("  • " + c.toString() + origin + status);
            }
        } else {
            System.out.println("  (Town deserted)");
        }

        System.out.println("\nAvailable food: " + foods.size());
        if (!foods.isEmpty()) {
            for (Food f : foods) {
                System.out.println("  • " + f.toString());
            }
        } else {
            System.out.println("  (No provisions)");
        }

        System.out.println("\nGeneral status: " + getTownStatus());

        if (isTenseAtmosphere()) {
            System.out.println("⚠️ Tense atmosphere - Risk of conflict!");
        }

        System.out.println("========================================\n");
    }

    /** @return nombre de Gaulois vivants */
    private int countGaulois() {
        int count = 0;
        for (GameCharacter c : characters) {
            if (c instanceof Gaulois && !c.isDead()) count++;
        }
        return count;
    }

    /** @return nombre de Romains vivants */
    private int countRomans() {
        int count = 0;
        for (GameCharacter c : characters) {
            if (c instanceof Roman && !c.isDead()) count++;
        }
        return count;
    }

    /** @return liste des Gaulois */
    public List<GameCharacter> getGaulois() {
        List<GameCharacter> gaulois = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c instanceof Gaulois) gaulois.add(c);
        }
        return gaulois;
    }

    /** @return liste des Romains */
    public List<GameCharacter> getRomans() {
        List<GameCharacter> romans = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c instanceof Roman) romans.add(c);
        }
        return romans;
    }

    /** @return true si l'atmosphère est tendue */
    public boolean isTenseAtmosphere() {
        if (peacefulnessLevel < 30) return true;

        int belligerentCount = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead() && c.getBelligerence() > 60) belligerentCount++;
        }

        if (belligerentCount > characters.size() * 0.5) return true;

        int gauloisCount = countGaulois();
        int romanCount = countRomans();
        if (gauloisCount > 0 && romanCount > 0) {
            float ratio = Math.max(gauloisCount, romanCount) / (float) Math.min(gauloisCount, romanCount);
            return ratio > 3.0;
        }

        return false;
    }

    /** Favorise le commerce et ajuste la paix */
    public void promoteCommerce() {
        System.out.println("\n💰 Promoting commerce in " + name);

        if (characters.size() < 2) {
            System.out.println("  Not enough residents to trade");
            return;
        }

        commerceLevel = Math.min(100, commerceLevel + 10);

        int foodPerPerson = foods.size() / characters.size();
        if (foodPerPerson > 0) increasePeacefulness(5);

        System.out.println("  Commerce level: " + commerceLevel + "%");
    }

    /** Organise une fête pour améliorer la cohésion */
    public void organizeFeast() {
        System.out.println("\n🎉 Organizing feast in " + name);

        if (foods.size() < characters.size()) {
            System.out.println("  ⚠️ Not enough food for everyone");
            decreasePeacefulness(10);
            return;
        }

        feedAll();

        for (GameCharacter c : characters) {
            if (!c.isDead()) c.setBelligerence(Math.max(0, c.getBelligerence() - 15));
        }

        increasePeacefulness(20);
        System.out.println("  Feast improves relations between Gauls and Romans!");
    }

    /** Gère les conflits potentiels dans la bourgade */
    public void manageConflicts() {
        System.out.println("\n⚖️ Managing conflicts in " + name);

        List<GameCharacter> troublemakers = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (!c.isDead() && c.getBelligerence() > 75) troublemakers.add(c);
        }

        if (troublemakers.isEmpty()) {
            System.out.println("  No conflicts detected");
            increasePeacefulness(5);
        } else {
            System.out.println("  " + troublemakers.size() + " troublemaker(s) identified");
            for (GameCharacter t : troublemakers) {
                t.setBelligerence(t.getBelligerence() - 25);
            }
            decreasePeacefulness(5);
        }
    }

    /** Augmente le niveau de paix */
    public void increasePeacefulness(int amount) {
        peacefulnessLevel = Math.min(100, peacefulnessLevel + amount);
    }

    /** Diminue le niveau de paix */
    public void decreasePeacefulness(int amount) {
        peacefulnessLevel = Math.max(0, peacefulnessLevel - amount);
    }

    /** Augmente le niveau de commerce */
    public void increaseCommerce(int amount) {
        commerceLevel = Math.min(100, commerceLevel + amount);
    }

    /** Diminue le niveau de commerce */
    public void decreaseCommerce(int amount) {
        commerceLevel = Math.max(0, commerceLevel - amount);
    }

    /** Barre visuelle représentant la paix */
    private String getPeacefulnessBar() {
        int bars = peacefulnessLevel / 10;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 10; i++) bar.append(i < bars ? "█" : "░");
        bar.append("]");
        return bar.toString();
    }

    /** Barre visuelle représentant le commerce */
    private String getCommerceBar() {
        int bars = commerceLevel / 10;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 10; i++) bar.append(i < bars ? "█" : "░");
        bar.append("]");
        return bar.toString();
    }

    /** État général de la bourgade */
    private String getTownStatus() {
        int avgLevel = (peacefulnessLevel + commerceLevel) / 2;

        if (avgLevel >= 80) return "🟢 Prosperous and peaceful";
        if (avgLevel >= 60) return "🟡 Stable";
        if (avgLevel >= 40) return "🟠 Moderate tensions";
        return "🔴 Frequent conflicts";
    }

    // Getters et setters
    public int getPeacefulnessLevel() { return peacefulnessLevel; }
    public void setPeacefulnessLevel(int peacefulnessLevel) {
        this.peacefulnessLevel = Math.max(0, Math.min(100, peacefulnessLevel));
    }

    public int getCommerceLevel() { return commerceLevel; }
    public void setCommerceLevel(int commerceLevel) {
        this.commerceLevel = Math.max(0, Math.min(100, commerceLevel));
    }
}
