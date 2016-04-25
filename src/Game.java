import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sahil on 4/17/2016.
 * TODO Multiplayer and AI
 */

public class Game {
    private JPanel gameCards;
    private JPanel gameMenu;
    private JPanel settingsMenu;
    private JLabel titleLabel;
    private JButton singleplayerButton;
    private JButton multiplayerButton;
    private JButton exitButton;
    private JButton settingsButton;

    private int screenNumber;
    private int ai;


    public Game() {
        gameCards = new JPanel(new CardLayout());
        settingsMenu = new JPanel();
        gameCards.add(gameMenu,"menu");
        gameCards.add(settingsMenu,"settings");
        screenNumber=0;
        ai=0;
        singleplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Game");
                frame.setContentPane(new Board(screenNumber));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) gameCards.getLayout();
                cardLayout.show(gameCards,"settings");
                initSettings();
            }
        });
        multiplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Game");
                MultiplayerOptions multiplayerOptions = new MultiplayerOptions();
                frame.setContentPane(multiplayerOptions.getContentPane());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400,400);
                frame.setResizable(false);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    private void initSettings() {
        if(settingsMenu.getComponentCount()>0)
            return;
        JLabel aiLabel = new JLabel("AI difficulty",JLabel.CENTER);
        String aiModes[] = {"Easy", "Hard"};
        final JComboBox aiSetting = new JComboBox(aiModes);
        aiSetting.setSelectedIndex(0);
        aiSetting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ai = aiSetting.getSelectedIndex();
            }
        });
        final JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) gameCards.getLayout();
                cardLayout.show(gameCards,"menu");
            }
        });
        settingsMenu.add(aiLabel);
        settingsMenu.add(aiSetting);
        settingsMenu.add(back);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        Game game = new Game();
        frame.getContentPane().add(game.gameCards);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
