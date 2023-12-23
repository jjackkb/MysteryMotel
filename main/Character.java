package main;

import java.util.ArrayList;
import java.util.Objects;

public class Character {
    private final String name;
    private static final ArrayList<Item> inventory = new ArrayList<>();
    private static final ArrayList<Character> characters = new ArrayList<>();

    public Character(String name, int room) {
        this.name = name;

        Objects.requireNonNull(Room.getRoom(room)).addCharacter(this, room);
        characters.add(this);
    }

    public static void addInventory(Item item) {
        inventory.add(item);
    }
    public static void removeInventory(Item a) {
        inventory.removeIf(i -> i == a);
    }
    public String getName() {
        return name;
    }
    public Character getCharacter(String name) {
        for (Character c : characters) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }
    public static ArrayList<Item> getInventory() {
        return inventory;
    }
}
