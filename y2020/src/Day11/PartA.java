package y2020.src.Day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartA {
    private int nOfLines = 93;
    private BufferedReader reader;
    private char[][] array;
    private int lineSize = 94; //94 comes from a quick print of line.length after line 23

    public static void main(String[] args) {
        PartA part = new PartA();
        part.setup();
        part.solution();
    }

    public PartA() {
        this.array = new char[nOfLines][lineSize];
    }

    public PartA(PartA part) {
        array = new char[nOfLines][lineSize];
        for (int i = 0; i < nOfLines; i++) {
            for (int j = 0; j < lineSize; j++) {
                array[i][j] = part.array[i][j];
            }
        }
    }

    public void setup() {
        try {
            reader = new BufferedReader(new FileReader("y2020/src/Day11/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                String line = reader.readLine();
                for (int j = 0; j < line.length(); j++) {
                    array[i][j] = line.charAt(j);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public List<Character> getNeighbours(PartA grid, int i, int j) {
        List<Character> myList = new ArrayList<>();

        //left top
        if (i != 0 && j != 0) {
            myList.add(grid.array[i - 1][j - 1]);
        }

        //middle top
        if (i != 0) {
            myList.add(grid.array[i - 1][j]);
        }

        //right top
        if (i != 0 && j != lineSize - 1) {
            myList.add(grid.array[i - 1][j + 1]);
        }

        //right middle
        if (j != lineSize - 1) {
            myList.add(grid.array[i][j + 1]);
        }

        //right bottom
        if (j != lineSize - 1 && i != nOfLines - 1) {
            myList.add(grid.array[i + 1][j + 1]);
        }

        //middle bottom
        if (i != nOfLines - 1) {
            myList.add(grid.array[i + 1][j]);
        }

        //left bottom
        if (i != nOfLines - 1 && j != 0) {
            myList.add(grid.array[i + 1][j - 1]);
        }

        //left middle
        if (j != 0) {
            myList.add(grid.array[i][j - 1]);
        }


        return myList;
    }

    public void solution() {
        int lastSum = 0;
        for (int k = 0; k < 1000; k++) {
            PartA copy = new PartA(this);
            for (int i = 0; i < nOfLines; i++) {
                for (int j = 0; j < lineSize; j++) {
                    if (this.array[i][j] != '.') {
                        List<Character> neighbours = getNeighbours(copy, i ,j);
                        if (!neighbours.contains('#')) {
                            this.array[i][j] = '#';
                        } else if (neighbours.size() >= 4) {
                            int innercount = 0;
                            for (int m = 0; m < neighbours.size(); m++) {
                                if (neighbours.get(m) == '#') {
                                    innercount++;
                                }
                            }
                            if (innercount >= 4) {
                                this.array[i][j] = 'L';
                            }
                        }

                    }
                }
            }
            int sum = 0;
            for (int i = 0; i < nOfLines; i++) {
                for (int j = 0; j < lineSize; j++) {
                    if (this.array[i][j] == '#') {
                        sum++;
                    }
                }
            }
            System.out.println(sum);
            if (sum == lastSum) {
                System.exit(0);
            }
            lastSum = sum;
        }
    }
}
