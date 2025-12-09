package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.Gaulois;
import org.example.envahissementarmorique.model.character.base.Druid;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;
import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.item.Potion;
import java.util.ArrayList;
import java.util.List;

/**
 * Village gaulois - ne peut contenir que des Gaulois et des créatures fantastiques
 * Bastion de la résistance gauloise contre l'envahisseur romain
 */
public class GaulishVillage extends Place {

    private int resistanceLevel;      // Niveau de résistance (0-100)
    private int moraleLevel;          // Moral des habitants (0-100)
    private List<Potion> potions; // Stock de potions magiques

    public GaulishVillage(String name, float area, ClanLeader chief) {
        super(name, area, chief);
        this.resistanceLevel = 100; // Résistance maximale par défaut
        this.moraleLevel = 90;      // Moral élevé par défaut
        this.potions = new ArrayList<>();
    }

    public GaulishVillage(String name, float area, ClanLeader chief, int resistanceLevel, int moraleLevel) {
        super(name, area, chief);
        this.resistanceLevel = Math.max(0, Math.min(100, resistanceLevel));
        this.moraleLevel = Math.max(0, Math.min(100, moraleLevel));
        this.potions = new ArrayList<>();
    }

    @Override
    protected boolean canAddCharacter(GameCharacter c) {
        // Accepte uniquement les Gaulois et les créatures fantastiques
        return (c instanceof Gaulois || c instanceof FantasticCreature);
    }

    @Override
    public boolean addCharacter(GameCharacter c) {
        if (c == null) {
            System.out.println("Erreur : personnage null");
            return false;
        }

        if (!(c instanceof Gaulois || c instanceof FantasticCreature)) {
            System.out.println("❌ " + c.getName() + " ne peut pas entrer dans ce village gaulois !");
            System.out.println("   (Seuls les Gaulois et créatures fantastiques sont acceptés)");
            return false;
        }

        if (canAddCharacter(c)) {
            characters.add(c);
            System.out.println("🛡️ " + c.getName() + " entre dans le village " + name);

            // Améliorer le moral si un druide arrive
            if (c instanceof Druid) {
                System.out.println("   ✨ L'arrivée d'un druide remonte le moral du village !");
                increaseMorale(10);
            }

            return true;
        }

        return false;
    }

    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("🛡️  VILLAGE GAULOIS : " + name);
        System.out.println("Superficie : " + area + " m²");

        if (chief != null) {
            System.out.println("Chef : " + chief.getName() + " (irréductible)");
        } else {
            System.out.println("Chef : Aucun");
        }

        // Indicateurs du village
        System.out.println("\n⚔️ Indicateurs :");
        System.out.println("  Résistance : " + getResistanceBar() + " (" + resistanceLevel + "%)");
        System.out.println("  Moral : " + getMoraleBar() + " (" + moraleLevel + "%)");
        System.out.println("  Potions magiques : " + potions.size() + " dose(s)");

        // Composition de la population
        int gauloisCount = countGaulois();
        int druidsCount = countDruids();
        int creaturesCount = countCreatures();

        System.out.println("\n👥 Population : " + characters.size() + " habitant(s)");
        System.out.println("  - Gaulois : " + gauloisCount);
        System.out.println("  - Druides : " + druidsCount);
        System.out.println("  - Créatures : " + creaturesCount);

        if (!characters.isEmpty()) {
            System.out.println("\nHabitants présents :");
            for (GameCharacter c : characters) {
                String status = c.isDead() ? " [MORT]" : " [Santé: " + c.getHealth() + ", Potion: " + c.getMagicpotion() + "]";
                String type = getCharacterType(c);
                System.out.println("  • " + c.getName() + type + status);
            }
        } else {
            System.out.println("  (Village déserté)");
        }

        System.out.println("\nNourriture disponible : " + foods.size());
        if (!foods.isEmpty()) {
            System.out.println("Provisions :");
            for (Food f : foods) {
                System.out.println("  • " + f.toString());
            }
        } else {
            System.out.println("  (Garde-manger vide)");
        }

