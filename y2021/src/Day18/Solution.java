package y2021.src.Day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Big thanks to u/Pun-Master-General and u/Dataforce in this reddit thread https://www.reddit.com/r/adventofcode/comments/rj1p92/2021_day_18_part_1_if_i_encounter_a_pair_that/
public class Solution {
    private int nOfLines = 100;
    private String[] array = new String[nOfLines];
    private int max;

    public static void main(String[] args) {
        Solution part = new Solution();
        part.setup();
        part.solution(part.array[0], 0);
        part.max = 0;
        for (int i = 0; i < part.nOfLines; i++) {
            for (int j = 0; j < part.nOfLines; j++) {
                if (i != j) {
                    part.solution("[" + part.array[i] + "," + part.array[j] + "]", -1);
                }
            }
        }
        System.out.println("part2: " + part.max);
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("y2021/src/Day18/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution(String line, int lineNumber) {
        boolean run;
        do {
            int bracketCounter = 0;
            boolean somethingHappend = false;
            for (int j = 0; j < line.length(); j++) {
                char symbol = line.charAt(j);
                if (symbol == '[') {
                    bracketCounter++;
                } else if (symbol == ']') {
                    bracketCounter--;
                }

                if (bracketCounter == 5) {
                    int k = j + 1;
                    boolean innerrun = true;
                    while (innerrun) {
                        symbol = line.charAt(k);
                        if (symbol == '[') {
                            line = explode(line, k);
                            innerrun = false;
                        } else if (symbol == ']') {
                            line = explode(line, j);
                            innerrun = false;
                        } else {
                            k++;
                        }
                    }
                    somethingHappend = true;
                    break;
                }

            }
            if (!somethingHappend) {
                for (int j = 0; j < line.length(); j++) {
                    if (Character.isDigit(line.charAt(j)) && Character.isDigit(line.charAt(j + 1))) {
                        line = split(line, j);
                        somethingHappend = true;
                        break;
                    }
                }
            }
            run = somethingHappend;

        } while (run);

        if (lineNumber >= 0 && lineNumber + 1 != nOfLines) {
            String newLine = "[" + line + "," + array[lineNumber + 1] + "]";
            solution(newLine,lineNumber + 1);
            if (lineNumber == 1) {
            }
        } else {
            while (line.contains("[")) {
                line = calculateSum(line);
            }
            if (lineNumber >= 0) {
                System.out.println("part1: " + line);
            } else {
                int number = Integer.parseInt(line);
                if (number > max) {
                    max = number;
                }
            }
        }


    }


    public String calculateSum(String line) {
        StringBuilder newLine = new StringBuilder();
        for (int i = 0; i < line.length() - 2; i++) {
            if (line.charAt(i) == '[') {
                for (int j = i + 1; j < line.length(); j++) {
                    if (line.charAt(j) == '[') {
                        break;
                    } else if (line.charAt(j) == ']') {
                        newLine.append(line.substring(0, i));
                        String subString = line.substring(i + 1, j);
                        String[] numbers = subString.split(",");
                        int sum = 3 * Integer.parseInt(numbers[0]) + 2 * Integer.parseInt(numbers[1]);
                        newLine.append(sum);
                        newLine.append(line.substring(j + 1));
                        return newLine.toString();
                    }
                }
            }
        }
        return null;
    }

    public String split(String line, int pos) {
        String newLine = line.substring(0, pos) + "[";
        double number = Double.parseDouble(String.valueOf(line.charAt(pos)) + line.charAt(pos + 1));
        newLine += (int) Math.floor(number / 2) + "," + (int) Math.ceil(number / 2);
        newLine += "]" + line.substring(pos + 2);
        return newLine;
    }

    public String explode(String line, int pos) {
        StringBuilder newLine = new StringBuilder();
        int i = pos - 1;
        boolean run = true;
        do {
            if (Character.isDigit(line.charAt(i))) {
                run = false;
            } else {
                i--;
            }
        } while (run && i > 0);

        int number;
        if (Character.isDigit(line.charAt(pos + 2))) {
            number = Integer.parseInt(String.valueOf(line.charAt(pos + 1)) + line.charAt(pos + 2));
        } else {
            number = Integer.parseInt(String.valueOf(line.charAt(pos + 1)));
        }

        if (i > 0) {
            if (Character.isDigit(line.charAt(i - 1))) {
                newLine.append(line.substring(0, i - 1));
                int sum = number + Integer.parseInt(line.charAt(i - 1) + String.valueOf(line.charAt(i)));
                newLine.append(sum);

            } else {
                newLine.append(line.substring(0, i));
                int sum = number + Integer.parseInt(String.valueOf(line.charAt(i)));
                newLine.append(sum);
            }
            newLine.append(line.substring(i + 1, pos));
            if (line.charAt(pos - 1) == ',' || line.charAt(pos - 1) == '[') {
                newLine.append("0");
            }
        } else { //first number pair is the one that needs to be exploded
            newLine.append(line.substring(0, pos) + "0");
        }

        int j = pos;
        run = true;
        do {
            if (line.charAt(j) == ']') {
                run = false;
            } else {
                j++;
            }
        } while (run);

        int k = j + 1;
        run = true;
        do {
            if (Character.isDigit(line.charAt(k))) {
                run = false;
            } else {
                k++;
            }
        } while (run && k < line.length());

        if (Character.isDigit(line.charAt(j - 2))) {
            number = Integer.parseInt(line.charAt(j - 2) + String.valueOf(line.charAt(j - 1)));
        } else {
            number = Integer.parseInt(String.valueOf(line.charAt(j - 1)));
        }

        if (k + 1 < line.length()) {
            newLine.append(line.substring(j + 1, k));
            if (Character.isDigit(line.charAt(k + 1))){
                int sum = number + Integer.parseInt(String.valueOf(line.charAt(k)) + line.charAt(k + 1));
                newLine.append(sum + line.substring(k + 2));
            } else {
                int sum = number + Integer.parseInt(String.valueOf(line.charAt(k)));
                newLine.append(sum + line.substring(k + 1));
            }
        } else {
            newLine.append(line.substring(j + 1));
        }

        return newLine.toString();
    }
}
