package entities.bullets;

import game.*;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author 191135Alamillo
 */
public class Flash extends Sprite {
    
    public Flash() {
        hp = 10;
        xPos = 0;
        yPos = 0;
        width = Gameplay.WIDTH;
        height = Gameplay.HEIGHT;
    }
    
    @Override
    public int getID() {
        return -10;
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(getRandomColor());
        g.fillRect(xPos, yPos, width, height);
        if (hp > 0) hp--;
        deathEvent();
    }
    
    
}
