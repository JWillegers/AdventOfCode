package y2024.Solutions;


import Util_Java.Parse;

import java.io.IOException;
import java.util.List;

public class Day4 extends Solution {

    List<List<Character>> input;

    public Day4() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        input = parser.make2DMapChar(parser.readFile(day));
    }

    private boolean checkBoundsDay4(int row_shift, int col_shift, int row_curr, int col_curr) {
        boolean rmin = 0 <= row_curr + row_shift;
        boolean cmin = 0 <= col_curr + col_shift;
        boolean rmax = row_curr + row_shift <= input.size() - 1;
        boolean cmax = col_curr + col_shift <= input.get(0).size() - 1;
        return rmin && cmin && rmax && cmax;
    }

    @Override
    public String partOne() {
        int xmas = 0;

        // loop over each char
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                // if not X ignore
                if (input.get(i).get(j) != 'X') {
                    continue;
                }

                // check south
                if (checkBoundsDay4(3, 0, i, j)) {
                    if (input.get(i + 1).get(j) == 'M' && input.get(i + 2).get(j) == 'A' && input.get(i + 3).get(j) == 'S') {
                        xmas++;
                    }

                    // check south east
                    if (checkBoundsDay4(3, 3, i, j)) {
                        if (input.get(i + 1).get(j + 1) == 'M' && input.get(i + 2).get(j + 2) == 'A' && input.get(i + 3).get(j + 3) == 'S') {
                            xmas++;
                        }
                    }

                    // check south west
                    if (checkBoundsDay4(3, -3, i, j)) {
                        if (input.get(i + 1).get(j - 1) == 'M' && input.get(i + 2).get(j - 2) == 'A' && input.get(i + 3).get(j - 3) == 'S') {
                            xmas++;
                        }
                    }
                }

                // check north
                if (checkBoundsDay4(-3, 0, i, j)) {

                    if (input.get(i - 1).get(j) == 'M' && input.get(i - 2).get(j) == 'A' && input.get(i - 3).get(j) == 'S') {
                        xmas++;
                    }

                    // check north east
                    if (checkBoundsDay4(-3, 3, i, j)) {
                        if (input.get(i - 1).get(j + 1) == 'M' && input.get(i - 2).get(j + 2) == 'A' && input.get(i - 3).get(j + 3) == 'S') {
                            xmas++;
                        }
                    }

                    // check north west
                    if (checkBoundsDay4(-3, -3, i, j)) {
                        if (input.get(i - 1).get(j - 1) == 'M' && input.get(i - 2).get(j - 2) == 'A' && input.get(i - 3).get(j - 3) == 'S') {
                            xmas++;
                        }
                    }
                }

                // check west
                if (checkBoundsDay4(0, -3, i, j)) {
                    if (input.get(i).get(j - 1) == 'M' && input.get(i).get(j - 2) == 'A' && input.get(i).get(j - 3) == 'S') {
                        xmas++;
                    }
                }

                // check east
                if (checkBoundsDay4(0, 3, i, j)) {
                    if (input.get(i).get(j + 1) == 'M' && input.get(i).get(j + 2) == 'A' && input.get(i).get(j + 3) == 'S') {
                        xmas++;
                    }
                }
            }
        }
        return String.valueOf(xmas);
    }

    @Override
    public String partTwo() {
        int xmas = 0;

        // loop over each char
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).size(); j++) {
                // if not A ignore
                if (input.get(i).get(j) != 'A') {
                    continue;
                }

                // check the four different rotations
                if (checkBoundsDay4(1, 1, i, j) && checkBoundsDay4(-1, -1, i, j)) {
                    if (input.get(i - 1).get(j - 1) == 'M' && input.get(i - 1).get(j + 1) == 'M' &&
                            input.get(i + 1).get(j - 1) == 'S' && input.get(i + 1).get(j + 1) == 'S') {
                        xmas++;
                    } else if (input.get(i - 1).get(j - 1) == 'S' && input.get(i - 1).get(j + 1) == 'S' &&
                            input.get(i + 1).get(j - 1) == 'M' && input.get(i + 1).get(j + 1) == 'M') {
                        xmas++;
                    } else if (input.get(i - 1).get(j - 1) == 'M' && input.get(i - 1).get(j + 1) == 'S' &&
                            input.get(i + 1).get(j - 1) == 'M' && input.get(i + 1).get(j + 1) == 'S') {
                        xmas++;
                    } else if (input.get(i - 1).get(j - 1) == 'S' && input.get(i - 1).get(j + 1) == 'M' &&
                            input.get(i + 1).get(j - 1) == 'S' && input.get(i + 1).get(j + 1) == 'M') {
                        xmas++;
                    }
                }
            }
        }
        return String.valueOf(xmas);
    }

    @Override
    public int getDay() {
        return 4;
    }

    public static void main(String[] args) throws IOException {
        Day4 d = new Day4();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
