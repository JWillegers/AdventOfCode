package Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartA {
    private int nOfLines = 776;
    private BufferedReader reader;
    private String[] array = new String[nOfLines];

    public static void main(String[] args) {
        PartA part = new PartA();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            reader = new BufferedReader(new FileReader("2020/src/Day12/input.txt"));
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
        int distanceEastWest = 0; //going east is positive, going west is negative
        Direction direction = Direction.EAST;

        for (int i = 0; i < nOfLines; i++) {
            try {
                char instruction = array[i].charAt(0);
                int number = Integer.parseInt(array[i].substring(1));
                if (instruction == 'E' || (instruction == 'F' && direction == Direction.EAST)) {
                    distanceEastWest += number;
                } else if (instruction == 'W' || (instruction == 'F' && direction == Direction.WEST)) {
                    distanceEastWest -= number;
                } else if (instruction == 'N' || (instruction == 'F' && direction == Direction.NORTH)) {
                    distanceNorthSouth += number;
                } else if (instruction == 'S' || (instruction == 'F' && direction == Direction.SOUTH)) {
                    distanceNorthSouth -= number;
                } else if (instruction == 'R' || instruction == 'L') {
                    if (number == 180) {
                        if (direction == Direction.EAST) {
                            direction = Direction.WEST;
                        } else if (direction == Direction.WEST) {
                            direction = Direction.EAST;
                        } else if (direction == Direction.NORTH) {
                            direction = Direction.SOUTH;
                        } else if (direction == Direction.SOUTH) {
                            direction = Direction.NORTH;
                        }
                    } else if ((instruction == 'R' && number == 90) || (instruction == 'L' && number == 270)) {
                        if (direction == Direction.EAST) {
                            direction = Direction.SOUTH;
                        } else if (direction == Direction.WEST) {
                            direction = Direction.NORTH;
                        } else if (direction == Direction.NORTH) {
                            direction = Direction.EAST;
                        } else if (direction == Direction.SOUTH) {
                            direction = Direction.WEST;
                        }
                    } else if ((instruction == 'L' && number == 90) || (instruction == 'R' && number == 270)) {
                        if (direction == Direction.EAST) {
                            direction = Direction.NORTH;
                        } else if (direction == Direction.WEST) {
                            direction = Direction.SOUTH;
                        } else if (direction == Direction.NORTH) {
                            direction = Direction.WEST;
                        } else if (direction == Direction.SOUTH) {
                            direction = Direction.EAST;
                        }
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