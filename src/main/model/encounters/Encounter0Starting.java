package model.encounters;

import model.Player;
import model.Story;
import model.monsters.Monster;

import java.util.Random;

public class Encounter0Starting extends Encounter {

    public Encounter0Starting(Player player, Story story) {
        super(player, story);
        this.story = story;
        this.player = player;
        random = new Random();
    }

    @Override
    public void startEncounter() {
        story.addStory("You wake up in a dark wood.\n"
                 + "There is a path to your \"north\".");
    }

    @Override
    public void uniqueCommands(String command) {
        if (command.equals("north")) {
            travel(2);
        } else if (command.equals("look")) {
            lookBranch();
        } else if (command.equals("pickup")) {
            story.addStory("What did you want to pick up?");
            if (!player.checkItem("branch")) {
                story.addStory("branch");
            }
        } else if (command.equals("pickup branch")) {
            if (player.checkItem("branch")) {
                story.addStory("You can't find any more usable branches.\n");
            } else {
                story.addStory("You grab a few of the branches. Most are flimsy and bend easily.\n"
                        + "You think one is strong enough to use as a weapon. You discard the rest.");
                player.addItem("branch");
            }
        }
    }

    @Override
    public void queryCombatEnd(Monster defeatedMonster) {
        story.addStory("You're not supposed to get into a fight here.");
    }

    //Effects: looks around, the branches are removed from the description once the player picks them up.
    public void lookBranch() {
        if (player.checkItem("branch")) {
            story.addStory("The ground is patchy and sparse.\n"
                    + "There is a path to your \"north\". You hear a faint buzzing coming from it.");
        } else {
            story.addStory("The ground is patchy and sparse. You notice some branches nearby.\n"
                    + "There is a path to your \"north\". You hear a faint buzzing coming from it.");
        }
    }

//    //Modifies: player, inventory
//    //Effects: lists the possible items the player can pick up, allows them to pick up a branch if they do not
//    //         have one already
//    public void pickupBranch() {
//
//    }

}
