package model.encounters;

import model.Item;
import model.Player;
import model.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class Encounter0Test {
    private Encounter testEncounter;
    private Player player;
    private Story story;
    private Item grief;
    private Item branch;
    private ArrayList<Encounter> map;

    @BeforeEach
    public void setup() {
        story = new Story();
        player = new Player();
        ArrayList<Item> loot = new ArrayList<>();
        ArrayList<Item> events = new ArrayList<>();
        branch = new Item("branch",
                "A solid branch. It must have fallen from a large cedar.\nThe base is"
                        + " covered in silver.",
                true);
        loot.add(branch);
        grief = new Item("grief",
                "The fairy's suffering weighs you down like a cloak.",
                false);
        loot.add(grief);
        events.add(branch);
        events.add(grief);
        player.setEvents(events);
        player.setLoot(loot);
        testEncounter = new Encounter0Starting(player, story);
        map = new ArrayList<>();
        map.add(testEncounter);
        map.add(new Encounter1(player, story));
        map.add(new Encounter2Fly(player, story));
        testEncounter.setMap(map);
    }

    @Test
    public void testStartingEncounter0() {
        testEncounter.startEncounter();
        assertEquals("You wake up in a dark wood.\n"
                + "There is a path to your \"north\".\n", story.getString());
    }

    @Test
    public void testPickupBranch() {
        testEncounter.uniqueCommands("pickup branch");
        assertTrue(player.getInventory().contains(branch));
        assertEquals("You grab a few of the branches. Most are flimsy and bend easily.\n"
                + "You think one is strong enough to use as a weapon. You discard the rest.\n"
        ,story.getString());
    }

    @Test
    public void testPickupBranchWithBranch() {
        player.addItem("branch");
        assertEquals(1, player.getInventory().size());
        testEncounter.uniqueCommands("pickup branch");
        assertEquals(1, player.getInventory().size());
        assertEquals("You can't find any more usable branches.\n\n" , story.getString());
    }

    @Test
    public void testPickup() {
        testEncounter.uniqueCommands("pickup");
        assertEquals("What did you want to pick up?\n"
        + "branch\n", story.getString());
    }

    @Test
    public void testPickupWithBranch() {
        player.addItem("branch");
        testEncounter.uniqueCommands("pickup");
        assertEquals("What did you want to pick up?\n", story.getString());
    }

    @Test
    public void testLookWithBranch() {
        player.addItem("branch");
        testEncounter.uniqueCommands("look");
        assertEquals("The ground is patchy and sparse.\n"
                + "There is a path to your \"north\". You hear a faint buzzing coming from it.\n", story.getString());
    }

    @Test
    public void testLook() {
        testEncounter.uniqueCommands("look");
        assertEquals("The ground is patchy and sparse. You notice some branches nearby.\n"
                + "There is a path to your \"north\". You hear a faint buzzing coming from it.\n", story.getString());
    }

    @Test
    public void testMoveNorth() {
        testEncounter.uniqueCommands("north");
        assertEquals(2, player.getPosition());
    }


}
