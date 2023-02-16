package Day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solution {
    private int nOfLines = 10;
    private int[][] array = new int[nOfLines][nOfLines];

    public static void main(String[] args) {
        Solution part = new Solution();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2021/src/Day11/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                String line = reader.readLine();
                for (int j = 0; j < nOfLines; j++) {
                    array[i][j] = Integer.parseInt(String.valueOf(line.charAt(j)));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution() {
        int flashes = 0;
        int i = 1;
        boolean run = true;
        while (run) {
            int flashCounter = 0;
            //increase everything by one
            for (int x = 0; x < nOfLines; x++) {
                for (int y = 0; y < nOfLines; y++) {
                    array[x][y]++;
                }
            }

            boolean noTwoDigits;
            do {
                noTwoDigits = true;
                for (int x = 0; x < nOfLines; x++) {
                    for (int y = 0; y < nOfLines; y++) {
                        if (array[x][y] > 9) {
                            flashes++;
                            flashCounter++;
                            noTwoDigits = false;
                            for (int m = Math.max(0, x - 1); m <= Math.min(x + 1, nOfLines - 1); m++) {
                                for (int n = Math.max(0, y - 1); n <= Math.min(nOfLines - 1, y + 1); n++) {
                                    if (!(m == x && n == y) && array[m][n] != 0) {
                                        array[m][n]++;
                                    }
                                }
                            }
                            array[x][y] = array[x][y] = 0;
                        }
                    }
                }
            } while (!noTwoDigits);
            if (i == 100) {
                System.out.println("Part 1: " + flashes + " flashes");
            }
            if (flashCounter == 100) {
                System.out.println("Part 2: step " + i);
                run = false;
            }
            i++;
        }


    }
}
