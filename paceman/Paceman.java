package paceman;

import javax.swing.*;

public class Paceman extends JFrame {
    private static int nums = 100;


    public static void main(String[] args) {

        JFrame obj = new JFrame();
        Board board = new Board();
        obj.setBounds(10, 10, 800, 400);
        obj.setTitle("Pacman");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(EXIT_ON_CLOSE);
        obj.add(board);

    }
}
