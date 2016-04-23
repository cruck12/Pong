import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sahil on 4/17/2016.
 */

public class Game {
    private JPanel gameMenu;
    private JLabel titleLabel;
    private JButton singleplayerButton;
    private JButton multiplayerButton;
    private JButton exitButton;
    private JButton settingsButton;

    private int screenNumber;
    public Game() {
        screenNumber=0;
        singleplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Game");
                frame.setContentPane(new Board(screenNumber));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400,400);
                frame.setResizable(false);
                frame.pack();
                frame.setVisible(true);
                screenNumber=++screenNumber%4;
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setContentPane(new Game().gameMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
