import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Tracks the number of flags on the board relative
 * to the number of mines. Checks for wins and losses
 * on flag change
 */
public class MineTracker extends JLabel {
    int numFlags;
    int mines;

    /**
     * Constructor
     * @param mines number of mines on a board
     */
    public MineTracker(int mines) {
        this.mines = mines;
        this.numFlags = mines;

        setText(String.valueOf(mines));
        setHorizontalAlignment(CENTER);
        setFont(new Font("Inter", Font.PLAIN, 20));
        setVisible(true);

    }

    /**
     * Ran when adding a flag anywhere on the board
     * runs helper method <>checkWon()</> when called
     */
    public void addFlag() {
        numFlags += 1;
        setText(String.valueOf((numFlags)));
        checkWon();
    }


    /**
     * Ran when removing a flag anywhere on the board
     * runs helper method <>checkWon()</> when called
     */
    public void removeFlag() {
        numFlags -= 1;
        setText(String.valueOf((numFlags)));
        checkWon();
    }

    /**
     * Checks if you won, runs game over method if won
     */
    public void checkWon() {
        int counter = 0;
        ArrayList<Integer> mineCoords = Main.game.getBoard().getMineCoords();
        if (numFlags == 0) {

            for (Integer mineCoord : mineCoords) {
                if (Main.game.getBoard().getButtonArr()[mineCoord / Main.game.getBoard().getColumns()][mineCoord % Main.game.getBoard().getColumns()].isFlagged()) {
                    counter += 1;
                }
            }

        }
        if (counter == mines) {
            Main.game.getBoard().gameOver(true);
        }
    }
}