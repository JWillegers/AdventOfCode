package y2024.Solutions;


import Util_Java.Parse;

import java.io.IOException;
import java.util.*;

public class Day10 extends Solution {
    List<List<Integer>> input;


    public Day10() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        List<String> in = parser.readFile(day);
        input = parser.make2DMapInt(in);
    }

    /**
     * requires input[startRow][startCol] == 0
     * @param startRow 0 <= startRow < input.size()
     * @param startCol 0 <= startCol < input.get(startRow).size()
     * @param part2  indicate if this function is used for part 2
     * @return amount of 9 found when gradually increasing from 0 to 9 with steps of 1
     */
    private int searchNines(int startRow, int startCol, boolean part2) {
        List<List<Integer>> queue = new ArrayList<>();
        queue.add(new ArrayList<>(Arrays.asList(startRow, startCol)));
        // items in seen follow: 100 * row + col
        Set<Integer> seen = new HashSet<>();
        seen.add(setScore(startRow, startCol));
        int nines = 0;
        while (!queue.isEmpty()) {
            List<Integer> current = queue.remove(0);
            int currentRow = current.get(0);
            int currentCol = current.get(1);
            int currentValue = input.get(currentRow).get(currentCol);
            if (currentValue == 9) {
                nines ++;
                continue;
            }

            // north
            if (currentRow > 0 && !seen.contains(setScore(currentRow - 1, currentCol)) &&
            currentValue + 1 == input.get(currentRow - 1).get(currentCol)) {
                queue.add(new ArrayList<>(Arrays.asList(currentRow - 1, currentCol)));
                if (!part2) seen.add(setScore(currentRow - 1, currentCol));
            }
            // south
            if (currentRow + 1 < input.size() && !seen.contains(setScore(currentRow + 1, currentCol)) &&
                    currentValue + 1 == input.get(currentRow + 1).get(currentCol)) {
                queue.add(new ArrayList<>(Arrays.asList(currentRow + 1, currentCol)));
                if (!part2) seen.add(setScore(currentRow + 1, currentCol));
            }
            // west
            if (currentCol > 0 && !seen.contains(setScore(currentRow, currentCol - 1)) &&
                    currentValue + 1 == input.get(currentRow).get(currentCol - 1)) {
                queue.add(new ArrayList<>(Arrays.asList(currentRow, currentCol - 1)));
                if (!part2) seen.add(setScore(currentRow, currentCol - 1));
            }
            // east
            if (currentCol + 1 < input.get(currentRow).size() && !seen.contains(setScore(currentRow, currentCol + 1)) &&
                    currentValue + 1 == input.get(currentRow).get(currentCol + 1)) {
                queue.add(new ArrayList<>(Arrays.asList(currentRow, currentCol + 1)));
                if (!part2) seen.add(setScore(currentRow, currentCol + 1));
            }
        }
        return nines;
    }

    private int setScore(int row, int col) {
        return 100 * row + col;
    }

    @Override
    public String partOne(){
        int trailheads = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                if (input.get(i).get(j) == 0) {
                    trailheads += searchNines(i, j, false);
                }
            }
        }
        return String.valueOf(trailheads);
    }

    // https://www.reddit.com/r/adventofcode/comments/1haulfr/2024_day_10_part_2_when_your_first_attempt_at/?utm_source=share&utm_medium=web3x&utm_name=web3xcss&utm_term=1&utm_content=share_button

    @Override
    public String partTwo() {
        int trailheads = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                if (input.get(i).get(j) == 0) {
                    trailheads += searchNines(i, j, true);
                }
            }
        }
        return String.valueOf(trailheads);
    }

    @Override
    public int getDay() {
        return 10;
    }

    public static void main(String[] args) throws IOException {
        Day10 d = new Day10();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
