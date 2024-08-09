package y2021.src.Day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartB {
    private int nOfLines = 500;
    private int diagramSize = 1000;
    private String[] array = new String[nOfLines];
    private int[][] diagram = new int[diagramSize][diagramSize];

    public static void main(String[] args) {
        PartB part = new PartB();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("y2021/src/Day5/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution() {
        for (int i = 0; i < nOfLines; i++) {
            String[] split = array[i].split(" ");
            int x1 = Integer.parseInt(split[0].split(",")[0]);
            int y1 = Integer.parseInt(split[0].split(",")[1]);
            int x2 = Integer.parseInt(split[2].split(",")[0]);
            int y2 = Integer.parseInt(split[2].split(",")[1]);

            if (x1 == x2) {
                for (int j = Math.min(y1, y2); j <= Math.max(y1, y2); j++) {
                    diagram[x1][j]++;
                }
            } else if (y1 == y2) {
                for (int j = Math.min(x1, x2); j <= Math.max(x1, x2); j++) {
                    diagram[j][y1]++;
                }
            } else {
                while (x1 != x2 && y1 != y2) {
                    diagram[x1][y1]++;

                    if (x1 > x2) {
                        x1--;
                    } else {
                        x1++;
                    }

                    if (y1 > y2) {
                        y1--;
                    } else {
                        y1++;
                    }

                }
                diagram[x2][y2]++;
            }
        }

        int sum = 0;

        for (int i = 0; i < diagramSize; i++) {
            for (int j = 0; j < diagramSize; j++) {
                if (diagram[i][j] >= 2) {
                    sum++;
                }
            }
        }

        System.out.println(sum);
    }
}
