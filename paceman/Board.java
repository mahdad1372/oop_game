package paceman;

import com.sun.source.tree.WhileLoopTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Board extends JPanel implements KeyListener, ActionListener{

    private Dimension d;
    private final Font smallFont = new Font("Helvetica",Font.BOLD,14);

    private Image ii;
    private int rows = 10;
    private int columns = 10;
    private int cellSize = 20;
    private final Color dotColor = new Color(192,192,0);
    private Color mazeColor;
    private boolean display_menu_winner;

    private boolean Bullet_shooting = false;
    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int PAC_ANIM_DELAY = 2;
    private final int MAX_GHOSTS = 12;
    private final int PACMAN_SPEED = 6;
    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;
    private int N_GHOSTS = 6;
    private int pacsLeft,score;
    private int [] dx,dy;
    private int [] ghost_x,ghost_y,ghost_dx,ghost_dy,ghostSpeed;
    private int imageX = 20;
    private int imageY = 100;
    private int enemy_y = 100;
    private int imageSpeed = 5;
    private Image enemy_icon=new ImageIcon("bad-person.png").getImage();
    private Image pacman1,pacman2up,pacman2left,pacman2right,pacman2down;
    private Image pacman3up,pacman3left,pacman3right,pacman3down;
    private Image pacman4up,pacman4left,pacman4right,pacman4down;
    boolean increase = true;
    private Image player =new ImageIcon("pacman.png").getImage();
    private int bullet_shooting_x = imageX;
    private int bullet_shooting_y = imageY;
    List<Rectangle> rectanglesList = new ArrayList<>();
    List<Integer> xmarze_list = new ArrayList<>();
    List<Integer> ymarze_list = new ArrayList<>();
    ArrayList<Bullet> bullet_position = new ArrayList<Bullet>();
    ArrayList<enemy> enemy_list = new ArrayList<enemy>();
    ArrayList<Maze> Maze_list = new ArrayList<Maze>();
    ArrayList<Menu> Menu_list = new ArrayList<Menu>();
    private final int validSpeeds[] = {1,2,3,4,6,8};
    private final int maxSpeed = 6;
    private int currentSpeed = 3;
    private int scores = 0;
    private short[] screenData;
    private Timer timer;
    private int Health = 100;
    private String direction_player = "left";

    public Board (){
        Menu_list.add(new Menu(230,40,380,250,"winner"));
        initVariables();
        enemy_list.add(new enemy(enemy_icon,200,70,30,30,0));
        enemy_list.add(new enemy(enemy_icon,390,165,30,30,1));
        enemy_list.add(new enemy(enemy_icon,350,310,30,30,2));
        enemy_list.add(new enemy(enemy_icon,480,310,30,30,3));
        enemy_list.add(new enemy(enemy_icon,595,140,30,30,4));
        enemy_list.add(new enemy(enemy_icon,580,10,30,30,5));
        enemy_list.add(new enemy(enemy_icon,665,50,30,30,6));
        Maze_list.add(new Maze(130,0,20,140));
        Maze_list.add(new Maze(130,200,20,140));
        Maze_list.add(new Maze(150,40,120,20));
        Maze_list.add(new Maze(270,40,20,120));
        Maze_list.add(new Maze(270,100,180,20));
        Maze_list.add(new Maze(270,240,20,130));
        Maze_list.add(new Maze(270,345,180,20));
        Maze_list.add(new Maze(430,120,20,120));
        Maze_list.add(new Maze(530,190,20,170));
        Maze_list.add(new Maze(530,0,20,120));
        Maze_list.add(new Maze(550,300,100,20));
        Maze_list.add(new Maze(630,80,20,220));
        Maze_list.add(new Maze(630,20,300,20));
        Maze_list.add(new Maze(710,20,20,160));
        Maze_list.add(new Maze(710,270,20,100));
        Maze_list.add(new Maze(710,180,230,20));
        Maze_list.add(new Maze(710,250,230,20));
        initBoard();
    }
    public void moving_player(){

    }
    private void initBoard(){
        addKeyListener(this);
        setFocusable(true);
        timer = new Timer(16, this); // 16ms delay for smooth movement (about 60 FPS)
        timer.start();
        setVisible(true);
        setBackground(Color.white);

    }
    private void initVariables(){
        screenData = new short[N_BLOCKS*N_BLOCKS];
        mazeColor = Color.BLUE;
        d = new Dimension(0,400);
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];
    }
    @Override
    public void addNotify(){
        super.addNotify();
        initGame();
    }

    private void playGame(Graphics2D g2d ,Image img , int x_position , int y_position , int final_y_position){
        g2d.drawImage(img,x_position ,y_position,30,30,this);
    }

    public void enemy_position(){

        if (enemy_y < 300 && increase == true){
            enemy_y+=1;
        } else if (enemy_y == 300) {
            increase = false;
            enemy_y -=1;
        } else if (increase == false) {
            enemy_y -=1;
            if (enemy_y == 100){
                increase = true;
            }
        }
    }

    private void initGame(){
        pacsLeft = 3;
        score = 0;
        N_GHOSTS = 6;
        currentSpeed = 3;

    }


    private void drawMaze (Graphics2D g2d , int x,int y,int width,int height){
        g2d.setColor(Color.BLUE);
        g2d.fillRect(x,y,width,height);
    }
    private void drawEnemy(Graphics2D g2d , Image img,int x,int y ,int width , int height){
        g2d.drawImage(img,x ,y,width,height,this);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    public void bullet_position(Integer index){
        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);

        Runnable function1 = new Runnable() {
            @Override
            public void run() {

                bullet_position.get(index).getPosition_x_on_time(direction_player);

            }
        };
        executor2.scheduleAtFixedRate(function1, 0, 12, TimeUnit.MILLISECONDS);

    }
    public boolean bulletIntersectsEnemy(Bullet bullet, enemy enemy) {
        Rectangle bulletRect = new Rectangle(bullet.getPosition_x(), bullet.getPosition_y(),
                10, 10);
        Rectangle enemyRect = new Rectangle(enemy.getPosition_enemy_x(), enemy.getPosition_enemy_y(),
                30,30);

        return bulletRect.intersects(enemyRect);
    }
    public boolean bulletIntersectsMaze(Bullet bullet, Maze maz) {
        Rectangle bulletRect = new Rectangle(bullet.getPosition_x(), bullet.getPosition_y(),
                10, 10);
        Rectangle enemyRect = new Rectangle(maz.getPosition_maze_x(), maz.getPosition_maze_y(),
                maz.getWidth(),maz.getHeight());

        return bulletRect.intersects(enemyRect);
    }
    public boolean playerIntersectMaze(Maze maz) {
        Rectangle playerRect = new Rectangle(imageX, imageY,
                30, 30);
        Rectangle enemyRect = new Rectangle(maz.getPosition_maze_x(), maz.getPosition_maze_y(),
                maz.getWidth(),maz.getHeight());

        return playerRect.intersects(enemyRect);
    }
    public boolean playerIntersectEnemy(enemy enemy) {
        Rectangle playerRect = new Rectangle(imageX, imageY,
                30, 30);
        Rectangle enemyRect = new Rectangle(enemy.getPosition_enemy_x(), enemy.getPosition_enemy_y(),
                enemy.getWidth(),enemy.getHeight());

        return playerRect.intersects(enemyRect);
    }

    public void collapse(Bullet bullet, enemy enemy){
        enemy_list.remove(enemy);
        int index = enemy_list.indexOf(enemy);
        System.out.println(index);
    }

    public void enemy_movement(){
        for (enemy enemies:enemy_list){
            if (enemies.get_enemy_number() == 0){
                enemies.enemy_movement(70,320,"y");
            }
            if (enemies.get_enemy_number() == 1){
                enemies.enemy_movement(390,120,"x");
            }
            if (enemies.get_enemy_number() == 2){
                enemies.enemy_movement(310,120,"y");
            }
            if (enemies.get_enemy_number() == 3){
                enemies.enemy_movement(310,70,"y");
            }
            if (enemies.get_enemy_number() == 4){
                enemies.enemy_movement(595,450,"x");
            }
            if (enemies.get_enemy_number() == 5){
                enemies.enemy_movement(10,180,"y");
            }
            if (enemies.get_enemy_number() == 6){
                enemies.enemy_movement(50,210,"y");
            }
        }

    }


    public void doDrawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0,0,d.width,d.height);
        if (bullet_position.size() > 0){
            for (int i =0; i <bullet_position.size();i++){
                createBullet(g2d,bullet_position.get(i).getPosition_x(),bullet_position.get(i).getPosition_y(),
                        10);
                boolean found = bullet_position.contains(bullet_position.get(i));
                if (found){
                    bullet_position(bullet_position.get(i).getPosition_x());
                    bullet_position(i);
                }
            }
        }
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.setColor(Color.BLUE);
        String text = "Score : "+ scores;
        int x = 750;
        int y = 100;
        g.drawString(text, x, y);
        g.setColor(Color.RED);
        String text2 = "Health : " + Health +" %";
        g.drawString(text2, 750, 150);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.GREEN);
        String text3 = "Exit";
        g.drawString(text3, 890, 230);
        if (enemy_list.size()>0){
            for (int i=0; i< enemy_list.size();i++){
                drawEnemy(g2d,enemy_list.get(i).getImage_enemy()
                        ,enemy_list.get(i).getPosition_enemy_x()
                        ,enemy_list.get(i).getPosition_enemy_y()
                        ,enemy_list.get(i).getWidth(),
                        enemy_list.get(i).getHeight());
            }
        }
        playGame(g2d,player,imageX,imageY,300);

        for (Maze maze:Maze_list){
            drawMaze(g2d ,maze.getPosition_maze_x(),maze.getPosition_maze_y(),maze.getWidth(),maze.getHeight());
        }

        for (Menu menu:Menu_list){
            if (display_menu_winner == true && menu.get_menu_type() == "winner"){

                g2d.setColor(Color.GREEN);
                g2d.fillRect(menu.getPosition_menu_x(),menu.getPosition_menu_y(),menu.getWidth(),menu.getHeight());
                g2d.setFont(new Font("Arial", Font.BOLD, 25));
                g2d.setColor(Color.WHITE);
                g.drawString("WINNER", 380, 110);
            }

        }
        if (enemy_list.size()>0){
                for (int j = 0; j< enemy_list.size();j++){
                    for (int i= 0 ; i< bullet_position.size();i++){
                        if (bulletIntersectsEnemy(bullet_position.get(i),enemy_list.get(j))){
                            if (enemy_list.contains(enemy_list.get(j))){
                                enemy_list.remove(enemy_list.get(j));
                            }
                            if (bullet_position.contains(bullet_position.get(i))){
                                bullet_position.remove(bullet_position.get(i));
                            }
                            scores+=10;
                        }
                    }
                }


            for (enemy enemies:enemy_list){
              if (playerIntersectEnemy(enemies)){
                  imageX -=40;
                  Health-=10;
              }
            }
        }
        if (Maze_list.size()>0){
            for (int i=0; i<Maze_list.size();i++){
                for (int j=0; j<bullet_position.size();j++){
                    if (bulletIntersectsMaze(bullet_position.get(j),Maze_list.get(i))){
                        boolean found = bullet_position.contains(bullet_position.get(j));
                        if (found){
                            bullet_position.remove(bullet_position.get(j));
                        }
                    }
                }
            }
        }
        if (Maze_list.size()>0){
            for (Maze maze:Maze_list){
                if (playerIntersectMaze(maze)){
                    imageX = maze.getPosition_maze_x() - (maze.getWidth()+30);
                    imageY = maze.getPosition_maze_y();
                    System.out.println("the hit occur");
                }
            }
        }
        if (imageX >= 890){
            display_menu_winner = true;
        }
        enemy_movement();
        checkIntersect();
        g2d.drawImage(ii,5,5,this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }
    public void checkIntersect(){
        Rectangle playerBoundingBox = new Rectangle(imageX , imageY, 30, 30);
        for (int x:xmarze_list){
            if (imageX+30 ==x ){
                imageX -=1;
            }
        }

    }

    private void createBullet(Graphics2D g2d , int x , int y , int r){
        g2d.setColor(Color.BLUE);
        int radius = r;
        g2d.fillOval(x, y, radius, radius);
        g2d.drawOval(x, y, radius, radius);
    }

    public void movingplayer(KeyEvent e){

        ScheduledExecutorService executor2 = Executors.newScheduledThreadPool(1);

        Runnable function1 = new Runnable() {
            @Override
            public void run() {


                System.out.println();

            }
        };
        executor2.scheduleAtFixedRate(function1, 0, 12, TimeUnit.MILLISECONDS);

    }





    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE){
            Bullet bullet = new Bullet(imageX, imageY,5);
            bullet_position.add(bullet);
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            this.direction_player = "left";
            player = new ImageIcon("pacman_left.png").getImage();
            if (imageX > 0){
                imageX -= imageSpeed;
            }
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            this.direction_player = "right";
            imageX += imageSpeed;
            player = new ImageIcon("pacman.png").getImage();

        } else if (keyCode == KeyEvent.VK_UP) {
            this.direction_player = "up";
            player = new ImageIcon("pacman_up.png").getImage();
            imageY -= imageSpeed;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            this.direction_player = "down";
            player = new ImageIcon("pacman_down.png").getImage();
            imageY += imageSpeed;
        }

    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    @Override

    public void actionPerformed(ActionEvent ae) {
        repaint();
    }

//    public static void main(String args[]) {
//        new KeyListenerTest();
//    }
}
