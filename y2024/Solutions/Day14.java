package y2024.Solutions;


import Util_Java.Parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 extends Solution {
    private List<List<Integer>> input;
    private final int wide = 101;
    private final int tall = 103;

    public Day14() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        List<String> in = parser.readFile(day);
        input = new ArrayList<>();
        for (String line : in) {
            List<String> s = new ArrayList<>(Arrays.asList(line.split("[=, ]")));
            s.remove("p");
            s.remove("v");
            input.add(parser.toInt(s));
        }

    }

    @Override
    public String partOne() {
        int quadrantNE = 0;
        int quadrantNW = 0;
        int quadrantSW = 0;
        int quadrantSE = 0;

        for (List<Integer> instruction : input) {
            int posX = instruction.get(0);
            int posY = instruction.get(1);
            int velX = instruction.get(2);
            int velY = instruction.get(3);

            // calculate new position without teleport
            posX += 100 * velX;
            posY += 100 * velY;

            // account for teleport
            posX = posX % wide;
            posY = posY % tall;
            if (posX < 0) {
                posX += wide;
            }
            if (posY < 0) {
                posY += tall;
            }

            // determine which quadrant the robot is in
            if      (posX < (wide - 1) / 2 && posY < (tall - 1) / 2) quadrantNE++;
            else if (posX < (wide - 1) / 2 && posY > (tall - 1) / 2) quadrantNW++;
            else if (posX > (wide - 1) / 2 && posY < (tall - 1) / 2) quadrantSE++;
            else if (posX > (wide - 1) / 2 && posY > (tall - 1) / 2) quadrantSW++;
        }

        return String.valueOf(quadrantNE * quadrantNW * quadrantSE * quadrantSW);
    }

    @Override
    public String partTwo() {
        return "Made in Python";
    }

    @Override
    public int getDay() {
        return 14;
    }

    public static void main(String[] args) throws IOException {
        Day14 d = new Day14();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
