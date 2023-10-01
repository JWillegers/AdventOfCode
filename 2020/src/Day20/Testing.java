package Day20;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Testing {

    @Test
    void rotate() {
        /*
         * Starting pixels
         * 1 0 0 0
         * 1 0 0 0
         * 0 1 0 0
         * 0 0 0 0
         *
         * expected results
         * 0 0 1 1
         * 0 1 0 0
         * 0 0 0 0
         * 0 0 0 0
         */
        Solution solution = new Solution();
        solution.setTileSize(4);
        String[][] pixels = new String[4][4];
        pixels[0][0] = "#";
        pixels[1][0] = "#";
        pixels[2][1] = "#";

        Tile t = new Tile(0, pixels, solution);
        t.rotate();
        assertNull(t.pixels[0][0]);
        assertEquals("#", t.pixels[0][3]);
        assertNull(t.pixels[1][0]);
        assertEquals("#", t.pixels[0][2]);
        assertNull(t.pixels[2][1]);
        assertEquals("#", t.pixels[1][1]);
    }

    @Test
    void flip() {
        /*
         * Starting pixels
         * 1 0 0 0
         * 1 0 0 0
         * 0 1 0 0
         * 0 0 0 0
         *
         * expected results
         * 0 0 0 1
         * 0 0 0 1
         * 0 0 1 0
         * 0 0 0 0
         */
        Solution solution = new Solution();
        solution.setTileSize(4);
        String[][] pixels = new String[4][4];
        pixels[0][0] = "#";
        pixels[1][0] = "#";
        pixels[2][1] = "#";

        Tile t = new Tile(0, pixels, solution);
        t.flip();
        assertNull(t.pixels[0][0]);
        assertEquals("#", t.pixels[0][3]);
        assertNull(t.pixels[1][0]);
        assertEquals("#", t.pixels[1][3]);
        assertNull(t.pixels[2][1]);
        assertEquals("#", t.pixels[2][2]);
    }
}
