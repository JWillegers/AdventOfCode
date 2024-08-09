package y2020.src.Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartB {

    public static void main(String[] args) {
        new PartB().solution();
    }

    public void solution() {
        int nOfLines = 92;
        BufferedReader reader;
        List<Integer> input = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader("y2020/src/Day10/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                input.add(Integer.parseInt(reader.readLine()));
            }
            reader.close();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        input.add(0);
        Collections.sort(input);
        int fourInARow = 0;
        int threeInARow = 0;
        int twoInARow = 0;

        for (int i = 0; i < nOfLines; i++) {
            if (i + 4 < input.size()) {
                int current = input.get(i);
                if (current + 4 == input.get(i + 4)) {
                    fourInARow++;
                } else if (current + 3 == input.get(i + 3) && (i == 0 || current - 3 == input.get(i - 1))) {
                    threeInARow++;
                } else if (current + 2 == input.get(i + 2) && (i == 0 || current - 3 == input.get(i - 1))) {
                    twoInARow++;
                }
            } else if (i + 3 < input.size()) {
                int current = input.get(i);
                if (current + 3 == input.get(i + 3) && (i == 0 || current - 3 == input.get(i - 1))) {
                    threeInARow++;
                } else if (current + 2 == input.get(i + 2) && (i == 0 || current - 3 == input.get(i - 1))) {
                    twoInARow++;
                }
            }
            else if (i + 2 < input.size()) {
                int current = input.get(i);
                if (current + 2 == input.get(i + 2) && (i == 0 || current - 3 == input.get(i - 1))) {
                    twoInARow++;
                }
            }

        }

        //Credit for how to solve: https://www.reddit.com/r/adventofcode/comments/ka8z8x/comment/gfhcywp/?utm_source=share&utm_medium=web2x&context=3
        System.out.println((long) (Math.pow(7, fourInARow)*Math.pow(4, threeInARow)*Math.pow(2, twoInARow)));
    }
}
