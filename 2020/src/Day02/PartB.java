package Day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartB {
    public static void main(String[] args) {
        try {
            int nOfLines = 1000;
            BufferedReader reader = new BufferedReader(new FileReader("2020/src/Day02/input.txt"));
            int[] min = new int[nOfLines];
            int[] max = new int[nOfLines];
            String[] password = new String[nOfLines];
            char[] character = new char[nOfLines];
            for (int i = 0; i < nOfLines; i++) {
                String line = reader.readLine();
                String[] split = line.split(" ");
                String[] minmax = split[0].split("-");
                min[i] = Integer.parseInt(minmax[0]);
                max[i] = Integer.parseInt(minmax[1]);
                character[i] = split[1].charAt(0);
                password[i] = split[2];
            }
            reader.close();

            int counter = 0;
            for (int i = 0; i < nOfLines; i++) {
                boolean statement1 = character[i] == password[i].charAt(min[i] - 1);
                boolean statement2 = (password[i].charAt(max[i] - 1) == character[i]);

                if ((statement1 && !statement2) || (!statement1 && statement2)) {
                    counter++;
                }

            }

            System.out.println(counter);

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}

