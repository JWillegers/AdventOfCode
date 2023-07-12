package Day20;

public class Tile {
    Solution parent;
    /** ID of the tile */
    int id;
    /** All the pixels in the tile */
    boolean[][] pixels;
    /**Neighbours of this tile
     * 0 = North
     * 1 = East
     * 2 = South
     * 3 = West
     */
    Tile[] side;
    /** If true tile is not allowed to rotate or flip */
    boolean locked;

    public Tile(int id, boolean[][] tile, Solution solution) {
        this.id = id;
        this.pixels = tile;
        this.side = new Tile[4];
        this.locked = false;
        this.parent = solution;
    }

    /*
     * visualization grid for operations
     * a b c d
     * e f g h
     * i j k l
     * m n o p
     */

    /**
     * Rotate pixels 90 degrees clockwise
     */
    public void rotate() {
        for (int i = 0; i < parent.getTileSize() / 2; i++) {
            for (int j = 0; j < parent.getTileSize() / 2; j++) {
                boolean temp = pixels[i][j];
                int farEdge = parent.getTileSize() - 1;
                pixels[i][j] = pixels[farEdge - j][i];
                pixels[farEdge - j][i] = pixels[farEdge - i][farEdge - j];
                pixels[farEdge - i][farEdge - j] = pixels[j][farEdge - i];
                pixels[j][farEdge - i] = temp;
            }
        }
    }

    /**
     * Flip the pixels through vertical axis
     */
    public void flip() {
        for (int i = 0; i < parent.getTileSize(); i++) {
            for (int j = 0; j < parent.getTileSize() / 2; j++) {
                boolean temp = pixels[i][j];
                int farEdge = parent.getTileSize() - 1;
                pixels[i][j] = pixels[i][farEdge - j];
                pixels[i][farEdge - j] = temp;
            }
        }
    }

}
