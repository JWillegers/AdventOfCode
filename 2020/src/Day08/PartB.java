package Day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartB {

    public static void main(String[] args) {
        new PartB().solution();
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

        boolean run = true;
        int counter = 0;
        int accumulator = 0;
        while (run && counter < nOfLines) {
            boolean innerrun = true;
            String[] line = array[counter].split(" ");
            if (line[0].equals("nop")) {
                array[counter] = "jmp " + line[1];
            } else if (line[0].equals("jmp")) {
                array[counter] = "nop " + line[1];
            } else {
                innerrun = false;
            }

            accumulator = 0;
            int innercounter = 0;

            List<Integer> usedCounter = new ArrayList<>();

            while (innerrun && innercounter < nOfLines) {
                if (!usedCounter.isEmpty() && usedCounter.contains(innercounter)) {
                    innerrun = false;
                }

                String[] split = array[innercounter].split(" ");
                String instruction = split[0];
                int value = 0;
                try {
                    value = Integer.parseInt(split[1]);
                } catch (NumberFormatException e) {
                    System.exit(1);
                }

                usedCounter.add(innercounter);
                if (instruction.equals("nop")) {
                    innercounter++;
                } else if (instruction.equals("acc")) {
                    accumulator += value;
                    innercounter++;
                } else if (instruction.equals("jmp")) {
                    innercounter += value;
                }
            }
            //swap back
            String[] lineX = array[counter].split(" ");
            if (lineX[0].equals("nop")) {
                array[counter] = "jmp " + lineX[1];
            } else if (lineX[0].equals("jmp")) {
                array[counter] = "nop " + lineX[1];
            }

            run = innercounter < nOfLines;
            counter++;
        }

        System.out.println(accumulator);
    }
}
