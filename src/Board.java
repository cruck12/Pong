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
    private int lives[];

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
        lives= new int[4];
        bats= new Bat[4];
        ball = new Ball();
        ball.setPosition(200,200);
        ball.setVelocity();
        for(int i=0;i<4;i++){
            lives[i]=5;
            switch (i){
                case 0: bats[i]=new Bat(true);
                    bats[i].setPosition(180,390);
                    break;
                case 1: bats[i]=new Bat(false);
                    bats[i].setPosition(390,180);
                    break;
                case 2: bats[i]=new Bat(true);
                    bats[i].setPosition(180,0);
                    break;
                case 3: bats[i]=new Bat(false);
                    bats[i].setPosition(0,180);
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
        //collision with the walls
        if(ball.x <=0 ){
            if(ball.y<350 && ball.y>50){
                lives[3]--;
                ball.dx=-ball.dx;
            }
            else{
                ball.dx=-ball.dx;
            }
        }
        else if(ball.x >=385 ){
            if(ball.y<350 && ball.y>50){
                lives[1]--;
                ball.dx=-ball.dx;
            }
            else{
                ball.dx=-ball.dx;
            }
        }
        else if(ball.y >=385 ){
            if(ball.x<350 && ball.x>50){
                lives[0]--;
                ball.dy=-ball.dy;
            }
            else{
                ball.dy=-ball.dy;
            }
        }
        else if(ball.y <=0 ){
            if(ball.x<350 && ball.x>50){
                lives[2]--;
                ball.dy=-ball.dy;
            }
            else{
                ball.dy=-ball.dy;
            }
        }
        /* Collision with the Bat
        for (int i=0; i<4; i++){
            switch (i){
                case 0: if((ball.x+5)>bats[0].x && (ball.x+5)<bats[0].x+75 && ball.y+10 > bats[0].y && ball.y+10 < bats[0].y+10){
                        ball.dy=-ball.dy-bats[0].dy;
                        ball.dx+=bats[0].dx;
                    }
                    break;
                case 1: if((ball.x+5)>bats[1].x && (ball.x+5)<bats[1].x+10 && ball.y+5 > bats[1].y && ball.y+5 < bats[1].y+75){
                    ball.dx=-ball.dx-bats[1].dx;
                    ball.dy+=bats[1].dy;
                }
                    break;
                case 2: if((ball.x+5)>bats[2].x && (ball.x+5)<bats[2].x+75 && ball.y > bats[2].y && ball.y < bats[2].y+10){
                    ball.dy=-ball.dy-bats[2].dy;
                    ball.dx+=bats[2].dx;
                }
                    break;
                case 3: if((ball.x)>bats[3].x && (ball.x)<bats[3].x+75 && ball.y+5 > bats[3].y && ball.y+5 < bats[3].y+75){
                    ball.dy=-ball.dy-bats[3].dy;
                    ball.dx+=bats[3].dx;
                }
                    break;
            }
        }
        */
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
