package paceman;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Ranged_enemy extends enemy {

    int Shooting_position_x;
    int Shooting_position_y;
    public Ranged_enemy(Image img, int x, int y, int w, int h, int n) {
        super(img, x, y, w, h, n);
    }
    public void drawEnemy(Graphics g){
        g.drawImage(super.getImage_enemy(),super.getPosition_enemy_x() ,super.getPosition_enemy_y(),
                super.getWidth(),super.getHeight(), (ImageObserver) this);
    }


}
