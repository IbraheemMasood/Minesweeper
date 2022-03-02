import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;

/**
 * The smiley-face and the reset button
 */
public class ResetButton extends JButton {

    public ResetButton(MouseHandler mouseHandler) {
        addMouseListener(mouseHandler);
        setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED), new BevelBorder(BevelBorder.RAISED)));
        setIcon(Resources.scaledImage(Resources.SMILE0, 100, 100));
        setBackground(new Color(255, 235, 0));
    }


    /**
     * sets the emotion of the smiley to one of four options
     * @param state a value from 0->3
     */
    public void setState(int state) {
        if (state == 0) {//won
            setIcon(Resources.scaledImage(Resources.SMILE2, 100, 100));
        } else if (state == 1) {//normal
            setIcon(Resources.scaledImage(Resources.SMILE0, 100, 100));
        } else if (state == 2) {//scared
            setIcon(Resources.scaledImage(Resources.SMILE1, 100, 100));
        } else if (state == 3) {//dead
            setIcon(Resources.scaledImage(Resources.SMILE3, 100, 100));
        }
    }


}
