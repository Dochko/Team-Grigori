package Models;

import Engine.GameScreen;
import Utilities.Animator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Enemy {
    BufferedImage image;
    String normalZombiePath = "Resources/zombie.png";
    //TODO: Enemy test animation init
    private ArrayList<BufferedImage> spritesMove;
    private Animator animatorMove;

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
        this.spritesMove = new ArrayList<>();

        // Enemy Types
        switch (type) {
            // Normal zombie
            case 1:
                //TODO: enemy test animation
                try {
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/NormalZombie/normalZombieMoving0.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/NormalZombie/normalZombieMoving1.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/NormalZombie/normalZombieMoving2.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/NormalZombie/normalZombieMoving3.png")));

                    this.animatorMove = new Animator(this.spritesMove);
                    this.animatorMove.setSpeed(200);
                    this.animatorMove.start();

                    animatorMove.update(System.currentTimeMillis());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*
                try {
                    this.image = ImageIO.read(new File(this.normalZombiePath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                */

                this.speed = 2;
                this.health = 2;
                break;
            default:
                break;
        }

        /*
        this.width = image.getWidth();
        this.height = image.getHeight();
        */

        this.width = this.animatorMove.sprite.getWidth();
        this.height = this.animatorMove.sprite.getHeight();

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

        this.animatorMove.update(System.currentTimeMillis());

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
        g.drawImage(this.animatorMove.sprite, this.x, this.y, this.width, this.height, null);
        g.setTransform(reset);
    }
}
