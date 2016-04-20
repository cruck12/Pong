import javax.swing.*;
import java.awt.*;

/**
 * Created by Sahil on 4/20/2016.
 */
public class Bat {
    int dx =0;
    int dy =0;
    int x;
    int y;
    // bat is 75 px wide, 10 px tall
    //could also possibly make sprite/ implement with rectangle: sprite seems better
    Image image;
    boolean horizontal=true;
    public Bat(){
        ImageIcon imgBat = new ImageIcon("batHorizontal.png");
        image = imgBat.getImage();
    }
    public Bat(boolean horizontal){
        this.horizontal=horizontal;
        if(this.horizontal){
            ImageIcon imgBat = new ImageIcon("batHorizontal.png");
            image = imgBat.getImage();
        }
        else
        {
            ImageIcon imgBat = new ImageIcon("bat.png");
            image = imgBat.getImage();
        }
    }

    public void setPosition(int x, int y){
        this.x=x;
        this.y=y;
    }
}
