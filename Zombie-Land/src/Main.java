import Engine.StartScreen;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new StartScreen().setVisible(true));
        // TODO: Mandatory - OOP code optimization (if we got time), Perks or some kind of skill system;
        // TODO: Whats left - game map objects and obstacles (very very very very optional);
    }
}
