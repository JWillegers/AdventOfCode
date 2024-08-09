package y2021.src.Day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartB {
    protected final int infinity = (int) 9e9;
    protected final String minimum = "min";
    protected final String maximum = "max";
    public static void main(String[] args) {
        PartB pb = new PartB();
        pb.parse();
    }
    public void parse() {
        BufferedReader reader;
        List<Cube> input_cubes = new ArrayList<>();
        // read input
        try {
            reader = new BufferedReader(new FileReader("y2021/src/Day22/input.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] firstSplit = line.split(" ");
                Cube cube = new Cube(firstSplit[0].equals("on"));
                String[] splitDimensons = firstSplit[1].split(",");
                String[] splitX = splitDimensons[0].split("=")[1].split("\\.\\.");
                String[] splitY = splitDimensons[1].split("=")[1].split("\\.\\.");
                String[] splitZ = splitDimensons[2].split("=")[1].split("\\.\\.");
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++) {
                        for (int k = 0; k < 2; k++) {
                            cube.addCorner(new Cord(Integer.parseInt(splitX[i]),
                                    Integer.parseInt(splitY[j]), Integer.parseInt(splitZ[k])));
                        }
                    }
                }
                input_cubes.add(cube);

                // read next line
                line = reader.readLine();
            }
            reader.close();

            //process last cube
            long volume = 0;
            if (input_cubes.get(input_cubes.size() - 1).on) {
                volume += calculateVolume(input_cubes.get(input_cubes.size() - 1));
            }
            List<Cube> processedCubes = new ArrayList<>();
            processedCubes.add(input_cubes.get(input_cubes.size() - 1));
            //process other cubes (reversed order)
            for (int i = input_cubes.size() - 2; i >= 0; i--) {
                List<Cube> newCubes = process(input_cubes.get(i), processedCubes);
                for (Cube c: newCubes) {
                    if (input_cubes.get(i).on) {
                        volume += calculateVolume(c);
                    }
                }
            }

            System.out.println("valid solution: " + validSolution(volume, input_cubes));
            System.out.println("solution: " + volume);
            System.out.println(volume >= Long.parseLong("2758514936282235")); //TODO remove
            // Only case 4: 2621950122807544
            // Cases 2 & 4: 2617774534071931
            // Cases 1, 2, 4: 2616056978794596 (incorrect)



        } catch (
        IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean validSolution(long volume, List<Cube> input_cubes) {
        int minX = infinity;
        int maxX = -infinity;
        int minY = infinity;
        int maxY = -infinity;
        int minZ = infinity;
        int maxZ = -infinity;
        for (Cube cube : input_cubes) {
            for (Cord cord : cube.corners) {
                minX = Math.min(minX, cord.x);
                maxX = Math.max(maxX, cord.x);
                minY = Math.min(minY, cord.y);
                maxY = Math.max(maxY, cord.y);
                minZ = Math.min(minZ, cord.z);
                maxZ = Math.max(maxZ, cord.z);
            }
        }
        long maxVolume = (long) Math.abs(maxX - minX) * Math.abs(maxY - minY) * Math.abs(maxZ - minZ);
        return volume < maxVolume;
    }

    //START GENERAL

    //Return all newly processed cubes
    protected List<Cube> process(Cube input, List<Cube> processedCubes) {
        List<Cube> newlyProcessedCubes = new ArrayList<>();
        for (Cube processedCube : processedCubes) {
            List<Cord> toBeRemoved = findOverlap(input, processedCube);
            switch (toBeRemoved.size()) {
                case 0:
                    List<Cube> toProcess0 = caseZero(input, processedCube);
                    if (toProcess0 == null || toProcess0.isEmpty()) {
                        break;
                    } else {
                        for (Cube c : toProcess0) {
                            newlyProcessedCubes.addAll(process(c, processedCubes));
                        }
                        return newlyProcessedCubes;
                    }
                case 1:
                    List<Cube> toProcess1 = caseOne(input, toBeRemoved, processedCube);
                    for (Cube c : toProcess1) {
                        newlyProcessedCubes.addAll(process(c, processedCubes));
                    }
                    return newlyProcessedCubes;
                case 2:
                    List<Cube> toProcess2 = caseTwo(input, toBeRemoved, processedCube);
                    for (Cube c : toProcess2) {
                        newlyProcessedCubes.addAll(process(c, processedCubes));
                    }
                    return newlyProcessedCubes;
                case 4:
                    input = caseFour(input, toBeRemoved, processedCube);
                    break;
                case 8:
                    return newlyProcessedCubes;
                default:
                    System.out.println("Error in 'process', size not found: " + toBeRemoved.size());
                    System.exit(1);
            }
        }
        newlyProcessedCubes.add(input);
        return newlyProcessedCubes;
    }

    //find corners of input that lie in aProcessedCube
    //return these corners
    protected List<Cord> findOverlap(Cube input, Cube aProcessedCube) {
        List<Cord> cornersThatOverlap = new ArrayList<>();
        Map<String, Cord> minmax = findMinMaxOfCube(aProcessedCube);
        for (Cord inputCord : input.corners) {
            if (inputCord.x >= minmax.get(minimum).x && inputCord.x <= minmax.get(maximum).x &&
                    inputCord.y >= minmax.get(minimum).y && inputCord.y <= minmax.get(maximum).y &&
                    inputCord.z >= minmax.get(minimum).z && inputCord.z <= minmax.get(maximum).z) {
                cornersThatOverlap.add(inputCord);
            }
        }
        return cornersThatOverlap;
    }

    //Calculate volume of the cube
    protected long calculateVolume(Cube cube) {
        Map<String, Cord> minmax = findMinMaxOfCube(cube);
        return (long) Math.abs(minmax.get(minimum).x - minmax.get(maximum).x) *
                Math.abs(minmax.get(minimum).y - minmax.get(maximum).y) *
                Math.abs(minmax.get(minimum).z - minmax.get(maximum).z);
    }

    //return the Cord with all the smallest value and the Cord with all the biggest values
    protected Map<String, Cord> findMinMaxOfCube(Cube cube) {
        int minX = infinity;
        int maxX = -infinity;
        int minY = infinity;
        int maxY = -infinity;
        int minZ = infinity;
        int maxZ = -infinity;
        for (Cord cord : cube.corners) {
            minX = Math.min(minX, cord.x);
            maxX = Math.max(maxX, cord.x);
            minY = Math.min(minY, cord.y);
            maxY = Math.max(maxY, cord.y);
            minZ = Math.min(minZ, cord.z);
            maxZ = Math.max(maxZ, cord.z);
        }
        Map<String, Cord> r = new HashMap<>();
        r.put(minimum, new Cord(minX, minY, minZ));
        r.put(maximum, new Cord(maxX, maxY, maxZ));
        return r;
    }

    protected Cube removeCords(Cube input, List<Cord> toBeRemoved) {
        //remove old cords
        for (Cord cordToBeRemoved : toBeRemoved) {
            input.removeCorner(cordToBeRemoved);
        }
        return input;
    }

    //END GENERAL
    //START CASE FOUR

    protected Cube caseFour(Cube input, List<Cord> toBeRemoved, Cube processedCube) {
        input = removeCords(input, toBeRemoved);
        //find plane the cords of input lie on
        List<Cord> toAdd = caseFourFindNewCords(input, processedCube);
        for (Cord c : toAdd) {
            input.addCorner(c);
        }

        return input;
    }

    protected List<Cord> caseFourFindNewCords(Cube input, Cube processedCube) {
        boolean xPlane = true;
        boolean yPlane = true;
        boolean zPlane = true;
        Cord baseline = input.corners.get(0);
        for (int i = 1; i < input.corners.size(); i++) {
            xPlane = xPlane && baseline.x == input.corners.get(i).x;
            yPlane = yPlane && baseline.y == input.corners.get(i).y;
            zPlane = zPlane && baseline.z == input.corners.get(i).z;
        }
        assert(xPlane || yPlane || zPlane);
        assert(!(xPlane && yPlane));
        assert(!(xPlane && zPlane));
        assert(!(yPlane && zPlane));

        //Find plane that new points need to be added on
        List<Cord> toAdd = new ArrayList<>();
        int value;
        Map<String, Cord> minMaxProcessedCube = findMinMaxOfCube(processedCube);
        if (xPlane) {
            if (input.corners.get(0).x < minMaxProcessedCube.get(minimum).x) {
                value = minMaxProcessedCube.get(minimum).x - 1;
            } else {
                value = minMaxProcessedCube.get(maximum).x + 1;
            }
            for (Cord c : input.corners) {
                toAdd.add(new Cord(value, c.y, c.z));
            }
        } else if (yPlane) {
            if (input.corners.get(0).y < minMaxProcessedCube.get(minimum).y) {
                value = minMaxProcessedCube.get(minimum).y - 1;
            } else {
                value = minMaxProcessedCube.get(maximum).y + 1;
            }
            for (Cord c : input.corners) {
                toAdd.add(new Cord(c.x, value, c.z));
            }
        } else {
            if (input.corners.get(0).z < minMaxProcessedCube.get(minimum).z) {
                value = minMaxProcessedCube.get(minimum).z - 1;
            } else {
                value = minMaxProcessedCube.get(maximum).z + 1;
            }
            for (Cord c : input.corners) {
                toAdd.add(new Cord(c.x, c.y, value));
            }
        }
        return toAdd;
    }

    //END CASE FOUR
    //START CASE TWO

    protected List<Cube> caseTwo(Cube input, List<Cord> toBeRemoved, Cube processedCube) {
        input = removeCords(input, toBeRemoved);

        //find on which line the 2 removed cords lie
        Cord c0 = toBeRemoved.get(0);
        Cord c1 = toBeRemoved.get(1);
        boolean xLine = c0.x != c1.x;
        boolean yLine = c0.y != c1.y;
        boolean zLine = c0.z != c1.z;
        assert(xLine || yLine || zLine);
        assert(!(xLine && yLine));
        assert(!(xLine && zLine));
        assert(!(yLine && zLine));

        List<Cube> dividedCubes = caseTwoDivide(input, zLine);
        Cube cubeSize2 = dividedCubes.get(0);
        Cube cubeSize4 = dividedCubes.get(1);
        List<Cube> returnList = new ArrayList<>();

        //process cubeSize4
        List<Cord> toAdd = caseFourFindNewCords(cubeSize4, processedCube);
        for(Cord c : toAdd) {
            cubeSize4.addCorner(c);
        }
        returnList.add(cubeSize4);

        //process cubeSize2
        c0 = cubeSize2.corners.get(0);
        Map<String, Cord> minMaxProcessedCube = findMinMaxOfCube(processedCube);

        if (zLine) {
            returnList.add(caseTwoZ(c0, cubeSize2, cubeSize4, minMaxProcessedCube));
        } else {
            returnList.add(caseTwoXorY(c0, cubeSize2, cubeSize4, minMaxProcessedCube, xLine));
        }

        return returnList;
    }

    protected List<Cube> caseTwoDivide(Cube input, boolean zLine) {
        List<Cube> returnList = new ArrayList<>();
        Cube cube1 = new Cube(input.on);
        Cube cube2 = new Cube(input.on);
        cube1.addCorner(input.corners.get(0));
        if (zLine) {
            int y = input.corners.get(0).y;
            for (int i = 1; i < input.corners.size(); i++) {
                Cord c = input.corners.get(i);
                if (c.y == y) {
                    cube1.addCorner(c);
                } else {
                    cube2.addCorner(c);
                }
            }
        } else {
            int z = input.corners.get(0).z;
            for (int i = 1; i < input.corners.size(); i++) {
                Cord c = input.corners.get(i);
                if (c.z == z) {
                    cube1.addCorner(c);
                } else {
                    cube2.addCorner(c);
                }
            }
        }
        Cube cubeSize2 = cube1.corners.size() == 4 ? cube2 : cube1;
        Cube cubeSize4 = cube1.corners.size() == 4 ? cube1 : cube2;
        returnList.add(cubeSize2);
        returnList.add(cubeSize4);
        return returnList;
    }

    protected Cube caseTwoZ(Cord c0, Cube cubeSize2, Cube cubeSize4, Map<String, Cord> minMaxProcessedCube) {
        List<Cord> toAdd = new ArrayList<>();
        //find 2 points that lie on the same x cord as the 2 points in cubeSize2
        int y = c0.y < cubeSize4.corners.get(0).y ?
                minMaxProcessedCube.get(maximum).y : minMaxProcessedCube.get(minimum).y;
        // cannot loop over cubeSize2 and add something to it at the same
        for (Cord c : cubeSize2.corners) {
            toAdd.add(new Cord(c.x, y, c.z));
        }
        for (Cord c : toAdd) {
            cubeSize2.addCorner(c);
        }
        toAdd.clear();

        //find the last 4 cords to complete the cube
        int x = c0.x < minMaxProcessedCube.get(minimum).x ?
                minMaxProcessedCube.get(minimum).x - 1 : minMaxProcessedCube.get(maximum).x + 1;
        for (Cord c : cubeSize2.corners) {
            toAdd.add(new Cord(x, c.y, c.z));
        }
        for (Cord c : toAdd) {
            cubeSize2.addCorner(c);
        }

        return cubeSize2;
    }

    protected Cube caseTwoXorY(Cord c0, Cube cubeSize2, Cube cubeSize4, Map<String, Cord> minMaxProcessedCube, boolean xLine) {
        List<Cord> toAdd = new ArrayList<>();
        //find 2 points that lie on the same z cord as the 2 points in cubeSize2
        int z = c0.z < cubeSize4.corners.get(0).z ?
                minMaxProcessedCube.get(maximum).z : minMaxProcessedCube.get(minimum).z;
        // cannot loop over cubeSize2 and add something to it at the same
        for (Cord c : cubeSize2.corners) {
            toAdd.add(new Cord(c.x, c.y, z));
        }
        for(Cord c : toAdd) {
            cubeSize2.addCorner(c);
        }
        toAdd.clear();

        //find the last 4 cords to complete the cube
        if (xLine) {
            int y = c0.y < minMaxProcessedCube.get(minimum).y ?
                    minMaxProcessedCube.get(minimum).y - 1 : minMaxProcessedCube.get(maximum).y + 1;
            for (Cord c : cubeSize2.corners) {
                toAdd.add(new Cord(c.x, y, c.z));
            }
            for(Cord c : toAdd) {
                cubeSize2.addCorner(c);
            }
        } else {
            int x = c0.x < minMaxProcessedCube.get(minimum).x ?
                    minMaxProcessedCube.get(minimum).x - 1 : minMaxProcessedCube.get(maximum).x + 1;
            for (Cord c : cubeSize2.corners) {
                toAdd.add(new Cord(x, c.y, c.z));
            }
            for(Cord c : toAdd) {
                cubeSize2.addCorner(c);
            }
        }
        return cubeSize2;
    }

    protected List<Cube> caseOne(Cube input, List<Cord> toBeRemoved, Cube processedCube) {
        //find cord of the processed cube that lies within input (needed later)
        Cord pc = findOverlap(processedCube, input).get(0);

        //remove cord in to be removed and divide input
        input = removeCords(input, toBeRemoved);

        Cord r = toBeRemoved.get(0);
        Cube cubeSize1 = new Cube(input.on);
        Cube cubeSize2 = new Cube(input.on);
        Cube cubeSize4 = new Cube(input.on);

        for (Cord c : input.corners) {
            if (c.z != r.z) {
                cubeSize4.corners.add(c);
            } else if (c.y != r.y) {
                cubeSize2.corners.add(c);
            } else {
                cubeSize1.corners.add(c);
            }
        }

        assert(cubeSize1.corners.size() == 1);
        assert(cubeSize2.corners.size() == 2);
        assert(cubeSize4.corners.size() == 4);

        //process cubeSize4
        List<Cube> newCubes = new ArrayList<>();
        List<Cord> toAdd = caseFourFindNewCords(cubeSize4, processedCube);
        for(Cord c : toAdd) {
            cubeSize4.addCorner(c);
        }
        newCubes.add(cubeSize4);

        //process cubeSize2
        Cord c0 = cubeSize2.corners.get(0);
        cubeSize2 = caseTwoXorY(c0, cubeSize2, cubeSize4, findMinMaxOfCube(processedCube), true);
        newCubes.add(cubeSize2);

        //process cubeSize1
        Cord cube1cord = cubeSize1.corners.get(0);
        Cube newCube = new Cube(input.on);
        List<Integer> x = new ArrayList<>();
        if (pc.x < cube1cord.x) {
            x.add(pc.x + 1);
        } else if (pc.x > cube1cord.x) {
            x.add(pc.x - 1);
        } else {
            System.out.println("ERROR: equality in caseOne");
            System.exit(1);
        }
        x.add(cubeSize1.corners.get(0).x);
        List<Integer> y = new ArrayList<>();
        y.add(pc.y);
        y.add(cubeSize1.corners.get(0).y);
        List<Integer> z = new ArrayList<>();
        z.add(pc.z);
        z.add(cubeSize1.corners.get(0).z);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for(int k = 0; k < 2; k++) {
                    newCube.addCorner(new Cord(x.get(i), y.get(j), z.get(k)));
                }
            }
        }
        newCubes.add(newCube);

        return newCubes;
    }

    protected List<Cube> caseZero(Cube input, Cube processedCube) {
        List<Cord> reverseOverlap = findOverlap(processedCube, input);
        switch (reverseOverlap.size()) {
            case 0:
                break;
            case 2:
                break;
            case 4:
                return caseZeroFour(input, reverseOverlap, processedCube);
            case 8:
                break;
            default:
                System.out.println("Error in 'caseZero', size not found: " + reverseOverlap.size());
                System.exit(1);
        }
        return null;
    }

    protected List<Cube> caseZeroFour(Cube input, List<Cord> reverseOverlap, Cube processedCube) {
        boolean xPlane = true;
        boolean yPlane = true;
        boolean zPlane = true;
        List<Cube> toReturn = new ArrayList<>();
        Map<String, Cord> minmaxInput = findMinMaxOfCube(input);
        Map<String, Cord> minmaxProcessedCube = findMinMaxOfCube(processedCube);

        //find on which plane the cords in reverseOverlap lie
        for (int i = 0; i < reverseOverlap.size(); i++) {
            Cord ci = reverseOverlap.get(i);
            for (int j = i + 1; j < reverseOverlap.size(); j++) {
                Cord cj = reverseOverlap.get(j);
                xPlane = xPlane && ci.x == cj.x;
                yPlane = yPlane && ci.y == cj.y;
                zPlane = zPlane && ci.z == cj.z;
            }
        }

        assert (xPlane || yPlane || zPlane);
        assert(!(xPlane && yPlane));
        assert(!(xPlane && zPlane));
        assert(!(yPlane && zPlane));

        if (xPlane || yPlane) {
            List<Cord> upper = new ArrayList<>();
            List<Cord> lower = new ArrayList<>();
            for (Cord c : input.corners) {
                if (c.z == minmaxInput.get(maximum).z) {
                    upper.add(c);
                } else {
                    lower.add(c);
                }
            }
            toReturn.add(caseZeroZ(minmaxProcessedCube.get(maximum).z + 1, upper, input.on));
            toReturn.add(caseZeroZ(minmaxProcessedCube.get(minimum).z - 1, lower, input.on));
        }
        return toReturn;
    }

    protected Cube caseZeroZ(int z_processed_with_offset, List<Cord> input, boolean on) {
        Cube toReturn = new Cube(on);
        for (Cord c : input) {
            toReturn.addCorner(c);
            toReturn.addCorner(new Cord(c.x, c.y, z_processed_with_offset));
        }
        return toReturn;
    }
}
