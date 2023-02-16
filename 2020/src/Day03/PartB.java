package Day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartB {
    public static void main(String[] args) {
        try {
            int nOfLines = 323;
            BufferedReader reader = new BufferedReader(new FileReader("2020/src/Day03/input.txt"));
            String[] array = new String[nOfLines];
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();

            int[] right = new int[]{1, 3, 5, 7, 1};
            int[] down = new int[]{1, 1, 1, 1, 2};
            int[] trees = new int[5];
            int lineLength = array[0].length();
            int charCounter;
            int treeCounter;

            for (int j = 0; j < 5; j++) {
                charCounter = 0;
                treeCounter = 0;
                for (int i = 0; i < nOfLines; i += down[j]) {
                    if (array[i].charAt(charCounter) == '#') {
                        treeCounter++;
                    }
                    charCounter = charCounter + right[j];
                    if (charCounter > lineLength - 1) {
                        charCounter = charCounter % lineLength;
                    }

                }
                trees[j] = treeCounter;
            }

            int treeMultiplier = 1;


            for (int k = 0; k < 5; k++) {
                treeMultiplier = treeMultiplier * trees[k];
            }

            System.out.println(treeMultiplier);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
