package y2021.src.Day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartA {
    private int nOfLines = 601;
    //private int nOfLines = 19;
    private List<BingoField[][]> bingoCards;
    private int[] drawNumbers;

    public static void main(String[] args) {
        PartA part = new PartA();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("y2021/src/Day4/input.txt"));
            //BufferedReader reader = new BufferedReader(new FileReader("src/Day4/test.txt"));
            BingoField[][] bingoCardX = new BingoField[5][5];
            bingoCards = new ArrayList<>();
            int row = 0;
            for (int i = 0; i < nOfLines; i++) {
                String line = reader.readLine();
                if (i == 0) {
                    String[] bingoBalls = line.split(",");
                    drawNumbers = new int[bingoBalls.length];
                    for (int j = 0; j < bingoBalls.length; j++) {
                        drawNumbers[j] = Integer.parseInt(bingoBalls[j]);
                    }
                } else if (line.isEmpty()) {
                    if (i != 1) {
                        bingoCards.add(bingoCards.size(),bingoCardX);
                    }
                    bingoCardX = new BingoField[5][5];
                    row = 0;
                } else {
                    if (line.charAt(0) == ' ') {
                        line = line.substring(1);
                    }
                    String[] numbers = line.split(" ");
                    int column = 0;
                    for (int j = 0; j < numbers.length; j++) {
                        if (!numbers[j].isEmpty()) {
                            bingoCardX[row][column] = new BingoField(Integer.parseInt(numbers[j]), false);
                            column++;
                        }
                    }
                    row++;

                    if (i == nOfLines - 1) {
                        bingoCards.add(bingoCards.size(), bingoCardX);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void solution() {
        boolean bingo = false;
        int count = 0;
        int sum = 0;

        while (!bingo && count < drawNumbers.length) {
            //draw a new number and mark that number on all fields as drawn = true
            for (BingoField[][] bingoCard : bingoCards) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (bingoCard[i][j].number == drawNumbers[count]) {
                            bingoCard[i][j].drawn = true;
                        }
                    }
                }
            }

            //check if there is a bingo
            for (BingoField[][] bingoCard : bingoCards) {
                //check for rows
                for (int i = 0; i < 5; i++) {
                    boolean rowBingo = true;
                    for (int j = 0; j < 5; j++) {
                        if (!bingoCard[i][j].drawn) {
                            rowBingo = false;
                        }
                    }
                    if (rowBingo) {
                        bingo = true;
                        break;
                    }
                }

                //check for columns
                for (int i = 0; i < 5; i++) {
                    boolean columnBingo = true;
                    for (int j = 0; j < 5; j++) {
                        if (!bingoCard[j][i].drawn) {
                            columnBingo = false;
                        }
                    }
                    if (columnBingo) {
                        bingo = true;
                        break;
                    }
                }

                //sum
                if (bingo) {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (!bingoCard[i][j].drawn) {
                                sum += bingoCard[i][j].number;
                            }
                        }
                    }
                    break;
                }
            }

            if (!bingo) {
                count++;
            }
        }


        System.out.println("last number: " + drawNumbers[count]);
        System.out.println("sum: "+ sum);
        System.out.println("answer: " + sum*drawNumbers[count]);
    }
}
