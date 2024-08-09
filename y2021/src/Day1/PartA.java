package y2021.src.Day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartA {
    private int nOfLines = 2000;
    private BufferedReader reader;
    private int[] array = new int[nOfLines];

    public static void main(String[] args) {
        PartA part = new PartA();
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
        int lastNumber = array[0];
        for (int i = 1; i < nOfLines; i++) {
            if (array[i] > lastNumber) {
                increased++;
            }
            lastNumber = array[i];
        }

        System.out.println(increased);
    }
}
