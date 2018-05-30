package entities.enemies;

import entities.bullets.DirectionalBullet;
import entities.bullets.Flash;
import game.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

/**
 *
 * @author 191135Alamillo
 */
public class HexBoss extends Sprite implements Movable {
    private int entranceCount, blinkCount, moveCount, destinX, destinY;
    private Color color;
    
    public HexBoss() {
        hp = 200;
        damageCooldown = 40;
        entranceCount = 300;
        moveCount = 600;
        
        color = Color.white;
        blinkCount = 10;
        
        width = 10;
        height = 10;
        
        xPos = (Gameplay.WIDTH - width)/2;
        yPos = (Gameplay.HEIGHT - height)/2;
        destinX = (Gameplay.WIDTH - width)/2;
        destinY = (Gameplay.HEIGHT - height)/2;
    }
    
    @Override
    public int getID() {return 6;}
    
    @Override
    public void draw(Graphics g) {
        boolean flashBlink = entranceCount%20 == 0 && entranceCount > 0;
        tickTimers();
        if (flashBlink) blink();
        if (moveCount <= 0) setDestination();
        if (moveCount <= 300) moveTo(destinX, destinY);
        g.setColor(color);
        g.fillPolygon(getHexagon());
        g.setColor(getRandomColor());
        g.drawPolygon(getHexagon());
        setBounds();
        deathEvent();
    }
    
    private void tickTimers() {
        if (damageTimer > 0) damageTimer--;
        if (entranceCount > 0) entranceCount--;
        if (moveCount > 0) moveCount--;
        if (blinkCount > 0) {
            blinkCount--;
            color = Color.black;
        } else {
            color = Color.white;
        }
    }
    
    private void blink() {
        shootFlash();
        blinkCount = 20;
        if (width < 140 && height < 140) {
            width+=10;
            height+=10;
            xPos-=5;
            yPos-=5;
        }
    }
    
    private void setDestination() {
        moveCount = 400;
        entranceCount = 300;
        int edgeLimit = 200;
        destinX = (int) (Math.random() * Gameplay.WIDTH);
        if (destinX < edgeLimit) destinX = edgeLimit;
        if (destinX > Gameplay.WIDTH-edgeLimit) destinX = Gameplay.WIDTH-edgeLimit;
        destinY = (int) (Math.random() * Gameplay.HEIGHT);
        if (destinY < edgeLimit) destinY = edgeLimit;
        if (destinY > Gameplay.HEIGHT/2) destinY = Gameplay.HEIGHT/2;
    }
    
    private Polygon getHexagon() {
        Polygon h = new Polygon();	
        int side = width/2;
	for (int i = 0; i < 6; i++){
            h.addPoint((int) (xPos+width/2 + side * Math.cos(i*2*Math.PI/6)),
	               (int) (yPos+height/2 + side * Math.sin(i*2*Math.PI/6)));
	}
        return h;
    }
    
    public void shootFlash() {
        Gameplay.addScreenSprite(new Flash());
        for (int i = 1; i <= 9; i++) {
            if (i != 4 || i != 5 || i != 6)
                Gameplay.addSprite(new DirectionalBullet(this, i));
        }
    }
    
    @Override
    public void collisionEvent(Sprite sprite) {
        switch (sprite.getID()) {
            case -1:
                damageEvent();
                break;
            case -10:
                color = Color.black;
                break;
        }
        
    }
    
    private void setBounds() {
        if (xPos < 0) xPos = 0;
        if (xPos > Gameplay.WIDTH-width) xPos = Gameplay.WIDTH-width;
        if (yPos < 0) yPos = 0;
        if (yPos > Gameplay.HEIGHT/2) yPos = Gameplay.HEIGHT/2;
    }
    
    private void moveTo(int x2, int y2) {
        if (xPos != x2 && yPos != y2) {
            if (xPos < x2) moveRight();
            if (xPos > x2) moveLeft();
            if (yPos > y2) moveUp();
            if (yPos < y2) moveDown();
        }
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
        return (int) Gameplay.PM()*2;
    }
    
}
