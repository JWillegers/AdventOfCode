package y2020.src.Day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartA {
    public static void main(String[] args) {
        try {
            int nOfLines = 323;
            BufferedReader reader = new BufferedReader(new FileReader("y2020/src/Day03/input.txt"));
            String[] array = new String[nOfLines];
            for (int i = 0; i < nOfLines; i++) {
                array[i] = reader.readLine();
            }
            reader.close();

            int treeCounter = 0;
            int charCounter = 0;
            int lineLength = array[0].length();

            for (int i = 1; i < nOfLines; i++) {
                charCounter = charCounter + 3;
                if (charCounter > lineLength - 1) {
                    charCounter = charCounter % lineLength;
                }
                if (array[i].charAt(charCounter) == '#') {
                    treeCounter++;
                }
            }

            System.out.println(treeCounter);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
