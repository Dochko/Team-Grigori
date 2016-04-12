package GameState;

import java.awt.*;

// Initially implemented for different game screens in one JPanel. For the moment it is used only for the level classes.
public abstract class GameState {
    protected GameStateManager gsm;

    protected abstract void init();

    protected abstract void update();

    protected abstract void draw(Graphics2D g);
}
