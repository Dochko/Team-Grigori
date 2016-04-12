package GameState;

import java.awt.*;
import java.util.ArrayList;

// The intention of this class was to switch between different game states like levels, menus and so on
// and to pass them to the GamePanel to draw then it in. Currently used only to draw the background.
public class GameStateManager {
    private ArrayList<GameState> gameStates;
    private int currentState;

    public static final int GRASSLEVEL = 0;
    public static final int NORMALDIFICULTYSETTING = 1;

    public GameStateManager() {
        gameStates = new ArrayList<GameState>();

        currentState = 0;
        gameStates.add(new GrassLevel(this));
    }

    public void setState (int state) {
        currentState = state;
        gameStates.get(currentState).init();
    }

    public void update() {
        gameStates.get(currentState).update();
    }

    public void draw (Graphics2D g) {
        gameStates.get(currentState).draw(g);
    }
}
