import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

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

    private int player=0;

    public Board(){
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        ImageIcon imgBack = new ImageIcon("Board.jpg");
        background = imgBack.getImage();
        addKeyListener(new TAdapter());
        initGame();
}
    public Board(int player){
        this.player=player;
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
        ball.setPosition( ball.x+ball.dx, ball.y+ball.dy);
        for (int i=0; i <4; i++){
            bats[i].setPosition(bats[i].x+bats[i].dx, bats[i].y + bats[i].dy);
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
            ball.x=0;
            collision=-1;
        }
        else if(ball.x >=380){
            if(ball.y<350 && ball.y>50){
                lives[1]--;
                ball.dx=-ball.dx;
            }
            else{
                ball.dx=-ball.dx;
            }
            ball.x=380;
            collision=-1;
        }
        else if(ball.y >=380 ){
            if(ball.x<350 && ball.x>50){
                lives[0]--;
                ball.dy=-ball.dy;
            }
            else{
                ball.dy=-ball.dy;
            }
            ball.y=380;
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
            ball.y=0;
            collision=-1;
        }
        // Collision with the Bat
        for (int i=0; i<4; i++){
            if(collision==i){
                continue;
            }
            switch (i){
                case 0: if(bats[0].getBounds().intersects(ball.getBounds())){
                    collision=0;
                    Rectangle2D intersection= bats[0].getBounds().createIntersection(ball.getBounds());
                    if(intersection.getWidth()>intersection.getHeight()){
                        ball.dy=-ball.dy+bats[0].dy/5;
                        ball.dx+=bats[0].dx/5;
                        ball.y=ball.y<bats[0].y?bats[0].y-20:bats[0].y+10;
                    }
                    else{
                        ball.dx=-ball.dx;
                    }
                }
                    break;
                case 1: if(bats[1].getBounds().intersects(ball.getBounds())){
                    collision = 1;
                    Rectangle2D intersection= bats[1].getBounds().createIntersection(ball.getBounds());
                    if(intersection.getHeight()>intersection.getWidth()) {
                        ball.dx = -ball.dx + bats[1].dx / 5;
                        ball.dy += bats[1].dy / 5;
                        ball.x=ball.x<bats[1].x?bats[1].x-20:bats[1].x+10;
                    }
                    else{
                        ball.dy=-ball.dy;
                    }
                }
                    break;
                case 2: if(bats[2].getBounds().intersects(ball.getBounds())){
                    collision = 2;
                    Rectangle2D intersection= bats[2].getBounds().createIntersection(ball.getBounds());
                    if(intersection.getWidth()>intersection.getHeight()) {
                        ball.dy = -ball.dy + bats[2].dy / 5;
                        ball.dx += bats[2].dx / 5;
                        ball.y=ball.y>bats[2].y?bats[2].y+10:bats[2].y-20;
                    }
                    else{
                        ball.dx=-ball.dx;
                    }
                }
                    break;
                case 3: if(bats[3].getBounds().intersects(ball.getBounds())){
                    collision = 3;
                    Rectangle2D intersection= bats[3].getBounds().createIntersection(ball.getBounds());
                    if(intersection.getHeight()>intersection.getWidth()) {
                        ball.dx = -ball.dx + bats[3].dx / 5;
                        ball.dy += bats[3].dy / 5;
                        ball.x=ball.x>bats[3].x?bats[3].x+10:bats[3].x-20;
                    }
                    else{
                        ball.dy=-ball.dy;
                    }
                }
                    break;
            }
        }

    }
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            bats[player].keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            bats[player].keyPressed(e);
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
