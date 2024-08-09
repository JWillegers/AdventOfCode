package y2021.src.Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartA {
    private int nOfLines = 1000;
    private String[] array = new String[nOfLines];

    public static void main(String[] args) {
        PartA part = new PartA();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("y2021/src/Day2/input.txt"));
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
        int horizontal = 0;
        int depth = 0;
        for (int i = 0; i < nOfLines; i++) {
            String[] split = array[i].split(" ");
            int number = Integer.parseInt(split[1]);
            if (split[0].equals("forward")) {
                horizontal += number;
            } else if (split[0].equals("down")) {
                depth += number;
            } else if (split[0].equals("up")) {
                depth -= number;
            }
        }

        System.out.println(horizontal * depth);
    }
}
