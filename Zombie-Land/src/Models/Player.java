package Models;

import Engine.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player{
    private BufferedImage image;
    private String path = "Resources/player.png";

    private int width;
    private int height;
    private Rectangle playerBorder;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private int x;
    private int y;
    private double angle;

    private int dx;
    private int dy;

    private boolean firing = false;
    private long firingTimer = System.nanoTime();
    private long firingDelay = 100; // firing speed

    private boolean isDead;

    private int speed;
    private double health;

    public Player() {
        try {
            image = ImageIO.read(new File(this.path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);

        this.x = GameScreen.WIDTH / 2;
        this.y = GameScreen.HEIGHT / 2;

        this.dx = 0;
        this.dy = 0;
        this.speed = 5;
        this.health = 100;
        this.isDead = false;

        this.playerBorder = new Rectangle(this.x, this.y, this.width, this.height);
    }

    public void setLeft(boolean b) {
        this.left = b;
    }

    public void setRight(boolean b) {
        this.right = b;
    }

    public void setUp(boolean b) {
        this.up = b;
    }

    public void setDown(boolean b) {
        this.down = b;
    }

    public int getX() {return this.x;}

    public int getY() {return this.y;}

    public int getHealth() {
        return (int)this.health;
    }

    public Rectangle getPlayerBorder() {
        return this.playerBorder;
    }

    public boolean isDead() {
        return this.isDead;
    }

    public void setAngle(double angle) { this.angle = angle; }

    public void setFiring(boolean b) { this.firing = b; }

    public void hit() {
        this.health -= 10f / 20f;
        // System.out.println(this.health);
        if (health <= 0) {
            this.health = 0;
            this.isDead = true;
        }
    }

    public void update() {
        if (this.left) {
            this.dx = -this.speed;
        }
        if (this.right) {
            this.dx = this.speed;
        }
        if (this.up) {
            this.dy = -this.speed;
        }
        if (this.down) {
            this.dy = this.speed;
        }

        this.x += this.dx;
        this.y += this.dy;

        if (this.x > GameScreen.WIDTH - this.width) {
            this.x = GameScreen.WIDTH - this.width;
        } else if (this.x < 0) {
            this.x = 0;
        }

        if (this.y > GameScreen.HEIGHT - this.height) {
            this.y = GameScreen.HEIGHT - this.height;
        } else if (this.y < 0) {
            this.y = 0;
        }

        this.playerBorder.x = this.x;
        this.playerBorder.y = this.y;

        this.dx = 0;
        this.dy = 0;

        if(this.firing) {
            int random = (int)((Math.random() * 11) -8);
            long elapsed = (System.nanoTime() - this.firingTimer) / 1000000;
            if(elapsed > this.firingDelay) {
                GameScreen.projectiles.add(new Projectiles((this.angle - 90) + random, x + (this.width / 2), y + (this.height / 2)));
                firingTimer = System.nanoTime();
            }
        }
    }

    public void draw(Graphics2D g) throws IOException {
        // Both methods work nearly the same way

        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        g.rotate(Math.toRadians(this.angle), this.x + (this.width / 2), this.y + (this.height / 2));
        g.drawImage(this.image, this.x, this.y, null);
        g.setTransform(reset);


        /*
        AffineTransform backup = g.getTransform();
        AffineTransform trans = new AffineTransform();

        trans.rotate(Math.toRadians(this.angle), x + (image.getWidth(null) - 21), y + (image.getHeight(null) - 12));
        g.transform(trans);
        g.drawImage(image, x, y, null);  // the actual location of the sprite

        g.setTransform(backup); // restore previous transform
        */
    }
}
