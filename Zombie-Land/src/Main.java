import Engine.StartScreen;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new StartScreen().setVisible(true));
        //TODO: DONE - Mandatory Alpha - find a way to split the sprites and create animations - DONE
        //TODO: Mandatory Beta - DONE - Create the high score and help screens - DONE; Decide what to do with the GameState classes; Different weapons and bullets;
        //TODO: Mandatory Beta - More maps and random map generator; Boss shooting ability and enemies optimization; More sound options and optimization;
        //TODO: Whats left - Perks or some kind of skill system;  game map objects and obstacles (very very very very optional);
    }
}
