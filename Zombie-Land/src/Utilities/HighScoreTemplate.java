package Utilities;

import java.io.Serializable;

/**
 * Created by Yani on 16.4.2016 г..
 */
public class HighScoreTemplate implements Serializable{
    private String name;
    private int wave;
    private int score;

    public HighScoreTemplate(String name, int wave, int score){
        this.name = name;
        this.wave = wave;
        this.score = score;
    }

    public String getName(){
        return this.name;
    }
    public int getWave(){
        return this.wave;
    }
    public int getScore(){
        return this.score;
    }

    public void setName(String name){
        this.name = name;
    }
}
