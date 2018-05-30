package entities.bullets;

import game.*;
import java.awt.Graphics;

/**
 *
 * @author 191135Alamillo
 */
public class Beam extends Bullet implements Movable {
    private int dxPos, dyPos;
    
    public Beam(Sprite shootFrom) {
        super(shootFrom);
        hp = shooter.energy;
        xPos = shooter.xPos + shooter.width/4;
    }
    
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        hp = shooter.energy;
        if (hp <= 0) Gameplay.removeSprite(this);
        xPos = shooter.xPos + shooter.width/4 + dxPos;
        yPos = shooter.yPos - shooter.height/2 + dyPos;
        
        if (width < 50) {
            dxPos--;
            width+=2;
        }
        setBounds();
    }
    
    @Override
    public void collisionEvent(Sprite sprite) {
        switch (sprite.getID()) {
            case 6:
                moveDown();
                moveDown();
                break;
        }
    }
    
    @Override
    public void setBounds() {
        if (yPos < 0) moveDown();
    }
    
    @Override
    public void moveUp() {
        dyPos-=getSpeed();
        height+=getSpeed();
    }
    
    @Override
    public void moveDown() {
        height-=getSpeed();
        dyPos+=getSpeed();
        
    }
    
    @Override
    public int getSpeed() {
        return super.getSpeed()/2;
    }
    
}
