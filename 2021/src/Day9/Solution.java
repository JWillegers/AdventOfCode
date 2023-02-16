package Day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {
    private int nOfLines = 100;
    private int[][] array = new int[nOfLines][nOfLines];

    public static void main(String[] args) {
        Solution part = new Solution();
        part.setup();
        part.partA();
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2021/src/Day9/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                String line = reader.readLine();
                for (int j = 0; j < nOfLines; j++) { //each line has nOfLines characters
                    array[i][j] = Integer.parseInt(String.valueOf(line.charAt(j)));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void partA() {
        int sum = 0;
        List<Integer> basins = new ArrayList<>();
        for (int i = 0; i < nOfLines; i++) {
            for (int j = 0; j < nOfLines; j++) {
                boolean lowest = true;
                if (i != 0) {
                    lowest = array[i][j] < array[i - 1][j];
                }
                if (lowest && i != nOfLines - 1) {
                    lowest = array[i][j] < array[i + 1][j];
                }
                if (lowest && j != 0) {
                    lowest = array[i][j] < array[i][j - 1];
                }
                if (lowest && j != nOfLines - 1) {
                    lowest = array[i][j] < array[i][j + 1];
                }
                if (lowest) {
                    sum += array[i][j] + 1;
                    basins.add(partB(i, j));
                }
            }
        }

        System.out.println("partA: " + sum);
        Collections.sort(basins);
        System.out.println("partB: " + basins.get(basins.size() - 1) * basins.get(basins.size() - 2) * basins.get(basins.size() - 3));
    }

    private class Cord {
        int x;
        int y;
        private Cord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public int partB(int x, int y) {
       int sum = 1;
       List<Cord> checked = new ArrayList<>();
       checked.add(new Cord(x, y));
       boolean run = true;
       while (run) {
           boolean allchecked = true;
           for (int i = 0; i < checked.size(); i++) {
               Cord cord = checked.get(i);
               int check = 0;
               boolean[][] neighbourCheck = new boolean[2][2];
               /*  shift (x,y)
                   (1,0)  (0,1)
                   (-1, 0) (0,-1)
                */
               if (array[cord.x][cord.y] != 9) {
                   for (Cord otherCord : checked) {
                       if (otherCord.x == cord.x - 1 && otherCord.y == cord.y) {
                           check++;
                           neighbourCheck[0][0] = true;
                       } else if (otherCord.x == cord.x + 1 && otherCord.y == cord.y) {
                           check++;
                           neighbourCheck[0][1] = true;
                       } else if (otherCord.x == cord.x && otherCord.y == cord.y - 1) {
                           check++;
                           neighbourCheck[1][0] = true;
                       } else if (otherCord.x == cord.x && otherCord.y == cord.y + 1) {
                           check++;
                           neighbourCheck[1][1] = true;
                       }
                       if (check == 4) {
                           break;
                       }
                   }
                   boolean edgeX = cord.x == 0 || cord.x == nOfLines - 1;
                   boolean edgeY = cord.y == 0 || cord.y == nOfLines - 1;

                   if (!(edgeX && edgeY && check == 2)) {
                       if (!((edgeX || edgeY) && check == 3)) {
                           if (check != 4) {
                               if (!neighbourCheck[0][0] && cord.x != 0) {
                                   int number = array[cord.x - 1][cord.y];
                                   if (number != 9) {
                                       sum++;
                                   }
                                   checked.add(new Cord(cord.x - 1, cord.y));
                               }
                               if (!neighbourCheck[0][1] && cord.x + 1 != nOfLines) {
                                   int number = array[cord.x + 1][cord.y];
                                   if (number != 9) {
                                       sum++;
                                   }
                                   checked.add(new Cord(cord.x + 1, cord.y));
                               }
                               if (!neighbourCheck[1][0] && cord.y != 0) {
                                   int number = array[cord.x][cord.y - 1];
                                   if (number != 9) {
                                       sum++;
                                   }
                                   checked.add(new Cord(cord.x, cord.y - 1));
                               }
                               if (!neighbourCheck[1][1] && cord.y + 1 != nOfLines) {
                                   int number = array[cord.x][cord.y + 1];
                                   if (number != 9) {
                                       sum++;
                                   }
                                   checked.add(new Cord(cord.x, cord.y + 1));
                               }
                               allchecked = false;
                           }
                       }
                   }
               }
           }
           run = !allchecked;
       }
       return sum;
    }
}
