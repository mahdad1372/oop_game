package paceman;

public class Menu {
    private int Menu_x;
    private int  Menu_y;
    private int  width ;
    private int  height ;
    private String menu_type;
    public Menu ( int x, int y,int w , int h , String menu_type){
        this.Menu_x = x;
        this.Menu_y = y;
        this.width = w;
        this.height = h;
        this.menu_type = menu_type;
    }
    public int getPosition_menu_x() {
        return Menu_x;
    }


    public int getPosition_menu_y() {
        return Menu_y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public String get_menu_type() {
        return menu_type;
    }
}
