package y2020.src.Day15;

import java.util.HashMap;
import java.util.Map;

public class Solution {

    public static void main(String[] args) {
        Solution part = new Solution();
        part.solution();
    }

    public void solution() {
        int lastNumber = 9;
        Map<Integer, Integer> lastEntry = new HashMap<>(); //number, last turn spoken
        lastEntry.put(8, 1);
        lastEntry.put(13, 2);
        lastEntry.put(1, 3);
        lastEntry.put(0, 4);
        lastEntry.put(18, 5);
        for (int i = 7; i <= 30000000; i++) {
            if (lastEntry.containsKey(lastNumber)) {
                int lastTime = lastEntry.get(lastNumber);
                lastEntry.put(lastNumber, i - 1);
                lastNumber = i - 1 - lastTime;
            } else {
                lastEntry.put(lastNumber, i - 1);
                lastNumber = 0;
            }

            if (i == 2020) {
                System.out.println("2020: " + lastNumber + "\nbe patient, may take a few seconds to calculate");
            }
        }

        System.out.println("30000000: " + lastNumber);
    }
}