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
        boolean[][] pixels = new boolean[4][4];
        pixels[0][0] = true;
        pixels[1][0] = true;
        pixels[2][1] = true;

        Tile t = new Tile(0, pixels, solution);
        t.rotate();
        assertFalse(t.pixels[0][0]);
        assertTrue(t.pixels[0][3]);
        assertFalse(t.pixels[1][0]);
        assertTrue(t.pixels[0][2]);
        assertFalse(t.pixels[2][1]);
        assertTrue(t.pixels[1][1]);
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
        boolean[][] pixels = new boolean[4][4];
        pixels[0][0] = true;
        pixels[1][0] = true;
        pixels[2][1] = true;

        Tile t = new Tile(0, pixels, solution);
        t.flip();
        assertFalse(t.pixels[0][0]);
        assertTrue(t.pixels[0][3]);
        assertFalse(t.pixels[1][0]);
        assertTrue(t.pixels[1][3]);
        assertFalse(t.pixels[2][1]);
        assertTrue(t.pixels[2][2]);
    }
}
