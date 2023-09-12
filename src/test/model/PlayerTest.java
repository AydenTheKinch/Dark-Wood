package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player testPlayer;
    private Item branch;
    private Item grief;

    @BeforeEach
    public void setup() {
        testPlayer = new Player();
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
        testPlayer.setEvents(events);
        testPlayer.setLoot(loot);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testPlayer.getInventory().size());
        assertEquals(Player.STARTING_HEALTH, testPlayer.getHealth());
    }

    @Test
    public void testAddItemOneItem() {
        assertEquals(0, testPlayer.getInventory().size());
        testPlayer.addItem("branch");
        assertEquals(1, testPlayer.getInventory().size());
        assertTrue(testPlayer.getInventory().contains(branch));
        testPlayer.addItem("branch");
        assertEquals(2, testPlayer.getInventory().size());
    }

    @Test
    public void testAddItemTwoItems() {
        assertEquals(0, testPlayer.getInventory().size());
        testPlayer.addItem("branch");
        assertEquals(1, testPlayer.getInventory().size());
        testPlayer.addItem("grief");
        assertEquals(2, testPlayer.getInventory().size());
        assertTrue(testPlayer.getInventory().contains(grief));
    }

    @Test
    public void testAddMemoryOneItem() {
        assertEquals(0, testPlayer.getMemory().size());
        testPlayer.addMemory("branch");
        assertEquals(1, testPlayer.getMemory().size());
        assertTrue(testPlayer.getMemory().contains(branch));
        testPlayer.addMemory("branch");
        assertEquals(2, testPlayer.getMemory().size());
    }

    @Test
    public void testAddMemoryTwoItems() {
        assertEquals(0, testPlayer.getMemory().size());
        testPlayer.addMemory("branch");
        assertEquals(1, testPlayer.getMemory().size());
        testPlayer.addMemory("grief");
        assertEquals(2, testPlayer.getMemory().size());
        assertTrue(testPlayer.getMemory().contains(grief));
    }

    @Test
    public void testCheckItem() {
        testPlayer.addItem("branch");
        assertTrue(testPlayer.checkItem("branch"));
        assertFalse(testPlayer.checkItem("grief"));
    }

    @Test
    public void testCheckMemory() {
        testPlayer.addMemory("grief");
        assertTrue(testPlayer.checkMemory("grief"));
        assertFalse(testPlayer.checkMemory("branch"));
    }

    @Test
    public void testCheckItemEmpty() {
        assertFalse(testPlayer.checkItem("branch"));
    }

    @Test
    public void testTakeDamage() {
        testPlayer.takeDamage(50);
        assertEquals(Player.STARTING_HEALTH - 50, testPlayer.getHealth());
        testPlayer.takeDamage(48);
        assertEquals(Player.STARTING_HEALTH - 98, testPlayer.getHealth());
        testPlayer.takeDamage(1);
        assertEquals(Player.STARTING_HEALTH - 99, testPlayer.getHealth());
        testPlayer.takeDamage(0);
        assertEquals(Player.STARTING_HEALTH - 99, testPlayer.getHealth());
        assertFalse(testPlayer.getDead());
        testPlayer.takeDamage(10);
        assertEquals(Player.STARTING_HEALTH - 109, testPlayer.getHealth());
        assertTrue(testPlayer.getDead());
    }

    @Test
    public void testHealDamageFullHealth() {
        testPlayer.takeDamage(-50);
        assertEquals(Player.STARTING_HEALTH, testPlayer.getHealth());
    }

    @Test
    public void testHealDamageOverHeal() {
        testPlayer.setHealth(Player.STARTING_HEALTH - 49);
        testPlayer.takeDamage(-50);
        assertEquals(Player.STARTING_HEALTH, testPlayer.getHealth());
    }

    @Test
    public void testHealDamageUnderHeal() {
        testPlayer.setHealth(Player.STARTING_HEALTH - 51);
        testPlayer.takeDamage(-50);
        assertEquals(Player.STARTING_HEALTH - 1, testPlayer.getHealth());
    }

    @Test
    public void testHealDamagePerfectHeal() {
        testPlayer.setHealth(Player.STARTING_HEALTH-50);
        testPlayer.takeDamage(-50);
        assertEquals(Player.STARTING_HEALTH, testPlayer.getHealth());
    }

    @Test
    public void testSetHealth() {
        assertFalse(testPlayer.getDead());
        testPlayer.setHealth(-10);
        assertFalse(testPlayer.getDead());
        testPlayer.takeDamage(-5);
        assertTrue(testPlayer.getDead());
    }

    @Test
    public void testLoseItems() {
        testPlayer.addMemory("branch");
        testPlayer.addMemory("branch");
        testPlayer.addItem("branch");
        testPlayer.addItem("grief");
        assertEquals(2, testPlayer.getInventory().size());
        assertEquals(2, testPlayer.getMemory().size());
        assertTrue(testPlayer.getInventory().contains(branch));
        assertTrue(testPlayer.getMemory().contains(branch));
        assertTrue(testPlayer.getInventory().contains(grief));
        testPlayer.loseInventory();
        testPlayer.loseMemory();
        assertEquals(0, testPlayer.getMemory().size());
        assertEquals(1, testPlayer.getInventory().size());
        assertFalse(testPlayer.getInventory().contains(branch));
        assertFalse(testPlayer.getMemory().contains(branch));
        assertTrue(testPlayer.getInventory().contains(grief));
    }

    @Test
    public void testKill() {
        assertFalse(testPlayer.getDead());
        testPlayer.kill();
        assertTrue(testPlayer.getDead());
    }

    @Test
    public void testRevive() {
        assertFalse(testPlayer.getDead());
        testPlayer.kill();
        assertTrue(testPlayer.getDead());
        testPlayer.revive();
        assertFalse(testPlayer.getDead());
    }

    @Test
    public void testItemsToString() {
        testPlayer.addItem("branch");
        testPlayer.addItem("grief");
        ArrayList<String> testStrings = new ArrayList<>();
        testStrings.add("branch");
        testStrings.add("grief");
        assertEquals(testStrings,testPlayer.itemsToString());
    }
}
