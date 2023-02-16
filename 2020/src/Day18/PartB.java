package Day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartB {
    private int nOfLines = 376; //376
    private BufferedReader reader;
    private String[] array = new String[nOfLines];

    public static void main(String[] args) {
        PartB part = new PartB();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            reader = new BufferedReader(new FileReader("2020/src/Day18/input.txt"));
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
        String[] split = line.split(" ");
        boolean calculationDone = false;
        boolean skip = false;
        String returnLine = "";
        for (int i = 0; i < split.length; i += 2) {
            if (skip) {
                if (split.length != 3 && i + 1 != split.length) {
                    returnLine += " " + split[i + 1] + " ";
                }
                skip = false;
            } else if (i + 1 == split.length) {
                returnLine += split[i];
            } else if (calculationDone) {
                returnLine += split[i] + " " + split[i + 1] + " ";
            } else if (split[i + 1].equals("+")) {
                returnLine += (Long.parseLong(split[i]) + Long.parseLong(split[i + 2]));
                skip = true;
                calculationDone = true;
            } else if (!line.contains("+")) {
                returnLine += (Long.parseLong(split[i]) * Long.parseLong(split[i + 2]));
                skip = true;
                calculationDone = true;
            } else {
                returnLine += split[i] + " " + split[i + 1] + " ";
            }
        }
        return returnLine;
    }
}