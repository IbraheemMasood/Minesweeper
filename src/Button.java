import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;


/**
 * The game buttons
 */
public class Button extends JButton {
    private static int tileSize = 50; //Pixel size of tile
    private int value; //Number of adjacent mines
    private int x; //x location relative to other squares
    private int y;//y location relative to other squares
    private int[] location = new int[2]; //x and y location relative to other squares
    private boolean exposed = false;
    private boolean flagged = false;
    private int adjFlags; //Neighbouring flags


    /**
     * Constructor for game buttons
     * @param x x location relative to other buttons
     * @param y y location relative to other buttons
     * @param handler handler for mouse-inputs
     */
    public Button(int x, int y, MouseHandler handler) {
        this.x = x;
        this.y = y;
        //Save location
        location[0] = x;
        location[1] = y;

        //Format button
        setFont(new Font("Inter", Font.PLAIN, 15));
        setPreferredSize(new Dimension(tileSize, tileSize));
        setBorder(new CompoundBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED), new BevelBorder(BevelBorder.RAISED)), new BevelBorder(BevelBorder.RAISED))); //Very Beveled
        setBackground(new Color(67, 67, 67));
        setForeground(Color.BLACK);
        addMouseListener(handler);

    }

    /**
     * @param size The pixel width/height of a tile
     */
    public static void setTileSize(int size) {
        tileSize = size;
    }

    public static int getTileSize() {
        return tileSize;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int val) {
        value = val;
    }

    public int[] getLoc() {
        return location;
    }

    public void setLoc(int x, int y) {
        location[0] = x;
        location[1] = y;
    }

    public boolean isExposed() {
        return exposed;
    }


    /**
     * Exposes a button, ending the game on a loss
     * if the button is a mine
     */
    public void expose() {
        exposed = true;
        if (getValue() != 0) {
            setText(String.valueOf(getValue()));
        }

        setIcon(null);
        setBackground(Color.lightGray);
        setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED), new BevelBorder(BevelBorder.LOWERED)));

        if (getValue() == -1 && Main.game.getBoard().getGameState() != 3) {
            Main.game.getBoard().gameOver(false);
            setBackground(new Color(166, 73, 73));
        }


    }


    public boolean isFlagged() {
        return flagged;
    }

    /**
     * Add and remove flags from buttons
     */
    public void changeFlag() {
        flagged = !isFlagged();

        if (isFlagged()) {
            ImageIcon scaledImg = (Resources.scaledImage(Resources.FLAG, tileSize, tileSize));
            setIcon(scaledImg);
            for (int r = -1; r <= 1; r++) {
                for (int c = -1; c <= 1; c++) {
                    if (Main.game.getBoard().isValid(r + x, c + y)) {
                        Main.game.getBoard().getButtonArr()[r + x][c + y].addFlag(true);
                    }
                }
            }
        } else {
            setIcon(null);
            for (int r = -1; r <= 1; r++) {
                for (int c = -1; c <= 1; c++) {
                    if (Main.game.getBoard().isValid(r + x, c + y)) {
                        Main.game.getBoard().getButtonArr()[r + x][c + y].addFlag(false);
                    }
                }
            }
        }
    }

    /**
     * tracks adjacent flags, for use in square expose method
     * @param add true if adding a flag, false if removing
     */
    private void addFlag(boolean add) {
        if (add) {
            adjFlags += 1;
        } else {
            adjFlags -= 1;
        }
    }

    public int getAdjFlags() {
        return adjFlags;
    }

}
