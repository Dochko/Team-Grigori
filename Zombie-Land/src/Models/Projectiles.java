package Models;

import Engine.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class Projectiles {
    private BufferedImage image;

    private int width;
    private int height;
    private Rectangle bulletBorder;
    private boolean targetHit;

    private int x;
    private int y;
    private int r;

    private double dx;
    private double dy;
    private double rad;
    private double angle;
    private double speed;
    private int bulletDamage;

    public Projectiles(double angle, int x, int y, String path) {
        try {
            this.image = ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.width = image.getWidth();
        this.height = image.getHeight();

        this.targetHit = false;

        this.x = x;
        this.y = y;
        this.r = this.height;
        this.angle = angle;

        this.rad = Math.toRadians(angle);
        this.speed = 20;
        this.dx = Math.cos(rad) * this.speed;
        this.dy = Math.sin(rad) * this.speed;

        this.bulletBorder = new Rectangle(this.x, this.y, this.width, this.height);
    }

    public Rectangle getBulletBorder() {
        return this.bulletBorder;
    }

    public int getBulletDamage() { return this.bulletDamage; }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setBulletDamage(int damage) {
        this.bulletDamage = damage;
    }

    public void setTargetHit(boolean targetHit){
        this.targetHit = targetHit;
    }
    public boolean getTargetHit(){
        return this.targetHit;
    }

    public boolean update() {
        this.x += this.dx;
        this.y += this.dy;

        this.getBulletBorder().x = this.x;
        this.getBulletBorder().y = this.y;

        if(this.x < -this.r || this.x > GameScreen.WIDTH + this.r ||
        y < -this.r || this.y > GameScreen.HEIGHT + this.r) {
            return true;
        }

        return false;
    }

    public void draw(Graphics2D g) {
        AffineTransform reset = new AffineTransform();
        reset.rotate(0, 0, 0);
        g.rotate(Math.toRadians(this.angle), this.x + (this.width / 2), this.y + (this.height / 2));
        g.drawImage(this.image, this.x, this.y, null);
        g.setTransform(reset);
    }
}
