package Utilities;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by nuro on 4/3/16.
 */

public class Sound {

    private String path;

    private AudioInputStream ais;
    private Clip clip;

    public Sound(String path) {
        this.path = path;
        try {
            clip = AudioSystem.getClip();
            ais = AudioSystem.getAudioInputStream(new File(path));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void Play() {
        clip.start();
    }

    public void Loop(){

        try {
            clip.open(ais);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }catch (IOException | LineUnavailableException ex){

            System.out.println(ex.getMessage());
        }
    }

    public void Stop(){
        clip.stop();
    }
}
