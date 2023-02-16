package Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<String> oxygen = new ArrayList<>();
        List<String> co2 = new ArrayList<>();

        for (int i = 0; i < nOfLines; i++) {
            oxygen.add(array[i]);
            co2.add(array[i]);
        }

        int bitLength = 12;
        int bitCheck = 0;

        while (oxygen.size() > 1) {
            int oneCounter = 0;

            for (String number : oxygen) {
                if (number.charAt(bitCheck) == '1') {
                    oneCounter++;
                }
            }

            char check;
            if (oneCounter >= (double) oxygen.size() / 2) {
                check = '0';
            } else {
                check = '1';
            }

            List<String> toRemove = new ArrayList<>();
            for (String number : oxygen) {
                if (number.charAt(bitCheck) == check) {
                    toRemove.add(number);
                }
            }
            //extra loop get rid of ConcurrentModificationError
            for (String number : toRemove) {
                oxygen.remove(oxygen.indexOf(number));
            }

            bitCheck = bitCheck + 1 % bitLength;
        }

        bitCheck = 0;

        while (co2.size() > 1) {
            int oneCounter = 0;

            for (String number : co2) {
                if (number.charAt(bitCheck) == '1') {
                    oneCounter++;
                }
            }

            char check;
            if (oneCounter >= (double) co2.size() / 2) {
                check = '1';
            } else {
                check = '0';
            }

            List<String> toRemove = new ArrayList<>();
            for (String number : co2) {
                if (number.charAt(bitCheck) == check) {
                    toRemove.add(number);
                }
            }
            //extra loop get rid of ConcurrentModificationError
            for (String number : toRemove) {
                co2.remove(co2.indexOf(number));
            }
            bitCheck = bitCheck + 1 % bitLength;
        }

        int decimalOxygen = 0;
        int decimalCO2 = 0;
        for (int i = 0; i < bitLength; i++) {
            if (oxygen.get(0).charAt(i) == '1') {
                decimalOxygen += Math.pow(2, bitLength - i - 1);
            }
            if (co2.get(0).charAt(i) == '1') {
                decimalCO2 += Math.pow(2, bitLength - i - 1);
            }
        }


        System.out.println(decimalCO2 * decimalOxygen);
    }
}