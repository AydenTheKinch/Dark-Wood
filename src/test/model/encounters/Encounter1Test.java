package model.encounters;

import model.Player;
import model.Story;
import model.monsters.MonsterFly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Encounter1Test {
    private Encounter testEncounter;
    private Player player = new Player();
    private Story story = new Story();

    @BeforeEach
    public void setup() {
        testEncounter = new Encounter1(player, story);
    }

    @Test
    public void testStartEncounter() {
        testEncounter.startEncounter();
        assertEquals("You arrive back at the clearing you woke up in. The region fills you with dread.\n",
                story.getString());
    }

    @Test
    public void testQueryCombatEnd() {
        testEncounter.queryCombatEnd(new MonsterFly());
        assertEquals("You're not supposed to get into a fight here.\n", story.getString());
    }

}