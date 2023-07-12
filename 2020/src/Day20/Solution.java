package Day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private int tileSize = 10; //Program is only tested on tileSize = 10, changing number can give in unexpected results or errors
    private final List<Tile> allTiles = new ArrayList<>();
    private final List<Tile> unusedTiles = new ArrayList<>();
    private String[][] image;
    public static void main(String[] args) {
        Day20.Solution sol = new Day20.Solution();
        sol.readFile();
        sol.createImage();
        sol.solutionPartOne();
        sol.concatTiles();
    }


    /**
     * Read the file for this day
     */
    private void readFile() {
        BufferedReader reader = null;

        // read input
        try {
            reader = new BufferedReader(new FileReader("2020/src/Day20/test.txt"));
            String line = reader.readLine();
            String[][] tile = null;
            int id = 0;
            int lineCounter = 0;

            while (line != null) {
                if (line.contains("Tile")) {
                    //create new tile
                    tile = new String[tileSize][tileSize];
                    String[] split = line.split(" ");
                    id = Integer.parseInt(split[1].replace(":", ""));
                    lineCounter = 0;
                } else if (line.contains(".") || line.contains("#")) {
                    for(int i = 0; i < tileSize; i++) {
                        tile[lineCounter][i] = String.valueOf(line.charAt(i));
                    }
                    lineCounter++;
                } else {
                    Tile t = new Tile(id, tile, this);
                    allTiles.add(t);
                    unusedTiles.add(t);
                }

                // read next line
                line = reader.readLine();
            }
            //adding last tile
            Tile t = new Tile(id, tile, this);
            allTiles.add(t);
            unusedTiles.add(t);

            reader.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the image with the given tiles
     */
    private void createImage() {
        Tile startTile = unusedTiles.get(0);
        startTile.locked = true;
        List<Tile> tileUsedButNotChecked = new ArrayList<>();
        tileUsedButNotChecked.add(startTile);
        while (!tileUsedButNotChecked.isEmpty()) {
            Tile currentTile = tileUsedButNotChecked.remove(0);
            for (int sideID = 0; sideID < 4; sideID++) {
                if (currentTile.side[sideID] == null) {
                    List<Tile> tiles = findMatch(currentTile, sideID);
                    for (Tile t : tiles) {
                        unusedTiles.remove(t);
                        tileUsedButNotChecked.add(t);
                    }
                }
            }
        }
        for (Tile currentTile : allTiles) {
            checkSides(currentTile);
        }
    }

    /**
     * Loop over all unused tiles, rotate them and flip them to check if there are any matches
     * @param currentTile current Tile
     * @param sideID side
     * @return a list of newly placed tiles
     */
    private List<Tile> findMatch(Tile currentTile, int sideID) {
        List<Tile> matches = new ArrayList<>();
        for (Tile otherTile : unusedTiles) {
            boolean match = false;

            for (int s = 0; s < 2; s++) {
                if (match) {
                    break;
                }
                otherTile.flip();
                for (int r = 0; r < 4; r++) {
                    otherTile.rotate();
                    if (checkMatch(currentTile, otherTile, sideID)) {
                        matches.add(otherTile);
                        match = true;
                        break;
                    }
                }
            }
        }

        List<Tile> returnList = new ArrayList<>();

        if (matches.size() == 1) {
            Tile tile = matches.get(0);
            tile.locked = true;
            clickSides(currentTile, tile, sideID);
            returnList.add(tile);
        } else if (matches.size() > 1) {
            System.out.println("More than 1 matches");
            System.exit(1);
        }
        return returnList;
    }

    public void checkSides(Tile currentTile) {
        for (Tile t : allTiles) {
            for (int sideID = 0; sideID < 4; sideID++) {
                if (currentTile.side[sideID] != null) {
                    continue;
                }
                if (checkMatch(currentTile, t, sideID)) {
                    clickSides(currentTile, t, sideID);
                }
            }
        }
    }


    /**
     * If two tiles lie next to each other, make sure it is keep tracked of properly
     * @param currentTile
     * @param tile otherTile
     * @param sideID
     */
    public void clickSides(Tile currentTile, Tile tile, int sideID) {
        currentTile.side[sideID] = tile;
        switch (sideID) {
            case 0:
                tile.side[2] = currentTile;
                break;
            case 1:
                tile.side[3] = currentTile;
                break;
            case 2:
                tile.side[0] = currentTile;
                break;
            case 3:
                tile.side[1] = currentTile;
                break;
            default:
                System.out.println("Unexpected error in switch");
                System.exit(1);
        }
    }


    /**
     * Given the sideID and the orientation of the otherTile, is there a match between the 2 tiles?
     * @param currentTile
     * @param otherTile
     * @param sideID 0 = North, 1 = East, 2 = South, 3 = West
     * @return
     */
    private boolean checkMatch(Tile currentTile, Tile otherTile, int sideID) {
        for (int i = 0; i < tileSize; i++) {
            if ((sideID == 0 && !currentTile.pixels[0][i].equals(otherTile.pixels[tileSize - 1][i])) ||
                    (sideID == 1 && !currentTile.pixels[i][tileSize - 1].equals(otherTile.pixels[i][0])) ||
                    (sideID == 2 && !currentTile.pixels[tileSize - 1][i].equals(otherTile.pixels[0][i])) ||
                    (sideID == 3 && !currentTile.pixels[i][0].equals(otherTile.pixels[i][tileSize - 1]))) {
                return false;
            }
        }
        return true;
    }

    private void solutionPartOne() {
        long result = 1;
        for (Tile tile : allTiles) {
            int emptySideCounter = 0;
            for (Tile side : tile.side) {
                if (side == null) {
                    emptySideCounter++;
                }
            }
            if (emptySideCounter == 2) {
                result *= tile.id;
            } else if (emptySideCounter > 2) {
                System.out.println("Tile " + tile.id + " has too many empty sides");
                System.exit(1);
            }
        }
        System.out.println("Part 1: " + result);
    }

    private void concatTiles() {
        int tilesN = (int) Math.sqrt(allTiles.size());
        int imageSize = tilesN * tileSize - 2 * tilesN;
        image = new String[imageSize][imageSize];
        Tile topleft = findTopLeft();
        Tile currentTile = topleft;

        for (int i = 0; i < tilesN; i++) {
            for (int col = 0; col < tilesN; col++) {
                for (int j = 1; j < tileSize - 1; j++) {
                    for (int k = 1; k < tileSize - 1; k++) {
                        image[(tileSize * i - 2 * i) + (j - 1)][(tileSize * col - 2 * col) + (k - 1)] = currentTile.pixels[j][k];
                    }
                }
                currentTile = currentTile.side[1];
            }
            currentTile = topleft.side[2];
            topleft = currentTile;
        }
        printImage(imageSize);
    }

    public Tile findTopLeft() {
        for (Tile tile : allTiles) {
            int emptySideCounter = 0;
            for (Tile side : tile.side) {
                if (side == null) {
                    emptySideCounter++;
                }
            }
            if (emptySideCounter == 2 && tile.side[0] == null && tile.side[3] == null) {
                return tile;
            }
        }
        System.out.println("No topleft found");
        System.exit(1);
        return null;
    }

    /**
     * Used for debugging purposes
     */
    public void printImage(int imageSize) {
        for (int i = 0; i < imageSize; i++) {
            String line = "";
            for (int j = 0; j < imageSize; j++) {
                line = line + image[i][j];
            }
            System.out.println(line);
        }
    }

    /*
    01234567890123456789
                      #
    #    ##    ##    ###
     #  #  #  #  #  #
     */

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }
}
