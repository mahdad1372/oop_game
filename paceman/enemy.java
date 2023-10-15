package paceman;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Timer;
import java.awt.Graphics2D;


public class enemy  {
    private int enemy_x;
    private int  enemy_y;
    private Image enemy;
    private int enemy_2x=300;
    private int  enemy_2y = 300;
    private int  width ;
    private int  height ;
    private boolean display = true;
    private boolean increase_x = true;
    private boolean increase_y = true;
    private int enemy_number;
    public enemy (Image img, int x, int y,int w , int h, int n){
            this.enemy_x = x;
            this.enemy_y = y;
            this.enemy = img;
            this.width = w;
            this.height = h;
            this.enemy_number = n;
    }
    public void set_display_enemy(Boolean display){
        if (display == false){
            this.width = 0;
            this.height = 0;
        }
    }

    public int getPosition_enemy_x() {
        return enemy_x;
    }
    public int get_enemy_number() {
        return enemy_number;
    }

    public int getPosition_enemy_y() {
        return enemy_y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Image getImage_enemy() {
        return enemy;
    }
    public void enemy_movement(int curr_position,int final_position ,String direction){
        if (direction == "y"){
            if (this.enemy_y > final_position && increase_y == true){
                this.enemy_y -=1;
            }
            else if (this.enemy_y == final_position && increase_y == true && this.enemy_y < curr_position){
                increase_y = false;
                this.enemy_y +=1;
            }
            else if (this.enemy_y > final_position && increase_y == false && this.enemy_y < curr_position){
                this.enemy_y +=1;
            }
            else if  (this.enemy_y < final_position && increase_y == true){
                this.enemy_y +=1;
            } else if (this.enemy_y == final_position) {
                increase_y = false;
                this.enemy_y -=1;
            }else if (this.enemy_y > curr_position && increase_y == false) {
                this.enemy_y -=1;
            }else if (this.enemy_y == curr_position && increase_y == false) {
                increase_y = true;
            }
        } else if (direction == "x") {
            if (this.enemy_x > final_position && increase_x == true){
                this.enemy_x -=1;
            } else if (this.enemy_x == final_position && this.enemy_x < curr_position && increase_x == true){
                this.increase_x = false;
                this.enemy_x +=1;
            }else if (increase_x == false && this.enemy_x < curr_position ){
                this.enemy_x +=1;
            }
            else if (increase_x == false && this.enemy_x == curr_position && this.enemy_x > final_position ){
                this.enemy_x -=1;
                increase_x = true;
            }


        }
    }
    public void set_Position_enemy_x(int x) {
        this.width = x;
        this.height=x;
    }

    public void draw_enemy(Graphics2D g2d, int x_position , int y_position){

        g2d.drawImage(enemy,x_position,y_position,30,30, (ImageObserver) this);


    }


}
