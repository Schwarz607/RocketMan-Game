package entities.bullets;

import game.Sprite;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author 191135Alamillo
 */
public class DirectionalBullet extends Bullet {
    private int direction, dSpin;
    private boolean spin = shooter.getID() == 6;    
    
    public DirectionalBullet(Sprite shootFrom, int dir) {
        super(shootFrom);
        direction = dir;
        dSpin = 0;
        xPos = shooter.xPos + shooter.width/2 - 2;
        yPos = shooter.yPos + shooter.height/2 - 2;
        
        width = 4;
        height = 4;
    }
    
    @Override
    public void draw(Graphics g) {
        if (spin) dSpin++;
        g.setColor(Color.white);
        g.fillOval(xPos, yPos, width, height);
        moveInDirection();
        setBounds();
        deathEvent();
    }
    
    private void moveInDirection() {
        switch (direction) {
            case 1:
                moveLeft();
                moveUp();
                xPos+=dSpin/2;
                break;
            case 2:
                moveUp();
                xPos+=dSpin/2;
                break;
            case 3:
                moveRight();
                moveUp();
                yPos+=dSpin/2;
                break;
            case 4: 
                moveLeft();
                yPos-=dSpin/2;
                break;
            case 5:
                hp = 0;
                break;
            case 6:
                moveRight();
                yPos+=dSpin/2;
                break;
            case 7:
                moveLeft();
                moveDown();
                yPos-=dSpin/2;
                break;
            case 8:
                moveDown();
                xPos-=dSpin/2;
                break;
            case 9:
                moveRight();
                moveDown();
                xPos-=dSpin/2;
                break;
        }
    }
    
}
