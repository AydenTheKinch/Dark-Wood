package model;

import model.monsters.Monster;
import model.monsters.MonsterFairy;
import model.monsters.MonsterFly;
import model.monsters.MonsterKing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterTest {
    private Player testPlayer;
    private Monster testFairy;
    private Monster testKing;
    private Monster testFly;

    @BeforeEach
    public void setup() {
        testFairy = new MonsterFairy();
        testKing = new MonsterKing();
        testFly = new MonsterFly();
    }

    @Test
    public void testConstructor() {
        Random random = new Random();
        assertFalse(testKing.getDefeated());
        assertFalse(testKing.getRanWay());
        assertFalse(testFairy.getRanWay());
        assertFalse(testFairy.getDefeated());
        assertFalse(testFly.getDefeated());
        assertFalse(testFly.getRanWay());
        int fairyHealth = MonsterFairy.FAIRY_BASE_HEALTH + random.nextInt(MonsterFairy.FAIRY_HEALTH_RANGE);
        testFairy.setHealth(fairyHealth);
        assertTrue(testFairy.getHealth() >= MonsterFairy.FAIRY_BASE_HEALTH
                && testFairy.getHealth() <= MonsterFairy.FAIRY_HEALTH_RANGE + MonsterFairy.FAIRY_BASE_HEALTH);
        assertEquals(MonsterKing.KING_BASE_HEALTH, testKing.getHealth());
        assertEquals("king", testKing.getName());
        assertEquals("fly", testFly.getName());
        assertEquals("fairy", testFairy.getName());
    }

    @Test
    public void testTakeDamage() {
        Random random = new Random();
        int fairyHealth = MonsterFairy.FAIRY_BASE_HEALTH + random.nextInt(MonsterFairy.FAIRY_HEALTH_RANGE);
        testFairy.setHealth(fairyHealth);
        assertFalse(testFairy.getDefeated());
        testFairy.takeDamage(fairyHealth - 1);
        assertEquals(1, testFairy.getHealth());
        assertFalse(testFairy.getDefeated());
        testFairy.takeDamage(2);
        assertEquals(-1, testFairy.getHealth());
        assertTrue(testFairy.getDefeated());
    }

    @Test
    public void testHealDamage() {
        Random random = new Random();
        int fairyHealth = MonsterFairy.FAIRY_BASE_HEALTH + random.nextInt(MonsterFairy.FAIRY_HEALTH_RANGE);
        testFairy.setHealth(fairyHealth);
        assertFalse(testFairy.getDefeated());
        testFairy.takeDamage(fairyHealth - 1);
        assertEquals(1, testFairy.getHealth());
        testFairy.takeDamage(-10);
        assertEquals(11, testFairy.getHealth());
    }

    @Test
    public void testHealDamageAfterDefeat() {
        Random random = new Random();
        int fairyHealth = MonsterFairy.FAIRY_BASE_HEALTH + random.nextInt(MonsterFairy.FAIRY_HEALTH_RANGE);
        testFairy.setHealth(fairyHealth);
        assertFalse(testFairy.getDefeated());
        testFairy.takeDamage(fairyHealth - 1);
        assertEquals(1, testFairy.getHealth());
        testFairy.takeDamage(2);
        assertEquals(-1, testFairy.getHealth());
        assertTrue(testFairy.getDefeated());
        testFairy.takeDamage(-3);
        assertEquals(2, testFairy.getHealth());
        assertTrue(testFairy.getDefeated());
    }

    @Test
    public void testRunAway() {
        assertFalse(testKing.getRanWay());
        testKing.runAway();
        assertTrue(testKing.getRanWay());
    }

    @Test
    public void testGetAttackMessage() {
        assertEquals("They swing their sword with a steady hand. But their heart's not in it.",
                testKing.getAttackMessage());
        assertEquals("The creature sinks its fangs into you ripping away a chunk of your skin.",
                testFairy.getAttackMessage());
        assertEquals("The fly flails it's body at you",
                testFly.getAttackMessage());
    }

    @Test
    public void testGetAttack() {
        for (int i = 0; i < 30; i++) {
            assertTrue(testKing.getAttack() >= MonsterKing.KING_BASE_ATTACK
                    && testKing.getAttack() <= (MonsterKing.KING_ATTACK_RANGE + MonsterKing.KING_BASE_ATTACK));
        }
        for (int i = 0; i < 30; i++) {
            assertTrue(testFly.getAttack() >= MonsterFly.FLY_BASE_ATTACK
                    && testFly.getAttack() <= (MonsterFly.FLY_ATTACK_RANGE + MonsterFly.FLY_BASE_ATTACK));
        }
        for (int i = 0; i < 30; i++) {
            assertTrue(testFairy.getAttack() >= MonsterFairy.FAIRY_BASE_ATTACK
                    && testFairy.getAttack() <= (MonsterFairy.FAIRY_BASE_ATTACK + MonsterFairy.FAIRY_ATTACK_RANGE));
        }
    }

    @Test
    public void testGetDefeatText() {
        assertEquals("They crumples beneath your assault. Their crown rolls to your feet. " +
                "The forest hasn't changed.", testKing.getDefeatText());
        assertEquals("The creature blubbers to the ground and melts into a puddle.",
                testFairy.getDefeatText());
        assertEquals("The fly collapses to the ground with its host. It's dead.",
                testFly.getDefeatText());
    }

    @Test
    public void testGetReadyText() {
        assertEquals("They stand up and pull their chipped sword from their scabbard.\n"
                + "Their arm is thin. You can see the bone.", testKing.getReadyText());
        assertEquals("The creature spreads its butterfly wings and bears its fangs. It's crying.",
                testFairy.getReadyText());
        assertEquals("The creature clicks its claws and puppets its host towards you.",
                testFly.getReadyText());
    }
}
