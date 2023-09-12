package model.monsters;

import java.util.Random;

public class MonsterFly extends Monster {

    public static final int FLY_BASE_HEALTH = 10;
    public static final int FLY_HEALTH_RANGE = 10;
    public static final int FLY_BASE_ATTACK = 5;
    public static final int FLY_ATTACK_RANGE = 10;


    public MonsterFly() {
        super();
        name = "fly";
        random = new Random();
        health = FLY_BASE_HEALTH + random.nextInt(FLY_HEALTH_RANGE);

    }

    @Override
    public String getAttackMessage() {
        return "The fly flails it's body at you";
    }

    @Override
    public int getAttack() {
        return FLY_BASE_ATTACK + random.nextInt(FLY_ATTACK_RANGE);
    }

    @Override
    public String getDefeatText() {
        return "The fly collapses to the ground with its host. It's dead.";
    }

    @Override
    public String getReadyText() {
        return "The creature clicks its claws and puppets its host towards you.";
    }
}
