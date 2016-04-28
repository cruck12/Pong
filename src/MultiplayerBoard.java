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
public class MultiplayerBoard extends JPanel  {
    private final int B_WIDTH = 400;
    private final int B_HEIGHT = 400;
//    private final int DELAY = 18;
    private final long OPTIMAL_TIME = 18000000;

    long lastLoopTime;
    long lastFpsTime;
    int fps;

    private boolean inGame[] = {true,true,true,true};
    private int lives[];
    private int collision=-1;

    private Timer timer;
    private Ball ball;
    private Image background;
    public Bat[] bats;

    private int player=0;

    public MultiplayerBoard(){
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        ImageIcon imgBack = new ImageIcon("Board.jpg");
        background = imgBack.getImage();
        addKeyListener(new TAdapter());
        initGame();
    }
    public MultiplayerBoard(int player){
        this.player=player;
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        ImageIcon imgBack = new ImageIcon("Board.jpg");
        background = imgBack.getImage();
        addKeyListener(new TAdapter());
        initGame();
    }

    private void initGame() {
        lastLoopTime=System.nanoTime();
        lives= new int[4];
        bats= new Bat[4];
        ball = new Ball();
        ball.setPosition(200,200);
        ball.setVelocity();
        for(int i=0;i<4;i++){
            lives[i]=5;
            switch (i){
                case 0: bats[i]=new Bat(true);
                    bats[i].setPosition(180,385);
                    break;
                case 1: bats[i]=new Bat(false);
                    bats[i].setPosition(385,180);
                    break;
                case 2: bats[i]=new Bat(true);
                    bats[i].setPosition(180,5);
                    break;
                case 3: bats[i]=new Bat(false);
                    bats[i].setPosition(5,180);
                    break;
            }
        }
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

        g2d.drawImage(ball.image, (int)ball.x, (int)ball.y, this);

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
    private void move(double delta) {
        float x =(float)(ball.x + ((ball.dx)*delta));
        float y =(float)(ball.y + (ball.dy)*delta);
        ball.setPosition(x,y);
        for (int i = 0; i < 4; i++) {
            if(bats[i].AI)
            {
                Board.easyAI(i,bats,ball);
            }
            switch (i) {
                case 0:
                    if (((bats[i].x < 50 && bats[i].dx < 0) || (bats[i].x > 275 && bats[i].dx > 0)) && ((bats[i].y > 390 && bats[i].dy > 0) || (bats[i].y < 350 && bats[i].dy < 0))) {
                        bats[i].setPosition(bats[i].x, bats[i].y);
                        continue;
                    }
                    if (bats[i].x < 50 && bats[i].dx < 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].x > 275 && bats[i].dx > 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    }
                    if (bats[i].y > 390 && bats[i].dy > 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    } else if (bats[i].y < 350 && bats[i].dy < 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    }
                    break;
                case 1:
                    if (((bats[i].x < 350 && bats[i].dx < 0) || (bats[i].x > 390 && bats[i].dx > 0)) && ((bats[i].y > 275 && bats[i].dy > 0) || (bats[i].y < 50 && bats[i].dy < 0))) {
                        bats[i].setPosition(bats[i].x, bats[i].y);
                        continue;
                    }
                    if (bats[i].x < 350 && bats[i].dx < 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].x > 390 && bats[i].dx > 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].y > 275 && bats[i].dy > 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    } else if (bats[i].y < 50 && bats[i].dy < 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    }
                    break;
                case 2:
                    if (((bats[i].x < 50 && bats[i].dx < 0) || (bats[i].x > 275 && bats[i].dx > 0)) && ((bats[i].y > 40 && bats[i].dy > 0) || (bats[i].y < 0 && bats[i].dy < 0))) {
                        bats[i].setPosition(bats[i].x, bats[i].y);
                        continue;
                    }
                    if (bats[i].x < 50 && bats[i].dx < 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].x > 275 && bats[i].dx > 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].y > 40 && bats[i].dy > 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    } else if (bats[i].y < 0 && bats[i].dy < 0) {
                        bats[i].setPosition(bats[i].x + bats[i].dx, bats[i].y);
                        continue;
                    }
                    break;
                case 3:
                    if (((bats[i].x < 0 && bats[i].dx < 0) || (bats[i].x > 40 && bats[i].dx > 0)) && ((bats[i].y > 275 && bats[i].dy > 0) || (bats[i].y < 50 && bats[i].dy < 0))) {
                        bats[i].setPosition(bats[i].x, bats[i].y);
                        continue;
                    }
                    if (bats[i].x < 0 && bats[i].dx < 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].x > 40 && bats[i].dx > 0) {
                        bats[i].setPosition(bats[i].x, bats[i].y + bats[i].dy);
                        continue;
                    } else if (bats[i].y > 275 && bats[i].dy > 0) {
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
            if(collision==i)
                continue;
            switch (i){
                case 0:if(collision!=-1) {
                    if (bats[0].getBounds().intersects(ball.getBounds())) {
                        collision = 0;
                        ball.last=0;
                        Rectangle2D intersection = bats[0].getBounds().createIntersection(ball.getBounds());
                        if (intersection.getWidth() > intersection.getHeight()) {
                            ball.dy = -ball.dy + (float) (bats[0].dy) / 4;
                            ball.dx += (float) bats[0].dx / 10;
                            ball.y = ball.y < bats[0].y ? bats[0].y - 20 : bats[0].y + 10;
                        } else {
                            if((ball.x<bats[0].x&&ball.dx<0)||(ball.x+ball.WIDTH>bats[0].x+bats[0].WIDTH&&ball.dx>0)){
                                ball.dy = -ball.dy + (float) (bats[0].dy) / 4;
                                ball.dx += (float) bats[0].dx / 10;
                                ball.y = ball.y < bats[0].y ? bats[0].y - 20 : bats[0].y + 10;
                            }
                            else
                                ball.dx = -ball.dx;
                        }
                    }
                }
                    break;
                case 1:if(collision!=-2) {
                    if (bats[1].getBounds().intersects(ball.getBounds())) {
                        ball.last=1;
                        collision = 1;
                        Rectangle2D intersection = bats[1].getBounds().createIntersection(ball.getBounds());
                        if (intersection.getHeight() > intersection.getWidth()) {
                            ball.dx = -ball.dx + (float) bats[1].dx / 4;
                            ball.dy += (float) bats[1].dy / 10;
                            ball.x = ball.x < bats[1].x ? bats[1].x - 20 : bats[1].x + 10;
                        } else {
                            if((ball.y<bats[1].y&&ball.dy<0)||(ball.y+ball.HEIGHT>bats[1].WIDTH+bats[1].y&&ball.dy>0)){
                                ball.dx = -ball.dx + (float) bats[1].dx / 4;
                                ball.dy += (float) bats[1].dy / 10;
                                ball.x = ball.x < bats[1].x ? bats[1].x - 20 : bats[1].x + 10;
                            }
                            else
                                ball.dy = -ball.dy;
                        }
                    }
                }
                    break;
                case 2:if(collision!=-3) {
                    if (bats[2].getBounds().intersects(ball.getBounds())) {
                        ball.last=2;
                        collision = 2;
                        Rectangle2D intersection = bats[2].getBounds().createIntersection(ball.getBounds());
                        if (intersection.getWidth() > intersection.getHeight()) {
                            ball.dy = -ball.dy + (float) bats[2].dy / 4;
                            ball.dx += (float) bats[2].dx / 10;
                            ball.y = ball.y > bats[2].y ? bats[2].y + 10 : bats[2].y - 20;
                        } else {
                            if((ball.x<bats[2].x&&ball.dx<0)||(ball.x+ball.WIDTH>bats[2].x+bats[2].WIDTH&&ball.dx>0)){
                                ball.dy = -ball.dy + (float) bats[2].dy / 4;
                                ball.dx += (float) bats[2].dx / 10;
                                ball.y = ball.y > bats[2].y ? bats[2].y + 10 : bats[2].y - 20;
                            }
                            else
                                ball.dx = -ball.dx;
                        }
                    }
                }
                    break;
                case 3:if(collision!=-4) {
                    if (bats[3].getBounds().intersects(ball.getBounds())) {
                        ball.last=3;
                        collision = 3;
                        Rectangle2D intersection = bats[3].getBounds().createIntersection(ball.getBounds());
                        if (intersection.getHeight() > intersection.getWidth()) {
                            ball.dx = -ball.dx + (float) bats[3].dx / 4;
                            ball.dy += (float) bats[3].dy / 10;
                            ball.x = ball.x > bats[3].x ? bats[3].x + 10 : bats[3].x - 20;
                        } else {
                            if((ball.y<bats[3].y&&ball.dy<0)||(ball.y+ball.HEIGHT>bats[3].WIDTH+bats[3].y&&ball.dy>0)){
                                ball.dx = -ball.dx + (float) bats[3].dx / 4;
                                ball.dy += (float) bats[3].dy / 10;
                                ball.x = ball.x > bats[3].x ? bats[3].x + 10 : bats[3].x - 20;
                            }
                            else
                                ball.dy = -ball.dy;
                        }
                    }
                }
                    break;
            }
        }
        //limit the speed of the ball
        float speed=(float)Math.sqrt(ball.dx*ball.dx+ball.dy*ball.dy);
        if(speed>9){
            ball.dx=ball.dx*9/speed;
            ball.dy=ball.dy*9/speed;
        }

    }
    private void moveItems(float[] ballpos, int[][] bat, float[] ballv) {
        setBallPosition(ballpos);
//        setBallVelocity(ballv);
        for(int i=0;i<4;i++){
            if(!bats[i].AI)
            {
                bats[i].setPosition(bat[i][0],bat[i][1]);
            }
        }
    }
    private void moveItems(float[] ballpos, int[][] bat) {
        setBallPosition(ballpos);
        for(int i=0;i<4;i++){
            if(!bats[i].AI)
            {
                bats[i].setPosition(bat[i][0],bat[i][1]);
            }
        }
    }
    private void moveItems(int[][] bat) {
        for(int i=0;i<4;i++){
            if(!bats[i].AI)
            {
                bats[i].setPosition(bat[i][0],bat[i][1]);
            }
        }
    }
    private void checkIngame() {
        for(int i=0;i<4;i++){
            if(lives[i]<=0) {
                inGame[i]=false;
            }
        }
    }
    private void checkIngame(int[] lives) {
        this.lives=lives;
        for(int i=0;i<4;i++){
            if(lives[i]<=0) {
                inGame[i]=false;
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

    public void Update(boolean collision,float[] ballPos,int[][] bat, float[] ballv){
        int count=0;
        for (boolean x:inGame)
            if(x)
                count++;
        if(count>1){
//            checkCollision();
            setBallVelocity(ballv);
            checkIngame();
            if(collision)
                moveItems(ballPos,bat);
            else
                moveItems(bat);
            move(1);
        }
        repaint();
    }
    public void Update(boolean collision,float[] ballPos,int[][] bat){
        long now = System.nanoTime();
        long updateLength= now - lastLoopTime;
        lastLoopTime=now;
        // update the frame counter
        lastFpsTime += updateLength;
        fps++;

        // update our FPS counter if a second has passed since
        // we last recorded
        if (lastFpsTime >= 1000000000)
        {
            System.out.println("(FPS: "+fps+")");
            lastFpsTime = 0;
            fps = 0;
        }

        double delta = updateLength / ((double)OPTIMAL_TIME);
        int count=0;
        for (boolean x:inGame)
            if(x)
                count++;
        if(count>1){
            checkCollision();
            checkIngame();
            if(collision)
                moveItems(ballPos,bat);
            else
                moveItems(bat);
            move(delta);
        }
        repaint();
        try{
            Thread.sleep(Math.abs(lastLoopTime - System.nanoTime())/1000000);
        }
        catch (InterruptedException exp){
            exp.printStackTrace();
        }
    }
    public int[] getPlayerBatPosition(int player){
        int[] tmp = {bats[player].x,bats[player].y};
        return tmp;
    }

    public float[] getBallPosition(){
        float[] tmp = {ball.x,ball.y};
        return tmp;
    }
    public float[] getBallVelocity(){
        float[] tmp = {ball.dx,ball.dy};
        return tmp;
    }

    public int[] getLives(){
        return lives;
    }

    public void setBallVelocity(float[] vel){
        ball.setVelocity(vel[0],vel[1]);
    }
    public void setBallPosition(float[] pos){
        ball.setPosition(pos[0],pos[1]);
    }
}
