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
    private boolean inGame = true;
    private boolean dying = false;
    private boolean Bullet_shooting = false;
    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;

    private final int PAC_ANIM_DELAY = 2;
    private final int PACMAN_ANIM_COUNT = 4;
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
    private Image enemy_icon;
    private Image pacman1,pacman2up,pacman2left,pacman2right,pacman2down;
    private Image pacman3up,pacman3left,pacman3right,pacman3down;
    private Image pacman4up,pacman4left,pacman4right,pacman4down;
    boolean increase = true;

    private int bullet_shooting_x = imageX;
    private int bullet_shooting_y = imageY;
    List<Rectangle> rectanglesList = new ArrayList<>();
    List<Integer> xmarze_list = new ArrayList<>();
    List<Integer> ymarze_list = new ArrayList<>();
    ArrayList<Bullet> bullet_position = new ArrayList<Bullet>();
    ArrayList<enemy> enemy_list = new ArrayList<enemy>();
    private final int validSpeeds[] = {1,2,3,4,6,8};
    private final int maxSpeed = 6;
    private int currentSpeed = 3;
    private short[] screenData;
    private Timer timer;

    public Board (){
        loadImages();
        initVariables();
        enemy_list.add(new enemy(enemy_icon,180,100,30,30));
        enemy_list.add(new enemy(enemy_icon,280,50,30,30));
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
        setBackground(Color.BLACK);

    }
    private void initVariables(){
        screenData = new short[N_BLOCKS*N_BLOCKS];
        mazeColor = Color.BLUE;
        d = new Dimension(400,400);
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


    private void drawMaze (Graphics2D g2d , int x,int y){
        g2d.setColor(Color.yellow);
        g2d.fillRect(x,y,cellSize,cellSize);
        Rectangle mazeRectangular= new Rectangle(x,y, cellSize, cellSize);
        if (!rectanglesList.contains(mazeRectangular)){
             for (int i = mazeRectangular.x; i <= mazeRectangular.x + cellSize; i++){
                 if (!xmarze_list.contains(i)){
                     xmarze_list.add(i);
                 }
             }
            for (int j = mazeRectangular.y; j <= mazeRectangular.y + cellSize; j++){
                if (!ymarze_list.contains(j)){
                    ymarze_list.add(j);
                }
            }
        }

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
                bullet_position.get(index).getPosition_x_on_time();

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

    public void collapse(Bullet bullet, enemy enemy){
        enemy_list.remove(enemy);
        int index = enemy_list.indexOf(enemy);
        System.out.println(index);
    }
    public void doDrawing(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0,0,d.width,d.height);
        if (bullet_position.size() > 0){
            for (int i =0; i <bullet_position.size();i++){
                createBullet(g2d,bullet_position.get(i).getPosition_x(),bullet_position.get(i).getPosition_y());
                bullet_position(bullet_position.get(i).getPosition_x());
                bullet_position(i);
            }

        }
        if (enemy_list.size()>0){
            for (int i=0; i< enemy_list.size();i++){
                drawEnemy(g2d,enemy_list.get(i).getImage_enemy()
                        ,enemy_list.get(i).getPosition_enemy_x()
                        ,enemy_list.get(i).getPosition_enemy_y()
                        ,enemy_list.get(i).getWidth(),
                        enemy_list.get(i).getHeight());
            }
        }


        playGame(g2d,pacman2right,imageX,imageY,300);
        drawMaze(g2d ,130,130);
        drawMaze(g2d ,130,150);
        drawMaze(g2d ,130,170);
        for (enemy enemys:enemy_list){
            for (Bullet Bullet:bullet_position){
                if (bulletIntersectsEnemy(Bullet,enemys)){
                    System.out.println("Collapse occur");
                    enemy_list.remove(enemys);
                }
            }
        }
        enemy_list.get(0).enemy_movement(100,300,"y");
        enemy_list.get(1).enemy_movement(100,350,"x");
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

    private void createBullet(Graphics2D g2d , int x , int y){
        g2d.setColor(Color.BLUE);
        int radius = 5;
        g2d.fillOval(x, y, 2 * radius, 2 * radius);
        g2d.drawOval(x, y, 2 * radius, 2 * radius);
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

    private void loadImages(){
        pacman1 = new ImageIcon("pacman.png").getImage();
        pacman2up = new ImageIcon("pacman.png").getImage();
        pacman3up = new ImageIcon("pacman.png").getImage();
        pacman4up = new ImageIcon("pacman.png").getImage();
        pacman2down = new ImageIcon("pacman.png").getImage();
        pacman3down = new ImageIcon("pacman.png").getImage();
        pacman4down = new ImageIcon("pacman.png").getImage();
        pacman2left = new ImageIcon("pacman.png").getImage();
        pacman3left = new ImageIcon("pacman.png").getImage();
        pacman4left = new ImageIcon("pacman.png").getImage();
        pacman2right = new ImageIcon("pacman.png").getImage();
        pacman3right = new ImageIcon("pacman.png").getImage();
        pacman4right = new ImageIcon("pacman.png").getImage();
        enemy_icon = new ImageIcon("bad-person.png").getImage();
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

            if (imageX > 0){
                imageX -= imageSpeed;
            }
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            imageX += imageSpeed;


        } else if (keyCode == KeyEvent.VK_UP) {
            imageY -= imageSpeed;
        } else if (keyCode == KeyEvent.VK_DOWN) {
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
