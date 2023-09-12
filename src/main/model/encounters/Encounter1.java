package model.encounters;

import model.Player;
import model.Story;

import java.util.Random;

public class Encounter1 extends Encounter0Starting {

    public Encounter1(Player player, Story story) {
        super(player, story);
        this.story = story;
        this.player = player;
        random = new Random();
    }

    @Override
    public void startEncounter() {
        story.addStory("You arrive back at the clearing you woke up in. The region fills you with dread.");
    }
}
