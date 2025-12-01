package org.example.envahissementarmorique.model.place;


/**
 * Champ de bataille - peut contenir tous les types de personnages
 * N'a pas de chef de clan
 */
public class Battlefield extends Place {

    public Battlefield(String name, float area) {
        super(name, area, null); // Pas de chef pour un champ de bataille
    }

    @Override
    protected boolean canAddCharacter(Character c) {
        return true; // Tous les personnages peuvent entrer sur un champ de bataille
    }

    @Override
    public void display() {
        System.out.println("\n========================================");
        System.out.println("⚔️  CHAMP DE BATAILLE : " + name);
        System.out.println("Superficie : " + area + " m²");
        System.out.println("Chef : Aucun (zone de combat)");

        System.out.println("\nNombre de combattants : " + characters.size());
        if (!characters.isEmpty()) {
            System.out.println("Combattants présents :");
            for (Character c : characters) {
                System.out.println("  • " + c.toString() +
                        (c.isDead() ? " [MORT]" : " [Santé: " + c.getHealth() + "]"));
            }
        } else {
            System.out.println("  (Aucun combattant)");
        }

        System.out.println("\nNombre d'aliments : " + foods.size());
        if (!foods.isEmpty()) {
            System.out.println("Aliments présents :");
            for (Food f : foods) {
                System.out.println("  • " + f.toString());
            }
        } else {
            System.out.println("  (Aucun aliment)");
        }
        System.out.println("========================================\n");
    }

    /**
     * Organise un combat entre tous les belligérants présents
     * Priorité aux combats entre camps opposés
     */
    public void organizeBattle() {
        System.out.println("\n🗡️  COMBAT SUR " + name.toUpperCase() + " 🗡️");

        if (characters.size() < 2) {
            System.out.println("Pas assez de combattants pour organiser un combat.");
            return;
        }

        // Identifier les camps
        List<Character> gaulois = getGauloisCharacters();
        List<Character> romans = getRomanCharacters();
        List<Character> creatures = getFantasticCreatures();

        // Combats entre Gaulois et Romains en priorité
        battleBetweenCamps(gaulois, romans);

        // Combats impliquant les créatures
        if (!creatures.isEmpty()) {
            battleWithCreatures(creatures, gaulois);
            battleWithCreatures(creatures, romans);
        }

        System.out.println("Fin du combat sur " + name);
    }

    private void battleBetweenCamps(List<Character> camp1, List<Character> camp2) {
        int i = 0, j = 0;
        while (i < camp1.size() && j < camp2.size()) {
            Character c1 = camp1.get(i);
            Character c2 = camp2.get(j);

            if (!c1.isDead() && !c2.isDead() && c1.isBelligerent() && c2.isBelligerent()) {
                System.out.println("\n⚔️  " + c1.getName() + " affronte " + c2.getName());
                c1.fight(c2);
            }

            if (c1.isDead()) i++;
            if (c2.isDead()) j++;
            if (!c1.isDead() && !c2.isDead()) {
                i++; j++;
            }
        }
    }

    private void battleWithCreatures(List<Character> creatures, List<Character> others) {
        for (Character creature : creatures) {
            if (creature.isDead() || !creature.isBelligerent()) continue;

            for (Character other : others) {
                if (other.isDead() || !other.isBelligerent()) continue;

                System.out.println("\n🐺  " + creature.getName() + " attaque " + other.getName());
                creature.fight(other);

                if (creature.isDead()) break;
            }
        }
    }

    private List<Character> getGauloisCharacters() {
        List<Character> result = new ArrayList<>();
        for (Character c : characters) {
            if (c instanceof Gaulois) {
                result.add(c);
            }
        }
        return result;
    }

    private List<Character> getRomanCharacters() {
        List<Character> result = new ArrayList<>();
        for (Character c : characters) {
            if (c instanceof Roman) {
                result.add(c);
            }
        }
        return result;
    }

    private List<Character> getFantasticCreatures() {
        List<Character> result = new ArrayList<>();
        for (Character c : characters) {
            if (c instanceof FantasticCreature) {
                result.add(c);
            }
        }
        return result;
    }

    /**
     * Renvoie les survivants dans leur lieu d'origine
     */
    public void sendBackSurvivors(List<Place> allPlaces) {
        System.out.println("\nRenvoi des survivants dans leur lieu d'origine...");

        List<Character> toRemove = new ArrayList<>();
        for (Character c : characters) {
            if (!c.isDead()) {
                Place originPlace = c.getOriginPlace();
                if (originPlace != null && originPlace != this) {
                    if (originPlace.addCharacter(c)) {
                        toRemove.add(c);
                        System.out.println(c.getName() + " retourne à " + originPlace.getName());
                    }
                }
            }
        }

        characters.removeAll(toRemove);
        removeDeadCharacters(); // Retirer les morts du champ de bataille
    }

    /**
     * Compte les pertes
     */
    public int countDeaths() {
        int count = 0;
        for (Character c : characters) {
            if (c.isDead()) {
                count++;
            }
        }
        return count;
    }
}