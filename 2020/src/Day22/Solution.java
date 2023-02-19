package Day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();
        sol.run();
    }

    private void run() {
        BufferedReader reader;
        List<Integer> player1 = new ArrayList<>();
        List<Integer> player2 = new ArrayList<>();
        boolean first_half = true;

        // read input
        try {
            reader = new BufferedReader(new FileReader("2020/src/Day22/input.txt"));
            String line = reader.readLine();
            while (line != null) {
                if (!line.contains("Player")) {
                    if (line.equals("")) {
                        first_half = false;
                    } else if (first_half) {
                        player1.add(Integer.parseInt(line));
                    } else {
                        player2.add(Integer.parseInt(line));
                    }

                }

                // read next line
                line = reader.readLine();
            }
            reader.close();
            play_rounds(player1, player2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void play_rounds(List<Integer> player1, List<Integer> player2) {
        while (!player1.isEmpty() && !player2.isEmpty()) {
            int card1 = player1.remove(0);
            int card2 = player2.remove(0);
            if (card1 > card2) {
                player1.add(card1);
                player1.add(card2);
            } else {
                player2.add(card2);
                player2.add(card1);
            }
        }
        long winner_score = Math.max(calculate_score(player1), calculate_score(player2));
        System.out.println("Score winner part 1: " + winner_score);
    }

    private long calculate_score(List<Integer> list) {
        long score = 0;
        for (int i = 0; i < list.size(); i++) {
            score += (long) (list.size() - i) * list.get(i);
        }
        return score;
    }
}
