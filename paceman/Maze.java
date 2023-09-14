package paceman;

import java.awt.*;

public class Maze {
    private int Maze_x;
    private int  Maze_y;
    private int  width ;
    private int  height ;
    public Maze ( int x, int y,int w , int h){
        this.Maze_x = x;
        this.Maze_y = y;
        this.width = w;
        this.height = h;
    }
    public int getPosition_maze_x() {
        return Maze_x;
    }


    public int getPosition_maze_y() {
        return Maze_y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
