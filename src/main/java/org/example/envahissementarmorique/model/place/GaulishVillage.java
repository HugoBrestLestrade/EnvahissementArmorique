package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.Gaulish.Gaulois;
import org.example.envahissementarmorique.model.character.base.Gaulish.Druid;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;
import org.example.envahissementarmorique.model.character.base.ClanLeader;
import org.example.envahissementarmorique.model.item.Food;
import org.example.envahissementarmorique.model.item.Potion;

import java.util.ArrayList;
import java.util.List;

/**
 * Village gaulois.
 *
 * <p>Ne peut contenir que des Gaulois et des créatures fantastiques.
 * Bastion de la résistance gauloise contre l'envahisseur romain.</p>
 */
public final class GaulishVillage extends Place {

    /** Niveau de résistance du village (0-100) */
    private int resistanceLevel;

    /** Moral des habitants (0-100) */
    private int moraleLevel;

    /** Stock de potions magiques */
    private List<Potion> potions;

    public GaulishVillage(String name, float area, ClanLeader chief) {
        super(name, area, chief);
        this.resistanceLevel = 100;
        this.moraleLevel = 90;
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
        return (c instanceof Gaulois || c instanceof FantasticCreature);
    }

    @Override
    public boolean addCharacter(GameCharacter c) {
        if (c == null) {
            System.out.println("Error: null character");
            return false;
        }

        if (!(c instanceof Gaulois || c instanceof FantasticCreature)) {
            System.out.println("❌ " + c.getName() + " cannot enter this Gaulish village");
            return false;
        }

        if (canAddCharacter(c)) {
            characters.add(c);
            System.out.println("🛡️ " + c.getName() + " enters the village " + name);

            if (c instanceof Druid) {
                System.out.println("   ✨ A druid boosts village morale!");
                increaseMorale(10);
            }

            return true;
        }
        return false;
    }

    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("🛡️ GAULISH VILLAGE: " + name);
        System.out.println("Area: " + area + " m²");

        System.out.println("Chief: " + (chief != null ? chief.getName() : "None"));

        System.out.println("\n⚔️ Indicators:");
        System.out.println("  Resistance: " + getResistanceBar() + " (" + resistanceLevel + "%)");
        System.out.println("  Morale: " + getMoraleBar() + " (" + moraleLevel + "%)");
        System.out.println("  Magic potions: " + potions.size());

        System.out.println("\n👥 Population: " + characters.size());
        System.out.println("  - Gauls: " + countGaulois());
        System.out.println("  - Druids: " + countDruids());
        System.out.println("  - Creatures: " + countCreatures());

        if (!characters.isEmpty()) {
            System.out.println("\nResidents:");
            for (GameCharacter c : characters) {
                String status = c.isDead() ? " [DEAD]" : " [Health: " + c.getHealth() + ", Potion: " + c.getMagicpotion() + "]";
                System.out.println("  • " + c.getName() + getCharacterType(c) + status);
            }
        } else {
            System.out.println("  (Village deserted)");
        }

        System.out.println("\nFood available: " + foods.size());
        if (!foods.isEmpty()) for (Food f : foods) System.out.println("  • " + f);

        System.out.println("\nVillage status: " + getVillageStatus());
        if (isUnderThreat()) System.out.println("⚠️ ALERT: Village under threat!");
        System.out.println("========================================\n");
    }

    private String getCharacterType(GameCharacter c) {
        if (c instanceof Druid) return " [Druid]";
        if (c instanceof FantasticCreature) return " [Creature]";
        if (c instanceof Gaulois) return " [Gaul]";
        return "";
    }

    private int countGaulois() { return (int) characters.stream().filter(c -> c instanceof Gaulois && !c.isDead()).count(); }
    private int countDruids() { return (int) characters.stream().filter(c -> c instanceof Druid && !c.isDead()).count(); }
    private int countCreatures() { return (int) characters.stream().filter(c -> c instanceof FantasticCreature && !c.isDead()).count(); }

    public List<GameCharacter> getGaulois() {
        List<GameCharacter> list = new ArrayList<>();
        for (GameCharacter c : characters) if (c instanceof Gaulois) list.add(c);
        return list;
    }

    public List<GameCharacter> getDruids() {
        List<GameCharacter> list = new ArrayList<>();
        for (GameCharacter c : characters) if (c instanceof Druid) list.add(c);
        return list;
    }

    public boolean hasDruid() { return countDruids() > 0; }

    public boolean isUnderThreat() {
        if (resistanceLevel < 30 || moraleLevel < 30) return true;
        int fighters = (int) characters.stream().filter(c -> !c.isDead() && c.getStrength() > 30).count();
        return fighters < 3 && potions.isEmpty();
    }

    public void addPotion(Potion potion) {
        potions.add(potion);
        increaseMorale(5);
        increaseResistance(5);
        System.out.println("✨ Magic potion added to village stock!");
    }

    public void distributePotions(int doses) {
        if (potions.isEmpty()) { System.out.println("❌ No potion available!"); return; }
        int distributed = 0;
        for (GameCharacter c : characters) {
            if (distributed >= doses) break;
            if (!c.isDead() && c instanceof Gaulois && c.getMagicpotion() < 100) {
                c.ToDrinkPotion(potions.remove(0));
                distributed++;
            }
        }
        if (distributed > 0) increaseMorale(10);
        System.out.println("Distributed " + distributed + " potion dose(s)");
    }

    public void organizeBanquet() { increaseMorale(20); increaseResistance(10); feedAll(); }
    public void prepareDefense() { increaseResistance(15); characters.forEach(c -> { if (!c.isDead() && c instanceof Gaulois) c.setBelligerence(Math.min(100, c.getBelligerence()+20)); }); }
    public void organizeAssembly() { increaseMorale(15); if (hasDruid()) increaseResistance(10); }
    public void requestMagicPotion() { addPotion(new Potion(org.example.envahissementarmorique.model.item.Foods.SECRET_INGREDIENT,10)); }

    public void increaseResistance(int amount) { resistanceLevel = Math.min(100, resistanceLevel + amount); }
    public void decreaseResistance(int amount) { resistanceLevel = Math.max(0, resistanceLevel - amount); }
    public void increaseMorale(int amount) { moraleLevel = Math.min(100, moraleLevel + amount); }
    public void decreaseMorale(int amount) { moraleLevel = Math.max(0, moraleLevel - amount); }

    private String getResistanceBar() { return getBar(resistanceLevel); }
    private String getMoraleBar() { return getBar(moraleLevel); }
    private String getBar(int value) { StringBuilder bar = new StringBuilder("["); for(int i=0;i<10;i++) bar.append(i<value/10?"█":"░"); bar.append("]"); return bar.toString(); }

    private String getVillageStatus() {
        int avg = (resistanceLevel+moraleLevel)/2;
        if (avg >= 80) return "🟢 Unbreakable - still resisting!";
        if (avg >= 60) return "🟡 Fighting - holding ground";
        if (avg >= 40) return "🟠 Weakened - defense compromised";
        return "🔴 Critical - village in danger!";
    }

    // Getters & setters
    public int getResistanceLevel() { return resistanceLevel; }
    public void setResistanceLevel(int resistanceLevel) { this.resistanceLevel = Math.max(0, Math.min(100, resistanceLevel)); }
    public int getMoraleLevel() { return moraleLevel; }
    public void setMoraleLevel(int moraleLevel) { this.moraleLevel = Math.max(0, Math.min(100, moraleLevel)); }
    public List<Potion> getPotions() { return potions; }
    public int getPotionCount() { return potions.size(); }
}
