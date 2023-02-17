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
        int biggest_interval = 0;
        int biggest_inteval_key_offset = 0;
        // parse input
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("x")) {
                try {
                    int interval = Integer.parseInt(split[i]);
                    busses.put(i, interval);
                    if (interval > biggest_interval) {
                        biggest_interval = interval;
                        biggest_inteval_key_offset = i;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }

        // because of the text, we can set the timestamp to later
        timestamp = (long) 1e14;
        while (timestamp % biggest_interval != 1) {
            timestamp++;
        }

        boolean solutionFound = false;
        while (!solutionFound) {
            solutionFound = true;
            for (Map.Entry<Integer, Integer> bus : busses.entrySet()) {
                if ((timestamp + bus.getKey() - biggest_inteval_key_offset) % bus.getValue() != 1) {
                    solutionFound = false;
                }
            }
            if (!solutionFound) {
                timestamp += biggest_interval;
            }
        }

        // timestamp first bus leaving - 1 because off by one - offset
        System.out.println(timestamp - 1 - biggest_inteval_key_offset);
    }
}