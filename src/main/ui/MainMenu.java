package ui;

import log.Event;
import log.EventLog;
import model.GameModule;
import model.Player;
import model.Story;
import persistence.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/darkwood.json";
    private JsonReader jsonReader;
    private Player player;
    private GameModule module;
    private Story story;
    public static final Dimension SCREEN_DIMENSION = new Dimension(800, 500);
    private BufferedImage img;

    //Effects: create main menu GUI, this proceeds the game GUI.
    public MainMenu() {
        super("In A Dark Wood");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(SCREEN_DIMENSION);
        setLayout(new BorderLayout());
        createInterfacePanel();
        initializeGame();

        try {
            img = ImageIO.read(new File("data/pictures/darkwood.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(525, 350,
                Image.SCALE_SMOOTH);
        add(new JLabel(new ImageIcon(dimg)), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //Effects: create two buttons the player can press to either save or load the game.
    public void createInterfacePanel() {
        JPanel flowPanel = new JPanel();
        flowPanel.setLayout(new FlowLayout());
        JButton newGameBtn = new JButton("New Game");
        newGameBtn.setActionCommand("new");
        newGameBtn.addActionListener(this);
        JButton loadGameBtn = new JButton("Continue");
        loadGameBtn.setActionCommand("continue");
        loadGameBtn.addActionListener(this);
        flowPanel.add(newGameBtn);
        flowPanel.add(loadGameBtn);
        add(flowPanel, BorderLayout.SOUTH);
    }

    //Effects: initialize the fields.
    public void initializeGame() {
        story = new Story();
        player = new Player();
        jsonReader = new JsonReader(JSON_STORE);
    }

    //Effects: creates a new Game.
    public void newGame() {
        module = new GameModule(player, story);
        module.addEncounters();
        player.setPosition(0);
        module.getGameMap().get(player.getPosition()).startEncounter();
        new GameAppGUI(module);
    }

    // MODIFIES: module, player
    // EFFECTS: loads player from file
    private void loadGame() {
        try {
            EventLog.getInstance().logEvent(new Event("Beginning to load game."));
            this.player = jsonReader.read();
            module = new GameModule(player, story);
            module.addEncounters();
            story.addStory("You wake up.");
            module.getGameMap().get(player.getPosition()).startEncounter();
            EventLog.getInstance().logEvent(new Event("Game successfully loaded."));
            new GameAppGUI(module);
        } catch (IOException e) {
            story.addStory("Unable to read from file: " + JSON_STORE);
        }
    }

    public static void main(String[] args) {
        new MainMenu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("continue")) {
            loadGame();
            dispose();
        } else if (e.getActionCommand().equals("new")) {
            newGame();
            dispose();
        }
    }
}
