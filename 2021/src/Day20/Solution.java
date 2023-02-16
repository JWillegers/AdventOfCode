package Day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solution {
    private String enchancementString;
    private int nOfLines = 100;
    private String[][] inputImage = new String[nOfLines][nOfLines];

    public static void main(String[] args) {
        Solution part = new Solution();
        part.setup();
        part.solution(part.inputImage, 1);
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2021/src/Day20/input.txt"));
            enchancementString = reader.readLine();
            reader.readLine(); //empty line
            for (int i = 0; i < nOfLines; i++) {
                String line = reader.readLine();
                for (int j = 0; j < line.length(); j++) {
                    inputImage[i][j] = line.charAt(j) == '#' ? "1" : "0";
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    //between 5339 and 5829
    public void solution(String[][] input, int round) {
        String[][] output = new String[input.length + 2][input.length + 2];
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output.length; j++) {
                StringBuilder toConvert = new StringBuilder();
                for (int k = i - 2; k <= i; k++) {
                    for (int l = j - 2; l <= j; l++) {
                        try {
                            toConvert.append(input[k][l]);
                        } catch (IndexOutOfBoundsException expected) {
                            if (round % 2 == 1) {
                                toConvert.append("0");
                            } else {
                                toConvert.append("1");
                            }
                        }
                    }
                }
                int index = Integer.parseInt(toConvert.toString(), 2);
                output[i][j] = enchancementString.charAt(index) == '#' ? "1" : "0";
            }
        }


        if (round == 2 || round == 50) {
            int lit = 0;
            for (int i = 0; i < output.length; i++) {
                for (int j = 0; j < output.length; j++) {
                    if (output[i][j].equals("1")) {
                        lit++;
                    }
                }
            }
            if (round == 2) {
                System.out.println("part1: " + lit);
                solution(output, round + 1);
            } else {
                System.out.println("part2: " + lit);
            }
        } else {
            solution(output, round + 1);
        }
    }
}
