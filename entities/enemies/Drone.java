package entities.enemies;

import entities.bullets.Bullet;
import game.*;
import java.awt.*;

/**
 *
 * @author lnoza
 */
public class Drone extends Sprite implements Movable {
    public int idX, idY, bulletTimer;
    private int regenTimer;
    private boolean attack, reset;
    private Sprite target;
    
    public boolean isAttacking() {return attack;}
    
    public Drone(int x, int y) {
        hp = 3;
        damageCooldown = 10;    
        
        attack = false;
        target = Gameplay.getPlayer();
        
        bulletTimer = 300;
        regenTimer = 150;
        
        xPos = x;
        yPos = y;
        
        width = 16;
        height = 16;        
    }
    
    @Override
    public int getID() {return 2;}
    
    @Override
    public void draw(Graphics g) {
        
        g.setColor(getColor());
        g.fillRect(xPos, yPos, width, height);
        if (attack) {
            moveAttack();
        } else if (!attack) {
            regenHP();
            shoot();
        }
        setBounds();
        if (bulletTimer > 0) bulletTimer-=Math.random();
        deathEvent();
        
    }
    
    private void reset() {
        yPos = 0;
        reset = true;
    }
    
    private void setBounds() {
        if (yPos > Gameplay.HEIGHT) reset();
        if (reset && yPos == 40+idY*50) attack = false;
        
        if (xPos < 0) xPos = Gameplay.WIDTH-1;
        if (xPos > Gameplay.WIDTH) xPos = 1;
    }
    
    private void regenHP() {
        if (regenTimer > 0 && hp < 3) regenTimer--;
        if(regenTimer == 0 && hp < 3) {
            hp++;
            regenTimer = 180;
        }
    }
    
    public void setAttack(boolean tf) {
        attack = tf;
    }
    
    public void shoot() {
        if (bulletTimer == 0 && Math.random() < 0.0012) {
            Gameplay.addSprite(new Bullet(this));
            bulletTimer = 300;
        }
    }
    
    public void orderShoot() {
        Gameplay.addSprite(new Bullet(this));
    }
    public Color getColor() {
        if (!attack) 
            switch (hp) {
                case 3: return Color.blue;
                case 2: return Color.cyan;
                case 1: return Color.pink;
            }
        if (attack)
            switch (hp) {
                case 3: return Color.magenta;
                case 2: return Color.orange;
                case 1: return Color.yellow;
            }
        return Color.magenta;
    }
    
    @Override
    public void collisionEvent(Sprite sprite) {
        switch (sprite.getID()) {
            case -1: 
                damageEvent();
                break;
            case -5: 
                if (hp > 1 && !attack) 
                    damageEvent(); 
                break;
        }
    }
    
    public void setTarget(Sprite sprite) {
        target = sprite;
    }
    
    public Sprite getTarget() {
        return target;
    }
    
    public void moveAttack() {
        boolean playerLeft = target.xPos > xPos;
        boolean playerRight = target.xPos < xPos;
        boolean playerAbove = target.yPos > yPos;
        moveDown();
        if (playerLeft && playerAbove) moveRight();
        if (playerRight && playerAbove) moveLeft();
    }

     @Override
    public void moveLeft() {
        xPos-=getSpeed();
    }

    @Override
    public void moveRight() {
        xPos+=getSpeed();
    }

    @Override
    public void moveUp() {
        yPos-=3*getSpeed();
    }

    @Override
    public void moveDown() {
        yPos+=3*getSpeed();
    }

    @Override
    public int getSpeed() {
        return (int) Gameplay.PM();
    }
    
    
    
}
