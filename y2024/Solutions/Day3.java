package y2024.Solutions;


import Util_Java.Parse;

import java.io.IOException;
import java.util.List;

public class Day3 extends Solution {
    private String input = "";
    public Day3() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        for (String line : parser.readFile(day)) {
            input += line;
        }
    }

    /**
     * Check if potential Instruction is of format (X,Y) where X and Y are numbers
     * @param potentialInstruction String that comes right after a mul
     * @return 0 or X * Y
     */
    private long checkValidMul(String potentialInstruction) {
        if (potentialInstruction.length() < 5) {
            return 0;
        }
        // check first char after mul
        if (potentialInstruction.charAt(0) != '(') {
            return 0;
        }
        int i = 1;
        boolean firstNumberCheck = false;
        boolean comma = false;
        boolean secondNumberCheck = false;
        long firstNumber = 0;
        long secondNumber = 0;
        // check subsequent chars
        while (i < potentialInstruction.length()) {
            char c = potentialInstruction.charAt(i);
            if (firstNumberCheck && comma && secondNumberCheck && c == ')') {
                return firstNumber * secondNumber;
            } else if (firstNumberCheck && comma && Character.isDigit(c)) {
                secondNumberCheck = true;
                secondNumber = secondNumber * 10 + Long.parseLong(String.valueOf(c));
            } else if (firstNumberCheck && !comma && c == ',') {
                comma = true;
            } else if (!comma && Character.isDigit(c)) {
                firstNumberCheck = true;
                firstNumber = firstNumber * 10 + Long.parseLong(String.valueOf(c));
            } else {
                break;
            }

            i++;
        }
        return 0;
    }

    @Override
    public String partOne(){
        long result = 0;
        // find all mul
        String[] split = input.split("mul");
        for (String potentialInstruction : split) {
            result += checkValidMul(potentialInstruction);
        }

        return String.valueOf(result);
    }

    @Override
    public String partTwo() {
        long result = 0;
        // split by do() to find all the places where we should look for mul
        String[] do_split = input.split("do\\(\\)");
        for (String do_substring : do_split) {
            // split by don't() to find all the places where we should look for mul
            String[] dont_split = do_substring.split("don't\\(\\)");
            // find all mul before the first don't()
            String[] mul_split = dont_split[0].split("mul");
            for (String potentialInstruction : mul_split) {
                result += checkValidMul(potentialInstruction);
            }
        }

        return String.valueOf(result);
    }

    @Override
    public int getDay() {
        return 3;
    }

    public static void main(String[] args) throws IOException {
        Day3 d = new Day3();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
