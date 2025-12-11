package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;
import org.example.envahissementarmorique.model.item.Food;



/**
 * Représente un enclos destiné aux créatures fantastiques.
 *
 * <p>
 * Un enclos ne peut contenir que des créatures fantastiques et n'a pas de chef.
 * La capacité maximale est limitée en fonction de la superficie ou d'une valeur donnée.
 * </p>
 */
public final class Enclosure extends Place {

    /** Capacité maximale de créatures dans l'enclos */
    private int maxCapacity;

    /**
     * Constructeur avec calcul automatique de la capacité maximale.
     *
     * @param name nom de l'enclos
     * @param area superficie en m²
     */
    public Enclosure(String name, float area) {
        super(name, area, null);
        this.maxCapacity = (int) (area / 10); // 1 créature pour 10m²
    }

    /**
     * Constructeur avec capacité maximale définie manuellement.
     *
     * @param name nom de l'enclos
     * @param area superficie en m²
     * @param maxCapacity capacité maximale d'accueil
     */
    public Enclosure(String name, float area, int maxCapacity) {
        super(name, area, null);
        this.maxCapacity = maxCapacity;
    }

    /**
     * Vérifie si un personnage peut entrer dans l'enclos.
     *
     * @param character personnage à tester
     * @return true si le personnage est une créature fantastique et que l'enclos n'est pas plein
     */
    @Override
    protected boolean canAddCharacter(GameCharacter character) {
        if (!(character instanceof FantasticCreature)) return false;
        if (characters.size() >= maxCapacity) {
            System.out.println("Enclosure full! Maximum capacity reached (" + maxCapacity + ")");
            return false;
        }
        return true;
    }

    /**
     * Ajoute un personnage dans l'enclos si possible.
     *
     * @param character personnage à ajouter
     * @return true si l'ajout a réussi
     */
    @Override
    public boolean addCharacter(GameCharacter character) {
        if (character == null) {
            System.out.println("Error: null creature");
            return false;
        }

        if (!(character instanceof FantasticCreature)) {
            System.out.println(character.getName() + " is not a fantastic creature");
            return false;
        }

        if (characters.size() >= maxCapacity) {
            System.out.println("Enclosure " + name + " is full (" + characters.size() + "/" + maxCapacity + ")");
            return false;
        }

        if (canAddCharacter(character)) {
            characters.add(character);
            System.out.println(character.getName() + " enters the enclosure " + name);
            return true;
        }

        return false;
    }

    /**
     * Affiche les informations de l'enclos et l'état des créatures.
     */
    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("ENCLOSURE: " + name);
        System.out.println("Area: " + area + " m²");
        System.out.println("Capacity: " + characters.size() + "/" + maxCapacity + " creatures");
        System.out.println("Chief: None (monitored enclosure)");

        float occupancyRate = (characters.size() * 100.0f) / maxCapacity;
        System.out.println("Occupancy rate: " + String.format("%.1f", occupancyRate) + "%");

        System.out.println("\nCreatures present: " + characters.size());
        if (!characters.isEmpty()) {
            for (GameCharacter c : characters) {
                String status = c.isDead() ? " [DEAD]" : " [Health: " + c.getHealth() + "]";
                String hunger = " [Hunger: " + c.getHunger() + "]";
                System.out.println("  • " + c.toString() + status + hunger);
            }
        } else {
            System.out.println("  (Empty enclosure)");
        }

        System.out.println("\nAvailable food: " + foods.size());
        if (!foods.isEmpty()) {
            System.out.println("Food:");
            for (Food f : foods) {
                System.out.println("  • " + f.toString());
            }
        } else {
            System.out.println("  (No food)");
        }

        if (needsAttention()) {
            System.out.println("\nWARNING: This enclosure needs attention!");
        }

        System.out.println("========================================\n");
    }

    /**
     * Vérifie si l'enclos nécessite une attention particulière (soins, surpopulation, faim).
     *
     * @return true si une créature est en danger ou si l'enclos est presque plein
     */
    public boolean needsAttention() {
        for (GameCharacter c : characters) {
            if (c.isDead() || c.getHunger() > 70 || c.getHealth() < 30) return true;
        }
        return characters.size() > maxCapacity * 0.9;
    }

    /** @return l'espace disponible restant dans l'enclos */
    public int getAvailableSpace() {
        return maxCapacity - characters.size();
    }

    /** @return true si l'enclos est plein */
    public boolean isFull() {
        return characters.size() >= maxCapacity;
    }

    /** @return true si l'enclos est vide */
    public boolean isEmpty() {
        return characters.isEmpty();
    }

    /**
     * Nourrit toutes les créatures de l'enclos.
     */
    @Override
    public void feedAll() {
        System.out.println("\nFeeding creatures in enclosure " + name);

        if (foods.isEmpty()) {
            System.out.println("No food available!");
            return;
        }

        int fed = 0;
        for (GameCharacter c : characters) {
            if (c.isDead()) continue;
            if (c.getHunger() < 100) {
                Food food = findSuitableFoodForCreature(c);
                if (food != null) {
                    c.ToEat(food);
                    foods.remove(food);
                    fed++;
                }
            }
        }

        if (fed == 0) {
            System.out.println("No creature needed food");
        } else {
            System.out.println(fed + " creature(s) fed");
        }
    }

    /**
     * Soigne toutes les créatures de l'enclos.
     *
     * @param amount quantité de soin à appliquer
     */
    @Override
    public void healAll(int amount) {
        System.out.println("\nHealing creatures in enclosure " + name);

        int healed = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead() && c.getHealth() < 100) {
                c.ToHeal(amount);
                healed++;
                System.out.println("  - " + c.getName() + " has been healed");
            }
        }

        if (healed == 0) {
            System.out.println("No creature needed healing");
        } else {
            System.out.println(healed + " creature(s) healed");
        }
    }

    /**
     * Calme les créatures agressives de l'enclos.
     */
    public void calmCreatures() {
        System.out.println("\nCalming creatures in enclosure " + name);

        int calmed = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead() && c.getBelligerence() > 50) {
                c.setBelligerence(c.getBelligerence() - 20);
                calmed++;
                System.out.println("  - " + c.getName() + " calmed down");
            }
        }

        if (calmed == 0) {
            System.out.println("All creatures are already calm");
        } else {
            System.out.println(calmed + " creature(s) calmed");
        }
    }

    /** @return la capacité maximale de l'enclos */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /** Définit la capacité maximale de l'enclos */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /** Renvoie la première nourriture disponible pour une créature */
    private Food findSuitableFoodForCreature(GameCharacter c) {
        if (!foods.isEmpty()) return foods.get(0);
        return null;
    }
}
