package Utilities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animator {
    private ArrayList<BufferedImage> frames;
    public BufferedImage sprite;
    private volatile boolean running = false;

    private long prevTime;
    private long speed;
    private int frameAtPause;
    private int currentFrame = 0;

    public Animator(ArrayList<BufferedImage> frames) {
        this.frames = frames;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public void update(long time) {
        if(this.running) {
            if(time - this.prevTime >= this.speed) {
                this.currentFrame++;
                try{
                    if(currentFrame < frames.size()) {
                        this.sprite = this.frames.get(this.currentFrame);
                    } else {
                        this.reset();
                        this.sprite = this.frames.get(this.currentFrame);
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                this.prevTime = time;
            }
        }
    }

    public void start() {
        this.running = true;
        this.prevTime = 0;
        this.frameAtPause = 0;
        this.currentFrame = 0;
    }

    public void stop() {
        this.running = false;
        this.prevTime = 0;
        this.frameAtPause = 0;
        this.currentFrame = 0;
    }

    public void pause() {
        this.frameAtPause = this.currentFrame;
        this.running = false;
    }

    public void resume() {
        this.currentFrame = this.frameAtPause;
    }

    public void reset() {
        this.currentFrame = 0;
    }

    public boolean isDoneAnimating() {
        if(this.currentFrame == this.frames.size()) {
            return  true;
        } else {
            return false;
        }
    }
}
