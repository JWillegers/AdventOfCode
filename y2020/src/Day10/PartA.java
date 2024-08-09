package y2020.src.Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartA {

    public static void main(String[] args) {
        new PartA().solution();
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

        int plug = 0;
        int laptop = 0;
        Collections.sort(input);
        int one = 0;
        int three = 1; //The last jolt to the laptop has a difference of 3

        for (int i = 0; i < nOfLines; i++) {
            if (i == 0) {
                if (input.get(0) == 1) {
                    one++;
                } else if (input.get(0) == 3) {
                    three++;
                }
            } else {
                int difference = input.get(i) - input.get(i - 1);
                if (difference == 1) {
                    one++;
                } else if (difference == 3) {
                    three++;
                } else if (difference > 3) {
                    System.exit(2);
                }
            }

        }

        System.out.println(one * three);
    }
}
