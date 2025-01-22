package y2024.Solutions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Util_Java.*;

public class Day13 extends Solution {
    /**
     * [i j k l x y]
     * where
     * Button A: X+i, Y+j
     * Button B: X+k, Y+l
     * Prize: X=x, Y=y
     */
    List<List<Long>> input;

    public Day13() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        List<String> i = parser.readFile(day);
        List<List<String>> blocks = parser.toBlocks(i);
        input = new ArrayList<>();
        for (List<String> b : blocks) {
            String[] buttonA = b.get(0).split("[,+]");
            String[] buttonB = b.get(1).split("[,+]");
            String[] prize = b.get(2).split("[,=]");

            List<Long> machine = new ArrayList<>();
            machine.add(Long.parseLong(buttonA[1]));
            machine.add(Long.parseLong(buttonA[3]));
            machine.add(Long.parseLong(buttonB[1]));
            machine.add(Long.parseLong(buttonB[3]));
            machine.add(Long.parseLong(prize[1]));
            machine.add(Long.parseLong(prize[3]));
            input.add(machine);
        }
    }


    @Override
    public String partOne(){
        /*
         * [i j k l x y]
         * [0 1 2 3 4 5]
         * where
         * Button A: X+i, Y+j
         * Button B: X+k, Y+l
         * Prize: X=x, Y=y
         *
         *  x = ai+bk
         *  y = aj+bl
         *
         *  a = (x-bk)/i
         *
         *  y = (j*(x-bk)/i)+ibl/i
         *  iy = jx-jbk+ibl
         *  iy-jx = (il-kj)b
         *  b = (iy-jx)/(il-kj)
         */
        long res = 0;
        for (List<Long> machine : input) {
            // first, calculate b
            long nominator = machine.get(0) * machine.get(5) - machine.get(1) * machine.get(4);
            long denominator = machine.get(0) * machine.get(3) - machine.get(2) * machine.get(1);
            if (nominator % denominator != 0) continue; // check if result is a long
            long b = nominator / denominator;
            // next calculate a
            if ((machine.get(4)-b*machine.get(2)) % machine.get(0) != 0) continue; // check if result is an= long
            long a = (machine.get(4)-b*machine.get(2))/machine.get(0);
            res += (3 * a + b);
        }
        return String.valueOf(res);
    }

    @Override
    public String partTwo() {
        long res = 0;
        for (List<Long> machine : input) {
            // update x y
            long x = Long.parseLong("10000000000000") + machine.get(4);
            long y = Long.parseLong("10000000000000") + machine.get(5);
            // first, calculate b
            long nominator = machine.get(0) * y - machine.get(1) * x;
            long denominator = machine.get(0) * machine.get(3) - machine.get(2) * machine.get(1);
            if (nominator % denominator != 0) continue; // check if result is a long
            long b = nominator / denominator;
            // next calculate a
            if ((x-b*machine.get(2)) % machine.get(0) != 0) continue; // check if result is an= long
            long a = (x-b*machine.get(2))/machine.get(0);
            res += (3 * a + b);
        }
        return String.valueOf(res);
    }

    @Override
    public int getDay() {
        return 13;
    }

    public static void main(String[] args) throws IOException {
        Day13 d = new Day13();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
