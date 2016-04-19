package Models;

import Engine.GameScreen;
import Utilities.Animator;
import Utilities.Sound;

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
    private int collisionWidth;

    private int x;
    private int y;
    private double rad;
    private double angle;

    private double dx;
    private double dy;

    private int speed;
    private int health;
    private double damage;
    private int type;
    private int pointsWorth;
    private boolean droppedBonus;

    private long bossFiringTimer = System.nanoTime();
    private long bossFiringDelay = 1000; // firing speed

    private boolean isReady;
    private boolean isDead;

    private Sound boss_shoot = new Sound("sound/Zombies/boss-shoot.wav");
    //private ArrayList<Sound> zombie_die = new ArrayList<>();
    private Sound zombie_die;

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
        this.droppedBonus = false;

        this.enemyBorder = new Rectangle(this.x, this.y, this.width - this.collisionWidth, this.height);
    }

    private void enemyTypes(int type) {
        switch (type) {
            // Normal zombie
            case 1:
                zombie_die = new Sound("sound/Zombies/zombie-die1.wav");
                zombie_die.setVolumeDown(15f);
                try {
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/NormalZombie/normalZombieMoving0.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/NormalZombie/normalZombieMoving1.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/NormalZombie/normalZombieMoving2.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/NormalZombie/normalZombieMoving3.png")));

                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/NormalZombie/normalZombieDieing0.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/NormalZombie/normalZombieDieing1.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/NormalZombie/normalZombieDieing2.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/NormalZombie/normalZombieDieing3.png")));

                    this.animator = new Animator(this.spritesMove);
                    this.animator.setSpeed(200);
                    this.animator.start();
                    this.collisionWidth = 15;

                    animator.update(System.currentTimeMillis());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                this.speed = 2;
                this.health = 3 * GameScreen.difficult;
                this.damage = 6 * GameScreen.difficult;
                this.pointsWorth = 5;
                break;
            //Zombie Dog
            case 2:
                zombie_die = new Sound("sound/Zombies/zombie-dog-die.wav");
                zombie_die.setVolumeDown(8f);
                try {
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/Dog/dogMove0.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/Dog/dogMove1.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/Dog/dogMove2.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/Dog/dogMove3.png")));

                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/Dog/dogDieing0.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/Dog/dogDieing1.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/Dog/dogDieing2.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/Dog/dogDieing3.png")));

                    this.animator = new Animator(this.spritesMove);
                    this.animator.setSpeed(200);
                    this.animator.start();
                    this.collisionWidth = 20;

                    animator.update(System.currentTimeMillis());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                this.speed = 5;
                this.health = 2 * GameScreen.difficult;
                this.damage = 4 * GameScreen.difficult;
                this.pointsWorth = 15 * GameScreen.difficult;
                break;
            //Advanced Zombie
            case 3:
                zombie_die = new Sound("sound/Zombies/zombie-die2.wav");
                zombie_die.setVolumeDown(15f);
                try {
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/AdvancedZombie/AdvancedZombieMove0.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/AdvancedZombie/AdvancedZombieMove1.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/AdvancedZombie/AdvancedZombieMove2.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/AdvancedZombie/AdvancedZombieMove3.png")));

                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/AdvancedZombie/AdvancedZombieDieing0.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/AdvancedZombie/AdvancedZombieDieing1.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/AdvancedZombie/AdvancedZombieDieing2.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/AdvancedZombie/AdvancedZombieDieing3.png")));

                    this.animator = new Animator(this.spritesMove);
                    this.animator.setSpeed(200);
                    this.animator.start();
                    this.collisionWidth = 15;

                    animator.update(System.currentTimeMillis());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                this.speed = 3;
                this.health = 4 * GameScreen.difficult;
                this.damage = 8 * GameScreen.difficult;
                this.pointsWorth = 20 * GameScreen.difficult;
                break;
            //Zombie Boss
            case 4:
                zombie_die = new Sound("sound/Zombies/zombie-growl1.wav");
                try {
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/Boss/BossMove0.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/Boss/BossMove1.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/Boss/BossMove2.png")));
                    this.spritesMove.add(ImageIO.read(new File("Resources/Models/Enemies/Boss/BossMove3.png")));

                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/Boss/BossDieing0.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/Boss/BossDieing1.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/Boss/BossDieing2.png")));
                    this.spritesDie.add(ImageIO.read(new File("Resources/Models/Enemies/Boss/BossDieing3.png")));

                    this.animator = new Animator(this.spritesMove);
                    this.animator.setSpeed(200);
                    this.animator.start();
                    this.collisionWidth = 15;

                    animator.update(System.currentTimeMillis());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                this.speed = 2;
                this.health = 20 * GameScreen.difficult;
                this.damage = 100 * GameScreen.difficult;
                this.pointsWorth = 50 * GameScreen.difficult;
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

    public double getDamage() { return this.damage; }

    public boolean isDead() {
        return this.isDead;
    }

    public boolean getDroppedBonus() {
        return this.droppedBonus;
    }

    public void setDroppedBonus(boolean b) {
        this.droppedBonus = b;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void hit(int damageTaken) {
        if(this.isReady) {
            this.health -= damageTaken;
            if (health <= 0) {
                GameScreen.deadEnemiesCounter--;
                GameScreen.gameScore += this.pointsWorth;
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

            this.enemyBorder.x = this.x + this.collisionWidth / 2;
            this.enemyBorder.y = this.y;

            if(this.type == 4) {
                int random = (int)((Math.random() * 11) -8);
                long elapsed = (System.nanoTime() - this.bossFiringTimer) / 1000000;
                if(elapsed > this.bossFiringDelay) {
                    GameScreen.enemyProjectiles.add(new Projectiles((this.angle - 90) + random, x, y ,"Resources/Models/Projectiles/Virus.png"));
                    bossFiringTimer = System.nanoTime();
                    boss_shoot.setVolumeDown(8f);
                    boss_shoot.PlayGunSound();
                }
            }
        }else{
            if(animator.isDoneAnimating()){
                animator.pause();
            }else{
                zombie_die.Play();
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
        g.setTransform(reset);
    }


}
