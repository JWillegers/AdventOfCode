package y2024.Solutions;

import Util_Java.Parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day12 extends Solution {
    private List<List<Character>> input;

    public Day12() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        List<String> in = parser.readFile(day);
        input = parser.make2DMapChar(in);
    }

    private int differentNeighbors(int row, int col, char current) {
        int dn = 0;

        // north
        if (row == 0 || (input.get(row - 1).get(col) != current &&
                input.get(row - 1).get(col) != Character.toLowerCase(current))) dn++;

        // south
        if (row + 1 == input.size() || (input.get(row + 1).get(col) != current &&
                input.get(row + 1).get(col) != Character.toLowerCase(current))) dn++;

        // west
        if (col == 0 || (input.get(row).get(col - 1) != current &&
                input.get(row).get(col - 1) != Character.toLowerCase(current))) dn++;

        // east
        if (col + 1 == input.get(row).size() || (input.get(row).get(col + 1) != current &&
                input.get(row).get(col + 1) != Character.toLowerCase(current))) dn++;

        return dn;
    }

    /**
     * Find 90 degree corners
     */
    private int corners(int row, int col, char current) {
        int corners = 0;

        // outer corners
        boolean north = (row == 0 || (input.get(row - 1).get(col) != current &&
                input.get(row - 1).get(col) != Character.toUpperCase(current)));

        boolean south = (row + 1 == input.size() || (input.get(row + 1).get(col) != current &&
                input.get(row + 1).get(col) != Character.toUpperCase(current)));

        boolean west = (col == 0 || (input.get(row).get(col - 1) != current &&
                input.get(row).get(col - 1) != Character.toUpperCase(current)));

        boolean east = (col + 1 == input.get(row).size() || (input.get(row).get(col + 1) != current &&
                input.get(row).get(col + 1) != Character.toUpperCase(current)));

        if (north && east) corners++;
        if (north && west) corners++;
        if (south && east) corners++;
        if (south && west) corners++;

        // inner corners

        // northeast
        if (!east && !north && input.get(row - 1).get(col + 1) != current &&
                input.get(row - 1).get(col + 1) != Character.toUpperCase(current)) corners++;

        // northwest
        if (!north && !west && input.get(row - 1).get(col - 1) != current &&
                input.get(row - 1).get(col - 1) != Character.toUpperCase(current)) corners++;

        // southeast
        if (!east && !south && input.get(row + 1).get(col + 1) != current &&
                input.get(row + 1).get(col + 1) != Character.toUpperCase(current)) corners++;

        // southwest
        if (!south && !west && input.get(row + 1).get(col - 1) != current &&
                input.get(row + 1).get(col - 1) != Character.toUpperCase(current)) corners++;

        return corners;
    }

    /**
     * Explore the area around inputPart1[startingRow][startingCol] with BFS to find the area and perimeter of the
     * region that inputPart1[startingRow][startingCol] is part of.
     *
     * @param startingRow
     * @param startingCol
     * @return area * perimeter
     */
    private long findPrice(int startingRow, int startingCol, boolean part1) {
        List<List<Integer>> queue = new ArrayList<>();
        queue.add(new ArrayList<>(Arrays.asList(startingRow, startingCol)));
        char currentSymbol = input.get(startingRow).get(startingCol);
        // change to lowerCase or higherSignal (depending on Part 1 or Part 2) to signal it already has been processed
        char newSymbol = part1 ? Character.toLowerCase(currentSymbol) : Character.toUpperCase(currentSymbol);
        input.get(startingRow).set(startingCol, newSymbol);
        int area = 0;
        int perimeter = 0;

        while (!queue.isEmpty()) {
            List<Integer> curr = queue.remove(0);
            int row = curr.get(0);
            int col = curr.get(1);
            area += 1;
            perimeter += part1 ? differentNeighbors(row, col, currentSymbol) : corners(row, col, currentSymbol);

            // check north, south, west, and east neighbors if they are the same value
            // if they are, add those (row, col) to queue and make them lowercase

            // north
            if (row > 0 && input.get(row - 1).get(col) == currentSymbol) {
                queue.add(new ArrayList<>(Arrays.asList(row - 1, col)));
                input.get(row - 1).set(col, newSymbol);
            }

            // south
            if (row + 1 < input.size() && input.get(row + 1).get(col) == currentSymbol) {
                queue.add(new ArrayList<>(Arrays.asList(row + 1, col)));
                input.get(row + 1).set(col, newSymbol);
            }

            // west
            if (col > 0 && input.get(row).get(col - 1) == currentSymbol) {
                queue.add(new ArrayList<>(Arrays.asList(row, col - 1)));
                input.get(row).set(col - 1, newSymbol);
            }

            // east
            if (col + 1 < input.get(row).size() && input.get(row).get(col + 1) == currentSymbol) {
                queue.add(new ArrayList<>(Arrays.asList(row, col + 1)));
                input.get(row).set(col + 1, newSymbol);
            }
        }
        return (long) area * perimeter;
    }

    @Override
    public String partOne() {
        long sum = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                // lower case letters already have been processed
                if (Character.isUpperCase(input.get(i).get(j))) {
                    sum += findPrice(i, j, true);
                }

            }
        }
        return String.valueOf(sum);
    }

    @Override
    public String partTwo() {
        long sum = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                // upper case letters already have been processed (opposite of part1)
                if (Character.isLowerCase(input.get(i).get(j))) {
                    sum += findPrice(i, j, false);
                }

            }
        }
        return String.valueOf(sum);
    }

    @Override
    public int getDay() {
        return 12;
    }

    public static void main(String[] args) throws IOException {
        Day12 d = new Day12();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
