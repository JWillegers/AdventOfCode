package Day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartA {
    private int nOfLines = 262;
    private BufferedReader reader;
    private String[] array = new String[nOfLines];

    public static void main(String[] args) {
        PartA part = new PartA();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            reader = new BufferedReader(new FileReader("2020/src/Day16/input.txt"));
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
        try {
            //setup of excludedNumbers
            List<Integer> excludedNumbers = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                excludedNumbers.add(i);
            }

            //remove all valid numbers from excludedNumbers
            for (int i = 0; i < 20; i++) {
                String[] numberRanges = array[i].split(": ")[1].split(" or ");
                for (int j = 0; j <= 1; j++) {
                    String[] minmax = numberRanges[j].split("-");
                    int min = Integer.parseInt(minmax[0]);
                    int max = Integer.parseInt(minmax[1]);
                    for (int k = min; k <= max; k++) {
                        int index = excludedNumbers.indexOf(k);
                        if (index != -1) {
                            excludedNumbers.remove(index);
                        }
                    }
                }
            }

            List<Integer> invalidNumbersFound = new ArrayList<>();

            //find all lines that have invalid numbers
            for (int i = 25; i < nOfLines; i++) {
                String[] numbers = array[i].split(",");

                for (int j = 0; j < numbers.length; j++) {
                    int number = Integer.parseInt(numbers[j]);
                    if (excludedNumbers.contains(number)) {
                        invalidNumbersFound.add(number);
                    }
                }
            }

            //calculate sum
            int sum = 0;
            for (int number : invalidNumbersFound) {
                sum += number;
            }


            System.out.println(sum);
        } catch (NumberFormatException e) {
            System.exit(1);
        }
    }
}