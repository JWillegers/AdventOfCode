package Day24;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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

        //read input
        try {
            //reader = new BufferedReader(new FileReader("2020/src/Day24/test.txt"));
            reader = new BufferedReader(new FileReader("2020/src/Day24/input.txt"));
            List<String> instructions = new ArrayList<>();

            String line = reader.readLine();
            while (line != null) {
                instructions.add(line);
                line = reader.readLine();
            }

            reader.close();
            partOne(instructions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Solution to part 1 using the axial coordinates
     * reference: https://www.redblobgames.com/grids/hexagons/#coordinates
     * @param instructions lines of instructions without delimiter
     */
    private void partOne(List<String> instructions) {
        List<String> blackTiles = new ArrayList<>();
        for (String line : instructions) {
            int counter = 0; //keeps track at which character we are
            int q = 0;
            int s = 0;
            int r = 0;
            while (counter < line.length()) {
                if (line.charAt(counter) == 'e') {
                    s += -1;
                    q += 1;
                    counter += 1;
                } else if (line.charAt(counter) == 'w') {
                    s += 1;
                    q += -1;
                    counter += 1;
                } else if (line.charAt(counter) == 'n') {
                    r += -1;
                    if (line.charAt(counter + 1) == 'w') {
                        s += 1;
                    } else { //ne
                        q += 1;
                    }
                    counter += 2;
                } else {
                    //se or sw
                    r += 1;
                    if (line.charAt(counter + 1) == 'w') {
                        q += -1;
                    } else { //sw
                        s += -1;
                    }
                    counter += 2;
                }
            }

            //flip the tile
            String tile = q + ";" + s + ";" + r;
            boolean alreadyBlack = false;
            for (String flippedTile : blackTiles) {
                if (flippedTile.equals(tile)) {
                    alreadyBlack = true;
                    break;
                }
            }
            if (alreadyBlack) {
                blackTiles.remove(tile);
            } else {
                blackTiles.add(tile);
            }
        }
        System.out.println("Part1: " + blackTiles.size());
    }
}
