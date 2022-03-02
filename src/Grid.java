import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class manages all the game pieces. generating and
 * resetting the game, managing win and loss and storing board data.
 * @author Ibraheem Masood
 */
public class Grid {

    private JPanel gameTools;
    private JFrame gameFrame;
    private JPanel gameGrid;
    //Board size and number of mines
    private int rows;
    private int columns;
    private int mines;
    private Button[][] buttonArr;
    private int[][] boardArr;
    private boolean gameOver;
    private ResetButton resetButton;
    private MineTracker mineTracker;
    private MyTimer myTimer;
    private ArrayList<Integer> mineCoords = new ArrayList<>(); //Used to get mine locations
    private final DifficultySelect difficultySelect = new DifficultySelect();
    private boolean bigIcons = true;

    /**
     * Generates first board sets initial format.
     */
    Grid(int rows, int columns, int mines) {
        this.gameFrame = new JFrame();
        this.gameGrid = new JPanel();
        this.gameTools = new JPanel();

/*
Depreciated, should be moved to generate function.
        for (int row = 0; row < rows; row++) {//Print out the board
            for (int col = 0; col < columns; col++) {
                if (boardArr[row][col] == -1) {
                    System.out.print("|-1 |");

                } else {
                    System.out.print("| " + boardArr[row][col] + " |");

                }
            }
            System.out.println();
        }
*/

        generate(rows, columns, mines);

        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameGrid.setLayout(new GridLayout(rows, columns));
        gameTools.setLayout(new GridLayout(0, 4));
        gameTools.setPreferredSize(new Dimension(columns * Button.getTileSize(), 100));
        gameFrame.setLayout(new GridBagLayout());
        GridBagConstraints gridPlacement = new GridBagConstraints();
        gridPlacement.gridx = 0;
        gridPlacement.gridy = 1;
        GridBagConstraints toolPlacement = new GridBagConstraints();
        toolPlacement.gridx = 0;
        toolPlacement.gridy = 0;

        toolPlacement.anchor = GridBagConstraints.PAGE_START;
        gameFrame.add(gameTools, toolPlacement);
        gameFrame.add(gameGrid, gridPlacement);

        gameFrame.setVisible(true);
        gameFrame.pack();


    }


    /**
     * Helper function for checking if a point exists
     * checks row and column, comparing to the dimensions
     * of the board.
     * @param row the row of the point
     * @param col the column of the point
     * @return  true if the point exists. False if it doesn't.
     */
    public boolean isValid(int row, int col) {

        boolean rowLow = row >= 0;
        boolean rowHigh = row < rows;
        boolean colLow = col >= 0;
        boolean colHigh = col < columns;
        return rowLow && rowHigh && colLow && colHigh;
    }


    /**
     * runs the game ending sequences, setting smiley emotion
     * to either win or dead, and exposing squares
     * @param won boolean checking if the game was ended due to a win or a loss
     */
    public void gameOver(boolean won) {
        myTimer.stop();
        ImageIcon mineIcon = Resources.MINEBIG;
        if (!bigIcons) {
            mineIcon = Resources.MINESMALL;
        }
        resetButton.setState(3);


        if (!won) {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if (!buttonArr[row][col].isExposed()) {
                        buttonArr[row][col].expose();
                    }
                    if (buttonArr[row][col].getValue() == -1) {
                        buttonArr[row][col].setIcon(mineIcon);
                        buttonArr[row][col].setText(null);
                    }
                }
            }
        } else {
            getResetButton().setState(0);
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    if (!buttonArr[row][col].isExposed() && !buttonArr[row][col].isFlagged()) {
                        buttonArr[row][col].expose();
                    }
                }
            }
        }

        gameOver = true;

    }

    /**
     * Recursive function to expose all non-negative squares around a 0
     *
     * @param button The button to expose
     */
    public void massExpose(Button button) {
        int x = button.getLoc()[0];
        int y = button.getLoc()[1];
        buttonArr[x][y].expose();


        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isValid(x + i, y + j) && !buttonArr[x + i][y + j].isExposed() && buttonArr[x + i][y + j].getValue() == 0 && !buttonArr[x + i][y + j].isFlagged()) {
                    massExpose(buttonArr[x + i][y + j]);
                } else if (isValid(x + i, y + j) && !buttonArr[x + i][y + j].isExposed() && buttonArr[x + i][y + j].getValue() > 0 && !buttonArr[x + i][y + j].isFlagged()) {
                    buttonArr[x + i][y + j].expose();
                }
            }

        }

    }

    /**
     * Exposes adjacent buttons, if the number of flags
     * around the initial button matches the number of
     * mines around said button
     * @param button the button to square expose
     */
    public void squareExpose(Button button) {
        int x = button.getLoc()[0];
        int y = button.getLoc()[1];


        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isValid(x + i, y + j) && !buttonArr[x + i][y + j].isFlagged()) {
                    buttonArr[x + i][y + j].expose();
                }
            }

        }
    }


    /**
     * Takes in the values of a game, and generates a new board
     */
    public void generate(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        this.boardArr = new int[rows][columns];
        this.buttonArr = new Button[rows][columns];
        int gridSize = rows * columns;
        this.resetButton = new ResetButton(new MouseHandler());
        this.mineTracker = new MineTracker(mines);
        this.gameOver = false;
        this.myTimer = new MyTimer();
        this.mineCoords = new ArrayList<>();
        Random rand = new Random();

        if (rows > 18 || columns > 36) {
            Button.setTileSize(30);
            bigIcons = false;
        } else {
            Button.setTileSize(50);
            bigIcons = true;
        }

        gameGrid.removeAll();
        gameTools.removeAll();
        //gameFrame.setPreferredSize(new Dimension(Button.TILE_SIZE * columns, Button.TILE_SIZE * (rows+1)));
        gameGrid.setPreferredSize(new Dimension(Button.getTileSize() * columns, Button.getTileSize() * rows));
        int mineCount = 0;
        while (mineCount < mines) {//Generate mine locations
            int number = rand.nextInt(0, gridSize);
            if (!mineCoords.contains(number)) {
                mineCoords.add(number);
            }
            mineCount = mineCoords.size();

        }

        for (Integer mineCoord : mineCoords) {//Place mines
            boardArr[mineCoord / columns][mineCoord % columns] = -1;
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (boardArr[row][col] == 0) {
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            if (isValid(row + i, col + j) && boardArr[row + i][col + j] == -1) {
                                boardArr[row][col] += 1;
                            }
                        }
                    }
                }
            }
        }


        gameGrid.setLayout(new GridLayout(rows, columns));

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Button button = new Button(row, col, new MouseHandler());
                button.setValue(boardArr[row][col]);
                button.setLoc(row, col);
                buttonArr[row][col] = button;
                gameGrid.add(button);
            }
        }

        myTimer.start();


        gameTools.add(difficultySelect);
        gameTools.add(myTimer);
        gameTools.add(resetButton);
        gameTools.add(mineTracker);
        gameFrame.setVisible(true);
        gameFrame.pack();
    }


    public int getColumns() {
        return columns;
    }

    public MineTracker getTracker() {
        return mineTracker;
    }

    public ResetButton getResetButton() {
        return resetButton;
    }

    public ArrayList<Integer> getMineCoords() {
        return mineCoords;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Button[][] getButtonArr() {
        return buttonArr;
    }

    public DifficultySelect getDifficultySelect() {
        return difficultySelect;
    }
}









