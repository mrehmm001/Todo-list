package assets;

import java.awt.*;

public enum Category {
    RED(new Color(255,0,0)),
    WHITE(new Color(255,255,255)),
    BLUE(new Color(0,0,255)),
    PURPLE(new Color(128,0,128)),
    YELLOW(new Color(255,255,0)),
    GREEN(new Color(0,255,0));


    private Color colour;

    Category(Color colour) {
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }
}
