import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sahil on 4/20/2016.
 */
public class Board extends JPanel implements ActionListener {
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 400;
    private final int DELAY = 18;

    private boolean inGame = true;

    private Timer timer;
    private Ball ball;
    private Image background;
    private Bat[] bats;

    public Board(){
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        ImageIcon imgBack = new ImageIcon("Board.jpg");
        background = imgBack.getImage();
        initGame();
    }

    private void initGame() {
        bats= new Bat[4];
        ball = new Ball();
        ball.setPosition(200,200);
        ball.setVelocity();
        for(int i=0;i<4;i++){
            switch (i){
                case 0: bats[i]=new Bat(true);
                    bats[i].setPosition(200,390);
                    break;
                case 1: bats[i]=new Bat(false);
                    bats[i].setPosition(390,200);
                    break;
                case 2: bats[i]=new Bat(true);
                    bats[i].setPosition(200,10);
                    break;
                case 3: bats[i]=new Bat(false);
                    bats[i].setPosition(10,200);
                    break;
            }

        }

        timer = new Timer(DELAY,this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background,0,0,this);
        draw(g);
    }

    private void draw(Graphics g) {
        if(inGame){
            Graphics2D g2d = (Graphics2D) g;
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHints(rh);

            g2d.drawImage(ball.image,ball.x,ball.y,this);

            for(int i=0;i<4;i++){
                g2d.drawImage(bats[i].image,bats[i].x,bats[i].y,this);
            }


            Toolkit.getDefaultToolkit().sync();
        }
    }
    private void move(){
        ball.setPosition(ball.x+ball.dx, ball.y+ball.dy);
    }
    private void checkCollision(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkCollision();
            move();
        }
        repaint();
    }
}
