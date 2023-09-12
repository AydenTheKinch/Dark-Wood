package persistence;

import model.Player;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// This code was adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Player read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        Player player = new Player();
        addItems(player, jsonObject);
        addMemories(player, jsonObject);
        setHealth(player, jsonObject);
        setPosition(player, jsonObject);
        return player;
    }

    // MODIFIES: player
    // EFFECTS: parses items from JSON object and adds them to player's inventory
    private void addItems(Player p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");
        for (Object json : jsonArray) {
            JSONObject item = (JSONObject) json;
            addItem(p, item);
        }
    }

    // MODIFIES: player
    // EFFECTS: parses string from JSON array and adds it to player's inventory
    private void addItem(Player p, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        p.addItem(name);
    }

    // MODIFIES: player
    // EFFECTS: parses experiences(items) from JSON object and adds them to player's memory.
    private void addMemories(Player p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("memory");
        for (Object json : jsonArray) {
            JSONObject experience = (JSONObject) json;
            addMemory(p, experience);
        }
    }

    //MODIFIES: player
    //EFFECTS: parses experience from JSON array and adds it to player's memory.
    private void addMemory(Player p, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        p.addMemory(name);
    }

    //MODIFIES: player
    //EFFECTS: parses health from JSON object and sets player health to the value.
    private void setHealth(Player p, JSONObject jsonObject) {
        int health = jsonObject.getInt("health");
        p.setHealth(health);
    }

    //MODIFIES: player
    //EFFECTS: parses position from JSON object and sets player position to the value
    private void setPosition(Player p, JSONObject jsonObject) {
        int position = jsonObject.getInt("position");
        p.setPosition(position);
    }
}

