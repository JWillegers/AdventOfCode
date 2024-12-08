package y2024.Solutions;


import Util_Java.Parse;

import java.io.IOException;
import java.util.*;

public class Day7 extends Solution {
    List<List<Long>> input;
    List<Integer> part2Indexes;
    Long calibrationResult;

    public Day7() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        List<String> rawInput = parser.readFile(day);
        input = new ArrayList<>();
        for (String line : rawInput) {
            String[] splitByColon = line.split(": ");
            List<String> splitBySpace = Arrays.asList(splitByColon[1].split(" "));
            List<Long> lineParsed = new ArrayList<>();
            lineParsed.add(Long.parseLong(splitByColon[0]));
            lineParsed.addAll(parser.toLong(splitBySpace));
            input.add(lineParsed);
        }
        part2Indexes = new ArrayList<>();
    }

    private boolean searchValidEquation(int index, boolean part2) {
        // queue has format: [currenResult, index]
        List<List<Long>> queue = new ArrayList<>();
        List<Long> numbers = input.get(index);
        Long expectedResult = numbers.get(0);

        // start with the result being the first item
        queue.add(new ArrayList<>(Arrays.asList(numbers.get(1), (long) 2)));

        while (!queue.isEmpty()) {
            List<Long> current = queue.remove(0);
            if (current.get(1) >= numbers.size()) {
                if (current.get(0).equals(expectedResult)) {
                    return true;
                }
            } else {
                long currentResult = current.get(0);
                long nextValue = numbers.get(current.get(1).intValue());
                long nextIndex = 1 + current.get(1);
                // buffer in case overflow
                if (currentResult * nextValue <= expectedResult) {
                    queue.add(new ArrayList<>(Arrays.asList(currentResult * nextValue, nextIndex)));
                }
                // buffer in case overflow
                if (currentResult + nextValue <= expectedResult) {
                    queue.add(new ArrayList<>(Arrays.asList(currentResult + nextValue, nextIndex)));
                }
                if (part2) {
                    long concatResult = currentResult * (long) Math.pow(10, String.valueOf(nextValue).length()) + nextValue;
                    if (concatResult <= expectedResult) {
                        queue.add(new ArrayList<>(Arrays.asList(concatResult, nextIndex)));
                    }
                }
            }
        }
        if (!part2) {
            part2Indexes.add(index);
        }
        return false;
    }

    @Override
   public String partOne(){
        calibrationResult = (long) 0;
        for (int i = 0; i < input.size(); i++) {
            if (searchValidEquation(i, false)) {
                calibrationResult += input.get(i).get(0);
            }
        }
        return String.valueOf(calibrationResult);
    }

    @Override
    public String partTwo() {
        for (int i : part2Indexes) {
            if (searchValidEquation(i, true)) {
                calibrationResult += input.get(i).get(0);
            }
        }
        return String.valueOf(calibrationResult);
    }

    @Override
    public int getDay() {
        return 7;
    }

    public static void main(String[] args) throws IOException {
        Day7 d = new Day7();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
