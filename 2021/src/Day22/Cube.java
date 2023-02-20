package Day22;

import java.util.ArrayList;
import java.util.List;

public class Cube {
    List<Cord> corners;
    boolean on;

    protected Cube(boolean on) {
        this.corners = new ArrayList<>();
        this.on = on;
    }

    protected void addCorner(Cord c) {
        corners.add(c);
    }

    protected void removeCorner(Cord c) {corners.remove(c); }

    protected void generatePerfectCube(int offset, int size) {
        for (int i = offset; i < offset+size+1; i += size) {
            for (int j = offset; j < offset+size+1; j += size) {
                for (int k = offset; k < offset+size+1; k += size) {
                    this.addCorner(new Cord(i, j, k));
                }
            }
        }
    }

    protected void generateOffsetCube(int offsetX, int offsetY, int offsetZ, int size) {
        for (int i = offsetX; i < offsetX+size+1; i += size) {
            for (int j = offsetY; j < offsetY+size+1; j += size) {
                for (int k = offsetZ; k < offsetZ+size+1; k += size) {
                    this.addCorner(new Cord(i, j, k));
                }
            }
        }
    }
}
