package Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartB {
    private int nOfLines = 25;
    private List<Cave> myCaves = new ArrayList<>();
    private List<String> validPaths = new ArrayList<>();

    public static void main(String[] args) {
        PartB part = new PartB();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2021/src/Day12/input.txt"));
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
        pathFinder("start", "start", false);
        System.out.println(validPaths.size() + " valid paths");
    }

    public void pathFinder(String currentCave, String path, boolean doubleVisitSmallCave) {
        if (path.contains("end")) {
            if (!validPaths.contains(path)) {
                validPaths.add(path);
            }
        } else {
            for (Cave thisCave : myCaves) {
                if (thisCave.name.equals(currentCave)) {
                    for (String neighbour : thisCave.neighbours) {
                        if (neighbour.toLowerCase().equals(neighbour)) { //small cave
                            if (!neighbour.equals("start")) {
                                int counter = 0;
                                String[] split = path.split(",");
                                for (String cave : split) {
                                    if (cave.equals(neighbour)) {
                                        counter++;
                                    }
                                }
                                if (counter < 1) {
                                    pathFinder(neighbour, path + "," + neighbour, doubleVisitSmallCave);
                                } else if (!doubleVisitSmallCave && counter < 2) {
                                    pathFinder(neighbour, path + "," + neighbour, true);
                                }
                            }
                        } else {
                            pathFinder(neighbour, path + "," + neighbour, doubleVisitSmallCave);
                        }
                    }
                }

            }
        }
    }


}
