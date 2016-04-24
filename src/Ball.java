import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Sahil on 4/20/2016.
 */
public class Ball {
    float dx =0;
    float dy =0;
    int x;
    int y;
    Rectangle bounds;
    Image image;
    public Ball(){
        ImageIcon imgBall = new ImageIcon("ball.png");
        image = imgBall.getImage();
        bounds = new Rectangle(x,y,image.getWidth(null),image.getHeight(null));
    }

    public void setPosition(int x, int y){
        this.x=x;
        this.y=y;
        bounds.setLocation(x,y);
    }

    public void setVelocity(){
        Random rand = new Random();
        dx = rand.nextInt()%6 +1 ;
        dy = rand.nextInt()%6+ 1 ;
    }

    public Rectangle getBounds(){
        return bounds;
    }
}
