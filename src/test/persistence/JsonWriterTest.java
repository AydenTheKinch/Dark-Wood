package persistence;

import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Player testPlayer = new Player();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyInventory() {
        try {
            Player testPlayer = new Player();
            JsonWriter writer = new JsonWriter("./data/testEmptyInventory.json");
            writer.open();
            writer.write(testPlayer);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyInventory.json");
            testPlayer = reader.read();
            assertEquals(0, testPlayer.getInventory().size());
            assertEquals(0, testPlayer.getMemory().size());
            assertEquals(100, testPlayer.getHealth());
            assertEquals(0, testPlayer.getPosition());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testWriterRobustInventory() {
        try {
            Player testPlayer = new Player();
            testPlayer.addItem("branch");
            testPlayer.addItem("heart");
            testPlayer.addItem("grief");
            testPlayer.addMemory("a burning heart");
            testPlayer.addMemory("killed the fairy");
            testPlayer.addMemory("hanged man");
            testPlayer.setHealth(37);
            testPlayer.setPosition(4);
            JsonWriter writer = new JsonWriter("./data/testRobustInventory.json");
            writer.open();
            writer.write(testPlayer);
            writer.close();

            JsonReader reader = new JsonReader("./data/testRobustInventory.json");
            testPlayer = reader.read();
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
}
