package Models;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bonus {
    BufferedImage image;
    Rectangle bonusBorder;
    private int width;
    private int height;

    int type;

    int x;
    int y;
    private double angle;

    int bonusGiven;

    public Bonus(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = (int) (Math.random() * 3);

        this.bonusType(type);

        this.width = image.getWidth();
        this.height = image.getHeight();

        this.bonusBorder = new Rectangle(this.x, this.y, this.width, this.height);
    }

    public Rectangle getBonusBorder() {
        return this.bonusBorder;
    }

    public int getBonusGiven() {
        return this.bonusGiven;
    }

    public int getType() {
        return this.type;
    }

    private void bonusType(int type) {
        switch (type) {
            // Health bonus
            case 0:
                try {
                    this.image = ImageIO.read(new File("Resources/Models/Bonuses/health.png"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                this.bonusGiven = 25;
                break;
            // Shotgun ammo
            case 1:
                try {
                    this.image = ImageIO.read(new File("Resources/Models/Bonuses/shotgunAmmo.png"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                this.bonusGiven = 50;
                break;
            // Gauss ammo
            case 2:
                try {
                    this.image = ImageIO.read(new File("Resources/Models/Bonuses/gaussAmmo.png"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                this.bonusGiven = 5;
                break;
            // no bonus
            default:
                break;
        }
    }

    public void update() {
        this.angle++;
    }

    public void draw(Graphics2D g) {
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        g.rotate(Math.toRadians(this.angle), this.x + (this.width / 2), this.y + (this.height / 2));
        g.drawImage(image, this.x, this.y, this.width, this.height, null);
        g.setTransform(reset);
    }
}
