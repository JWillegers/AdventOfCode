package y2020.src.Day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartB {
    private int nOfLines = 93;
    private BufferedReader reader;
    private char[][] array;
    private int lineSize = 94; //94 comes from a quick print of line.length after line 23

    public static void main(String[] args) {
        PartB part = new PartB();
        part.setup();
        part.solution();
    }

    public PartB() {
        this.array = new char[nOfLines][lineSize];
    }

    public PartB(PartB part) {
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

    public List<Character> getNeighbours(PartB grid, int i, int j) {
        List<Character> myList = new ArrayList<>();

        //going left & up
        if (i != 0 && j != 0) {
            int k = i - 1;
            int l = j - 1;
            boolean run = true;
            while (run && k >= 0 && l >= 0) {
                if (grid.array[k][l] != '.') {
                    myList.add(grid.array[k][l]);
                    run = false;
                }
                k--;
                l--;
            }
        }

        //going up
        for (int k = i - 1; k >= 0; k--) {
            if (grid.array[k][j] != '.') {
                myList.add(grid.array[k][j]);
                break;
            }
        }

        //going right & up
        if (i != 0 && j != lineSize - 1) {
            int k = i - 1;
            int l = j + 1;
            boolean run = true;
            while (run && k >= 0 && l < lineSize) {
                if (grid.array[k][l] != '.') {
                    myList.add(grid.array[k][l]);
                    run = false;
                }
                k--;
                l++;
            }
        }

        //going right
        for (int k = j + 1; k < lineSize; k++) {
            if (grid.array[i][k] != '.') {
                myList.add(grid.array[i][k]);
                break;
            }
        }

        //going right & down
        if (j != lineSize - 1 && i != nOfLines - 1) {
            int k = i + 1;
            int l = j + 1;
            boolean run = true;
            while (run && k < nOfLines && l < lineSize) {
                if (grid.array[k][l] != '.') {
                    myList.add(grid.array[k][l]);
                    run = false;
                }
                k++;
                l++;
            }
        }

        //going down
        for (int k = i + 1; k < nOfLines; k++) {
            if (grid.array[k][j] != '.') {
                myList.add(grid.array[k][j]);
                break;
            }
        }

        //going left & down
        if (i != nOfLines - 1 && j != 0) {
            int k = i + 1;
            int l = j - 1;
            boolean run = true;
            while (run && k < nOfLines && l >= 0) {
                if (grid.array[k][l] != '.') {
                    myList.add(grid.array[k][l]);
                    run = false;
                }
                k++;
                l--;
            }
        }

        //going left
        for (int k = j - 1; k >= 0; k--) {
            if (grid.array[i][k] != '.') {
                myList.add(grid.array[i][k]);
                break;
            }
        }


        return myList;
    }

    public void solution() {
        int lastSum = 0;
        for (int k = 0; k < 1000; k++) {
            PartB copy = new PartB(this);
            for (int i = 0; i < nOfLines; i++) {
                for (int j = 0; j < lineSize; j++) {
                    if (this.array[i][j] != '.') {
                        List<Character> neighbours = getNeighbours(copy, i ,j);
                        if (!neighbours.contains('#')) {
                            this.array[i][j] = '#';
                        } else if (neighbours.size() >= 5) {
                            int innercount = 0;
                            for (int m = 0; m < neighbours.size(); m++) {
                                if (neighbours.get(m) == '#') {
                                    innercount++;
                                }
                            }
                            if (innercount >= 5) {
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