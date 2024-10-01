package y2024;

import y2024.Solutions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //grab all solutions (not necessarily complete)
        Solution[] allSolutions;
        allSolutions = new Solution[] {
                new Day1()
        };

        for (Solution solution : allSolutions) {
            int day = solution.getDay();
            System.out.println(STR."===== Day \{day} =====");
            try {
                solution.getInput(day);

                long start = System.nanoTime();
                String result = solution.partOne();
                double time = (System.nanoTime() - start) / 1_000_000.0;
                System.out.println(STR."Solution Part 1: \{result} (took \{time}ms)");

                start = System.nanoTime();
                result = solution.partTwo();
                time = (System.nanoTime() - start) / 1_000_000.0;
                System.out.println(STR."Solution Part 2: \{result} (took \{time}ms)");

            } catch (IOException e) {
                System.out.println("Input file was not found");
            }
        }

    }
}
