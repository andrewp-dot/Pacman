package game.fields;

/**
 * Represents a starting Field in pacman.
 * @author Ondřej Vrána
 */
public class StartField extends PathField {
    /**
     * Initializes an empty {@link StartField}.
     *
     * @param row Row index.
     * @param col Column index.
     */
    public StartField(int row, int col) {
        super(row, col);
    }

}
