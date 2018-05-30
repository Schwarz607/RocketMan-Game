package music;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {
    
    public static void getSound(String pathSound) {
        try {
            AudioInputStream audioInputStream =
            AudioSystem.getAudioInputStream(new File(pathSound));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            NoMusic();
        }
    }
    
    private static void NoMusic() {
        System.out.println("no music");
    }
}
