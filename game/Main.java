package game;

import javax.swing.JFrame;
import music.Music;

/**
 *
 * @author 191135Alamillo
 */
public class Main {
    private static Gameplay gameplay;
    private static int currentLevel;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        initGameplay();
        playMusic();
    }
    
    public static void initGameplay() {
        JFrame obj = new JFrame("Rocket Man");
        gameplay = new Gameplay();
        obj.setBounds(10, 10, Gameplay.WIDTH, Gameplay.HEIGHT);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setLocationRelativeTo(null);
        obj.add(gameplay);
    }
    
    private static void playMusic() {
        while(true) playSong();
    }
    
    private static void playSong() {
        switch (currentLevel) {
            case 1:
                Music.getSound("src/music/resource/project4.wav");
                try {
                    Thread.sleep(270000);
                } catch (InterruptedException ex) {}
                break;
        }
    }
    
    public static void setLevel(int level) {
        currentLevel = level;
    }
    
}
