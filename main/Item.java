package main;

public class Item {
    public String name;
    private final String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public String getDescription() { return description; }
    public static Item getItem(String name) {
        for (Item i : Character.getInventory()) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }
}

