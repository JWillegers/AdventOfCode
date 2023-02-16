package Day22;

import java.util.ArrayList;
import java.util.List;

public class CaseTwo {
    private PartB pb;
    private int line;

    public CaseTwo(PartB pb) {
        this.pb = pb;
        this.line = 0;
    }

    /**
     * If there are two points to be removed, find the 2 new cubes that need to be processed
     * @param cube
     * @param listOfCorners
     * @throws IncorrectSizeException
     * @throws XORException
     */
    public void main(Cube cube, List<Cord> listOfCorners) throws IncorrectSizeException, XORException {
        String methodname = new Object() {}.getClass().getEnclosingMethod().getName();
        if (cube.intersectionCords.size() == 4) {
            //the two cords that need to be removed lie on one line, so we need to find that line
            Cord r0 = cube.cordsToBeRemoved.get(0);
            Cord r1 = cube.cordsToBeRemoved.get(1);
            boolean xLine = r0.y == r1.y && r0.z == r1.z;
            boolean yLine = r0.x == r1.x && r0.z == r1.z;
            boolean zLine = r0.y == r1.y && r0.x == r1.x;
            if (!((xLine || yLine || zLine) && !((xLine && yLine) || (xLine && zLine) || (yLine && zLine)))) {
                throw new XORException(methodname, xLine, yLine, zLine);
            }
            //Now we need to find the 2 new cubes
            List<PartB.Direction> directionList = new ArrayList<>();
            if (xLine) {
                directionList.add(PartB.Direction.Z);
                directionList.add(PartB.Direction.X);
                directionList.add(PartB.Direction.Y);
            } else if (yLine) {
                directionList.add(PartB.Direction.X);
                directionList.add(PartB.Direction.Y);
                directionList.add(PartB.Direction.Z);
            } else {
                directionList.add(PartB.Direction.Y);
                directionList.add(PartB.Direction.Z);
                directionList.add(PartB.Direction.X);
            }
            List<List<Cord>> llc = createSecondCube(listOfCorners, r0, r1, directionList.get(0));
            listOfCorners = llc.get(0);
            List<Cord> secondCube = llc.get(1);
            findNewCornersBasedOnIntersectionCords(cube, r0, listOfCorners, secondCube, directionList.get(0), directionList.get(1));
            secondCube = finishSecondCube(secondCube, directionList.get(2));
            finalStep(listOfCorners, secondCube, cube);
        } else {
            for (Cord c : cube.intersectionCords) {
                System.out.println(c.x + ", " + c.y + ", " + c.z);
            }
            throw new IncorrectSizeException("caseTwo." + methodname, 4, cube.intersectionCords.size());
        }
    }

    /**
     * create the second cube
     * remove corners from listOfCorners
     * add the needed corners to secondCube
     * @param listOfCorners
     * @param r0
     * @param r1
     * @param findDifferent
     * @return
     * @throws IncorrectSizeException
     */
    public List<List<Cord>> createSecondCube(List<Cord> listOfCorners, Cord r0, Cord r1, PartB.Direction findDifferent) throws IncorrectSizeException {
        String methodname = new Object() {}.getClass().getEnclosingMethod().getName();
        listOfCorners.remove(r0);
        listOfCorners.remove(r1);
        List<Cord> secondCube = new ArrayList<>();
        for (Cord loc : listOfCorners) {
            if ((findDifferent == PartB.Direction.Z && (loc.x == r0.x && loc.y == r0.y || loc.x == r1.x && loc.y == r1.y)) ||
                    (findDifferent == PartB.Direction.Y && (loc.x == r0.x && loc.z == r0.z || loc.x == r1.x && loc.z == r1.z)) ||
                    (findDifferent == PartB.Direction.X && (loc.z == r0.z && loc.y == r0.y || loc.z == r1.z && loc.y == r1.y))) {
                secondCube.add(loc);
            }
        }
        listOfCorners.removeAll(secondCube);
        if (secondCube.size() != 2) {
            throw new IncorrectSizeException(methodname, 2, secondCube.size());
        }
        List<List<Cord>> returnObject = new ArrayList<>();
        returnObject.add(listOfCorners);
        returnObject.add(secondCube);
        return returnObject;
    }


