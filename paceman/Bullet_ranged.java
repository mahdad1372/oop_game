package paceman;

public class Bullet_ranged extends Bullet{
    String direction;
    public Bullet_ranged(int position_x, int position_y , int radius,String direction){
        super(position_x,position_y,radius);
        this.direction = direction;
    }

    @Override
    public int getPosition_x() {
        return super.getPosition_x();
    }

    @Override
    public int getPosition_y() {
        return super.getPosition_y();
    }

    @Override
    public int get_radius() {
        return super.get_radius();
    }

    @Override
    public void set_position_x(int x) {
        super.set_position_x(x);
    }

    @Override
    public void set_position_y(int y) {
        super.set_position_y(y);
    }

    @Override
    public void set_radius(int r) {
        super.set_radius(r);
    }
    public String getDirection(){
        return  this.direction;
    }
    public void setDirection(String direction){
        this.direction = direction;
    }
}
