package entities.enemies;

import entities.bullets.Wave;
import java.awt.Graphics;
import java.awt.Color;
import game.*;

/**
 *
 * @author 191135Alamillo
 */
public class Bomber extends Drone {
    
    public Bomber(int idPos) {
        super(0, Gameplay.HEIGHT/2);
        idY = idPos;
        hp = 6;
        width = 18;
        height = 18;
        
        damageCooldown = 2;
        setAttack(true);
    }
    
    @Override
    public int getID() {return 3;}
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(xPos, yPos, width, height);
        drawOutline(g);
        deathEvent();
        yPos = getMovePattern();
        moveRight();
        setBounds();
    }
    
    @Override
    public int getSpeed() {
        return 2;
    }
    
    @Override
    public void deathEvent() {
        if (hp <= 0) Gameplay.removeSprite(this);
        if (hp <= 0 && !atEdge()) {
            Gameplay.addScore(this);
            Gameplay.addSprite(new Wave(this));
        }
    }
    
    private void setBounds() {
        if (atEdge()) hp = 0;
    }
    
    private boolean atEdge() {
        return xPos > Gameplay.WIDTH;
    }
    
    private int getMovePattern() {
        int amp = 140;
        if (idY%2==0) amp = -amp;
        
        double trueX = (double) xPos;
        double fSin = (amp*Math.sin(trueX/80));
        
        return (int) Math.round(fSin + Gameplay.HEIGHT/2);
    }
    
    private void drawOutline(Graphics g) {
        if (hp > 1) {
            int colorHP = 75 + hp*30;
            g.setColor(new Color(colorHP, colorHP, colorHP));
            g.drawRect(xPos, yPos, width, height);
        }
    }
}
