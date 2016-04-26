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

    private boolean inGame[] = {true,true,true,true};
    private int lives[];
    private int collision=-5;
    private int ai=0;

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
    public Board(int player,int ai){
        this.ai=ai;
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
        ball.setPosition(B_WIDTH/2,B_HEIGHT/2);
        ball.setVelocity();
        for(int i=0;i<4;i++){
            lives[i]=5;
            switch (i){
                case 0: bats[i]=new Bat(true);
                    bats[i].setPosition((B_WIDTH-bats[i].WIDTH)/2,B_HEIGHT-bats[i].HEIGHT-5);
                    break;
                case 1: bats[i]=new Bat(false);
                    bats[i].setPosition(B_WIDTH-5-bats[i].HEIGHT,(B_HEIGHT-bats[i].WIDTH)/2);
                    break;
                case 2: bats[i]=new Bat(true);
                    bats[i].setPosition((B_WIDTH-bats[i].WIDTH)/2,5);
                    break;
                case 3: bats[i]=new Bat(false);
                    bats[i].setPosition(5,(B_HEIGHT-bats[i].WIDTH)/2);
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

        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        g2d.drawImage(ball.image, ball.x, ball.y, this);

        for (int i = 0; i < 4; i++) {
            if (inGame[i])
                g2d.drawImage(bats[i].image, bats[i].x, bats[i].y, this);
            else
                bats[i].setPosition(500,500);
        }
        Graphics2D g2dText = (Graphics2D) g;
        g2dText.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if(lives[0]>0)
            g2dText.drawString("Lives: Player 1-" + lives[0], 150, 335);
        g2dText.rotate(-Math.PI / 2);
        if(lives[1]>0)
            g2dText.drawString("Lives: Player 2-" + lives[1], -250, 335);
        g2dText.rotate(Math.PI / 2);
        if(lives[2]>0)
            g2dText.drawString("Lives: Player 3-" + lives[2], 150, 75);
        g2dText.rotate(Math.PI / 2);
        if(lives[3]>0)
            g2dText.drawString("Lives: Player 4-" + lives[3], 150, -75);

        Toolkit.getDefaultToolkit().sync();

    }
    private void move() {

        ball.setPosition(Math.round(ball.x + ball.dx), Math.round(ball.y + ball.dy));
        for (int i = 0; i < 4; i++) {
            if (i != player){
                if(ai==0) {
                    switch (i) {
                        case 0:
                        case 2:
                            if (bats[i].x + bats[i].WIDTH-ball.WIDTH/2 < ball.x)
                                bats[i].dx = 5;
                            else if (bats[i].x - ball.WIDTH/2 > ball.x)
                                bats[i].dx = -5;
                            else
                                bats[i].dx = (ball.x - bats[i].x - (bats[i].WIDTH-ball.WIDTH)/2)*5/ bats[i].WIDTH;
                            break;
                        case 1:
                        case 3:
                            if (bats[i].y + bats[i].WIDTH-ball.HEIGHT/2 < ball.y)
                                bats[i].dy = 5;
                            else if (bats[i].y - ball.HEIGHT/2 > ball.y)
                                bats[i].dy = -5;
                            else
                                bats[i].dy = (ball.y - bats[i].y - (bats[i].WIDTH-ball.HEIGHT)/2)*5 / bats[i].WIDTH;
                            break;
                    }
                }
                else if(ai==1){
                    int xf;
                    switch(i){
                        case 0:
                            if(ball.dy<0) {
                                xf = ball.x + (int) ((ball.y + B_HEIGHT-ball.HEIGHT/2-5-bats[0].HEIGHT) * ball.dx /(- ball.dy));
                                if((xf/B_WIDTH)%2==0)
                                    xf=Math.abs(xf%B_WIDTH);
                                else
                                    xf=B_WIDTH-Math.abs(xf%B_WIDTH);
                                if (bats[0].x + bats[0].WIDTH-ball.WIDTH/2 < xf)
                                    bats[0].dx = 5;
                                else if (bats[0].x - ball.WIDTH/2 > xf)
                                    bats[0].dx = -5;
                                else
                                    bats[0].dx = (xf - bats[0].x - (bats[0].WIDTH-ball.WIDTH)/2)*5 / bats[0].WIDTH;
                            }
                            else if(ball.dy>0){
                                xf = ball.x + (int) ((B_HEIGHT-ball.HEIGHT/2-5-bats[0].HEIGHT - ball.y) * ball.dx / ball.dy);
                                if((xf/B_WIDTH)%2==0)
                                    xf=Math.abs(xf%B_WIDTH);
                                else
                                    xf=B_WIDTH-Math.abs(xf%B_WIDTH);
                                if (bats[0].x + bats[0].WIDTH-ball.WIDTH/2 < xf)
                                    bats[0].dx = 5;
                                else if (bats[0].x - ball.WIDTH/2 > xf)
                                    bats[0].dx = -5;
                                else
                                    bats[0].dx = (xf - bats[0].x - (bats[0].WIDTH-ball.WIDTH)/2)*5 / bats[0].WIDTH;
                            }
                            break;
                        case 1:
                            if(ball.dx<0) {
                                xf = ball.y + (int) ((ball.x + B_WIDTH-ball.WIDTH/2-5-bats[1].HEIGHT) * ball.dy /(- ball.dx));
                                if((xf/B_HEIGHT)%2==0)
                                    xf=Math.abs(xf%B_HEIGHT);
                                else
                                    xf=B_HEIGHT-Math.abs(xf%B_HEIGHT);
                                if (bats[1].y + bats[1].WIDTH-ball.HEIGHT/2 < xf)
                                    bats[1].dy = 5;
                                else if (bats[1].y - ball.HEIGHT/2 > xf)
                                    bats[1].dy = -5;
                                else
                                    bats[1].dy = (xf - bats[1].y - (bats[1].WIDTH-ball.HEIGHT)/2)*5 / bats[1].WIDTH;
                            }
                            else if(ball.dx>0){
                                xf = ball.y + (int) ((B_WIDTH-ball.WIDTH/2-5-bats[1].HEIGHT - ball.x) * ball.dy / ball.dx);
                                if((xf/B_HEIGHT)%2==0)
                                    xf=Math.abs(xf%B_HEIGHT);
                                else
                                    xf=B_HEIGHT-Math.abs(xf%B_HEIGHT);
                                if (bats[1].y + bats[1].WIDTH-ball.HEIGHT/2 < xf)
                                    bats[1].dy = 5;
                                else if (bats[1].y - ball.HEIGHT/2 > xf)
                                    bats[1].dy = -5;
                                else
                                    bats[1].dy = (xf - bats[1].y - (bats[1].WIDTH-ball.HEIGHT)/2)*5 / bats[1].WIDTH;
                            }
                            break;
                        case 2:
                            if(ball.dy<0) {
                                xf = ball.x + (int) ((ball.y-5-bats[2].HEIGHT) * ball.dx /(- ball.dy));
                                if((xf/B_WIDTH)%2==0)
                                    xf=Math.abs(xf%B_WIDTH);
                                else
                                    xf=B_WIDTH-Math.abs(xf%B_WIDTH);
                                if (bats[2].x + bats[2].WIDTH-ball.WIDTH/2 < xf)
                                    bats[2].dx = 5;
                                else if (bats[2].x - ball.WIDTH/2 > xf)
                                    bats[2].dx = -5;
                                else
                                    bats[2].dx = (xf - bats[2].x - (bats[2].WIDTH-ball.WIDTH)/2)*5 / bats[2].WIDTH;
                            }
                            else if(ball.dy>0){
                                xf = ball.x + (int) ((2*B_HEIGHT-5-bats[2].HEIGHT-ball.HEIGHT- ball.y) * ball.dx / ball.dy);
                                if((xf/B_WIDTH)%2==0)
                                    xf=Math.abs(xf%B_WIDTH);
                                else
                                    xf=B_WIDTH-Math.abs(xf%B_WIDTH);
                                if (bats[2].x + bats[2].WIDTH-ball.WIDTH/2 < xf)
                                    bats[2].dx = 5;
                                else if (bats[2].x - ball.WIDTH/2 > xf)
                                    bats[2].dx = -5;
                                else
                                    bats[2].dx = (xf - bats[2].x - (bats[2].WIDTH-ball.WIDTH)/2)*5 / bats[2].WIDTH;
                            }
                            break;
                        case 3:
                            if(ball.dx<0) {
                                xf = ball.y + (int) ((ball.x-5-bats[3].HEIGHT) * ball.dy /(- ball.dx));
                                if((xf/B_HEIGHT)%2==0)
                                    xf=Math.abs(xf%B_HEIGHT);
                                else
                                    xf=B_HEIGHT-Math.abs(xf%B_HEIGHT);
                                if (bats[3].y + bats[1].WIDTH-ball.HEIGHT/2 < xf)
                                    bats[3].dy = 5;
                                else if (bats[3].y - ball.HEIGHT/2 > xf)
                                    bats[3].dy = -5;
                                else
                                    bats[3].dy = (xf - bats[3].y - (bats[1].WIDTH-ball.HEIGHT)/2)*5 / bats[3].WIDTH;
                            }
                            else if(ball.dx>0){
                                xf = ball.y + (int) ((2*B_WIDTH-5-bats[3].HEIGHT-ball.WIDTH/2 - ball.x) * ball.dy / ball.dx);
                                if((xf/400)%2==0)
                                    xf=Math.abs(xf%400);
                                else
                                    xf=400-Math.abs(xf%400);
                                if (bats[3].y + 65 < xf)
                                    bats[3].dy = 5;
                                else if (bats[3].y - 10 > xf)
                                    bats[3].dy = -5;
                                else
                                    bats[3].dy = (xf - bats[3].y - 27) / 15;
                            }
                            break;
                    }
                }
                else{
                    int xf;
                    switch(i){
                        case 0:
                            if(ball.x>30&&ball.x<350&&ball.y>350-ball.HEIGHT){
                                int temp=ball.x+(ball.WIDTH-bats[0].WIDTH)/2-bats[0].x;
                                if(temp==0)
                                    bats[0].dy=-2;
                                else {
                                    double dir = (double) (ball.y + (ball.HEIGHT - bats[0].HEIGHT) / 2 - bats[0].y) / temp;
                                    double dx1 = 2 / dir;
                                    if (Math.abs(dx1) > 5) {
                                        bats[0].dx = ((ball.x + (ball.WIDTH - bats[0].WIDTH) / 2 - bats[0].x) > 0) ? 5 : -5;
                                        bats[0].dy = (int) (bats[0].dx * dir);
                                    } else {
                                        bats[0].dy = -2;
                                        bats[0].dx = (int) (-dx1);
                                    }
                                }
                            }
                            else {
                                if(ball.dy<0) {
                                    xf = ball.x + (int) ((ball.y + B_HEIGHT-ball.HEIGHT/2-5-bats[0].HEIGHT) * ball.dx /(- ball.dy));
                                    if((xf/B_WIDTH)%2==0)
                                        xf=Math.abs(xf%B_WIDTH);
                                    else
                                        xf=B_WIDTH-Math.abs(xf%B_WIDTH);
                                    if (bats[0].x + bats[0].WIDTH-ball.WIDTH/2 < xf)
                                        bats[0].dx = 5;
                                    else if (bats[0].x - ball.WIDTH/2 > xf)
                                        bats[0].dx = -5;
                                    else
                                        bats[0].dx = (xf - bats[0].x - (bats[0].WIDTH-ball.WIDTH)/2)*5 / bats[0].WIDTH;
                                }
                                else if(ball.dy>0){
                                    xf = ball.x + (int) ((B_HEIGHT-ball.HEIGHT/2-5-bats[0].HEIGHT - ball.y) * ball.dx / ball.dy);
                                    if((xf/B_WIDTH)%2==0)
                                        xf=Math.abs(xf%B_WIDTH);
                                    else
                                        xf=B_WIDTH-Math.abs(xf%B_WIDTH);
                                    if (bats[0].x + bats[0].WIDTH-ball.WIDTH/2 < xf)
                                        bats[0].dx = 5;
                                    else if (bats[0].x - ball.WIDTH/2 > xf)
                                        bats[0].dx = -5;
                                    else
                                        bats[0].dx = (xf - bats[0].x - (bats[0].WIDTH-ball.WIDTH)/2)*5 / bats[0].WIDTH;
                                }
                                bats[0].dy = 2;
                            }
                            break;
                        case 1:
                            if(ball.x>350-ball.WIDTH&&ball.y<350&&ball.y>50-ball.HEIGHT){
                                int temp=ball.x+(ball.WIDTH-bats[1].HEIGHT)/2-bats[1].x;
                                if(temp==0)
                                    bats[1].dx=-2;
                                else {
                                    double dir = (ball.y + (ball.HEIGHT - bats[1].WIDTH) / 2 - bats[1].y) / temp;
                                    double dy1 = 2 * dir;
                                    if (Math.abs(dy1) > 5) {
                                        bats[1].dy = ((ball.y + (ball.WIDTH - bats[1].HEIGHT) / 2 - bats[1].y) > 0) ? 5 : -5;
                                        bats[1].dx = (int) (bats[1].dy / dir);
                                    } else {
                                        bats[1].dx = -2;
                                        bats[1].dy = (int) (-dy1);
                                    }
                                }
                            }
                            else {
                                if(ball.dx<0) {
                                    xf = ball.y + (int) ((ball.x + B_WIDTH-ball.WIDTH/2-5-bats[1].HEIGHT) * ball.dy /(- ball.dx));
                                    if((xf/B_HEIGHT)%2==0)
                                        xf=Math.abs(xf%B_HEIGHT);
                                    else
                                        xf=B_HEIGHT-Math.abs(xf%B_HEIGHT);
                                    if (bats[1].y + bats[1].WIDTH-ball.HEIGHT/2 < xf)
                                        bats[1].dy = 5;
                                    else if (bats[1].y - ball.HEIGHT/2 > xf)
                                        bats[1].dy = -5;
                                    else
                                        bats[1].dy = (xf - bats[1].y - (bats[1].WIDTH-ball.HEIGHT)/2)*5 / bats[1].WIDTH;
                                }
                                else if(ball.dx>0){
                                    xf = ball.y + (int) ((B_WIDTH-ball.WIDTH/2-5-bats[1].HEIGHT - ball.x) * ball.dy / ball.dx);
                                    if((xf/B_HEIGHT)%2==0)
                                        xf=Math.abs(xf%B_HEIGHT);
                                    else
                                        xf=B_HEIGHT-Math.abs(xf%B_HEIGHT);
                                    if (bats[1].y + bats[1].WIDTH-ball.HEIGHT/2 < xf)
                                        bats[1].dy = 5;
                                    else if (bats[1].y - ball.HEIGHT/2 > xf)
                                        bats[1].dy = -5;
                                    else
                                        bats[1].dy = (xf - bats[1].y - (bats[1].WIDTH-ball.HEIGHT)/2)*5 / bats[1].WIDTH;
                                }
                                bats[1].dx = 2;
                            }
                            break;
                        case 2:
                            if(ball.x>50-ball.WIDTH&&ball.x<350&&ball.y<50){
                                int temp=ball.x+(ball.WIDTH-bats[2].WIDTH)/2-bats[2].x;
                                if(temp==0)
                                    bats[2].dy=2;
                                else {
                                    double dir = (ball.y + (ball.HEIGHT - bats[2].HEIGHT) / 2 - bats[2].y) / temp;
                                    double dx1 = 2 / dir;
                                    if (Math.abs(dx1) > 5) {
                                        bats[2].dx = ((ball.x + (ball.WIDTH - bats[2].WIDTH) / 2 - bats[2].x) > 0) ? 5 : -5;
                                        bats[2].dy = (int) (bats[2].dx * dir);
                                    } else {
                                        bats[2].dy = 2;
                                        bats[2].dx = (int) (dx1);
                                    }
                                }
                            }
                            else {
                                if(ball.dy<0) {
                                    xf = ball.x + (int) ((ball.y-5-bats[2].HEIGHT) * ball.dx /(- ball.dy));
                                    if((xf/B_WIDTH)%2==0)
                                        xf=Math.abs(xf%B_WIDTH);
                                    else
                                        xf=B_WIDTH-Math.abs(xf%B_WIDTH);
                                    if (bats[2].x + bats[2].WIDTH-ball.WIDTH/2 < xf)
                                        bats[2].dx = 5;
                                    else if (bats[2].x - ball.WIDTH/2 > xf)
                                        bats[2].dx = -5;
                                    else
                                        bats[2].dx = (xf - bats[2].x - (bats[2].WIDTH-ball.WIDTH)/2)*5 / bats[2].WIDTH;
                                }
                                else if(ball.dy>0){
                                    xf = ball.x + (int) ((2*B_HEIGHT-5-bats[2].HEIGHT-ball.HEIGHT- ball.y) * ball.dx / ball.dy);
                                    if((xf/B_WIDTH)%2==0)
                                        xf=Math.abs(xf%B_WIDTH);
                                    else
                                        xf=B_WIDTH-Math.abs(xf%B_WIDTH);
                                    if (bats[2].x + bats[2].WIDTH-ball.WIDTH/2 < xf)
                                        bats[2].dx = 5;
                                    else if (bats[2].x - ball.WIDTH/2 > xf)
                                        bats[2].dx = -5;
                                    else
                                        bats[2].dx = (xf - bats[2].x - (bats[2].WIDTH-ball.WIDTH)/2)*5 / bats[2].WIDTH;
                                }
                                bats[2].dy=-2;
                            }
                            break;
                        case 3:
                            if(ball.x<50&&ball.y<350&&ball.y>50-ball.WIDTH){
                                int temp=ball.x+(ball.WIDTH-bats[3].HEIGHT)/2-bats[3].x;
                                if(temp==0)
                                    bats[3].dx=2;
                                else {
                                    double dir = (ball.y + (ball.HEIGHT - bats[3].WIDTH) / 2 - bats[3].y) / temp;
                                    double dy1 = 2 * dir;
                                    if (Math.abs(dy1) > 5) {
                                        bats[3].dy = ((ball.y + (ball.WIDTH - bats[3].HEIGHT) / 2 - bats[3].y) > 0) ? 5 : -5;
                                        bats[3].dx = (int) (bats[3].dy / dir);
                                    } else {
                                        bats[3].dx = 2;
                                        bats[3].dy = (int) (dy1);
                                    }
                                }
                            }
                            else {
                                if(ball.dx<0) {
                                    xf = ball.y + (int) ((ball.x-5-bats[3].HEIGHT) * ball.dy /(- ball.dx));
                                    if((xf/B_HEIGHT)%2==0)
                                        xf=Math.abs(xf%B_HEIGHT);
                                    else
                                        xf=B_HEIGHT-Math.abs(xf%B_HEIGHT);
                                    if (bats[3].y + bats[1].WIDTH-ball.HEIGHT/2 < xf)
                                        bats[3].dy = 5;
                                    else if (bats[3].y - ball.HEIGHT/2 > xf)
                                        bats[3].dy = -5;
                                    else
                                        bats[3].dy = (xf - bats[3].y - (bats[1].WIDTH-ball.HEIGHT)/2)*5 / bats[3].WIDTH;
                                }
                                else if(ball.dx>0){
                                    xf = ball.y + (int) ((2*B_WIDTH-5-bats[3].HEIGHT-ball.WIDTH/2 - ball.x) * ball.dy / ball.dx);
                                    if((xf/400)%2==0)
                                        xf=Math.abs(xf%400);
                                    else
                                        xf=400-Math.abs(xf%400);
                                    if (bats[3].y + 65 < xf)
                                        bats[3].dy = 5;
                                    else if (bats[3].y - 10 > xf)
                                        bats[3].dy = -5;
                                    else
                                        bats[3].dy = (xf - bats[3].y - 27) / 15;
                                }
                                bats[3].dx = -2;
                            }
                            break;
                    }
                }
            }
            switch (i) {
                case 0:
                    if (((bats[i].x < 50 && bats[i].dx < 0) || (bats[i].x > B_WIDTH-50-bats[i].WIDTH && bats[i].dx > 0)) && ((bats[i].y > B_HEIGHT-bats[i].HEIGHT && bats[i].dy > 0) || (bats[i].y < B_HEIGHT-50 && bats[i].dy < 0))) {
                        bats[i].setPosition(bats[i].x, bats[i].y);
                        continue;
                    }
                    if (bats[i].x < 50 && bats[i].dx < 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].x > B_WIDTH-50-bats[i].WIDTH && bats[i].dx > 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    }
                    if (bats[i].y > B_HEIGHT-bats[i].HEIGHT && bats[i].dy > 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    } else if (bats[i].y < B_HEIGHT-50 && bats[i].dy < 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    }
                    break;
                case 1:
                    if (((bats[i].x < B_WIDTH-50 && bats[i].dx < 0) || (bats[i].x > B_WIDTH-bats[i].HEIGHT && bats[i].dx > 0)) && ((bats[i].y > B_HEIGHT-50-bats[i].WIDTH && bats[i].dy > 0) || (bats[i].y < 50 && bats[i].dy < 0))) {
                        bats[i].setPosition(bats[i].x, bats[i].y);
                        continue;
                    }
                    if (bats[i].x < B_WIDTH-50 && bats[i].dx < 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].x > B_WIDTH-bats[i].HEIGHT && bats[i].dx > 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].y > B_HEIGHT-50-bats[i].WIDTH && bats[i].dy > 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    } else if (bats[i].y < 50 && bats[i].dy < 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    }
                    break;
                case 2:
                    if (((bats[i].x < 50 && bats[i].dx < 0) || (bats[i].x > B_WIDTH-50-bats[i].WIDTH && bats[i].dx > 0)) && ((bats[i].y > 50-bats[i].HEIGHT && bats[i].dy > 0) || (bats[i].y < 0 && bats[i].dy < 0))) {
                        bats[i].setPosition(bats[i].x, bats[i].y);
                        continue;
                    }
                    if (bats[i].x < 50 && bats[i].dx < 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].x > B_WIDTH-50-bats[i].WIDTH && bats[i].dx > 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].y > 50-bats[i].HEIGHT && bats[i].dy > 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    } else if (bats[i].y < 0 && bats[i].dy < 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    }
                    break;
                case 3:
                    if (((bats[i].x < 0 && bats[i].dx < 0) || (bats[i].x > 50-bats[i].HEIGHT && bats[i].dx > 0)) && ((bats[i].y > B_HEIGHT-50-bats[i].WIDTH && bats[i].dy > 0) || (bats[i].y < 50 && bats[i].dy < 0))) {
                        bats[i].setPosition(bats[i].x, bats[i].y);
                        continue;
                    }
                    if (bats[i].x < 0 && bats[i].dx < 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].x > 50-bats[i].HEIGHT && bats[i].dx > 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].y > B_HEIGHT-50-bats[i].WIDTH && bats[i].dy > 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    } else if (bats[i].y < 50 && bats[i].dy < 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    }
                    break;
            }
            bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y + bats[i].dy);
        }

    }
    private void checkCollision(){
        //collision with the walls
        if(ball.x <=0 ){
            if(ball.y<350 && ball.y>50){
                lives[3]--;
                ball.dx=-ball.dx;
                collision=-4;
            }
            else{
                ball.dx=-ball.dx;
                collision=-5;
            }
            ball.x=0;
        }
        else if(ball.x >=400-ball.WIDTH){
            if(ball.y<350 && ball.y>50){
                lives[1]--;
                ball.dx=-ball.dx;
                collision=-2;
            }
            else{
                ball.dx=-ball.dx;
                collision=-5;
            }
            ball.x=400-ball.WIDTH;
        }
        else if(ball.y >=400-ball.HEIGHT ){
            if(ball.x<350 && ball.x>50){
                lives[0]--;
                ball.dy=-ball.dy;
                collision=-1;
            }
            else{
                ball.dy=-ball.dy;
                collision=-5;
            }
            ball.y=400-ball.HEIGHT;
        }
        else if(ball.y <=0 ){
            if(ball.x<350 && ball.x>50){
                lives[2]--;
                ball.dy=-ball.dy;
                collision=-3;
            }
            else{
                ball.dy=-ball.dy;
                collision=-5;
            }
            ball.y=0;
        }
        // Collision with the Bat
        for (int i=0; i<4; i++){
            switch (i){
                case 0:if(collision!=-1) {
                    if (bats[0].getBounds().intersects(ball.getBounds())) {
                        collision = 0;
                        Rectangle2D intersection = bats[0].getBounds().createIntersection(ball.getBounds());
                        if (intersection.getWidth() > intersection.getHeight()) {
                            ball.dy = -ball.dy + (float) (bats[0].dy) / 4;
                            ball.dx += (float) bats[0].dx / 10;
                            ball.y = ball.y < bats[0].y ? bats[0].y - 20 : bats[0].y + 10;
                        } else {
                            ball.dx = -ball.dx;
                        }
                    }
                }
                    break;
                case 1:if(collision!=-2) {
                    if (bats[1].getBounds().intersects(ball.getBounds())) {
                        collision = 1;
                        Rectangle2D intersection = bats[1].getBounds().createIntersection(ball.getBounds());
                        if (intersection.getHeight() > intersection.getWidth()) {
                            ball.dx = -ball.dx + (float) bats[1].dx / 4;
                            ball.dy += (float) bats[1].dy / 10;
                            ball.x = ball.x < bats[1].x ? bats[1].x - 20 : bats[1].x + 10;
                        } else {
                            ball.dy = -ball.dy;
                        }
                    }
                }
                    break;
                case 2:if(collision!=-3) {
                    if (bats[2].getBounds().intersects(ball.getBounds())) {
                        collision = 2;
                        Rectangle2D intersection = bats[2].getBounds().createIntersection(ball.getBounds());
                        if (intersection.getWidth() > intersection.getHeight()) {
                            ball.dy = -ball.dy + (float) bats[2].dy / 4;
                            ball.dx += (float) bats[2].dx / 10;
                            ball.y = ball.y > bats[2].y ? bats[2].y + 10 : bats[2].y - 20;
                        } else {
                            ball.dx = -ball.dx;
                        }
                    }
                }
                    break;
                case 3:if(collision!=-4) {
                    if (bats[3].getBounds().intersects(ball.getBounds())) {
                        collision = 3;
                        Rectangle2D intersection = bats[3].getBounds().createIntersection(ball.getBounds());
                        if (intersection.getHeight() > intersection.getWidth()) {
                            ball.dx = -ball.dx + (float) bats[3].dx / 4;
                            ball.dy += (float) bats[3].dy / 10;
                            ball.x = ball.x > bats[3].x ? bats[3].x + 10 : bats[3].x - 20;
                        } else {
                            ball.dy = -ball.dy;
                        }
                    }
                }
                    break;
            }
        }
        float speed=(float)Math.sqrt(ball.dx*ball.dx+ball.dy*ball.dy);
        if(speed>9){
            ball.dx=ball.dx*9/speed;
            ball.dy=ball.dy*9/speed;
        }

    }
    private void checkIngame() {
        for(int i=0;i<4;i++){
            if(lives[i]<=0)
                inGame[i]=false;
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
        int count=0;
        for (boolean x:inGame)
            if(x)
                count++;
        if(count>1){
            checkCollision();
            checkIngame();
            move();
        }
        repaint();
    }
}
