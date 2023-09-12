package model.encounters;

import model.GameModule;
import model.Player;
import model.Story;
import model.monsters.MonsterKing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Encounter5test {
    GameModule module;
    Player player = new Player();
    Story story = new Story();
    Encounter encounter;

    @BeforeEach
    void setup() {
        module = new GameModule(player, story);
        module.addEncounters();
        player.setPosition(5);
        encounter = module.getGameMap().get(5);
    }

    @Test
    void testStartEncounterWithMemory() {
        player.addMemory("death of the king");
        encounter.startEncounter();
        assertEquals("You return to the throne. You look at the crown.\n", story.getString());
    }

    @Test
    void testStartEncounter() {
        encounter.startEncounter();
        assertEquals("You proceed through the forest. In front of you is a throne with a sickly king.\n"
                + "They look ready for a \"challenge\". There is a path to your \"east\".\n", story.getString());
    }

    @Test
    void testChallenge() {
        assertFalse(encounter.getInCombat());
        encounter.uniqueCommands("challenge");
        assertEquals("You raise your weapon and stare them down with all the strength you can muster.\n"
                + "The king sighs.\n"
                + "They stand up and pull their chipped sword from their scabbard.\n"
                + "Their arm is thin. You can see the bone.\n"
                + "You can \"attack\" or you can try to \"run\"\n", story.getString());
        assertTrue(encounter.getInCombat());
    }

    @Test
    void testChallengeReady() {
        assertFalse(encounter.getInCombat());
        player.addItem("heart");
        player.addItem("grief");
        encounter.uniqueCommands("challenge");
        assertEquals("You raise your weapon and stare them down with all the strength you can muster.\n"
                + "The king looks at you and sees an equal.\n"
                + "They stand up and pull their chipped sword from their scabbard.\n"
                + "Their arm is thin. You can see the bone.\n"
                + "You can \"attack\" or you can try to \"run\"\n", story.getString());
        assertTrue(encounter.getInCombat());
    }

    @Test
    void testEast() {
        encounter.uniqueCommands("east");
        assertEquals(4, player.getPosition());
    }

    @Test
    void testLook() {
        encounter.uniqueCommands("look");
        assertEquals("The king sits on their throne. You can't make out their gender\n"
                + "but they radiate royalty.\n", story.getString());
    }

    @Test
    void testLookWithMemory() {
        player.addMemory("death of the king");
        encounter.uniqueCommands("look");
        assertEquals("The throne sits empty. The crown calls.\n", story.getString());
    }

    @Test
    void testPickupCrown() {
        encounter.uniqueCommands("pickup the crown");
        assertEquals("What crown?\n", story.getString());
    }

    @Test
    void testPickupCrownWithMemory() {
        player.addMemory("death of the king");
        encounter.uniqueCommands("pickup the crown");
        assertEquals("You pick up the crown and place it on your head. "
                + "\nIt fits perfectly. Exhaustion overtakes you and you sink into the "
                + "king's throne.\nYou grow tired. The forest spins around you. You fall asleep.\n"
                + "You wake up in a dark wood.\n"
                + "There is a path to your \"north\".\n", story.getString());
    }

    @Test
    void testCombatEnd() {
        encounter.queryCombatEnd(new MonsterKing());
        assertTrue(player.checkMemory("death of the king"));

    }

}
