package entities;

import entities.bullets.*;
import game.*;
import java.awt.*;

/**
 *
 * @author 191135Alamillo
 */
public class Player extends Sprite implements Movable {
    public boolean slow, bomb, wave;
    private int blink, bulletTimer, waveTimer;
    
    public Player() {
        hp = 3;
        energy = 0;
        damageCooldown = 200;
        xPos = (Gameplay.WIDTH-width)/2;
        yPos = (Gameplay.HEIGHT-height)*3/4;
        
        width = 18;
        height = 18;
        blink = 0;
    }
    
    @Override
    public int getID() {return 1;}  
    
    
    @Override
    public void draw(Graphics g) {
        if (wave) shootWave();
        if (energy > 300) energy = 300;
        if (energy < 0) energy = 0;
        damageBlink(g);         
        setBounds();
        deathEvent();
        tickTimers();
        drainEnergy();  
    }
    
    private void damageBlink(Graphics g) {
        int blinkTimer = 28;
        if (damageTimer > 0) blink++;
        if (damageTimer == 0 || blink >= blinkTimer) drawPlayer(g);
        if (blink > 2*blinkTimer) blink = 0;
    }
   
    private void drawPlayer(Graphics g) {
        if (hp > 0) {
            g.setColor(Color.white);
            g.fillRect(xPos, yPos, width, height);
        } else {
            deathEvent();
        }
    }
    
    private void tickTimers() {
        if (bulletTimer > 0) bulletTimer--;
        if (waveTimer >  0) waveTimer--;
    }
    
    private void drainEnergy() {
        if (energy > 0 && bomb) energy--;
        if (energy <= 0) bomb = false;
    }
    
    private void setBounds() {
        int xBound = Gameplay.WIDTH-width-10;
        if (xPos < 0) xPos = 0;
        if (xPos > xBound) xPos = xBound;
        
        int yBound = Gameplay.HEIGHT-height-30;
        if (yPos < 0) yPos = 0;
        if (yPos > yBound) yPos = yBound;
    }
    
    private void setBounds(Sprite sprite) {
        if (xPos < sprite.xPos) moveLeft();
        if (xPos > sprite.xPos) moveRight();
        if (yPos > sprite.yPos) moveDown();
        if (yPos < sprite.yPos) moveUp();
    }
    
    @Override
    public void collisionEvent(Sprite sprite) {
        switch (sprite.getID()) {
            case 2:
            case 6:
                if (!bomb) damageEvent();
                setBounds(sprite);   
                break;
            case -2:
            case -6:
                if (!bomb) damageEvent();
                break;
            case 3:
                damageEvent();
                break;
            case -4:
                if(energy > 0) energy-=2;
                break;
        }    
    }  
    
    public void slow(boolean yn) {
        if (!yn) {
            slow = false;
        } else {
            slow = true;
        }
    }
    
    public void shoot() {
        if (bulletTimer == 0 && !bomb) {
            Gameplay.addSprite(new Bullet(this));
            bulletTimer = 16;
        }
    }
    
    public void shootBomb() {
        if (!bomb && energy >= 300) {
            bomb = true;
            Gameplay.addSprite(new Beam(this));
        }
    }
    
    public void shootWave() {
        if (waveTimer == 0) {
            waveTimer = 100;
            energy-=3;
            Gameplay.addSprite(new Wave(this));
        }
    }

    @Override
    public void moveLeft() {
        xPos -= getSpeed();
    }

    @Override
    public void moveRight() {
        xPos += getSpeed();
    }

    @Override
    public void moveUp() {
        yPos -= getSpeed();
    }

    @Override
    public void moveDown() {
        yPos += getSpeed();
    }
    
    @Override
    public int getSpeed() {
        if (!slow) return (int) (Gameplay.PM()*4);
        return (int) (Gameplay.PM()*2);
    }
    
}
