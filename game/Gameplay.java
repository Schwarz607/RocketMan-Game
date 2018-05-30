package game;

import entities.*;
import entities.enemies.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JPanel;
import javax.swing.Timer;
import stages.*;

/**
 *
 * @author 191135Alamillo
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play, dead;
    private final Collection<String> moveDir = new ArrayList<>();
    private static final ArrayList<Sprite> gameSprites = new ArrayList<>();
    
    private static final double performanceMultiplier = 1;
    public static double PM() {return performanceMultiplier;}
    
    private final Timer timer;
    private static Player player;
    private static Stage stage;
    
    private int inputLag, blinkTimer;
    private static int score, level;
    
    public static Sprite getPlayer() {return player;}
    
    public static Collection<Sprite> getSprites() {return gameSprites;}
    
    public static void addSprite(Sprite sprite) {
        gameSprites.add(0, sprite);
    }
    
    public static void addScreenSprite(Sprite sprite) {
        gameSprites.add(gameSprites.size()-1, sprite);
    }
    
    public static void addBossSprite(Sprite sprite) {
        gameSprites.add(gameSprites.size(), sprite);
    }
    
    public static void removeSprite(Sprite sprite) {
        gameSprites.remove(sprite);
    }
    
    private static void clearSprites() {
        gameSprites.removeAll(gameSprites);
    }
    
    public static final int WIDTH=960;
    public static final int HEIGHT=720;
    
    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(8, this);
        timer.start();
        play = false;
        
        score = 0; 
        level = 1;
        Main.setLevel(level);
    }
    
    public static void addScore(Sprite sprite) {
        switch (sprite.getID()) {
            case 2:
                score+=10;
                break;
            case 3:
                score+=20;
                break;
            case 6:
                score+=200;
        }
    }
    
    private void drawSprites(Graphics g) {
        stage.tick();
        for(int i = 0; i < gameSprites.size(); i++) {
            Sprite sprite = gameSprites.get(i); 
            if (sprite != null) sprite.draw(g);
        }
    }
    
    @Override
    public void paint(Graphics g) {
        if (inputLag > 0) inputLag--;
        if (blinkTimer < 100) blinkTimer++;
        if (blinkTimer >= 100) blinkTimer = 0;
        moveInputs();
        
        //BG
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);        
        
        if (play) {
            if (stage.bossIsDead()) {
                nextStage();
                stage.setFinish(true);
            }
            drawSprites(g);
            drawHealth(g);
            drawScore(g);
        } else if (dead) {
            drawDeathScreen(g);
        } else {
            drawTitleScreen(g);
        }
        if (play) {
            if (!gameSprites.contains(player)) gameOver();
        }
    }
    
    private void drawTitleScreen(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Impact", Font.PLAIN, 48));
        switch (level) {
            case 1:
                g.drawString("Rocket Man", WIDTH/2-120, 100);
                g.setFont(new Font("Impact", Font.PLAIN, 24));
                if (blinkTimer < 50) g.drawString("PRESS ANY KEY", WIDTH/2-70, 500);
                break;
            case 2:
                g.drawString("Joey Defeated!", WIDTH/2-140, 100);
                g.setFont(new Font("Impact", Font.PLAIN, 24));
                if (blinkTimer < 50) g.drawString("PRESS TO CONTINUE", WIDTH/2-85, 500);
                break;
        }
    }
    
    private void drawDeathScreen(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Impact", Font.PLAIN, 48));
        g.drawString("GAME OVER", WIDTH/2-120, 100);
        g.setFont(new Font("Impact", Font.PLAIN, 24));
        g.drawString("Score: " + score, WIDTH/2-80, HEIGHT/2-200);
        if (blinkTimer < 50) g.drawString("PRESS KEY FOR RESTART", WIDTH/2-100, 500);
    }
    
    private void startGame(int difficulty) {
        if (!play && !dead) {
            if (gameSprites.contains(player)) removeSprite(player);
            
            score = 0;
            play = true;
            player = new Player();
            stage = new Stage(difficulty);
            addSprite(player);
            if (difficulty == 1)
                addScreenSprite(new Tutorial(player));
        }
        if (dead) {
            inputLag = 20;
            dead = false;
        }
    }
    
    private void gameOver() {
        inputLag = 100;
        play = false;
        dead = true;
        clearSprites();
    }
    
    private void nextStage() {
        level++;
        inputLag = 100;
        play = false;
        clearSprites();
        Main.setLevel(level);
        
    }
    
    private void drawHealth(Graphics g) {
        //player health
        int start = Gameplay.WIDTH - 78;
        for (int i = 0; i < player.hp; i++) {
            g.setColor(Color.red);
            g.fillRect(start + i*24, 4, 20, 20);
        }
        g.setColor(getEnergyColor());
        g.fillRect(start, 26, (player.energy*3)/13, 4);
        
        //boss health
        if (stage.hasActiveBoss()) {
            g.setColor(Color.white);
            g.fillRect(100, 4, (stage.getBossHealth()*15)/4, 4);
        }
    }
    
    private void drawScore(Graphics g) {
        g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        g.drawString("Score: " + score, 2, 10);
    }
    
    private Color getEnergyColor() {
        Color[] colors = {Color.pink, Color.red, Color.white, 
                          Color.yellow, Color.green, Color.cyan, 
                          Color.blue, Color.magenta};
        if (player.energy >= 300 || player.bomb) return colors[(int) (Math.random() * 8)];
        int whitenR = (player.energy*255)/300;
        int whitenG = 180 + (player.energy*75)/300;
        int whitenB = 180 +(player.energy*75)/300;
        return new Color(whitenR, whitenG, whitenB);
    }

    private void moveInputs() {
        for (String dir : moveDir) {
            if (dir != null) {
                switch (dir) {
                    case "up":
                        player.moveUp();
                        break;
                    case "left":
                        player.moveLeft();
                        break;
                    case "down":
                        player.moveDown();
                        break;
                    case "right":
                        player.moveRight();
                        break;
                    case "shoot":
                        player.shoot();
                        break;
                    case "bomb":
                        player.shootBomb();
                        break;
                }
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        //nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (inputLag == 0) {
            startGame(level);
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:    
                    if (!moveDir.contains("up"))
                        moveDir.add("up");
                    break;
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    if (!moveDir.contains("left"))
                        moveDir.add("left");
                    break;
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    if (!moveDir.contains("down"))
                        moveDir.add("down");
                    break;
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    if (!moveDir.contains("right"))
                        moveDir.add("right");
                    break;
                case KeyEvent.VK_SHIFT:
                    player.slow(true);
                    break;
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_Z:
                    if (!moveDir.contains("shoot"))
                        moveDir.add("shoot");
                    break;
                case KeyEvent.VK_X:
                    if (!moveDir.contains("bomb"))
                        moveDir.add("bomb");
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP: 
                moveDir.remove("up");
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                moveDir.remove("left");
                break;            
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                moveDir.remove("down");
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:    
                moveDir.remove("right");
                break;
            case KeyEvent.VK_SHIFT:
                //moveDir.remove("slow");
                player.slow(false);
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_Z:
                moveDir.remove("shoot");
                break;
            case KeyEvent.VK_X:
                moveDir.remove("bomb");
                break;
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        
        for (int i = 0; i < gameSprites.size(); i++) {
            for (int j = 0; j < gameSprites.size(); j++) {
                Sprite spriteA = gameSprites.get(i);
                Sprite spriteB = gameSprites.get(j);
                if (spriteA != null && spriteB != null && spriteA != spriteB)
                    if (spriteA.collides(spriteB))
                    spriteA.collisionEvent(spriteB);                
            }
        }
        
        repaint();
    }
    
}
