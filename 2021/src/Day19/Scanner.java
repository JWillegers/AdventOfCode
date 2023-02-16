package Day19;

import java.util.ArrayList;
import java.util.List;

public class Scanner{
    protected boolean matched;
    protected Cord cord;
    protected List<Beacon> beacons;

    public Scanner() {
        this.matched = false;
        this.beacons = new ArrayList<>();
        this.cord = new Cord();
    }

    public void calculateRelativePositions() {
        for(int i = 0; i < beacons.size(); i++) {
            Beacon b = beacons.get(i);
            int bx = b.cord.x;
            int by = b.cord.y;
            int bz = b.cord.z;
            for(int j = 0; j < beacons.size(); j++) {
                if (j == i) {
                    b.relativePositionsOtherBeacons.add(new Cord(0, 0, 0));
                } else {
                    Cord c = beacons.get(j).cord;
                    b.relativePositionsOtherBeacons.add(new Cord(c.x-bx, c.y-by, c.z-bz));
                }
            }
        }
    }

    /*
    Cords of beacons will be their position relative to scanner 0 (located at 0, 0, 0)
    Face meaning
    0: x,y,z
    1: x,z,y
    2: y,x,z
    3: y,z,x
    4: z,x,y
    5: z,y,x
     */
    public void alignBeacons(int face, int mx, int my, int mz) {
        for(Beacon b : beacons) {
            b.relativePositionsOtherBeacons.clear();
            if (face == 0) {
                b.cord.x = mx*b.cord.x + cord.x;
                b.cord.y = my*b.cord.y + cord.y;
                b.cord.z = mz*b.cord.z + cord.z;
            } else if (face == 1) {
                int p = my*b.cord.y + cord.z;
                int q = mz*b.cord.z + cord.y;
                b.cord.x = mx*b.cord.x + cord.x;
                b.cord.y = q;
                b.cord.z = p;
            } else if (face == 2) {
                int p = my*b.cord.y + cord.x;
                int q = mx*b.cord.x + cord.y;
                b.cord.x = p;
                b.cord.y = q;
                b.cord.z = mz*b.cord.z + cord.z;
            } else if (face == 3) {
                int p = mx*b.cord.x + cord.y;
                int q = my*b.cord.y + cord.z;
                int r = mz*b.cord.z + cord.x;
                b.cord.x = r;
                b.cord.y = p;
                b.cord.z = q;
            } else if (face == 4) {
                int p = mx*b.cord.x + cord.z;
                int q = my*b.cord.y + cord.x;
                int r = mz*b.cord.z + cord.y;
                b.cord.x = q;
                b.cord.y = r;
                b.cord.z = p;
            } else {
                int p = mx*b.cord.x + cord.z;
                int q = mz*b.cord.z + cord.x;
                b.cord.x = q;
                b.cord.y = my*b.cord.y + cord.y;
                b.cord.z = p;
            }
        }

        calculateRelativePositions();
    }
}
