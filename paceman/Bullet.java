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
    public void set_radius(int r) {
        this.radius =r;
    }
    public void set_position_x(int x) {
        this.position_x =x;
    }
    public void set_position_y(int y) {
        this.position_y =y;
    }

    public int getPosition_x_on_time(String direction) {
//        if (direction.equals("right")){
//             position_x++;
//        } else if (direction.equals("left")) {
//            position_x--;
//        }
        return  position_x++;
    }
}
