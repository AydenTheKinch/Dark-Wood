package model.monsters;

import java.util.Random;

public class MonsterKing extends Monster {

    public static final int KING_BASE_HEALTH = 75;
    public static final int KING_HEALTH_RANGE = 0;
    public static final int KING_BASE_ATTACK = 10;
    public static final int KING_ATTACK_RANGE = 30;

    public MonsterKing() {
        super();
        name = "king";
        random = new Random();
        health = KING_BASE_HEALTH;
    }


    @Override
    public String getAttackMessage() {
        return "They swing their sword with a steady hand. But their heart's not in it.";
    }

    @Override
    public int getAttack() {
        return KING_BASE_ATTACK + random.nextInt(KING_ATTACK_RANGE);
    }

    @Override
    public String getDefeatText() {
        return "They crumples beneath your assault. Their crown rolls to your feet. The forest hasn't changed.";
    }

    @Override
    public String getReadyText() {
        return "They stand up and pull their chipped sword from their scabbard.\n"
                + "Their arm is thin. You can see the bone.";
    }
}
