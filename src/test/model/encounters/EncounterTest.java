package model.encounters;

import model.Item;
import model.Player;
import model.Story;
import model.monsters.Monster;
import model.monsters.MonsterFly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EncounterTest {
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
        map = new ArrayList<>();
        map.add(new Encounter0Starting(player, story));
        map.add(new Encounter1(player, story));
        map.add(new Encounter2Fly(player, story));
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
        testEncounter.setMap(map);
    }

    @Test
    public void testProcessCommandInvalid(){
        testEncounter.processCommand("test");
        story.addStory("Type \"help\" for a list of commands.");
    }

    @Test
    public void testListInventory() {
        player.addItem("branch");
        player.addItem("grief");
        testEncounter.processCommand("inventory");
        assertEquals("This is all you have with you:" + "\n"
                + "branch" + "\n" + "grief" + "\n", story.getString());
    }

    @Test
    public void testListInventoryEmpty() {
        testEncounter.processCommand("inventory");
        assertEquals("This is all you have with you:" + "\n"
                + "Your will to live." + "\n", story.getString());
    }

    @Test
    public void testListMemory() {
        player.addMemory("branch");
        player.addMemory("grief");
        testEncounter.processCommand("remember");
        assertEquals("You remember the following" + "\n"
        + "branch" + "\n" + "grief" + "\n", story.getString());
    }

    @Test
    public void testListMemoryEmpty() {
        testEncounter.processCommand("remember");
        assertEquals("You remember the following"
                + "\n" + "Nothing, absolutely nothing." + "\n", story.getString());
    }

    @Test
    public void testExamineItem() {
        player.addItem("grief");
        player.addItem("branch");
        player.addMemory("grief");
        testEncounter.processCommand("grief");
        assertEquals("The fairy's suffering weighs you down like a cloak." + "\n", story.getString());
    }

    @Test
    public void testExamineMemory() {
        player.addItem("grief");
        player.addMemory("grief");
        player.addMemory("branch");
        testEncounter.processCommand("branch");
        assertEquals("A solid branch. It must have fallen from a large cedar.\nThe base is"
                + " covered in silver." + "\n", story.getString());
    }

    @Test
    public void testHelp() {
        testEncounter.processCommand("help");
        assertEquals("Your inventory is on the left side of the screen. "
                        + "Your health is the bar above your inventory.\n"
                        + "Here is a list of common commands, unique commands are listed like \"this\"\n"
                        + "\"quit\" - Exit the game.\n"
                        + "\"save\" - Save your game.\n"
                        + "\"remember\" - Recall what you can.\n"
                        + "\"look\" - Look around.\n"
                        + "\"examine\" - Enter the item or memory you would like to examine.\n"
                        + "\"pickup\" - List the items that can be picked up. "
                        + "Enter \"pickup\", followed by the name of the item to add it to your inventory\n"
                , story.getString());
    }

    @Test
    public void testWakeUp() {
        player.addItem("grief");
        player.addItem("branch");
        player.setPosition(3);
        testEncounter.processCommand("wake up");
        assertFalse(player.checkItem("branch"));
        assertTrue(player.checkItem("grief"));
        assertEquals(0, player.getPosition());
    }

    @Test
    public void testResetGame() {
        player.addItem("grief");
        player.addItem("branch");
        player.setPosition(1);
        player.setHealth(Player.STARTING_HEALTH - 1);
        testEncounter.resetGame("Test");
        assertFalse(player.checkItem("branch"));
        assertTrue(player.checkItem("grief"));
        assertEquals(0, player.getPosition());
        assertEquals(Player.STARTING_HEALTH, player.getHealth());
        assertEquals("Test\n" + "You wake up in a dark wood.\n" +
                "There is a path to your \"north\".\n", story.getString());
    }

    @Test
    public void testGetActiveMonster() {
        Monster testMonster = new MonsterFly();
        testEncounter.loadCombat(player, testMonster);
        testEncounter.setActiveMonster(testMonster);
        assertEquals(testMonster, testEncounter.getActiveMonster());
    }

    @Test
    void testEndCombat() {
        Monster testMonster = new MonsterFly();
        testEncounter.loadCombat(player, testMonster);
        assertTrue(testEncounter.getInCombat());
        assertEquals(testMonster, testEncounter.getActiveMonster());
        assertNotNull(testEncounter.getCombat());
        testEncounter.endCombat();
        assertFalse(testEncounter.getInCombat());
        assertNull(testEncounter.getActiveMonster());
        assertNull(testEncounter.getCombat());
    }
}

