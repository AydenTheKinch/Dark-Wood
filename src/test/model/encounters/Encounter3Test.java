package model.encounters;

import model.GameModule;
import model.Player;
import model.Story;
import model.monsters.MonsterFly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Encounter3Test {
    GameModule module;
    Player player = new Player();
    Story story = new Story();
    Encounter encounter;

    @BeforeEach
    void setup() {
        module = new GameModule(player, story);
        module.addEncounters();
        player.setPosition(3);
        encounter = module.getGameMap().get(3);
    }

    @Test
    void testStartEncounter() {
        encounter.startEncounter();
        assertEquals("You proceed through the forest. Dry needles crunch underfoot.\n"
                + "The path continues for a while. The trees begin to thin out before stopping abruptly.\n"
                + "Ahead is a massive clearing with no fallen leaves and no grass.\n"
                + "The bare dirt rises in a slump before sprouting into a dying old cedar.\n\n"
                + "Do you \"approach\" or return \"south\"?\n", story.getString());
    }

    @Test
    void testSouth() {
        encounter.uniqueCommands("south");
        assertEquals(2, player.getPosition());
    }

    @Test
    void testApproach() {
        encounter.uniqueCommands("approach");
        assertEquals("You approach the tree. The well packed path gives way to loose dirt and mud.\n"
                + "As you climb up it, you're forced to pull yourself along with your hands and feet.\n"
                + "The tree is dry and a sickly shape, but it is absent the silver rot "
                + "that has infected the rest of the wood.\n"
                + "Suddenly, you hear a voice from above you.\n"
                + "It's high pitched and crackling, as if the speaker is constantly on the cusp of laughter.\n"
                + "A man drops down from the tree. His heads snaps downward and smashes "
                + "against the side of the cedar. You notice his left leg is pierced through with cord.\n"
                + "The rest of his body dangles limply.\n"
                + "Oh... look at this. You're in quite the state aren't you?\" he whispers."
                + "\"Why don't you rest for a bit, take a load off. There's no shame in taking a minute to collect your "
                + "thoughts.\"\n"
                + "You can \"sleep\" or you can head \"south\", far, far away from this place.\n", story.getString());
    }

    @Test
    void testApproachWithHangedMemory() {
        player.addMemory("hanged man");
        encounter.uniqueCommands("approach");
        assertEquals("\"You're back,\" the hanged man whistles.\n"
                + "\"Have you decided to rest your legs for a bit?\""
                + "You can head \"south\" the way you came, or \"sleep\" under the tree.\n", story.getString());
    }

    @Test
    void testApproachWithHeart() {
        player.addItem("heart");
        encounter.uniqueCommands("approach");
        assertEquals("The man is gone. All you can do is head \"south\"\n",story.getString());
    }

    @Test
    void testApproachWithHeartMemory() {
        player.addMemory("a burning heart");
        encounter.uniqueCommands("approach");
        assertEquals("You approach the tree and scramble up the hill.\n"
                + "\"Welcome back.\" The Hanged Man croons.\n "
                + "\"Have we met before? You seem awfully familiar.\" His face extends into a grin.\n"
                + "\"Don't be shy. Stay a while.\"\n"
                + "You can head \"south\" the way you came, or \"sleep\" under the tree.\n", story.getString());
    }

    @Test
    void testSleep() {
        encounter.uniqueCommands("sleep");
        assertEquals("You sit down beneath the branches and close your eyes.\n"
                + "There's nothing wrong with taking a breather,\" the Hanged Man croons.\n"
                + "\"The Buddha learned much, sleeping under a fig tree,\" he pauses, "
                + "\"Not to say you're the type.\"\n\"I'll figure out something to do with you.\"\n"
                + "You awaken. There's a hole cut in your chest. You can see your heart beating "
                + "openly from it.\n"
                + "Everytime it pumps, and immense and searing pain sets your nerves aflame.\n"
                + "But somehow, you can stand. You senses are sharper than they've ever been and you feel"
                + " an overwhelming urge to sprint towards the tree-line and set the whole forest on fire.\n"
        , story.getString());
        assertTrue(player.checkItem("heart"));
    }

    @Test
    void testSleepWithHeart() {
        player.addItem("heart");
        player.addMemory("a burning heart");
        encounter.uniqueCommands("sleep");
        assertEquals("You try to sleep, but the pain in your chest is too much.\n", story.getString());
    }

    @Test
    void testSleepWithBurningMemory() {
        player.addMemory("a burning heart");
        encounter.uniqueCommands("sleep");
        assertEquals("\"Let's practice some deep breathing. Focus on the sound of my voice.\"\n"
                + "You close your eyes and rest.\n"
                + "\"Quiet now, I don't want it finding you.\"\n"
                + "You awaken. The heart is freed from its prison. It's time to go.\n", story.getString());
    }

    @Test
    public void testQueryCombatEnd() {
        encounter.queryCombatEnd(new MonsterFly());
        assertEquals("You're not supposed to get into a fight here.\n", story.getString());
    }

    @Test
    void testLook() {
        encounter.uniqueCommands("look");
        assertEquals("The cedar is lonely amidst the clearing. It's clearly dead, but the rot\n"
                + "hasn't touched it.\n", story.getString());
    }

    @Test
    void testLookWithHeart() {
        player.addItem("heart");
        player.addMemory("hanged man");
        encounter.uniqueCommands("look");
        assertEquals("The cedar is lonely amidst the clearing. It's clearly dead, but the rot\n"
                + "hasn't touched it. The Hanged Man is nowhere to be found. It's time to go south.\n",
                story.getString());
    }

    @Test
    void testLookWithHangedMemory() {
        player.addMemory("hanged man");
        encounter.uniqueCommands("look");
        assertEquals("The cedar is lonely amidst the clearing. It's clearly dead, but the rot\n"
                + "hasn't touched it. The Hanged Man dangles from the tree and chuckles at you.\n", story.getString());
    }

}
