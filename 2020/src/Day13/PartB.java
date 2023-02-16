package Day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartB {
    private int nOfLines = 2;
    private long timestamp;
    private String otherline;
    private BufferedReader reader;

    public static void main(String[] args) {
        PartB part = new PartB();
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
        Map<Integer, Integer> busses = new HashMap<>();
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("x")) {
                try {
                    busses.put(i, Integer.parseInt(split[i]));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
        boolean run = true;
        long nextBusTime = timestamp;
        while (run) {
            run = false;
            nextBusTime++;
            if (nextBusTime % 100000000 == 0) {
                System.out.println(nextBusTime);
            }

            for (int slot : busses.keySet()) {
                if ((nextBusTime + slot) % busses.get(slot) != 0) {
                    run = true;
                    break;
                }
            }
        }


        System.out.println(nextBusTime);
    }
}