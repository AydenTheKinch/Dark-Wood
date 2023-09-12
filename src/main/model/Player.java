package model;

import log.Event;
import log.EventLog;
import model.encounters.Encounter;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class Player implements Writable {

    public static final int STARTING_HEALTH = 100;
    public static final int BASE_DAMAGE = 3;
    public static final int BASE_DAMAGE_RANGE = 5;

    private final ArrayList<Item> inventory;
    private final ArrayList<Item> memory;
    private ArrayList<Item> loot;
    private ArrayList<Item> events;
    private int health;
    private boolean dead;
    private int position;
    private ArrayList<Encounter> map;
    private EventLog log;

    //EFFECTS: create player with four empty lists, loot and events will have indexes passed in to them.
    public Player() {
        health = STARTING_HEALTH;
        inventory = new ArrayList<>();
        memory = new ArrayList<>();
        loot = new ArrayList<>(GameModule.createLoot());
        events = new ArrayList<>(GameModule.createEvents());
        dead = false;
        position = 0;
        log = EventLog.getInstance();
    }

    //MODIFIES: this, inventory
    //EFFECTS: if the item is on the loot index, add it to the player's inventory. Logs the event.
    public void addItem(String name) {
        for (Item item : loot) {
            if (item.getName().equals(name)) {
                inventory.add(item);
                Event event = new Event(item.getName() + " added to inventory.");
                log.logEvent(event);
            }
        }
    }

    //MODIFIES: this, memory
    //EFFECTS: if the experience is on the memory index, add it to the player's memory.
    public void addMemory(String name) {
        for (Item experience : events) {
            if (experience.getName().equals(name)) {
                memory.add(experience);
                Event event = new Event(experience.getName() + " added to memory.");
                log.logEvent(event);
            }
        }
    }

    //EFFECTS: check if inventory contains the name of an item, returns true or false.
    public boolean checkItem(String name) {
        for (Item item : inventory) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: check if memory contains the name of an experience, return true or false.
    public boolean checkMemory(String name) {
        for (Item experience : memory) {
            if (experience.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: deal damage to player. if it is negative, heal the player by that amount. if the player is reduced to
    //         0 health as a result of this damage, the player dies.
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            dead = true;
        } else if (health >= 100) {
            setHealth(100);
        }
    }

    //MODIFIES: this, memory, inventory
    //EFFECTS: remove only items from inventory or memory that are ephemeral. Logs the event.
    public void loseInventory() {
        ArrayList<Item> itemsToRemove = new ArrayList<>();
        ArrayList<String> itemLog = new ArrayList<>();
        for (Item item : inventory) {
            if (item.getEphemeral()) {
                itemsToRemove.add(item);
                itemLog.add(item.getName());
            }
        }
        logRemoveEvents("inventory", itemLog);
        inventory.removeAll(itemsToRemove);
    }


    public void loseMemory() {
        ArrayList<Item> memoriesToRemove = new ArrayList<>();
        ArrayList<String> memoryLog = new ArrayList<>();
        for (Item experience : memory) {
            if (experience.getEphemeral()) {
                memoriesToRemove.add(experience);
                memoryLog.add(experience.getName());
            }
        }
        logRemoveEvents("memory", memoryLog);
        memory.removeAll(memoriesToRemove);
    }

    public void logRemoveEvents(String type, ArrayList<String> list) {
        StringBuilder description = new StringBuilder();
        if (!list.isEmpty()) {
            for (String item : list) {
                description.append(item).append(" removed from ").append(type).append("\n");
            }
            Event event = new Event(description.toString());
            log.logEvent(event);
        }
    }

    public ArrayList<String> itemsToString() {
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item: inventory) {
            itemNames.add(item.getName());
        }
        return itemNames;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("health", health);
        json.put("position", position);
        json.put("inventory", itemsToInventory());
        json.put("memory", itemsToMemory());
        return json;
    }

    //EFFECTS: returns items in player's inventory as a JSON array
    public JSONArray itemsToInventory() {
        JSONArray jsonArray = new JSONArray();

        for (Item item : inventory) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: returns experiences in player's memory as a JSON arrray.
    public JSONArray itemsToMemory() {
        JSONArray jsonArray = new JSONArray();

        for (Item experience: memory) {
            jsonArray.put(experience.toJson());
        }

        return jsonArray;
    }

    //MODIFIES: this
    //EFFECTS: kills player.
    public void kill() {
        dead = true;
    }

    //MODIFIES: this
    //EFFECTS: returns player to life.
    public void revive() {
        dead = false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public ArrayList<Item> getMemory() {
        return memory;
    }

    public void setLoot(ArrayList<Item> lootTable) {
        this.loot = lootTable;
    }

    public void setEvents(ArrayList<Item> memoryTable) {
        this.events = memoryTable;
    }

    public boolean getDead() {
        return dead;
    }

    public ArrayList<Item> getLoot() {
        return loot;
    }

    public ArrayList<Item> getEvents() {
        return events;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
