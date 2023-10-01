package Day24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {
    public static final char DELIMITER = ';';

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
     * Solution to part 1 using the cube coordinates from the reference
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
            String tile = String.valueOf(q) + DELIMITER + s + DELIMITER + r;
            if (blackTiles.contains(tile)) {
                blackTiles.remove(tile);
            } else {
                blackTiles.add(tile);
            }
        }
        System.out.println("Part1: " + blackTiles.size());
        partTwo(blackTiles);
    }

    private void partTwo(List<String> blackTiles) {
        for (int day = 1; day <= 100; day++) {
            Set<String> flipToWhite = new HashSet<>();
            Set<String> flipToBlack = new HashSet<>();

            for (String tile : blackTiles) {
                String[] split = tile.split(";");
                //q;s;r
                int q = Integer.parseInt(split[0]);
                int s = Integer.parseInt(split[1]);
                int r = Integer.parseInt(split[2]);

                //check rule: Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
                String blackNeighbors = checkIfNeighborsAreBlack(q, r, s, blackTiles);
                long nBlackNeighbors = blackNeighbors.chars().filter(ch -> ch == '1').count();
                if (nBlackNeighbors == 0 || nBlackNeighbors > 2) {
                    flipToWhite.add(tile);
                }

                //check rule: Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.
                flipToBlack.addAll(checkWhiteToBlack(q, r, s, blackNeighbors, blackTiles));

            }

            for (String tile : flipToWhite) {
                blackTiles.remove(tile);
            }

            blackTiles.addAll(flipToBlack);
        }
        System.out.println("Part2: " + blackTiles.size());
    }

    /**
     * Check how many neighbors of (q,r,s) are black
     * @param q
     * @param r
     * @param s
     * @param blackTiles all current black Tiles
     * @return if neighboring tiles are black through binary, in the following pattern: E,SE,SW,W,NW,NE
     */
    private String checkIfNeighborsAreBlack(int q, int r, int s, List<String> blackTiles) {
        String binary = blackTiles.contains(getE(q, r, s)) ? "1" : "0";
        binary += blackTiles.contains(getSe(q, r, s)) ? "1" : "0";
        binary += blackTiles.contains(getSw(q, r, s)) ? "1" : "0";
        binary += blackTiles.contains(getW(q, r, s)) ? "1" : "0";
        binary += blackTiles.contains(getNw(q, r, s)) ? "1" : "0";
        binary += blackTiles.contains(getNe(q, r, s)) ? "1" : "0";

        return binary;
    }

    /**
     * @param q
     * @param r
     * @param s
     * @param blackNeighbors binary following pattern: E,SE,SW,W,NW,NE
     * @param blackTiles all currently black tiles following: q;s;r
     * @return
     */
    private List<String> checkWhiteToBlack(int q, int r, int s, String blackNeighbors, List<String> blackTiles) {
        int e = 0;
        int se = 0;
        int sw = 0;
        int w = 0;
        int nw = 0;
        int ne = 0;
        //look at the neighbors of q;r;s and the neighbors of the neighbors
        for (int a = -2; a <= 2; a++) {
            for (int b = -2; b <= 2; b++) {
                for (int c = -2; c <= 2; c++) {
                    //all valid tiles have q + r + s == 0, thus a + b + c == 0
                    if (a + b + c != 0) {
                        continue;
                    }

                    /*
                    corresponding numbers
                    q -> a
                    s -> b
                    r -> c
                     */

                    //check if tile is black
                    String tile = String.valueOf(q + a) + DELIMITER + (s + b) + DELIMITER + (r + c);
                    if (!blackTiles.contains(tile)) {
                        continue;
                    }

                    /*
                    A pattern has been established
                    corresponding number: x -> y
                    for cord x == 1 -> y >= 0
                    for cord x == 0 -> Math.abs(y) <= 1
                    for cord x == -1 -> y <= 0
                     */

                    //e -> 1;-1;0
                    if (a >= 0 && b <= 0 && Math.abs(c) <= 1) {
                        e += 1;
                    }

                    //se -> 0;-1;1
                    if (Math.abs(a) <= 1 && b <= 0 && c >= 0) {
                        se += 1;
                    }

                    //sw -> -1;0;1
                    if (a <= 0 && Math.abs(b) <= 1 && c >= 0) {
                        sw += 1;
                    }

                    //w -> -1;1;0
                    if (a <= 0 && b >= 0 && Math.abs(c) <= 1) {
                        w += 1;
                    }

                    //nw -> 0;1;-1
                    if (Math.abs(a) <= 1 && b >= 0 && c <= 0) {
                        nw += 1;
                    }

                    //ne -> 1;0;-1
                    if (a >= 0 && Math.abs(b) <= 1 && c <= 0) {
                        ne += 1;
                    }
                }
            }
        }

        //for the neighboring white tiles, check if they have exactly 2 black neighbors
        List<String> transformableTiles = new ArrayList<>();
        if (blackNeighbors.charAt(0) == '0' && e == 2) {
            transformableTiles.add(getE(q, r, s));
        }
        if (blackNeighbors.charAt(1) == '0' && se == 2) {
            transformableTiles.add(getSe(q, r, s));
        }
        if (blackNeighbors.charAt(2) == '0' && sw == 2) {
            transformableTiles.add(getSw(q, r, s));
        }
        if (blackNeighbors.charAt(3) == '0' && w == 2) {
            transformableTiles.add(getW(q, r, s));
        }
        if (blackNeighbors.charAt(4) == '0' && nw == 2) {
            transformableTiles.add(getNw(q, r, s));
        }
        if (blackNeighbors.charAt(5) == '0' && ne == 2) {
            transformableTiles.add(getNe(q, r, s));
        }

        return transformableTiles;
    }

    private String getE(int q, int r, int s) {
        return String.valueOf(q + 1) + DELIMITER + (s - 1) + DELIMITER + r;
    }

    private String getSe(int q, int r, int s) {
        return String.valueOf(q) + DELIMITER + (s - 1) + DELIMITER + (r + 1);
    }
    private String getSw(int q, int r, int s) {
        return String.valueOf(q - 1) + DELIMITER + s + DELIMITER + (r + 1);
    }
    private String getW(int q, int r, int s) {
        return String.valueOf(q - 1) + DELIMITER + (s + 1) + DELIMITER + r;
    }

    private String getNw(int q, int r, int s) {
       return String.valueOf(q) + DELIMITER + (s + 1) + DELIMITER + (r - 1);
    }
    private String getNe(int q, int r, int s) {
        return String.valueOf(q + 1) + DELIMITER + s + DELIMITER + (r - 1);
    }
}
