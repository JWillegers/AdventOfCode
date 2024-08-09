package y2021.src.Day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private int nOfLines = 865; //865
    private List<String> foldInstruction = new ArrayList<>();

    public static void main(String[] args) {
        Solution part = new Solution();
        part.setup();
    }

    public void setup() {
        try {
            boolean fold = false;
            BufferedReader reader = new BufferedReader(new FileReader("y2021/src/Day13/input.txt"));
            boolean[][] array = new boolean[2*nOfLines][2*nOfLines];
            for (int i = 0; i < nOfLines; i++) {
                String line = reader.readLine();
                if (line.isEmpty()) {
                    fold = true;
                } else if (fold) {
                    foldInstruction.add(line);
                } else {
                    String[] split = line.split(",");
                    array[Integer.parseInt(split[0])][Integer.parseInt(split[1])] = true;
                }
            }
            reader.close();
            solution(array, 0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution(boolean[][] paper, int instruction) {
        String myInstruction = foldInstruction.get(instruction);
        int number = Integer.parseInt(myInstruction.split("=")[1]);
        boolean[][] newPaper;
        if (myInstruction.contains("y")) {
            newPaper = new boolean[paper.length][number];
            for (int i = 0; i < paper.length; i++) {
                for (int j = 0; j < 2 * number + 1; j++) {
                    if (j < number) {
                        newPaper[i][j] = paper[i][j];
                    } else if (j > number && paper[i][j]) {
                        newPaper[i][number - (j - number)] = paper[i][j];
                    }
                 }
            }
        } else {
            newPaper = new boolean[number][paper[0].length];
            for (int i = 0; i < 2 * number + 1; i++) {
                for (int j = 0; j < paper[0].length; j++) {
                    if (i < number) {
                        newPaper[i][j] = paper[i][j];
                    } else if (i > number && paper[i][j]) {
                        newPaper[number - (i - number)][j] = paper[i][j];
                    }
                }
            }
        }
        if (instruction == foldInstruction.size() - 1 || instruction == 0) {
            if (instruction == 0) {
                int counter = 0;
                for (int i = 0; i < newPaper.length; i++) {
                    for (int j = 0; j < newPaper[0].length; j++) {
                        if (newPaper[i][j]) {
                            counter++;
                        }
                    }
                }
                System.out.println("part 1: "+ counter);
                solution(newPaper, 1);
            } else {
                System.out.println("Part 2:");
                for (int i = 0; i < newPaper[0].length; i++) {
                    String myLine = "";
                    for (int j = 0; j < newPaper.length; j++) {
                        if (newPaper[j][i]) {
                            myLine += "O";
                        } else {
                            myLine += " ";
                        }
                    }
                    System.out.println(myLine);
                }
            }
        } else {
            solution(newPaper, instruction + 1);
        }
    }
}
