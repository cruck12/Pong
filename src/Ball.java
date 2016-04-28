import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Sahil on 4/20/2016.
 */
public class Ball {
    // ball velocity
    float dx =0;
    float dy =0;

    //ball positions
    float x;
    float y;

    //last hit by whom
    int last=0;
    final int WIDTH=20;
    final int HEIGHT=20;
    Rectangle bounds;
    Image image;

    //initialize the ball with its image and bounds
    public Ball(){
        ImageIcon imgBall = new ImageIcon(getClass().getResource("ball.png"));
        image = imgBall.getImage();
        bounds = new Rectangle((int)x,(int)y,image.getWidth(null),image.getHeight(null));
    }
    //set position at some coordinate
    public void setPosition(float x, float y){
        this.x=x;
        this.y=y;
        bounds.setLocation((int)x,(int)y);
    }
    //set random velocity to begin the game
    public void setVelocity(){
        Random rand = new Random();
        dx = rand.nextInt()%6 +1 ;
        dy = rand.nextInt()%6+ 1 ;
    }

    //set particular velocity- multiplayer
    public void setVelocity(float dx, float dy){
        this.dx = dx ;
        this.dy = dy ;
    }

    //get bounding rectangle for ball sprite
    public Rectangle getBounds(){
        return bounds;
    }
}
