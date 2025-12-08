package org.example.envahissementarmorique.model.place;

import org.example.envahissementarmorique.model.character.base.GameCharacter;
import org.example.envahissementarmorique.model.item.*;
import org.example.envahissementarmorique.model.character.base.ClanLeader;

import java.util.ArrayList;
import java.util.List;

public abstract class Place {

    protected String name;
    protected float area;
    protected ClanLeader chief;
    protected List<GameCharacter> characters = new ArrayList<>();
    protected List<Food> foods = new ArrayList<>();

    public Place(String name, float area, ClanLeader chief) {
        this.name = name;
        this.area = area;
        this.chief = chief;
    }

    // ============== GETTERS / SETTERS ==============
    public String getName() { return name; }
    public float getArea() { return area; }
    public ClanLeader getChief() { return chief; }
    public void setChief(ClanLeader chief) { this.chief = chief; }
    public List<GameCharacter> getCharacters() { return characters; }
    public List<Food> getFoods() { return foods; }
    public int getNumberOfCharacters() { return characters.size(); }

    // ============== AFFICHAGE ==============
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

    public boolean removeCharacter(GameCharacter c) {
        if (characters.remove(c)) {
            System.out.println(c.getName() + " quitte " + name);
            return true;
        }
        return false;
    }

    public void removeDeadCharacters() {
        characters.removeIf(GameCharacter::isDead);
    }

    public GameCharacter getCharacterByName(String name) {
        for (GameCharacter c : characters) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public List<GameCharacter> getCharactersByType(Class<?> type) {
        List<GameCharacter> result = new ArrayList<>();
        for (GameCharacter c : characters) {
            if (type.isInstance(c)) {
                result.add(c);
            }
        }
        return result;
    }

    public int getAliveCharactersCount() {
        int count = 0;
        for (GameCharacter c : characters) {
            if (!c.isDead()) count++;
        }
        return count;
    }

    // ============== SOINS ==============
    public void healAll(int amount) {
        for (GameCharacter c : characters) {
            if (!c.isDead()) {
                c.heal(amount);
            }
        }
    }

    // ============== NOURRITURE ==============
    public void feedAll() {
        for (GameCharacter c : characters) {
            if (c.needsFood()) {
                Food food = findSuitableFood(c);
                if (food != null) {
                    c.consume(food);
                    foods.remove(food);
                }
            }
        }
    }

    private Food findSuitableFood(GameCharacter c) {
        for (Food food : foods) {
            if (c.canEat(food)) return food;
        }
        return null;
    }

    public void addFood(Food food) { foods.add(food); }
    public void addFoods(List<Food> foodList) { foods.addAll(foodList); }
    public void removeFood(Food food) { foods.remove(food); }
    public void clearFoods() { foods.clear(); }

    // ============== Filtrage par type de lieu ==============
    protected abstract boolean canAddCharacter(GameCharacter c);

}
