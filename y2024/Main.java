package y2024;

import y2024.Solutions.*;
import Util_Java.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //grab all solutions (not necessarily complete list)
        Solution[] allSolutions;
        allSolutions = new Solution[] {
                new Day1(), new Day2(), new Day3(), new Day4(), new Day5()
        };
        Parse p = new Parse();

        for (Solution solution : allSolutions) {
            int day = solution.getDay();
            System.out.println("===== Day " + day + " =====");
            try {
                solution.getInput(day, p);

                // solve and time part 1
                long start = System.nanoTime();
                String result = solution.partOne();
                double time = System.nanoTime() - start;
                if (time >  1_000_000_000.0) {
                    time = time / 1_000_000_000.0;
                    System.out.println("Solution Part 1: " + result + " (took " + time + " seconds)");
                } else {
                    time = time / 1_000_000.0;
                    System.out.println("Solution Part 1: " + result + " (took " + time + "ms)");
                }

                // solve and time part 2
                start = System.nanoTime();
                result = solution.partTwo();
                time = System.nanoTime() - start;
                if (time >  1_000_000_000.0) {
                    time = time / 1_000_000_000.0;
                    System.out.println("Solution Part 2: " + result + " (took " + time + " seconds)");
                } else {
                    time = time / 1_000_000.0;
                    System.out.println("Solution Part 2: " + result + " (took " + time + "ms)");
                }


            } catch (IOException e) {
                System.out.println("Input file was not found");
            }
        }

    }
}
