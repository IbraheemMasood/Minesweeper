import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

/**
 * The mouse-handler for all interactive
 * elements
 */
public class MouseHandler implements MouseListener {

    private boolean isRMBPressed = false;
    private boolean isLMBPressed = false;

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getSource() instanceof Button pressed) { //if input is on a game-square

            if (SwingUtilities.isLeftMouseButton(e)) { //Updates boolean if click originated from LMB
                isLMBPressed = true;
            }

            if (SwingUtilities.isRightMouseButton(e)) { //Updates boolean if click originated from RMB
                isRMBPressed = true;
            }


            if (Main.game.getBoard().isGameOver()) { //Ignores input if game is over
                return;
            }

            /*
             exposes adjacent tiles to clicked
             button if rmb and lmb are pressed in
             unison, and criteria is met
            */
            if (pressed.isExposed() && isLMBPressed && isRMBPressed && Objects.equals(pressed.getText(), Integer.toString(pressed.getAdjFlags()))) {
                Main.game.getBoard().squareExpose(Main.game.getBoard().getButtonArr()[pressed.getLoc()[0]][pressed.getLoc()[1]]);
            }

            if (!pressed.isExposed() && e.getButton() == MouseEvent.BUTTON3) { //If right click, change flag
                pressed.changeFlag();
                if (pressed.isFlagged()) {
                    Main.game.getBoard().getTracker().removeFlag();
                } else {
                    Main.game.getBoard().getTracker().addFlag();
                }

            } else if (!pressed.isFlagged() && !pressed.isExposed()) { //Expose if not already exposed, and un-flagged

                pressed.expose();

                if (!Main.game.getBoard().isGameOver()) {
                    Main.game.getBoard().getResetButton().setState(2); //Give reset button anxiety while mouse is pressed
                }

                if (pressed.getValue() == 0) { //massExpose if input square has no adjacent mines
                    Main.game.getBoard().massExpose(pressed);
                }

            }
        }
        /*
        Check if input is on reset button, generate new
        board from preselected difficulties
        */
        if (e.getSource() instanceof ResetButton) {

            Object difficulty = Main.game.getBoard().getDifficultySelect().getDiff();
            if (difficulty == "Easy") {
                Main.game.getBoard().generate(8, 8, 10);
            } else if (difficulty == "Medium") {
                Main.game.getBoard().generate(16, 16, 40);
            } else if (difficulty == "Hard") {
                Main.game.getBoard().generate(16, 30, 99);
            }
        }
    }


    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {

//Update variables
        if (SwingUtilities.isLeftMouseButton(e)) {
            isLMBPressed = false;
        }

        if (SwingUtilities.isRightMouseButton(e)) {
            isRMBPressed = false;
        }

        if (!Main.game.getBoard().isGameOver()) {
            Main.game.getBoard().getResetButton().setState(1);
        }

    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
