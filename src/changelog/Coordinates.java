package changelog;

/**
 * Represents coordinate in {@link Changelog}.
 */
public class Coordinates {
    public int row;
    public int col;

    /**
     * Create Coordinate with given values.
     * @param row Row index.
     * @param col Column index.
     */
    public Coordinates(int row, int col){
        this.row = row;
        this.col = col;
    }

}
