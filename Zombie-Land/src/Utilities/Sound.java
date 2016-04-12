package Utilities;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    private String path;

    private AudioInputStream ais;
    private Clip clip;

    public Sound(String path) {
        this.path = path;
        try {
            this.clip = AudioSystem.getClip();
            this.ais = AudioSystem.getAudioInputStream(new File(path));
            this.clip.open(this.ais);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void Play() {
        this.clip.start();
    }

    public void Stop(){
        this.clip.stop();
    }

    public void Loop(){
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
