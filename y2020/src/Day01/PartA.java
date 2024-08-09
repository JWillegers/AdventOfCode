package y2020.src.Day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartA {
    public static void main(String[] args) {
        try {
            int nOfLines = 200;
            BufferedReader reader = new BufferedReader(new FileReader("y2020/src/Day01/input.txt"));
            int[] array = new int[nOfLines];
            for (int i = 0; i < nOfLines; i++) {
                array[i] = Integer.parseInt(reader.readLine());
            }
            reader.close();

            for (int a = 0; a < nOfLines; a++) {
                for (int b = a + 1; b < nOfLines; b++) {
                    if (array[a] + array[b] == 2020) {
                        System.out.println(array[a]*array[b]);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

    }
}
