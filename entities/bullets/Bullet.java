package entities.bullets;

import game.Gameplay;
import game.Movable;
import game.Sprite;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author 191135Alamillo
 */
public class Bullet extends Sprite implements Movable {
    public Sprite shooter;
    private static int spread = 20;
    
    public Bullet(Sprite shootFrom) {
        hp = 1;
        shooter = shootFrom;
        xPos = shooter.xPos + shooter.width/4 + (int)(Math.random()*spread-spread/2);
        yPos = shooter.yPos - shooter.height/2;
        
        width = 8;
        height = 10;
    }
    
    @Override
    public int getID() {return -shooter.getID();}
    
    @Override
    public void draw(Graphics g) {
        if (hp > 0) {
            g.setColor(getColor());
            g.fillRect(xPos, yPos, width, height);
        }
        
        switch (getID()) {
            case -1:
                moveUp();
                break;
            case -2:
                moveDown();
                break;
        }
        setBounds();
        deathEvent();
    }
    
    @Override
    public void collisionEvent(Sprite sprite) {
        switch(getID()) {
            case -1:
                playerSideCollision(sprite);
                break;
            case -2:
                enemySideCollision(sprite);
                break;
        }
    }
    
    private void playerSideCollision(Sprite sprite) {
        switch (sprite.getID()) {
            case 2:
                if (shooter.energy < 300) shooter.energy+=3;
                damageEvent();
                break;
            case 3:
                if (shooter.energy < 300) shooter.energy+=5;
                damageEvent();
                break;
            case 6:
                if(shooter.energy < 300) shooter.energy+=6;
                damageEvent();
                break;
        }
    }
    
    private void enemySideCollision(Sprite sprite) {
        switch (sprite.getID()) {
            case 1:                
                damageEvent();
                break;       
            case -4:
                Gameplay.removeSprite(this);
                break;
        }
    }
    
    private Color getColor() {
        if (shooter.getID() == 2) return Color.green;
        return Color.orange;
    }
    
    public void setBounds() {
        if (yPos < 0 || yPos > Gameplay.HEIGHT) hp = 0;
        if (xPos < 0 || xPos > Gameplay.WIDTH) hp = 0;
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
        yPos-=getSpeed();
    }

    @Override
    public void moveDown() {
        yPos+=getSpeed();
    }

    @Override
    public int getSpeed() {
        if (shooter.getID() == 2) return (int) (Gameplay.PM()*5);
        if (shooter.getID() == 6) return (int) (Gameplay.PM()*9);
        return (int) (Gameplay.PM()*10);
    }
    
}
