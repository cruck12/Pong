import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Sahil on 4/20/2016.
 */
public class Ball {
    float dx =0;
    float dy =0;
    float x;
    float y;
    int last=0;
    final int WIDTH=20;
    final int HEIGHT=20;
    Rectangle bounds;
    Image image;
    public Ball(){
        ImageIcon imgBall = new ImageIcon("ball.png");
        image = imgBall.getImage();
        bounds = new Rectangle((int)x,(int)y,image.getWidth(null),image.getHeight(null));
    }

    public void setPosition(float x, float y){
        this.x=x;
        this.y=y;
        bounds.setLocation((int)x,(int)y);
    }

    public void setVelocity(){
        Random rand = new Random();
        dx = rand.nextInt()%6 +1 ;
        dy = rand.nextInt()%6+ 1 ;
    }
    public void setVelocity(float dx, float dy){
        this.dx = dx ;
        this.dy = dy ;
    }

    public Rectangle getBounds(){
        return bounds;
    }
}
