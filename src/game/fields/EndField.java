package game.fields;

/**
 * Represents target field in pacman.
 */
public class EndField extends PathField {

    /**
     * Initializes an empty {@link EndField}.
     *
     * @param row Row index.
     * @param col Column index.
     */
    public EndField(int row, int col) {
        super(row, col);
    }
}
