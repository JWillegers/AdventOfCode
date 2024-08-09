package y2020.src.Day09;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartB {

    public static void main(String[] args) {
        new PartB().solution();
    }

    public void solution() {
        int nOfLines = 1000;
        BufferedReader reader;
        long[] array = new long[nOfLines];

        try {
            reader = new BufferedReader(new FileReader("y2020/src/Day09/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = Long.parseLong(reader.readLine());
            }
            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            System.exit(1);
        }

        long missingProperty = 0;

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
                missingProperty = array[i];
                break;
            }
        }

        for (int i = 0; i < nOfLines; i++) {
            int counter = i;
            long sum = array[i];
            long largest = 0;
            long smallest = 0;
            while (sum < missingProperty) {
                if (counter == i) {
                    largest = sum;
                    smallest = sum;
                }
                counter++;
                long number = array[counter];
                sum += number;
                if (number < smallest) {
                    smallest = number;
                } else if (number > largest) {
                    largest = number;
                }
            }

            if (counter > i + 1 && sum == missingProperty) {
                System.out.println(smallest + largest);
                System.exit(1);
            }
        }

        System.out.println("FAIL");
    }
}
