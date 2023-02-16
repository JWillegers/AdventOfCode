package Day22;

import java.util.ArrayList;
import java.util.List;

public class CaseOne {
    private PartB pb;

    public CaseOne(PartB partB) {
        this.pb = partB;
    }

    /**
     * Main part of caseOne, where first the midpoint (which sees all intersection cords) is found
     * after that there are 3 new cubes calculated
     * @param cube
     * @param listOfCorners
     * @throws IncorrectSizeException
     * @throws XORException
     */
    public void main(Cube cube, List<Cord> listOfCorners) throws IncorrectSizeException, XORException {
        String methodname = new Object() {}.getClass().getEnclosingMethod().getName();
        if (cube.intersectionCords.size() == 3) {
            Cord toRemove = cube.cordsToBeRemoved.get(0);
            int midX = 0;
            int midY = 0;
            int midZ = 0;
            for (int i = 0; i < cube.intersectionCords.size(); i++) {
                Cord c = cube.intersectionCords.get(i);
                midX = c.y == toRemove.y && c.z == toRemove.z ? c.x : midX;
                midY = c.x == toRemove.x && c.z == toRemove.z ? c.y : midY;
                midZ = c.x == toRemove.x && c.y == toRemove.y ? c.z : midZ;
            }

            /*
            From the cutout shape, one can create 3 new cubes,
            A small one, which only has 1 corner in common with the original cube
            A medium one, which shares 2 corners with the original cube
            A large one, which shares 4 corners with the original cube
             */
            List<Cord> cubeSmall = new ArrayList<>();
            List<Cord> cubeMedium = new ArrayList<>();
            List<Cord> cubeLarge = new ArrayList<>();
            for (Cord c : listOfCorners) {
                if (c.z != toRemove.z) {
                    cubeLarge.add(c);
                } else if (c.y != toRemove.y) {
                    cubeMedium.add(c);
                } else if (c.x != toRemove.x) { //cannot be an else-statement because we don't want to include the corner that needs to be removed
                    cubeSmall.add(c);
                }
            }

            cubeSmall = solveCubeSmall(cubeSmall, midX, midY, midZ);
            List<Cord> minmaxSmall = pb.findMinMax(cubeSmall);
            pb.processCube(new Cube(minmaxSmall.get(0), minmaxSmall.get(1), cube.on));

            cubeMedium = solveCubeMedium(cubeMedium, midY, midZ);
            List<Cord> minmaxMedium = pb.findMinMax(cubeMedium);
            pb.processCube(new Cube(minmaxMedium.get(0), minmaxMedium.get(1), cube.on));

            cubeLarge = solveCubeLarge(cubeLarge, midZ);
            List<Cord> minmaxLarge = pb.findMinMax(cubeLarge);
            pb.processCube(new Cube(minmaxLarge.get(0), minmaxLarge.get(1), cube.on));

        } else {
            for (Cord c : cube.intersectionCords) {
                System.out.println(c.x + ", " + c.y + ", " + c.z);
            }
            throw new IncorrectSizeException("CaseOne." + methodname, 3, cube.intersectionCords.size());
        }
    }

    /**
     * Find all cords for the small cube, based on the known cords for it and the mid point
     * @param cubeSmall
     * @param midX
     * @param midY
     * @param midZ
     * @return
     * @throws IncorrectSizeException
     */
    public List<Cord> solveCubeSmall(List<Cord> cubeSmall, int midX, int midY, int midZ) throws IncorrectSizeException {
        String methodname = new Object() {}.getClass().getEnclosingMethod().getName();
        if (cubeSmall.size() != 1) {
            throw new IncorrectSizeException(methodname, 1, cubeSmall.size());
        }
        Cord outside = cubeSmall.get(0);
        int x = Math.abs(outside.x - midX - 1) > Math.abs(outside.x - midX + 1) ? midX - 1 : midX + 1;
        Cord mid = new Cord(x, midY, midZ);
        cubeSmall.add(mid); //2
        cubeSmall.add(new Cord(outside.x, outside.y, mid.z)); //3
        cubeSmall.add(new Cord(outside.x, mid.y, mid.z)); //4
        cubeSmall.add(new Cord(outside.x, mid.y, outside.z)); //5
        cubeSmall.add(new Cord(mid.x, outside.y, mid.z)); //6
        cubeSmall.add(new Cord(mid.x, outside.y, outside.z)); //7
        cubeSmall.add(new Cord(mid.x, mid.y, outside.z)); //8
        return cubeSmall;
    }

    /**
     * Find all cords for the medium cube, based on the known cords for it and the mid point
     * @param cubeMedium
     * @param midY
     * @param midZ
     * @return
     * @throws IncorrectSizeException
     */
    public List<Cord> solveCubeMedium(List<Cord> cubeMedium, int midY, int midZ) throws IncorrectSizeException {
        String methodname = new Object() {}.getClass().getEnclosingMethod().getName();
        if (cubeMedium.size() != 2) {
            throw new IncorrectSizeException(methodname, 2, cubeMedium.size());
        }
        int y = Math.abs(cubeMedium.get(0).y - midY - 1) > Math.abs(cubeMedium.get(0).y - midY + 1) ? midY - 1 : midY + 1;
        List<Cord> toAdd = new ArrayList<>();
        for (Cord c : cubeMedium) {
            toAdd.add(new Cord(c.x, c.y, midZ));
            toAdd.add(new Cord(c.x, y, midZ));
            toAdd.add(new Cord(c.x, y, c.z));
        }
        cubeMedium.addAll(toAdd);
        if (cubeMedium.size() != 8) {
            throw new IncorrectSizeException(methodname, 8, cubeMedium.size());
        }
        return cubeMedium;
    }

    /**
     * Find all cords for the large cube, based on the known cords for it and the mid point
     * @param cubeLarge
     * @param midZ
     * @return
     * @throws IncorrectSizeException
     */
    public List<Cord> solveCubeLarge(List<Cord> cubeLarge, int midZ) throws IncorrectSizeException {
        String methodname = new Object() {}.getClass().getEnclosingMethod().getName();
        if (cubeLarge.size() != 4) {
            throw new IncorrectSizeException(methodname, 4, cubeLarge.size());
        }
        int z = Math.abs(cubeLarge.get(0).z - midZ - 1) > Math.abs(cubeLarge.get(0).z - midZ + 1) ? midZ - 1 : midZ + 1;
        List<Cord> toAdd = new ArrayList<>();
        for (Cord c : cubeLarge) {
            toAdd.add(new Cord(c.x, c.y, z));
        }
        cubeLarge.addAll(toAdd);
        if (cubeLarge.size() != 8) {
            throw new IncorrectSizeException(methodname, 8, cubeLarge.size());
        }
        return cubeLarge;
    }

}
