package y2024.Solutions;


import Util_Java.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day2 extends Solution {

    List<List<Integer>> input;

    public Day2() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        List<String> local_input = parser.readFile(day);
        input = new ArrayList<>();
        for (String report : local_input) {
            // split report
            List<String> levels = Arrays.asList(report.split(" "));
            input.add(parser.toInt(levels));
        }
    }

    public boolean report_save(List<Integer> report) {
        boolean increasing = false;
        for (int j = 1; j < report.size(); j++) {
            // check if first pair is increasing or decreasing
            increasing = j == 1 ? report.get(1) > report.get(0) : increasing;

            // check if always increasing / decreasing
            if (report.get(j) > report.get(j-1) != increasing) {
                break;
            }

            // check step size
            int step = Math.abs(report.get(j) - report.get(j-1));
            if (step < 1 || step > 3) {
                break;
            }

            // increase safe_report by 1 if last level
            if (j + 1 == report.size()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String partOne(){
        int safe_report = 0;
        // loop over all reports
        for (List<Integer> report : input) {
            if (report_save(report)) {
                safe_report += 1;
            }
        }
        return String.valueOf(safe_report);
    }

    @Override
    public String partTwo() {
        int safe_report = 0;
        // loop over all reports
        for (List<Integer> report : input) {
            if (report_save(report)) {
                safe_report += 1;
            } else {
                for (int i = 0; i < report.size(); i++) {
                    List<Integer> adjusted_report = remove_level(report, i);
                    if (report_save(adjusted_report)) {
                        safe_report += 1;
                        break;
                    }
                }
            }
        }
        return String.valueOf(safe_report);
    }

    /**
     * Deepcopy report without the element at index level_index
     * @param original
     * @param level_index between 0 and original.size() [including, excluding]
     * @return
     */
    public List<Integer> remove_level(List<Integer> original, int level_index) {
        List<Integer> new_report = new ArrayList<>();
        for (int i = 0; i < original.size(); i++) {
            if (i != level_index) {
                new_report.add(original.get(i));
            }
        }
        return new_report;
    }

    @Override
    public int getDay() {
        return 2;
    }

    public static void main(String[] args) throws IOException {
        Day2 d = new Day2();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
