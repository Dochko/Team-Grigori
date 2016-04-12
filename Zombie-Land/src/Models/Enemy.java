package Models;

import Engine.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class Enemy {
    BufferedImage image;
    String normalZombiePath = "Resources/zombie.png";


    private int width;
    private int height;
    private Rectangle enemyBorder;

    private int x;
    private int y;
    private double rad;
    private double angle;

    private double dx;
    private double dy;

    private int speed;
    private int health;
    private int type;

    private boolean isReady;
    private boolean isDead;

    public Enemy(int type) {
        this.type = type;

        // Normal zombie
        switch (type) {
            case 1:
                try {
                    this.image = ImageIO.read(new File(this.normalZombiePath));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                this.speed = 2;
                this.health = 2;
                break;
            default:
                break;
        }

        this.width = image.getWidth();
        this.height = image.getHeight();

        int spawningPointLocations = (int) (Math.random() * 4);
        switch (spawningPointLocations) {
            case 0:
                this.x = (int) (Math.random() * GameScreen.WIDTH / 2 + GameScreen.WIDTH / 4);
                this.y = -this.height;
                break;
            case 1:
                this.x = -this.width;
                this.y = (int) (Math.random() * GameScreen.HEIGHT / 2 + GameScreen.HEIGHT / 4);
                break;
            case 2:
                this.x = (int) (Math.random() * GameScreen.WIDTH / 2 + GameScreen.WIDTH / 4);
                this.y = GameScreen.HEIGHT + this.height;
                break;
            case 3:
                this.x = GameScreen.WIDTH + this.width;
                this.y = (int) (Math.random() * GameScreen.HEIGHT / 2 + GameScreen.HEIGHT / 4);
                break;
            default:
                break;
        }

        this.isReady = false;
        this.isDead = false;

        this.enemyBorder = new Rectangle(this.x, this.y, this.width, this.height);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public Rectangle getEnemyBorder() {
        return this.enemyBorder;
    }

    public boolean isDead() {
        return this.isDead;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void hit() {
        if(this.isReady) {
            this.health--;
            if (health <= 0) {
                this.isDead = true;
            }
        }
    }

    public void update() {
        this.rad = (int) Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
        this.x += (int) (dx / rad * speed);
        this.y += (int) (dy / rad * speed);

        this.enemyBorder.x = this.x;
        this.enemyBorder.y = this.y;

        if (!this.isReady) {
            if (this.x > 0 && this.x < GameScreen.WIDTH - this.width &&
                    this.y > 0 && this.y < GameScreen.HEIGHT - this.height) {
                this.isReady = true;
            }
        }
    }

    public void draw(Graphics2D g) {
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        g.rotate(Math.toRadians(this.angle), this.x + (this.width / 2), this.y + (this.height / 2));
        g.drawImage(this.image, this.x, this.y, null);
        g.setTransform(reset);
    }
}
