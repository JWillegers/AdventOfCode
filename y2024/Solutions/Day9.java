package y2024.Solutions;

import Util_Java.Parse;

import java.io.IOException;
import java.util.*;

public class Day9 extends Solution {
    private String input;


    public Day9() {

    }

    @Override
    public void getInput(int day, Parse parser) throws IOException {
        input = parser.readFile(day).get(0);
    }

    @Override
    public String partOne(){
        int indexFront = 0;
        int indexBack = input.length() % 2 == 0 ? input.length() - 2 : input.length() - 1;
        int backCount = 0;
        long checkSum = 0;
        int indexInternal = 0;

        while (indexFront < indexBack) {
            // add files
            if (indexFront % 2 == 0) {
                int idNumber = indexFront / 2;
                for (int i = 0; i < Integer.parseInt(String.valueOf(input.charAt(indexFront))); i++) {
                    checkSum += (long) idNumber * indexInternal;
                    indexInternal++;
                }
            } else {
                // "free spaces", which are back filled
                for (int i = 0; i < Integer.parseInt(String.valueOf(input.charAt(indexFront))); i++) {
                    int idNumber = indexBack / 2;
                    checkSum += (long) idNumber * indexInternal;
                    indexInternal++;
                    backCount++;
                    if (backCount == Integer.parseInt(String.valueOf(input.charAt(indexBack)))) {
                        backCount = 0;
                        indexBack -= 2;
                        if (indexBack < indexFront) {
                            break;
                        }
                    }
                }
            }
            // last step
            indexFront++;
        }
        // if front and back index are the same, there is a change that a few more ID numbers need to be added
        if (indexFront == indexBack) {
            while (backCount < Integer.parseInt(String.valueOf(input.charAt(indexBack)))) {
                int idNumber = indexBack / 2;
                checkSum += (long) idNumber * indexInternal;
                indexInternal++;
                backCount++;
            }
        }

        return String.valueOf(checkSum);
    }

    @Override
    public String partTwo() {
        int indexFront = 0;
        int indexBack = input.length() % 2 == 0 ? input.length() - 2 : input.length() - 1;
        Set<Integer> movedIdBack = new HashSet<>();
        long checkSum = 0;
        int indexInternal = 0;

        while (indexFront < input.length()) {
            // process potential files
            if (indexFront % 2 == 0) {
                int idNumber = indexFront / 2;
                // files have been moved back at some point, see this as free space
                if (movedIdBack.contains(idNumber)) {
                    indexInternal += Integer.parseInt(String.valueOf(input.charAt(indexFront)));
                } else {
                    // add files. update checksum
                    for (int i = 0; i < Integer.parseInt(String.valueOf(input.charAt(indexFront))); i++) {
                        checkSum += (long) idNumber * indexInternal;
                        indexInternal++;
                    }
                }
            } else {
                int spacesToBeFilled = Integer.parseInt(String.valueOf(input.charAt(indexFront)));
                // try to fill free spaces
                while (indexFront < indexBack && spacesToBeFilled > 0) {
                    // size file <= spacesToBeFilled
                    if (Integer.parseInt(String.valueOf(input.charAt(indexBack))) <= spacesToBeFilled) {
                        int idNumber = indexBack / 2;
                        // check if we didn't already move this file
                        if (!movedIdBack.contains(idNumber)) {
                            for (int i = 0; i < Integer.parseInt(String.valueOf(input.charAt(indexBack))); i++) {
                                // update checkSum
                                checkSum += (long) idNumber * indexInternal;
                                indexInternal++;
                                spacesToBeFilled--;
                                movedIdBack.add(idNumber);
                            }
                        }
                    }
                    indexBack -= 2;
                }
                // reset indexBack
                indexBack = input.length() % 2 == 0 ? input.length() - 2 : input.length() - 1;
                // make sure indexInternal is at the right place in case some spaces weren't filled
                indexInternal += spacesToBeFilled;
            }

            // last step
            indexFront++;
        }
        // 13435774190323
        // 12988874760259 too high
        return String.valueOf(checkSum);
    }

    @Override
    public int getDay() {
        return 9;
    }

    public static void main(String[] args) throws IOException {
        Day9 d = new Day9();
        Parse p = new Parse();
        d.getInput(d.getDay(), p);
        System.out.println("part1: " + d.partOne());
        System.out.println("part2: " + d.partTwo());
    }
}
