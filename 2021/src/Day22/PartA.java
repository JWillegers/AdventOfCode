package Day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartA {
    private final int nOfLines = 420;
    private final int region = 101;
    private final int shift = 50;
    private final int maxAcceptable = 50;
    private final int minAcceptable = -50;
    private String[] array = new String[nOfLines];
    private boolean[][][] cubes = new boolean[region][region][region];

    public static void main(String[] args) {
        PartA part = new PartA();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2021/src/Day22/input.txt"));
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
        for (int i = 0; i < nOfLines; i++) {
            /*
                [1] => min x
                [3] => max x
                [5] => min y
                [7] => max y
                [9] => min z
                [11] => max z
             */
            String[] split = array[i].split("[,=.]");
            for (int x = Math.max(minAcceptable, Integer.parseInt(split[1])); x <= Math.min(maxAcceptable, Integer.parseInt(split[3])); x++) {
                for (int y = Math.max(minAcceptable, Integer.parseInt(split[5])); y <= Math.min(maxAcceptable, Integer.parseInt(split[7])); y++) {
                    for (int z = Math.max(minAcceptable, Integer.parseInt(split[9])); z <= Math.min(maxAcceptable, Integer.parseInt(split[11])); z++) {
                        cubes[x + shift][y + shift][z + shift] = split[0].contains("on");
                    }
                }
            }

        }

        int counter = 0;
        for (int x = 0; x < cubes.length; x++) {
            for (int y = 0; y < cubes.length; y++) {
                for (int z = 0; z < cubes.length; z++) {
                    if (cubes[x][y][z]) {
                        counter++;
                    }
                }
            }
        }

        System.out.println(counter);
    }
}
