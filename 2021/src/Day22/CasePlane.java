package Day22;

import java.util.ArrayList;
import java.util.List;

public class CasePlane {
    private PartB pb;

    public CasePlane(PartB pb) {
        this.pb = pb;
    }

    public void main(Cube cube, List<Cord> listOfCorners, boolean xPlane, boolean yPlane, boolean zPlane) throws XORException, IncorrectSizeException {
        if (xPlane && yPlane && zPlane) { //DOT
            System.out.println("CasePlane: implement all planes");
            System.exit(1);
        } else if (checkLine(xPlane, yPlane, zPlane)) { //LINE
            Cord lineMax = minmaxLine(true, listOfCorners);
            Cord lineMin = minmaxLine(false, listOfCorners);
            Cord remove = cube.cordsToBeRemoved.get(0);
            Cord intersect = cube.intersectionCords.get(0);
            listOfCorners.clear();
            if (lineMax == remove) {
                listOfCorners.add(lineMin);
                int x = lineMin.x == intersect.x ? intersect.x : intersect.x - 1;
                int y = lineMin.y == intersect.y ? intersect.y : intersect.y - 1;
                int z = lineMin.z == intersect.z ? intersect.z : intersect.z - 1;
                listOfCorners.add(new Cord(x, y, z));
                List<Cord> minmax = pb.findMinMax(listOfCorners);
                pb.processCube(new Cube(minmax.get(0), minmax.get(1), cube.on));
            } else if (lineMin == remove) {
                listOfCorners.add(lineMax);
                int x = lineMax.x == intersect.x ? intersect.x : intersect.x + 1;
                int y = lineMax.y == intersect.y ? intersect.y : intersect.y + 1;
                int z = lineMax.z == intersect.z ? intersect.z : intersect.z + 1;
                listOfCorners.add(new Cord(x, y, z));
                List<Cord> minmax = pb.findMinMax(listOfCorners);
                pb.processCube(new Cube(minmax.get(0), minmax.get(1), cube.on));
            } else {
                System.out.println("CasePlane: remove is neither lineMax nor lineMin");
                System.exit(1);
            }

        } else { //PLANE
            if (cube.cordsToBeRemoved.size() == 2) {


                System.out.println("REMOVE: ");
                for (Cord c : cube.cordsToBeRemoved) {
                    System.out.println(c.x + ", " + c.y + ", " + c.z);
                }
                System.out.println("INTERSECT: ");
                for (Cord c : cube.intersectionCords) {
                    System.out.println(c.x + ", " + c.y + ", " + c.z);
                }
                Cord remove = cube.cordsToBeRemoved.get(0);
                List<Cord> cube1 = new ArrayList<>();
                List<Cord> cube2 = new ArrayList<>();
                if (xPlane) {

                } else if (yPlane) {

                } else {
                    for (Cord c : listOfCorners) {
                        if (!(c.x == remove.x && c.y == remove.y && c.z == remove.z)) {
                            if (c.x == remove.x) {
                                cube1.add(c);
                            } else {
                                cube2.add(c);
                            }
                        }
                    }
                    pb.printCube(cube1);
                    pb.printCube(cube2);

                }
            } else {
                System.out.println("CasePlane: UGH\n" + cube.cordsToBeRemoved.size());
            }


            System.exit(3);
        }
    }

    public boolean checkLine(boolean xPlane, boolean yPlane, boolean zPlane) {
        boolean xLine = !xPlane && yPlane && zPlane;
        boolean yLine = xPlane && !yPlane && zPlane;
        boolean zLine = xPlane && yPlane && !zPlane;
        return xLine || yLine || zLine;
    }

    public Cord minmaxLine(boolean max, List<Cord> listOfCorners) {
        Cord chosen = listOfCorners.get(0);
        for (int i = 1; i < listOfCorners.size(); i++) {
            Cord c = listOfCorners.get(i);
            if ((max && (c.x > chosen.x || c.y > chosen.y || c.z > chosen.z)) ||
                    (!max && (c.x < chosen.x || c.y < chosen.y || c.z < chosen.z))) {
                chosen = c;
            }
        }
        return chosen;
    }
}
