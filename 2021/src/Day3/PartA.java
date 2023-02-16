package Day3;

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
            BufferedReader reader = new BufferedReader(new FileReader("2021/src/Day3/input.txt"));
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
        int bitLength = 12;
        int gamma = 0;
        int epsilon = 0;
        for (int h = 0; h < bitLength;h++) {
            int oneCounter = 0;
            for (int i = 0; i < nOfLines; i++) {
                if (array[i].charAt(h) == '1') {
                    oneCounter++;
                }
            }
            if (oneCounter > nOfLines / 2) {
                gamma += Math.pow(2, bitLength - h - 1);
            } else {
                epsilon += Math.pow(2, bitLength - h - 1);
            }
        }
        System.out.println(gamma*epsilon);
    }
}
