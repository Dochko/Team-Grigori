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
    private ArrayList<BufferedImage> spritesMove;
    private ArrayList<BufferedImage> spritesDie;
    private Animator animator;

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
        this.spritesDie = new ArrayList<>();

        // Enemy Types
        enemyTypes(type);

        this.width = this.animator.sprite.getWidth();
        this.height = this.animator.sprite.getHeight();

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

    private void enemyTypes(int type) {
        switch (type) {
            // Normal zombie
            case 1:
                try {
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/NormalZombie/normalZombieMoving0.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/NormalZombie/normalZombieMoving1.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/NormalZombie/normalZombieMoving2.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/NormalZombie/normalZombieMoving3.png")));

                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/NormalZombie/normalZombieDieing0.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/NormalZombie/normalZombieDieing1.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/NormalZombie/normalZombieDieing2.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/NormalZombie/normalZombieDieing3.png")));

                    this.animator = new Animator(this.spritesMove);
                    this.animator.setSpeed(200);
                    this.animator.start();

                    animator.update(System.currentTimeMillis());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                this.speed = 2;
                this.health = 3;
                break;
            case 2:
                try {
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Dog/dogMove0.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Dog/dogMove1.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Dog/dogMove2.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Dog/dogMove3.png")));

                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Dog/dogDieing0.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Dog/dogDieing1.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Dog/dogDieing2.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Dog/dogDieing3.png")));

                    this.animator = new Animator(this.spritesMove);
                    this.animator.setSpeed(200);
                    this.animator.start();

                    animator.update(System.currentTimeMillis());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                this.speed = 5;
                this.health = 2;
                break;
            case 3:
                try {
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/AdvancedZombie/AdvancedZombieMove0.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/AdvancedZombie/AdvancedZombieMove1.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/AdvancedZombie/AdvancedZombieMove2.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/AdvancedZombie/AdvancedZombieMove3.png")));

                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/AdvancedZombie/AdvancedZombieDieing0.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/AdvancedZombie/AdvancedZombieDieing1.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/AdvancedZombie/AdvancedZombieDieing2.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/AdvancedZombie/AdvancedZombieDieing3.png")));

                    this.animator = new Animator(this.spritesMove);
                    this.animator.setSpeed(200);
                    this.animator.start();

                    animator.update(System.currentTimeMillis());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                this.speed = 3;
                this.health = 4;
                break;
            case 4:
                try {
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Boss/BossMove0.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Boss/BossMove1.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Boss/BossMove2.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Boss/BossMove3.png")));

                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Boss/BossDieing0.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Boss/BossDieing1.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Boss/BossDieing2.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Sprites/EnemySprites/Boss/BossDieing3.png")));

                    this.animator = new Animator(this.spritesMove);
                    this.animator.setSpeed(200);
                    this.animator.start();

                    animator.update(System.currentTimeMillis());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                this.speed = 2;
                this.health = 20;
                break;
            default:
                break;
        }
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
                GameScreen.deadEnemiesCounter--;
                this.isDead = true;
                this.DeadEnemy();
            }
        }
    }

    private void DeadEnemy(){
        this.animator = new Animator(this.spritesDie);
        this.animator.setSpeed(200);
        this.animator.start();
        animator.update(System.currentTimeMillis());
    }

    public void update() {
        if(!this.isDead()){
            this.rad = (int) Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
            this.x += (int) (dx / rad * speed);
            this.y += (int) (dy / rad * speed);

            this.animator.update(System.currentTimeMillis());

            this.enemyBorder.x = this.x;
            this.enemyBorder.y = this.y;
        }else{
            if(animator.isDoneAnimating()){
                animator.pause();
            }else{
                this.animator.update(System.currentTimeMillis());
            }
        }

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
        g.drawImage(this.animator.sprite, this.x, this.y, this.width, this.height, null);
        g.draw(enemyBorder);
        g.setTransform(reset);
    }


}
