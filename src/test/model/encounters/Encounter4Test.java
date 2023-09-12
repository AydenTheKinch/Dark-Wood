package model.encounters;

import model.GameModule;
import model.Player;
import model.Story;
import model.monsters.MonsterFairy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Encounter4Test {
    GameModule module;
    Player player = new Player();
    Story story = new Story();
    Encounter encounter;

    @BeforeEach
    void setup() {
        module = new GameModule(player, story);
        module.addEncounters();
        player.setPosition(4);
        encounter = module.getGameMap().get(4);
    }

    @Test
    void testEast() {
        encounter.uniqueCommands("east");
        assertEquals(2, player.getPosition());
    }

    @Test
    void testWest() {
        encounter.uniqueCommands("west");
        assertEquals(5, player.getPosition());
    }

    @Test
    void testLook() {
        encounter.uniqueCommands("look");
        assertEquals("There's a pond nearby. Flies buzz over the water\n"
                + "There is a path to your east, and to your west.\n", story.getString());
    }

    @Test
    void testKilledFairy() {
        encounter.queryCombatEnd(new MonsterFairy());
        assertEquals("The puddle slides forward until it surrounds your feet.\n"
                + "The water sizzles and burns through your clothes as it roils up and "
                + "slides through your skin.\nSuddenly, you are overcome with grief. You look around "
                + "you and realize the forest is dying. Something did this. Why?\n"
                + "The fairy tried to find it, but there was nothing it could do.\n", story.getString());
        assertTrue(player.checkItem("grief"));
    }

    @Test
    void testKilledFairyWithGrief() {
        player.addItem("grief");
        player.setHealth(Player.STARTING_HEALTH - 49);
        encounter.queryCombatEnd(new MonsterFairy());
        assertEquals("The puddle slides forward until it surrounds your feet.\n"
                + "The water begins to burn you, but suddenly, it stops. It knows what you've seen.\n"
                + "It begins to embrace you. Its cool waters stink with rot, but you needed this.\n"
                + "It holds you tight and fills you with determination. You can do this.\n"
                + "It slides off you and disappears into the lake.\n"
                + "You are healed for 50 damage!\n", story.getString());
        assertEquals(1, player.getInventory().size());
        assertEquals(Player.STARTING_HEALTH, player.getHealth());
    }
}
