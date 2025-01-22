package y2024.Solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Util_Java.*;

public class Day15 extends Solution {
    List<List<CellState>> part1map;
    List<List<CellState>> part2map;
    List<CellState> instructions;

    public Day15() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        List<String> input = parser.readFile(day);
        List<List<String>> blocks = parser.toBlocks(input);
        part1map = parser.make2DMapCellState(blocks.get(0));
        part2map = parser.make2DMapCellState(blocks.get(0));
        instructions = new ArrayList<>();
        for (String str : blocks.get(1)) {
            instructions.addAll(parser.string2CellState(str));
        }
    }

    /**
     * Find the next free space, or return -1 if a wall is encountered
     *
     * @param startRow
     * @param startCol
     * @param cellState
     * @param map
     * @return
     */
    private int nextFree(int startRow, int startCol, CellState cellState, List<List<CellState>> map) {
        int row = startRow;
        int col = startCol;
        while (map.get(row).get(col) != CellState.WALL) {
            // cell is empty so something can move to there
            if (map.get(row).get(col) == CellState.EMPTY) {
                if (cellState == CellState.DOWN || cellState == CellState.UP) {
                    return row;
                } else {
                    return col;
                }
            }

            // move 1 space
            switch (cellState) {
                case RIGHT:
                    col += 1;
                    break;
                case LEFT:
                    col -= 1;
                    break;
                case DOWN:
                    row += 1;
                    break;
                case UP:
                    row -= 1;
            }
        }

        // no free spaces between robot and wall
        return -1;
    }

    private int findRobot(List<List<CellState>> map, boolean returnRow) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).size(); j++) {
                if (map.get(i).get(j) == CellState.ME) {
                    if (returnRow) {
                        return i;
                    } else {
                        return j;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public String partOne() {
        int robotRow = findRobot(part1map, true);
        int robotCol = findRobot(part1map, false);

        for (CellState instruction : instructions) {
            switch (instruction) {
                case UP:
                    if (part1map.get(robotRow - 1).get(robotCol) == CellState.EMPTY) {
                        part1map.get(robotRow).set(robotCol, CellState.EMPTY);
                        robotRow -= 1;
                        part1map.get(robotRow).set(robotCol, CellState.ME);
                    } else {
                        int nextFreeRow = nextFree(robotRow - 1, robotCol, CellState.UP, part1map);
                        if (nextFreeRow != -1) {
                            part1map.get(robotRow).set(robotCol, CellState.EMPTY);
                            robotRow -= 1;
                            part1map.get(robotRow).set(robotCol, CellState.ME);
                            part1map.get(nextFreeRow).set(robotCol, CellState.OCCUPIED);
                        }
                    }
                    break;
                case DOWN:
                    if (part1map.get(robotRow + 1).get(robotCol) == CellState.EMPTY) {
                        part1map.get(robotRow).set(robotCol, CellState.EMPTY);
                        robotRow += 1;
                        part1map.get(robotRow).set(robotCol, CellState.ME);
                    } else {
                        int nextFreeRow = nextFree(robotRow + 1, robotCol, CellState.DOWN, part1map);
                        if (nextFreeRow != -1) {
                            part1map.get(robotRow).set(robotCol, CellState.EMPTY);
                            robotRow += 1;
                            part1map.get(robotRow).set(robotCol, CellState.ME);
                            part1map.get(nextFreeRow).set(robotCol, CellState.OCCUPIED);
                        }
                    }
                    break;
                case LEFT:
                    if (part1map.get(robotRow).get(robotCol - 1) == CellState.EMPTY) {
                        part1map.get(robotRow).set(robotCol, CellState.EMPTY);
                        robotCol -= 1;
                        part1map.get(robotRow).set(robotCol, CellState.ME);
                    } else {
                        int nextFreeCol = nextFree(robotRow, robotCol - 1, CellState.LEFT, part1map);
                        if (nextFreeCol != -1) {
                            part1map.get(robotRow).set(robotCol, CellState.EMPTY);
                            robotCol -= 1;
                            part1map.get(robotRow).set(robotCol, CellState.ME);
                            part1map.get(robotRow).set(nextFreeCol, CellState.OCCUPIED);
                        }
                    }
                    break;
                case RIGHT:
                    if (part1map.get(robotRow).get(robotCol + 1) == CellState.EMPTY) {
                        part1map.get(robotRow).set(robotCol, CellState.EMPTY);
                        robotCol += 1;
                        part1map.get(robotRow).set(robotCol, CellState.ME);
                    } else {
                        int nextFreeCol = nextFree(robotRow, robotCol + 1, CellState.RIGHT, part1map);
                        if (nextFreeCol != -1) {
                            part1map.get(robotRow).set(robotCol, CellState.EMPTY);
                            robotCol += 1;
                            part1map.get(robotRow).set(robotCol, CellState.ME);
                            part1map.get(robotRow).set(nextFreeCol, CellState.OCCUPIED);
                        }
                    }
                    break;
                default:
                    System.exit(15101);
            }
        }
        int sum = 0;
        for (int i = 0; i < part1map.size(); i++) {
            for (int j = 0; j < part1map.get(0).size(); j++) {
                if (part1map.get(i).get(j) == CellState.OCCUPIED) {
                    sum += 100 * i + j;
                }
            }
        }
        return String.valueOf(sum);
    }

    @Override
    public String partTwo() {

        return "";
    }

    @Override
    public int getDay() {
        return 15;
    }

    public static void main(String[] args) throws IOException {
        Day15 d = new Day15();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
