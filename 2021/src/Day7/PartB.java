package Day7;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartB {
    List<Integer> positions = new ArrayList<>();

    public static void main(String[] args) {
        PartB part = new PartB();
        part.setup();
        part.solution();
    }

    public void setup() {
        String input = "" // PUT YOUR INPUT HERE
        String[] split = input.split(",");
        for (int i = 0; i < split.length; i++) {
            positions.add(Integer.parseInt(split[i]));
        }
        Collections.sort(positions);
    }

    public void solution() {
        long leastFuel = -1;

        for (int goal = 0; goal < positions.get(positions.size() - 1); goal++) {
            long currentfuel = 0;
            for (int pos : positions) {
                int move = Math.abs(pos - goal);
                for (int i = 0; i <= move; i++) {
                    currentfuel += i;
                }
            }
            if ((currentfuel < leastFuel || leastFuel < 0) && currentfuel >=0) {
                leastFuel = currentfuel;
            }
        }
        System.out.println(leastFuel);
    }
}
