import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The difficulty selection menu
 */
public class DifficultySelect extends JComboBox<String> {
    /**
     * A JComboBox that hold 4 options
     */
    DifficultySelect() {
        addItem("Easy");
        addItem("Medium");
        addItem("Hard");
        addItem("Custom");
    }

    /**
     * @return the difficulty selected. If custom
     * difficulty returns null generates a selection
     * menu and a game-board if custom difficulty is
     * selected
     */
    public String getDiff() {
        if (getSelectedItem() == "Custom") {
            //Custom difficulty selector, in a new window
            JFrame customise = new JFrame();
            customise.setLayout(new GridLayout(4, 1));

            SpinnerNumberModel r = new SpinnerNumberModel(10, 1, 30, 1);
            SpinnerNumberModel c = new SpinnerNumberModel(10, 1, 60, 1);
            SpinnerNumberModel m = new SpinnerNumberModel(8, 1, null, 1);


//Selectors, labels, confirm button
            JSpinner pickRows = new JSpinner(r);
            JLabel labelR = new JLabel("Rows:");
            JSpinner pickCols = new JSpinner(c);
            JLabel labelC = new JLabel("Columns:");
            JSpinner pickMines = new JSpinner(m);
            JLabel labelM = new JLabel("Mines:");
            JButton confirm = new JButton();
            JLabel ok = new JLabel("Generate ");

//Label format
            labelR.setHorizontalAlignment(SwingConstants.RIGHT);
            labelC.setHorizontalAlignment(SwingConstants.RIGHT);
            labelM.setHorizontalAlignment(SwingConstants.RIGHT);
            ok.setHorizontalAlignment(SwingConstants.RIGHT);

//Default values
            pickRows.setValue(8);
            pickCols.setValue(8);
            pickMines.setValue(10);

//Add objects to JFrame
            customise.add(labelR);
            customise.add(pickRows);

            customise.add(labelC);
            customise.add(pickCols);

            customise.add(labelM);
            customise.add(pickMines);

            customise.add(ok);
            customise.add(confirm);


            customise.setPreferredSize(new Dimension(200, 300));
            customise.setVisible(true);
            customise.pack();
/*
generate the board when confirm button is pressed
if the selected mine-count is over the number of
squares, use rows*columns as the number of mines
instead
*/
            confirm.setAction(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Main.game.getBoard().generate((int) r.getNumber(), (int) c.getNumber(), Math.min((int) m.getNumber(), ((int) r.getNumber() * (int) c.getNumber())));
                    customise.dispose();
                }
            });
        }
        return (String) getSelectedItem();
    }
}