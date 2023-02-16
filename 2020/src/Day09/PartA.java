package Day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartA {

    public static void main(String[] args) {
        new PartA().solution();
    }

    public void solution() {
        int nOfLines = 1000;
        BufferedReader reader;
        long[] array = new long[nOfLines];

        try {
            reader = new BufferedReader(new FileReader("2020/src/Day09/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = Long.parseLong(reader.readLine());
            }
            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            System.exit(1);
        }

        for (int i = 25; i < nOfLines; i++) {
            boolean check = false;
            for (int a = i - 25; a < i; a++) {
                for (int b = a + 1; b < i; b++) {
                    long sum = array[a] + array[b];
                    if (sum == array[i]) {
                        check = true;
                    }
                }
            }
            if (!check) {
                System.out.println(array[i]);
                System.exit(0);
            }
        }
    }
}
