package model.encounters;

import model.Player;
import model.Story;
import model.monsters.Monster;
import model.monsters.MonsterKing;

import java.util.Random;

public class Encounter5King extends Encounter {

    public Encounter5King(Player player, Story story) {
        super(player, story);
        this.player = player;
        random = new Random();
    }

    @Override
    //Effects: initially describes the encounter, will change if the player has killed the king.
    public void startEncounter() {
        if (player.checkMemory("death of the king")) {
            story.addStory("You return to the throne. You look at the crown.");
        } else {
            story.addStory("You proceed through the forest. In front of you is a throne with a sickly king.\n"
                    + "They look ready for a \"challenge\". There is a path to your \"east\".");
        }
    }

    @Override
    public void uniqueCommands(String command) {
        if (command.equals("east")) {
            travel(4);
        } else if (command.equals("pickup")) {
            story.addStory("What would you like to pick up?");
            if (player.checkMemory("death of the king")) {
                story.addStory("the crown");
            } else {
                story.addStory("There is nothing to pick up.");
            }
        } else if (command.equals("challenge")) {
            challenge();
        } else if (command.equals("look")) {
            look();
        } else if (command.equals("pickup the crown")) {
            pickupCrown();
        }
    }

    private void pickupCrown() {
        if (player.checkMemory("death of the king")) {
            player.addMemory("touched the crown");
            resetGame("You pick up the crown and place it on your head. "
                    + "\nIt fits perfectly. Exhaustion overtakes you and you sink into the "
                    + "king's throne.\nYou grow tired. The forest spins around you. You fall asleep.");
        } else {
            story.addStory("What crown?");
        }
    }

    @Override
    public void queryCombatEnd(Monster defeatedMonster) {
        if (defeatedMonster.getName().equals("king")) {
            player.addMemory("death of the king");
        }
    }

    //Effects: creates a combat between the king and the player.
    public void challenge() {
        Monster kingMonster = new MonsterKing();
        if (player.checkItem("grief") && player.checkItem("heart")) {
            story.addStory("You raise your weapon and stare them down with all the strength you can muster.\n"
                    + "The king looks at you and sees an equal.");
        } else {
            story.addStory("You raise your weapon and stare them down with all the strength you can muster.\n"
                    + "The king sighs.");
        }
        loadCombat(player, kingMonster);
    }

    //Effects: prints a description of the surroundings, the description changes if the player has killed the king.
    public void look() {
        if (player.checkMemory("death of the king")) {
            story.addStory("The throne sits empty. The crown calls.");
        } else {
            story.addStory("The king sits on their throne. You can't make out their gender\n"
                      + "but they radiate royalty.");
        }
    }
}
