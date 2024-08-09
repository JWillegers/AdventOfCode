package y2020.src.Day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartA {
    private int nOfLines = 8;
    private BufferedReader reader;
    private List<Coordinate> coordinates;
    private List<Coordinate> copy;
    private int maxXY;
    private int maxZ;
    private int minXY;



    public static void main(String[] args) {
        PartA part = new PartA();
        part.setup();
        part.solution();
    }

    public PartA() {}

    public PartA(PartA partA) {
        this.coordinates = new ArrayList<>();
        for (Coordinate cord : partA.coordinates) {
            this.coordinates.add(new Coordinate(cord));
        }
    }

    public void setup() {
        try {
            maxZ = 0;
            maxXY = nOfLines / 2 - 1 ;
            minXY = - nOfLines / 2;
            this.coordinates = new ArrayList<>();
            reader = new BufferedReader(new FileReader("y2020/src/Day17/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                String line = reader.readLine();
                for (int j = 0; j < line.length(); j++) {
                    Coordinate cord = new Coordinate(j - (line.length() / 2), i - (nOfLines / 2), 0, line.charAt(j) == '#');
                    this.coordinates.add(cord);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }



    public void solution() {
        for (int i = 0; i < 6; i++) {
            //adding a layer around the existing cubes

            //x
            for (int y = minXY; y <= maxXY; y++) {
                for (int z = 0; z <= maxZ; z++) {
                    this.coordinates.add(new Coordinate(minXY - 1, y, z, false));
                    this.coordinates.add(new Coordinate(maxXY + 1, y, z, false));
                    if (z != 0) {
                        this.coordinates.add(new Coordinate(minXY - 1, y, -z, false));
                        this.coordinates.add(new Coordinate(maxXY + 1, y, -z, false));
                    }
                }
            }

            //y
            for (int x = minXY - 1; x <= maxXY + 1; x++) {
                for (int z = 0; z <= maxZ; z++) {
                    this.coordinates.add(new Coordinate(x, minXY - 1, z, false));
                    this.coordinates.add(new Coordinate(x, maxXY + 1, z, false));
                    if (z != 0) {
                        this.coordinates.add(new Coordinate(x, minXY - 1, -z, false));
                        this.coordinates.add(new Coordinate(x, maxXY + 1, -z, false));
                    }
                }
            }

            maxXY++;
            minXY--;
            maxZ++;

            //z
            for (int x = minXY; x <= maxXY; x++) {
                for (int y = minXY; y <- maxXY; y++) {
                    this.coordinates.add(new Coordinate(x, y, maxZ, false));
                    this.coordinates.add(new Coordinate(x, y, -maxZ, false));
                }
            }

            PartA copy = new PartA(this);
            for (int cord = 0; cord < this.coordinates.size(); cord++) {
                int neighbours = calculateAmountOfNeighbours(copy.coordinates.get(cord), copy);
                if (copy.coordinates.get(cord).active && !(neighbours == 2 || neighbours == 3)) {
                    this.coordinates.get(cord).active = false;
                } else if (!copy.coordinates.get(cord).active && neighbours == 3) {
                    this.coordinates.get(cord).active = true;
                }
                System.out.println("Copy status: " + copy.coordinates.get(cord).active +
                        "\nActual status: " + this.coordinates.get(cord).active);
            }
            if (i != 5) {
                System.out.println("\n---------- NEW ITERATION ---------- \n");
            }

        }

        int sum = 0;
        for (Coordinate cord : coordinates) {
            if (cord.active) {
                sum++;
            }
        }

        System.out.println("\n\n\nactive cubes: " + sum);
    }

    public int calculateAmountOfNeighbours(Coordinate cord, PartA part) {
        int neighbours = 0;
        System.out.println("\nNow testing for cords: x="
                + cord.x + ", y=" + cord.y + ", z=" + cord.z);
        for (int ix = cord.x - 1; ix < cord.x + 2; ix++) {
            for (int iy = cord.y - 1; iy < cord.y + 2; iy++) {
                for (int iz = cord.z - 1; iz < cord.z + 2; iz++) {
                    if (!(ix == cord.x && iy == cord.y && iz == cord.z)) {
                        for (int i = 0; i < part.coordinates.size(); i++) {
                            Coordinate cordA = part.coordinates.get(i);

                            if (cordA.x == ix && cordA.y == iy && cordA.z == iz && cordA.active) {
                                System.out.println("x=" + ix + " y=" + iy + " z=" + iz);
                                neighbours++;
                                break;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("amount of neigbours: " + neighbours);

        return neighbours;
    }
}