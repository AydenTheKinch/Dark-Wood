package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StoryTest {
    private GameModule testModule;
    private Story testStory;

    @BeforeEach
    public void setup() {
        testStory = new Story();
    }

    @Test
    public void testConstructor() {
        assertEquals("", testStory.getString());
    }

    @Test
    public void testAddStory() {
        testStory.addStory("Hello.");
        testStory.addStory("World.");
        assertEquals("Hello." + "\n" + "World." + "\n", testStory.getString());
    }

    @Test
    public void clearStory() {
        testStory.addStory("Hello.");
        testStory.addStory("World.");
        assertEquals("Hello." + "\n" + "World." + "\n", testStory.getString());
        testStory.clearStory();
        assertEquals("", testStory.getString());
    }
}
