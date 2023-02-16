package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solution {
    private int nOfLines = 200;
    private String[] array = new String[nOfLines];

    public static void main(String[] args) {
        Solution part = new Solution();
        part.setup();
        part.solution();
    }

    public void setup() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("2021/src/Day8/input.txt"));
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /*
            number  | n of segments
                0   |       6
                1   |       2
                2   |       5
                3   |       5
                4   |       4
                5   |       5
                6   |       6
                7   |       3
                8   |       7
                9   |       6
    */

    public void solution() {
        int counterP1 = 0;
        long sumP2 = 0;
        for (int i = 0; i < nOfLines; i++) {
            String[] inputOutput = array[i].split(" [|] ");
            String[] inputDigits = inputOutput[0].split(" ");
            String[] outputDigits = inputOutput[1].split(" ");

            String one = "";
            String four = "";
            for (String number : inputDigits) {
                if (number.length() == 2) {
                    one = number;
                } else if (number.length() == 4) {
                    four = number;
                }
            }

            String myNumber = "";
            for (String number : outputDigits) {
                int fourMatchCounter = 0;
                boolean oneMatch = true;
                if (!one.equals("") && !four.equals("")) {
                    for (int j = 0; j < 2; j++) {
                        if (!number.contains(String.valueOf(one.charAt(j)))) {
                            oneMatch = false;
                        }
                    }
                    for (int j = 0; j < 4; j++) {
                        if (number.contains(String.valueOf(four.charAt(j)))) {
                            fourMatchCounter++;
                        }
                    }
                } else {
                    System.out.println("NO FOUR");
                    System.exit(1);
                }
                switch (number.length()) {
                    case 2: //1
                        counterP1++;
                        myNumber += "1";
                        break;
                    case 3: //7
                        counterP1++;
                        myNumber += "7";
                        break;
                    case 4: //4
                        counterP1++;
                        myNumber += "4";
                        break;
                    case 5: //2, 3, 5
                        if (fourMatchCounter == 2) { //2
                            myNumber += "2";
                        } else if (oneMatch) { //3
                            myNumber += "3";
                        } else { //5
                            myNumber += "5";
                        }
                        break;
                    case 6://0, 9
                        if (!oneMatch) {
                            myNumber += "6";
                        } else if (fourMatchCounter == 4) {
                            myNumber += "9";
                        } else {
                            myNumber += "0";
                        }

                        break;
                    case 7: //8
                        counterP1++;
                        myNumber += "8";
                        break;
                    default:
                        System.out.println("Unexpected length for number: " + number);
                        System.exit(1);
                }
            }
            sumP2 += Long.parseLong(myNumber);

        }

        System.out.println("Part 1: " + counterP1);
        System.out.println("Part 2: " + sumP2);
    }
}