        System.out.println("\nÉtat du village : " + getVillageStatus());

        if (isUnderThreat()) {
            System.out.println("⚠️ ALERTE : Le village est menacé !");
        }

        System.out.println("========================================\n");
    }

    /**
     * Obtient le type de personnage pour l'affichage
     */
    private String getCharacterType(GameCharacter c) {
        if (c instanceof Druid) {
            return " [Druide]";
        } else if (c instanceof FantasticCreature) {
            return " [Créature]";
        } else if (c instanceof Gaulois) {
            String className = c.getClass().getSimpleName();
            return " [" + className + "]";
        }
        return "";
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
     * Compte le nombre de Druides
     */
    private int countDruids() {
        int count = 0;
        for (GameCharacter c : characters) {
            if (c instanceof Druid && !c.isDead()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Compte le nombre de créatures
     */
    private int countCreatures() {
        int count = 0;
        for (GameCharacter c : characters) {
            if (c instanceof FantasticCreature && !c.isDead()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Obtient tous les Gaulois du village
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
     * Obtient tous les Druides du village
     */
    public List<GameCharacter> getDruids() {
        List<GameCharacter> druids = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (c instanceof Druid) {
                druids.add(c);
            }
        }
        return druids;
    }

    /**
     * Vérifie si le village a un druide
     */
    public boolean hasDruid() {
        return countDruids() > 0;
    }

    /**
     * Vérifie si le village est menacé
     */
    public boolean isUnderThreat() {
        // Village menacé si :
        // - Résistance faible
        // - Moral bas
        // - Peu de combattants
        // - Pas de potion magique

        if (resistanceLevel < 30 || moraleLevel < 30) {
            return true;
        }

        int fighters = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead() && c.getStrength() > 30) {
                fighters++;
            }
        }

        if (fighters < 3 && potions.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * Ajoute une potion magique au stock
     */
    public void addPotion(Potion potion) {
        potions.add(potion);
        System.out.println("✨ Une potion magique a été ajoutée au stock du village !");
        increaseMorale(5);
        increaseResistance(5);
    }

    /**
     * Distribue des potions aux habitants
     */
    public void distributePotions(int numberOfDoses) {
        if (potions.isEmpty()) {
            System.out.println("❌ Aucune potion disponible dans le village !");
            return;
        }

        System.out.println("\n✨ Distribution de potion magique à " + name);

        int distributed = 0;
        for (GameCharacter c : characters) {
            if (distributed >= numberOfDoses) break;

            if (!c.isDead() && c instanceof Gaulois && c.getMagicpotion() < 100) {
                if (!potions.isEmpty()) {
                    Potion potion = potions.remove(0);
                    c.ToDrinkPotion(potion);
                    distributed++;
                    System.out.println("  - " + c.getName() + " boit une dose de potion magique");
                }
            }
        }

        if (distributed == 0) {
            System.out.println("  Personne n'a eu besoin de potion");
        } else {
            System.out.println("  " + distributed + " dose(s) distribuée(s)");
            increaseMorale(10);
        }
    }

    /**
     * Organise un banquet avec sangliers
     */
    public void organizeBanquet() {
        System.out.println("\n🍖 Organisation d'un banquet au village " + name + " !");

        if (foods.size() < characters.size()) {
            System.out.println("  ⚠️ Pas assez de nourriture pour tout le monde...");
            decreaseMorale(10);
            return;
        }

        // Nourrir tout le monde
        feedAll();

        // Améliorer le moral
        increaseMorale(20);
        increaseResistance(10);

        System.out.println("  Le banquet remonte le moral des troupes !");
        System.out.println("  Résistance : +" + 10 + "% | Moral : +" + 20 + "%");
    }

    /**
     * Prépare le village pour la défense
     */
    public void prepareDefense() {
        System.out.println("\n🛡️ Préparation de la défense du village " + name);

        int fighters = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead() && c instanceof Gaulois) {
                c.setBelligerence(Math.min(100, c.getBelligerence() + 20));
                fighters++;
                System.out.println("  - " + c.getName() + " se prépare au combat");
            }
        }

        if (fighters == 0) {
            System.out.println("  ⚠️ Aucun combattant disponible !");
            decreaseResistance(20);
        } else {
            System.out.println("  " + fighters + " guerrier(s) prêt(s) à défendre le village !");
            increaseResistance(15);
        }
    }

    /**
     * Organise une assemblée du village
     */
    public void organizeAssembly() {
        System.out.println("\n🗣️ Assemblée du village " + name);

        if (characters.size() < 3) {
            System.out.println("  Pas assez d'habitants pour tenir une assemblée");
            return;
        }

        System.out.println("  Les habitants se rassemblent pour discuter de la résistance");

        // Améliorer le moral et la cohésion
        increaseMorale(15);

        // Les druides motivent les troupes
        if (hasDruid()) {
            System.out.println("  Les druides encouragent la résistance contre Rome !");
            increaseResistance(10);
        }

        System.out.println("  L'assemblée renforce l'unité du village");
    }

    /**
     * Demande au druide de concocter une potion
     */
    public void requestMagicPotion() {
        System.out.println("\n🧪 Demande de confection de potion magique...");

        List<GameCharacter> druids = getDruids();
        if (druids.isEmpty()) {
            System.out.println("  ❌ Aucun druide disponible dans le village !");
            return;
        }

        // TODO: Implement concoctPotion() method in Druid class
        // Druid druid = (Druid) druids.get(0);
        // Potion potion = druid.concoctPotion();

        // For now, create a potion manually
        Potion potion = new Potion(org.example.envahissementarmorique.model.item.Foods.SECRET_INGREDIENT, 10);

        if (potion != null) {
            addPotion(potion);
            System.out.println("  ✅ Potion magique concoctée avec succès !");
        } else {
            System.out.println("  ❌ Échec de la confection de la potion");
        }
    }

    /**
     * Augmente le niveau de résistance
     */
    public void increaseResistance(int amount) {
        resistanceLevel = Math.min(100, resistanceLevel + amount);
    }

    /**
     * Diminue le niveau de résistance
     */
    public void decreaseResistance(int amount) {
        resistanceLevel = Math.max(0, resistanceLevel - amount);
    }

    /**
     * Augmente le moral
     */
    public void increaseMorale(int amount) {
        moraleLevel = Math.min(100, moraleLevel + amount);
    }

    /**
     * Diminue le moral
     */
    public void decreaseMorale(int amount) {
        moraleLevel = Math.max(0, moraleLevel - amount);
    }

    /**
     * Barre visuelle pour la résistance
     */
    private String getResistanceBar() {
        int bars = resistanceLevel / 10;
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
     * Barre visuelle pour le moral
     */
    private String getMoraleBar() {
        int bars = moraleLevel / 10;
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
     * Détermine l'état du village
     */
    private String getVillageStatus() {
        int avgLevel = (resistanceLevel + moraleLevel) / 2;

        if (avgLevel >= 80) {
            return "🟢 Irréductible - Résiste encore et toujours !";
        } else if (avgLevel >= 60) {
            return "🟡 Combatif - Continue la résistance";
        } else if (avgLevel >= 40) {
            return "🟠 Affaibli - Défense compromise";
        } else {
            return "🔴 Critique - Village en danger !";
        }
    }

    // Getters et setters
    public int getResistanceLevel() {
        return resistanceLevel;
    }

    public void setResistanceLevel(int resistanceLevel) {
        this.resistanceLevel = Math.max(0, Math.min(100, resistanceLevel));
    }

    public int getMoraleLevel() {
        return moraleLevel;
    }

    public void setMoraleLevel(int moraleLevel) {
        this.moraleLevel = Math.max(0, Math.min(100, moraleLevel));
    }

    public List<Potion> getPotions() {
        return potions;
    }

    public int getPotionCount() {
        return potions.size();
    }
}