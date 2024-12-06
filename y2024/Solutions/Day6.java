package y2024.Solutions;


import Util_Java.CellState;
import Util_Java.Parse;

import java.io.IOException;
import java.util.List;

public class Day6 extends Solution {
    private List<List<CellState>> puzzleMap;
    private Parse parse;
    private List<String> input;
    private int originalStartingRow;
    private int originalStartingCol;

    public Day6() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        input = parser.readFile(day);
        this.parse = parser;
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
        puzzleMap = parse.make2DMapCellState(input);
        try {
            int[] loc = findInitialLocation();
            if (loc == null) System.exit(2406);
            int row = loc[0];
            int col = loc[1];
            // also store globally for part2
            originalStartingRow = loc[0];
            originalStartingCol = loc[1];

            // let the guard walk around
            // will get broken by IndexOutOfBoundsException
            while (true) {
                CellState direction = puzzleMap.get(row).get(col);
                switch (direction) {
                    case CellState.UP:
                        // if wall found, turn 90 degrees
                        // else move 1 cell up
                        if (puzzleMap.get(row - 1).get(col) == CellState.WALL) {
                            puzzleMap.get(row).set(col, CellState.RIGHT);
                        } else {
                            puzzleMap.get(row).set(col, CellState.OCCUPIED);
                            puzzleMap.get(row - 1).set(col, CellState.UP);
                            row = row - 1;
                        }
                        break;
                    // if wall found, turn 90 degrees
                    // else move 1 cell to the right
                    case CellState.RIGHT:
                        if (puzzleMap.get(row).get(col + 1) == CellState.WALL) {
                            puzzleMap.get(row).set(col, CellState.DOWN);
                        } else {
                            puzzleMap.get(row).set(col, CellState.OCCUPIED);
                            puzzleMap.get(row).set(col + 1, CellState.RIGHT);
                            col = col + 1;
                        }
                        break;
                    // if wall found, turn 90 degrees
                    // else move 1 cell down
                    case CellState.DOWN:
                        if (puzzleMap.get(row + 1).get(col) == CellState.WALL) {
                            puzzleMap.get(row).set(col, CellState.LEFT);
                        } else {
                            puzzleMap.get(row).set(col, CellState.OCCUPIED);
                            puzzleMap.get(row + 1).set(col, CellState.DOWN);
                            row = row + 1;
                        }
                        break;
                    // if wall found, turn 90 degrees
                    // else move 1 cell to the left
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
        int loopsFound = 0;
        // for each traveled location in part1, create a new map with one of those location replace by a wall
        for (int i = 0; i < puzzleMap.size(); i++) {
            for (int j = 0; j < puzzleMap.get(i).size(); j++) {
                // don't place a wall on the starting cell
                if (i == originalStartingRow && j == originalStartingCol) {
                    continue;
                }
                CellState cell = puzzleMap.get(i).get(j);
                // check if the cell is visited in part 1
                if (cell != CellState.EMPTY && cell != CellState.WALL) {
                    // create a new map (shallow copy vs deep copy)
                    List<List<CellState>> newMap = parse.make2DMapCellState(input);
                    newMap.get(i).set(j, CellState.WALL);
                    if (checkNewMap(newMap))  {
                        loopsFound++;
                    }
                }
            }
        }
        return String.valueOf(loopsFound);
    }

    private boolean checkNewMap(List<List<CellState>> newMap) {
        try{
            int row = originalStartingRow;
            int col = originalStartingCol;
            int loopStartRow = 0;
            int loopStartCol = 0;
            CellState loopStartCell = CellState.WALL; // obvious wrong value in case we do visit (0, 0)
            int steps = 0;
            int stepLimit = newMap.size() * newMap.getFirst().size() / 2;
            // first walk stepLimit amount of steps, as the guard doesn't always start in the loop
            // this should give enough times for a non-loop to be filtered out
            // but to be sure we check if we revisited the same cell in the same orientation
            while (steps < stepLimit || !(row == loopStartRow && col == loopStartCol &&
                    newMap.get(row).get(col) == loopStartCell)) {
                CellState direction = newMap.get(row).get(col);
                steps++;
                // start of checking the loop
                if (steps == stepLimit) {
                    loopStartRow = row;
                    loopStartCol = col;
                    loopStartCell = newMap.get(row).get(col);
                }


                switch (direction) {
                    // if wall found, turn 90 degrees
                    // else move 1 cell up
                    case CellState.UP:
                        if (newMap.get(row - 1).get(col) == CellState.WALL) {
                            newMap.get(row).set(col, CellState.RIGHT);
                        } else {
                            newMap.get(row - 1).set(col, CellState.UP);
                            row = row - 1;
                        }
                        break;
                    // if wall found, turn 90 degrees
                    // else move 1 cell to the right
                    case CellState.RIGHT:
                        if (newMap.get(row).get(col + 1) == CellState.WALL) {
                            newMap.get(row).set(col, CellState.DOWN);
                        } else {
                            newMap.get(row).set(col + 1, CellState.RIGHT);
                            col = col + 1;
                        }
                        break;
                    // if wall found, turn 90 degrees
                    // else move 1 cell down
                    case CellState.DOWN:
                        if (newMap.get(row + 1).get(col) == CellState.WALL) {
                            newMap.get(row).set(col, CellState.LEFT);
                        } else {
                            newMap.get(row + 1).set(col, CellState.DOWN);
                            row = row + 1;
                        }
                        break;
                    // if wall found, turn 90 degrees
                    // else move 1 cell to the left
                    case CellState.LEFT:
                        if (newMap.get(row).get(col - 1) == CellState.WALL) {
                            newMap.get(row).set(col, CellState.UP);
                        } else {
                            newMap.get(row).set(col - 1, CellState.LEFT);
                            col = col - 1;
                        }
                        break;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // this is expected behaviour
            return false;
        }
        return true;
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
