package y2020.src.Day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartB {
    private int nOfLines = 262;
    private BufferedReader reader;
    private String[] array = new String[nOfLines];

    public static void main(String[] args) {
        PartB part = new PartB();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            reader = new BufferedReader(new FileReader("y2020/src/Day16/input.txt"));
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

            //for all fields, make a list for all posible values
            List<List<Integer>> possibleNumbers = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                String[] numberRanges = array[i].split(": ")[1].split(" or ");
                List<Integer> subList = new ArrayList<>();
                for (int j = 0; j <= 1; j++) {
                    String[] minmax = numberRanges[j].split("-");
                    int min = Integer.parseInt(minmax[0]);
                    int max = Integer.parseInt(minmax[1]);
                    for (int k = min; k <= max; k++) {
                        subList.add(k);
                    }
                }
                possibleNumbers.add(i, subList);
            }

            //list of possible positions for each field of nearby tickets
            List<List<Integer>> possibleFields = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                List<Integer> subList = new ArrayList<>();
                for (int j = 0; j < 20; j++) {
                    subList.add(j);
                }
                possibleFields.add(i, subList);
            }

            //for all nearby tickets
            for (int i = 25; i < nOfLines; i++) {
                String[] numbers = array[i].split(",");
                boolean valid = true;

                //check of line is invalid
                for (int j = 0; j < numbers.length; j++) {
                    int number = Integer.parseInt(numbers[j]);
                    valid = !excludedNumbers.contains(number);
                    if (!valid) {
                        break;
                    }
                }

                //if valid, check for each field if they are a valid option
                int j = 0;
                while (valid && j < numbers.length) {
                    int number = Integer.parseInt(numbers[j]);
                    for (int k = 0; k < possibleFields.size(); k++) {
                        if (possibleFields.get(k).contains(j)) {
                            if (!possibleNumbers.get(k).contains(number)) {
                                possibleFields.get(k).remove(possibleFields.get(k).indexOf(j));
                            }
                        }
                    }

                    j++;
                }
            }

            debugPrint(possibleFields);

            for (int i = 0; i < possibleFields.size() - 1; i++) { //do this for x amount of times
                int oneCounter = 0;
                for (int j = 0; j < possibleFields.size(); j++) { //for each field
                    if (possibleFields.get(j).size() == 1) { //if size is 1
                        oneCounter++;
                        for (int k = 0; k < possibleFields.size(); k++) {
                            int indexToRemove = possibleFields.get(k).indexOf(possibleFields.get(j).get(0));
                            if (indexToRemove != -1 && k != j) {
                                possibleFields.get(k).remove(indexToRemove);
                            }
                        }
                    }
                }
                debugPrint(possibleFields);
                if (oneCounter == 20) {
                    break;
                }
            }



            long answer = 1;
            String[] myTicket = array[22].split(",");
            for (int i = 0; i < 6; i++) {
                answer *= Integer.parseInt(myTicket[possibleFields.get(i).get(0)]);
            }

            System.out.println("answer: " + answer);
        } catch (NumberFormatException e) {
            System.exit(1);
        }
    }

    public void debugPrint(List<List<Integer>> possibleFields) {
        String message = "Possible options per field (debugging line): ";
        for (int i = 0; i < possibleFields.size(); i++) {
            message = message + possibleFields.get(i).size();
            if (i != possibleFields.size() - 1) {
                message = message + ", ";
            }
        }
        System.out.println(message);
    }
}