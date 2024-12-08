package y2024.Solutions;


import Util_Java.Parse;

import java.io.IOException;
import java.util.*;

public class Day8 extends Solution {
    private Map<Character, List<List<Integer>>> locations;
    private int colMax;
    private int rowMax;

    public Day8() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        List<List<Character>> input = parser.make2DMapChar(parser.readFile(day));
        locations = new HashMap<>();
        rowMax = input.size() - 1;
        colMax = input.get(1).size() - 1;
        // instead of working with the raw map, a hashmap with symbols and all their locations are used
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.size(); j++) {
                Character c = input.get(i).get(j);
                if (locations.containsKey(c)) {
                    locations.get(c).add(new ArrayList<>(Arrays.asList(i, j)));
                } else if (!c.equals('.')){
                    List<List<Integer>> outerList = new ArrayList<>();
                    List<Integer> innerList = new ArrayList<>();
                    innerList.add(i);
                    innerList.add(j);
                    outerList.add(innerList);
                    locations.put(c, outerList);
                }
            }
        }
    }

    @Override
    public String partOne(){
        // 100 * row + col
        Set<Integer> antinodes = new HashSet<>();
        // loop through all symbols
        for (char key : locations.keySet()) {
            // loop through all coordinates
            for (int i = 0; i < locations.get(key).size(); i++) {
                List<Integer> locationOne = locations.get(key).get(i);
                // loop through all coordinates after this one
                for (int j = i + 1; j < locations.get(key).size(); j++) {
                    List<Integer> locationTwo = locations.get(key).get(j);

                    // find difference in row and col between the coordinates
                    int rowDelta = locationOne.get(0) - locationTwo.get(0);
                    int colDelta = locationOne.get(1) - locationTwo.get(1);

                    // new node at (locationOne.get(0) + rowDelta, locationOne.get(1) + colDelta)
                    int newNodeRow = locationOne.get(0) + rowDelta;
                    int newNodeCol = locationOne.get(1) + colDelta;
                    if (0 <= newNodeRow && newNodeRow <= rowMax && 0 <= newNodeCol && newNodeCol <= colMax) {
                        antinodes.add(100 * newNodeRow + newNodeCol);
                    }

                    // new node at (locationTwe.get(0) - rowDelta, locationTwo.get(1) - colDelta)
                    newNodeRow = locationTwo.get(0) - rowDelta;
                    newNodeCol = locationTwo.get(1) - colDelta;
                    if (0 <= newNodeRow && newNodeRow <= rowMax && 0 <= newNodeCol && newNodeCol <= colMax) {
                        antinodes.add(100 * newNodeRow + newNodeCol);
                    }
                }
            }
        }
        return String.valueOf(antinodes.size());
    }

    @Override
    public String partTwo() {
        // 100 * row + col
        Set<Integer> antinodes = new HashSet<>();
        // loop through all symbols
        for (char key : locations.keySet()) {
            // loop through all coordinates
            for (int i = 0; i < locations.get(key).size(); i++) {
                List<Integer> locationOne = locations.get(key).get(i);
                // loop through all coordinates after this one
                for (int j = i + 1; j < locations.get(key).size(); j++) {
                    List<Integer> locationTwo = locations.get(key).get(j);

                    // find difference in row and col between the coordinates
                    int rowDelta = locationOne.get(0) - locationTwo.get(0);
                    int colDelta = locationOne.get(1) - locationTwo.get(1);

                    // new node at (locationOne.get(0) + rowDelta, locationOne.get(1) + colDelta)
                    int newNodeRow = locationOne.get(0) + rowDelta;
                    int newNodeCol = locationOne.get(1) + colDelta;
                    antinodes.add(100 * locationOne.get(0) + locationOne.get(1));
                    while (0 <= newNodeRow && newNodeRow <= rowMax && 0 <= newNodeCol && newNodeCol <= colMax) {
                        antinodes.add(100 * newNodeRow + newNodeCol);
                        newNodeRow += rowDelta;
                        newNodeCol += colDelta;
                    }

                    // new node at (locationTwe.get(0) - rowDelta, locationTwo.get(1) - colDelta)
                    newNodeRow = locationTwo.get(0) - rowDelta;
                    newNodeCol = locationTwo.get(1) - colDelta;
                    antinodes.add(100 * locationTwo.get(0) + locationTwo.get(1));
                    while (0 <= newNodeRow && newNodeRow <= rowMax && 0 <= newNodeCol && newNodeCol <= colMax) {
                        antinodes.add(100 * newNodeRow + newNodeCol);
                        newNodeRow -= rowDelta;
                        newNodeCol -= colDelta;
                    }
                }
            }
        }
        return String.valueOf(antinodes.size());
    }

    @Override
    public int getDay() {
        return 8;
    }

    public static void main(String[] args) throws IOException {
        Day8 d = new Day8();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
