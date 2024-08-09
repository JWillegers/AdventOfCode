package y2021.src.Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartB {
    private int nOfLines = 1000;
    private String[] array = new String[nOfLines];

    public static void main(String[] args) {
        PartB part = new PartB();
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
        int aim = 0;
        for (int i = 0; i < nOfLines; i++) {
            String[] split = array[i].split(" ");
            int number = Integer.parseInt(split[1]);
            if (split[0].equals("forward")) {
                horizontal += number;
                depth += number * aim;
            } else if (split[0].equals("down")) {
                aim += number;
            } else if (split[0].equals("up")) {
                aim -= number;
            }
        }

        System.out.println(horizontal * depth);
    }
}
