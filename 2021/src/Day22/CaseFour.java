package Day22;

import java.util.List;

public class CaseFour {

    /**
     * Adjust cube in case there are four points that need to be removed
     * @param cube
     * @param listOfCorners
     */
    public void caseFour(Cube cube, List<Cord> listOfCorners, PartB pb) throws IncorrectSizeException, XORException {
        String methodname = new Object() {}.getClass().getEnclosingMethod().getName();
        if (cube.intersectionCords.size() == 4) {
            //since there are 4 intersection points, they are on a plane. So first we need to find that plane
            Cord intersectionCord0 = cube.intersectionCords.get(0);
            Cord c1 = cube.intersectionCords.get(1);
            boolean xPlane = intersectionCord0.x == c1.x;
            boolean yPlane = intersectionCord0.y == c1.y;
            boolean zPlane = intersectionCord0.z == c1.z;
            for (int i = 2; i <= 3; i++) {
                if (xPlane && yPlane || xPlane && zPlane || yPlane && zPlane) {
                    Cord c2 = cube.intersectionCords.get(i);
                    xPlane = xPlane && intersectionCord0.x == c2.x;
                    yPlane = yPlane && intersectionCord0.y == c2.y;
                    zPlane = zPlane && intersectionCord0.z == c2.z;
                }
            }
            //check that we found only one plane
            if (!((xPlane || yPlane || zPlane) && !(xPlane && yPlane || xPlane && zPlane || yPlane && zPlane))) {
                throw new XORException(methodname, xPlane, yPlane, zPlane);
            }
            //because the adjusted cube should not overlap with the cube it intersects with, we need to find the correct offset with the intersection points
            int edge;
            if (xPlane) {
                if (cube.cordsToBeRemoved.get(0).x != intersectionCord0.x) {
                    edge = Math.abs(cube.cordsToBeRemoved.get(0).x - intersectionCord0.x - 1) < Math.abs(cube.cordsToBeRemoved.get(0).x - intersectionCord0.x + 1) ? intersectionCord0.x - 1 : intersectionCord0.x + 1;
                } else {
                    edge = Math.abs(listOfCorners.get(0).x - intersectionCord0.x - 1) > Math.abs(listOfCorners.get(0).x - intersectionCord0.x + 1) ? intersectionCord0.x - 1 : intersectionCord0.x + 1;
                }
                for (Cord c : cube.cordsToBeRemoved) {
                    listOfCorners.remove(c);
                    listOfCorners.add(new Cord(edge, c.y, c.z));
                }
            } else if (yPlane) {
                if (cube.cordsToBeRemoved.get(0).y != intersectionCord0.y) {
                    edge = Math.abs(cube.cordsToBeRemoved.get(0).y - intersectionCord0.y - 1) < Math.abs(cube.cordsToBeRemoved.get(0).y - intersectionCord0.y + 1) ? intersectionCord0.y - 1 : intersectionCord0.y + 1;
                } else {
                    edge = Math.abs(listOfCorners.get(0).y - intersectionCord0.y - 1) > Math.abs(listOfCorners.get(0).y - intersectionCord0.y + 1) ? intersectionCord0.y - 1 : intersectionCord0.y + 1;
                }
                for (Cord c : cube.cordsToBeRemoved) {
                    listOfCorners.remove(c);
                    listOfCorners.add(new Cord(c.x, edge, c.z));
                }
            } else {
                if (cube.cordsToBeRemoved.get(0).z != intersectionCord0.z) {
                    edge = Math.abs(cube.cordsToBeRemoved.get(0).z - intersectionCord0.z - 1) < Math.abs(cube.cordsToBeRemoved.get(0).z - intersectionCord0.z + 1) ? intersectionCord0.z - 1 : intersectionCord0.z + 1;
                } else {
                    edge = Math.abs(listOfCorners.get(0).z - intersectionCord0.z - 1) > Math.abs(listOfCorners.get(0).z - intersectionCord0.z + 1) ? intersectionCord0.z - 1 : intersectionCord0.z + 1;
                }
                for (Cord c : cube.cordsToBeRemoved) {
                    listOfCorners.remove(c);
                    listOfCorners.add(new Cord(c.x, c.y, edge));
                }
            }
            List<Cord> minmax = pb.findMinMax(listOfCorners);
            pb.processCube(new Cube(minmax.get(0), minmax.get(1), cube.on));
        } else {
            for (Cord c : cube.intersectionCords) {
                System.out.println(c.x + ", " + c.y + ", " + c.z);
            }
            throw new IncorrectSizeException(methodname, 4, cube.intersectionCords.size());
        }
    }
}
