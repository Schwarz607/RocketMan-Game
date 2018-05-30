package stages;
import entities.enemies.Drone;
import game.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author 191135Alamillo
 */
public class Formation extends Sprite {
    private Drone map[][];
    
    private int orderCount, orderIndex, orderRate, hard, rows, cols;
    private Collection<Drone> graveYard = new ArrayList<>();
    
    public Formation(int difficulty) {
        hard = difficulty;
        rows = hard + 2;
        cols = 26;
        map = new Drone[cols][rows];
        xPos = 90;
        yPos = 40;
        width = 780;
        height = 150;
        orderCount = 0;
        orderIndex = 0;
        addSprites();
    }
    
    @Override
    public int getID() {return -3;} 
    
    @Override
    public void draw(Graphics g) {
        //loop through to send "orders"
        orderCount++;
        orderRate++;
        if (graveYard.size() == cols*rows) Gameplay.removeSprite(this);
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Drone drone = map[i][j];
                boolean canOrder = !drone.isAttacking() && orderRate >= (120-hard*20);
                if (!Gameplay.getSprites().contains(drone) && !graveYard.contains(drone)) {
                    graveYard.add(drone);
                }
                if (canOrder && Math.random() < 0.1) {                    
                    doOrder(drone, "attack");
                } else if (!drone.isAttacking()) {
                    doOrder(drone, "idle");
                }
            //drawID(g,i,j); 
            }
        }
    }
    
    private void doOrder(Drone sprite, String order) {
        switch (order) {
            case "idle":
                moveIdle(sprite);
                setIndex(30, 3);
                break;
            case "attack":
                sprite.setAttack(true);
                orderRate = 0;
                break;
        }
    }
    
    private void setIndex(int countLimit, int indexLimit) {
        if (orderCount > countLimit) {
            orderCount = 0;
            orderIndex++;
        }
        if (orderIndex > indexLimit) {
            orderIndex = 0;
        }
    }
    
    private void moveIdle(Drone sprite) {
        switch (orderIndex) {
            case 0:
            case 3:
               if (sprite.idY % 2 == 0) {
                   sprite.moveRight();
               } else {
                   sprite.moveLeft();
               }
               break;
            case 1:
            case 2:
               if (sprite.idY % 2 == 0) {
                   sprite.moveLeft();
               } else {
                   sprite.moveRight();
               }
               break;
        }
    }
    
    private void addSprites() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Drone drone = new Drone(i*30+90, 40+j*50);
                map[i][j] = drone;
                drone.idX = i; drone.idY = j;
                Gameplay.addSprite(drone);
            }
        }   
    }
    
    @Override
    public void collisionEvent(Sprite sprite) {}
    
    
}
