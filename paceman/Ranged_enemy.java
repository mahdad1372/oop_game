package paceman;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class Ranged_enemy extends enemy {

    int Shooting_position_x;
    int Shooting_position_y;
    public Ranged_enemy(Image img, int x, int y, int w, int h, int n) {

        super(img, x, y, w, h, n);
    }


    @Override
    public Image getImage_enemy() {
        return super.getImage_enemy();
    }

    @Override
    public int getPosition_enemy_x() {
        return super.getPosition_enemy_x();
    }

    @Override
    public int getPosition_enemy_y() {
        return super.getPosition_enemy_y();
    }

    @Override
    public int getWidth() {
        return super.getWidth();
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }


}
