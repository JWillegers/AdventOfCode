package y2021.src.Day25;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solution {
    private int nOfLines = 137;
    int offset = 2;
    private char[][] seaFloor = new char[nOfLines][nOfLines + offset];

    public static void main(String[] args) {
        Solution part = new Solution();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("y2021/src/Day25/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                String line = reader.readLine();
                for (int j = 0; j < nOfLines + offset; j++) {
                    seaFloor[i][j] = line.charAt(j);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution() {
        boolean run = true;
        int stepsDone = 0;
        while (run) {
            run = false;
            // >
            for(int i = 0; i < nOfLines; i++) {
                for(int j = 0; j < nOfLines + offset; j++) {
                    if (seaFloor[i][j] == '>') {
                        if (j < nOfLines + offset - 1 && seaFloor[i][j + 1] == '.') {
                            seaFloor[i][j] = 'p';
                            seaFloor[i][j + 1] = 'm';
                            run = true;
                        } else if (j == nOfLines + offset - 1 && seaFloor[i][0] == '.') {
                            seaFloor[i][j] = 'p';
                            seaFloor[i][0] = 'm';
                            run = true;
                        }
                    } else if (seaFloor[i][j] == 'm') {
                        seaFloor[i][j] = '>';
                    }
                }
            }
            for(int i = 0; i < nOfLines; i++) {
                if (seaFloor[i][0] == 'm') {
                    seaFloor[i][0] = '>';
                }
                for (int j = 0; j < nOfLines + offset; j++) {
                    if (seaFloor[i][j] == 'p') {
                        seaFloor[i][j] = '.';
                    }
                }
            }

            // v
            for(int i = 0; i < nOfLines; i++) {
                for(int j = 0; j < nOfLines + offset; j++) {
                    if (seaFloor[i][j] == 'v') {
                        if (i < nOfLines - 1 && seaFloor[i + 1][j] == '.') {
                            seaFloor[i][j] = 'p';
                            seaFloor[i + 1][j] = 'm';
                            run = true;
                        } else if (i == nOfLines - 1 && seaFloor[0][j] == '.') {
                            seaFloor[i][j] = 'p';
                            seaFloor[0][j] = 'm';
                            run = true;
                        }
                    } else if (seaFloor[i][j] == 'm') {
                        seaFloor[i][j] = 'v';
                    }
                }
            }
            for(int j = 0; j < nOfLines + offset; j++) {
                if (seaFloor[0][j] == 'm') {
                    seaFloor[0][j] = 'v';
                }
                for(int i = 0; i < nOfLines; i++) {
                    if (seaFloor[i][j] == 'p') {
                        seaFloor[i][j] = '.';
                    }
                }
            }

            stepsDone++;

        }
        System.out.println(stepsDone);
    }
}
