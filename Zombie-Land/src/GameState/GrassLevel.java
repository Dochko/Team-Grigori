package GameState;

import java.awt.*;

// This is currently the only level in the game. If we don`t implement any objects or obstacles in the maps
// this will be removed as code pollution and GameStateBackground will be used for background drawing.
public class GrassLevel extends GameState {
    private GameStateBackground gameStateBackground;
    private String backgroundPath = "Resources/Backgrounds/grass.jpg";

    public GrassLevel(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            gameStateBackground = new GameStateBackground(backgroundPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
        gameStateBackground.draw(g);
    }
}
