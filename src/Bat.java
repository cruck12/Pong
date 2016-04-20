import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Sahil on 4/20/2016.
 */
public class Bat {
    int dx =0;
    int dy =0;
    int x;
    int y;
    Rectangle bounds;
    // bat is 75 px wide, 10 px tall
    //could also possibly make sprite/ implement with rectangle: sprite seems better
    Image image;
    boolean horizontal=true;
    public Bat(){
        ImageIcon imgBat = new ImageIcon("batHorizontal.png");
        image = imgBat.getImage();
        bounds = new Rectangle(x,y,image.getWidth(null),image.getHeight(null));
    }
    public Bat(boolean horizontal){
        this.horizontal=horizontal;
        if(this.horizontal){
            ImageIcon imgBat = new ImageIcon("batHorizontal.png");
            image = imgBat.getImage();
            bounds = new Rectangle(x,y,image.getWidth(null),image.getHeight(null));
        }
        else
        {
            ImageIcon imgBat = new ImageIcon("bat.png");
            image = imgBat.getImage();
            bounds = new Rectangle(x,y,image.getWidth(null),image.getHeight(null));
        }
    }

    public void setPosition(int x, int y){
        this.x=x;
        this.y=y;
        bounds.setLocation(x,y);
    }

    public void resetVelocity() {
        dx=0;
        dy=0;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = -5;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 5;
        }
        if (key == KeyEvent.VK_UP) {
            dy = -5;
        }
        if (key == KeyEvent.VK_DOWN) {
            dy = 5;
        }
    }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }
        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

}
