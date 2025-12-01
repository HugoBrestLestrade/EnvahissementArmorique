package org.example.envahissementarmorique.model.place;


/**
 * Enclos - ne peut contenir que des créatures fantastiques
 * N'a pas de chef de clan
 */
public class Enclosure extends Place {

    private int maxCapacity; // Capacité maximale de créatures

    public Enclosure(String name, float area) {
        super(name, area, null); // Pas de chef pour un enclos
        this.maxCapacity = (int)(area / 10); // 1 créature pour 10m²
    }

    public Enclosure(String name, float area, int maxCapacity) {
        super(name, area, null);
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean canAddCharacter(Character c) {
        // Accepte uniquement les créatures fantastiques
        if (!(c instanceof FantasticCreature)) {
            return false;
        }

        // Vérifie la capacité maximale
        if (characters.size() >= maxCapacity) {
            System.out.println("⚠️ Enclos plein ! Capacité maximale atteinte (" + maxCapacity + ")");
            return false;
        }

        return true;
    }

    @Override
    public boolean addCharacter(Character c) {
        if (c == null) {
            System.out.println("Erreur : créature null");
            return false;
        }

        if (!(c instanceof FantasticCreature)) {
            System.out.println("❌ " + c.getName() + " n'est pas une créature fantastique");
            return false;
        }

        if (characters.size() >= maxCapacity) {
            System.out.println("❌ Enclos " + name + " est plein (" + characters.size() + "/" + maxCapacity + ")");
            return false;
        }

        if (canAddCharacter(c)) {
            characters.add(c);
            System.out.println("🐺 " + c.getName() + " entre dans l'enclos " + name);
            return true;
        }

        return false;
    }

    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("🐺 ENCLOS : " + name);
        System.out.println("Superficie : " + area + " m²");
        System.out.println("Capacité : " + characters.size() + "/" + maxCapacity + " créatures");
        System.out.println("Chef : Aucun (enclos surveillé)");

        float occupancyRate = (characters.size() * 100.0f) / maxCapacity;
        System.out.println("Taux d'occupation : " + String.format("%.1f", occupancyRate) + "%");

        System.out.println("\nCréatures présentes : " + characters.size());
        if (!characters.isEmpty()) {
            for (Character c : characters) {
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
            System.out.println("\n⚠️ ATTENTION : Cet enclos nécessite des soins !");
        }

        System.out.println("========================================\n");
    }

    /**
     * Vérifie si l'enclos nécessite une attention particulière
     */
    public boolean needsAttention() {
        // Vérifier si des créatures ont faim, sont blessées ou sont mortes
        for (Character c : characters) {
            if (c.isDead() || c.getHunger() > 70 || c.getHealth() < 30) {
                return true;
            }
        }

        // Vérifier si l'enclos est surpeuplé
        if (characters.size() > maxCapacity * 0.9) {
            return true;
        }

        return false;
    }

    /**
     * Obtient le nombre de places disponibles
     */
    public int getAvailableSpace() {
        return maxCapacity - characters.size();
    }

    /**
     * Vérifie si l'enclos est plein
     */
    public boolean isFull() {
        return characters.size() >= maxCapacity;
    }

    /**
     * Vérifie si l'enclos est vide
     */
    public boolean isEmpty() {
        return characters.isEmpty();
    }

    /**
     * Nourrit toutes les créatures de l'enclos
     */
    @Override
    public void feedAll() {
        System.out.println("\n🍖 Nourrissage des créatures dans l'enclos " + name);

        if (foods.isEmpty()) {
            System.out.println("⚠️ Pas de nourriture disponible !");
            return;
        }

        int fed = 0;
        for (Character c : characters) {
            if (c.isDead()) continue;

            if (c.getHunger() > 40) { // Nourrir si faim > 40
                Food food = findSuitableFood(c);
                if (food != null) {
                    c.eat(food);
                    foods.remove(food);
                    fed++;
                    System.out.println("  - " + c.getName() + " a mangé " + food.getName());
                }
            }
        }

        if (fed == 0) {
            System.out.println("  Aucune créature n'avait faim");
        } else {
            System.out.println("  " + fed + " créature(s) nourrie(s)");
        }
    }

    /**
     * Soigne toutes les créatures de l'enclos
     */
    @Override
    public void healAll(int amount) {
        System.out.println("\n💚 Soins des créatures dans l'enclos " + name);

        int healed = 0;
        for (Character c : characters) {
            if (!c.isDead() && c.getHealth() < 100) {
                c.heal(amount);
                healed++;
                System.out.println("  - " + c.getName() + " a été soigné");
            }
        }

        if (healed == 0) {
            System.out.println("  Aucune créature n'avait besoin de soins");
        } else {
            System.out.println("  " + healed + " créature(s) soignée(s)");
        }
    }

    /**
     * Calme les créatures agressives
     */
    public void calmCreatures() {
        System.out.println("\n🌙 Apaisement des créatures dans l'enclos " + name);

        int calmed = 0;
        for (Character c : characters) {
            if (!c.isDead() && c.getBelligerence() > 50) {
                c.setBelligerence(c.getBelligerence() - 20);
                calmed++;
                System.out.println("  - " + c.getName() + " s'est calmé");
            }
        }

        if (calmed == 0) {
            System.out.println("  Les créatures sont déjà calmes");
        } else {
            System.out.println("  " + calmed + " créature(s) apaisée(s)");
        }
    }

    /**
     * Affiche un rapport détaillé de l'enclos
     */
    public void displayDetailedReport() {
        System.out.println("\n📋 RAPPORT DÉTAILLÉ - Enclos " + name);
        System.out.println("════════════════════════════════════════");

        int total = characters.size();
        int alive = 0;
        int hungry = 0;
        int wounded = 0;
        int aggressive = 0;

        for (Character c : characters) {
            if (!c.isDead()) {
                alive++;
                if (c.getHunger() > 60) hungry++;
                if (c.getHealth() < 50) wounded++;
                if (c.getBelligerence() > 60) aggressive++;
            }
        }

        System.out.println("Créatures totales : " + total);
        System.out.println("Vivantes : " + alive);
        System.out.println("Mortes : " + (total - alive));
        System.out.println("Affamées : " + hungry);
        System.out.println("Blessées : " + wounded);
        System.out.println("Agressives : " + aggressive);
        System.out.println("Nourriture disponible : " + foods.size() + " portion(s)");
        System.out.println("Places disponibles : " + getAvailableSpace() + "/" + maxCapacity);

        System.out.println("\nÉtat général : " + getEnclosureStatus());
        System.out.println("════════════════════════════════════════\n");
    }

    /**
     * Détermine l'état général de l'enclos
     */
    private String getEnclosureStatus() {
        if (characters.isEmpty()) {
            return "🟢 Vide";
        }

        int healthyCount = 0;
        for (Character c : characters) {
            if (!c.isDead() && c.getHealth() > 60 && c.getHunger() < 50) {
                healthyCount++;
            }
        }

        float healthyRate = (healthyCount * 100.0f) / characters.size();

        if (healthyRate >= 80) {
            return "🟢 Excellent";
        } else if (healthyRate >= 60) {
            return "🟡 Bon";
        } else if (healthyRate >= 40) {
            return "🟠 Moyen";
        } else {
            return "🔴 Critique - Intervention nécessaire";
        }
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}