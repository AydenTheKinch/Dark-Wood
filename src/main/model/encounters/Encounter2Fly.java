package model.encounters;

import model.Player;
import model.Story;
import model.monsters.Monster;
import model.monsters.MonsterFly;

import java.util.Random;

public class Encounter2Fly extends Encounter {

    public Encounter2Fly(Player player, Story story) {
        super(player, story);
        this.story = story;
        random = new Random();
        this.player = player;
    }

    @Override
    //Effects: has a 25% chance to trigger combat with a fly monster.
    public void startEncounter() {
        story.addStory("You proceed through the forest.");
        if (random.nextInt(9) >= 6) {
            story.addStory("The buzzing sound grows louder as a decrepit human stumbles out of the forest.\n"
                    + "A massive fly is on their back. It vibrates and hisses at you.");
            Monster flyMonster = new MonsterFly();
            loadCombat(player, flyMonster);
        } else {
            story.addStory("There is a path to your south, to your north, and to your west.\n"
                    + "You hear buzzing from off the path. You might be able to \"investigate\"");
        }
    }

    @Override
    public void uniqueCommands(String command) {
        if (command.equals("look")) {
            story.addStory("The ground is patchy and sparse. A dead tree has collapsed along the side of the road."
                    + "\nThere's a buzzing sound coming from the woods. To the north you hear laughter\n"
                    + "To the west you hear someone crying.");
        } else if (command.equals("south")) {
            travel(1);
        } else if (command.equals("north")) {
            travel(3);
        } else if (command.equals("west")) {
            travel(4);
        } else if (command.equals("investigate")) {
            investigate();
        }
    }

    @Override
    public void queryCombatEnd(Monster defeatedMonster) {
        if (defeatedMonster.getName().equals("fly")) {
            story.addStory("You collect some puss from the fly's eye socket.");
        }
        story.addStory("There is a path to your south, to your north, and to your west.\n"
                + "You hear buzzing from off the path. You might be able to \"investigate\"");
    }

    //Effects: creates new combat between player and the fly monster
    public void investigate() {
        Monster flyMonster = new MonsterFly();
        story.clearStory();
        story.addStory("You step off the path into the woods. It smells moldy and damp.\n"
                + "The buzzing sound grows louder as a decrepit human stumbles into view.\n"
                + "A massive fly is on their back. It vibrates and hisses at you.");
        loadCombat(player, flyMonster);
    }
}
