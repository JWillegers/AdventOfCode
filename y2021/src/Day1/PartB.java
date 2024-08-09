package y2021.src.Day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartB {
    private int nOfLines = 2000;
    private BufferedReader reader;
    private int[] array = new int[nOfLines];

    public static void main(String[] args) {
        PartB part = new PartB();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            reader = new BufferedReader(new FileReader("y2021/src/Day1/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = Integer.parseInt(reader.readLine());
            }
            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution() {
        int increased = 0;
        for (int i = 3; i < nOfLines; i++) {
            if (array[i - 3] < array[i]) { //array[i - 2] and array[i - 1] used in both sums, so negligible
                increased++;
            }
        }

        System.out.println(increased);
    }
}
