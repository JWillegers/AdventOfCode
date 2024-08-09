package y2020.src.Day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartB {
    public static void main(String[] args) {
        int nOfLines = 875;
        BufferedReader reader;
        String[] array = new String[nOfLines];

        try {
            reader = new BufferedReader(new FileReader("y2020/src/Day05/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.exit(1);
        }

        List<Integer> taken_seats = new ArrayList<>();

        for (int i = 0; i < nOfLines; i++) {
            int row = 0;
            int column = 0;
            for (int j = 0; j < 10; j++) {
                if (j < 7 && array[i].charAt(j) == 'B') {
                    row += Math.pow(2, (6 - j));
                } else if (j >= 7 && array[i].charAt(j) == 'R') {
                    column += Math.pow(2, (2 - (j - 7)));
                }
            }
            int seat = row * 8 + column;
            taken_seats.add(seat);
        }

        //974 is highest seat
        for (int i = 1; i < 973; i++) {
            if (!taken_seats.contains(i)) {
                if (taken_seats.contains(i - 1) && taken_seats.contains(i + 1)) {
                    System.out.println("My seat is " + i);
                    break;
                }
            }
        }

    }
}
