package paceman;

import java.util.Timer;
public class Bullet {
    private int position_x;
    private int position_y;
    private int radius;
    Timer t = new java.util.Timer();
    public Bullet(int position_x, int position_y , int radius) {
        this.position_x = position_x;
        this.position_y = position_y;
        this.radius = radius;

    }

    public int getPosition_x() {
        return position_x;
    }

    public int getPosition_y() {
        return position_y;
    }
    public int get_radius() {
        return radius;
    }
    public int getPosition_x_on_time() {

        return position_x++;
    }


}
