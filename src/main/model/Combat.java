package model;

import exceptions.ExceptionInvalidAction;
import model.encounters.Encounter;
import model.monsters.Monster;

import java.util.Random;
import java.util.Scanner;

public class Combat {

    private final Player player;
    private final Monster monster;
    private Story story;
    private Encounter encounter;
    private final Random random;

    //Effects: creates new combat between the player and monster.
    public Combat(Player player, Monster monster, Encounter encounter, Story story) {
        this.player = player;
        this.monster = monster;
        this.encounter = encounter;
        this.story = story;
        random = new Random();
    }

    public void startCombat() {
        story.addStory(monster.getReadyText());
        story.addStory("You can \"attack\" or you can try to \"run\"");
    }

    //Modifies: player, monster
    //Effects: runs combat until monster is defeated, player is killed, or player runs away.
    public void runCombat(String command) {
        try {
            if (!monster.getDefeated() && !monster.getRanWay() && !player.getDead()) {
                int playerAttack = combatActions(command);
                monster.takeDamage(playerAttack);
            }
            if (monster.getDefeated()) {
                encounter.endCombat();
                story.addStory(monster.getDefeatText());
                encounter.queryCombatEnd(monster);
            } else if (player.getDead()) {
                encounter.endCombat();
                encounter.resetGame("You collapse and your mind fades away.");
            } else if (monster.getRanWay()) {
                encounter.endCombat();
                story.addStory("You successfully escape.");
            } else {
                monsterTurn();
                deathsDoor();
            }
        } catch (ExceptionInvalidAction e) {
            story.addStory("You still have to fight.");
        }
    }

    //Modifies: player
    //Effects: deals damage to player based on the monster attack value.
    public void monsterTurn() {
        int defense = playerDefense();
        int damage = monster.getAttack() - defense;
        if (damage < 0) {
            damage = 0;
        }
        story.addStory(monster.getAttackMessage());
        player.takeDamage(damage);
        story.addStory("You took " + damage + " damage!");
    }

    //Effects: processes command in combat
    public int combatActions(String command) throws ExceptionInvalidAction {
        if (command.equals("attack")) {
            return playerAttack();
        } else if (command.equals("run")) {
            return run();
        } else if (command.equals("help")) {
            help();
            throw new ExceptionInvalidAction();
        } else if (player.checkMemory(command) || player.checkItem(command)) {
            encounter.examine(command);
            throw new ExceptionInvalidAction();
        } else {
            story.addStory("Type \"help\" for a list of commands.");
            throw new ExceptionInvalidAction();
        }
    }

    //Effects: return the damage value when attacking a monster
    public int playerAttack() {
        int bonus = 0;
        if (player.checkItem("heart")) {
            story.addStory("You feel a surge of energy!");
            bonus = 12;
        }
        if (player.checkItem("branch")) {
            story.addStory("You swing your branch hard at your opponent.");
            int damage = 8 + random.nextInt(10) + bonus;
            story.addStory("You deal " + damage + " damage!");
            return damage;
        }
        story.addStory("You desperately claw and bite at your opponent.");
        int damage = Player.BASE_DAMAGE + random.nextInt(Player.BASE_DAMAGE_RANGE) + bonus;
        story.addStory("You deal " + damage + " damage!");
        return damage;
    }

    //Effects: randomly determines if player successfully escapes the monster. this action always does 0 damage.
    public int run() {
        if (random.nextInt(5) > 1) {
            monster.runAway();
        } else {
            story.addStory("You fail to escape.");
        }
        return 0;
    }

    public void help() {
        story.addStory("You are fighting for your life. You can not save your game in combat. "
                + "Here is a list of common commands in combat.\n"
                + "\"quit\" - Exit the game."
                + "\"attack\" - Swing with all your might, you will use the most damaging item in your inventory."
                + "\"run\" - You can try to escape combat, this will give up your turn if you fail.");
    }

    public int playerDefense() {
        int defense = 0;
        if (player.checkItem("grief")) {
            defense = 5;
            story.addStory("You're struck, but grief has hardened your heart.");
        }
        return defense;
    }

    //Effects: prints a message letting the player know they are going to die at the end of their turn.
    public void deathsDoor() {
        if (player.getDead()) {
            story.addStory("You're about to die. You've lost too much blood.");
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public Monster getMonster() {
        return monster;
    }

    public Story getStory() {
        return story;
    }
}
