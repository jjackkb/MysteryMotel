package main;

import java.util.ArrayList;
import java.util.Objects;

public class Room {
    public  ArrayList<Character> characters;
    private static final ArrayList<Room> rooms = new ArrayList<>();;
    private final ArrayList<Item> items;
    private final String name;
    private final int roomNum;
    private final int roomID;
    private final int x;
    private final int y;
    private boolean locked;

    public Room(int roomNum, String name, Item item, boolean locked, int x, int y) {
        characters = new ArrayList<>();
        items = new ArrayList<>();
        this.name = name;
        this.roomNum = roomNum;
        this.locked = locked;
        this.x = x;
        this.y = y;
        roomID = Commands.id(x, y);

        items.add(item);
        rooms.add(this);
    }

    public void setLocked() {
        locked = false;
    }
    public void addCharacter(Character c, int r) {
        Objects.requireNonNull(getRoom(r)).characters.add(c);
    }
    public void removeItem(Item a) {
        items.removeIf(i -> i == a);
    }
    public void setSearched() {
        boolean searched = true;
    }
    public boolean getLocked() {
        return locked;
    }
    public String getName() {
        return name;
    }
    public Character getCharacter(Room room) {return room.characters.getFirst();}
    public int getNum() {
        return roomNum;
    }
    public int getID() {
        return roomID;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public ArrayList<Item> getItems() {
        return items;
    }
    public static ArrayList<Room> getRooms() {
        return rooms;
    }
    public static Room getRoom(int num) {
        for (Room r : rooms) {
            if(r.getID() == num) {
                return r;
            }
        }
        return null;
    }
}
