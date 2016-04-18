package Utilities;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {


    private AudioInputStream ais;
    private Clip clip;
    private FloatControl gainControl;

    public Sound(String path) {
        try {
            this.clip = AudioSystem.getClip();
            this.ais = AudioSystem.getAudioInputStream(new File(path));
            this.clip.open(this.ais);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public void setVolumeDown(float vol){
        gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-vol);
    }

    public void setVolumeUp(float vol){
        gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(vol);
    }

    public void Play() {
        if(!this.clip.isRunning()){
            this.clip.setFramePosition(0);
            this.clip.start();
        }
    }
    public void PlayOnce(){
        this.clip.start();
    }

    public void PlayGunSound(){
        this.clip.setFramePosition(0);
        this.clip.start();
    }

    public void Stop(){
        this.clip.stop();
        this.clip.setFramePosition(0);
    }

    public void Close(){
        this.clip.stop();
        this.clip.close();
    }

    public void Loop(){
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
