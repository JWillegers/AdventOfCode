package Day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.AssertJUnit.assertEquals;

public class PartB {
    private String busLines;
    private BufferedReader reader;

    public static void main(String[] args) {
        PartB part = new PartB();
        part.test();
        part.setup();
        part.solution();
    }

    private void test() {
        busLines = "7,13,x,x,59,x,31,19";
        assertEquals(1068781, solution());
        busLines = "17,x,13,19";
        assertEquals(3417, solution());
        busLines = "67,7,59,61";
        assertEquals(754018, solution());
        busLines = "67,x,7,59,61";
        assertEquals(779210, solution());
        busLines = "67,7,x,59,61";
        assertEquals(1261476, solution());
        busLines = "1789,37,47,1889";
        assertEquals(1202161486, solution());

    }

    public void setup() {
        try {
            reader = new BufferedReader(new FileReader("2020/src/Day13/input.txt"));
            reader.readLine();
            busLines = reader.readLine();
            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public long solution() {
        String[] split = busLines.split(",");
        List<Integer> busses = new ArrayList<>();
        // parse input
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("x")) {
                try {
                    int interval = Integer.parseInt(split[i]);
                    busses.add(i, interval);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            } else {
                busses.add(i, 0);
            }
        }

        System.out.println(busses);
        long a2 = 0;
        long n2 = busses.get(0);
        //https://en.wikipedia.org/wiki/Chinese_remainder_theorem
        for (int i = 1; i < busses.size(); i++) {
            if (busses.get(i) > 0) {
                long n1 = busses.get(i);
                long a1 = i;

                List<Long> st = gcd(n1, n2);
                long m1 = st.get(0);
                long m2 = st.get(1);

                if (m1 * n1 + m2 * n2 != 1) {
                    System.out.println("ERROR: Bezout's identity validated");
                    System.exit(1);
                }

                long new_n2 = n1 * n2;
                long new_a2 = (a1 * m2) % new_n2;
                new_a2 = (new_a2 * n2) % new_n2;
                long part2 = (a2 * m1) % new_n2;
                part2 = (part2 * n1) % new_n2;
                a2 = (new_a2 + part2) % new_n2;
                n2 = new_n2;
                //a2 = (a1 * m2 * n2 + a2 * m1 * n1) % (n1 * n2);
                //n2 = n1 * n2;
                a2 = (a2 + n2) % n2;
            }
        }

        System.out.println("solution:");
        System.out.println(n2 - a2);
        return n2 - a2;
    }

    /**
     * Extended euclidean algorithm
     * https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
     */
    List<Long> gcd(long a, long b) {
        long q = 0;
        long old_r = a;
        long r = b;
        long old_s = 1;
        long s = 0;
        long old_t = 0;
        long t = 1;
        long temp = 0;

        while (r != 0) {
            q = old_r / r;

            temp = r;
            r = old_r - q * temp;
            old_r = temp;

            temp = s;
            s = old_s - q * temp;
            old_s = temp;

            temp = t;
            t = old_t - q * temp;
            old_t = temp;
        }

        List<Long> st = new ArrayList<>();
        st.add(old_s);
        st.add(old_t);
        return st;
    }

}

