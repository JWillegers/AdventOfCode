package y2020.src.Day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartA {
    private int nOfLines = 376; //376
    private BufferedReader reader;
    private String[] array = new String[nOfLines];

    public static void main(String[] args) {
        PartA part = new PartA();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            reader = new BufferedReader(new FileReader("y2020/src/Day18/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution() {
        long sum = 0;
        for (int i = 0; i < nOfLines; i++) {
            String line = array[i];
            boolean run = true;
            while (run) {
                /*
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.exit(1);
                }
                System.out.println(line);
                */
                if (line.contains("(")) {
                    String[] split = line.split("[)]");
                    String subline = split[0].substring(split[0].lastIndexOf('(') + 1);
                    int lastIndex = split[0].lastIndexOf('(');
                    if (lastIndex != 0) {
                        line = split[0].substring(0, lastIndex);
                    } else {
                        line = "";
                    }
                    while (subline.contains("*") || subline.contains("+")) {
                        subline = calculate(subline);
                    }
                    if (line.equals("")) {
                        line = subline;
                    } else {
                        line = line + subline;
                    }
                    for (int k = 1; k < split.length; k++) {
                        line = line + split[k];
                        if (k + 1 != split.length) {
                            line = line + ")";
                        }
                    }

                } else if (line.contains("*") || line.contains("+")) {
                    line = calculate(line);
                } else {
                    System.out.println(line);
                    sum += Long.parseLong(line);
                    run = false;
                }
            }

        }

        System.out.println("sum: " + sum);
    }

    public String calculate(String line) {
        boolean twoNumbers = false;
        String[] split = line.split(" ");
        long number = -1;
        if (split.length == 3) {
            twoNumbers = true;
        }
        if (split[1].equals("+")) {
            number = Long.parseLong(split[0]) + Long.parseLong(split[2]);
        } else {
            number = Long.parseLong(split[0]) * Long.parseLong(split[2]);
        }
        int spaceCounter = 0;
        if (number != -1) {
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == ' ') {
                    spaceCounter++;
                    if (spaceCounter == 3) {
                        return String.valueOf(number) + line.substring(j);
                    } else if (twoNumbers) {
                        return String.valueOf(number);
                    }
                }
            }
        } else {
            System.out.println("HELP2");
            System.exit(1);
        }
        return null;
    }
}