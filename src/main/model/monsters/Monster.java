package model.monsters;

import java.util.Random;

public abstract class Monster {
    protected String name;
    protected int health;
    protected boolean defeated;
    protected boolean ranAway;
    protected String description;
    protected Random random;

    //EFFECTS: create generic monster, it is currently undefeated and the player has not successfully escaped from it.
    public Monster() {
        defeated = false;
        ranAway = false;
        random = new Random();
    }

    //MODIFIES: this;
    //EFFECTS: lower the monsters' health by the listed damage. if damage is a negative value, heal it.
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            defeated = true;
        }
    }

    //MODIFIES: this
    //EFFECTS: tells the monster that the player has successfully run away.
    public void runAway() {
        ranAway = true;
    }

    //EFFECTS: return the specific monster's introduction.
    public abstract String getAttackMessage();

    //EFFECTS: returns how much damage the Monster's attack will do.
    public abstract int getAttack();

    //EFFECTS: return the specific monster's defeat text.
    public abstract String getDefeatText();

    //EFFECTS: return the specific monster's ready text.
    public abstract String getReadyText();

    public boolean getDefeated() {
        return defeated;
    }

    public boolean getRanWay() {
        return ranAway;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getName() {
        return name;
    }
}