    /**
     * trying to get as many new corners as possible based on the intersection points and the currently known points
     * note: this is one of the methods that probably could do with some tiding up.
     * @param cube
     * @param r0
     * @param listOfCorners
     * @param secondCube
     * @param overlapRemove
     * @param currentDirection
     */
    public void findNewCornersBasedOnIntersectionCords(Cube cube, Cord r0, List<Cord> listOfCorners, List<Cord> secondCube, PartB.Direction overlapRemove, PartB.Direction currentDirection) {
        for (Cord ic : cube.intersectionCords) {
            //if ic has the same cord with r0 in the overlapRemove direction -> the new cord will be part of listOfCorners
            //else the new cord will be part of secondCube
            if ((overlapRemove == PartB.Direction.X && ic.x == r0.x)
                    || (overlapRemove == PartB.Direction.Y && ic.y == r0.y)
                    ||(overlapRemove == PartB.Direction.Z && ic.z == r0.z)) {
                int offset = 1;
                if (currentDirection == PartB.Direction.X) {
                    if (r0.y == cube.max.y) {
                        offset = -1;
                    }
                    line = ic.y;
                    listOfCorners.add(new Cord(ic.x, line + offset, ic.z));
                    listOfCorners.add(new Cord(ic.x ,line + offset, secondCube.get(0).z));
                } else if (currentDirection == PartB.Direction.Y) {
                    if (r0.z == cube.max.z) {
                        offset = -1;
                    }
                    line = ic.z;
                    listOfCorners.add(new Cord(ic.x, ic.y, line + offset));
                    listOfCorners.add(new Cord(secondCube.get(0).x, ic.y, line + offset));
                } else {
                    if (r0.x == cube.max.x) {
                        offset = -1;
                    }
                    line = ic.x;
                    listOfCorners.add(new Cord(line + offset, ic.y, ic.z));
                    listOfCorners.add(new Cord(line + offset, secondCube.get(0).y, ic.z));
                }
            } else {
                int offsetX = 1;
                int offsetY = 1;
                int offsetZ = 1;
                if (overlapRemove == PartB.Direction.X) {
                    if (cube.max.x == r0.x) {
                        offsetX = -1;
                    }
                } else {
                    offsetX = 0;
                }
                if (overlapRemove == PartB.Direction.Y) {
                    if (cube.max.y == r0.y) {
                        offsetY = -1;
                    }
                } else {
                    offsetY = 0;
                }
                if (overlapRemove == PartB.Direction.Z) {
                    if (cube.max.z == r0.z) {
                        offsetZ = -1;
                    }
                } else {
                    offsetZ = 0;
                }
                secondCube.add(new Cord(ic.x + offsetX, ic.y + offsetY, ic.z + offsetZ));
            }
        }
    }

    /**
     * get secondCube from 4 to 8 corners with help of line and lineDirection
     * @param secondCube
     * @param lineDirection
     * @return
     */
    public List<Cord> finishSecondCube(List<Cord> secondCube, PartB.Direction lineDirection) throws IncorrectSizeException {
        if (secondCube.size() != 4) {
            System.out.println("Points in secondCube:\n=====================");
            for (Cord c : secondCube) {
                System.out.println(c.x + ", " + c.y + ", " + c.z);
            }
            throw new IncorrectSizeException(new Object() {}.getClass().getEnclosingMethod().getName(), 4, secondCube.size());
        }
        List<Cord> toAdd = new ArrayList<>();
        for (Cord c : secondCube) {
            toAdd.add(new Cord(lineDirection == PartB.Direction.X ? line : c.x,
                    lineDirection == PartB.Direction.Y ? line : c.y,
                    lineDirection == PartB.Direction.Z ? line : c.z));
        }
        secondCube.addAll(toAdd);
        return secondCube;
    }

    /**
     * Check cube sizes and make new cubes to process
     * @param cube1
     * @param cube2
     * @param originalCube
     * @throws IncorrectSizeException
     * @throws XORException
     */
    public void finalStep(List<Cord> cube1, List<Cord> cube2, Cube originalCube) throws IncorrectSizeException, XORException {
        String methodname = new Object() {}.getClass().getEnclosingMethod().getName();
        if (cube1.size() != 8) {
            throw new IncorrectSizeException(methodname, 8, cube1.size());
        }
        if (cube2.size() != 8) {
            throw new IncorrectSizeException(methodname, 8, cube2.size());
        }

        //find minmax of both cubes and process the cubes
        List<Cord> minmax1 = pb.findMinMax(cube1);
        pb.processCube(new Cube(minmax1.get(0), minmax1.get(1), originalCube.on));
        List<Cord> minmax2 = pb.findMinMax(cube2);
        pb.processCube(new Cube(minmax2.get(0), minmax2.get(1), originalCube.on));
    }
}
