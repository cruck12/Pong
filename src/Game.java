import javax.swing.*;

/**
 * Created by Sahil on 4/17/2016.
 */

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setContentPane(new Board());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
