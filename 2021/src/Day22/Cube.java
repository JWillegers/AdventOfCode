package Day22;

import java.util.ArrayList;
import java.util.List;

public class Cube {
    boolean on;
    protected Cord min;
    protected Cord max;
    protected List<Cord> intersectionCords;
    protected List<Cord> cordsToBeRemoved;

    public Cube(int minx, int miny, int minz, int maxx, int maxy, int maxz, boolean on) {
        this.intersectionCords = new ArrayList<>();
        this.cordsToBeRemoved = new ArrayList<>();
        this.min = new Cord(minx, miny, minz);
        this.max = new Cord(maxx, maxy, maxz);
        this.on = on;
    }

    public Cube(Cord min, Cord max, boolean on) {
        this.intersectionCords = new ArrayList<>();
        this.cordsToBeRemoved = new ArrayList<>();
        this.min = min;
        this.max = max;
        this.on = on;
    }
}
