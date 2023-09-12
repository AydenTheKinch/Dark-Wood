package persistence;

import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Player testPlayer = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyInventory() {
        JsonReader reader = new JsonReader("./data/testEmptyInventory.json");
        try {
            Player testPlayer = reader.read();
            assertEquals(0, testPlayer.getInventory().size());
            assertEquals(0, testPlayer.getMemory().size());
            assertEquals(100, testPlayer.getHealth());
            assertEquals(0, testPlayer.getPosition());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderRobustInventory() {
        JsonReader reader = new JsonReader("./data/testRobustInventory.json");
        try {
            Player testPlayer = reader.read();
            assertEquals(3, testPlayer.getInventory().size());
            assertTrue(testPlayer.checkItem("branch"));
            assertTrue(testPlayer.checkItem("heart"));
            assertTrue(testPlayer.checkItem("grief"));
            assertEquals(3, testPlayer.getMemory().size());
            assertTrue(testPlayer.checkMemory("a burning heart"));
            assertTrue(testPlayer.checkMemory("killed the fairy"));
            assertTrue(testPlayer.checkMemory("hanged man"));
            assertEquals(37, testPlayer.getHealth());
            assertEquals(4, testPlayer.getPosition());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderZeroHealth() {
        JsonReader reader = new JsonReader("./data/testZeroHealth.json");
        try {
            Player testPlayer = reader.read();
            assertEquals(3, testPlayer.getInventory().size());
            assertEquals(3, testPlayer.getMemory().size());
            assertEquals(0, testPlayer.getHealth());
            assertEquals(4, testPlayer.getPosition());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
