package y2021.src.Day7;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartA {
    List<Integer> positions = new ArrayList<>();

    public static void main(String[] args) {
        PartA part = new PartA();
        part.setup();
        part.solution();
    }

    public void setup() {
        String input = ""; //PUT YOUR INPUT HERE
        String[] split = input.split(",");
        for (int i = 0; i < split.length; i++) {
            positions.add(Integer.parseInt(split[i]));
        }
        Collections.sort(positions);
    }

    public void solution() {
        int start = 1000000;
        int leastFuel = start;

        for (int goal = 0; goal < positions.get(positions.size() - 1); goal++) {
            int currentfuel = 0;
            for (int pos : positions) {
                currentfuel += Math.abs(pos - goal);
            }
            if (currentfuel < leastFuel) {
                leastFuel = currentfuel;
            }
        }
        System.out.println(leastFuel);
    }
}
