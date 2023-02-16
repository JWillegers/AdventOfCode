package Day19;

import java.util.ArrayList;
import java.util.List;

public class Beacon {
    protected Cord cord;
    protected List<Cord> relativePositionsOtherBeacons;

    public Beacon(int x, int y, int z) {
        this.cord = new Cord(x, y ,z);
        this.relativePositionsOtherBeacons = new ArrayList<>();
    }
}
