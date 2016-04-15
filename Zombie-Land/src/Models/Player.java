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

public class Player{
    private ArrayList<BufferedImage> spritesMove;
    private ArrayList<BufferedImage> spritesDead;
    private Animator animator;

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
        this.spritesMove = new ArrayList<>();
        this.spritesDead = new ArrayList<>();

        try {
            this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/PlayerSprites/movingWithGunOne0.png")));
            this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/PlayerSprites/movingWithGunOne1.png")));
            this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/PlayerSprites/movingWithGunOne2.png")));
            this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/PlayerSprites/movingWithGunOne3.png")));

            this.spritesDead.add(ImageIO.read(new File("Resources/Sprites/PlayerSprites/dieing0.png")));
            this.spritesDead.add(ImageIO.read(new File("Resources/Sprites/PlayerSprites/dieing1.png")));
            this.spritesDead.add(ImageIO.read(new File("Resources/Sprites/PlayerSprites/dieing2.png")));
            this.spritesDead.add(ImageIO.read(new File("Resources/Sprites/PlayerSprites/dieing3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.animator = new Animator(this.spritesMove);
        this.animator.setSpeed(200);
        this.animator.start();
        this.animator.update(System.currentTimeMillis());

        this.width = this.animator.sprite.getWidth();
        this.height = this.animator.sprite.getHeight();

        this.x = GameScreen.WIDTH / 2;
        this.y = GameScreen.HEIGHT / 2;

        this.dx = 0;
        this.dy = 0;
        this.speed = 5;
        this.health = 100;
        this.isDead = false;

        this.playerBorder = new Rectangle(this.x, this.y, this.width-20, this.height - 8);
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

    public boolean getAnimator() {
        return this.animator.isDoneAnimating();
    }

    public void hit() {
        this.health -= 10f / 20f;
        if (health <= 0) {
            this.health = 0;
            this.isDead = true;
            this.deadPlayer();
        }
    }

    private void deadPlayer() {
        this.animator = new Animator(this.spritesDead);
        this.animator.setSpeed(200);
        this.animator.start();
        animator.update(System.currentTimeMillis());
    }

    public void update() {
        if (this.isDead()) {
            animator.update(System.currentTimeMillis());
        }

        if (this.left && !this.isDead()) {
            this.dx = -this.speed;
            animator.update(System.currentTimeMillis());
        }

        if (this.right && !this.isDead()) {
            this.dx = this.speed;
            animator.update(System.currentTimeMillis());
        }

        if (this.up && !this.isDead()) {
            this.dy = -this.speed;
            animator.update(System.currentTimeMillis());
        }

        if (this.down && !this.isDead()) {
            this.dy = this.speed;
            animator.update(System.currentTimeMillis());
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

        this.playerBorder.x = this.x + 10;
        this.playerBorder.y = this.y + 4;

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

    public void draw(Graphics2D g) {
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        g.rotate(Math.toRadians(this.angle), this.x + (this.width / 2), this.y + (this.height / 2));
        g.drawImage(this.animator.sprite, this.x, this.y, this.width, this.height, null);
        g.draw(playerBorder);
        g.setTransform(reset);
    }
}
