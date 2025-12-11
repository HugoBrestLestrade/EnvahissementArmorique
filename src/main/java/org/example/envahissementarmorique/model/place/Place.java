package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.item.*;
import org.example.envahissementarmorique.model.character.base.ClanLeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant un lieu dans le jeu.
 *
 * Un lieu possède :
 * <ul>
 *     <li>Un nom</li>
 *     <li>Une superficie</li>
 *     <li>Un chef (ClanLeader)</li>
 *     <li>Une liste de personnages présents</li>
 *     <li>Une liste d'aliments disponibles</li>
 * </ul>
 *
 * Cette classe fournit des méthodes pour :
 * <ul>
 *     <li>Ajouter, retirer et filtrer des personnages</li>
 *     <li>Gérer la nourriture et nourrir les personnages</li>
 *     <li>Soigner tous les personnages</li>
 *     <li>Afficher les informations du lieu</li>
 * </ul>
 */
public abstract sealed class Place permits Battlefield, Camp, Enclosure, GalloRomanTown, GaulishVillage, RomanCamp, RomanCity, Village {

    /** Nom du lieu */
    protected String name;

    /** Superficie du lieu en m² */
    protected float area;

    /** Chef du lieu */
    protected ClanLeader chief;

    /** Liste des personnages présents */
    protected List<GameCharacter> characters = new ArrayList<>();

    /** Liste des aliments disponibles */
    protected List<Food> foods = new ArrayList<>();

    /**
     * Constructeur d'un lieu.
     *
     * @param name nom du lieu
     * @param area superficie du lieu
     * @param chief chef du lieu
     */
    public Place(String name, float area, ClanLeader chief) {
        this.name = name;
        this.area = area;
        this.chief = chief;
    }

    // ============== GETTERS / SETTERS ==============

    /** @return le nom du lieu */
    public String getName() { return name; }

    /** @return la superficie du lieu */
    public float getArea() { return area; }

    /** @return le chef du lieu */
    public ClanLeader getChief() { return chief; }

    /** Définit un nouveau chef pour le lieu */
    public void setChief(ClanLeader chief) { this.chief = chief; }

    /** @return la liste des personnages présents */
    public List<GameCharacter> getCharacters() { return characters; }

    /** @return la liste des aliments disponibles */
    public List<Food> getFoods() { return foods; }

    /** @return le nombre de personnages présents */
    public int getNumberOfCharacters() { return characters.size(); }

    // ============== AFFICHAGE ==============

    /** Affiche les informations du lieu, des personnages et des aliments */
    public void display() {
        System.out.println("\n========================================");
        System.out.println("Lieu : " + name + " (" + area + " m²)");
        System.out.println("Type : " + this.getClass().getSimpleName());

        if (chief != null) {
            System.out.println("Chef : " + chief.getName());
        } else {
            System.out.println("Chef : Aucun");
        }

        System.out.println("\nNombre de personnages : " + characters.size());
        if (!characters.isEmpty()) {
            System.out.println("Personnages présents :");
            for (GameCharacter c : characters) {
                System.out.println("  • " + c.toString());
            }
        } else {
            System.out.println("  (Aucun personnage)");
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

    // ============== PERSONNAGES ==============

    /**
     * Ajoute un personnage au lieu si possible.
     *
     * @param c personnage à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
    public boolean addCharacter(GameCharacter c) {
        if (c == null) {
            System.out.println("Erreur : personnage null");
            return false;
        }

        if (canAddCharacter(c)) {
            characters.add(c);
            System.out.println(c.getName() + " entre dans " + name);
            return true;
        }

        System.out.println(c.getName() + " ne peut pas entrer dans " + name);
        return false;
    }

    /**
     * Retire un personnage du lieu.
     *
     * @param c personnage à retirer
     * @return true si le personnage a été retiré, false sinon
     */
    public boolean removeCharacter(GameCharacter c) {
        if (characters.remove(c)) {
            System.out.println(c.getName() + " quitte " + name);
            return true;
        }
        return false;
    }

    /** Supprime tous les personnages morts du lieu */
    public void removeDeadCharacters() {
        characters.removeIf(GameCharacter::isDead);
    }

    /**
     * Recherche un personnage par son nom.
     *
     * @param name nom du personnage
     * @return le personnage si trouvé, null sinon
     */
    public GameCharacter getCharacterByName(String name) {
        for (GameCharacter c : characters) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Retourne la liste des personnages d'un certain type.
     *
     * @param type classe recherchée
     * @return liste des personnages du type spécifié
     */
    public List<GameCharacter> getCharactersByType(Class<?> type) {
        List<GameCharacter> result = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (type.isInstance(c)) {
                result.add(c);
            }
        }
        return result;
    }

    /** @return le nombre de personnages vivants */
    public int getAliveCharactersCount() {
        int count = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead()) count++;
        }
        return count;
    }

    // ============== SOINS ==============

    /**
     * Soigne tous les personnages vivants du lieu.
     *
     * @param amount quantité de points de vie à restaurer
     */
    public void healAll(int amount) {
        for (GameCharacter c : characters) {
            if (!c.isDead()) {
                c.ToHeal(amount);
            }
        }
    }

    // ============== NOURRITURE ==============

    /** Nourrit tous les personnages vivants du lieu */
    public void feedAll() {
        for (GameCharacter c : characters) {
            if (!c.isDead() && c.getHunger() < 100) {
                Food food = findSuitableFood();
                if (food != null) {
                    c.ToEat(food);
                    foods.remove(food);
                }
            }
        }
    }

    /** Cherche un aliment disponible adapté */
    private Food findSuitableFood() {
        if (!foods.isEmpty()) {
            return foods.get(0);
        }
        return null;
    }

    public void addFood(Food food) { foods.add(food); }
    public void addFoods(List<Food> foodList) { foods.addAll(foodList); }
    public void removeFood(Food food) { foods.remove(food); }
    public void clearFoods() { foods.clear(); }

    // ============== FILTRAGE PAR TYPE DE LIEU ==============

    /**
     * Vérifie si un personnage peut entrer dans ce lieu.
     * Doit être implémenté par les classes concrètes.
     *
     * @param c personnage à tester
     * @return true si le personnage peut entrer, false sinon
     */
    protected abstract boolean canAddCharacter(GameCharacter c);
}
