package y2021.src.Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartA {
    private int nOfLines = 25;
    private List<Cave> myCaves = new ArrayList<>();
    private List<String> validPaths = new ArrayList<>();

    public static void main(String[] args) {
        PartA part = new PartA();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("y2021/src/Day12/input.txt"));
            String[] array = new String[nOfLines];
            //loads all lines from file
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            //process all lines from file
            for (int i = 0; i < nOfLines; i++) {
                String[] split = array[i].split("-");
                for (String newCave : split) {
                    boolean contain = false;
                    for (Cave existingCave : myCaves) {
                        if (existingCave.name.equals(newCave)) {
                            contain = true;
                        }
                    }
                    if (!contain) {
                        List<String> neighbours = new ArrayList<>();
                        for (int j = 0; j < nOfLines; j++) {
                            if (array[j].contains("-" + newCave) || array[j].contains(newCave + "-")) {
                                String[] innerSplit = array[j].split("-");
                                if (innerSplit[0].equals(newCave)) {
                                    neighbours.add(innerSplit[1]);
                                } else {
                                    neighbours.add(innerSplit[0]);
                                }
                            }
                        }
                        myCaves.add(new Cave(newCave, neighbours));
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution() {
        pathFinder("start", "start");
        System.out.println(validPaths.size() + " valid paths");
    }

    public void pathFinder(String currentCave, String path) {
        if (path.contains("end")) {
            if (!validPaths.contains(path)) {
                validPaths.add(path);
            }
        } else {
            for (Cave thisCave : myCaves) {
                if (thisCave.name.equals(currentCave)) {
                    for (String neighbour : thisCave.neighbours) {
                        if (neighbour.toLowerCase().equals(neighbour)) {
                            if (!path.contains(neighbour)) {
                                pathFinder(neighbour, path + "," + neighbour);
                            }
                        } else {
                            pathFinder(neighbour, path + "," + neighbour);
                        }
                    }
                }

            }
        }
    }


}
