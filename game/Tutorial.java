package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author 191135Alamillo
 */
public class Tutorial extends Sprite {
    private Sprite player;
    
    public Tutorial(Sprite sprite) {
        super(sprite.xPos, sprite.yPos);
        hp = 300;
        player = sprite;
    }
    
    @Override
    public void collisionEvent(Sprite sprite){}
    
    @Override
    public void draw(Graphics g) {
        if (hp > 0) hp--;
        deathEvent();
        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        g.drawString("Z - shoot", player.xPos+player.width, player.yPos);
        g.drawString("X - beam (full charge only)", player.xPos+player.width, player.yPos+20);
    }
    
}
