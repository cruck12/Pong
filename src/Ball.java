import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Sahil on 4/20/2016.
 */
public class Ball {
    int dx =0;
    int dy =0;
    int x;
    int y;
    Image image;
    public Ball(){
        ImageIcon imgBall = new ImageIcon("ball.png");
        image = imgBall.getImage();
    }

    public void setPosition(int x, int y){
        this.x=x;
        this.y=y;
    }

    public void setVelocity(){
        Random rand = new Random();
        dx = rand.nextInt()%6;
        dy = rand.nextInt()%6+1;
    }
}
