package model;

import model.encounters.*;
import model.encounters.Encounter;

import java.util.ArrayList;

public class GameModule {

    public static final int STARTING_INDEX = 0;

    private final ArrayList<Encounter> gameMap;
    private static ArrayList<Item> loot;
    private static ArrayList<Item> events;
    protected Story story;
    private Player player;

    //EFFECTS: Creates new module and generates all the encounters, items, and memories that will be in the game.
    public GameModule(Player player, Story story) {
        this.story = story;
        gameMap = new ArrayList<>();
        events = createEvents();
        loot = createLoot();
        this.player = player;
    }

    //MODIFIES: this, Encounter
    //EFFECTS: creatures list of all encounters that will be in the game and passes it all encounters. this is used for
    //         travelling between encounters.
    public void addEncounters() {
        gameMap.add(new Encounter0Starting(player, story));
        gameMap.add(new Encounter1(player, story));
        gameMap.add(new Encounter2Fly(player, story));
        gameMap.add(new Encounter3Man(player, story));
        gameMap.add(new Encounter4Fairy(player, story));
        gameMap.add(new Encounter5King(player, story));
        for (Encounter encounter : gameMap) {
            encounter.setMap(gameMap);
        }
    }

    //MODIFIES: this, loot, player
    //EFFECTS: creates an index for all items that will be in the game and passes them to the player.
    public static ArrayList<Item> createLoot() {
        loot = new ArrayList<>();
        loot.add(new Item("branch",
                "A solid branch. It must have fallen from a large cedar.\nThe base is"
                        + " covered in silver.",
                true));
        loot.add(new Item("heart",
                "Your heart beats openly from your chest. It fills you with flame.",
                true));
        loot.add(new Item("grief",
                "The fairy's suffering weighs you down like a cloak.",
                false));
        return loot;
    }

    //MODIFIES: events
    //EFFECTS: creates an index for all memories that will be in the game.
    public static ArrayList<Item> createEvents() {
        events = new ArrayList<>();
        events.add(new Item("hanged man",
                "You've met the hanged man in the clearing.",
                false));
        events.add(new Item("a burning heart",
                "Your heart still aches. It yearns to be free.",
                false));
        events.add(new Item("death of the king",
                "You have conquered the king and claimed the forest. The crown is yours.",
                true));
        events.add(new Item("touched the crown",
                "The ruling king that sees in another their equivalent rules nothing.",
                false));
        events.add(new Item("killed the fairy",
                "Could it have helped you?",
                true));
        return events;
    }

    public ArrayList<Item> getLoot() {
        return loot;
    }

    public ArrayList<Item> getEvents() {
        return events;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Encounter> getGameMap() {
        return gameMap;
    }

    public Story getStory() {
        return story;
    }

}
