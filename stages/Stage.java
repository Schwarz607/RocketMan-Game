package stages;

import entities.enemies.*;
import game.*;

public class Stage {
    private Formation stageDrones;
    private double pickupRate, bomberRate;
    private int dropCounter, bombTimer, level;
    
    private Sprite boss;
    private boolean finish;
    
    public Stage(int level) {
        this.level = level;
        stageDrones = new Formation(level);
        Gameplay.addSprite(stageDrones);
        
        dropCounter = 3000;
        bomberRate = level*1000;
        bombTimer = 0;
    }
    
    public void setFinish(boolean n) {finish = n;}
    
    public boolean hasActiveDrones() {
        return Gameplay.getSprites().contains(stageDrones);
    }
    
    public boolean hasActiveBoss() {
        return Gameplay.getSprites().contains(boss);
    }
    
    public int getBossHealth() {return boss.hp;}
    
    public Sprite getBoss() {
        switch (level) {
            case 1: return new HexBoss();
        }
        return boss;
    }
    
    public void tick() {
        if (!finish) {
            if (dropCounter == bomberRate) sendBombers();
            if (dropCounter > 0) dropCounter--;
            if (bombTimer > 0) {
                bombTimer--;
                if (bombTimer % 7 == 0)
                    Gameplay.addSprite(new Bomber(bombTimer));
            }      
            if (dropCounter <= 0) dropCounter = 3000;
            if (!hasActiveBoss() && !hasActiveDrones()) {
                boss = getBoss();
                Gameplay.addBossSprite(boss);
            }
        }
    }
    
    public boolean bossIsDead() {
        return hasActiveBoss() && boss.hp == 0;
    }
    
    
    public void sendBombers() {
        bombTimer = 350;
    }
}
