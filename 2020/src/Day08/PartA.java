package Day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartA {

    public static void main(String[] args) {
        new PartA().solution();
    }

    public void solution() {

        int nOfLines = 621;
        BufferedReader reader;
        String[] array = new String[nOfLines];

        try {
            reader = new BufferedReader(new FileReader("2020/src/Day08/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int accumulator = 0;
        int counter = 0;
        List<Integer> usedCounter = new ArrayList<>();

        while (counter < nOfLines) {
            if (!usedCounter.isEmpty() && usedCounter.contains(counter)) {
                System.out.println("double instruction line: " + counter + "\naccumulator before double instruction " + accumulator);
                System.exit(0);
            }

            String[] split = array[counter].split(" ");
            String instruction = split[0];
            int value = 0;
            try {
                value = Integer.parseInt(split[1]);
            } catch (NumberFormatException e) {
                System.exit(1);
            }

            usedCounter.add(counter);
            if (instruction.equals("nop")) {
                counter++;
            } else if (instruction.equals("acc")) {
                accumulator += value;
                counter++;
            } else if (instruction.equals("jmp")) {
                counter += value;
            }
        }

        System.out.println(accumulator);
    }
}
