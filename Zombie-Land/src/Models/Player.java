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

public class Player{
    private ArrayList<BufferedImage> spritesMoveGun;
    private ArrayList<BufferedImage> spritesMoveShotgun;
    private ArrayList<BufferedImage> spritesMoveGauss;
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

    private ArrayList<Projectiles> bullets;
    private boolean firing = false;
    private long firingTimer = System.nanoTime();
    private long firingDelay = 100; // firing speed
    private int weaponType;
    private int shotgunAmmo;
    private int gaussAmmo;

    private boolean isDead;

    private int speed;
    private double health;

    //Player Sounds
    private Sound player_grunt = new Sound("sound/Player/grunt.wav");
    private Sound player_shoot_ak74 = new Sound("sound/Guns/ak74-shoot.wav");
    private Sound player_shoot_gauss = new Sound("sound/Guns/Rifle-Shoot.wav");
    private Sound player_shoot_shotgun = new Sound("sound/Guns/Shotgun-Shoot.wav");

    public Player() {
        this.spritesMoveGun = new ArrayList<>();
        this.spritesMoveShotgun = new ArrayList<>();
        this.spritesMoveGauss = new ArrayList<>();
        this.spritesDead = new ArrayList<>();

        try {
            this.spritesMoveGun.add(ImageIO.read(new File("Resources/Models/Player/movingWithGunOne0.png")));
            this.spritesMoveGun.add(ImageIO.read(new File("Resources/Models/Player/movingWithGunOne1.png")));
            this.spritesMoveGun.add(ImageIO.read(new File("Resources/Models/Player/movingWithGunOne2.png")));
            this.spritesMoveGun.add(ImageIO.read(new File("Resources/Models/Player/movingWithGunOne3.png")));

            this.spritesMoveShotgun.add(ImageIO.read(new File("Resources/Models/Player/movingWithGunTwo0.png")));
            this.spritesMoveShotgun.add(ImageIO.read(new File("Resources/Models/Player/movingWithGunTwo1.png")));
            this.spritesMoveShotgun.add(ImageIO.read(new File("Resources/Models/Player/movingWithGunTwo2.png")));
            this.spritesMoveShotgun.add(ImageIO.read(new File("Resources/Models/Player/movingWithGunTwo3.png")));

            this.spritesMoveGauss.add(ImageIO.read(new File("Resources/Models/Player/movingWithGunThree0.png")));
            this.spritesMoveGauss.add(ImageIO.read(new File("Resources/Models/Player/movingWithGunThree1.png")));
            this.spritesMoveGauss.add(ImageIO.read(new File("Resources/Models/Player/movingWithGunThree2.png")));
            this.spritesMoveGauss.add(ImageIO.read(new File("Resources/Models/Player/movingWithGunThree3.png")));

            this.spritesDead.add(ImageIO.read(new File("Resources/Models/Player/dieing0.png")));
            this.spritesDead.add(ImageIO.read(new File("Resources/Models/Player/dieing1.png")));
            this.spritesDead.add(ImageIO.read(new File("Resources/Models/Player/dieing2.png")));
            this.spritesDead.add(ImageIO.read(new File("Resources/Models/Player/dieing3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.animator = new Animator(this.spritesMoveGun);
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

        this.bullets = new ArrayList<>();
        this.shotgunAmmo = 100;
        this.gaussAmmo = 10;
        this.weaponType = 1;

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

    public void addHealth(int health) {
        if (this.health < 100) {
            this.health += health;
            if (this.health > 100) {
                this.health = 100;
            }
        }
    }

    public int getShotgunAmmo() { return this.shotgunAmmo; }

    public int getGaussAmmo() { return this.gaussAmmo; }

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

    public void setWeaponType(int weaponType) {
        this.weaponType = weaponType;
    }

    public int getWeaponType() {
        return this.weaponType;
    }

    public void addShotgunAmmo(int ammo) {
        this.shotgunAmmo += ammo;
    }

    public void addGaussAmmo(int ammo) {
        this.gaussAmmo += ammo;
    }

    public void hit(double damageTaken) {
        //Sound effects during hit
        player_grunt.setVolumeDown(10f);
        player_grunt.Play();

        this.health -= damageTaken / 10f;
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
        this.animator.update(System.currentTimeMillis());
    }

    public void update() {
        if (this.isDead()) {
            this.animator.update(System.currentTimeMillis());
        }

        if(weaponType == 1){
            this.animator.setFrames(this.spritesMoveGun);
        }

        if(weaponType == 2){
            this.animator.setFrames(this.spritesMoveShotgun);
        }

        if(weaponType == 3){
            this.animator.setFrames(this.spritesMoveGauss);
        }


        if (this.left && !this.isDead()) {
            this.dx = -this.speed;
            this.animator.update(System.currentTimeMillis());
        }

        if (this.right && !this.isDead()) {
            this.dx = this.speed;
            this.animator.update(System.currentTimeMillis());
        }

        if (this.up && !this.isDead()) {
            this.dy = -this.speed;
            this.animator.update(System.currentTimeMillis());
        }

        if (this.down && !this.isDead()) {
            this.dy = this.speed;
            this.animator.update(System.currentTimeMillis());
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

        this.switchWeapon();
    }

    private void switchWeapon() {
        switch (weaponType) {
            // Default weapon
            case 1:
                if (this.firing) {
                    int firingSpread = (int) ((Math.random() * 11) - 8);
                    long elapsed = (System.nanoTime() - this.firingTimer) / 1000000;
                    this.firingDelay = 200;
                    if (elapsed > this.firingDelay) {
                        Projectiles bullet = new Projectiles((this.angle - 90) + firingSpread, x + (this.width / 2), y + (this.height / 2), "Resources/Models/Projectiles/bullet.png");
                        bullet.setBulletDamage(2);
                        GameScreen.projectiles.add(bullet);
                        firingTimer = System.nanoTime();

                        player_shoot_ak74.setVolumeDown(20f);
                        player_shoot_ak74.PlayGunSound();
                    }
                }
                break;
            // Shotgun
            case 2:
                if (this.firing && shotgunAmmo > 0) {
                    ArrayList<Integer> randomAngles = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        int firingSpread = (int) ((Math.random() * 30) - 25);
                        randomAngles.add(firingSpread);
                    }

                    long elapsed = (System.nanoTime() - this.firingTimer) / 1000000;
                    this.firingDelay = 1000;
                    if (elapsed > this.firingDelay) {
                        for (int i = 0; i < 10; i++) {
                            bullets.add(new Projectiles((this.angle - 90) + randomAngles.get(i), x + (this.width / 2), y + (this.height / 2), "Resources/Models/Projectiles/bullet.png"));
                        }

                        for (Projectiles bullet : bullets) {
                            bullet.setSpeed(5);
                            bullet.setBulletDamage(1);
                            GameScreen.projectiles.add(bullet);
                        }

                        this.firingTimer = System.nanoTime();
                        this.shotgunAmmo -= 10;

                        player_shoot_shotgun.setVolumeDown(13f);
                        player_shoot_shotgun.PlayGunSound();
                    }
                }
                break;
            // Gauss rifle
            case 3:
                if (this.firing && this.gaussAmmo > 0) {
                    int firingSpread = (int) ((Math.random() * 5) - 3);
                    long elapsed = (System.nanoTime() - this.firingTimer) / 1000000;
                    this.firingDelay = 600;
                    if (elapsed > this.firingDelay) {
                        Projectiles bullet = new Projectiles((this.angle - 90) + firingSpread, x + (this.width / 2), y + (this.height / 2), "Resources/Models/Projectiles/bullet.png");
                        bullet.setBulletDamage(10);
                        bullet.setSpeed(40);
                        GameScreen.projectiles.add(bullet);
                        firingTimer = System.nanoTime();
                        this.gaussAmmo--;


                        player_shoot_gauss.setVolumeDown(12f);
                        player_shoot_gauss.PlayGunSound();
                    }
                }
                break;
            default:
                break;
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