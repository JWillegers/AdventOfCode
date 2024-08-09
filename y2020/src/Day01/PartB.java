package y2020.src.Day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartB {
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
                    for (int c = b + 1; c < nOfLines; c++) {
                        if (array[a] + array[b] + array[c] == 2020) {
                            System.out.println(array[a] * array[b] * array[c]);
                        }
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

    }
}