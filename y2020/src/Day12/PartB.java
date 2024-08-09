package y2020.src.Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartB {
    private int nOfLines = 776;
    private BufferedReader reader;
    private String[] array = new String[nOfLines];

    public static void main(String[] args) {
        PartB part = new PartB();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            reader = new BufferedReader(new FileReader("y2020/src/Day12/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution() {
        int distanceNorthSouth = 0; //going north is positive, going south is negative
        int waypointNorthSouth = 1; //going north is positive, going south is negative
        int distanceEastWest = 0; //going east is positive, going west is negative
        int waypointEastWest = 10; //going north is positive, going south is negativ

        for (int i = 0; i < nOfLines; i++) {
            try {
                char instruction = array[i].charAt(0);
                int number = Integer.parseInt(array[i].substring(1));
                if (instruction == 'E') {
                    waypointEastWest += number;
                } else if (instruction == 'W') {
                    waypointEastWest -= number;
                } else if (instruction == 'N') {
                    waypointNorthSouth += number;
                } else if (instruction == 'S') {
                    waypointNorthSouth -= number;
                } else if (instruction == 'F') {
                    distanceNorthSouth += number * waypointNorthSouth;
                    distanceEastWest += number * waypointEastWest;
                } else if (instruction == 'R' || instruction == 'L') {
                    if (number == 180) {
                        waypointEastWest = -waypointEastWest;
                        waypointNorthSouth = -waypointNorthSouth;
                    } else if ((instruction == 'R' && number == 90) || (instruction == 'L' && number == 270)) {
                        int temp = waypointNorthSouth;
                        waypointNorthSouth = -waypointEastWest;
                        waypointEastWest = temp;
                    } else if ((instruction == 'L' && number == 90) || (instruction == 'R' && number == 270)) {
                        int temp = waypointNorthSouth;
                        waypointNorthSouth = waypointEastWest;
                        waypointEastWest = -temp;

                    } else {
                        System.exit(2);
                    }
                } else {
                    System.exit(2);
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        System.out.println(Math.abs(distanceNorthSouth) + Math.abs(distanceEastWest));
    }
}