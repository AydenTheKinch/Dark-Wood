package model.encounters;

import model.Player;
import model.Story;
import model.monsters.Monster;

import java.util.Random;

public class Encounter3Man extends Encounter {

    public Encounter3Man(Player player, Story story) {
        super(player, story);
        this.story = story;
        this.player = player;
        random = new Random();
    }

    @Override
    public void startEncounter() {
        story.addStory("You proceed through the forest. Dry needles crunch underfoot.\n"
                + "The path continues for a while. The trees begin to thin out before stopping abruptly.\n"
                + "Ahead is a massive clearing with no fallen leaves and no grass.\n"
                + "The bare dirt rises in a slump before sprouting into a dying old cedar.\n\n"
                + "Do you \"approach\" or return \"south\"?");
    }

    @Override
    public void uniqueCommands(String command) {
        if (command.equals("approach")) {
            approach();
        } else if (command.equals("south")) {
            travel(2);
        } else if (command.equals("sleep")) {
            sleep();
        } else if (command.equals("look")) {
            look();
        }
    }

    @Override
    public void queryCombatEnd(Monster defeatedMonster) {
        story.addStory("You're not supposed to get into a fight here.");
    }

    //Effects: prints out the approach towards the dead cedar.
    public void approach() {
        if (player.checkItem("heart")) {
            story.addStory("The man is gone. All you can do is head \"south\"");
        } else if (player.checkMemory("a burning heart")) {
            story.addStory("You approach the tree and scramble up the hill.\n"
                    + "\"Welcome back.\" The Hanged Man croons.\n "
                    + "\"Have we met before? You seem awfully familiar.\" His face extends into a grin.\n"
                    + "\"Don't be shy. Stay a while.\"\n"
                    + "You can head \"south\" the way you came, or \"sleep\" under the tree.");
        } else if (player.checkMemory("hanged man")) {
            story.addStory("\"You're back,\" the hanged man whistles.\n"
                    + "\"Have you decided to rest your legs for a bit?\""
                    + "You can head \"south\" the way you came, or \"sleep\" under the tree.");
        } else {
            firstHangedManScene();
        }
    }

    //Effects: introduces the hanged man if the player has never seen them before, this persists between deaths.
    public void firstHangedManScene() {
        story.clearStory();
        story.addStory("You approach the tree. The well packed path gives way to loose dirt and mud.\n"
                + "As you climb up it, you're forced to pull yourself along with your hands and feet.\n"
                + "The tree is dry and a sickly shape, but it is absent the silver rot "
                + "that has infected the rest of the wood.\n"
                + "Suddenly, you hear a voice from above you.\n"
                + "It's high pitched and crackling, as if the speaker is constantly on the cusp of laughter.\n"
                + "A man drops down from the tree. His heads snaps downward and smashes "
                + "against the side of the cedar. You notice his left leg is pierced through with cord.\n"
                + "The rest of his body dangles limply.\n"
                + "Oh... look at this. You're in quite the state aren't you?\" he whispers."
                + "\"Why don't you rest for a bit, take a load off. There's no shame in "
                + "taking a minute to collect your thoughts.\"\n"
                + "You can \"sleep\" or you can head \"south\", far, far away from this place.");
        player.addMemory("hanged man");
    }

    //Modifies: player, inventory
    //Effects: add burning heart to player's inventory
    public void sleep() {
        if (player.checkItem("heart")) {
            story.addStory("You try to sleep, but the pain in your chest is too much.");
        } else if (player.checkMemory("a burning heart")) {
            story.addStory("\"Let's practice some deep breathing. Focus on the sound of my voice.\"\n"
                    + "You close your eyes and rest.\n"
                    + "\"Quiet now, I don't want it finding you.\"\n"
                    + "You awaken. The heart is freed from its prison. It's time to go.");
            player.addItem("heart");
        } else if (!player.checkItem("heart") && !player.checkMemory("a burning heart")) {
            firstHeartScene();
        }
    }

    public void firstHeartScene() {
        story.clearStory();
        story.addStory("You sit down beneath the branches and close your eyes.\n"
                + "There's nothing wrong with taking a breather,\" the Hanged Man croons.\n"
                + "\"The Buddha learned much, sleeping under a fig tree,\" he pauses, "
                + "\"Not to say you're the type.\"\n\"I'll figure out something to do with you.\"\n"
                + "You awaken. There's a hole cut in your chest. You can see your heart beating "
                + "openly from it.\n"
                + "Everytime it pumps, and immense and searing pain sets your nerves aflame.\n"
                + "But somehow, you can stand. You senses are sharper than they've ever been and you feel"
                + " an overwhelming urge to sprint towards the tree-line and set the whole forest on fire.");
        player.addItem("heart");
        if (!player.checkMemory("a burning heart")) {
            player.addMemory("a burning heart");
        }
    }

    //Effects: prints out surroundings.
    public void look() {
        if (player.checkItem("heart")) {
            story.addStory("The cedar is lonely amidst the clearing. It's clearly dead, but the rot\n"
                    + "hasn't touched it. The Hanged Man is nowhere to be found. It's time to go south.");
        } else if (player.checkMemory("hanged man")) {
            story.addStory("The cedar is lonely amidst the clearing. It's clearly dead, but the rot\n"
                    + "hasn't touched it. The Hanged Man dangles from the tree and chuckles at you.");
        } else {
            story.addStory("The cedar is lonely amidst the clearing. It's clearly dead, but the rot\n"
                    + "hasn't touched it.");
        }
    }
}
