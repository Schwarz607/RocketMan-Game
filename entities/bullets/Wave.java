package entities.bullets;
import game.*;
import java.awt.Color;
import java.awt.Graphics;
/**
 *
 * @author 191135Alamillo
 */
public class Wave extends Bullet implements Movable {
    
    public Wave(Sprite shootFrom) {
        super(shootFrom);
        xPos = shooter.xPos;
        yPos = shooter.yPos;
        
        width = shooter.width;
        height = shooter.height;
    }
    
    @Override
    public int getID() {
        if (shooter.getID() == 2) return -4;
        return -5;
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(getRandomColor());
        g.drawRect(xPos, yPos, width, height);
        expand();
    }
    
    @Override
    public void collisionEvent(Sprite sprite) {}
    
    private void expand() {
        
        moveLeft();
        width+=2*getSpeed();
        
        moveUp();
        height+=2*getSpeed();
        
        double widthLimit = Gameplay.WIDTH*1.5;
        if (shooter.getID() == 3) widthLimit = 300;
        double heightLimit = Gameplay.HEIGHT*1.5;
        if (shooter.getID() == 3) heightLimit = 300;
        
        if (width > widthLimit || height > heightLimit)
            Gameplay.removeSprite(this);
    }
    
    @Override
    public int getSpeed() {
        if (shooter.getID() != 1) return 2;
        return super.getSpeed();
    }
    
}
