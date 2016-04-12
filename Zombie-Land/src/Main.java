import Engine.StartScreen;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new StartScreen().setVisible(true));
        //TODO: Mandatory - find a way to split the sprites and create animations
        //TODO: Optional - game map objects and obstacles; different weapons and bullets; perks or some kind of skill system
    }
}
