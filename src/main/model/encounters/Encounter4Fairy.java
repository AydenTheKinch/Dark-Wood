package model.encounters;

import model.Player;
import model.Story;
import model.monsters.Monster;
import model.monsters.MonsterFairy;

import java.util.Random;

public class Encounter4Fairy extends Encounter {

    public Encounter4Fairy(Player player, Story story) {
        super(player, story);
        this.story = story;
        this.player = player;
        random = new Random();
    }

    @Override
    //Effects: creates a combat between the fairy and the player, this will no longer happen if the fairy is dead.
    public void startEncounter() {
        story.addStory("You proceed through the forest. In front of you looms a stagnant lake.");
        story.addStory("There is a path to your east, and to your west.");
        if (!player.checkMemory("killed the fairy")) {
            story.addStory("The water begins to bubble, crawling out of it is a human-like creature. \n"
                    + "It's wings shimmer in the dappled light from above. Silver mushrooms"
                    + " grow out of it's shoulders.");
            Monster fairyMonster = new MonsterFairy();
            loadCombat(player, fairyMonster);
        }
    }

    @Override
    public void uniqueCommands(String command) {
        if (command.equals("east")) {
            travel(2);
        } else if (command.equals("west")) {
            travel(5);
        } else if (command.equals("look")) {
            story.addStory("There's a pond nearby. Flies buzz over the water\n"
                    + "There is a path to your east, and to your west.");
        }
    }

    @Override
    public void queryCombatEnd(Monster defeatedMonster) {
        grief();
    }

    //Modifies: player, inventory
    //Effects: adds grief to the player's inventory if they kill the fairy. If they already have grief (it's immortal),
    //         the fairy heals them instead.
    public void grief() {
        if (player.checkItem("grief")) {
            story.addStory("The puddle slides forward until it surrounds your feet.\n"
                    + "The water begins to burn you, but suddenly, it stops. It knows what you've seen.\n"
                    + "It begins to embrace you. Its cool waters stink with rot, but you needed this.\n"
                    + "It holds you tight and fills you with determination. You can do this.\n"
                    + "It slides off you and disappears into the lake.");
            int heal = 50;
            player.takeDamage(-heal);
            story.addStory("You are healed for " + heal + " damage!");
            player.addMemory("killed the fairy");
        } else {
            story.addStory("The puddle slides forward until it surrounds your feet.\n"
                    + "The water sizzles and burns through your clothes as it roils up and "
                    + "slides through your skin.\nSuddenly, you are overcome with grief. You look around "
                    + "you and realize the forest is dying. Something did this. Why?\n"
                    + "The fairy tried to find it, but there was nothing it could do.");
            player.addItem("grief");
            player.addMemory("killed the fairy");
        }
    }
}
