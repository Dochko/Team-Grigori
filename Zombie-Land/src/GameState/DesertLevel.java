package GameState;

import java.awt.*;

public class DesertLevel extends GameState {
    private GameStateBackground gameStateBackground;
    private String backgroundPath = "Resources/Backgrounds/desert.jpg";

    public DesertLevel(GameStateManager gsm) {
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
