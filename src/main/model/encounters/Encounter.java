package model.encounters;

import model.*;
import model.monsters.Monster;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static javax.swing.UIManager.get;

public abstract class Encounter {
    protected Random random;
    protected Story story;
    protected Combat combat;
    protected Monster activeMonster;
    protected int timesRun;
    protected boolean inCombat;
    protected Player player;
    protected ArrayList<Encounter> map;

    //Effects: create generic encounter, establish scanner, player is passed into it so it's easier to modify.
    public Encounter(Player player, Story story) {
        this.player = player;
        this.story = story;
        this.map = new ArrayList<>();
        random = new Random();
        timesRun = 0;
        inCombat = false;
        combat = null;
    }

    //Effects: processes most commands
    public void processCommand(String command) {
        String oldString = story.getString();
        if (inCombat) {
            combat.runCombat(command);
        } else {
            defaultCommands(command);
            uniqueCommands(command);
            if (oldString.equals(story.getString())) {
                story.addStory("Type \"help\" for a list of commands.");
            }
        }
    }

    //Effects: processes all generic commands, all encounters use these.
    public void defaultCommands(String command) {
        if (command.equals("inventory")) {
            listInv();
        } else if (command.equals("remember")) {
            listMemory();
        } else if (player.checkMemory(command) || player.checkItem(command)) {
            examine(command);
        } else if (command.equals("help")) {
            help();
        } else if (command.equals("wake up")) {
            resetGame("This is all a bad dream, you just need to...");
        } else if (command.equals("examine")) {
            story.addStory("Enter the item or memory you would like to examine.");
//        } else if (command.equals("health")) {
//            health();
        }
    }

    //Effect: prints text that informs the player their general health. the actual value is slightly obfuscated.
//    public void health() {
//        if (player.getHealth() > 100) {
//            story.addStory("You have never felt better.");
//        } else if (player.getHealth() == 100) {
//            story.addStory("There's not a scratch on you.");
//        } else if ((100 > player.getHealth()) && (player.getHealth() >= 75)) {
//            story.addStory("You're hurt, but they haven't drawn blood.");
//        } else if ((75 > player.getHealth()) && (player.getHealth() >= 50)) {
//            story.addStory("You're bleeding, but it's manageable");
//        } else if ((50 > player.getHealth()) && (player.getHealth() >= 25)) {
//            story.addStory("You're covered in your own blood.");
//        } else if ((25 > player.getHealth()) && (player.getHealth() >= 1)) {
//            story.addStory("You can barely stand. Blood covers your eyes.");
//        } else if (player.getHealth() <= 0) {
//            System.out.print("Your soul flickers. It's about to go out unless you do something.");
//        }
//    }

    //Effects: returns list of items in your inventory
    public void listInv() {
        story.addStory("This is all you have with you:");
        if (player.getInventory().size() == 0) {
            story.addStory("Your will to live.");
        } else {
            for (Item item : player.getInventory()) {
                story.addStory(item.getName());
            }
        }
    }

    //Effects: returns list of experiences in you memory.
    public void listMemory() {
        story.addStory("You remember the following");
        if (player.getMemory().size() == 0) {
            story.addStory("Nothing, absolutely nothing.");
        } else {
            for (Item experience : player.getMemory()) {
                story.addStory(experience.getName());
            }
        }
    }

    //Effects: print description of given object name, otherwise, print an error message.
    public void examine(String command) {
        if (player.checkItem(command)) {
            for (Item item : player.getInventory()) {
                if (item.getName().equals(command)) {
                    story.addStory(item.getDescription());
                }
            }
        } else if (player.checkMemory(command)) {
            for (Item experience : player.getMemory()) {
                if (experience.getName().equals(command)) {
                    story.addStory(experience.getDescription());
                }
            }
        }
    }

    //Effects: print a list of generic commands.
    public void help() {
        story.addStory("Your inventory is on the left side of the screen. "
                + "Your health is the bar above your inventory.\n"
                + "Here is a list of common commands, unique commands are listed like \"this\"\n"
                + "\"quit\" - Exit the game.\n"
                + "\"save\" - Save your game.\n"
                + "\"remember\" - Recall what you can.\n"
                + "\"look\" - Look around.\n"
                + "\"examine\" - Enter the item or memory you would like to examine.\n"
                + "\"pickup\" - List the items that can be picked up. "
                + "Enter \"pickup\", followed by the name of the item to add it to your inventory");
    }

    //Modifies: player, inventory, memory
    //Effects: removes all items from player that are not immortal, return them to life and to full health, then
    //         moves them to the starting encounter.
    public void resetGame(String resetMessage) {
        story.clearStory();
        story.addStory(resetMessage);
        player.loseInventory();
        player.loseMemory();
        player.setHealth(Player.STARTING_HEALTH);
        player.revive();
        player.setPosition(GameModule.STARTING_INDEX);
        map.get(GameModule.STARTING_INDEX).startEncounter();
    }

    //Requires: index is greater than 0 and less than size of encounter list -1.
    //Effects: runs the specified encounter and updates the player's position.
    public void travel(int index) {
        player.setPosition(index);
        Encounter encounter = map.get(index);
        story.clearStory();
        encounter.startEncounter();
    }

    //Effects: Creates combat with player and monster in current encounter.
    public void loadCombat(Player player, Monster monster) {
        combat = new Combat(player, monster, this, story);
        setCombat(true);
        setActiveMonster(monster);
        combat.startCombat();
    }

    public void endCombat() {
        story.clearStory();
        setCombat(false);
        stopCombat();
        setActiveMonster(null);
    }

    public void stopCombat() {
        combat = null;
    }

    //Effects: runs the first portion of the encounter without any player interaction.
    public abstract void startEncounter();

    //Effects: process most commands unique to the encounter
    public abstract void uniqueCommands(String command);

    public void setMap(ArrayList<Encounter> module) {
        this.map = module;
    }

    public void setCombat(boolean fighting) {
        inCombat = fighting;
    }

    public abstract void queryCombatEnd(Monster defeatedMonster);

    public void setActiveMonster(Monster monster) {
        this.activeMonster = monster;
    }

    public Monster getActiveMonster() {
        return activeMonster;
    }

    public boolean getInCombat() {
        return inCombat;
    }

    public Combat getCombat() {
        return combat;
    }
}
