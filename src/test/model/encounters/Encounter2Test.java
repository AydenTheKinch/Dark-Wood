package model.encounters;

import model.GameModule;
import model.Player;
import model.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Encounter2Test {
    GameModule module;
    Player player = new Player();
    Story story = new Story();
    Encounter encounter;

    @BeforeEach
    void setup() {
        module = new GameModule(player, story);
        module.addEncounters();
        player.setPosition(2);
        encounter = module.getGameMap().get(2);
    }

    @Test
    void testLook() {
        encounter.uniqueCommands("look");
        assertEquals("The ground is patchy and sparse. A dead tree has collapsed along the side of the road."
                + "\nThere's a buzzing sound coming from the woods. To the north you hear laughter\n"
                + "To the west you hear someone crying.\n", story.getString());
    }

    @Test
    void testSouth() {
        encounter.uniqueCommands("south");
        assertEquals(1, player.getPosition());
    }

    @Test
    void testNorth() {
        encounter.uniqueCommands("north");
        assertEquals(3, player.getPosition());
    }

    @Test
    void testWest() {
        encounter.uniqueCommands("west");
        assertEquals(4, player.getPosition());
    }

    @Test
    void testInvestigate() {
        encounter.uniqueCommands("investigate");
        assertEquals("You step off the path into the woods. It smells moldy and damp.\n"
                    + "The buzzing sound grows louder as a decrepit human stumbles into view.\n"
                    + "A massive fly is on their back. It vibrates and hisses at you.\n"
                    + "The creature clicks its claws and puppets its host towards you.\n"
                    + "You can \"attack\" or you can try to \"run\"\n", story.getString());
        assertNotNull(encounter.getActiveMonster());
        assertTrue(encounter.getInCombat());
        assertNotNull(encounter.getCombat());
    }

}
