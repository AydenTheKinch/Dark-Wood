package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameModuleTest {
    private GameModule testModule;
    private Player testPlayer = new Player();

    @BeforeEach
    public void setup() {
        testModule = new GameModule(testPlayer, new Story());
        testModule.addEncounters();
    }

    @Test
    public void testConstuctor() {
        assertEquals(3, testModule.getLoot().size());
        assertEquals(5, testModule.getEvents().size());
        assertEquals(6, testModule.getGameMap().size());
        assertEquals("", testModule.getStory().getString());
        assertEquals(testPlayer, testModule.getPlayer());
    }

    @Test
    public void testAddToPlayer() {
        ArrayList<Item> testPlayerLoot = testModule.getPlayer().getLoot();
        ArrayList<Item> testPlayerEvents = testModule.getPlayer().getEvents();
        assertEquals(3, testPlayerLoot.size());
        assertEquals(5, testPlayerEvents.size());
    }
}