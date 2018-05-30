package game;

import java.awt.*;

/**
 *
 * @author 191135Alamillo
 */
public class Sprite {
    
    public int xPos, yPos, width, height, hp, damageTimer, damageCooldown, energy;
    
    public int getID() {return 0;}
    
    public Sprite(int x, int y) {
        hp = 10;
        damageCooldown = 100;
        
        xPos = x;
        yPos = y;
        
        width = 20;
        height = 20;
    }
    
    public Sprite() {
        hp = 10;
        xPos = 100;
        yPos = 100;
        
        width = 20;
        height = 20;
    }
    
    public boolean collides(Sprite sprite) {
        Rectangle thisBounds = new Rectangle(this.xPos, this.yPos, this.width, this.height);
        Rectangle spriteBounds = new Rectangle(sprite.xPos, sprite.yPos, sprite.width, sprite.height);
        return thisBounds.intersects(spriteBounds);
    }
    
    public void collisionEvent(Sprite sprite) {
        switch (sprite.getID()) {
            case -1: damageEvent();
        }      
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.drawRect(xPos, yPos, width, height);
        deathEvent();
    }
    
    public void damageEvent() {
        if (damageTimer == 0) {
            damageTimer = damageCooldown;
            hp--;
        }
    }
    
    public void deathEvent() {
        if (damageTimer > 0) damageTimer--;
        if (hp <= 0) {
            Gameplay.removeSprite(this);
            Gameplay.addScore(this);
        }
    }
    
    public static Color getRandomColor() {
        Color[] colors = {Color.pink, Color.red, Color.white, 
            Color.yellow, Color.green, Color.cyan, 
            Color.blue, Color.magenta};
        return colors[(int) (Math.random() * 8)];
    }
}
