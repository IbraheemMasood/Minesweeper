import javax.swing.*;
import java.awt.*;

/**
 * The images used in the program
 */
public class Resources {
    public static final ImageIcon MINEBIG = new ImageIcon("mineiconbig.png");
    public static final ImageIcon MINESMALL = new ImageIcon("mineiconsmall.png");
    public static final ImageIcon FLAG = new ImageIcon("flag.png");
    public static final ImageIcon SMILE0 = new ImageIcon("Smiley1.png"); //Neutral
    public static final ImageIcon SMILE1 = new ImageIcon("Smiley2.png"); //Scared
    public static final ImageIcon SMILE2 = new ImageIcon("Smiley3.png"); //Won
    public static final ImageIcon SMILE3 = new ImageIcon("Smiley4.png"); //Dead


    /**
     * Image resizer
     * @param width width of the object you want the image in.
     * @param height height of the object you want the image in.
     * @return scaled image
     */
    public static ImageIcon scaledImage(ImageIcon unscaledImage, int width, int height) {
        Image img = unscaledImage.getImage();
        return new ImageIcon(img.getScaledInstance((int) (width / 1.3), (int) (height / 1.3), Image.SCALE_FAST));
    }
}
