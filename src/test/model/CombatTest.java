package model;

import exceptions.ExceptionInvalidAction;
import model.encounters.Encounter;
import model.encounters.Encounter0Starting;
import model.encounters.Encounter1;
import model.encounters.Encounter2Fly;
import model.monsters.Monster;
import model.monsters.MonsterFly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CombatTest {
    Monster monster;
    Player player = new Player();
    Encounter encounter;
    Combat testCombat;
    Story story = new Story();
    ArrayList<Encounter> map;
    Random random = new Random();

    @BeforeEach
    void setup() {
        encounter = new Encounter2Fly(player, story);
        monster = new MonsterFly();
        testCombat = new Combat(player, monster, encounter, story);
        map = new ArrayList<>();
        map.add(new Encounter0Starting(player, story));
        map.add(new Encounter1(player, story));
        map.add(encounter);
        encounter.setMap(map);
        ArrayList<Item> loot = new ArrayList<>();
        loot.add(new Item("grief", "test", false));
        loot.add(new Item("branch", "test", false));
        loot.add(new Item("heart", "test", false));
        player.setLoot(loot);
    }

    @Test
    void testConstructor() {
        assertEquals(testCombat.getPlayer(), player);
        assertEquals(testCombat.getEncounter(), encounter);
        assertEquals(testCombat.getStory(), story);
        assertEquals(testCombat.getMonster(), monster);
    }

    @Test
    void testRunCombatNotDefeated() {
        testCombat.runCombat("attack");
        assertTrue(Player.STARTING_HEALTH  >=
                player.getHealth());
        assertTrue(monster.getHealth() < MonsterFly.FLY_BASE_HEALTH + MonsterFly.FLY_HEALTH_RANGE);
    }

    @Test
    void testRunCombatPlayerDefeated() {
        player.takeDamage(Player.STARTING_HEALTH);
        monster.setHealth(10);
        testCombat.runCombat("run");
        assertEquals(10, monster.getHealth());
        assertEquals(Player.STARTING_HEALTH, player.getHealth());
        assertEquals("You collapse and your mind fades away.\n"
                + "You wake up in a dark wood.\n"
                + "There is a path to your \"north\".\n", story.getString());
    }

    @Test
    void testRunCombatMonsterDefeated() {
        monster.takeDamage(MonsterFly.FLY_BASE_HEALTH+MonsterFly.FLY_HEALTH_RANGE);
        testCombat.runCombat("attack");
        assertEquals(Player.STARTING_HEALTH, player.getHealth());
        assertEquals("The fly collapses to the ground with its host. It's dead.\n"
                + "You collect some puss from the fly's eye socket.\n"
                + "There is a path to your south, to your north, and to your west.\n"
                + "You hear buzzing from off the path. You might be able to \"investigate\"\n",
                story.getString());
    }

    @Test
    void testRunCombatMonsterRanAway() {
        monster.runAway();
        testCombat.runCombat("attack");
        assertEquals(Player.STARTING_HEALTH, player.getHealth());
        assertEquals("You successfully escape.\n", story.getString());
    }

    @Test
    void testPlayerDefenseNoDefense() {
        assertEquals(0, testCombat.playerDefense());
    }

    @Test
    void testPlayerDefenseGrief() {
        player.addItem("grief");
        assertEquals(5, testCombat.playerDefense());
    }

    @Test
    void testPlayerAttack() {
        assertTrue(testCombat.playerAttack() <= Player.BASE_DAMAGE + Player.BASE_DAMAGE_RANGE);
    }

    @Test
    void testPlayerAttackWithBranch() {
        player.addItem("branch");
        assertFalse(testCombat.playerAttack() < 8);
        assertTrue(testCombat.playerAttack() <=  18);
    }

    @Test
    void testPlayerAttackWithHeart() {
        player.addItem("heart");
        assertFalse(testCombat.playerAttack() < 12 + Player.BASE_DAMAGE);
        assertTrue(testCombat.playerAttack() <= Player.BASE_DAMAGE + Player.BASE_DAMAGE_RANGE + 12);
    }

    @Test
    void testPlayerHeartAndBranch() {
        player.addItem("heart");
        player.addItem("branch");
        assertFalse(testCombat.playerAttack() < 20);
        assertTrue(testCombat.playerAttack() <=  30);
    }

    @Test
    void examineInCombat() {
        player.addItem("branch");
        testCombat.runCombat("branch");
        assertEquals(Player.STARTING_HEALTH, player.getHealth());
        assertEquals("test\n"
                    + "You still have to fight.\n", story.getString());
    }


    @Test
    void testInvalidCommandInCombat() {
        testCombat.runCombat("test");
        assertEquals("Type \"help\" for a list of commands.\n"
                + "You still have to fight.\n", story.getString());
        assertEquals(Player.STARTING_HEALTH, player.getHealth());
    }

    @Test
    void testInvalidCommandException() {
        try {
            testCombat.combatActions("test");
            fail();
        } catch (ExceptionInvalidAction e) {
            //exception expected;
        }
    }

    @Test
    void testHelpInCombat() {
        testCombat.runCombat("help");
        assertEquals("You are fighting for your life. You can not save your game in combat. "
                + "Here is a list of common commands in combat.\n"
                + "\"quit\" - Exit the game."
                + "\"attack\" - Swing with all your might, you will use the most damaging item in your inventory."
                + "\"run\" - You can try to escape combat, this will give up your turn if you fail.\n"
                + "You still have to fight.\n"
                , story.getString());
    }

    @Test
    void testDeathsDoorNotDead() {
        testCombat.deathsDoor();
        assertEquals("", story.getString());
    }

    @Test
    void testDeathsDoor() {
        player.kill();
        testCombat.deathsDoor();
        assertEquals("You're about to die. You've lost too much blood.\n", story.getString());
    }
}
