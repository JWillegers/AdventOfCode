package Day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartA {
    private int nOfLines = 2;
    private long timestamp;
    private String otherline;
    private BufferedReader reader;

    public static void main(String[] args) {
        PartA part = new PartA();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            reader = new BufferedReader(new FileReader("2020/src/Day13/input.txt"));
            timestamp = Long.parseLong(reader.readLine());
            otherline = reader.readLine();
            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution() {
        String[] split = otherline.split(",");
        List<Integer> buslines = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("x")) {
                try {
                    buslines.add(Integer.parseInt(split[i]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
        boolean run = true;
        long nextBusTime = timestamp;
        int nextBusLine = 0;
        while (run) {
            nextBusTime++;
            for (int i = 0; i < buslines.size(); i++) {
                if (nextBusTime % buslines.get(i) == 0) {
                    nextBusLine = buslines.get(i);
                    run = false;
                }
            }
        }


        System.out.println(nextBusLine * (nextBusTime - timestamp));
    }
}