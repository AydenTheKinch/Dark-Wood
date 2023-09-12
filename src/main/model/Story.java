package model;

public class Story {
    protected String story;

    public Story() {
        story = "";
    }

    //Modifies: this
    //Effects: Add given text to story.
    public void addStory(String string) {
        String tale = string + "\n";
        story += tale;
    }

    //Modifies: this
    //Effects: Clear story.
    public void clearStory() {
        story = "";
    }

    public String getString() {
        return story;
    }
}
