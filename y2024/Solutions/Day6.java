package y2024.Solutions;


import Util_Java.CellState;
import Util_Java.Parse;

import java.io.IOException;
import java.util.List;

public class Day6 extends Solution {
    List<List<CellState>> puzzleMap;

    public Day6() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        List<String> input = parser.readFile(day);
        puzzleMap = parser.make2DMapCellState(input);
    }

    private int[] findInitialLocation() {
        // find initial location of guard
        for (int i = 0; i < puzzleMap.size(); i++) {
            for (int j = 0; j < puzzleMap.get(i).size(); j++) {
                CellState cell = puzzleMap.get(i).get(j);
                if (cell != CellState.EMPTY && cell != CellState.WALL) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    @Override
    public String partOne(){
        try {
            int[] loc = findInitialLocation();
            if (loc == null) System.exit(2406);
            int row = loc[0];
            int col = loc[1];

            // let the guard walk around
            // will get broken by IndexOutOfBoundsException
            while (true) {
                CellState direction = puzzleMap.get(row).get(col);
                switch (direction) {
                    case CellState.UP:
                        if (puzzleMap.get(row - 1).get(col) == CellState.WALL) {
                            puzzleMap.get(row).set(col, CellState.RIGHT);
                        } else {
                            puzzleMap.get(row).set(col, CellState.OCCUPIED);
                            puzzleMap.get(row - 1).set(col, CellState.UP);
                            row = row - 1;
                        }
                        break;
                    case CellState.RIGHT:
                        if (puzzleMap.get(row).get(col + 1) == CellState.WALL) {
                            puzzleMap.get(row).set(col, CellState.DOWN);
                        } else {
                            puzzleMap.get(row).set(col, CellState.OCCUPIED);
                            puzzleMap.get(row).set(col + 1, CellState.RIGHT);
                            col = col + 1;
                        }
                        break;
                    case CellState.DOWN:
                        if (puzzleMap.get(row + 1).get(col) == CellState.WALL) {
                            puzzleMap.get(row).set(col, CellState.LEFT);
                        } else {
                            puzzleMap.get(row).set(col, CellState.OCCUPIED);
                            puzzleMap.get(row + 1).set(col, CellState.DOWN);
                            row = row + 1;
                        }
                        break;
                    case CellState.LEFT:
                        if (puzzleMap.get(row).get(col - 1) == CellState.WALL) {
                            puzzleMap.get(row).set(col, CellState.UP);
                        } else {
                            puzzleMap.get(row).set(col, CellState.OCCUPIED);
                            puzzleMap.get(row).set(col - 1, CellState.LEFT);
                            col = col - 1;
                        }
                        break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // this is expected behaviour
        }

        // check how many cell where visited by the guard
        int visited = 0;
        for (List<CellState> cellStates : puzzleMap) {
            for (CellState cell : cellStates) {
                if (cell != CellState.EMPTY && cell != CellState.WALL) {
                    visited += 1;
                }
            }
        }

        return String.valueOf(visited);
    }

    @Override
    public String partTwo() {
        return "";
    }

    @Override
    public int getDay() {
        return 6;
    }

    public static void main(String[] args) throws IOException {
        Day6 d = new Day6();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
