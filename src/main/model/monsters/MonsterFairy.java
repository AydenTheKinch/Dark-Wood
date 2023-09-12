package model.monsters;

import java.util.Random;

public class MonsterFairy extends Monster {

    public static final int FAIRY_BASE_HEALTH = 50;
    public static final int FAIRY_HEALTH_RANGE = 10;
    public static final int FAIRY_BASE_ATTACK = 15;
    public static final int FAIRY_ATTACK_RANGE = 10;

    public MonsterFairy() {
        super();
        name = "fairy";
        random = new Random();
        health = FAIRY_BASE_HEALTH + random.nextInt(FAIRY_HEALTH_RANGE);
    }

    @Override
    public String getAttackMessage() {
        return "The creature sinks its fangs into you ripping away a chunk of your skin.";
    }

    @Override
    public int getAttack() {
        return FAIRY_BASE_ATTACK + random.nextInt(FAIRY_ATTACK_RANGE);
    }

    @Override
    public String getDefeatText() {
        return "The creature blubbers to the ground and melts into a puddle.";
    }

    @Override
    public String getReadyText() {
        return "The creature spreads its butterfly wings and bears its fangs. It's crying.";
    }
}
