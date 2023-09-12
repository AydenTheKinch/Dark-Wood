package ui;

import log.Event;
import log.EventLog;
import model.GameModule;
import model.Player;
import model.Story;
import model.encounters.Encounter;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GameAppGUI extends JFrame implements ActionListener {
    private JLabel areaLabel;
    private JLabel areaPicture;
    private JTextField field;
    private JTextArea inventory;
    private JTextArea narrative;

    private BufferedImage defaultImage;
    private BufferedImage rotStreamImage;
    private BufferedImage swarmOfBugsImage;
    private BufferedImage throneImage;
    private BufferedImage deadTreeImage;
    private BufferedImage deadKingImage;
    private BufferedImage flyMonsterImage;
    private BufferedImage fairyImage;

    private JProgressBar healthbar;
    private JPanel interfacePanel;
    private JPanel environmentPanel;
    private JPanel playerPanel;
    private JScrollPane narrativePanel;

    private static final String JSON_STORE = "./data/darkwood.json";
    private JsonWriter jsonWriter;
    private ArrayList<String> inventoryItems;
    private Player player;
    private GameModule module;
    private Encounter currentEncounter;
    private Story story;
    private int position;
    private int health;
    private EventLog log = EventLog.getInstance();
    private ArrayList<Encounter> gameMap;


    //Effects: Creates GUI with multiple Panels and input field.
    public GameAppGUI(GameModule module) {
        super("A Dark Wood");
        this.module = module;
        jsonWriter = new JsonWriter(JSON_STORE);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setPreferredSize(MainMenu.SCREEN_DIMENSION);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new BorderLayout());

        initializeFromModule();

        createInterfacePanel();
        createPlayerPanel();
        createNarrativePanel();
        createEnvironmentPanel();

        add(environmentPanel, BorderLayout.EAST);
        add(playerPanel, BorderLayout.WEST);
        add(narrativePanel, BorderLayout.CENTER);
        add(interfacePanel, BorderLayout.SOUTH);
        pack();
        createWindowCloseEvent();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //Effects: creates command box and submit button.
    public void createInterfacePanel() {
        interfacePanel = new JPanel();
        interfacePanel.setLayout(new FlowLayout());

        field = new JTextField(20);
        field.addActionListener(this);
        field.setActionCommand("insert");

        JButton submitBtn = new JButton("Submit");
        submitBtn.setActionCommand("enter");
        submitBtn.addActionListener(this);

        interfacePanel.add(field);
        interfacePanel.add(submitBtn);
    }

    //Effects: Creates healthbar and inventory.
    public void createPlayerPanel() {
        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
        createInventory();
        createHealthBar();
        playerPanel.add(healthbar);
        playerPanel.add(Box.createVerticalStrut(20));
        playerPanel.add(inventory);
        JButton saveBtn = new JButton("Save");
        saveBtn.setActionCommand("save");
        saveBtn.addActionListener(this);
        playerPanel.add(saveBtn);
    }

    //Effects: Creates inventory display.
    public void createInventory() {
        inventory = new JTextArea(20, 10);
        inventory.setEditable(false);
        inventory.setLineWrap(true);
        inventory.setWrapStyleWord(true);
        updateInv();
    }

    //Effects: Creates health bar.
    public void createHealthBar() {
        healthbar = new JProgressBar(0, 100);
        healthbar.setPreferredSize(new Dimension(100, 15));
        healthbar.setValue(health);
        healthbar.setStringPainted(true);
        healthbar.setForeground(Color.RED);
    }

    //Effects: creates main narrative panel.
    public void createNarrativePanel() {
        narrative = new JTextArea(5, 45);
        narrativePanel = new JScrollPane(narrative);
        narrative.setEditable(false);
        narrativePanel.setHorizontalScrollBarPolicy(31);
        narrative.setLineWrap(true);
        narrative.setWrapStyleWord(true);
        narrative.setText(story.getString());
    }

    //Effects: creates environment picture and area label.
    public void createEnvironmentPanel() {
        loadImages();
        environmentPanel = new JPanel();
        environmentPanel.setLayout(new BoxLayout(environmentPanel, BoxLayout.PAGE_AXIS));
        areaPicture = new JLabel();
        updatePicture();
        environmentPanel.add(areaPicture);
        environmentPanel.add(Box.createVerticalStrut(30));

        areaLabel = new JLabel("");
        environmentPanel.add(areaLabel);
        setAreaLabel("A dark wood.");
        updateLabel();
    }

    //Effects: converts images into readable fields.
    public void loadImages() {
        try {
            defaultImage = ImageIO.read(new File("data/pictures/darkwood.png"));
            rotStreamImage = ImageIO.read(new File("data/pictures/FrFeia_aAAEEwDk.png"));
            swarmOfBugsImage = ImageIO.read(new File("data/pictures/fly_infested.png"));
            throneImage = ImageIO.read(new File("data/pictures/king_on_throne.png"));
            deadTreeImage = ImageIO.read(new File("data/pictures/the_dead_tree.png"));
            deadKingImage = ImageIO.read(new File("data/pictures/throne_of_a_dead_king.png"));
            fairyImage = ImageIO.read(new File("data/pictures/dark_fairy.png"));
            flyMonsterImage = ImageIO.read(new File("data/pictures/fly_monster.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Effects: sets label to given string.
    public void setAreaLabel(String area) {
        areaLabel.setText(area);
    }

    //Effects: updates label depending on what area the player is in.
    public void updateLabel() {
        if (position == 2) {
            setAreaLabel("The flies buzz incessantly.");
        } else if (position == 3) {
            if (player.checkMemory("hanged man")) {
                setAreaLabel("The Hanged Man");
            } else {
                setAreaLabel("The dead tree looms ahead.");
            }
        } else if (position == 4) {
            setAreaLabel("The stench of rot hangs in the air.");
        } else if (position == 5) {
            if (player.checkMemory("death of the king")) {
                setAreaLabel("The king is dead, cross the abyss.");
            } else {
                setAreaLabel("The king sits on their throne.");
            }
        } else {
            setAreaLabel("A dark wood.");
        }
    }

    //Effects: scales picture and sets picture to given field.
    public void setPicture(Image img) {
        Image scaledImage = img.getScaledInstance(200, 133, Image.SCALE_SMOOTH);
        ImageIcon display = new ImageIcon(scaledImage);
        areaPicture.setIcon(display);
    }

    //Effects: sets initial fields
    public void initializeFromModule() {
        player = module.getPlayer();
        inventoryItems = player.itemsToString();
        story = module.getStory();
        gameMap = module.getGameMap();
        health = player.getHealth();
        position = player.getPosition();
        currentEncounter = gameMap.get(position);
    }

    //Effects: updates field after each command is submitted.
    public void update() {
        player = module.getPlayer();
        inventoryItems = player.itemsToString();
        updateInv();
        story = module.getStory();
        health = player.getHealth();
        healthbar.setValue(health);
        position = player.getPosition();
        currentEncounter = gameMap.get(position);
        updatePicture();
        updateTextArea();
        updateLabel();
    }

    //Effects: sets picture depending on the position of player.
    public void updatePicture() {
        if (position == 2) {
            if (currentEncounter.getInCombat()) {
                setPicture(flyMonsterImage);
            } else {
                setPicture(swarmOfBugsImage);
            }
        } else if (position == 3) {
            setPicture(deadTreeImage);
        } else if (position == 4) {
            if (currentEncounter.getInCombat()) {
                setPicture(fairyImage);
            } else {
                setPicture(rotStreamImage);
            }
        } else if (position == 5) {
            if (player.checkMemory("death of the king")) {
                setPicture(deadKingImage);
            } else {
                setPicture(throneImage);
            }
        } else {
            setPicture(defaultImage);
        }
    }

    //Effects: update inventory area to display current items in players inventory
    public void updateInv() {
        inventory.setText(null);
        for (String item : inventoryItems) {
            inventory.append(item + "\n");
        }
    }

    //Effects: updates narrative panel to display story.
    public void updateTextArea() {
        narrative.setText(story.getString());
    }

    //Effects: performs high level action or sends command to current encounter.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("enter")) {
            String command = field.getText();
            if (command.equals("quit")) {
                printLog(EventLog.getInstance());
                System.exit(0);
            } else {
                currentEncounter.processCommand(command);
                update();
            }
        } else if (e.getActionCommand().equals("save")) {
            if (currentEncounter.getInCombat()) {
                story.addStory("You can't save in combat.");
            } else {
                saveGame();
            }
            updateTextArea();
        }
    }

    public void saveGame() {
        try {
            //Effects: Writes player data to file.
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
            story.addStory("Saved your progress to " + JSON_STORE);
            Event event = new Event("Progress was saved to " + JSON_STORE);
            log.logEvent(event);
        } catch (FileNotFoundException exc) {
            story.addStory("Unable to write to file: " + JSON_STORE);
        }
    }

    public void createWindowCloseEvent() {
        /*Some piece of code*/
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                printLog(EventLog.getInstance());
            }
        });
    }

    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n\n");
        }
    }
}