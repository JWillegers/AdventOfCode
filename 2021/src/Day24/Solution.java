package Day24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Solution {
    /*
    Analytical solution using: https://github.com/dphilipson/advent-of-code-2021/blob/master/src/days/day24.rs

    Values {CHECK},{OFFSET}
    11, 1
    11, 11
    14, 1
    11, 11,
    -8, 2
    -5, 9
    11, 7
    -13, 11
    12, 6
    -1, 15
    14, 7
    -5, 1
    -4, 8
    -8, 6

    PUSH/POP
    PUSH w[0] + 1
    PUSH w[1] + 11
    PUSH w[2] + 1
    PUSH w[3] + 11
    POP  w[4] == poppedValue - 8
    POP  w[5] == poppedValue - 5
    PUSH w[6] + 7
    POP  w[7] == poppedValue - 13
    PUSH w[8] + 6
    POP  w[9] == poppedValue - 1
    PUSH w[10] + 7
    POP  w[11] == poppedValue - 5
    POP  w[12] == poppedValue - 4
    POP  w[13] == poppedValue - 8

    Which will become these equations:
        w[4] == w[3] + 3
        w[5] == w[2] - 4
        w[7] == w[6] - 6
        w[9] == w[8] + 5
        w[11] == w[10] + 2
        w[12] == w[1] + 7
        w[13] == w[0] - 7


    Max accepted number (0 is left most number)
    92969593497992

    Min accepted number
    81514171161381
     */
}