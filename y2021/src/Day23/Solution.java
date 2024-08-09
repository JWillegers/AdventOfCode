package y2021.src.Day23;

import java.util.Arrays;
import java.util.HashMap;

public class Solution {
    private HashMap<Character, Integer> costs = new HashMap<>();
    private HashMap<Character, Integer> column = new HashMap<>();
    private int lowestCost = 1000000000;

    public static void main(String[] args) {
        Solution sol = new Solution();
        sol.setup();
    }

    public void setup() {
        long start = System.currentTimeMillis();
        costs.put('A', 1);
        costs.put('B', 10);
        costs.put('C', 100);
        costs.put('D', 1000);
        column.put('A', 2);
        column.put('B', 4);
        column.put('C', 6);
        column.put('D', 8);

        /*
            00000000001
            01234567890
            ...........
            ##D#B#B#A##
            ##C#C#D#A##
         */
        char[][] burrow = new char[3][11];
        //INPUT HERE
        burrow[1][8] = 'A';
        burrow[2][8] = 'A';
        burrow[1][4] = 'B';
        burrow[1][6] = 'B';
        burrow[2][2] = 'C';
        burrow[2][4] = 'C';
        burrow[1][2] = 'D';
        burrow[2][6] = 'D';

        for (int j = 1; j <= 2; j++){
            for (int i = 0; i <= 10; i++) {
                if (burrow[j][i] == '\0') {
                    burrow[j][i] = '#';
                }
                burrow[0][i] = '.';

            }
        }

        solution(burrow, 0 , 2);
        System.out.println("part 1 answer: " + lowestCost);
        long mid = System.currentTimeMillis();
        System.out.println("part 1 time: " + ((double) mid - start)/1000 + " seconds");
        lowestCost = 1000000000;
        /*
            00000000001
            01234567890
            ...........
            ##D#B#B#A##
            ##C#C#D#A##
         */
        burrow = new char[5][11];
        burrow[1][8] = 'A';
        burrow[2][8] = 'A';
        burrow[3][6] = 'A';
        burrow[4][8] = 'A';
        burrow[1][4] = 'B';
        burrow[1][6] = 'B';
        burrow[2][6] = 'B';
        burrow[3][4] = 'B';
        burrow[2][4] = 'C';
        burrow[3][8] = 'C';
        burrow[4][2] = 'C';
        burrow[4][4] = 'C';
        burrow[1][2] = 'D';
        burrow[2][2] = 'D';
        burrow[3][2] = 'D';
        burrow[4][6] = 'D';

        for (int j = 1; j <= 4; j++){
            for (int i = 0; i <= 10; i++) {
                if (burrow[j][i] == '\0') {
                    burrow[j][i] = '#';
                }
                burrow[0][i] = '.';

            }
        }

        solution(burrow, 0, 4);
        System.out.println("part 2 answer: " + lowestCost);
        System.out.println("part 2 time: " + ((double) System.currentTimeMillis() - mid)/1000 + " seconds");
    }

    public void solution(char[][] burrow, int cost, int depth) {
        boolean result = true;
        int y = 1;
        while (result && y <= depth) {
            result = burrow[y][2] == 'A' && burrow[y][4] == 'B' && burrow[y][6] == 'C' && burrow[y][8] == 'D';
            y++;
        }
        if (result) {
            if (cost < lowestCost) {
                lowestCost = cost;
            }
        } else {
            boolean run = true;
            int i = 0;
            while (run && i < burrow.length) {
                int j = 0;
                while (run && j < burrow[0].length) {
                    char current = burrow[i][j];
                    if (current != '#' && current != '.'
                        && ((!(j == 2 && current == 'A')
                        && !(j == 4 && current == 'B')
                        && !(j == 6 && current == 'C')
                        && !(j == 8 && current == 'D'))
                        || checkBelow(burrow, j, depth, i))) {
                        if (i == 0) {
                            run = !toFinalDestination(i, j, current, burrow, cost, depth);
                        } else {
                            if (i == 1 || checkAbove(burrow, i, j)) {
                                if (!toFinalDestination(i, j, current, burrow, cost, depth)) {
                                    for (int m = 0; m < burrow[0].length; m++) {
                                        if ((m % 2 == 1 || m == 0 || m == burrow[0].length - 1) && burrow[0][m] == '.' && noObstacle(burrow, m, j)) {
                                            char[][] newBurrow = new char[burrow.length][];
                                            for (int p = 0; p < burrow.length; p++) {
                                                newBurrow[p] = Arrays.copyOf(burrow[p], burrow[p].length);
                                            }
                                            newBurrow[i][j] = '.';
                                            newBurrow[0][m] = current;
                                            solution(newBurrow, cost + costs.get(current) * (Math.abs(m - j) + i), depth);

                                        }
                                    }
                                } else {
                                    run = false;
                                }
                            }
                        }
                    }
                    j++;
                }
                i++;
            }
        }
    }

    public boolean checkBelow(char[][] burrow, int columnID, int depth, int start) {
        char toCheck = 'A';
        if (columnID == 4) {
            toCheck = 'B';
        } else if (columnID == 6) {
            toCheck = 'C';
        } else if (columnID == 8) {
            toCheck = 'D';
        }
        for (int i = start + 1; i <= depth; i++) {
            if (burrow[i][columnID] != toCheck) {
                return true;
            }
        }
        return false;
    }

    public boolean checkAbove(char[][] burrow, int start, int j) {
        for (int i = start - 1; i > 0; i--) {
            if (burrow[i][j] != '.') {
                return false;
            }
        }
        return true;
    }

    public boolean noObstacle(char[][] burrow, int dest, int start) {
        if (start - dest > 0) {
            for (int i = start - 1; i >= dest; i--) {
                if (burrow[0][i] != '.') {
                    return false;
                }
            }
        } else {
            for (int i = start + 1; i <= dest; i++) {
                if (burrow[0][i] != '.') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean toFinalDestination(int i, int j, char current, char[][] burrow, int cost, int depth) {
        char[][] newBurrow = new char[burrow.length][];
        for (int p = 0; p < burrow.length; p++) {
            newBurrow[p] = Arrays.copyOf(burrow[p], burrow[p].length);
        }
        newBurrow[i][j] = '.';
        int destColumn = column.get(current);
        if (noObstacle(burrow, destColumn, j)){
            for (int m = depth; m > 0; m--) {
                if (burrow[m][destColumn] == '.') {
                    if (m != depth) {
                        for (int n = depth; n > m; n--) {
                            if (burrow[n][destColumn] != current) {
                                return false;
                            }
                        }
                    }
                    newBurrow[m][destColumn] = current;
                    solution(newBurrow, cost + costs.get(current) * (m + i + Math.abs(j - destColumn)), depth);
                    return true;
                }
            }
        }
        return false;
    }
}
