package GameState;

import Engine.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// This class passes the level to the GameStateManager and he pass it to the GamePanel for drawing the background.
// If the game remains in its current state and the plan isn`t changed this will replace GameStateManager
// and will just generate different background images with Math.random() and switch statement.
public class GameStateBackground {
    BufferedImage image;

    private int width = GameScreen.WIDTH;
    private int height = GameScreen.HEIGHT;

    private int x;
    private int y;

    public GameStateBackground(String path) {
        try {
            this.image = ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g) {
        int imageWidth = this.image.getWidth(null);
        int imageHeight = this.image.getHeight(null);
        this.x = (width - imageWidth ) / 2;          // center the image
        this.y = (height - imageHeight) / 2;         // in its container

        g.drawImage(this.image, this.x, this.y, null);
    }
}
