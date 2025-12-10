package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.character.base.FantasticCreature;
import org.example.envahissementarmorique.model.item.Food;

/**

 * Enclos - ne peut contenir que des créatures fantastiques
 * N'a pas de chef de clan
 */
public final class Enclosure extends Place {

    private int maxCapacity; // Capacité maximale de créatures

    public Enclosure(String name, float area) {
        super(name, area, null); // Pas de chef pour un enclos
        this.maxCapacity = (int) (area / 10); // 1 créature pour 10m²
    }

    public Enclosure(String name, float area, int maxCapacity) {
        super(name, area, null);
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean canAddCharacter(GameCharacter c) {
        if (!(c instanceof FantasticCreature)) {
            return false;
        }

        if (characters.size() >= maxCapacity) {
            System.out.println("Enclos plein ! Capacité maximale atteinte (" + maxCapacity + ")");
            return false;
        }

        return true;


    }

    @Override
    public boolean addCharacter(GameCharacter c) {
        if (c == null) {
            System.out.println("Erreur : créature null");
            return false;
        }


        if (!(c instanceof FantasticCreature)) {
            System.out.println(c.getName() + " n'est pas une créature fantastique");
            return false;
        }

        if (characters.size() >= maxCapacity) {
            System.out.println("Enclos " + name + " est plein (" + characters.size() + "/" + maxCapacity + ")");
            return false;
        }

        if (canAddCharacter(c)) {
            characters.add(c);
            System.out.println(c.getName() + " entre dans l'enclos " + name);
            return true;
        }

        return false;

    }

    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("ENCLOS : " + name);
        System.out.println("Superficie : " + area + " m²");
        System.out.println("Capacité : " + characters.size() + "/" + maxCapacity + " créatures");
        System.out.println("Chef : Aucun (enclos surveillé)");

        float occupancyRate = (characters.size() * 100.0f) / maxCapacity;
        System.out.println("Taux d'occupation : " + String.format("%.1f", occupancyRate) + "%");

        System.out.println("\nCréatures présentes : " + characters.size());
        if (!characters.isEmpty()) {
            for (GameCharacter c : characters) {
                String status = c.isDead() ? " [MORT]" : " [Santé: " + c.getHealth() + "]";
                String hunger = " [Faim: " + c.getHunger() + "]";
                System.out.println("  • " + c.toString() + status + hunger);
            }
        } else {
            System.out.println("  (Enclos vide)");
        }

        System.out.println("\nNourriture disponible : " + foods.size());
        if (!foods.isEmpty()) {
            System.out.println("Aliments :");
            for (Food f : foods) {
                System.out.println("  • " + f.toString());
            }
        } else {
            System.out.println("  (Pas de nourriture)");
        }

        if (needsAttention()) {
            System.out.println("\nATTENTION : Cet enclos nécessite des soins !");
        }

        System.out.println("========================================\n");


    }

    public boolean needsAttention() {
        for (GameCharacter c : characters) {
            if (c.isDead() || c.getHunger() > 70 || c.getHealth() < 30) {
                return true;
            }
        }
        return characters.size() > maxCapacity * 0.9;
    }

    public int getAvailableSpace() {
        return maxCapacity - characters.size();
    }

    public boolean isFull() {
        return characters.size() >= maxCapacity;
    }

    public boolean isEmpty() {
        return characters.isEmpty();
    }

    @Override
    public void feedAll() {
        System.out.println("\nNourrissage des créatures dans l'enclos " + name);


        if (foods.isEmpty()) {
            System.out.println("Pas de nourriture disponible !");
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
            System.out.println("Aucune créature n'avait faim");
        } else {
            System.out.println(fed + " créature(s) nourrie(s)");
        }


    }

    @Override
    public void healAll(int amount) {
        System.out.println("\nSoins des créatures dans l'enclos " + name);


        int healed = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead() && c.getHealth() < 100) {
                c.ToHeal(amount);
                healed++;
                System.out.println("  - " + c.getName() + " a été soigné");
            }
        }

        if (healed == 0) {
            System.out.println("Aucune créature n'avait besoin de soins");
        } else {
            System.out.println(healed + " créature(s) soignée(s)");
        }


    }

    public void calmCreatures() {
        System.out.println("\nApaisement des créatures dans l'enclos " + name);


        int calmed = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead() && c.getBelligerence() > 50) {
                c.setBelligerence(c.getBelligerence() - 20);
                calmed++;
                System.out.println("  - " + c.getName() + " s'est calmé");
            }
        }

        if (calmed == 0) {
            System.out.println("Les créatures sont déjà calmes");
        } else {
            System.out.println(calmed + " créature(s) apaisée(s)");
        }

    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    private Food findSuitableFoodForCreature(GameCharacter c) {
        // Return the first available food for creatures
        if (!foods.isEmpty()) {
            return foods.get(0);
        }
        return null;
    }
}
