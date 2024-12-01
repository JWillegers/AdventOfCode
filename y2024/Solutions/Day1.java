package y2024.Solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Util_Java.*;

public class Day1 extends Solution {
    List<Integer> left = new ArrayList<>();
    List<Integer> right = new ArrayList<>();

    public Day1() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        List<String> input = parser.readFile(day);

        // split each line in two numbers and add them to right and left list
        for (String pair : input) {
            String[] split = pair.split("   ");
            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[1]));
        }

        // sort the lists
        Collections.sort(left);
        Collections.sort(right);
    }

    @Override
    public String partOne(){
        // sum difference between left[i] and right[i]
        long sum = 0;
        for (int i = 0; i < left.size(); i++) {
            sum += Math.abs(left.get(i) - right.get(i));
        }

        return String.valueOf(sum);
    }

    @Override
    public String partTwo() {
        long similarity = 0;
        for (int number : left) {
            long right_occurrences = Collections.frequency(right, number);
            similarity += number * right_occurrences;
        }
        return String.valueOf(similarity);
    }

    @Override
    public int getDay() {
        return 1;
    }

    public static void main(String[] args) throws IOException {
        Day1 d = new Day1();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
