import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Sahil on 4/20/2016.
 */
public class Board extends JPanel implements ActionListener {
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 400;
    private final int DELAY = 18;

    private boolean inGame = true;
    private int lives[];
    private int collision=-1;

    private Timer timer;
    private Ball ball;
    private Image background;
    private Bat[] bats;

    public Board(){
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        ImageIcon imgBack = new ImageIcon("Board.jpg");
        background = imgBack.getImage();
        addKeyListener(new TAdapter());
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
        for (int i=0; i <4; i++){
            bats[i].setPosition(bats[i].x+bats[i].dx, bats[i].y + bats[i].dy);
//            bats[i].resetVelocity();
        }
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
            collision=-1;
        }
        else if(ball.x >=385 ){
            if(ball.y<350 && ball.y>50){
                lives[1]--;
                ball.dx=-ball.dx;
            }
            else{
                ball.dx=-ball.dx;
            }
            collision=-1;
        }
        else if(ball.y >=385 ){
            if(ball.x<350 && ball.x>50){
                lives[0]--;
                ball.dy=-ball.dy;
            }
            else{
                ball.dy=-ball.dy;
            }
            collision=-1;
        }
        else if(ball.y <=0 ){
            if(ball.x<350 && ball.x>50){
                lives[2]--;
                ball.dy=-ball.dy;
            }
            else{
                ball.dy=-ball.dy;
            }
            collision=-1;
        }
        // Collision with the Bat
        for (int i=0; i<4; i++){
            if(collision==i){
                continue;
            }
            switch (i){
                case 0: if(bats[0].bounds.contains(ball.x+5,ball.y+10)){
                    ball.dy=-ball.dy-bats[0].dy/5;
                    ball.dx+=bats[0].dx/5;
                    collision=0;
                }
                    break;
                case 1: if(bats[1].bounds.contains(ball.x+10,ball.y+5)){
                    ball.dx=-ball.dx-bats[1].dx/5;
                    ball.dy+=bats[1].dy/5;
                    collision=1;
                }
                    break;
                case 2: if(bats[2].bounds.contains(ball.x+5,ball.y)){
                    ball.dy=-ball.dy-bats[2].dy/5;
                    ball.dx+=bats[2].dx/5;
                    collision=2;
                }
                    break;
                case 3: if(bats[3].bounds.contains(ball.x,ball.y+5)){
                    ball.dx=-ball.dx-bats[3].dx/5;
                    ball.dy+=bats[3].dy/5;
                    collision=3;
                }
                    break;
            }
        }

    }
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            bats[0].keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            bats[0].keyPressed(e);
        }
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
