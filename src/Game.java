/**
 * Here for scaling, allows multiple unique
 * games to run parallel
 */
public class Game {
    private final Grid board;

    Game() {
        this.board = new Grid(8, 8, 10);
    }

    public Grid getBoard() {
        return board;
    }
}
