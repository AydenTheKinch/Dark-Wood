package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    private Player testPlayer;
    private Item grief;
    private Item heart;

    @BeforeEach
    public void setup() {
        heart = new Item("heart",
                "Your heart beats openly from your chest. It fills you with flame.",
                true);
        grief = new Item("grief",
                "The fairy's suffering weighs you down like a cloak.",
                false);
    }

    @Test
    public void testChangeName() {
        heart.changeName("mind");
        assertEquals("mind", heart.getName());
    }

    @Test
    public void testChangeDescription() {
        heart.changeDesc("Dang this really hurts.");
        assertEquals("Dang this really hurts.", heart.getDescription());
    }

    @Test
    public void testMakeEphemeral() {
        assertFalse(grief.getEphemeral());
        grief.makeEphemeral();
        assertTrue(grief.getEphemeral());
    }

    @Test
    public void testMakeImmortal() {
        assertTrue(heart.getEphemeral());
        heart.makeImmortal();
        assertFalse(heart.getEphemeral());
    }

    @Test
    public void testMakeImmortalAlreadyImmortal() {
        assertFalse(grief.getEphemeral());
        grief.makeImmortal();
        assertFalse(grief.getEphemeral());
    }
}