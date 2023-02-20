package Day22;

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
            reader = new BufferedReader(new FileReader("2021/src/Day22/input.txt"));
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
            System.out.println(volume);
        } catch (
        IOException e) {
            e.printStackTrace();
        }
    }

    //START GENERAL

    //Return all newly processed cubes
    protected List<Cube> process(Cube input, List<Cube> processedCubes) {
        List<Cube> newlyProcessedCubes = new ArrayList<>();
        for (Cube processedCube : processedCubes) {
            List<Cord> toBeRemoved = findOverlap(input, processedCube);
            switch (toBeRemoved.size()) {
                case 0:
                    break;
                case 1:

                    break;
                case 2:

                    break;
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

    //END GENERAL
    //START CASE FOUR

    protected Cube caseFour(Cube input, List<Cord> toBeRemoved, Cube processedCube) {
        //remove old cords
        for (Cord cordToBeRemoved : toBeRemoved) {
            input.removeCorner(cordToBeRemoved);
        }
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

}
