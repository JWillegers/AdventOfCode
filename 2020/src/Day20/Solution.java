package Day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private final int tileSize = 10;
    private final List<Tile> allTiles = new ArrayList<>();
    public static void main(String[] args) {
        Day20.Solution sol = new Day20.Solution();
        sol.readFile();
        sol.createImage();
    }

    private void readFile() {
        BufferedReader reader = null;

        // read input
        try {
            reader = new BufferedReader(new FileReader("2020/src/Day20/test.txt"));
            String line = reader.readLine();
            boolean[][] tile = null;
            int id = 0;
            int lineCounter = 0;

            while (line != null) {
                if (line.contains("Tile")) {
                    //create new tile
                    tile = new boolean[tileSize][tileSize];
                    String[] split = line.split(" ");
                    id = Integer.parseInt(split[1].replace(":", ""));
                    lineCounter = 0;
                } else if (line.contains(".") || line.contains("#")) {
                    for(int i = 0; i < tileSize; i++) {
                        tile[lineCounter][i] = line.charAt(i) == '#';
                    }
                    lineCounter++;
                } else {
                    allTiles.add(new Tile(id, tile));
                }
                // read next line
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void createImage() {
        Tile startTile = allTiles.get(0);
    }
}
