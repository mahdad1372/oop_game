package paceman;

import java.awt.*;
import java.util.Timer;
public class Bullet extends Paceman {
    private int position_x;
    private int position_y;
    private int radius;

    private boolean display_bullet = true;
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
    public void hide_bullet(){
        this.display_bullet = false;
    }

    public int getPosition_x_on_time(String direction) {
        if (direction == "right"){
            return  position_x++;
        } else if (direction == "left") {
            return  position_x--;
        }else if (direction == "up") {
            return  position_y--;
        }else if (direction == "down") {
            return  position_y++;
        }else {
            return position_x++;
        }
    }
}
