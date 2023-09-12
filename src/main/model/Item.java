package model;

import org.json.JSONObject;
import persistence.Writable;

public class Item implements Writable {

    protected boolean ephemeral;
    protected String name;
    protected String description;

    //EFFECTS: Create item with given name and description
    public Item(String name, String description, boolean ephemeral) {
        this.name = name;
        this.description = description;
        this.ephemeral = ephemeral;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        return json;
    }

    //MODIFIES: this
    //EFFECTS: change item name to given string.
    public void changeName(String newName) {
        name = newName;
    }

    //MODIFIES: this
    //EFFECTS: change description to given string.
    public void changeDesc(String newDesc) {
        description = newDesc;
    }

    //MODIFIES: this
    //EFFECTS: set item to immortal. this means it's not removed on player death
    public void makeEphemeral() {
        ephemeral = true;
    }

    //MODIFIES: this
    //EFFECTS: set item to ephemeral. this means it's removed on player death
    public void makeImmortal() {
        ephemeral = false;
    }

    public boolean getEphemeral() {
        return ephemeral;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
